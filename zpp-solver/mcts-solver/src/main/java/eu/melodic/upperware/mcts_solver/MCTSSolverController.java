package eu.melodic.upperware.mcts_solver;

import eu.melodic.models.interfaces.cpSolver.ConstraintProblemSolutionFromFileRequestImpl;
import eu.melodic.models.interfaces.cpSolver.ConstraintProblemSolutionRequestImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MCTSSolverController {

    private MCTSSolverCoordinator MCTSSolverCoordinator;

    @RequestMapping(value = "/constraintProblemSolutionFromFile", method = POST)
    public void constraintProblemSolutionFromFile(@RequestBody ConstraintProblemSolutionFromFileRequestImpl request) throws Exception {
        String camelModelFilePath = request.getCamelModelFilePath();
        String cpModelPath = request.getCpProblemFilePath();
        String nodeCandidatesFilePath = request.getNodeCandidatesFilePath();
        int seconds = request.getTimeLimit();
        log.info("Received constraintProblemSolutionFromFile request: \n" + camelModelFilePath + " \n" + cpModelPath);

        MCTSSolverCoordinator.generateCPSolutionFromFile(camelModelFilePath, cpModelPath, nodeCandidatesFilePath, seconds);
        log.info("Sleeping...");
    }

    @RequestMapping(value = "/constraintProblemSolutionFromFileWithTemplate", method = POST)
    public void constraintProblemSolutionFromFileWithTemplate(@RequestBody ConstraintProblemSolutionFromFileRequestImpl request) throws Exception {
        throw new RuntimeException("Not implemented yet");
    }

    @RequestMapping(value = "/constraintProblemSolution", method = POST)
    public void constraintProblemSolution(@RequestBody ConstraintProblemSolutionRequestImpl request) {
        String applicationId = request.getApplicationId();
        String cdoResourcePath = request.getCdoModelsPath();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();
        int seconds = request.getTimeLimit();
        log.info("Received request: " + applicationId + " " + cdoResourcePath + " " + notificationUri + " " + requestUuid);

        MCTSSolverCoordinator.generateCPSolution(applicationId, cdoResourcePath, notificationUri, requestUuid, seconds);
        log.info("Sleeping...");
    }
}
