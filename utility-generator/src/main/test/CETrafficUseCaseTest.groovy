/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

import io.github.cloudiator.rest.model.NodeCandidate
import eu.melodic.upperware.utilitygenerator.UtilityFunctionType
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.costfunction.*
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import eu.melodic.upperware.utilitygenerator.model.MetricDTO
import jersey.repackaged.com.google.common.collect.Lists
import spock.lang.Shared
import spock.lang.Specification


class CETrafficUseCaseTest extends Specification {

    CostUtilityFunction costUtilityFunction
    @Shared
    CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
    @Shared
    CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
    @Shared
    CostUtilityFunction costUtilityFunctionAbs = new CostUtilityFunctionWithAbsoluteCost(10)
    @Shared
    CostUtilityFunction costUtilityFunctionFraction = new CostUtilityFunctionFraction()


    List<MetricDTO> metrics
    int cardinality
    ConfigurationElement initialDeployment

    def setup() {

        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 2.0

        initialDeployment = new ConfigurationElement("Component", initNC, 3)
        metrics = Lists.newArrayList(Mock(MetricDTO))

    }

    def "less machines - one machine"() {
        cardinality = 1
        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true,
                        UtilityFunctionType.CETRAFFIC, costUtilityFunction)

        when:
        System.out.println("ONE MACHINE ")
        System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluateToTest(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "less machines"() {
        setup:
        cardinality = 2
        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true,
                        UtilityFunctionType.CETRAFFIC, costUtilityFunction)

        when:
        System.out.println("LESS MACHINES ")
        System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluateToTest(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }


    def "no changes"() {
        cardinality = 3
        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true,
                        UtilityFunctionType.CETRAFFIC, costUtilityFunction)

        when:
        System.out.println("MORE MACHINES ")
        System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluateToTest(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "more machines"() {
        cardinality = 4
        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true,
                        UtilityFunctionType.CETRAFFIC, costUtilityFunction)

        when:
        System.out.println("MORE MACHINES ")
        System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluateToTest(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "more machines - 6 machines"() {
        cardinality = 6
        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true,
                        UtilityFunctionType.CETRAFFIC, costUtilityFunction)

        when:
        System.out.println("MORE MACHINES ")
        System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluateToTest(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

}