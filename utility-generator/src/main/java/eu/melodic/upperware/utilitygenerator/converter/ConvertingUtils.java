/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import camel.metric.impl.MetricVariableImpl;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@Slf4j
public class ConvertingUtils {


    public static VariableType getVariableType(MetricVariableImpl metric){

        String annotation = metric.getMetricTemplate().getAttribute().getAnnotations().get(0).getId();
        log.info("Found annotation: " + annotation);
        VariableType resultType = Stream.of(VariableType.values())
                .filter(type -> annotation.contains(type.getName()))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("Wrong annotation - Melodic does not support that"));
        log.info("Found type: " + resultType);
        return resultType;

    }

    public static Collection<Argument> convertToArgument(Collection<Element> elements){

        Collection<Argument> arguments = new ArrayList<>();
        elements.forEach(element -> arguments.add(new Argument(element.getName(), (int)element.getValue())));
        return arguments;
    }


    public static Collection<Constant> convertToConstants(Collection<Element> elements){

        Collection<Constant> constants = new ArrayList<>();
        elements.forEach(element -> constants.add(new Constant(element.getName(), (int)element.getValue())));
        return constants;
    }
}
