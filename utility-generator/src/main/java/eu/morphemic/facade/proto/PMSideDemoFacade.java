package eu.morphemic.facade.proto;

import eu.melodic.upperware.utilitygenerator.facade.pm.PMFacade;
import eu.morphemic.facade.proto.test.CallPmPrediction;
import eu.morphemic.facade.proto.test.CallPmPredictionResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PMSideDemoFacade extends AbstractResponderFacade<CallPmPrediction, CallPmPredictionResponse> {

	public PMSideDemoFacade() {
		super(PMFacade.PM_PREDICT_REQUEST_TOPIC, PMFacade.PM_PREDICT_REPLY_TOPIC, CallPmPrediction.class, CallPmPredictionResponse.class);
	}

	public void run() {
		listenForRequests();
	}

	@Override
	public Map<String, Object> callExternal(CallPmPrediction data) {
		log.info("!!!!!!!!  Enter PM code -------------------------------------------------------------------------------------------");
		int sum = data.getDispatcherCores() + data.getDispatcherRam() + data.getWorkerCores() + data.getWorkerRam();
		double result = sum * data.getWorkerMulti();
		log.info("!!!!!!!!  Calculated value = " + result);

		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("utilityValue", result);
		resultMap.put("senderID", data.getSenderID());
		log.info("!!!!!!!!  Exit PM code -------------------------------------------------------------------------------------------");
		return resultMap;
	}

	public static void main(String[] args) {
		PMSideDemoFacade pmFacade = new PMSideDemoFacade();
		pmFacade.run();
	}

}
