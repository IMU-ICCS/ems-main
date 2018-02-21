/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import eu.melodic.cloudiator.client.model.NodeCandidate
import eu.melodic.upperware.utilitygenerator.UtilityFunctionType
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import spock.lang.Ignore
import spock.lang.Specification

//todo to update
@Ignore
class CASUseCaseTest extends Specification {

    CostUtilityFunction costUtilityFunction
    List<ConfigurationElement> initDeployment = new ArrayList<>()

    ConfigurationElement initialDeployment

    def setup() {

        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 2.0

        initialDeployment = new ConfigurationElement("Component", initNC, 3)
        initDeployment.add(initialDeployment)
        costUtilityFunction = new CostUtilityFunctionFraction()

    }
    //VAR_cardinality * VAR_ram * METRIC_maxRamUsage >= METRIC_currentRamUsage


    def "simple test"() {

        setup:

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(null, initDeployment, true, UtilityFunctionType.CAS,
                        costUtilityFunction)

        when:
        System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluateToTest(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        cardinality << [1, 2, 3, 4, 5]

    }
}