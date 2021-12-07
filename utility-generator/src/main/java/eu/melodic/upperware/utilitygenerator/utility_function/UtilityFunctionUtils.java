/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.utility_function;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidateAttribute.createAttributeName;

@Slf4j
public class UtilityFunctionUtils {


    public static String createUtilityFunctionCostFormula(Collection<VariableDTO> variablesFromConstraintProblem) {
        log.warn("The Optimisation requirement is not defined in the Camel Model, default utility function which optimises the cost will be created.");
        Collection<String> componentCosts = new ArrayList<>();
        variablesFromConstraintProblem.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .forEach(v -> componentCosts.add(v.getId() + "*"
                        + createAttributeName(v.getComponentId(), CamelMetadata.PRICE)));

        return "1/(1+" + String.join("+", componentCosts) + ")";
    }

    public static boolean isInFormula(String formula, String name) {
        Expression expression = new Expression(formula);
        String[] arguments = expression.getMissingUserDefinedArguments();
        return Arrays.asList(arguments).contains(name);
    }

    static Collection<Constant> convertToConstants(Collection<Argument> arguments) {
        return arguments.stream().map(ConstantFactory::createConstant).collect(Collectors.toList());
    }

}
