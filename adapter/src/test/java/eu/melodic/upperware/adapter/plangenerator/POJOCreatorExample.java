package eu.melodic.upperware.adapter.plangenerator;

import com.google.common.collect.Maps;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class POJOCreatorExample {

  CloudApi createApi(String name, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {

    CloudApi t = CloudApi.builder()
            .name(name)
            .driver("aws-ec2")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.CLOUD_API);
    set.add(new CloudApiTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD_API, set);
    return t;
  }

  Cloud createCloud(String name, String apiName, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Cloud t = Cloud.builder()
            .name(name)
            .apiName(apiName)
            .endpoint("https://ec2.eu-west-1.amazonaws.com")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.CLOUD);
    set.add(new CloudTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD, set);
    return t;
  }

  CloudProperty createCloudProperty(String name, String cloudName,
                                    Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Map filters = Maps.newHashMap();
    filters.put("sword.ec2.ami.query", "image-id=ami-b9b394ca");
    filters.put("sword.ec2.ami.cc.query", "image-id=ami-b9b394ca");

    CloudProperty t = CloudProperty.builder()
            .name(name)
            .cloudName(cloudName)
            .filters(filters)
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.CLOUD_PROPERTY);
    set.add(new CloudPropertyTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD_PROPERTY, set);
    return t;
  }

  CloudCredential createCloudCredential(String name, String cloudName,
                                        Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    CloudCredential t = CloudCredential.builder()
            .name(name)
            .cloudName(cloudName)
            .login("AKIAIUVVELYXJVJMKR5A")
            .password("daISW/6LNS5lBTrqkFL5H5ROpzoYDSvLGuhKKuZ0")
            .tenant(1L)
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.CLOUD_CREDENTIAL);
    set.add(new CloudCredentialTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.CLOUD_CREDENTIAL, set);
    return t;
  }

  Application createApplication(String name, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Application t = Application.builder()
            .name(name)
            .version("1.0")
            .owner("7bulls.com")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.APPLICATION);
    set.add(new ApplicationTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION, set);
    return t;
  }

  ApplicationInstance createApplicationInstance(String name, String appName, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    ApplicationInstance t = ApplicationInstance.builder()
            .name(name)
            .appName(appName)
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.APPLICATION_INSTANCE);
    set.add(new ApplicationInstanceTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION_INSTANCE, set);
    return t;
  }

  LifecycleComponent createLifecycleComponent(String name, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    LifecycleComponent t = LifecycleComponent.builder()
            .name(name)
            .preInstallCmd("sudo apt-get -y update && sudo apt-get -y install wget unzip && wget -O download https://owncloud.7bulls.com/index.php/s/K115JCDvH8Br1xG/download && unzip download")
            .installCmd("./paasage-7bc/elastic-docker.sh install")
            .postInstallCmd("./paasage-7bc/elastic-docker.sh configure")
            .startCmd("/paasage-7bc/elastic-docker.sh start")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.LIFECYCLE);
    set.add(new LifecycleComponentTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.LIFECYCLE, set);
    return t;
  }


  VirtualMachine createVirtualMachine(String name, String cloudName, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachine t = VirtualMachine.builder()
            .name(name)
            .cloudName(cloudName)
            .location("eu-west-1")
            .locationTimeout(100000L)
            .hardware("m4.large")
            .hardwareTimeout(100000L)
            .image("ami-b9b394ca")
            .imageTimeout(100000L)
            .build();

    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.VIRTUALMACHINE);
    set.add(new VirtualMachineTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.VIRTUALMACHINE, set);
    return t;
  }

  VirtualMachineInstance createVirtualMachineInstance(String name, String vmName, Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachineInstance t = VirtualMachineInstance.builder()
            .name(name)
            .vmName(vmName)
            .cloudName("EC2")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.VIRTUALMACHINE_INSTANCE);
    set.add(new VirtualMachineInstanceTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.VIRTUALMACHINE_INSTANCE, set);
    return t;
  }


  ApplicationComponent createApplicationComponent(String name, String appName, String lcName, String vmName,
                                                  Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {

    ApplicationComponent t = ApplicationComponent.builder()
            .name(name)
            .appName(appName)
            .lcName(lcName)
            .vmName(vmName)
            .cloudName("EC2")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.APPLICATION_COMPONENT);
    set.add(new ApplicationComponentTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION_COMPONENT, set);
    return t;
  }

  ApplicationComponentInstance createApplicationComponentInstance(String name, String appName, String vmInstName, String acName,
                                                                  Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    ApplicationComponentInstance t = ApplicationComponentInstance.builder()
            .name(name)
            .acName(acName)
            .vmInstName(vmInstName)
            .cloudName("EC2")
            .appName(appName)
            .lcName("DamWEl")
            .vmName("VM_EL")
            .location("eu-west-1")
            .hardware("m4.large")
            .image("ami-b9b394ca")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.APPLICATION_COMPONENT_INSTANCE);
    set.add(new ApplicationComponentInstanceTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APPLICATION_COMPONENT_INSTANCE, set);
    return t;
  }

  Communication createCommunication(String name, String portProvName, String portReqName,
                                    Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    Communication t = Communication.builder()
            .name(name)
            .portProvName(portProvName)
            .portReqName(portReqName)
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.COMMUNICATION);
    set.add(new CommunicationTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.COMMUNICATION, set);
    return t;
  }

  PortProvided createPortProvided(String name, String acName,
                                  Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    PortProvided t = PortProvided.builder()
            .name(name)
            .acName(acName)
            .port(10000)
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_DAM")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.PORT_PROVIDED);
    set.add(new PortProvidedTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.PORT_PROVIDED, set);
    return t;
  }

  PortRequired createPortRequired(String name, String acName,
                                  Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    PortRequired t = PortRequired.builder()
            .name(name)
            .acName(acName)
            .mandatory(Boolean.TRUE)
            .cloudName("EC2")
            .appName("DamApp")
            .lcName("DamWApp")
            .vmName("VM_DAM")
            .location("eu-west-1")
            .hardware("t2.medium")
            .image("ami-b9b394ca")
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.PORT_REQUIRED);
    set.add(new PortRequiredTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.PORT_REQUIRED, set);
    return t;
  }

  VirtualMachineInstanceMonitor toMonitor1(String vmInstName,
                                           Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    VirtualMachineInstanceMonitor t = VirtualMachineInstanceMonitor.builder()
            .vmInstName(vmInstName)
            .vmInstTimeout(100000L)
            .build();
    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.VM_INSTANCE_MONITOR);
    set.add(new VirtualMachineInstanceMonitorTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.VM_INSTANCE_MONITOR, set);
    return t;
  }

  ApplicationComponentInstanceMonitor toMonitor3(String acInstName,
                                                 Map<GraphValidator.TASK_TYPE, Set<Task>> tasks) {
    ApplicationComponentInstanceMonitor t = ApplicationComponentInstanceMonitor.builder()
            .acInstName(acInstName)
            .acInstTimeout(100000L)
            .build();

    Set<Task> set = tasks.get(GraphValidator.TASK_TYPE.APP_COMP_INSTANCE_MONITOR);
    set.add(new ApplicationComponentInstanceMonitorTask(Type.CREATE, t));
    tasks.put(GraphValidator.TASK_TYPE.APP_COMP_INSTANCE_MONITOR, set);
    return t;
  }
}
