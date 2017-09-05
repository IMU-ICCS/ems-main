package eu.paasage.upperware.profiler.generator.db;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;

/**
 * This interface defines the services offered by a database. 
 * @author danielromero
 *
 */
public interface IDatabaseProxy {

	/**
	 * Retrieves the camel model with the specified path
	 * @param modelPath The model path
	 * @return The camel model
	 */
	CamelModel getCamelModel(String modelPath);

	ProviderModel loadPM(String cloud);

	ProviderModel loadPM(String appId, String providerId);

	ProviderModel loadPM(String appId, String providerId, String vmId);


	FunctionTypes loadFunctionTypes();

	OperatingSystems loadOperatingSystems();

	Locations loadLocations();

	ProviderTypes loadProviderTypes();

	void saveModels(PaasageConfiguration pc, ConstraintProblem cp);

	void savePM(ProviderModel pm, String paasageConfigurationId, String providerId);

}
