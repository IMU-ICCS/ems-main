/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.upperware.utilitygenerator.facade.solver.CamundaFacadeImpl;
import eu.melodic.upperware.utilitygenerator.facade.solver.CamundaRestController;
import eu.melodic.upperware.utilitygenerator.facade.solver.SolverFacadeImpl;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"eu.melodic.cache", "eu.melodic.upperware.utilitygenerator"})
@EnableConfigurationProperties({MelodicSecurityProperties.class, CacheProperties.class})
@Slf4j
public class UtilityGeneratorApplication {

    private final CamundaFacadeImpl camundaFacade;
    private final CamundaRestController camundaRestController;
    private final SolverFacadeImpl solverFacade;

    @Autowired
    public UtilityGeneratorApplication(CamundaFacadeImpl camundaFacade, CamundaRestController camundaRestController, SolverFacadeImpl solverFacade) {
        log.info("Creating of the Utility Generator");
        this.camundaFacade = camundaFacade;
        this.camundaRestController = camundaRestController;
        this.solverFacade = solverFacade;
    }

    public static void main(String[] args) {
        SpringApplication.run(UtilityGeneratorApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            //setupCamundaFacadeTimer();
            setupSolverFacadeTimer();
        };
    }

    private void setupCamundaFacadeTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    camundaFacade.run();
                }
                catch(Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        };

        final long startupDelay = 2000;
        final long callInterval = 2000;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(timerTask, startupDelay, callInterval, TimeUnit.MILLISECONDS);
    }

    private void setupSolverFacadeTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    solverFacade.run();
                }
                catch(Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        };

        final long startupDelay = 4000;
        final long callInterval = 500;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(timerTask, startupDelay, callInterval, TimeUnit.MILLISECONDS);
    }

}
