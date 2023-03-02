package eu.melodic.upperware.utilitygenerator.facade.solver;

import eu.morphemic.facade.AbstractTextRequesterFacade;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SolverSideDemoFacade extends AbstractTextRequesterFacade {

	public SolverSideDemoFacade() {
		super(SolverFacadeImpl.REQUEST_TOPIC, SolverFacadeImpl.REPLY_TOPIC);
	}

	public static void main(String[] args) {
		Map<String, Number> solution = new HashMap<>();
		solution.put("MinimumCoresContext", 2);
		solution.put("SimulationLeftNumber", 1);
		solution.put("SimulationElapsedTime", 360);
		solution.put("MinimumCores", 2);
		solution.put("TotalCores", 8);
		solution.put("EstimatedRemainingTimeContext", 1.5d);
		solution.put("NotFinished", 1);
		solution.put("NotFinishedOnTime", 2.0);
		solution.put("NotFinishedOnTimeContext", 99);
		solution.put("RemainingSimulationTimeMetric", 42);
		solution.put("WillFinishTooSoonContext", 99999);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put(SolverFacadeImpl.TARGET_PROPERTY_NAME, "ETPercentile");
		parameters.put(SolverFacadeImpl.SOLUTION_PROPERTY_NAME, solution);

		SolverSideDemoFacade demoFacade = new SolverSideDemoFacade();
		Map<String, Object> result = demoFacade.sendRequestAndAwaitReply(parameters, 20);
		log.info("RESULT: " + result.get(FacadeUtilityService.RESULT_PROPERTY_NAME));
	}
}
