/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.utilitygenerator.model.function;

import eu.melodic.upperware.utilitygenerator.model.DTO.DoubleMetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.FloatMetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.IntMetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;

public class ElementFactory {

    public static Element createElement(MetricDTO metric) {
        if (metric instanceof IntMetricDTO) {
            return new IntElement(metric.getName(), ((IntMetricDTO) metric).getValue());
        } else if (metric instanceof DoubleMetricDTO) {
            return new RealElement(metric.getName(), ((DoubleMetricDTO) metric).getValue());
        } else { //Float
            return new RealElement(metric.getName(), (double) ((FloatMetricDTO) metric).getValue());
        }
    }

    public static Element createElement(VariableValue variableValue) {
        if (variableValue instanceof IntegerValueUpperware) {
            return new IntElement(variableValue.getVariable().getId(), ((IntegerValueUpperware) variableValue).getValue());
        } else { //DoubleValueUpperware
            return new RealElement(variableValue.getVariable().getId(), ((DoubleValueUpperware) variableValue).getValue());
        }
    }

    public static Element createElementWithNewName(String name, Element element) {
        if (element instanceof IntElement) {
            return new IntElement(name, ((IntElement) element).getValue());
        } else { //RealElement
            return new RealElement(name, ((RealElement) element).getValue());
        }
    }

    public static Element createElement(String name, Number value) {
        if (value instanceof Integer) {
            return new IntElement(name, (int) value);
        } else { //Double
            return new RealElement(name, (double) value);
        }
    }

}
