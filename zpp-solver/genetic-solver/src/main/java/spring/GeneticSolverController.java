package spring;

import eu.melodic.models.interfaces.cpSolver.ConstraintProblemSolutionFromFileRequestImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runner.GeneticSolverRunner;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class GeneticSolverController {

    private GeneticSolverCoordinator geneticSolverCoordinator;

    @RequestMapping(value = "/constraintProblemSolutionFromFile", method = POST)
    public void constraintProblemSolutionFromFile(@RequestBody ConstraintProblemSolutionFromFileRequestImpl request) throws Exception {
        String camelModelFilePath = request.getCamelModelFilePath();
        String cpModelPath = request.getCpProblemFilePath();
        String nodeCandidatesFilePath = request.getNodeCandidatesFilePath();
        log.info("Received constraintProblemSolutionFromFile request: \n" + camelModelFilePath + " \n" + cpModelPath);

        geneticSolverCoordinator.generateCPSolutionFromFile(camelModelFilePath, cpModelPath, nodeCandidatesFilePath);
        log.info("Sleeping...");
    }
}
