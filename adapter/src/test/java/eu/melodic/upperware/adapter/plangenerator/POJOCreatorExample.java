package eu.melodic.upperware.adapter.plangenerator;

import com.google.common.collect.Maps;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class POJOCreatorExample {

  CloudApi createApi(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {

    CloudApi t = CloudApi.builder()
            .name("EC2Api")
            .driver("aws-ec2")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new CloudApiTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD_API, set);
    return t;
  }

  Cloud createCloud(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Cloud t = Cloud.builder()
            .name("EC2")
            .apiName("EC2Api")
            .endpoint("https://ec2.eu-west-1.amazonaws.com")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new CloudTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD, set);
    return t;
  }

  CloudProperty createCloudProperty(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Map filters = Maps.newHashMap();
    filters.put("sword.ec2.ami.query", "image-id=ami-b9b394ca");
    filters.put("sword.ec2.ami.cc.query", "image-id=ami-b9b394ca");

    CloudProperty t = CloudProperty.builder()
            .name("EC2Property")
            .cloudName("EC2")
            .filters(filters)
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new CloudPropertyTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD_PROPERTY, set);
    return t;
  }

  CloudCredential createCloudCredential(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    CloudCredential t = CloudCredential.builder()
            .name("EC2Credential")
            .cloudName("EC2")
            .login("AKIAIUVVELYXJVJMKR5A")
            .password("daISW/6LNS5lBTrqkFL5H5ROpzoYDSvLGuhKKuZ0")
            .tenant(1L)
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new CloudCredentialTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD_CREDENTIAL, set);
    return t;
  }

  Application createApplication(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Application t = Application.builder()
            .name("DamApp")
            .version("1.0")
            .owner("7bulls.com")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new ApplicationTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION, set);
    return t;
  }

  ApplicationInstance createApplicationInstance(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    ApplicationInstance t = ApplicationInstance.builder()
            .name("DamAppInst")
            .appName("DamApp")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new ApplicationInstanceTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION_INSTANCE, set);
    return t;
  }

  Application createApplicationV2(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Application t = Application.builder()
            .name("DamApp2")
            .version("2.0")
            .owner("7bulls.com")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new ApplicationTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION, set);
    return t;
  }

  LifecycleComponent createLifecycleComponentEl(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    LifecycleComponent t = LifecycleComponent.builder()
            .name("DamWEl")
            .preInstallCmd("sudo apt-get -y update && sudo apt-get -y install wget unzip && wget -O download https://owncloud.7bulls.com/index.php/s/K115JCDvH8Br1xG/download && unzip download")
            .installCmd("./paasage-7bc/elastic-docker.sh install")
            .postInstallCmd("./paasage-7bc/elastic-docker.sh configure")
            .startCmd("/paasage-7bc/elastic-docker.sh start")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new LifecycleComponentTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.LIFECYCLE, set);
    return t;
  }

  LifecycleComponent createLifecycleComponentApp(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    LifecycleComponent t = LifecycleComponent.builder()
            .name("DamWApp")
            .preInstallCmd("sudo apt-get -y update && sudo apt-get -y install wget unzip && wget -O download https://owncloud.7bulls.com/index.php/s/K115JCDvH8Br1xG/download && unzip download")
            .installCmd("./paasage-7bc/dam-docker.sh install")
            .postInstallCmd("./paasage-7bc/dam-docker.sh configure")
            .startCmd("/paasage-7bc/dam-docker.sh start")
            .build();
    tasks.get(GraphValidator.TASK_TYPE.LIFECYCLE).add(new LifecycleComponentTask(Type.CREATE, t));
    return t;
  }

  VirtualMachine createVirtualMachineEl(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachine t = VirtualMachine.builder()
            .name("VM_EL")
            .cloudName("EC2")
            .location("eu-west-1")
            .locationTimeout(100000L)
            .hardware("m4.large")
            .hardwareTimeout(100000L)
            .image("ami-b9b394ca")
            .imageTimeout(100000L)
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new VirtualMachineTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.VIRTUALMACHINE, set);
    return t;
  }

  VirtualMachine createVirtualMachineApp(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachine t = VirtualMachine.builder()
            .name("VM_DAM")
            .cloudName("EC2")
            .location("eu-west-1")
            .locationTimeout(100000L)
            .hardware("t2.medium")
            .hardwareTimeout(100000L)
            .image("ami-b9b394ca")
            .imageTimeout(100000L)
            .build();
    tasks.get(GraphValidator.TASK_TYPE.VIRTUALMACHINE).add(new VirtualMachineTask(Type.CREATE, t));
    return t;
  }

  VirtualMachineInstance createVirtualMachineInstanceEl(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachineInstance t = VirtualMachineInstance.builder()
            .name("WM_DAM_ELVMInstance_1_3")
            .vmName("VM_EL")
            .cloudName("EC2")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
    Set<Task> set = new HashSet<>();
    set.add(new VirtualMachineInstanceTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.VIRTUALMACHINE_INSTANCE, set);
    return t;
  }

  VirtualMachineInstance createVirtualMachineInstanceApp(Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachineInstance t = VirtualMachineInstance.builder()
            .name("WM_DAMVMInstance_1_11")
            .vmName("VM_DAM")
            .cloudName("EC2")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
    tasks.get(GraphValidator.TASK_TYPE.VIRTUALMACHINE_INSTANCE)
            .add(new VirtualMachineInstanceTask(Type.CREATE, t));
    return t;
  }

  ApplicationComponent createApplicationComponentEl() {
    return ApplicationComponent.builder()
            .name("DamAcEl")
            .appName("DamApp")
            .lcName("DamWEl")
            .vmName("VM_DAM")
            .cloudName("EC2")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
  }

  ApplicationComponent createApplicationComponentApp() {
    return ApplicationComponent.builder()
            .name("DamAcApp")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_EL")
            .cloudName("EC2")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
  }

  ApplicationComponentInstance createApplicationComponentInstanceEl() {
    return ApplicationComponentInstance.builder()
            .name("DamAcEl_Instance")
            .acName("DamAcEl")
            .vmInstName("WM_DAM_ELVMInstance_1_3")
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWEl")
            .vmName("VM_EL")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
  }

  ApplicationComponentInstance createApplicationComponentInstanceApp() {
    return ApplicationComponentInstance.builder()
            .name("DamAcApp_Instance")
            .acName("DamAcApp")
            .vmInstName("WM_DAMVMInstance_1_11")
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_DAM")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
  }

  Communication createCommunication() {
    return Communication.builder()
            .name("CommProvReq")
            .portProvName("ELPortA")
            .portReqName("ELPortAReq")
            .build();
  }

  PortProvided createPortProvided10000() {
    return PortProvided.builder()
            .name("Dam2Port")
            .acName("DamAcApp")
            .port(10000)
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_DAM")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
  }

  PortProvided createPortProvided9200() {
    return PortProvided.builder()
            .name("ELPortA")
            .acName("DamAcEl")
            .port(9200)
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWEl")
            .vmName("VM_EL")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
  }

  PortProvided createPortProvided443() {
    return PortProvided.builder()
            .name("Dam1Port")
            .acName("DamAcApp")
            .port(443)
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_DAM")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
  }

  PortRequired createPortRequired() {
    return PortRequired.builder()
            .name("ELPortAReq")
            .acName("DamAcApp")
            .mandatory(Boolean.TRUE)
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_DAM")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
  }

  VirtualMachineInstanceMonitor toMonitor1() {
    return VirtualMachineInstanceMonitor.builder()
            .vmInstName("WM_DAM_ELVMInstance_1_3")
            .vmInstTimeout(100000L)
            .build();
  }

  VirtualMachineInstanceMonitor toMonitor2() {
    return VirtualMachineInstanceMonitor.builder()
            .vmInstName("WM_DAMVMInstance_1_11")
            .vmInstTimeout(100000L)
            .build();
  }

  ApplicationComponentInstanceMonitor toMonitor3() {
    return ApplicationComponentInstanceMonitor.builder()
            .acInstName("DamAcEl_Instance")
            .acInstTimeout(100000L)
            .build();
  }

  ApplicationComponentInstanceMonitor toMonitor4() {
    return ApplicationComponentInstanceMonitor.builder()
            .acInstName("DamAcApp_Instance")
            .acInstTimeout(100000L)
            .build();
  }
}
