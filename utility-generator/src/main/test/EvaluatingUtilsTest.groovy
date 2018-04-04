/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import io.github.cloudiator.rest.model.NodeCandidate
import lombok.extern.slf4j.Slf4j
import spock.lang.Specification

@Slf4j
class EvaluatingUtilsTest extends Specification {

    List<ConfigurationElement> actualConfiguration
    List<ConfigurationElement> newCheaperConfiguration
    List<ConfigurationElement> newSmallerConfiguration

    String notReconfigurableComponentSuffix = "CTITRRR"


    def setup() {

        String componentId = "componentId_CTITRRR"

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

        newSmallerConfiguration = new ArrayList<>()
        newSmallerConfiguration.add(new ConfigurationElement(componentId, initNC, 1))
    }

    def "small test for check if not reconfigurable component are changed"() {

        when:
        boolean first = EvaluatingUtils.checkIfNotReconfigurableComponentsAreChanged(notReconfigurableComponentSuffix, actualConfiguration, newCheaperConfiguration)
        boolean same = EvaluatingUtils.checkIfNotReconfigurableComponentsAreChanged(notReconfigurableComponentSuffix, actualConfiguration, actualConfiguration)
        boolean smaller_same = EvaluatingUtils.checkIfNotReconfigurableComponentsAreChanged(notReconfigurableComponentSuffix, actualConfiguration, newSmallerConfiguration)

        then:
        noExceptionThrown()
        System.out.println("different returns " + first)
        first
        System.out.println("the same returns " + same)
        !same
        !smaller_same
        System.out.println("smaller_same returns " + smaller_same)

    }


}