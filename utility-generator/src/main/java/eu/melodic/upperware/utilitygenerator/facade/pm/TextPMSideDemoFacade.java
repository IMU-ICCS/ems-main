package eu.melodic.upperware.utilitygenerator.facade.pm;

import eu.morphemic.facade.AbstractTextRequesterFacade;
import eu.morphemic.facade.AbstractTextResponderFacade;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Demo facade implementation playing the PM side.
 */
@Slf4j
public class TextPMSideDemoFacade extends AbstractTextResponderFacade {

	public TextPMSideDemoFacade() {
		super(PMFacade.PM_PREDICT_REQUEST_TOPIC, PMFacade.PM_PREDICT_REPLY_TOPIC);
	}

	/**
	 * Method to start listening for incoming messages.
	 */
	public void run() {
		listenForRequestsAndDoReply();
	}

	@Override
	public Map<String, Object> callExternalWithResult(Map<String, Object> data) {
		log.info("Enter PM code -------------------------------------------------------------------------------------------");
		int dispatcherCores = (int) (data.get("dispatcherCores") == null ? 0 : data.get("dispatcherCores"));
		int dispatcherRam = (int) (data.get("dispatcherRam") == null ? 0 : data.get("dispatcherCores"));
		int sumDispatcher = dispatcherCores + dispatcherRam;
		log.info("Calculated value dispatcher = {}", sumDispatcher);

		int workerCores = (int) (data.get("workerCores") == null ? 0 : data.get("workerCores"));
		int workerRam = (int) (data.get("workerRam") == null ? 0 : data.get("workerRam"));
		int sumWorker = workerCores + workerRam;
		int workerMulti = (int) (data.get("workerMulti") == null ? 0 : data.get("workerMulti"));
		double resultWorker = sumWorker * workerMulti;
		log.info("Calculated value worker = {}", resultWorker);

		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		Map<String, Object> innerResultMap = new HashMap<>();
		innerResultMap.put("dispatcher", sumDispatcher);
		innerResultMap.put("worker", resultWorker);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("results", innerResultMap);
		resultMap.put("status", false);
		resultMap.put("ml", Collections.emptyMap());
		resultMap.put("message", "some text...");
		resultMap.put(AbstractTextRequesterFacade.SENDER_ID_PROPERTY_NAME, data.get(AbstractTextRequesterFacade.SENDER_ID_PROPERTY_NAME));
		log.info("Exit PM code -------------------------------------------------------------------------------------------");
		return resultMap;
	}

	@Override
	public void callExternal(Map<String, Object> data) {

	}

	/**
	 * Main method for testing.
	 */
	public static void main(String[] args) {
		TextPMSideDemoFacade pmFacade = new TextPMSideDemoFacade();
		pmFacade.run();
	}

}
