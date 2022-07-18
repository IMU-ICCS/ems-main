package eu.melodic.upperware.adapter.service.Instance_no_provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  This class assigns numbers to make sure that busy
 *  instances remains, and idle are deleted
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BusyFirstInstanceNoProvider extends InstanceNoProvider {
    private static final Integer NO_DATA_OR_INTEGER_ALREADY_USED = null;

    private final ConcurrentHashMap<String, List<Integer>> busyInstancesByComponentName;
    private final ConcurrentHashMap<String, List<Integer>> idleInstancesByComponentName;

    @Override
    public Integer getNewInstanceNoForComponent(String softwareComponentName) {
        log.debug("Providing instanceNo for instance of component: {}", softwareComponentName);
        Integer notYetUsedInstanceNo;
        notYetUsedInstanceNo = getNoFromListIfNotYetUsed(softwareComponentName, busyInstancesByComponentName);
        if (notYetUsedInstanceNo == NO_DATA_OR_INTEGER_ALREADY_USED) {
            log.debug("Could not provide instanceId of working BUSY instance");
            notYetUsedInstanceNo = getNoFromListIfNotYetUsed(softwareComponentName, idleInstancesByComponentName);
        }

        List<Integer> usedNo = super.usedNoByComponentName.computeIfAbsent(softwareComponentName, key-> new ArrayList<>());

        if (notYetUsedInstanceNo == NO_DATA_OR_INTEGER_ALREADY_USED) {
            log.debug("Could not provide instanceId of working IDLE instance");
            notYetUsedInstanceNo = super.getFirstNotPresent(usedNo);
        }

        int indexInSortedList = Collections.binarySearch(usedNo, notYetUsedInstanceNo);
        usedNo.add( (-indexInSortedList) - 1, notYetUsedInstanceNo);
        return notYetUsedInstanceNo;
    }

    @Override
    public void restart() {
        super.restart();
        this.busyInstancesByComponentName.clear();
        this.idleInstancesByComponentName.clear();
    }

    private Integer getNoFromListIfNotYetUsed(String softwareComponentName, ConcurrentHashMap<String, List<Integer>> instancesByComponentName) {

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
