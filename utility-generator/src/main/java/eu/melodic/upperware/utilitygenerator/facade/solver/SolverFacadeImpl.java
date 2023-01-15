package eu.melodic.upperware.utilitygenerator.facade.solver;

import eu.melodic.upperware.utilitygenerator.facade.solver.event.SolverExternalCallFinishedEvent;
import eu.morphemic.facade.AbstractTextRequesterFacade;
import eu.morphemic.facade.AbstractTextResponderFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SolverFacadeImpl extends AbstractTextResponderFacade implements ApplicationListener<SolverExternalCallFinishedEvent> {

	protected static final String REQUEST_TOPIC = "solver_ug_request";
	protected static final String REPLY_TOPIC = "solver_ug_reply";

	protected static final String SOLUTION_PROPERTY_NAME = "variables";
	protected static final String TARGET_PROPERTY_NAME = "target";
	protected static final String UTILITY_VALUE_PROPERTY_NAME = "utilityValue";

	private final FacadeUtilityService utilityService;

	@Autowired
	public SolverFacadeImpl(FacadeUtilityService utilityService) {
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

		utilityService.processSolverRequest(data);
	}

	@Override
	public void onApplicationEvent(SolverExternalCallFinishedEvent externalCallFinishedEvent) {
		Map<String, Object> resultData = externalCallFinishedEvent.getData();
		log.info("event data: {}", resultData);

		Map<String, Object> result = new HashMap<>();
		result.put(AbstractTextRequesterFacade.SENDER_ID_PROPERTY_NAME, resultData.get(AbstractTextRequesterFacade.SENDER_ID_PROPERTY_NAME));

		final Double utilityValue = (Double) resultData.get(UTILITY_VALUE_PROPERTY_NAME);
		if(utilityValue == null) { // return error message
			result.put(FacadeUtilityService.RESULT_PROPERTY_NAME, resultData.get(FacadeUtilityService.RESULT_PROPERTY_NAME));
		}
		else { // return utility value
			result.put(UTILITY_VALUE_PROPERTY_NAME, utilityValue);
		}
		log.info("UG finished event processed. Result data: {}", resultData);
		sendReply(result);
	}
}
