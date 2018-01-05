/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum

import de.uniulm.omi.cloudiator.colosseum.client.entities.Application
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationInstance
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationInstanceTask
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE


@SpringBootTest(webEnvironment = NONE)

class ApplicationInstanceTaskExecutorSpec extends Specification {

  String name, appName
  Long id

  def appInstance
  def appInstanceWithoutName

  def appInstEntity
  def appEntity

  def api
  def applicationInstanceTask
  def collection
  def context
  def executor

  def setup() {
    name = "testName"
    appName = "testAppName"
    id = 12345L

    appInstance = Mock(ApplicationInstance)
    appInstance.getName() >> name
    appInstance.getAppName() >> appName

    appInstanceWithoutName = Mock(ApplicationInstance)
    appInstanceWithoutName.getName() >> null

    appInstEntity = Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance)
    appEntity = Mock(Application)

    applicationInstanceTask = Mock(ApplicationInstanceTask)
    collection = Mock(Collection)
    api = Mock(ColosseumApi)
    context = Mock(ColosseumContext)

    executor = new ApplicationInstanceTaskExecutor(applicationInstanceTask, collection, api, context)
  }

  def "application instance create: correct"() {

    setup:
    context.getApplicationInstance(_) >> Optional.empty()
    context.getApplication(_) >> Optional.of(appEntity)
    appEntity.getId() >> id

    api.createApplicationInstance(_) >> appInstEntity

    when:
    executor.create(appInstance)

    then:
    noExceptionThrown()
    1 * context.addApplicationInstance(appInstEntity)
  }

  def "application instance create: instance already exists - skip task"() {

    setup:
    context.getApplicationInstance(_) >> Optional.of(Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance))

    when:
    executor.create(appInstance)

    then:
    0 * context.addApplicationInstance(_)
  }

  def "application instance create: application does not exist - exception"() {

    setup:
    context.getApplicationInstance(_) >> Optional.empty()
    context.getApplication(_) >> Optional.empty()

    when:
    executor.create(appInstance)

    then:
    thrown(IllegalStateException)
  }

  def "application instance create: null argument - exception"() {

    when:
    executor.create(null)

    then:
    thrown(NullPointerException)
    0 * context.getApplicationInstance(appName).isPresent()
  }

  def "application instance create: null application fields - exception"() {

    when:
    executor.create(appInstanceWithoutName)

    then:
    1 * appInstanceWithoutName.getName()
    thrown(NullPointerException)
    0 * appInstanceWithoutName.getAppName()
  }


  def "application instance create: null appEntity id - exception"() {

    setup:
    context.getApplicationInstance(_) >> Optional.empty()
    context.getApplication(_) >> Optional.of(appEntity)
    appEntity.getId(_) >> null

    when:
    executor.create(appInstance)

    then:
    1 * appEntity.getId()
    thrown(NullPointerException)
    0 * api.createApplicationInstance(_)
  }


  def "application instance update: correct - exception"() {

    when:
    executor.update(appInstance)

    then:
    thrown(UnsupportedOperationException)

    when:
    executor.update(null)

    then:
    thrown(UnsupportedOperationException)

  }

  def "application instance delete: correct"() {

    setup:
    context.getApplicationInstance(_) >> Optional.of(appInstEntity)

    when:
    executor.delete(appInstance)

    then:
    noExceptionThrown()
    1 * context.deleteApplicationInstance(_)
  }

  def "application instance delete: instance does not exist - skip"() {

    setup:
    context.getApplicationInstance(_) >> Optional.empty()

    when:
    executor.delete(appInstance)

    then:
    noExceptionThrown()
    0 * context.deleteApplicationInstance(_)
  }

  def "application instance delete: null argument - exception"() {

    when:
    executor.delete(null)

    then:
    thrown(NullPointerException)
  }

  def "application instance delete: null application fields - exception"() {

    when:
    executor.delete(appInstanceWithoutName)

    then:
    1 * appInstanceWithoutName.getName()
    thrown(NullPointerException)
    0 * appInstanceWithoutName.getAppName()
  }
}