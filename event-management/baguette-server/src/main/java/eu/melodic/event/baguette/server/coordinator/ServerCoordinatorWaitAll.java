/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.coordinator;

import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.baguette.server.ServerCoordinator;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;

//import java.util.Properties;

@Slf4j
public class ServerCoordinatorWaitAll implements ServerCoordinator {
    private BaguetteServer server;
    private Runnable callback;
    private int expectedClients;
    private int numClients;
    private int phase;
    private Vector<ClientShellCommand> clients;
    private ClientShellCommand broker;
    private int readyClients;

    public void initialize(BaguetteServer server, Runnable callback) {
        this.server = server;
        this.expectedClients = server.getConfiguration().getNumberOfInstances();
        this.callback = callback;
        this.clients = new Vector<>();
        log.info("initialize: Done");
    }

    public void start() {
    }

    public void stop() {
    }

    public int getPhase() {
        return phase;
    }

    public synchronized void register(ClientShellCommand c) {
        if (phase != 0) return;
        clients.add(c);
        numClients++;
        log.info("ServerCoordinatorWaitAll: {} of {} clients registered", numClients, expectedClients);
        if (numClients == expectedClients) {
            startPhase1();
        }
    }

    public synchronized void unregister(ClientShellCommand c) {
        if (phase != 0) return;
        clients.remove(c);
        numClients--;
    }

    protected synchronized void startPhase1() {
        if (phase != 0) return;
        log.info("ServerCoordinatorWaitAll: Phase #1");
        phase = 1;
        Thread runner = new Thread(new Runnable() {
            public void run() {
                // Pick a random client for Broker
                int sel = (int) Math.round((numClients - 1) * Math.random());
                if (sel >= numClients) sel = numClients - 1;
                broker = clients.get(sel);
                log.info("ServerCoordinatorWaitAll: Client #{} will become BROKER", broker.getId());

                // Signal BROKER to prepare
                phase = 2;
                broker.sendToClient("ROLE BROKER");
            }
        });
        runner.setDaemon(true);
        runner.start();
    }

    public synchronized void brokerReady(ClientShellCommand c) {
        if (phase != 2) return;
        log.info("ServerCoordinatorWaitAll: Broker is ready");
        phase = 3;
        readyClients = 1;
        if (readyClients == expectedClients) {
            phase = 4;
            signalTopologyReady();
        } else {
            Thread runner = new Thread(new Runnable() {
                public void run() {
                    // Signal all clients except broker to prepare
                    for (ClientShellCommand c : clients) {
                        if (c != broker) {
                            c.sendToClient("ROLE CLIENT");
                        }
                    }
                }
            });
            runner.setDaemon(true);
            runner.start();
        }
    }

    public synchronized void clientReady(ClientShellCommand c) {
        if (phase != 3) return;
        readyClients++;
        log.info("ServerCoordinatorWaitAll: {} of {} clients are ready", readyClients, expectedClients);
        if (readyClients == expectedClients) {
            phase = 4;
            signalTopologyReady();
        }
    }

    protected void signalTopologyReady() {
        if (phase != 4) return;
        log.info("ServerCoordinatorWaitAll: Invoking callback");
        phase = 5;
        Thread runner = new Thread(new Runnable() {
            public void run() {
                // Invoke callback
                callback.run();
                log.info("ServerCoordinatorWaitAll: FINISHED");
            }
        });
        runner.setDaemon(true);
        runner.start();
    }
}
