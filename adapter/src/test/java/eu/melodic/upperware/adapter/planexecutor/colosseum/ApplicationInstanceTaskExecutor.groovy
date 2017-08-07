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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;


@SpringBootTest(webEnvironment = NONE)

class ApplicationInstanceTaskExecutorTests extends Specification{

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

  def setup(){
    name = "testName"
    appName = "testAppName"
    id = 12345L

    appInstance = Stub(ApplicationInstance)
    appInstance.getName() >> name
    appInstance.getAppName() >> appName

    appInstanceWithoutName = Stub(ApplicationInstance)
    appInstanceWithoutName.getName() >> null

    appInstEntity = Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance)
    appEntity = Mock(Application)
    appEntity.getId() >> id

    applicationInstanceTask = Mock(ApplicationInstanceTask)
    collection = Mock(Collection)
    api = Mock(ColosseumApi)
    context = Mock(ColosseumContext)

    executor = new ApplicationInstanceTaskExecutor(applicationInstanceTask, collection, api, context)
  }

  def "creating"(){

    setup:
      context.getApplication(_) >> Optional.of(appEntity)
      api.createApplicationInstance(_) >> appInstEntity

    when:
      executor.create(appInstance)

    then:
      1*context.addApplicationInstance(_)
  }

  def "creating with null"(){

    when:
      executor.create(null)

    then:
      thrown(NullPointerException)
  }

  def "creating without application"(){

    setup:
      context.getApplication(_) >> Optional.empty()

    when:
      executor.create(appInstance)

    then:
      thrown(IllegalStateException)
  }

  def "creating without application fields"(){

    when:
      executor.create(appInstanceWithoutName)

    then:
      thrown(NullPointerException)
  }

  def "creating without appEntity id"(){

    setup:
      context.getApplication(_) >> Optional.of(appEntity)
      appEntity.getId(_) >> null

    when:
      executor.create(appInstance)

    then:
      thrown(NullPointerException)
  }


  def "updating"(){

    when:
      executor.update(appInstance)

    then:
      thrown(UnsupportedOperationException)

    when:
      executor.update(null)

    then:
      thrown(UnsupportedOperationException)

  }

  def "deleting"(){
    setup:
      context.getApplicationInstance(_) >> Optional.of(appInstEntity)

    when:
      executor.delete(appInstance)

    then:
      1*context.deleteApplicationInstance(_)
  }

  def "deleting without application instance"(){
    setup:
      context.getApplicationInstance(_) >> Optional.empty()

    when:
      executor.delete(appInstance)

    then:
      thrown(IllegalStateException)
  }

  def "deleting with null"(){
    when:
      executor.delete(appInstanceWithoutName)

    then:
      thrown(NullPointerException)

    when:
      executor.delete(null)

    then:
      thrown(NullPointerException)

  }
}