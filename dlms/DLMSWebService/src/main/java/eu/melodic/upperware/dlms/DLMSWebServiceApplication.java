/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Application class for the webservice.
 */
@SpringBootApplication
@EnableConfigurationProperties(MelodicSecurityProperties.class)
@Slf4j
@AllArgsConstructor
public class DLMSWebServiceApplication {

	private final Environment env;

	/**
	 * Main method for starting. No arguments needed for normal use.
	 */
	public static void main(String[] args) {
		// To use eu.melodic.upperware.dlms.properties instead of application.properties
//		System.setProperty("spring.config.name", "eu.melodic.upperware.dlms");
		SpringApplication.run(DLMSWebServiceApplication.class, args);
	}

	/**
	 * CommandLineRunner to fill the Database with sample data directly after
	 * startup.
	 *
	 * @param dsRepository JPA repository, injected by Spring
	 */
	@Bean
	public CommandLineRunner setup(DataSourceRepository dsRepository) {
		return args -> {
			// TODO remove sample data before go-live
			log.info("Alluxio master is located at " + env.getProperty("alluxio.master.address"));

			// this is test
//			dsRepository.save(new DataSource("DS1", DataSourceType.HDFS, "http://master:9000/", "/melodic/ds1"));
//			dsRepository.save(new DataSource("DS2", DataSourceType.S3, "s3a://bucketferox/", "/melodic/ds2"));
//			log.info("Sample data sources added");
		};
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
