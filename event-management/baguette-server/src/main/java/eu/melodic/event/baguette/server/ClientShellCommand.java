/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.session.ServerSession;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ClientShellCommand implements Command, Runnable, SessionAware {

    private static Object LOCK = new Object();
    private static long counter;
    private static Set<ClientShellCommand> activeCmdList = new HashSet<>();

    public static Set<ClientShellCommand> getActive() {
        return Collections.unmodifiableSet(activeCmdList);
    }

    private InputStream in;
    private PrintStream out;
    private PrintStream err;
    private ExitCallback callback;
    private boolean callbackCalled;

    @Getter @Setter
    private String id;
    @Getter @Setter
    private boolean echoOn = false;

    private String clientId;
    private String clientIpAddress;
    private int clientPort = -1;
    @Getter
    private String clientCertificate;

    private ServerCoordinator coordinator;
    private boolean clientAddressOverrideAllowed;
    @Getter
    private ServerSession session;

    public ClientShellCommand(ServerCoordinator coordinator, boolean allowClientOverrideItsAddress) {
        synchronized (LOCK) {
            id = String.format("#%05d", counter++);
        }
        this.coordinator = coordinator;
        this.clientAddressOverrideAllowed = allowClientOverrideItsAddress;
    }

    public void setSession(ServerSession session) {
        log.info("{}--> Got session : {}", id, session);
        this.session = session;
		
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
        synchronized (activeCmdList) {
            activeCmdList.add(this);
        }

        try {
            log.info("{}==> Thread started", id);
            out.printf("CLIENT (%s) : START\n", id);

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
                } else if (line.equalsIgnoreCase("READY")) {
                    if (coordinator.getPhase() == 2) {
                        coordinator.brokerReady(this);
                    } else {
                        coordinator.clientReady(this);
                    }
                }
            }

            log.info("{}==> Signaling client to exit", id);
            out.println("EXIT");

        } catch (IOException ex) {
            log.warn("{}==> EXCEPTION : {}", id, ex);
            out.printf("EXCEPTION %s\n", ex);
        } finally {
            synchronized (activeCmdList) {
                activeCmdList.remove(this);
            }
            log.info("{}--> Thread stops", id);
            if (!callbackCalled) {
                callbackCalled = true;
                callback.onExit(0);
            }
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
            if (s.startsWith("address=")) {
                if (clientAddressOverrideAllowed) {
                    this.clientIpAddress = s.substring("address=".length());
                    log.info("{}--> Effective IP: {}", id, clientIpAddress);
                }
            } else
            if (s.startsWith("port=")) {
                if (clientAddressOverrideAllowed) {
                    try {
                        this.clientPort = Integer.parseInt(s.substring("port=".length()));
                        log.info("{}--> Effective Port: {}", id, clientPort);
                    } catch (Exception ex) {
                    }
                }
            } else
            if (s.startsWith("cert=")) {
                this.clientCertificate = s.substring("cert=".length())
                        .replace("~~", " ")
                        .replace("##", "\r\n")
                        .replace("$$", "\n");
                log.info("{}--> Client Cert.: {}", id, clientCertificate);
            } else {
                log.warn("{}--> Unknown HELLO argument will be ignored: {}", id, s);
            }
        }
    }

    public String getClientIpAddress() {
        if (clientIpAddress != null) return clientIpAddress;
        clientIpAddress = ((InetSocketAddress) getSession().getIoSession().getRemoteAddress()).getAddress().getHostAddress();
        return clientIpAddress;
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

    public void sendGroupingConfiguration(String grouping, Properties config, BaguetteServer server) {
        GroupingConfiguration gc = new GroupingConfiguration(grouping, config, server);
        sendGroupingConfiguration(grouping, gc);
    }

    public void sendGroupingConfiguration(String grouping, GroupingConfiguration gc) {
        log.debug("sendGroupingConfiguration: id={}, grouping={}, grouping-config={}", id, grouping, gc);
        if (grouping != null && !grouping.trim().isEmpty()) {
            HashMap<String, Object> all = new HashMap<>(gc.getConfigurationMap());
            log.debug("sendGroupingConfiguration: Grouping configuration for {}: {}", grouping, all);

            try {
                String allStr = serializeToString(all);
                log.info("sendGroupingConfiguration: Serialization of Grouping configuration for {}: {}", grouping, allStr);
                sendToClient("SET-GROUPING-CONFIG " + allStr);
            } catch (IOException ex) {
                log.error("sendGroupingConfiguration: Exception while serializing Grouping configuration: ", ex);
                log.error("sendGroupingConfiguration: SET-GROUPING-CONFIG command *NOT* sent to client");
            }
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
        if (id != null && !id.trim().isEmpty()) sendToClient("SET-ID " + id.trim());
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
        if (!callbackCalled) {
            callbackCalled = true;
            callback.onExit(1);
        }
    }

    public String toString() {
        return "ClientShellCommand_" + id;
    }
}
