/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control;

import eu.melodic.event.control.util.LogPrintStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication(
        scanBasePackages = {"eu.melodic.event.baguette.server", "eu.melodic.event.brokercep", "eu.melodic.event.control", "eu.melodic.event.translate"},
        exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class } )
@EnableAsync
@Configuration
@Slf4j
public class ControlServiceApplication {
    private static ConfigurableApplicationContext applicationContext;
    private static Timer exitTimer;

    public static void main(String[] args) {
        // Set standard system streams being logged
        System.setOut(new LogPrintStream(System.out, Level.INFO, "OUT"));
        System.setErr(new LogPrintStream(System.err, Level.ERROR, "ERR"));

        // Start EMS
        SpringApplication springApplication = new SpringApplication(ControlServiceApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("./ems.pid"));
        applicationContext = springApplication.run(args);
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