package eu.melodic.upperware.utilitygenerator.facade.solver;

import eu.melodic.upperware.utilitygenerator.facade.solver.event.CamundaExternalCallFinishedEvent;
import eu.morphemic.facade.AbstractTextResponderFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class CamundaFacadeImpl extends AbstractTextResponderFacade implements ApplicationListener<CamundaExternalCallFinishedEvent> {

	protected static final String REQUEST_TOPIC = "camunda_ug_request";
	protected static final String REPLY_TOPIC = "camunda_ug_reply";

	protected static final String CAMEL_MODEL_FILE_PATH_PROPERTY_NAME = "applicationId";
	protected static final String CP_MODEL_FILE_PATH_PROPERTY_NAME = "cdoResourcePath";
	protected static final String READ_FROM_FILE_PROPERTY_NAME = "readFromFile";
	protected static final String NODE_CANDIDATES_FILE_PATH_PROPERTY_NAME = "nodeCandidatesFilePath";

	protected static final String PROCESS_ID = "processId";

	private final FacadeUtilityService utilityService;

	@Autowired
	public CamundaFacadeImpl(FacadeUtilityService utilityService) {
		super(REQUEST_TOPIC, REPLY_TOPIC);
		this.utilityService = utilityService;
	}

	public void run() {
		listenForRequests();
	}

	@Override
	public Map<String, Object> callExternalWithResult(Map<String, Object> data) {
		return Collections.emptyMap();
	}

	@Override
	public void callExternal(Map<String, Object> data) {
		log.info("incoming data: {}", data);

		// TODO remove
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		utilityService.processCamundaRequest(data);
	}

	@Override
	public void onApplicationEvent(CamundaExternalCallFinishedEvent externalCallFinishedEvent) {
		Map<String, Object> resultData = externalCallFinishedEvent.getData();
		log.info("event data: {}", resultData);
		sendReply(resultData);
	}
}
