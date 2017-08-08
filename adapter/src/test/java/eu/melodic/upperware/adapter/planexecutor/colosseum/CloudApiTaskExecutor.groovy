/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.adapter.planexecutor.colosseum

import de.uniulm.omi.cloudiator.colosseum.client.entities.Api
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext
import eu.melodic.upperware.adapter.plangenerator.model.CloudApi
import eu.melodic.upperware.adapter.plangenerator.tasks.CloudApiTask
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)

class CloudApiTaskExecutorTests extends Specification {

  String name
  String driver

  def cloudApi
  def cloudApiWithoutName
  def cloudApiWithoutDriver

  def api
  def cloudApiTask
  def collection
  def context
  def executor

  def setup() {
    name = "testName"
    driver = "testDriver"

    cloudApi = Stub(CloudApi)
    cloudApi.getDriver() >> driver
    cloudApi.getName() >> name

    cloudApiWithoutName = Stub(CloudApi)
    cloudApiWithoutName.getName() >> null

    cloudApiWithoutDriver = Stub(CloudApi)
    cloudApiWithoutDriver.getDriver() >> null



    cloudApiTask = Mock(CloudApiTask)
    collection = Mock(Collection)
    api = Mock(ColosseumApi)
    context = Mock(ColosseumContext)

    executor = new CloudApiTaskExecutor(cloudApiTask, collection, api, context)
  }

  def "cloud api create: correct"() {

    setup:
    context.getCloudApi(_) >> Optional.empty()

    when:
    executor.create(cloudApi)

    then:
    1 * context.addCloudApi(_)
  }

  def "cloud api create: api already exists - exception"() {

    setup:
    context.getCloudApi(_) >> Optional.of(Mock(Api))

    when:
    executor.create(cloudApi)

    then:
    0 * context.addCloudApi(_)
  }

  def "cloud api create: null argument - exception"() {

    when:
    executor.create(null)

    then:
    thrown(NullPointerException)
  }

  def "cloud api create: null api fields - exception"() {

    when:
    executor.create(cloudApiWithoutName)

    then:
    thrown(NullPointerException)

    when:
    executor.create(cloudApiWithoutDriver)

    then:
    thrown(NullPointerException)
  }


  def "cloud api update: correct"() {

    setup:
    context.getCloudApi(_) >> Optional.of(Mock(Api))

    when:
    executor.update(cloudApi)

    then:
    1 * api.updateApi(_)
  }

  def "cloud api update: api does not exist - exception"() {

    setup:
    context.getCloudApi(_) >> Optional.empty()

    when:
    executor.update(cloudApi)

    then:
    thrown(IllegalStateException)
  }

  def "cloud api update: null argument - exception"() {

    when:
    executor.update(null)

    then:
    thrown(NullPointerException)
  }

  def "cloud api update: null api fields - exception"() {

    when:
    executor.update(cloudApiWithoutDriver)

    then:
    thrown(NullPointerException)
  }

  def "cloud api delete: correct - exception"() {

    when:
    executor.delete(cloudApi)

    then:
    thrown(UnsupportedOperationException)

    when:
    executor.delete(null)

    then:
    thrown(UnsupportedOperationException)
  }

}


