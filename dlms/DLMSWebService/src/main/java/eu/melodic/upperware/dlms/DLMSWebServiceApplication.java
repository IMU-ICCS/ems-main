/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import alluxio.conf.AlluxioConfiguration;
import alluxio.conf.InstancedConfiguration;
import alluxio.conf.PropertyKey;
import eu.melodic.upperware.dlms.properties.DLMSDataSourceAccess;
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
@ComponentScan(basePackages = {"eu.paasage.upperware.dlms"})
public class DLMSWebServiceApplication {

	private final Environment env;
	private final DLMSServiceImpl dlmsService;
	private final DLMSDataSourceAccess dlmsDsAccess;
	private final InstancedConfiguration conf;
	

	/**
	 * Main method for starting. No arguments needed for normal use.
	 */
	public static void main(String[] args) {
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
			log.debug("Alluxio master is located at " + env.getProperty("alluxio.master.address"));
			// set master hostname		
			conf.set(PropertyKey.MASTER_HOSTNAME, env.getProperty("alluxio.master.hostname"));
			
			log.debug("Master host name is"  + conf.get(PropertyKey.MASTER_HOSTNAME));
			
			log.debug("Master host name that we set above is ", env.getProperty("alluxio.master.hostname"));
			// store user authentication in a hashmap for later use
			dlmsDsAccess.getDataSource().computeAccount();
			// this is test
//			dsRepository.save(new DataSource("DS1", DataSourceType.HDFS, "http://master:9000/", "/melodic/ds1"));
//			dsRepository.save(new DataSource("DS2", DataSourceType.S3, "s3a://bucketferox/", "/melodic/ds2"));
		};
	}

	/**
	 * On termination all the mounted storage should be removed This should not
	 * happen during one Application lifetime in Melodic. But it is useful for
	 * testing.
	 */
	@PreDestroy
	public void onExit() {
		log.debug("terminated and deletng data sources now");
		List<DataSource> dsList = dlmsService.getAllDataSources();
		for (DataSource ds : dsList) {
			dlmsService.deleteByName(ds.getName());
		}
		log.debug("Unmounted and deleted the data sources from the database");
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
