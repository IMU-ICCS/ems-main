/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.CPModelHandler;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class UtilityGeneratorApplication {

    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(String camelModelFilePath, boolean readFromFile, CPModelHandler handler, UtilityGeneratorProperties properties) {
        log.info("Creating of the Utility Generator");
        utilityFunctionEvaluator = new UtilityFunctionEvaluator(camelModelFilePath, readFromFile, handler, properties);
    }

    public double evaluate(Collection<VariableValueDTO> solution) {
        return this.utilityFunctionEvaluator.evaluate(solution);
    }

}
