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

import java.util.Map;
import java.util.Random;

import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadGenerator;
import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadMap;

@Service
@ConditionalOnProperty(name = "run-broker-cep-test", havingValue = "BrokerCepServiceTest5", matchIfMissing = false)
@Slf4j
public class BrokerCepServiceTest5 implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BrokerCepService brokerCep;

    private CepService cepService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting Broker-CEP test...");

        // Register new event types (aka new queues or topics)
        String[] propertyNames = new String[]{"metricValue", "vmName", "cloudName", "componentName", "level", "timestamp"};
        Class[] propertyTypes = new Class[]{Double.class, String.class, String.class, String.class, Integer.class, Long.class};
        brokerCep.addEventType("CpuMetric", propertyNames, propertyTypes);
        brokerCep.addEventType("RamMetric", propertyNames, propertyTypes);
        brokerCep.addEventType("DiskMetric", propertyNames, propertyTypes);

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
                    return PayloadMap.create().put("metricValue", r.nextDouble() * 100).put("level", 1).put("timestamp", System.currentTimeMillis());
                }
            }, 100, 3000);

            BrokerCepFakeEventsProducer generator2 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
            generator2.sendEvents("RamMetric", new PayloadGenerator() {
                Random r = new Random();

                public Object payloadAsObject(int it) {
                    return PayloadMap.create().put("metricValue", r.nextInt(4096)).put("level", 1).put("timestamp", System.currentTimeMillis());
                }
            }, 500, 1000);

            BrokerCepFakeEventsProducer generator3 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
            generator3.sendEvents("DiskMetric", new PayloadGenerator() {
                Random r = new Random();

                public Object payloadAsObject(int it) {
                    return PayloadMap.create().put("metricValue", r.nextDouble() * 1024).put("level", 1).put("timestamp", System.currentTimeMillis());
                }
            }, 50, 5000);
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
                return "select * from CpuMetric.std:lastevent() as A, RamMetric.std:lastevent() as B, DiskMetric.std:lastevent() as C";
            }

            public void update(java.util.Map<String, Map<String, Object>> eventMap) {
                // latest values
                Map<String, Object> cpu = (Map<String, Object>) eventMap.get("A");
                Map<String, Object> ram = (Map<String, Object>) eventMap.get("B");
                Map<String, Object> disk = (Map<String, Object>) eventMap.get("C");
                //double cpuAvg = cpu!=null ? (Double)cpu.get("metricValue") : -1;
                //double ramAvg = ram!=null ? (Integer)ram.get("metricValue") : -1;
                //double diskAvg= disk!=null ? (Double)disk.get("metricValue") : -1;
                log.info("- [!! TEST !!] Latest Usage: CPU={}, RAM={}, Disk={}",
                        //cpuAvg, ramAvg, diskAvg);
                        cpu, ram, disk);
            }
        });

    }
}