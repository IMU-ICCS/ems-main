package eu.morphemic.facade.proto;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.facade.pm.PMFacade;
import eu.morphemic.facade.proto.test.CallPmPrediction;
import eu.morphemic.facade.proto.test.CallPmPredictionResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PMFacadeImpl extends AbstractRequesterFacade<CallPmPrediction, CallPmPredictionResponse> implements PMFacade {

	public PMFacadeImpl() {
		super(PM_PREDICT_REQUEST_TOPIC, PM_PREDICT_REPLY_TOPIC, CallPmPrediction.class, CallPmPredictionResponse.class);
	}

	@Override
	public Double callPmPrediction(Collection<VariableValueDTO> solution) {
		Map<String, Object> data = new HashMap<>();

		for(VariableValueDTO dto : solution) {
			if("dispatcherLocation".equals(dto.getName()) || "workerLocation".equals(dto.getName())) {
				data.put(dto.getName(), transformLocationToStringValue(dto.getValue()));
			}
			else {
				data.put(dto.getName(), dto.getValue());
			}
		}

		CallPmPredictionResponse result = sendRequest(data);
		return result.getUtilityValue();
	}

	@Override
	public Map<String, Double> callPmPredictionText(Collection<VariableValueDTO> solution, String applicationId, Collection<VariableDTO> variablesFromConstraintProblem, Collection<MetricDTO> metricsFromConstraintProblem) {
		return null;
	}

	private String transformLocationToStringValue(Number value) {
		if(value.intValue() == 200) {
			return "Somewhere";
		}
		if(value.intValue() == 201) {
			return "Somewhere else";
		}
		else {
			log.warn("Unknown location " + value);
			return "Unknown location";
		}
	}

	@Override
	public String getIdFromReply(CallPmPredictionResponse reply) {
		return reply.getSenderID();
	}

	public static void main(String[] args) {
		PMFacadeImpl pmFacade = new PMFacadeImpl();

		Collection<VariableValueDTO> data = new ArrayList<>();
		data.add(new VariableValueDTO("dispatcherCores", 2));
		data.add(new VariableValueDTO("dispatcherCores", 2));
		data.add(new VariableValueDTO("dispatcherRam", 4));
		data.add(new VariableValueDTO("dispatcherLocation", 200));
		data.add(new VariableValueDTO("workerCores", 4));
		data.add(new VariableValueDTO("workerRam", 8));
		data.add(new VariableValueDTO("workerLocation", 201));
		data.add(new VariableValueDTO("workerMulti", 100));

		Double result = pmFacade.callPmPrediction(data);
		log.info("!!!!!!!!  FINAL RESULT = " + result);
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
