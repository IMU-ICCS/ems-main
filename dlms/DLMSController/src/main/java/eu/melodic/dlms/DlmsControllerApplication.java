package eu.melodic.dlms;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceAffinityRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.DataCenterRepository;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import eu.melodic.dlms.db.repository.ControllerDataSourceRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import eu.melodic.dlms.db.repository.TwoDataCenterCombinationRepository;
import eu.melodic.dlms.db.repository.TwoDataCentersRepository;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
/**
 * Application class for the DLMS controller.
 */
@SpringBootApplication
@EnableConfigurationProperties(MelodicSecurityProperties.class)
@Slf4j
@AllArgsConstructor
@Getter
public class DlmsControllerApplication {
	@Getter(AccessLevel.NONE) 
	private static final int DELAY_AFTER_CREATION = 1000;

	private final DlmsProperties dlmsProperties;
	private final DlmsRestController restController;
	private final DataCenterRepository dataCenterRepository;
	private final TwoDataCentersRepository twoDataCentersRepository;
	private final TwoDataCenterCombinationRepository twoDataCenterCombinationRepository;
	private final DataCenterZoneRepository dataCenterZoneRepository;
	private final ApplicationComponentRepository acRepository;
	private final ControllerDataSourceRepository dsRepository;
	private final ApplicationComponentDataSourceDataRepository acDsDataRepository;
	private final ApplicationComponentDataSourceAffinityRepository acDsAffinityRepository;
	private final CloudProviderRepository cpRepository;
	private final RegionRepository regionRepository;
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
//				Thread.sleep(2000);
				restController.registerAlgorithm(algo, runnerInstance);

				runnerInstance.initialize(this);

				TimerTask timerTask = setupTimerTask(algo, runnerInstance);

				Timer timer = new Timer();
				timer.schedule(timerTask, DELAY_AFTER_CREATION, (long) (algo.getInterval() * 1000));
				log.debug("Started timer with delay = {} sec. and interval = {} sec.", DELAY_AFTER_CREATION / 1000,
						algo.getInterval());
			}
		};
	}

	private AlgorithmRunner prepareRunnerInstance(Algorithm algo) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		log.debug("preparing algorithm {}", algo.getName());

		@SuppressWarnings("unchecked")
		Class<AlgorithmRunner> runnerClass = (Class<AlgorithmRunner>) Class.forName(algo.getClassName());
		algo.setRunnerClass(runnerClass);

		return runnerClass.getDeclaredConstructor().newInstance();
	}

	private TimerTask setupTimerTask(Algorithm algo, AlgorithmRunner runnerInstance) {
		return new TimerTask() {
			@Override
			public void run() {
				log.debug("running algorithm {}", algo.getName());
				int result = runnerInstance.update(algo.getArguments());

				if (result != 0) {
					log.debug("error occured in algorithm {} ", algo.getName());
				}
			}
		};
	}

}
