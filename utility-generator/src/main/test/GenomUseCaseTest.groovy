/* * Copyright (C) 2018 7bulls.com
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

class GenomUseCaseTest extends Specification {

    CostUtilityFunction costUtilityFunction
    CostUtilityFunction costUtilityFunctionFraction = new CostUtilityFunctionFraction()

    String METRIC_MINIMUM_CORES = "METRIC_MinimumCores"

    List<ConfigurationElement> actualConfiguration
    List<ConfigurationElement> newMoreCoresConfiguration
    List<ConfigurationElement> newBiggerConfiguration
    List<ConfigurationElement> newMoreMasterCoresConfiguration

    List<MetricDTO> metrics
    int cardinality

    MetricDTO metric


    def setup() {

        String componentId = "componentWorkerId"
        String masterId = "masterId"

        Hardware mockHardware = GroovyMock(Hardware)
        Hardware mockBiggerHardware = GroovyMock(Hardware)

        NodeCandidate initNC = GroovyMock(NodeCandidate)
        initNC.getPrice() >> 2.0
        initNC.getHardware() >> mockHardware
        mockHardware.getCores() >> 1

        NodeCandidate moreCoresNC = GroovyMock(NodeCandidate)
        moreCoresNC.getPrice() >> 2.0
        moreCoresNC.getHardware() >> mockBiggerHardware
        mockBiggerHardware.getCores() >> 3

        actualConfiguration = new ArrayList<>()
        actualConfiguration.add(new ConfigurationElement(componentId, initNC, 1))
        actualConfiguration.add(new ConfigurationElement(masterId, initNC, 1))

        newMoreCoresConfiguration = new ArrayList<>()
        newMoreCoresConfiguration.add(new ConfigurationElement(componentId, moreCoresNC, 2))
        newMoreCoresConfiguration.add(new ConfigurationElement(masterId, moreCoresNC, 1))

        newBiggerConfiguration = new ArrayList<>()
        newBiggerConfiguration.add(new ConfigurationElement(componentId, initNC, 3))
        newBiggerConfiguration.add(new ConfigurationElement(masterId, initNC, 1))

        newMoreMasterCoresConfiguration = new ArrayList<>()
        newMoreMasterCoresConfiguration.add(new ConfigurationElement(componentId, moreCoresNC, 2))
        newMoreMasterCoresConfiguration.add(new ConfigurationElement(masterId, moreCoresNC, 2))


        metrics = new ArrayList<>()

        metric = Mock(IntMetricDTO)
        metric.getName() >> METRIC_MINIMUM_CORES
        metrics.add(metric)

    }


    def "less machines - one machine"() {

        metric.getValue() >> 3

        cardinality = 1
        costUtilityFunction = costUtilityFunctionFraction

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(metrics, actualConfiguration,
                true, UtilityFunctionType.GENOM, costUtilityFunction)

        when:
        double init = utilityGenerator.evaluateToTest(actualConfiguration)
        double bigger = utilityGenerator.evaluateToTest(newBiggerConfiguration)
        double moreCores = utilityGenerator.evaluateToTest(newMoreCoresConfiguration)
        double moreMasterCores = utilityGenerator.evaluateToTest(newMoreMasterCoresConfiguration)

        then:
        noExceptionThrown()
        init == 0
        moreCores != 0
        bigger != 0
        bigger < moreCores
        moreCores > moreMasterCores
        moreMasterCores != 0
        System.out.println("moreCores = " + moreCores + "\nmore Master cores = " + moreMasterCores + "\ninit = " + init + "\nbigger = " + bigger)

    }
}
