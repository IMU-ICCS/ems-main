package eu.melodic.upperware.adapter.service.Instance_no_provider;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  This class assigns instance numbers to make sure that busy
 *  instances remains, and idle are deleted
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BusyFirstInstanceNoProvider extends InstanceNoProvider {

    private final BusyInstancesRegistry busyInstancesRegistry;

    @Override
    public Integer getNewInstanceNoForComponent(String softwareComponentName) {
        log.debug("Providing instanceNo for instance of component: {}", softwareComponentName);
        Integer notYetUsedInstanceNo;
        notYetUsedInstanceNo = busyInstancesRegistry.getNotUsedInstanceNoByStatus(softwareComponentName,
                InstanceStatus.BUSY);
        if (notYetUsedInstanceNo == BusyInstancesRegistry.NO_DATA_OR_INTEGER_ALREADY_USED) {
            log.debug("Could not provide instanceId of working BUSY instance");
            notYetUsedInstanceNo = busyInstancesRegistry.getNotUsedInstanceNoByStatus(softwareComponentName,
                    InstanceStatus.IDLE);
        }

        List<Integer> usedNo = super.usedNoByComponentName.computeIfAbsent(softwareComponentName, key-> new ArrayList<>());

        if (notYetUsedInstanceNo == BusyInstancesRegistry.NO_DATA_OR_INTEGER_ALREADY_USED) {
            log.debug("Could not provide instanceId of working IDLE instance");
            notYetUsedInstanceNo = super.getFirstNotPresent(usedNo);
        }

        int indexInSortedList = Collections.binarySearch(usedNo, notYetUsedInstanceNo);
        usedNo.add( (-indexInSortedList) - 1, notYetUsedInstanceNo);
        return notYetUsedInstanceNo;
    }

    @Override
    public void restart(String applicationId) {
        this.busyInstancesRegistry.restart(usedNoByComponentName, applicationId);
        super.restart(applicationId);
    }

}
