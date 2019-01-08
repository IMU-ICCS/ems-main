/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.Properties;

/**
 * Baguette client
 */
@SpringBootApplication(scanBasePackages = {"eu.melodic.event.baguette.client", "eu.melodic.event.brokercep", "eu.melodic.event.brokerclient"})
@Slf4j
public class BaguetteClient {
    public static void main(String[] args) throws IOException {
        // Load configuration
        log.trace("BaguetteClient: Starting");
        //XXX:TODO: Use SpringBoot to load baguette client properties
        Properties config = loadConfig("/opt/baguette-client/conf/baguette-client.properties");
        String idFile = null;
        if (args.length > 0 && !args[0].trim().isEmpty()) {
            config.putAll(loadConfig(args[0]));
            idFile = args[0];
        }
        log.debug("Boot-time config: {}", config);

        // Start Broker-CEP service
        log.debug("BaguetteClient: Starting the local Broker-CEP service...");
        ApplicationContext appCtx = SpringApplication.run(BaguetteClient.class, args);
        log.debug("BaguetteClient: Starting the local Broker-CEP service... ok");

        // Run SSH client
        boolean retry = true;
        while (true) {
            try {
                log.trace("BaguetteClient: spring-boot application-context: {}", appCtx);
                Sshc client = appCtx.getBean(Sshc.class);
                //Sshc client = new Sshc(config, idFile);
                client.setConfigAndId(config, idFile);
                log.trace("BaguetteClient: Sshc instance from application-context: {}", client);
                log.trace("BaguetteClient: Calling SSHC start()");
                client.start(retry);
                log.trace("BaguetteClient: Calling SSHC run()");
                client.run();
                log.trace("BaguetteClient: Calling SSHC stop()");
                client.stop();
            } catch (Exception ex) {
                log.error("BaguetteClient: EXCEPTION: {}", ex);
            }
            if (!retry) break;
            log.trace("BaguetteClient: Restarting client...");
        }
        log.trace("BaguetteClient: Exiting");
    }

    protected static Properties loadConfig(String configFile) throws IOException {
        Properties config = new Properties();
        try {
            try (InputStream in = new FileInputStream(new File(configFile))) {
                config.load(in);
            }
        } catch (FileNotFoundException ex) {
            try (InputStream in = BaguetteClient.class.getResourceAsStream(configFile)) {
                if (in == null) throw ex;
                config.load(in);
            }
        }
        return config;
    }
}
