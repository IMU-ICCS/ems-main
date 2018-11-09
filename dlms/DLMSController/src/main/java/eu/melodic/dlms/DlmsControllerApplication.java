package eu.melodic.dlms;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import eu.melodic.dlms.algorithms.latencyBandwidth.repository.DataCenterLatencyBandwidthRepository;
import eu.melodic.dlms.algorithms.latencyBandwidth.repository.DataCenterRepository;
import eu.melodic.dlms.algorithms.latencyBandwidth.repository.TwoDataCenterCombinationRepository;

/**
 * Application class for the DLMS controller.
 */
@SpringBootApplication
public class DlmsControllerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsControllerApplication.class);

	private static final int DELAY_AFTER_CREATION = 1000;

	@Autowired
	private DlmsProperties dlmsProperties;

	@Autowired
	private DlmsRestController restController;

	@Autowired
	private DataCenterRepository dataCenterRepository;
	@Autowired
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;
	@Autowired
	private TwoDataCenterCombinationRepository twoDataCenterCombinationRepository;

	/**
	 * Main method for starting. No arguments needed for normal use.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DlmsControllerApplication.class, args);
	}

	/**
	 * CommandLineRunner to start a timer for every algorithm in the configuration
	 * directly after startup.
	 */
	@Bean
	public CommandLineRunner run() {
		return (String... args) -> {
			List<Algorithm> algorithms = dlmsProperties.getAlgorithms();

			for (Algorithm algo : algorithms) {
				AlgorithmRunner runnerInstance = prepareRunnerInstance(algo);

				restController.registerAlgorithm(algo, runnerInstance);

				runnerInstance.initialize(this);

				TimerTask timerTask = setupTimerTask(algo, runnerInstance);

				Timer timer = new Timer();
				timer.schedule(timerTask, DELAY_AFTER_CREATION, (long) (algo.getInterval() * 1000));
				LOGGER.info("Started timer with delay=" + DELAY_AFTER_CREATION / 1000 + " sec. and interval={} sec.",
						algo.getInterval());
			}
		};
	}

	private AlgorithmRunner prepareRunnerInstance(Algorithm algo) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		LOGGER.info("preparing algorithm {}", algo.getName());

		@SuppressWarnings("unchecked")
		Class<AlgorithmRunner> runnerClass = (Class<AlgorithmRunner>) Class.forName(algo.getClassName());
		algo.setRunnerClass(runnerClass);

		return runnerClass.getDeclaredConstructor().newInstance();
	}

	private TimerTask setupTimerTask(Algorithm algo, AlgorithmRunner runnerInstance) {
		return new TimerTask() {
			@Override
			public void run() {
				LOGGER.info("running algorithm {}", algo.getName());
				int result = runnerInstance.update(algo.getArguments());

				if (result != 0) {
					LOGGER.info("error occured in algorithm {} ", algo.getName());
				}
			}
		};
	}

	public DataCenterRepository getDataCenterRepository() {
		return dataCenterRepository;
	}

	public DataCenterLatencyBandwidthRepository getDataCenterLatencyBandwidthRepository() {
		return dataCenterLatencyBandwidthRepository;
	}

	public TwoDataCenterCombinationRepository getTwoDataCenterCombinationRepository() {
		return twoDataCenterCombinationRepository;
	}

}
