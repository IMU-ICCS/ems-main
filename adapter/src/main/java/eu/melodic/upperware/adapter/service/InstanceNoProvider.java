package eu.melodic.upperware.adapter.service;

import java.util.*;

public class InstanceNoProvider {

    //prefix -> sorted ids
    Map<String, List<Integer>> usedNoByComponentName = new HashMap<>();

    public Integer getNewInstanceNoForComponent(String softwareComponentName) {
        List<Integer> usedNo = usedNoByComponentName.computeIfAbsent(softwareComponentName, key-> new ArrayList<>());
        int firstNotUsed = getFirstNotPresent(usedNo);
        usedNo.add(firstNotUsed, firstNotUsed);
        return firstNotUsed;
    }

    int getFirstNotPresent(List<Integer> integers) {
        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i) != i) {
                return i;
            }
        }
        return integers.size();
    }
}
