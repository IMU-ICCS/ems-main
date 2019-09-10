package eu.melodic.upperware.guibackend.service.process;

import eu.melodic.models.services.adapter.DifferenceRequestImpl;
import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.guibackend.communication.adapter.AdapterApi;
import eu.melodic.upperware.guibackend.communication.camunda.CamundaApi;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableName;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
import eu.melodic.upperware.guibackend.controller.common.ProcessState;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.controller.process.response.CpSolutionResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessInstanceResponse;
import eu.melodic.upperware.guibackend.service.cdo.CdoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.BadRequestException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessService {

    private CamundaApi camundaApi;
    private AdapterApi adapterApi;
    private ProcessCamundaService processCamundaService;
    private CdoService cdoService;

    public CpModelResponse getCpModel(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
        if (processVariables.containsKey(CamundaVariableName.CP_CDO_PATH.label)) {
            String cpCdoPath = processVariables.get(CamundaVariableName.CP_CDO_PATH.label).getValue();
            log.info("CpCdoPath: {}", cpCdoPath);

            String applicationId = processVariables.get(CamundaVariableName.APPLICATION_ID.label).getValue();
            log.info("Application id: {}", applicationId);

            return cdoService.getCpModelResponse(cpCdoPath, applicationId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exist for process with id: %s", processId));
        }
    }

    public CpSolutionResponse getCpSolutionForProcess(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
        if (processVariables.containsKey(CamundaVariableName.CP_CDO_PATH.label)) {
            String cpCdoPath = processVariables.get(CamundaVariableName.CP_CDO_PATH.label).getValue();
            log.info("CpCdoPath: {}", cpCdoPath);
            if (processVariables.containsKey(CamundaVariableName.DEPLOYED_SOLUTION_ID.label)) {
                log.info("Cp solution already deployed");
                return cdoService.getCpSolution(cpCdoPath, Integer.valueOf(processVariables.get(CamundaVariableName.DEPLOYED_SOLUTION_ID.label).getValue()));
            } else {
                log.info("Cp solution not deployed yet. Getting last available solution");
                return cdoService.getCpSolution(cpCdoPath, null);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exist for process with id: %s", processId));
        }
    }

    public DifferenceResponse getDeploymentDifference(String processId, String token) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
        String applicationId = processVariables.get(CamundaVariableName.APPLICATION_ID.label).getValue();
        String currentDeploymentInstanceName = getDeploymentInstanceName(processId);
        String previousDeploymentInstanceName = findPreviousDeploymentInstanceName(processId, processVariables);
        log.info("Getting deployment difference for current deployment instance name : {} and previous: {} for process: {}", currentDeploymentInstanceName, previousDeploymentInstanceName, processId);
        DifferenceRequestImpl differenceRequest = createDifferenceRequest(applicationId, currentDeploymentInstanceName, previousDeploymentInstanceName);
        return adapterApi.getDifference(differenceRequest, token);
    }

    private String findPreviousDeploymentInstanceName(String currentProcessId, Map<String, CamundaVariableResponseItem> currentProcessVariables) {
        Date currentProcessFinishDate = null;
        if (currentProcessVariables.containsKey(CamundaVariableName.PROCESS_FINISH_DATE.label)) {
            String currentProcessFinishDateAsString = currentProcessVariables.get(CamundaVariableName.PROCESS_FINISH_DATE.label).getValue();
            currentProcessFinishDate = mapStingCamundaResponseToDate(currentProcessFinishDateAsString);
        }
        List<ProcessInstanceResponse> allProcessesData = processCamundaService.getAllProcessesData();
        List<ProcessInstanceResponse> finishedProcessesSortedByFinishDate = allProcessesData.stream()
                .filter(processInstanceResponse -> ProcessState.FINISHED.equals(processInstanceResponse.getProcessState()))
                .sorted(Comparator.comparing(processInstanceResponse -> mapStingCamundaResponseToDate(processInstanceResponse.getFinishDate())))
                .collect(Collectors.toList());

        String previousProcessId = null;

        if (currentProcessFinishDate == null && !finishedProcessesSortedByFinishDate.isEmpty()) { // not initial deployment process in progress
            ProcessInstanceResponse processInstanceResponse = finishedProcessesSortedByFinishDate.get(finishedProcessesSortedByFinishDate.size() - 1);
            previousProcessId = processInstanceResponse.getProcessId();
        } else if (currentProcessFinishDate != null && finishedProcessesSortedByFinishDate.size() > 1) { // not initial deployment process finished
            ProcessInstanceResponse currentProcessInstanceResponse = finishedProcessesSortedByFinishDate.stream()
                    .filter(processInstanceResponse -> currentProcessId.equals(processInstanceResponse.getProcessId()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException(String.format("Process with id %s doesn't exist on finished process list", currentProcessId)));
            int indexOfCurrentProcess = finishedProcessesSortedByFinishDate.indexOf(currentProcessInstanceResponse);
            if (indexOfCurrentProcess > 0) {
                ProcessInstanceResponse previousInstanceResponse = finishedProcessesSortedByFinishDate.get(indexOfCurrentProcess - 1);
                previousProcessId = previousInstanceResponse.getProcessId();
            }
        }
        log.info("Previous process id: {}", previousProcessId);

        if (previousProcessId != null) {
            return getDeploymentInstanceName(previousProcessId);
        } else {
            return null; //initial deployment
        }
    }

    private Date mapStingCamundaResponseToDate(String currentProcessFinishDate) {
        currentProcessFinishDate = currentProcessFinishDate.replace('T', ' ');
        currentProcessFinishDate = currentProcessFinishDate.replace("+", " +");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        Date date = null;
        try {
            date = formatter.parse(currentProcessFinishDate);
        } catch (ParseException e) {
            log.error("Error by parsing date: {}", currentProcessFinishDate, e);
        }
        return date;
    }

    private String getDeploymentInstanceName(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
        if (processVariables.containsKey(CamundaVariableName.DEPLOYMENT_INSTANCE_NAME.label)) {
            String currentDeploymentInstanceName = processVariables.get(CamundaVariableName.DEPLOYMENT_INSTANCE_NAME.label).getValue();
            log.info("DeploymentInstanceName: {} for process: {} already exist", currentDeploymentInstanceName, processId);
            return currentDeploymentInstanceName;

        } else {
            log.info("Deployment difference for process {} not created yet", processId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable deploymentInstanceName doesn't exist for process: %s", processId));
        }
    }

    private DifferenceRequestImpl createDifferenceRequest(String applicationId, String currentDeploymentInstanceName, String previousDeploymentInstanceName) {
        DifferenceRequestImpl differenceRequest = new DifferenceRequestImpl();
        differenceRequest.setApplicationId(applicationId);
        differenceRequest.setCurrDeploymentInstanceName(currentDeploymentInstanceName);
        differenceRequest.setPrevDeploymentInstanceName(previousDeploymentInstanceName);
        return differenceRequest;
    }
}
