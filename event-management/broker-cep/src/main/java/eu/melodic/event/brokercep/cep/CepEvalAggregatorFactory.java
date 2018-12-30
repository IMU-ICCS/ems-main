/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.cep;

import com.espertech.esper.client.hook.AggregationFunctionFactory;
import com.espertech.esper.epl.agg.aggregator.AggregationMethod;
import com.espertech.esper.epl.agg.service.common.AggregationValidationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CepEvalAggregatorFactory implements AggregationFunctionFactory {

    private String aggregatorFunctionName;

    public Class getValueType() {
        return Double.class;
    }

    public AggregationMethod newAggregator() {
        return new CepEvalAggregator();
    }

    public void setFunctionName(String functionName) {
        aggregatorFunctionName = functionName;
    }

    public void validate(AggregationValidationContext validationContext) {
		/*Class[] paramType = validationContext.getParameterTypes();
		if (!paramType[0].equals(String.class))
			throw new IllegalArgumentException("CepExtensions.validate(): Invalid argument #0 type in aggregator '"+aggregatorFunctionName+"'. Expected String but found: "+paramType[0].getName());
		for (int i=1; i<paramType.length; i++) {
			if (!paramType[i].equals(Double.class))
				throw new IllegalArgumentException("CepExtensions.validate(): Invalid argument #"+i+" type in aggregator '"+aggregatorFunctionName+"'. Expected Double but found: "+paramType[i].getName());
		}*/
    }
}
