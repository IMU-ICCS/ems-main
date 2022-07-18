package eu.melodic.upperware.adapter.service.Instance_no_provider;

import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  This class assigns numbers to make sure that busy
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
        notYetUsedInstanceNo = busyInstancesRegistry.getBusyNoFromListIfNotYetUsed(softwareComponentName,
                usedNoByComponentName);
        if (notYetUsedInstanceNo == BusyInstancesRegistry.NO_DATA_OR_INTEGER_ALREADY_USED) {
            log.debug("Could not provide instanceId of working BUSY instance");
            notYetUsedInstanceNo = busyInstancesRegistry.getIdleNoFromListIfNotYetUsed(softwareComponentName,
                    usedNoByComponentName);
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
    public void restart(CamelModel camelModel) {
        super.restart(null);
        Optional<DeploymentInstanceModel> lastDeploymentInstanceModel =
        CdoTool.getLastElementAsOptional(camelModel.getExecutionModels())
                .flatMap(CdoTool::getCurrentlyInstalledModel);
        this.busyInstancesRegistry.restart(
                lastDeploymentInstanceModel
                        .map(DeploymentInstanceModel::getSoftwareComponentInstances)
                        .stream()
                        .flatMap(list -> list.stream().map(SoftwareComponentInstance::getName))
                        .collect(Collectors.toList())
        );
    }
}
