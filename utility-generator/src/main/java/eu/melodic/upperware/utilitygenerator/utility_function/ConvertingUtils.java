/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.utility_function;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class ConvertingUtils {

    public static Collection<Argument> convertToArgument(Collection<VariableValueDTO> variableValueDTOS) {
        return variableValueDTOS.stream().map(ArgumentFactory::createArgument).collect(Collectors.toList());
    }

    public static Collection<Constant> convertToConstants(Collection<VariableValueDTO> variableValueDTOS) {
        return variableValueDTOS.stream().map(ConstantFactory::createConstant).collect(Collectors.toList());
    }

}
