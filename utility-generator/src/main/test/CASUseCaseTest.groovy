/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import eu.melodic.upperware.utilitygenerator.UtilityFunctionType
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import eu.melodic.upperware.utilitygenerator.model.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.MetricDTO
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class CASUseCaseTest extends Specification {


    CostUtilityFunction costUtilityFunction = new CostUtilityFunctionFraction()

    String METRIC_RAM_USAGE = "METRIC_RAMavg"


    List<ConfigurationElement> actualConfiguration
    List<ConfigurationElement> newMoreRamConfiguration
    List<ConfigurationElement> newBiggerConfiguration
    List<ConfigurationElement> newBiggestConfiguration



    List<MetricDTO> metrics
    int cardinality

    MetricDTO metric


    def setup() {

        String componentId = "componentId"

        Hardware mockHardware = GroovyMock(Hardware)
        Hardware mockBiggerHardware = GroovyMock(Hardware)

        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 2.0
        initNC.getHardware() >> mockHardware
        mockHardware.getRam() >> 2000

        NodeCandidate moreRamNC = GroovyMock(NodeCandidate)
        moreRamNC.getPrice() >> 2.0
        moreRamNC.getHardware() >> mockBiggerHardware
        mockBiggerHardware.getRam() >> 3000

        actualConfiguration = new ArrayList<>()
        actualConfiguration.add(new ConfigurationElement(componentId, initNC, 1))

        newMoreRamConfiguration = new ArrayList<>()
        newMoreRamConfiguration.add(new ConfigurationElement(componentId, moreRamNC, 1))

        newBiggerConfiguration = new ArrayList<>()
        newBiggerConfiguration.add(new ConfigurationElement(componentId, initNC, 2))

        newBiggestConfiguration = new ArrayList<>()
        newBiggestConfiguration.add(new ConfigurationElement(componentId, initNC, 3))

        metrics = new ArrayList<>()

        metric = Mock(IntMetricDTO)
        metric.getName() >> METRIC_RAM_USAGE
        metrics.add(metric)


    }
    //VAR_cardinality * VAR_ram * METRIC_maxRamUsage >= METRIC_currentRamUsage


    def "simple test for configuration"() {

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, false, UtilityFunctionType.CAS,
                        costUtilityFunction)

        when:
        double moreRam = utilityGenerator.evaluateToTest(newMoreRamConfiguration)
        double init = utilityGenerator.evaluateToTest(actualConfiguration)
        double bigger = utilityGenerator.evaluateToTest(newBiggerConfiguration)

        then:
        noExceptionThrown()
        init != 0
        moreRam != 0
        bigger != 0
        System.out.println("moreCores = " + moreRam + "\ninit = " + init + "\nbigger = " + bigger)

    }


    def "simple test add more ram"() {

        metric.getValue() >> 90

        cardinality = 1

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true, UtilityFunctionType.CAS,
                        costUtilityFunction)

        when:
        double moreRam = utilityGenerator.evaluateToTest(newMoreRamConfiguration)
        double init = utilityGenerator.evaluateToTest(actualConfiguration)
        double bigger = utilityGenerator.evaluateToTest(newBiggerConfiguration)

        then:
        noExceptionThrown()
        init == 0
        moreRam != 0
        bigger != 0
        bigger < moreRam
        System.out.println("moreCores = " + moreRam + "\ninit = " + init + "\nbigger = " + bigger)

    }

    def "simple test add one machine"() {

        metric.getValue() >> 90

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(metrics, actualConfiguration, true, UtilityFunctionType.CAS,
                        costUtilityFunction)

        when:
        double init = utilityGenerator.evaluateToTest(actualConfiguration)
        double bigger = utilityGenerator.evaluateToTest(newBiggerConfiguration)
        double biggest = utilityGenerator.evaluateToTest(newBiggestConfiguration)

        then:
        noExceptionThrown()
        init == 0
        biggest != 0
        bigger != 0
        biggest < bigger
        System.out.println("biggest = " + biggest + "\ninit = " + init + "\nbigger = " + bigger)

    }
}