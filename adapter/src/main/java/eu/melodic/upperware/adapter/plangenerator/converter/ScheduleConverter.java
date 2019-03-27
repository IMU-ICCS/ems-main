package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterSchedule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ScheduleConverter implements ModelConverter<DeploymentInstanceModel, AdapterSchedule> {


    @Override
    public AdapterSchedule toComparableModel(DeploymentInstanceModel model) {

        return AdapterSchedule.builder()
                .jobName(ConverterUtils.getJobName(model))
                .instantiation(AdapterSchedule.InstantiationEnum.MANUAL)
                .build();
    }
}
