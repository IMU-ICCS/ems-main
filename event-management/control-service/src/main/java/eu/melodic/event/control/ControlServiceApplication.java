/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages={"eu.melodic.event.baguette.server", "eu.melodic.event.brokercep", "eu.melodic.event.control", "eu.melodic.event.translate"})
@EnableAsync
@Configuration
@Slf4j
public class ControlServiceApplication {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(ControlServiceApplication.class, args);
		
//XXX:DEL: after development -or- replace with a coordinator call for preloading model
		ControlServiceApplication app = ctx.getBean(ControlServiceApplication.class);
		if (app.camelModel!=null && !app.camelModel.trim().isEmpty()) {
			app.camelModel = app.camelModel.trim();
			if (app.cpModel!=null) app.cpModel = app.cpModel.trim();
			log.warn("!!!!!!!  Calling Endpoint: /camelModelJson  with camel-model: {}  !!!!!!!", app.camelModel);
			ControlServiceController controller = ctx.getBean(ControlServiceController.class);
			if (app.cpModel==null || app.cpModel.trim().isEmpty()) {
				controller.newCamelModel(String.format("{ 'camel-model-id': '%s' }", app.camelModel));
			} else {
				controller.newCamelModel(String.format("{ 'camel-model-id': '%s', 'cp-model-id': '%s' }", app.camelModel, app.cpModel));
			}
			log.warn("!!!!!!!  Calling Endpoint: Done  !!!!!!!");
		}
	}
//XXX:DEL: after development -or- move to properties
	@org.springframework.beans.factory.annotation.Value("${control.test.camel-model}")
	private String camelModel;
	@org.springframework.beans.factory.annotation.Value("${control.test.cp-model}")
	private String cpModel;
}