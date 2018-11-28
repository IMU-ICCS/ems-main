package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProcessesConverter implements ModelConverter<DeploymentInstanceModel, List<AdapterProcess >> {

    @Override
    public List<AdapterProcess> toComparableModel(DeploymentInstanceModel model) {

        String jobName = ConverterUtils.getJobName(model);
        String scheduleName = ConverterUtils.getScheduleName(model);

        return model
                .getSoftwareComponentInstances()
                .stream()
                .map(softwareComponentInstance -> createProcess(softwareComponentInstance, jobName, scheduleName))
                .collect(Collectors.toList());
    }

    private AdapterProcess createProcess(SoftwareComponentInstance softwareComponentInstance, String jobName, String scheduleName) {

        return AdapterProcess.builder()
                .jobName(jobName)
                .nodeName(softwareComponentInstance.getName())
                .scheduleName(scheduleName)
                .taskName(softwareComponentInstance.getType().getName())
                .build();
    }
}
