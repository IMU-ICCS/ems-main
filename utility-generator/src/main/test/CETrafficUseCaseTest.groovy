/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import eu.melodic.upperware.utilitygenerator.UtilityFunctionType
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.costfunction.*
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import eu.melodic.upperware.utilitygenerator.model.DoubleMetricDTO
import eu.melodic.upperware.utilitygenerator.model.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.MetricDTO
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.NodeCandidate
import org.junit.Ignore
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

    String SIMULATIONS_LEFT = "METRIC_SimulationLeftNumber"
    String  ET_PERCENTILE = "METRIC_ETPercentile"
    String REMAINING_SIMULATION_TIME = "METRIC_RemainingSimulationTimeMetric"


    List<ConfigurationElement> actualConfiguration
    List<ConfigurationElement> newCheaperConfiguration


    List<MetricDTO> metrics
    int cardinality
    ConfigurationElement initialDeployment

    MetricDTO metric
    MetricDTO metricPercentile
    MetricDTO metricRemainingSimTime


    def setup() {

        String componentId = "componentId"


        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 2.0
        Hardware mockHardware = GroovyMock(Hardware)

        initNC.getHardware() >> mockHardware
        mockHardware.getCores() >> 1

        NodeCandidate cheapNC = GroovyMock(NodeCandidate)
        cheapNC.getPrice() >> 1.0
        cheapNC.getHardware() >> mockHardware

        actualConfiguration = new ArrayList<>()
        actualConfiguration.add(new ConfigurationElement(componentId, initNC, 1))

        newCheaperConfiguration = new ArrayList<>()
        newCheaperConfiguration.add(new ConfigurationElement(componentId, cheapNC, 2))

        metrics = new ArrayList<>()

        metric = Mock(IntMetricDTO)
        metric.getName() >> SIMULATIONS_LEFT
        metrics.add(metric)

        metricPercentile = Mock(DoubleMetricDTO)
        metricPercentile.getName() >> ET_PERCENTILE
        metrics.add(metricPercentile)

        metricRemainingSimTime = Mock(IntMetricDTO)
        metricRemainingSimTime.getName() >> REMAINING_SIMULATION_TIME
        metrics.add(metricRemainingSimTime)


    }

    def "less machines - one machine"() {

        metric.getValue() >> 2
        metricPercentile.getValue() >> 2.3
        metricRemainingSimTime.getValue() >> 3

        cardinality = 1
        costUtilityFunction = costUtilityFunctionFraction

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true,
                        UtilityFunctionType.CETRAFFIC, costUtilityFunction)

        when:
        double result = utilityGenerator.evaluateToTest(newCheaperConfiguration)

        then:
        noExceptionThrown()
        System.out.println("utility = " + result)

//        where:
//        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
//                                costUtilityFunctionAbs, costUtilityFunctionFraction]


    }

    @Ignore
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

    @Ignore
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
    @Ignore
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

    @Ignore
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