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
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExampleV2
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import eu.melodic.upperware.utilitygenerator.model.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.MetricDTO
import lombok.extern.slf4j.Slf4j
import spock.lang.Shared
import spock.lang.Specification

@Slf4j
class FCRUseCaseTest extends Specification {

    String AVG_RT = "METRIC_TR_AVG"

    CostUtilityFunction costUtilityFunction

    @Shared
    CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
    @Shared
    CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
    @Shared
    CostUtilityFunction costUtilityFunctionFraction = new CostUtilityFunctionFraction()

    List<MetricDTO> metrics
    List<ConfigurationElement> actualConfiguration
    List<ConfigurationElement> newBiggerConfiguration
    List<ConfigurationElement> newSmallerConfiguration

    List<ConfigurationElement> newCheaperConfiguration
    List<ConfigurationElement> newMoreExpensiveConfiguration
    List<ConfigurationElement> newCheaperBiggerConfiguration
    List<ConfigurationElement> newMoreExpensiveBiggerConfiguration

    MetricDTO metric

    def setup() {
        metrics = new ArrayList<>()

        metric = Mock(IntMetricDTO)
        metric.getName() >> AVG_RT
        metrics.add(metric)

        String componentId = "componentId"

        NodeCandidate cheapNC = GroovyMock(NodeCandidate)
        cheapNC.getPrice() >> 1.0

        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 4.0

        NodeCandidate expNC = GroovyMock(NodeCandidate)
        expNC.getPrice() >> 6.0

        actualConfiguration = new ArrayList<>()
        actualConfiguration.add(new ConfigurationElement(componentId, initNC, 3))

        newCheaperConfiguration = new ArrayList<>()
        newCheaperConfiguration.add(new ConfigurationElement(componentId, cheapNC, 3))

        newMoreExpensiveConfiguration = new ArrayList<>()
        newMoreExpensiveConfiguration.add(new ConfigurationElement(componentId, expNC, 3))

        newSmallerConfiguration = new ArrayList<>()
        newSmallerConfiguration.add(new ConfigurationElement(componentId, initNC, 1))

        newBiggerConfiguration = new ArrayList<>()
        newBiggerConfiguration.add(new ConfigurationElement(componentId, initNC, 8))

        newCheaperBiggerConfiguration = new ArrayList<>()
        newCheaperBiggerConfiguration.add(new ConfigurationElement(componentId, cheapNC, 5))

        newMoreExpensiveBiggerConfiguration = new ArrayList<>()
        newMoreExpensiveBiggerConfiguration.add(new ConfigurationElement(componentId, expNC, 5))
    }

    def "RT_AVG is good, configurations with different prices"(){

        setup:

        metric.getValue() >> 500

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        double cheap = utilityGenerator.evaluateToTest(newCheaperConfiguration)
        double exp = utilityGenerator.evaluateToTest(newMoreExpensiveConfiguration)
        double init = utilityGenerator.evaluateToTest(actualConfiguration)


        then:
        noExceptionThrown()
        cheap >= exp
        cheap >= init

        where:
        costUtilityFunction << [costUtilityFunction_1]
    }


    def "RT_AVG is good, configurations with different cardinalities"(){

        setup:

        metric.getValue() >> 500

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        double cheap = utilityGenerator.evaluateToTest(newCheaperBiggerConfiguration)
        double exp = utilityGenerator.evaluateToTest(newMoreExpensiveBiggerConfiguration)
        double init = utilityGenerator.evaluateToTest(actualConfiguration)


        then:
        noExceptionThrown()
        init > cheap
        init > exp

        where:
        costUtilityFunction << [costUtilityFunction_1]
    }


    def "RT_AVG is too high, configurations with different cardinalities and prices"(){

        setup:

        metric.getValue() >> 1500

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        double cheap = utilityGenerator.evaluateToTest(newCheaperBiggerConfiguration)
        double exp = utilityGenerator.evaluateToTest(newMoreExpensiveBiggerConfiguration)
        double init = utilityGenerator.evaluateToTest(actualConfiguration)


        then:
        noExceptionThrown()
        cheap >= exp
        cheap >= init

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionFraction]
    }

    def "RT_AVG is too high, configurations with different cardinalities"(){

        setup:

        metric.getValue() >> 1500

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true, UtilityFunctionType.FCR, costUtilityFunction)

        when:
        double smaller = utilityGenerator.evaluateToTest(newSmallerConfiguration)
        double init = utilityGenerator.evaluateToTest(actualConfiguration)
        double bigger = utilityGenerator.evaluateToTest(newBiggerConfiguration)

        then:
        noExceptionThrown()
        bigger > smaller
        bigger > init

        where:
        costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionFraction]

    }
}