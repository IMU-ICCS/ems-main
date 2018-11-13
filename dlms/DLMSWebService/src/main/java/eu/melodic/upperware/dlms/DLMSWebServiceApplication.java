/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import eu.melodic.upperware.dlms.camel.ModelAnalyzer;

/**
 * Application class for the webservice.
 */
@SpringBootApplication
public class DLMSWebServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(DLMSWebServiceApplication.class);

	@Autowired
	private Environment env;

	@Autowired
	ModelAnalyzer modelAnalyzer;

	@Autowired
	DLMSServiceImpl dlmsService;

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
			logger.info("Alluxio master is located at " + env.getProperty("alluxio.master.address"));

			// this is test
			dsRepository.save(new DataSource("DS1", DataSourceType.HDFS, "http://master:9000/", "/melodic/ds1"));
			dsRepository.save(new DataSource("DS2", DataSourceType.S3, "s3a://bucketferox/", "/melodic/ds2"));
			logger.info("Sample data sources added");
		};
	}

//	@Bean
//	public CommandLineRunner run() {
//		return (String... args) -> {
//			System.out.println("started");
//			logger.info("Alluxio master is located at " + env.getProperty("alluxio.master.address"));
//			// to do: get the name of the model that was updated or added
//			String modelName = "PeopleFlow";
//			modelAnalyzer.readModel(modelName);
//			List<DataSource> dataSourceList = modelAnalyzer.getDataSourceList();
//
//			DLMSWebServiceClient dlmsclient = new DLMSWebServiceClient();
//			dlmsclient.addDatasource(dataSourceList.get(0));
//			System.out.println("finished");
//		};
//	}

}
