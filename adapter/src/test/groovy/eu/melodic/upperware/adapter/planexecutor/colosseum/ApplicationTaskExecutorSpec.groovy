/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.planexecutor.colosseum

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext
import eu.melodic.upperware.adapter.plangenerator.model.Application
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationTask
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)

class ApplicationTaskExecutorSpec extends Specification {

  String name, oldName

  def application
  def applicationWithoutName

  def applicationEntity

  def api
  def applicationTask
  def collection
  def context
  def executor

  def setup() {
    name = "testName"
    oldName = "testOldName"

    application = Mock(Application)
    application.getName() >> name
    application.getOldName() >> oldName

    applicationWithoutName = Mock(Application)
    applicationWithoutName.getName() >> null

    applicationEntity = Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.Application)

    applicationTask = Mock(ApplicationTask)
    collection = Mock(Collection)
    api = Mock(ColosseumApi)
    context = Mock(ColosseumContext)

    executor = new ApplicationTaskExecutor(applicationTask, collection, api, context)
  }

  def "application create: correct"() {

    setup:
    context.getApplication(_) >> Optional.empty()
    api.createApplication(_) >> applicationEntity

    when:
    executor.create(application)

    then:
    noExceptionThrown()
    1 * context.addApplication(applicationEntity)
  }

  def "application create: application already exists - skip task"() {

    setup:
    context.getApplication(_) >> Optional.of(Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.Application))

    when:
    executor.create(application)

    then:
    0 * context.addApplication(_)

  }

  def "application create: null argument - exception"() {

    when:
    executor.create(null)

    then:
    thrown(NullPointerException)
    0 * context.getApplication(name).isPresent()

  }

  def "application create: null application fields - exception"() {

    when:
    executor.create(applicationWithoutName)

    then:
    1 * applicationWithoutName.getName()
    thrown(NullPointerException)
    0 * context.getApplication(_)
  }

  def "application update: correct"() {

    setup:
    context.getApplication(_) >> Optional.of(applicationEntity)

    when:
    executor.update(application)

    then:
    noExceptionThrown()
    1 * api.updateApplication(applicationEntity)

  }

  def "application update: application does not exist - exception"() {

    setup:
    context.getApplication(_) >> Optional.empty()

    when:
    executor.update(application)

    then:
    thrown(IllegalStateException)
    0 * api.updateApplication(_)
  }

  def "application update: null argument - exception"() {

    when:
    executor.update(null)

    then:
    thrown(NullPointerException)
    0 * context.getApplication(_)
  }

  def "application update: null application fields - exception"() {

    when:
    executor.update(applicationWithoutName)

    then:
    1 * applicationWithoutName.getName()
    thrown(NullPointerException)
    0 * applicationWithoutName.getOldName()
  }

  def "application delete: correct"() {

    setup:
    context.getApplication(_) >> Optional.of(applicationEntity)

    when:
    executor.delete(application)

    then:
    noExceptionThrown()
    1 * context.deleteApplication(applicationEntity)

  }

  def "application delete: application does not exist - exception"() {

    setup:
    context.getApplication(_) >> Optional.empty()

    when:
    executor.delete(application)

    then:
    noExceptionThrown()
    0 * context.deleteApplication(_)
  }

  def "application delete: null argument - exception"() {

    when:
    executor.delete(null)

    then:
    thrown(NullPointerException)
    0 * context.getApplication(_)
  }

  def "application delete: null application fields - exception"() {

    when:
    executor.delete(applicationWithoutName)

    then:
    1 * applicationWithoutName.getName()
    thrown(NullPointerException)
    0 * context.getApplication(_)
  }
}