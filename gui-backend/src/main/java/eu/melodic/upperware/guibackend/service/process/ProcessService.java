package eu.melodic.upperware.guibackend.service.process;

import eu.melodic.upperware.guibackend.communication.camunda.CamundaClientApi;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.service.cdo.CdoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessService {

    private CamundaClientApi camundaClientApi;
    private CdoService cdoService;

    public CpModelResponse getCpModel(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaClientApi.getProcessVariables(processId);
        if (processVariables.containsKey("cpCdoPath")) {
            String cpCdoPath = processVariables.get("cpCdoPath").getValue();
            log.info("CpCdoPath: {}", cpCdoPath);

            String applicationId = processVariables.get("applicationId").getValue();
            log.info("Application id: {}", applicationId);

            return cdoService.getCpModelResponse(cpCdoPath, applicationId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exists for process with id: %s", processId));
        }
    }
}
