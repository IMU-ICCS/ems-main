package eu.melodic.upperware.adapter.service.Instance_no_provider;
import java.util.*;

/**
 *  This class is responsible for providing unique instance numbers
 *  for each component. Those numbers are important, as for reconfiguration,
 *  differences between models are created. It means that this class decides which
 *  instances will be left and which will be deleted during reconfiguration.
 *
 *  This class is the simplest option, assigning numbers in the incremented order.
 *  Class 'BusyFirstInstanceNoProvider' assigns numbers to make sure that busy
 *  instances remains, and idle are deleted
 */
public class InstanceNoProvider {

    //prefix -> sorted ids
    Map<String, List<Integer>> usedNoByComponentName = new HashMap<>();
    private String applicationId;

    public Integer getNewInstanceNoForComponent(String softwareComponentName) {
        List<Integer> usedNo = usedNoByComponentName.computeIfAbsent(softwareComponentName, key-> new ArrayList<>());
        int firstNotUsed = getFirstNotPresent(usedNo);
        usedNo.add(firstNotUsed, firstNotUsed);
        return firstNotUsed;
    }

    public void restart(String applicationId) {
        this.applicationId = applicationId;
        this.usedNoByComponentName.clear();
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
