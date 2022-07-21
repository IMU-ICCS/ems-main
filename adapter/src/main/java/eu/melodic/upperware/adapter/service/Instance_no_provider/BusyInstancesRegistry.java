package eu.melodic.upperware.adapter.service.Instance_no_provider;

import eu.melodic.upperware.adapter.communication.activemq.model.CheckIfComponentBusyMessage;
import eu.melodic.upperware.adapter.service.CamelInstanceNamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BusyInstancesRegistry {
    static final Integer NO_DATA_OR_INTEGER_ALREADY_USED = null;

    //instanceName -> instance number -> status
    private final ConcurrentHashMap<String, Map<Integer, InstanceStatus>> instancesByComponentName;

    public void processMessage(CheckIfComponentBusyMessage checkIfComponentBusyMessage) {
        String softwareComponentInstanceName = checkIfComponentBusyMessage.getComponentInstanceName();
        String softwareComponentName = CamelInstanceNamingService.getSoftwareComponentNameFromInstanceName(softwareComponentInstanceName);
        Integer softwareComponentInstanceNo = CamelInstanceNamingService.getInstanceNumberFromInstanceName(softwareComponentInstanceName);
        log.debug("Saving instanceNo: {} for component: {}", softwareComponentInstanceNo, softwareComponentName);
        if (verifyIfExistsInCurrentDeployment(softwareComponentName, softwareComponentInstanceNo)) {
            this.instancesByComponentName.computeIfPresent(softwareComponentName, (key, instanceStatusByInstanceNumber) -> {
                instanceStatusByInstanceNumber.put(softwareComponentInstanceNo, checkIfComponentBusyMessage.getInstanceStatus());
                return instanceStatusByInstanceNumber;
            });
        }
    }

    //sets current deployment model
    void restart(Map<String, List<Integer>> usedNoByComponentName) {
        this.instancesByComponentName.clear();
        usedNoByComponentName.forEach((softwareComponentName, instances) -> this.instancesByComponentName.put(
                softwareComponentName,
                instances.stream()
                        .collect(Collectors.toMap(Function.identity(), i -> InstanceStatus.BUSY))
        ));
    }

    Integer getNotUsedInstanceNoByStatus(String softwareComponentName, InstanceStatus instanceStatus) {
        AtomicInteger notUsedInstanceNo = new AtomicInteger(-1);
        //We need to iterate instead of remove the element because the listeners are still working
        instancesByComponentName.computeIfPresent(softwareComponentName, (key, statusByInstanceNo) -> {
            Optional<Integer> instanceNoToAssign = statusByInstanceNo.entrySet().stream()
                    .filter(e -> e.getValue().equals(instanceStatus))
                    .map(Map.Entry::getKey)
                    .sorted()
                    .findFirst();
            instanceNoToAssign.ifPresent(i -> {
                if (statusByInstanceNo.remove(i, instanceStatus)) {
                    notUsedInstanceNo.set(i);
                }
            });
            return statusByInstanceNo;
        });
        if (notUsedInstanceNo.get() >= 0) {
            return notUsedInstanceNo.get();
        } else {
            return NO_DATA_OR_INTEGER_ALREADY_USED;
        }
    }

    private boolean verifyIfExistsInCurrentDeployment(String softwareComponent, Integer instanceNo) {
        if (!instancesByComponentName.containsKey(softwareComponent)) {
            log.error("Received softwareComponent does not exist in the current deployment");
            return false;
        }
        if (!instancesByComponentName.get(softwareComponent).containsKey(instanceNo)) {
            log.error("Received softwareComponent Instance No does not exist in the current deployment");
            return false;
        }
        return true;
    }

}
