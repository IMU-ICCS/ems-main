/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */



package e.melodic.upperware.dlms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import e.melodic.upperware.dlms.DataSource;
import e.melodic.upperware.dlms.DataSourceRepository;
import e.melodic.upperware.dlms.DataSourceType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DLMSWebServiceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(DLMSWebServiceApplication.class);
	
	@Autowired
	private Environment env;
	

	public static void main(String[] args) {
		SpringApplication.run(DLMSWebServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner setup(DataSourceRepository dsRepository) {
		return (args) -> {
			logger.info("Alluxio master is located at "+env.getProperty("alluxio.master.address"));
			dsRepository.save(new DataSource("DS1",DataSourceType.HDFS,"/melodic/DS1","http://master:19998/"));
			dsRepository.save(new DataSource("DS2",DataSourceType.S3,"/melodic/DS2","s3a://ferozbucket/"));
			logger.info("Sample data sources added");
		};
	}
}
