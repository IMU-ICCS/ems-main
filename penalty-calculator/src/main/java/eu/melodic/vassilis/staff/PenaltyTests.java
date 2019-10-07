/*
 * Copyright (C) 2019 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.vassilis.staff;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

//import org.apache.commons.*;
//import eu.melodic.cloudiator.client.model.NodeCandidate;
//import eu.melodic.vassilis.staff.PenaltyFunction.ConfigurationElement;
//import org.apache.commons.*;

@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
public class PenaltyTests implements CommandLineRunner {
    @Autowired
    private PenaltyFunction penaltyCalculator;
    @Autowired
    private PenaltyFunctionProperties properties;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(PenaltyTests.class, args);
        log.info("APPLICATION FINISHED");
    }

    public void run(String... args) throws Exception {
        Collection<ConfigurationElement> collection_1 = readConfigElementsFromFile(args[0]);
        log.info("Collection-1:\n{}", PenaltyFunction.toString(collection_1));

        Collection<ConfigurationElement> collection_2 = readConfigElementsFromFile(args[0]);
        log.info("Collection-2:\n{}", PenaltyFunction.toString(collection_2));

        double penaltyValue = penaltyCalculator.evaluatePenaltyFunction(collection_1, collection_2);

        //normalized average startup time using max-min normalization
        log.info("Normalized Average Time of VM Startup Time : {}", penaltyValue);
    }

    protected Collection<ConfigurationElement> readConfigElementsFromFile(String fileName) throws IOException {
        try (Reader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<ArrayList<ConfigurationElement>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        }
    }
}