/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.cep;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class CepExtensions {
    
	static ApplicationContext appContext;
	static CepService cepService;
	
	// Single-Row Functions methods
	
	@Autowired
	public CepExtensions(ApplicationContext ac) {
		appContext = ac;
		cepService = appContext.getBean(CepService.class);
		cepService.addSingleRowFunction("EVAL", CepEvalFunction.class.getName(), "eval");
		cepService.addSingleRowFunction("NEWEVENT", CepEvalFunction.class.getName(), "newEvent");
		cepService.addAggregatorFunction("EVALAGG", CepEvalAggregatorFactory.class.getName());
	}
}
