/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client;

import eu.melodic.event.brokercep.BrokerCepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

/**
 * Baguette Client Command-Line Interface
 */
@Slf4j
@Service
public class BaguetteClientCLI {
    private Properties config;
    private String clientId;
    private String prompt = "CLI> ";

    @Autowired
    private CommandExecutor commandExecutor;
    @Autowired
    BrokerCepService brokerCepService;

    public void setConfigAndId(Properties config, String idFile) {
        this.config = config;
        this.clientId = config.getProperty("client.id", "");
        config.setProperty("exit-command.allowed", "true");
        log.trace("Sshc: cmd-exec: {}", commandExecutor);
        this.commandExecutor.setConfigAndId(config, idFile);
    }

    public void run() throws IOException {
        run(System.in, System.out, System.err);
    }

    public void run(InputStream in, PrintStream out, PrintStream err) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        /*String certOneLine = Optional
                .ofNullable(brokerCepService.getBrokerCertificate())
                .orElse("")
                .replace(" ","~~")
                .replace("\r\n","##")
                .replace("\n","$$");
        String clientAddress = config.getProperty("debug.fake-ip-address", "");
        int clientPort = -1;
        out.println(String.format("-HELLO FROM CLIENT: id=%s broker=%s address=%s port=%d cert=%s",
                clientId.replace(" ", "~~"),
                brokerCepService.getBrokerCepProperties().getBrokerUrlForClients(),
                clientAddress,
                clientPort,
                certOneLine));
        out.flush();*/

        out.print(prompt);
        out.flush();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            log.info(line);
            try {
                boolean exit = commandExecutor.execCmd(line.split("[ \t]+"), in, out, err);
                if (exit) break;
            } catch (Exception ex) {
                log.error("", ex);
                // Report exception back to server
                out.println(ex);
                ex.printStackTrace(out);
                out.flush();
            }
            out.print(prompt);
            out.flush();
        }
        /*out.println(String.format("-BYE FROM CLIENT: %s", clientId));*/
    }
}
