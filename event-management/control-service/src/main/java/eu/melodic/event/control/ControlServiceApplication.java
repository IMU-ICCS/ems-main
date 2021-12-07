/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.util.KeystoreUtil;
import eu.melodic.event.util.PasswordUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.InfoProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.StreamSupport;

@SpringBootApplication(
        scanBasePackages = {"eu.melodic.event.baguette.server", "eu.melodic.event.baguette.client.install",
                "eu.melodic.event.baguette.client.selfhealing", "eu.melodic.event.brokercep", "eu.melodic.event.control",
                "eu.melodic.event.translate", "eu.melodic.event.util"},
        exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class } )
@EnableAsync
@Configuration
@Slf4j
public class ControlServiceApplication implements ApplicationContextAware {
    private static ConfigurableApplicationContext applicationContext;
    private static Timer exitTimer;

    @Autowired
    private ControlServiceProperties properties;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private BuildProperties buildProperties;;

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
            // Wait for 'gracePeriod' seconds before forcing JVM to exit
            log.info("ControlServiceApplication.exitApp(): Wait for {}sec before exit", gracePeriod);
            exitTimer = new Timer("exit-timer", true);
            exitTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    log.info("ControlServiceApplication.exitApp(): exit-timer: Exiting with code: {}", exitCode);
                    System.exit(exitCode);
                    log.info("ControlServiceApplication.exitApp(): exit-timer: Bye");
                }
            }, gracePeriod * 1000);

            // Close SpringBoot application
            log.info("ControlServiceApplication.exitApp(): Closing application context...");
            ExitCodeGenerator exitCodeGenerator = () -> {
                log.info("ControlServiceApplication.exitApp(): exitCodeGenerator: Exit code: {}", exitCode);
                return exitCode;
            };
            SpringApplication.exit(applicationContext, exitCodeGenerator);
            log.info("ControlServiceApplication.exitApp(): Exiting with code: {}", exitCode);
            System.exit(exitCode);

        } else {
            log.warn("ControlServiceApplication.exitApp(): Exit timer has already started: {}", exitTimer);
        }
    }

    @Override
    @SneakyThrows
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (!properties.isPrintBuildInfo()) return;
        if (!log.isInfoEnabled()) return;

        // Print build info from 'BuildProperties'
        log.info("--------------------------------------------------------------------------------");
        log.info("Build Info:");
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(buildProperties.iterator(), Spliterator.ORDERED), false)
                .sorted(Comparator.comparing(InfoProperties.Entry::getKey))
                .forEach(e->log.info(" - {} = {}", e.getKey(), e.getValue()));
        log.info("--------------------------------------------------------------------------------");

        // Print info from bundled files
        printInfoFromFile(applicationContext, "Version Info", "classpath:/version.txt");
        log.info("--------------------------------------------------------------------------------");
        printInfoFromFile(applicationContext, "Git Info", "classpath:/git.properties");
        log.info("--------------------------------------------------------------------------------");
        printInfoFromFile(applicationContext, "Build Info", "classpath:/META-INF/build-info.properties");
        log.info("--------------------------------------------------------------------------------");
    }

    protected void printInfoFromFile(ApplicationContext applicationContext, String title, String resourceStr) throws IOException {
        Resource[] resources = applicationContext.getResources(resourceStr);
        if (resources.length>0) {
            Resource r = resources[0];
            log.info("** {} **\nFile: {}\nURL:  {}\n{}\n", title, r.getFilename(), r.getURL(),
                    StreamUtils.copyToString(r.getInputStream(), StandardCharsets.UTF_8));
        }
    }
}