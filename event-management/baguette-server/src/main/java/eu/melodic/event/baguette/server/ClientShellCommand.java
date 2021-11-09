/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import com.google.gson.Gson;
import eu.melodic.event.util.EventBus;
import eu.melodic.event.util.GroupingConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.session.ServerSession;
import org.cryptacular.util.CertUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ClientShellCommand implements Command, Runnable, SessionAware {

    private final static Object LOCK = new Object();
    private final static AtomicLong counter = new AtomicLong(0);
    private final static Set<ClientShellCommand> activeCmdList = new HashSet<>();
    private final static long INPUT_CHECK_DELAY = 100;

    public static Set<ClientShellCommand> getActive() {
        return Collections.unmodifiableSet(activeCmdList);
    }

    private InputStream in;
    private PrintStream out;
    private PrintStream err;
    private ExitCallback callback;
    private final AtomicBoolean callbackCalled = new AtomicBoolean(false);

    @Getter @Setter
    private String id;
    @Getter @Setter
    private boolean echoOn = false;

    @Getter private String clientId;
    @Getter private String clientBrokerUrl;
    @Getter private String clientBrokerUsername;
    @Getter private String clientBrokerPassword;
    private String clientIpAddress;
    private String clientHostname;
    private String clientCanonicalHostname;
    private int clientPort = -1;
    @Getter private String clientCertificate;   // Broker certificate of Client

    @Getter @Setter private int clientClusterNodePort;
    @Getter @Setter private String clientClusterNodeAddress;
    @Getter @Setter private String clientClusterNodeHostname;
    @Getter @Setter private Object clientZone;

    private final ServerCoordinator coordinator;
    private final boolean clientAddressOverrideAllowed;
    @Getter
    private ServerSession session;
    @Getter @Setter
    private boolean closeConnection = false;

    private Map<String,Object> inputsMap = new HashMap<>();
    private EventBus<String,Object,Object> eventBus;
    @Getter
    private Exception lastException;
    private NodeRegistry nodeRegistry;

    public ClientShellCommand(ServerCoordinator coordinator, boolean allowClientOverrideItsAddress, EventBus<String,Object,Object> eventBus, NodeRegistry registry) {
        synchronized (LOCK) {
            id = String.format("#%05d", counter.getAndIncrement());
        }
        this.coordinator = coordinator;
        this.clientAddressOverrideAllowed = allowClientOverrideItsAddress;
        this.eventBus = eventBus;
        this.nodeRegistry = registry;
    }

    public void setSession(ServerSession session) {
        log.info("{}--> Got session : {}", id, session);
        this.session = session;
        eventBus.send("BAGUETTE_SERVER_CLIENT_SESSION_STARTED", this);
		
		/*try {
			String clientIpAddr = ((InetSocketAddress)session.getIoSession().getRemoteAddress()).getAddress().getHostAddress();
			int clientPort = ((InetSocketAddress)session.getIoSession().getRemoteAddress()).getPort();
			log.info("{}--> Client connection : {}:{}", id, clientIpAddr, clientPort);
			String username = session.getUsername();
			log.info("{}--> Client session username: {}", username);
		} catch (Exception ex) {}*/
    }

    public void setInputStream(InputStream in) {
        this.in = in;
    }

    public void setOutputStream(OutputStream out) {
        this.out = new PrintStream(out, true);
    }

    public void setErrorStream(OutputStream err) {
        this.err = new PrintStream(err, true);
    }

    public void setExitCallback(ExitCallback callback) {
        this.callback = callback;
    }

    public void start(Environment env) {
        new Thread(this).start();
    }

    public void destroy() {
    }

    public void run() {
        if (closeConnection) {
            log.warn("{}--> Exiting immediately because 'closeConnection' flag is set", id);
            eventBus.send("BAGUETTE_SERVER_CLIENT_SESSION_CLOSING_IMMEDIATELY", this);
            coordinator.unregister(this);
            if (this.session!=null && this.session.isOpen()) {
                try {
                    this.session.close();
                } catch (IOException e) {
                    log.warn("Closing session caused on exception: ", e);
                }
                this.session = null;
            }
            if (!callbackCalled.getAndSet(true)) {
                callback.onExit(2);
            }
            log.info("{}--> Thread stopped immediately", id);
            eventBus.send("BAGUETTE_SERVER_CLIENT_SESSION_CLOSED_IMMEDIATELY", this);
            return;
        }

        synchronized (activeCmdList) {
            activeCmdList.add(this);
        }
        eventBus.send("BAGUETTE_SERVER_CLIENT_STARTING", this);

        try {
            log.info("{}==> Thread started", id);
            out.printf("CLIENT (%s) : START\n", id);

            this.clientIpAddress = getClientIpAddress();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                log.info("{}--> {}", id, line);

                //if (echoOn) out.printf("CLIENT (%s) : ECHO : %s\n", id, line);
                if (echoOn) out.printf("ECHO %s\n", line);
                //if (line.equalsIgnoreCase("exit")) break;

                if (line.startsWith("-HELLO FROM CLIENT:")) {
                    getClientInfoFromGreeting(line.substring("-HELLO FROM CLIENT:".length()));
                    coordinator.register(this);
                    eventBus.send("BAGUETTE_SERVER_CLIENT_REGISTERED", this);
                } else if (line.startsWith("-INPUT:")) {
                    String input = line.substring("-INPUT:".length());
                    String[] part = input.split(":",2 );
                    inputsMap.put(part[0].trim(), deserializeFromString(part[1]));
                } else if (StringUtils.startsWithIgnoreCase(line, "SERVER-")) {
                    String[] lineArgs = line.split(" ", 2);
                    if ("SERVER-GET-NODE-SSH-CREDENTIALS".equalsIgnoreCase(lineArgs[0].trim()) && lineArgs.length>1) {
                        String nodeAddress = lineArgs[1].trim();
                        if (!nodeAddress.isEmpty()) {
                            NodeRegistryEntry entry = nodeRegistry.getNodeByAddress(nodeAddress);
                            if (entry!=null) {
                                Map<String, String> preregInfo = entry.getPreregistration();
                                log.warn(">>>>>>>>>>>>>>>>>>>>>>   NODE PREREG INFO: address={}\n{}", nodeAddress, preregInfo);

                                if (preregInfo!=null) {
                                    String preregInfoStr = new Gson().toJson(preregInfo);
                                    log.warn(">>>>>>>>>>>>>>>>>>>>>>   NODE PREREG INFO: STR={}\n{}", nodeAddress, preregInfoStr);
                                    sendToClient(preregInfoStr);
                                } else {
                                    log.warn(">>>>>>>>>>>>>>>>>>>>>>   NO PRE-REGISTRATION INFO FOR NODE: {}", nodeAddress);
                                    sendToClient("{}");
                                }
                            } else {
                                log.warn(">>>>>>>>>>>>>>>>>>>>>>   UNKNOWN NODE: {}", nodeAddress);
                                sendToClient("{}");
                            }
                        }
                    }
                } else if (line.equalsIgnoreCase("READY")) {
                    coordinator.clientReady(this);
                } else {
                    coordinator.processClientInput(this, line);
                }
            }
            eventBus.send("BAGUETTE_SERVER_CLIENT_EXITING", this);

            log.info("{}==> Signaling client to exit", id);
            out.println("EXIT");

        } catch (IOException ex) {
            log.warn("{}==> EXCEPTION : {}", id, ex);
            out.printf("EXCEPTION %s\n", ex);
            this.lastException = ex;
            eventBus.send("BAGUETTE_SERVER_CLIENT_EXCEPTION", this);
        } finally {
            synchronized (activeCmdList) {
                activeCmdList.remove(this);
            }
            log.info("{}--> Thread stops", id);
            coordinator.unregister(this);
            eventBus.send("BAGUETTE_SERVER_CLIENT_UNREGISTERED", this);
            if (!callbackCalled.getAndSet(true)) {
                callback.onExit(0);
            }
            eventBus.send("BAGUETTE_SERVER_CLIENT_EXITED", this);
        }
    }

    protected Object deserializeFromString(String s) {
        try {
            byte[] data = Base64.getDecoder().decode(s);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception ex) {
            return ex;
        }
    }

    protected void getClientInfoFromGreeting(String greetingInfo) {
        if (StringUtils.isBlank(greetingInfo)) return;
        String[] clientInfo = greetingInfo.trim().split(" ");

        for (String s : clientInfo) {
            if (StringUtils.isBlank(s)) continue;
            if (s.startsWith("id=")) {
                this.clientId = s.substring("id=".length()).replace("~~", " ");
                log.info("{}--> Client Id: {}", id, clientId);
            } else
            if (s.startsWith("broker=")) {
                this.clientBrokerUrl = s.substring("broker=".length());
                log.info("{}--> Broker URL: {}", id, clientBrokerUrl);
            } else
            if (s.startsWith("address=")) {
                if (clientAddressOverrideAllowed) {
                    String addr = s.substring("address=".length());
                    if (StringUtils.isNotBlank(addr)) {
                        this.clientIpAddress = addr.trim();
                        log.info("{}--> Effective IP: {}", id, clientIpAddress);
                    }
                }
            } else
            if (s.startsWith("port=")) {
                if (clientAddressOverrideAllowed) {
                    try {
                        int port = Integer.parseInt(s.substring("port=".length()));
                        if (port>0 && port<65536) {
                            this.clientPort = port;
                            log.info("{}--> Effective Port: {}", id, clientPort);
                        }
                    } catch (Exception ex) {
                        log.warn("{}--> Invalid Port value: {}: {}", id, s.substring("port=".length()), ex.getMessage());
                    }
                }
            } else
            if (s.startsWith("username=")) {
                this.clientBrokerUsername = s.substring("username=".length());
                log.info("{}--> Broker Username: {}", id, clientBrokerUsername);
            } else
            if (s.startsWith("password=")) {
                this.clientBrokerPassword = s.substring("password=".length());
                log.info("{}--> Broker Password: {}", id, clientBrokerPassword);
            } else
            if (s.startsWith("cert=")) {
                this.clientCertificate = s.substring("cert=".length())
                        .replace("~~", " ")
                        .replace("##", "\r\n")
                        .replace("$$", "\n");
                log.info("{}--> Broker Cert.: {}", id, clientCertificate);

                // Get certificate alias from client Id or IP address
                String alias = /*StringUtils.isNotBlank(clientId)
                        ? clientId.trim()
                        :*/ getClientIpAddress();
                log.info("{}--> Adding/Replacing client certificate in Truststore: alias={}", id, alias);

                if (StringUtils.isNotEmpty(clientCertificate)) {
                    // Add certificate to truststore
                    try {
                        X509Certificate cert = (X509Certificate) coordinator
                                .getServer()
                                .getBrokerCepService()
                                .addOrReplaceCertificateInTruststore(alias, clientCertificate);
                        log.info("{}--> Added/Replaced client certificate in Truststore: alias={}, CN={}, certificate-names={}",
                                id, alias, cert.getSubjectDN().getName(), CertUtil.subjectNames(cert));
                    } catch (Exception e) {
                        log.warn("{}--> EXCEPTION while adding/replacing certificate in Trust store: alias={}, exception: ",
                                clientId, alias, e);
                    }
                } else {
                    log.info("{}--> Client PEM certificate is empty. Leaving truststore unchanged", id);
                }
            } else {
                log.warn("{}--> Unknown HELLO argument will be ignored: {}", id, s);
            }
        }

        if (StringUtils.isBlank(this.clientId) || "null".equalsIgnoreCase(this.clientId))
            this.clientId = getClientId();
        if (StringUtils.isBlank(this.clientIpAddress) || "null".equalsIgnoreCase(this.clientIpAddress))
            this.clientIpAddress = getClientIpAddress();
        if (this.clientPort<=0 || this.clientPort>65535)
            this.clientPort = getClientPort();
    }

    public String getClientId() {
        if (StringUtils.isNotBlank(clientId)) return clientId;
        clientId = getId();
        return clientId;
    }

    public String getClientIpAddress() {
        if (StringUtils.isNotBlank(clientIpAddress)) return clientIpAddress;
        clientIpAddress = ((InetSocketAddress) getSession().getIoSession().getRemoteAddress()).getAddress().getHostAddress();
        return clientIpAddress;
    }

    public String getClientHostname() {
        if (StringUtils.isNotBlank(clientHostname)) return clientHostname;
        clientHostname = ((InetSocketAddress) getSession().getIoSession().getRemoteAddress()).getAddress().getHostName();
        return clientHostname;
    }

    public String getClientCanonicalHostname() {
        if (StringUtils.isNotBlank(clientCanonicalHostname)) return clientCanonicalHostname;
        clientCanonicalHostname = ((InetSocketAddress) getSession().getIoSession().getRemoteAddress()).getAddress().getCanonicalHostName();
        return clientCanonicalHostname;
    }

    public int getClientPort() {
        if (clientPort > 0) return clientPort;
        clientPort = ((InetSocketAddress) getSession().getIoSession().getRemoteAddress()).getPort();
        return clientPort;
    }

    public void sendToClient(String msg) {
        if (msg == null || (msg = msg.trim()).isEmpty()) return;
        log.info("{}==> PUSH : {}", id, msg);
        out.println(msg);
    }

    public void sendCommand(String cmd) {
        sendToClient(cmd);
    }

    public void sendCommand(String[] cmd) {
        sendToClient(String.join(" ", cmd));
    }

    public Object readFromClient(String cmd) {
        String uuid = UUID.randomUUID().toString();
        log.trace("ClientShellCommand.readFromClient: uuid={}, cmd={}", uuid, cmd);
        Object oldValue = inputsMap.remove(uuid);
        log.trace("ClientShellCommand.readFromClient: uuid={}, old-inputMap-value={}", uuid, oldValue);
        log.trace("ClientShellCommand.readFromClient: uuid={}, inputMap-BEFORE={}", uuid, inputsMap);
        sendCommand(cmd+" "+uuid);
        log.trace("ClientShellCommand.readFromClient: uuid={}, Command sent to client", uuid);
        while (!inputsMap.containsKey(uuid)) {
            log.trace("ClientShellCommand.readFromClient: uuid={}, No input, waiting 500ms", uuid);
            try { Thread.sleep(INPUT_CHECK_DELAY); } catch (InterruptedException e) { }
        }
        log.trace("ClientShellCommand.readFromClient: uuid={}, inputMap-BEFORE={}", uuid, inputsMap);
        Object input = inputsMap.remove(uuid);
        log.trace("ClientShellCommand.readFromClient: uuid={}, Input found: {}", uuid, input);
        return input;
    }

    protected String _propertiesToBase64(Properties params) {
        if (params != null && params.size() > 0) {
            StringWriter writer = new StringWriter();
            try {
                params.store(writer, null);
            } catch (IOException e) {
                log.error("Could not serialize parameters: ", e);
            }
            String paramsStr = writer.getBuffer().toString();
            return Base64.getEncoder().encodeToString(paramsStr.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    public void sendParams(Properties params) {
        log.debug("sendParams: id={}, parameters={}", id, params);
        String paramsStr = _propertiesToBase64(params);
        if (paramsStr != null) {
            sendToClient("SET-PARAMS " + paramsStr);
        }
    }

    /**
     * Write an object to a Base64 string.
     */
    protected String serializeToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Read the object from Base64 string.
     */
    protected Object unserializeFromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public void sendGroupingConfiguration(String grouping, Map<String, GroupingConfiguration.BrokerConnectionConfig> connectionConfigs, BaguetteServer server) {
        GroupingConfiguration gc = GroupingConfigurationHelper.newGroupingConfiguration(grouping, connectionConfigs, server);
        sendGroupingConfiguration(gc);
    }

    public void sendGroupingConfiguration(GroupingConfiguration gc) {
        String grouping = gc.getName();
        log.debug("sendGroupingConfiguration: id={}, grouping={}, grouping-config={}", id, grouping, gc);
        try {
            String allStr = serializeToString(gc);
            log.info("sendGroupingConfiguration: Serialization of Grouping configuration for {}: {}", grouping, allStr);
            sendToClient("SET-GROUPING-CONFIG " + allStr);
        } catch (IOException ex) {
            log.error("sendGroupingConfiguration: Exception while serializing Grouping configuration: ", ex);
            log.error("sendGroupingConfiguration: SET-GROUPING-CONFIG command *NOT* sent to client");
        }
    }

    public void sendConstants(Map<String, Double> constants) {
        log.debug("sendConstants: constants={}", constants);
        HashMap<String, Object> all = new HashMap<>();
        all.put("constants", constants);

        try {
            String allStr = serializeToString(all);
            log.info("sendConstants: Serialization of Constants: {}", allStr);
            sendToClient("SET-CONSTANTS " + allStr);
        } catch (IOException ex) {
            log.error("sendConstants: Exception while serializing Constants: ", ex);
            log.error("sendConstants: SET-CONSTANTS command *NOT* sent to client");
        }
    }

    public void setClientId(String id) {
        if (id != null && !id.trim().isEmpty())
            sendToClient("SET-ID " + id.trim());
    }

    public void setRole(String role) {
        if (role != null && !role.trim().isEmpty()) sendToClient("SET-ROLE " + role.trim().toUpperCase());
    }

    public void setActiveGrouping(String grouping) {
        if (grouping != null && !grouping.trim().isEmpty())
            sendToClient("SET-ACTIVE-GROUPING " + grouping.trim().toUpperCase());
    }

    public void stop(String msg) {
        log.info("{}==> STOP : {}", id, msg);
        out.println("EXIT " + msg);
        if (!callbackCalled.getAndSet(true)) {
            callback.onExit(1);
        }
    }

    public String toString() {
        return "ClientShellCommand_" + id;
    }

    public String toStringCluster() {
        return getClientClusterNodeAddress()+":"+getClientClusterNodePort();
    }
}
