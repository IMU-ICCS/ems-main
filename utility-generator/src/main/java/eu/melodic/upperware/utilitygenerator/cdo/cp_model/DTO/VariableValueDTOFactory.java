/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO;

import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.metamodel.types.*;

public class VariableValueDTOFactory {


    public static VariableValueDTO createElement(CpVariableValue variableValue) {
        NumericValueUpperware value = variableValue.getValue();
        VariableValueDTO variable;
        if (value instanceof IntegerValueUpperware) {
            IntegerValueUpperware intVal = (IntegerValueUpperware) value;
            variable = new IntVariableValueDTO(variableValue.getVariable().getId(), intVal.getValue());
        } else if (value instanceof DoubleValueUpperware) {
            DoubleValueUpperware doubleVal = (DoubleValueUpperware) value;
            variable = new RealVariableValueDTO(variableValue.getVariable().getId(), doubleVal.getValue());
        } else if (value instanceof FloatValueUpperware) {
            FloatValueUpperware floatVal = (FloatValueUpperware) value;
            variable = new RealVariableValueDTO(variableValue.getVariable().getId(), (double) floatVal.getValue());
        } else { //Long
            LongValueUpperware longVal = (LongValueUpperware) value;
            variable = new IntVariableValueDTO(variableValue.getVariable().getId(), (int) longVal.getValue());
        }
        return variable;
    }

    public static VariableValueDTO createElement(String name, Number value) {
        if (value instanceof Integer) {
            return new IntVariableValueDTO(name, (int) value);
        } else { //Double
            return new RealVariableValueDTO(name, (double) value);
        }
    }

}
