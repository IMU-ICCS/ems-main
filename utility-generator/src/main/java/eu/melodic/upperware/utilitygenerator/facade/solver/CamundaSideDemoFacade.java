package eu.melodic.upperware.utilitygenerator.facade.solver;

import eu.morphemic.facade.AbstractTextRequesterFacade;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CamundaSideDemoFacade extends AbstractTextRequesterFacade {

	public CamundaSideDemoFacade() {
		super(CamundaFacadeImpl.REQUEST_TOPIC, CamundaFacadeImpl.REPLY_TOPIC);
	}

	public static void main(String[] args) {
		CamundaSideDemoFacade demoFacade = new CamundaSideDemoFacade();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(CamundaFacadeImpl.CAMEL_MODEL_FILE_PATH_PROPERTY_NAME, "utility-generator/src/main/test/resources/genom/Genomnew.xmi");
		parameters.put(CamundaFacadeImpl.CP_MODEL_FILE_PATH_PROPERTY_NAME, "utility-generator/src/main/test/resources/genom/GenomCPModel.xmi");
		parameters.put(CamundaFacadeImpl.READ_FROM_FILE_PROPERTY_NAME, true);
		parameters.put(CamundaFacadeImpl.NODE_CANDIDATES_FILE_PATH_PROPERTY_NAME, "utility-generator/src/main/test/resources/nodeCandidates.json");

		demoFacade.sendRequest(parameters);
	}
}
