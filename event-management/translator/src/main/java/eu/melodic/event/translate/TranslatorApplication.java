/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TranslatorApplication implements CommandLineRunner {
	
	private static boolean standalone = false;
	
	public static void main(String[] args) {
		standalone = true;
		SpringApplication.run(TranslatorApplication.class, args);
	}
	
	@Autowired
	private CamelToEplTranslator translator;

	@Override
	public void run(String... args) {
		if (!standalone) return;	// Execute only if called by 'main()'
		
		log.info("Testing CAMEL-to-EPL Translator");
		log.info("Args: {}", java.util.Arrays.asList(args));
		
		String camelModelId = (args.length>0 && !args[0].trim().isEmpty()) ? args[0].trim() : "/camel-new";
		log.info("Models to use...");
		log.info("  Camel-model: {}", camelModelId);
		translator.translate(camelModelId);
	}
}