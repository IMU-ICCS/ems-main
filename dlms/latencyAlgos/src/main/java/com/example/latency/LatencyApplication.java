package com.example.latency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.latency.Runner.Generator;
import com.example.latency.controller.GeneratorController;
import com.example.latency.controller.LatencyController;
import com.example.latency.extras.GetPropertyValues;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class LatencyApplication {

	@Autowired // to run from the same app
	LatencyController latencyController;
	@Autowired
	GeneratorController generatorController;
	@Autowired
	Generator generator;
	private static GetPropertyValues propValues = new GetPropertyValues(); // store config

	public static void main(String[] args) {
		SpringApplication.run(LatencyApplication.class, args);
	}

	// To run multiple threads
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return new CommandLineRunner() {
			public void run(String... args) throws Exception {
				simulateDataDatabase();

				// initially insert the config values
				generatorController.setPropValues(propValues);
				latencyController.setPropValues(propValues);

				generatorController.run(); // insert and purge old data
				latencyController.run(); // create data structure
			}
		};
	}

	// read the config properties
	public static void simulateDataDatabase() {
		propValues.readValues();
	}

	/*
	 * // do something after the spring boot starts if only one thread
	 * 
	 * @EventListener(ApplicationReadyEvent.class) public void
	 * doSomethingAfterStartup() { // Optional<CloudProvider> cloudProvider =
	 * cloudProviderRepository.findById((long) 1); // if (cloudProvider.isPresent())
	 * { // CloudProvider cloudProvider2 = cloudProvider.get(); //
	 * System.out.println(cloudProvider2.getName()); // }
	 * 
	 * latencyController.run(); }
	 * 
	 * 
	 */
}
