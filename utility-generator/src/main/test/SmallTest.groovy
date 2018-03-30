/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import io.github.cloudiator.rest.model.NodeCandidate
import lombok.extern.slf4j.Slf4j
import spock.lang.Specification

@Slf4j
class SmallTest extends Specification {

    List<ConfigurationElement> actualConfiguration
    List<ConfigurationElement> newCheaperConfiguration
    List<ConfigurationElement> newSmallerConfiguration


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

    def "small test for check if not reconfigurable component are not changed"() {

        when:
        boolean first = UtilityFunctionEvaluator.checkIfNotReconfigurableComponentsAreNotChanged(actualConfiguration, newCheaperConfiguration)
        boolean same = UtilityFunctionEvaluator.checkIfNotReconfigurableComponentsAreNotChanged(actualConfiguration, actualConfiguration)
        boolean smaller_same = UtilityFunctionEvaluator.checkIfNotReconfigurableComponentsAreNotChanged(actualConfiguration, newSmallerConfiguration)

        then:
        noExceptionThrown()
        System.out.println("first = " + first)
        !first
        System.out.println("same = " + same)
        same
        smaller_same
        System.out.println("smaller_same = " + smaller_same)

    }


}