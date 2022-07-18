package eu.melodic.upperware.adapter.service.Instance_no_provider;

import eu.melodic.upperware.adapter.communication.activemq.model.CheckIfComponentBusyMessage;
import eu.melodic.upperware.adapter.service.CamelInstanceNamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class BusyInstancesRegistry {
    static final Integer NO_DATA_OR_INTEGER_ALREADY_USED = null;

    private final ConcurrentHashMap<String, List<Integer>> busyInstancesByComponentName;
    private final ConcurrentHashMap<String, List<Integer>> idleInstancesByComponentName;

    private List<String> currentDeploymentSoftwareComponentsList;

    public void processMessage(CheckIfComponentBusyMessage checkIfComponentBusyMessage) {
        String softwareComponentInstanceName = checkIfComponentBusyMessage.getComponentInstanceName();
        String softwareComponentName = CamelInstanceNamingService.getSoftwareComponentNameFromInstanceName(softwareComponentInstanceName);
        Integer softwareComponentInstanceNo = CamelInstanceNamingService.getInstanceNumberFromInstanceName(softwareComponentInstanceName);
        log.debug("Saving instanceNo: {} for component: {}", softwareComponentInstanceNo, softwareComponentInstanceName);
        if (!currentDeploymentSoftwareComponentsList.contains(softwareComponentName)) {
            log.error("Received instance state concerns softwareComponent not existing in the current deployment");
        } else {
            switch (checkIfComponentBusyMessage.getInstanceStatus()) {
                case BUSY: {
                    this.busyInstancesByComponentName.compute(softwareComponentName, (key, list) -> {
                        if (list == null) {
                            return new LinkedList<>(Collections.singletonList(softwareComponentInstanceNo));
                        } else {
                            list.add(softwareComponentInstanceNo);
                            return list;
                        }
                    });
                    break;
                }
                case IDLE: {
                    this.idleInstancesByComponentName.compute(softwareComponentName, (key, list) -> {
                        if (list == null) {
                            return new LinkedList<>(Collections.singletonList(softwareComponentInstanceNo));
                        } else {
                            list.add(softwareComponentInstanceNo);
                            return list;
                        }
                    });
                    break;
                }
                case NOT_DEFINED: {
                    break;
                }
                default: {
                    log.error("Received message contains not recognised component status");
                    break;
                }
            }
        }
    }

    void restart(List<String> currentDeploymentSoftwareComponentsList) {
        this.currentDeploymentSoftwareComponentsList = currentDeploymentSoftwareComponentsList;
        this.busyInstancesByComponentName.clear();
        this.idleInstancesByComponentName.clear();
    }

    Integer getBusyNoFromListIfNotYetUsed(String softwareComponentName,
                                          Map<String, List<Integer>> usedNoByComponentName) {
        return getNoFromListIfNotYetUsed(softwareComponentName, busyInstancesByComponentName, usedNoByComponentName);
    }

    Integer getIdleNoFromListIfNotYetUsed(String softwareComponentName,
                                          Map<String, List<Integer>> usedNoByComponentName) {
        return getNoFromListIfNotYetUsed(softwareComponentName, idleInstancesByComponentName, usedNoByComponentName);
    }

    private Integer getNoFromListIfNotYetUsed(String softwareComponentName, ConcurrentHashMap<String, List<Integer>> instancesByComponentName,
                                              Map<String, List<Integer>> usedNoByComponentName) {

        AtomicInteger notUsedInstanceNo = new AtomicInteger(-1);
        instancesByComponentName.computeIfPresent(softwareComponentName, (key, list) -> {
            for (Integer i : list) {
                if (!usedNoByComponentName.get(softwareComponentName).contains(i)) {
                    notUsedInstanceNo.set(i);
                    return list;
                }
            }
            return list;
        });
        if (notUsedInstanceNo.get() >= 0) {
            return notUsedInstanceNo.get();
        } else {
            return NO_DATA_OR_INTEGER_ALREADY_USED;
        }
    }

}
