/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.segment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public abstract class AbstractSegment implements Segment {
    // ------------------------------------------------------------------------
    // Static part

    private static Map<String, Segment> segments = new HashMap<>();

    public static Set<String> getSegmentIds() {
        return segments.keySet();
    }

    public static Collection<Segment> getSegments() {
        return segments.values();
    }

    // ------------------------------------------------------------------------
    // Non-static part

    protected String id;
    protected SegmentFSM fsm;

    // ------------------------------------------------------------------------
    // Public Segment API - Segment Life-cycle part

    public AbstractSegment(String id) {
        synchronized (segments) {
            if (segments.containsKey(id)) throw new IllegalArgumentException("Segment id already in use: " + id);
            segments.put(id, this);
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return fsm.getState();
    }

    public void start() {
        checkNotStarted();
        initializeFsm();
        startEventDispatcher();
    }

    public void stop() {
        checkStarted();
        stopEventDispatcher();
        cleanup();
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // Private Segment part

    protected void checkStarted() {
        if (fsm == null) throw new IllegalStateException("Segment has not been started: " + id);
    }

    protected void checkNotStarted() {
        if (fsm != null) throw new IllegalStateException("Segment has already been started: " + id);
    }

    abstract List<SegmentFSM.TransitionSpec> getTransitions();

    protected void initializeFsm() {
        List<SegmentFSM.TransitionSpec> specs = getTransitions();
        this.fsm = new SegmentFSM(id + " Segment FSM", specs, "START-STATE");
    }

    private void cleanup() {
        fsm = null;
    }

    // ------------------------------------------------------------------------
    // Event dispatcher part

    @AllArgsConstructor
    private static class QueueEvent {
        String event;
        Object[] args;
    }

    private LinkedBlockingQueue<QueueEvent> eventQueue = new LinkedBlockingQueue<>();
    private Thread eventDispatcher;
    private boolean keepRunning;

    protected void sendEvent(String event, Object... args) {
        log.debug("sendEvent: Placed in Queue: event={}, args={}", event, args);
        //eventQueue.add(new QueueEvent(event, args));
        try {
            eventQueue.put(new QueueEvent(event, args));
        } catch (InterruptedException ex) {
            log.warn("sendEvent: Waiting to enqueue event interrupted");
        }
    }

    protected void doSendEvent(String event, Object... args) {
        try {
            log.debug("doSendEvent: Relaying event to FSM: event={}, args={}", event, args);
            fsm.onEvent(event, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void startEventDispatcher() {
        eventDispatcher = new Thread(
                new Runnable() {
                    public void run() {
                        keepRunning = true;
                        while (keepRunning || eventQueue.size() > 0) {
                            try {
                                //QueueEvent qe = eventQueue.poll();
                                QueueEvent qe = eventQueue.take();
                                if (qe != null) {
                                    log.debug("EventDispatcher: Dispatching event: event={}, args={}", qe.event, qe.args);
                                    doSendEvent(qe.event, qe.args);
                                } else {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException ex) {
                                        log.warn("EventDispatcher: Sleep interrupted: ", ex);
                                    }
                                }
                            } catch (InterruptedException ex) {
                                log.warn("EventDispatcher: Waiting for event interrupted: ", ex);
                            }
                        }
                    }
                }
        );
        eventDispatcher.setDaemon(true);
        eventDispatcher.start();
    }

    private void stopEventDispatcher() {
        keepRunning = false;
        try {
            eventDispatcher.interrupt();
        } catch (Exception ex) {
        }
        eventDispatcher = null;
    }
}