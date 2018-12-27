/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.utility_function;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.IntVariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.RealVariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import org.mariuszgromada.math.mxparser.Argument;

public class ArgumentFactory {

    public static Argument createArgument(VariableValueDTO variableValueDTO) {
        if (variableValueDTO instanceof IntVariableValueDTO) {
            return new Argument(variableValueDTO.getName(), ((IntVariableValueDTO) variableValueDTO).getValue());
        } else { //RealVariableValueDTO
            return new Argument(variableValueDTO.getName(), ((RealVariableValueDTO) variableValueDTO).getValue());
        }
    }
}
