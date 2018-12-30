/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.test;

import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.cep.CepService;
import eu.melodic.event.brokercep.cep.StatementSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Random;

import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadGenerator;

@Service
@ConditionalOnProperty(name = "run-broker-cep-test", havingValue = "BrokerCepServiceTest4", matchIfMissing = false)
@Slf4j
public class BrokerCepServiceTest4 implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BrokerCepService brokerCep;

    private CepService cepService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting Broker-CEP test...");

        // Register new event types (aka new queues or topics)
        brokerCep.addEventType("CpuMetric", VisorEvent.class);
        brokerCep.addEventType("RamMetric", VisorEvent.class);
        brokerCep.addEventType("DiskMetric", VisorEvent.class);

        // Register EPL statements in CEP engine
        cepService = brokerCep.getCepService();
        registerEplStatements();

        // Start fake event producers
        try {
            log.info("Waiting for 5 seconds...");
            java.util.concurrent.TimeUnit.SECONDS.sleep(5);

            BrokerCepFakeEventsProducer generator1 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
            generator1.sendEvents("CpuMetric", new PayloadGenerator() {
                Random r = new Random();

                public Object payloadAsObject(int it) {
                    return new VisorEvent(r.nextDouble() * 100, null, null, null, 1, System.currentTimeMillis());
                }
            }, 100, 5000);

            BrokerCepFakeEventsProducer generator2 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
            generator2.sendEvents("RamMetric", new PayloadGenerator() {
                Random r = new Random();

                public Object payloadAsObject(int it) {
                    return new VisorEvent(r.nextInt(4096), null, null, null, 1, System.currentTimeMillis());
                }
            }, 500, 1000);

            BrokerCepFakeEventsProducer generator3 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
            generator3.sendEvents("DiskMetric", new PayloadGenerator() {
                Random r = new Random();

                public Object payloadAsObject(int it) {
                    return new VisorEvent(r.nextDouble() * 1024, null, null, null, 1, System.currentTimeMillis());
                }
            }, 50, 10000);
        } catch (InterruptedException ex) {
        }

        log.info("Broker-CEP test started...");
    }

    protected void registerEplStatements() {
        log.info("Adding EPL statements & subscribers...");

        cepService.addStatementSubscriber(new StatementSubscriber() {
            public String getName() {
                return "AvgCpuSubscriber";
            }

            public String getStatement() {
                return "select avg(metricValue) as avg_val from CpuMetric.win:time_batch(5 sec)";
            }

            public void update(java.util.Map<String, Double> eventMap) {
                // average temperature over 10 secs
                Double avg = (Double) eventMap.get("avg_val");
                log.info("- [MONITOR] Average CPU usage = {}", avg);
            }
        });
        cepService.addStatementSubscriber(new StatementSubscriber() {
            public String getName() {
                return "AvgRamSubscriber";
            }

            public String getStatement() {
                return "select avg(metricValue) as avg_val from RamMetric.win:time_batch(5 sec)";
            }

            public void update(java.util.Map<String, Double> eventMap) {
                // average temperature over 10 secs
                Double avg = (Double) eventMap.get("avg_val");
                log.info("- [MONITOR] Average RAM usage = {}", avg);
            }
        });
        cepService.addStatementSubscriber(new StatementSubscriber() {
            public String getName() {
                return "AvgDiskSubscriber";
            }

            public String getStatement() {
                return "select avg(metricValue) as avg_val from DiskMetric.win:time_batch(5 sec)";
            }

            public void update(java.util.Map<String, Double> eventMap) {
                // average temperature over 10 secs
                Double avg = (Double) eventMap.get("avg_val");
                log.info("- [MONITOR] Average Disk usage = {}", avg);
            }
        });
        cepService.addStatementSubscriber(new StatementSubscriber() {
            public String getName() {
                return "LatestEventValuesSubscriber";
            }

            public String getStatement() {
                return "select * from CpuMetric.std:lastevent(), RamMetric.std:lastevent(), DiskMetric.std:lastevent()";
            }

            public void update(java.util.Map<String, VisorEvent> eventMap) {
                // latest values
                VisorEvent cpu = (VisorEvent) eventMap.get("CpuMetric");
                VisorEvent ram = (VisorEvent) eventMap.get("RamMetric");
                VisorEvent disk = (VisorEvent) eventMap.get("DiskMetric");
                log.info("- [!! TEST !!] Usage: CPU={}, RAM={}, Disk={}", cpu, ram, disk);
            }
        });

    }
}