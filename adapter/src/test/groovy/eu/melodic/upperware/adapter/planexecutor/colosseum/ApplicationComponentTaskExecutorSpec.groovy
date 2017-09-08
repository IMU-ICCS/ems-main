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
import de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud
import de.uniulm.omi.cloudiator.colosseum.client.entities.Hardware
import de.uniulm.omi.cloudiator.colosseum.client.entities.Image
import de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent
import de.uniulm.omi.cloudiator.colosseum.client.entities.Location
import de.uniulm.omi.cloudiator.colosseum.client.entities.VirtualMachineTemplate
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponent
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationComponentTask
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)

class ApplicationComponentTaskExecutorSpec extends Specification {

  String name, appName, lcName, vmName, cloudName, location, hardware, image
  Long id

  def appComponent
  def appComponentWithoutLocation
  def appComponentWithoutLcName
  def appComponentWithoutName

  def cloudEntity

  def locationEntity
  def hardwareEntity
  def imageEntity

  def appEntity
  def lcComponent
  def vmEntity

  def api
  def applicationComponentTask
  def collection
  def context
  def executor


  def setup() {

    name = "testName"
    appName = "testAppName"
    lcName = "testLcName"
    vmName = "testvmName"
    cloudName = "testCloudName"
    location = "testLocation"
    hardware = "testHardware"
    image = "testImage"
    id = 12345L

    appComponent = Stub(ApplicationComponent)
    fillFieldsInApplicationComponent ac: appComponent, name: name, appName: appName,
            lcName: lcName, vmName: vmName, cloudName: cloudName, location: location,
            hardware: hardware, image: image

    appComponentWithoutLocation = Stub(ApplicationComponent)
    fillFieldsInApplicationComponent ac: appComponentWithoutLocation, name: name, appName: appName,
            lcName: lcName, vmName: vmName, cloudName: cloudName, location: null,
            hardware: hardware, image: image

    appComponentWithoutName = Stub(ApplicationComponent)
    fillFieldsInApplicationComponent ac: appComponentWithoutName, name: null, appName: appName,
            lcName: lcName, vmName: vmName, cloudName: cloudName, location: location,
            hardware: hardware, image: image

    appComponentWithoutLcName = Stub(ApplicationComponent)
    fillFieldsInApplicationComponent ac: appComponentWithoutLcName, name: name, appName: appName,
            lcName: null, vmName: vmName, cloudName: cloudName, location: location,
            hardware: hardware, image: image


    locationEntity = Mock(Location)
    hardwareEntity = Mock(Hardware)
    imageEntity = Mock(Image)

    cloudEntity = Mock(Cloud)
    appEntity = Mock(Application)
    lcComponent = Mock(LifecycleComponent)
    vmEntity = Mock(VirtualMachineTemplate)

    applicationComponentTask = Mock(ApplicationComponentTask)
    collection = Mock(Collection)
    context = Mock(ColosseumContext)
    api = Mock(ColosseumApi)

    executor = new ApplicationComponentTaskExecutor(applicationComponentTask, collection, api, context)
  }


  def "application component create: correct"() {

    setup:
    setMockAppEntity(context: context, entity: appEntity, id:id)
    setMockLcComponent(context: context, entity: lcComponent, id:id)
    setMockVmEntity(context: context, entity: vmEntity, id:id)
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)

    setMockLocationEntity(entity: locationEntity, id: id)
    setMockHardwareEntity(entity: hardwareEntity, id: id)
    setMockImageEntity(entity: imageEntity, id: id)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: imageEntity)

    context.getApplicationComponent(_,_,_) >> Optional.empty()
    api.createApplicationComponent(_) >> Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationComponent)

    when:
    executor.create(appComponent)

    then:
    1 * context.addApplicationComponent(_)

  }

  def "application component create: component already exists - exception"(){

    setup:
    setMockAppEntity(context: context, entity: appEntity, id:id)
    setMockLcComponent(context: context, entity: lcComponent, id:id)
    setMockVmEntity(context: context, entity: vmEntity, id:id)
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)

    setMockLocationEntity(entity: locationEntity, id: id)
    setMockHardwareEntity(entity: hardwareEntity, id: id)
    setMockImageEntity(entity: imageEntity, id: id)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: imageEntity)

    context.getApplicationComponent(_,_,_) >> Optional.of(Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationComponent))

    when:
    executor.create(appComponent)

    then:
    0 * context.addApplicationComponent(_)
  }

  def "application component create: cloud is not configured - exception"() {

    setup:
    context.getCloud(_) >> Optional.empty()

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalStateException)
  }

  def "application component create: location does not exist - exception"(){

    setup:
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)
    setMockApi(api: api, location: null, hardware: hardwareEntity, image: imageEntity)

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalArgumentException)
  }

  def "application component create: hardware does not exist - exception"(){

    setup:
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)
    setMockApi(api: api, location: locationEntity, hardware: null, image: imageEntity)

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalArgumentException)
  }

  def "application component create: image does not exist - exception"(){

    setup:
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: null)

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalArgumentException)
  }

  def "application component create: application does not exist - exception"(){

    setup:
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)
    context.getApplication(_) >> Optional.empty()

    setMockLocationEntity(entity: locationEntity, id: id)
    setMockHardwareEntity(entity: hardwareEntity, id: id)
    setMockImageEntity(entity: imageEntity, id: id)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: imageEntity)

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalStateException)
  }
//FIXME
  def "application component create: lifecycle component does not exist - exception"(){

    setup:
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)
    setMockAppEntity(context: context, entity: appEntity, id:id)
    context.getLifecycleComponent(_) >> Optional.empty()


    setMockLocationEntity(entity: locationEntity, id: id)
    setMockHardwareEntity(entity: hardwareEntity, id: id)
    setMockImageEntity(entity: imageEntity, id: id)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: imageEntity)

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalStateException)
  }
//FIXME
  def "application component create: virtual machine does not exist - exception"(){

    setup:
    setMockCloudEntity(context: context, entity: cloudEntity, id:id)
    setMockAppEntity(context: context, entity: appEntity, id:id)
    setMockLcComponent(context: context, entity: lcComponent, id: id)
    context.getVirtualMachine(_, _, _, _) >> Optional.empty()

    setMockLocationEntity(entity: locationEntity, id: id)
    setMockHardwareEntity(entity: hardwareEntity, id: id)
    setMockImageEntity(entity: imageEntity, id: id)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: imageEntity)

    when:
    executor.create(appComponent)

    then:
    thrown(IllegalStateException)
  }

  def "application component create: null cloudEntity id - exception"() {

    setup:
    setMockCloudEntity(entity: cloudEntity, id:null)
    context.getCloud(_) >> Optional.of(cloudEntity)

    when:
    executor.create(appComponent)

    then:
    thrown(NullPointerException)
  }

  def "application component create: null imageEntity id - exception"(){

    setMockCloudEntity(entity: cloudEntity, id:id)
    setMockContext(context: context, cloudEntity: cloudEntity, appEntity: appEntity, lcComponent: lcComponent, vmEntity:vmEntity)

    setMockLocationEntity(entity: locationEntity, id: id)
    setMockHardwareEntity(entity: hardwareEntity, id: id)
    setMockImageEntity(entity: imageEntity, id: null)
    setMockApi(api: api, location: locationEntity, hardware: hardwareEntity, image: imageEntity)

    context.getApplicationComponent(_,_,_) >> Optional.empty()
    api.createApplicationComponent(_) >> Mock(de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationComponent)

  }

  def "application component create: null component fields - exception"() {

    when:
    executor.create(appComponentWithoutLocation)

    then:
    thrown(NullPointerException)

    when:
    executor.create(appComponentWithoutLcName)

    then:
    thrown(NullPointerException)

    when:
    executor.create(appComponentWithoutName)

    then:
    thrown(NullPointerException)

  }


  def "application component update: correct - exception"() {

    when:
    executor.update(appComponent)

    then:
    thrown(UnsupportedOperationException)

    when:
    executor.update(appComponentWithoutLocation)

    then:
    thrown(UnsupportedOperationException)

    when:
    executor.update(null)

    then:
    thrown(UnsupportedOperationException)
  }


  private fillFieldsInApplicationComponent(Map componentDetails) {
    componentDetails['ac'].getName() >> componentDetails['name']
    componentDetails['ac'].getAppName() >> componentDetails['appName']
    componentDetails['ac'].getLcName() >> componentDetails['lcName']
    componentDetails['ac'].getVmName() >> componentDetails['vmName']
    componentDetails['ac'].getCloudName() >> componentDetails['cloudName']
    componentDetails['ac'].getLocation() >> componentDetails['location']
    componentDetails['ac'].getHardware() >> componentDetails['hardware']
    componentDetails['ac'].getImage() >> componentDetails['image']

  }

  private setMockApi(Map details) {
    details['api'].getLocation(_, _) >> details['location']
    details['api'].getHardware(_, _) >> details['hardware']
    details['api'].getImage(_, _) >> details['image']
  }

  private setMockLocationEntity(Map details) {
    details['entity'].getId() >> details['id']
  }

  private setMockHardwareEntity(Map details) {
    details['entity'].getId() >> details['id']
  }

  private setMockImageEntity(Map details) {
    details['entity'].getId() >> details['id']
  }

  private setMockAppEntity(Map m){
    m['context'].getApplication(_) >> Optional.of(m['entity'])
    m['entity'].getId() >> m['id']
  }
  private setMockLcComponent(Map m){
    m['context'].getLifecycleComponent(_) >> Optional.of(m['entity'])
    m['entity'].getId() >> m['id']
  }
  private setMockVmEntity(Map m){
    m['context'].getVirtualMachine(_, _, _, _) >> Optional.of(m['entity'])
    m['entity'].getId() >> m['id']

  }

  private setMockCloudEntity(Map m){
    m['context'].getCloud(_) >> Optional.of(m['entity'])
    m['entity'].getId() >> m['id']
  }
}