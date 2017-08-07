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

class ApplicationTaskExecutorTests extends Specification{

  String name, oldName

  def application
  def applicationWithoutName

  def applicationEntity

  def api
  def applicationTask
  def collection
  def context
  def executor

  def setup(){
    name = "testName"
    oldName = "testOldName"

    application = Stub(Application)
    application.getName() >> name
    application.getOldName() >> oldName

    applicationWithoutName = Stub(Application)
    applicationWithoutName.getName() >> null

    applicationEntity = Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.Application)

    applicationTask = Mock(ApplicationTask)
    collection = Mock(Collection)
    api = Mock(ColosseumApi)
    context = Mock(ColosseumContext)

    executor = new ApplicationTaskExecutor(applicationTask, collection, api, context)
  }

  def "correct creating of application"(){

    setup:
      context.getApplication(_) >> Optional.empty()
      api.createApplication(_) >> applicationEntity

    when:
      executor.create(application)

    then:
      1*context.addApplication(_)
  }

  def "creating of application when application already exists"(){

    setup:
      context.getApplication(_) >> Optional.of(Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.Application))

    when:
      executor.create(application)

    then:
      0*context.addApplication(_)

  }

  def "creating of application with null argument"(){

    when:
      executor.create(null)

    then:
      thrown(NullPointerException)
  }

  def "creating of application without application fields"(){

    when:
      executor.create(applicationWithoutName)

    then:
      thrown(NullPointerException)
  }

  def "correct updating of application" (){

    setup:
      context.getApplication(_) >> Optional.of(applicationEntity)

    when:
      executor.update(application)

    then:
      1*api.updateApplication(_)

  }

  def "updating of application when application does not exist"(){

    setup:
      context.getApplication(_) >> Optional.empty()

    when:
      executor.update(application)

    then:
      thrown(IllegalStateException)
  }

  def "updating of application with null argument"(){

    when:
      executor.update(null)

    then:
      thrown(NullPointerException)
  }

  def "updating of application without application fields"(){

    when:
      executor.update(applicationWithoutName)

    then:
      thrown(NullPointerException)
  }

  def "correct deleting of application"(){

    setup:
      context.getApplication(_) >> Optional.of(applicationEntity)

    when:
      executor.delete(application)

    then:
      1*context.deleteApplication(_)

  }

  def "deleting of application when application does not exist"(){

    setup:
      context.getApplication(_) >> Optional.empty()

    when:
      executor.delete(application)

    then:
      thrown(IllegalStateException)
  }

  def "deleting of application with null argument"(){

    when:
      executor.delete(null)

    then:
      thrown(NullPointerException)
  }

  def "deleting of application without application fields"(){

    when:
      executor.delete(applicationWithoutName)

    then:
      thrown(NullPointerException)
  }
}