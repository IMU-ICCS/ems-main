/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import eu.melodic.upperware.utilitygenerator.model.function.Element;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
@AllArgsConstructor
public class VariableConverter {


    public Collection<Element> convertVariablesForFunction(Collection<Element> solution, String formula){

        return solution.stream()
                .filter(element -> isInFormula(formula, element.getName()))
                .collect(Collectors.toList());
    }
}
