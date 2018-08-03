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
import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.metamodel.types.*;

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

    public static Element createElement(CpVariableValue variableValue) {
        NumericValueUpperware value = variableValue.getValue();
        Element variable;
        if (value instanceof IntegerValueUpperware) {
            IntegerValueUpperware intVal = (IntegerValueUpperware) value;
            variable = new IntElement(variableValue.getVariable().getId(), intVal.getValue());
        } else if (value instanceof DoubleValueUpperware) {
            DoubleValueUpperware doubleVal = (DoubleValueUpperware) value;
            variable = new RealElement(variableValue.getVariable().getId(), doubleVal.getValue());
        } else if (value instanceof FloatValueUpperware) {
            FloatValueUpperware floatVal = (FloatValueUpperware) value;
            variable = new RealElement(variableValue.getVariable().getId(), (double) floatVal.getValue());
        } else { //Long
            LongValueUpperware longVal = (LongValueUpperware) value;
            variable = new IntElement(variableValue.getVariable().getId(), (int) longVal.getValue());
        }
        return variable;


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
