package eu.melodic.upperware.adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class BusyFirstInstanceNoProvider extends InstanceNoProvider {

    private ConcurrentHashMap<String, List<Integer>> busyInstancesByComponentName;
    private ConcurrentHashMap<String, List<Integer>> idleInstancesByComponentName;

    @Override
    public Integer getNewInstanceNoForComponent(String softwareComponentName) {
        Integer notYetUsedInstanceNo;
        notYetUsedInstanceNo = getNoFromListIfNotYetUsed(softwareComponentName, busyInstancesByComponentName);
        if (notYetUsedInstanceNo == null) {
            notYetUsedInstanceNo = getNoFromListIfNotYetUsed(softwareComponentName, idleInstancesByComponentName);
        }

        List<Integer> usedNo = super.usedNoByComponentName.computeIfAbsent(softwareComponentName, key-> new ArrayList<>());

        if (notYetUsedInstanceNo == null) {
            notYetUsedInstanceNo = super.getFirstNotPresent(usedNo);
        }

        int indexInSortedList = Collections.binarySearch(usedNo, notYetUsedInstanceNo);
        usedNo.add( (-indexInSortedList) - 1, notYetUsedInstanceNo);
        return notYetUsedInstanceNo;
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
            return null;
        }
    }
}
