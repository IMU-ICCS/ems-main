/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control;

import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication(
        scanBasePackages = {"eu.melodic.event.baguette.server", "eu.melodic.event.baguette.client.install",
                "eu.melodic.event.brokercep", "eu.melodic.event.control", "eu.melodic.event.translate",
                "eu.melodic.event.util"},
        exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class } )
@EnableAsync
@Configuration
@Slf4j
public class ControlServiceApplication {
    private static ConfigurableApplicationContext applicationContext;
    private static Timer exitTimer;

    @Autowired
    private ControlServiceProperties properties;
    @Autowired
    private PasswordUtil passwordUtil;

    public static void main(String[] args) {
        // Start EMS server
        SpringApplication springApplication = new SpringApplication(ControlServiceApplication.class);
        springApplication.setBannerMode(Banner.Mode.LOG);
        springApplication.addListeners(new ApplicationPidFileWriter("./ems.pid"));
        applicationContext = springApplication.run(args);
    }

    @Bean
    public ServletWebServerFactory servletWebServerFactory() throws Exception {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            protected void customizeConnector(Connector connector) {
                if (this.getSsl() != null && this.getSsl().isEnabled()) {
                    try {
                        log.debug("TomcatServletWebServerFactory: ControlServiceProperties: {}", properties);
                        log.info("TomcatServletWebServerFactory: Initializing HTTPS keystore, truststore and certificate...");
                        KeystoreUtil.initializeKeystoresAndCertificate(properties.getSsl(), passwordUtil);
                        log.info("TomcatServletWebServerFactory: Initializing HTTPS keystore, truststore and certificate... done");
                    } catch (Exception e) {
                        log.error("TomcatServletWebServerFactory: EXCEPTION while initializing HTTPS keystore, truststore and certificate:\n", e);
                    }
                }
                super.customizeConnector(connector);
            }
        };
        return tomcat;
    }

    synchronized static void exitApp(int exitCode, long gracePeriod) {
        if (exitTimer==null) {
            // Close SpringBoot application
            log.info("ControlServiceApplication.exitApp(): Closing application context...");
            ExitCodeGenerator exitCodeGenerator = () -> exitCode;
            SpringApplication.exit(applicationContext, exitCodeGenerator);

            // Wait for 'gracePeriod' seconds before force JVM to exit
            log.info("ControlServiceApplication.exitApp(): Wait for {}sec before exit", gracePeriod);
            exitTimer = new Timer("exit-app-grace-period-timer", true);
            exitTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    log.info("ControlServiceApplication.exitApp(): Exiting...");
                    System.exit(exitCode);
                    log.info("ControlServiceApplication.exitApp(): Bye");
                }
            }, gracePeriod * 1000);
        } else {
            log.warn("ControlServiceApplication.exitApp(): Exit timer has already started: {}", exitTimer);
        }
    }
}