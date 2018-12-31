/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.segment;

import eu.melodic.event.baguette.server.ClientShellCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;

@Slf4j
public class BaseScenarioSegment extends AbstractSegment {
    // ------------------------------------------------------------------------
    // Static part

    // Segment FSM events
    private final static String EVENT_START = "START-EVENT";
    private final static String EVENT_NEW_REGISTRATION = "NEW-REGISTRATION-EVENT";
    private final static String EVENT_TIMEOUT = "TIMEOUT-EVENT";
    private final static String EVENT_EXPECTED_REGISTERED = "EXPECTED-REGISTERED-EVENT";
    private final static String EVENT_CLIENT_READY = "CLIENT-READY-EVENT";
    private final static String EVENT_SEGMENT_READY = "SEGMENT-READY-EVENT";

    // Broker selection methods
    public static enum SELECTION_METHOD {
        FIRST, RANDOM, BEST_FIT, UNDEFINED;
    }

    // ------------------------------------------------------------------------
    // Non-static part

    private Timer timer;
    private long timeoutPeriod;
    private boolean timeoutOccurred;
    private int expectedMembers;
    private SELECTION_METHOD brokerSelectionMethod = SELECTION_METHOD.UNDEFINED;
    private Function readyCallback;
    private Function changeCallback;

    private Properties brokerConfig;
    private Properties clientConfig;
    private boolean noBroker = false;
    private boolean segmentReady = false;

    // ------------------------------------------------------------------------
    // Public Segment API - Segment Life-cycle part

    public BaseScenarioSegment(String id) {
        super(id);
    }

    public BaseScenarioSegment(String id, long timeout, int howmany, SELECTION_METHOD sm) {
        super(id);
        setTimeoutPeriod(timeout);
        setExpectedMembers(howmany);
        setSelectionMethod(sm);
    }

    public long getTimeoutPeriod() {
        return timeoutPeriod;
    }

    public int getExpectedMembers() {
        return expectedMembers;
    }

    public void setTimeoutPeriod(long t) {
        checkNotStarted();
        timeoutPeriod = t;
    }

    public void setExpectedMembers(int n) {
        checkNotStarted();
        expectedMembers = n;
    }

    public SELECTION_METHOD getSelectionMethod() {
        return brokerSelectionMethod;
    }

    public void setSelectionMethod(SELECTION_METHOD sm) {
        checkNotStarted();
        brokerSelectionMethod = sm;
    }

    public Function getReadyCallback() {
        return readyCallback;
    }

    public void setReadyCallback(Function f) {
        readyCallback = f;
    }

    public Function getChangeCallback() {
        return changeCallback;
    }

    public void setChangeCallback(Function f) {
        changeCallback = f;
    }

    public Properties getBrokerConfig() {
        return brokerConfig;
    }

    public void setBrokerConfig(Properties p) {
        brokerConfig = p;
    }

    public Properties getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(Properties p) {
        clientConfig = p;
    }

    public boolean getNoBroker() {
        return noBroker;
    }

    public void setNoBroker(boolean b) {
        noBroker = b;
    }

    // ------------------------------------------------------------------------
    // Overide inherited LC methods - Segment Life-cycle part

    public synchronized void start() {
        checkNotStarted();
        if (!noBroker && brokerSelectionMethod == SELECTION_METHOD.UNDEFINED)
            throw new IllegalStateException("Broker selection method has not been set for segment " + id);
        if (!noBroker && (brokerConfig == null || brokerConfig.size() == 0))
            throw new IllegalStateException("Broker configuration has not been set or is invalid, for segment " + id);
        if (noBroker && (clientConfig == null || clientConfig.size() == 0))
            throw new IllegalStateException("Client configuration has not been set or is invalid, for segment " + id);
        super.start();
        startTimeout();
        doSendEvent(EVENT_START);
    }

    public synchronized void stop() {
        checkStarted();
        stopTimeout();
        super.stop();
    }

    // ------------------------------------------------------------------------
    // Public Segment API - ClientShellCommand Life-cycle part

    public synchronized void register(ClientShellCommand c) {
        checkStarted();
        sendEvent(EVENT_NEW_REGISTRATION, c);
    }

    public synchronized void unregister(ClientShellCommand c) {
        checkStarted();
        //XXX: TODO
        pending.remove(c);
        clients.remove(c);
        ready.remove(c);
        if (broker == c) broker = null;
    }

    public synchronized void brokerReady(ClientShellCommand broker) {
        checkStarted();
        log.debug("brokerReady: segment={}, broker={}", id, broker);
        if (this.broker != broker) {
            log.warn("brokerReady: Argument does not match current broker: arg={}, curr={}", broker, this.broker);
            return;
        }
        if (this.broker == null) {
            log.warn("brokerReady: No broker set: broker={}", this.broker);
            return;
        }
        sendEvent(EVENT_CLIENT_READY, broker);
    }

    public synchronized void clientReady(ClientShellCommand client) {
        checkStarted();
        log.debug("clientReady: segment={}, client={}", id, client);
        sendEvent(EVENT_CLIENT_READY, client);
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // Private Segment part

    public List<SegmentFSM.TransitionSpec> getTransitions() {
        List<SegmentFSM.TransitionSpec> specs = new LinkedList<>();
        // START
        specs.add(new SegmentFSM.TransitionSpec("START-STATE", "REGISTER-AS-PENDING-STATE", EVENT_START, this::doStart));

        // REGISTER PENDING
        specs.add(new SegmentFSM.TransitionSpec("REGISTER-AS-PENDING-STATE", "REGISTER-AS-PENDING-STATE", EVENT_NEW_REGISTRATION, this::registerPending));
        specs.add(new SegmentFSM.TransitionSpec("REGISTER-AS-PENDING-STATE", "WAITING-BROKER-STATE", EVENT_TIMEOUT, this::selectBroker));
        specs.add(new SegmentFSM.TransitionSpec("REGISTER-AS-PENDING-STATE", "WAITING-BROKER-STATE", EVENT_EXPECTED_REGISTERED, this::selectBroker));

        // WAITING BROKER
        specs.add(new SegmentFSM.TransitionSpec("WAITING-BROKER-STATE", "WAITING-BROKER-STATE", EVENT_NEW_REGISTRATION, this::registerPending));
        specs.add(new SegmentFSM.TransitionSpec("WAITING-BROKER-STATE", "REGISTER-AS-CLIENT-STATE", EVENT_CLIENT_READY, this::notifyPending));

        // REGISTER CLIENTS
        specs.add(new SegmentFSM.TransitionSpec("REGISTER-AS-CLIENT-STATE", "REGISTER-AS-CLIENT-STATE", EVENT_NEW_REGISTRATION, this::registerClient));
        specs.add(new SegmentFSM.TransitionSpec("REGISTER-AS-CLIENT-STATE", "REGISTER-AS-CLIENT-STATE", EVENT_CLIENT_READY, this::countReady));
        specs.add(new SegmentFSM.TransitionSpec("REGISTER-AS-CLIENT-STATE", "REGISTER-AS-CLIENT-STATE", EVENT_SEGMENT_READY, this::signalSegmentReady));

        return specs;
    }

    private synchronized void startTimeout() {
        if (timeoutPeriod > 0) {
            timer = new Timer(id + "-REGISTRATION-TIMER-" + hashCode(), true);
            timeoutOccurred = false;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeoutExpired();
                }
            }, timeoutPeriod);
            log.info("Segment timeout period started: {}", id);
        } else {
            log.info("Segment has no timeout period: {}", id);
        }
    }

    private synchronized void stopTimeout() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private synchronized void timeoutExpired() {
        stopTimeout();
        log.info("Segment timeout period expired: segment={}", id);
        timeoutOccurred = true;
        sendEvent(EVENT_TIMEOUT);
    }

    private synchronized void expectedMembersRegistered() {
        stopTimeout();
        log.info("Expected segment members registered: segment={}, num={}", id, getPending().size());
        sendEvent(EVENT_TIMEOUT);
    }

    // ------------------------------------------------------------------------
    // Private FSM part - Transition actions

    private ClientShellCommand getCSC(Object args, int index) {
        if (args == null) return null;
        if (args instanceof ClientShellCommand) return (ClientShellCommand) args;
        if (args instanceof Object[]) return (ClientShellCommand) ((Object[]) args)[index];
        throw new IllegalArgumentException("Argument is not ClientShellCommand or ClientShellCommand array");
    }

    private Object doStart(Object args) {
        log.debug("doStart: segment={}, args={}", id, args);
        return null;
    }

    private synchronized Object registerPending(Object args) {
        log.debug("registerPending: segment={}, args={}", id, args);
        ClientShellCommand csc = getCSC(args, 0);
        synchronized (pending) {
            pending.add(csc);
        }
        if (this.getPending().size() == expectedMembers) {
            sendEvent(EVENT_EXPECTED_REGISTERED);
        } else if (timeoutPeriod <= 0 && expectedMembers <= 0) {
            // if no registration period and no expected members have been specified,
            // then the first client to register will become the broker of this segment
            sendEvent(EVENT_EXPECTED_REGISTERED);
        }
        return null;
    }

    private synchronized Object selectBroker(Object args) {
        log.debug("selectBroker: segment={}, args={}", id, args);
        if (noBroker) {
            sendEvent(EVENT_CLIENT_READY);
        } else {
            doSelectBroker();
            signalSegmentChanged(broker);
        }
        return null;
    }

    private synchronized Object notifyPending(Object args) {
        log.debug("notifyPending: segment={}, args={}", id, args);

        // prepare client config using broker info
        if (!noBroker) {
            clientConfig = new Properties();
            clientConfig.setProperty("broker.ip-address", broker.getClientIpAddress());
            clientConfig.setProperty("broker.port", Integer.toString(broker.getClientPort()));
        }

        // move pending clients to clients
        synchronized (clients) {
            clients.clear();
            clients.addAll(pending);
            pending.clear();
        }

        // notify pending clients
        for (ClientShellCommand csc : getClients()) {
            notifyClient(csc);
        }
        signalSegmentChanged(broker);

        if (expectedMembers <= 1 && !noBroker || expectedMembers <= 0 && noBroker) {
            // if no expected members have been specified, of ONE expected member (i.e. the BROKER) has been specified,
            // then signal that segment is ready
            sendEvent(EVENT_SEGMENT_READY);
        }

        return null;
    }

    private synchronized Object registerClient(Object args) {
        log.debug("registerClient: segment={}, args={}", id, args);
        ClientShellCommand csc = getCSC(args, 0);
        synchronized (clients) {
            clients.add(csc);
        }
        notifyClient(csc);
        signalSegmentChanged(csc);
        return null;
    }

    private synchronized Object countReady(Object args) {
        log.debug("countReady: segment={}, args={}", id, args);
        ClientShellCommand csc = getCSC(args, 0);
        if (clients.contains(csc)) {
            ready.add(csc);
            int cntBroker = noBroker ? 0 : 1;
            signalSegmentChanged(csc);
            if (expectedMembers - cntBroker == ready.size()) {
                sendEvent(EVENT_SEGMENT_READY);
            }
        } else {
            log.warn("countReady: Argument is not a client: segment={}, arg={}\nSegment Clients: pending={}, client={}, ready={}", id, csc, pending, clients, ready);
        }
        return null;
    }

    private synchronized Object signalSegmentChanged(Object args) {
        log.debug("signalSegmentChanged: segment={}, args={}", id, args);
        if (changeCallback != null) {
            //XXX: TODO: improve this using a pooled executor
            Thread thread = new Thread(
                    new Runnable() {
                        Segment seg;

                        public void run() {
                            log.debug("signalSegmentReady: Executing segment-change-callback in a separate thread: segment={}", id);
                            Object o = changeCallback.apply(seg);
                            log.debug("signalSegmentReady: Segment-change-callback returned: segment={}, returned-value={}", id, o);
                        }

                        public Runnable setSegment(Segment s) {
                            seg = s;
                            return this;
                        }
                    }
                            .setSegment(this)
            );
            thread.setDaemon(true);
            thread.start();
        }
        return null;
    }

    private synchronized Object signalSegmentReady(Object args) {
        log.debug("signalSegmentReady: segment={}, args={}", id, args);
        if (segmentReady) return null;    // already notified that segment is ready

        log.debug("signalSegmentReady: Segment is ready: {}, broker={}, ready-clients={}", id, broker, ready.size());
        segmentReady = true;
        if (readyCallback != null) {
            //XXX: TODO: improve this using a pooled executor
            Thread thread = new Thread(
                    new Runnable() {
                        Segment seg;

                        public void run() {
                            log.debug("signalSegmentReady: Executing segment-ready-callback in a separate thread: segment={}", id);
                            Object o = readyCallback.apply(seg);
                            log.debug("signalSegmentReady: Segment-ready-callback returned: segment={}, returned-value={}", id, o);
                        }

                        public Runnable setSegment(Segment s) {
                            seg = s;
                            return this;
                        }
                    }
                            .setSegment(this)
            );
            thread.setDaemon(true);
            thread.start();
        }
        return null;
    }

    // ------------------------------------------------------------------------
    // Private Segment part - Internal operations

    private List<ClientShellCommand> pending = new LinkedList<>();
    private List<ClientShellCommand> clients = new LinkedList<>();
    private List<ClientShellCommand> ready = new LinkedList<>();
    private ClientShellCommand broker;

    private synchronized List<ClientShellCommand> getPending() {
        return pending;
    }

    public synchronized List<ClientShellCommand> getPendings() {
        return Collections.unmodifiableList(pending);
    }

    public synchronized List<ClientShellCommand> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public synchronized List<ClientShellCommand> getReadyClients() {
        return Collections.unmodifiableList(ready);
    }

    public synchronized int countPendings() {
        return pending.size();
    }

    public synchronized int countClients() {
        return clients.size();
    }

    public synchronized int countReadyClients() {
        return ready.size();
    }

    public synchronized ClientShellCommand getBroker() {
        return broker;
    }

    private synchronized void setBroker(ClientShellCommand csc) {
        if (!noBroker) broker = csc;
        else broker = null;
    }

    private void doSelectBroker() {
        stopTimeout();
        int num = this.getPending().size();
        log.debug("doSelectBroker: segment={}, pending-size={}", id, num);
        if (num > 0) {
            int index = 0;
            switch (brokerSelectionMethod) {
                case FIRST:
                    //index = 0;
                    break;
                case RANDOM:
                    index = (int) Math.round((num - 1) * Math.random());
                    log.debug("doSelectBroker: Random index={}", index);
                    break;
                case BEST_FIT:
                    //index = 0;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid broker selection method: " + brokerSelectionMethod);
            }
            setBroker(this.getPending().remove(index));
            log.debug("doSelectBroker: segment={}, broker={}", id, broker);
            notifyBroker();
        } else {
            log.warn("doSelectBroker: No pending ClientShellCommands to select a broker: segment={}", id);
        }
    }

    private void notifyBroker() {
        log.info("notifyBroker: segment={}, broker={}", id, broker);
        broker.sendParams(brokerConfig);
        broker.setRole("BROKER");
    }

    private void notifyClient(ClientShellCommand client) {
        log.info("notifyClient: segment={}, client={}", id, client);
        client.sendParams(clientConfig);
        client.setRole("CLIENT");
    }
}