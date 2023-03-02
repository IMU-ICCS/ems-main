package eu.melodic.upperware.utilitygenerator.facade.pm;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for the communication between UtilityGenerator and Performance Module.
 */
public interface PMFacade {

	String PM_PREDICT_REQUEST_TOPIC = "performance_module_predict";
	String PM_PREDICT_REPLY_TOPIC = "performance_module_predict_feedback";


	Double callPmPrediction(Collection<VariableValueDTO> solution);

	/**
	 * Calls the predict functionality of Performance Module via the facade.
	 * @param solution Set of metrics to be included.
	 * @param applicationId
     * @param variablesFromConstraintProblem
* @param metricsFromConstraintProblem
* @return Map containing the performance estimation per feature.
	 */
	Map<String, Double> callPmPredictionText(Collection<VariableValueDTO> solution, String applicationId, Collection<VariableDTO> variablesFromConstraintProblem, Collection<MetricDTO> metricsFromConstraintProblem);

}
