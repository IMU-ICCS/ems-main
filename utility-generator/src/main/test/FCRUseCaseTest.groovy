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
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware
import eu.paasage.upperware.metamodel.types.TypesFactory
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

//todo to update - handle metrics
@Ignore
class FCRUseCaseTest extends Specification {

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
    List<ConfigurationElement> listInit

    ConfigurationElement initialDeployment

    DoubleValueUpperware avgResponseTime


    def setup() {
        metrics = new ArrayList<>()
        avgResponseTime = TypesFactory.eINSTANCE.createDoubleValueUpperware()

        avgResponseTime.setValue(3.0 as double)
        metrics.add(new MetricDTO("METRIC_RT_AVG", avgResponseTime))

        String componentId = "componentId"

        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 2.0

        initialDeployment = new ConfigurationElement(componentId, initNC, 3)
        listInit = new ArrayList<>()
        listInit.add(initialDeployment)
    }

    def "avg response time=3, less machines"() {
        setup:

        int cardinality = 2

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.503253
        noExceptionThrown()
        System.out.println("utility = " + result)

        result >= 0.45 && result <= 0.55

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "avg response time=3, no changes"() {
        setup:
        avgResponseTime.getValue() >> 3.0
        metrics.add(new MetricDTO("METRIC_RT_AVG", avgResponseTime))

        int cardinality = 3

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.500068
        noExceptionThrown()
        System.out.println("utility = " + result)

        result >= 0.45 && result <= 0.55

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "avg response time=3, more machines"() {

        setup:
        avgResponseTime.getValue() >> 3.0
        metrics.add(new MetricDTO("METRIC_RT_AVG", avgResponseTime))

        int cardinality = 4

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "avg response time=25, less machines"() {
        setup:
        avgResponseTime.getValue() >> 25.0
        metrics.add(new MetricDTO("METRIC_RT_AVG", avgResponseTime))

        int cardinality = 2

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.5
        noExceptionThrown()
        System.out.println("utility = " + result)
        result >= 0.45 && result <= 0.55

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]

    }

    def "avg response time=25, no changes"() {
        setup:

        int cardinality = 3

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.837175
        noExceptionThrown()
        System.out.println("utility = " + result)
        result >= 0.77 && result <= 0.87

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }


    def "avg response time=25, more machines"() {
        setup:
        int cardinality = 4

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.677073
        noExceptionThrown()
        System.out.println("utility = " + result)
        result >= 0.62 && result <= 0.72

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }


    def "avg response time=28, less machines"() {
        setup:
        int cardinality = 2

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.5
        noExceptionThrown()
        System.out.println("utility = " + result)
        result >= 0.45 && result <= 0.55

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]

    }

    def "avg response time=28, no changes"() {
        setup:

        int cardinality = 3

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.567346
        noExceptionThrown()
        System.out.println("utility = " + result)
        result >= 0.51 && result <= 0.61

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

    def "avg response time=28, more machines"() {
        setup:

        int cardinality = 4

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, listInit, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        //System.out.println("CARDINALITY = " + cardinality)
        double result = utilityGenerator.evaluate(cardinality)

        then:
        //0.785278
        noExceptionThrown()
        System.out.println("utility = " + result)
        result >= 0.73 && result <= 0.83

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                                costUtilityFunctionAbs, costUtilityFunctionFraction]
    }

}