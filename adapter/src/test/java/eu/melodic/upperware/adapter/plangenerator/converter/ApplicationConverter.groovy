/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.plangenerator.converter


import eu.melodic.upperware.adapter.plangenerator.model.Application
import eu.paasage.camel.deployment.DeploymentModel
import eu.paasage.camel.organisation.OrganisationModel
import org.junit.Rule
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import org.powermock.modules.junit4.rule.PowerMockRule

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE


@PrepareForTest([ConverterUtils.class])
@SpringBootTest(webEnvironment = NONE)
class ApplicationConverterTests extends Specification {

  //@Rule PowerMockRule powerMockRule = new PowerMockRule();

  def "converting application with owner organisation" (){

    setup:
      def name = "testName"
      def version = "testVersion"
      def desc = "testDescription"
      def owner = "testOwner"

      def converter = new ApplicationConverter()

      def model = Mock(DeploymentModel)
      def camelApplication = Mock(eu.paasage.camel.Application)
      def entityOrganisation = Mock(OrganisationModel)

      PowerMockito.mockStatic(ConverterUtils.class)

      //GroovyMock(ConverterUtils, global: true)
      //ConverterUtils.extractApplication(_) >> camelApplication

      camelApplication.getName() >> name
      camelApplication.getDescription() >> desc
      camelApplication.getVersion() >> version
      camelApplication.getOwner() >> entityOrganisation

      entityOrganisation.getName() >> owner

      PowerMockito.when(ConverterUtils.extractApplication(_)).thenReturn(camelApplication)

    when:
      Application app = converter.toComparableModel(model)

    then:
      app.equals(Application.builder().name(name).version(version).description(desc).owner(owner).build())

    }
}