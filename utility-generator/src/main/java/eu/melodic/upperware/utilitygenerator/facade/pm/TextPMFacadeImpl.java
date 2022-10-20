package eu.melodic.upperware.utilitygenerator.facade.pm;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.morphemic.facade.AbstractTextRequesterFacade;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Facade implementation for the UtilityGenerator --> Performance Module use case.
 */
@Slf4j
public class TextPMFacadeImpl extends AbstractTextRequesterFacade implements PMFacade {

	private static final String APPLICATION_PROPERTY_NAME = "application";

	public TextPMFacadeImpl() {
		super(PM_PREDICT_REQUEST_TOPIC, PM_PREDICT_REPLY_TOPIC);
	}

	@Override
	public Map<String, Double> callPmPredictionText(Collection<VariableValueDTO> solution, String applicationId, Collection<VariableDTO> variablesFromConstraintProblem, Collection<MetricDTO> metricsFromConstraintProblem) {
		if(solution.size() != variablesFromConstraintProblem.size()) {
			throw new RuntimeException("Solution and variables do not match");
		}

		Map<String, Object> features = new HashMap<>();
		for(VariableValueDTO dto : solution) {
			features.put(dto.getName(), dto.getValue());
		}

		for(MetricDTO dto : metricsFromConstraintProblem) {
			features.put(dto.getName(), dto.getValue());
		}

 		features.put(APPLICATION_PROPERTY_NAME, applicationId);

		Map<String, Object> result = sendRequestAndAwaitReply(features, 15);

		if(result.isEmpty()) {
			return Collections.emptyMap();
		}
		return (Map) result.get("results");
	}

	@Override
	public Double callPmPrediction(Collection<VariableValueDTO> solution) {
		return null;
	}

	/**
	 * Main method for testing.
	 */
	public static void main(String[] args) {
		TextPMFacadeImpl pmFacade = new TextPMFacadeImpl();

		Collection<VariableValueDTO> data = new ArrayList<>();
		// Java-Java test data
		data.add(new VariableValueDTO("dispatcherCores", 2));
		data.add(new VariableValueDTO("dispatcherCores", 2));
		data.add(new VariableValueDTO("dispatcherRam", 4));
		data.add(new VariableValueDTO("dispatcherLocation", 200.0));
		data.add(new VariableValueDTO("workerCores", 4));
		data.add(new VariableValueDTO("workerRam", 8));
		data.add(new VariableValueDTO("workerLocation", -201.0));
		data.add(new VariableValueDTO("workerMulti", 100));

		// Java-Python test data
//		data.add(new VariableValueDTO("MinimumCoresContext", 2));
//		data.add(new VariableValueDTO("SimulationLeftNumber", 1));
//		data.add(new VariableValueDTO("SimulationElapsedTime", 360));
//		data.add(new VariableValueDTO("MinimumCores", 2));
//		data.add(new VariableValueDTO("TotalCores", 8));
//		data.add(new VariableValueDTO("EstimatedRemainingTimeContext", 1.5d));
//		data.add(new VariableValueDTO("NotFinished", 1));
//		data.add(new VariableValueDTO("NotFinishedOnTime", 2.0));
//		data.add(new VariableValueDTO("NotFinishedOnTimeContext", 99));
//		data.add(new VariableValueDTO("RemainingSimulationTimeMetric", 42));
//		data.add(new VariableValueDTO("WillFinishTooSoonContext", 99999));

		Collection<VariableDTO> variablesFromConstraintProblem = new ArrayList<>();
		for(VariableValueDTO dto : data) {
			variablesFromConstraintProblem.add(new VariableDTO(dto.getName(), dto.getName(), VariableType.OS));
		}

		Collection<MetricDTO> metricsFromConstraintProblem = new ArrayList<>();
		metricsFromConstraintProblem.add(new MetricDTO("target", "ETPercentile"));
		metricsFromConstraintProblem.add(new MetricDTO("variant", "vm"));
		metricsFromConstraintProblem.add(new MetricDTO("hw", "cpu"));

		Map<String, Double> result = pmFacade.callPmPredictionText(data, "genome", variablesFromConstraintProblem, metricsFromConstraintProblem);
		log.info("!!!!!!!!  FINAL RESULT = {}", result);
		// just here to keep the VM up until all is logged
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
