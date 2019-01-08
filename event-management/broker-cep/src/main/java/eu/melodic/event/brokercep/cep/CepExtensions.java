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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * This class registers a few Single-Row Functions as EPL extensions.
 * Registered function implementations reside in CepEvalFunction and CepEvalAggregator classes.
 * This class is instantiated automatically by Spring-Boot (no need for explicit instantiation)
 */
@Service
@Slf4j
public class CepExtensions {

    // Register Single-Row Functions methods

    @Autowired
    public CepExtensions(ApplicationContext appContext) {
        CepService cepService = appContext.getBean(CepService.class);
        cepService.addSingleRowFunction("EVAL", CepEvalFunction.class.getName(), "eval");
        cepService.addSingleRowFunction("NEWEVENT", CepEvalFunction.class.getName(), "newEvent");
        cepService.addAggregatorFunction("EVALAGG", CepEvalAggregatorFactory.class.getName());
    }
}
