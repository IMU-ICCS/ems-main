package eu.melodic.upperware.dlm.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.Banner;
import static java.lang.System.exit;

import java.util.Arrays;

//@SpringBootApplication
//public class DataMonitor implements CommandLineRunner {
//
//	@Autowired
//	private MonitorService monitorService;
//
//	@Override
//	public void run(String... args) {
//		System.out.println(this.monitorService.getMessage());
//		if (args.length > 0 && args[0].equals("exitcode")) {
//			throw new ExitException();
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(DataMonitor.class, args);
//	}
//
//}

@SpringBootApplication
public class DataMonitor implements CommandLineRunner {

	@Autowired
	private MonitorService monitorService;

	public static void main(String[] args) throws Exception {

		// disabled banner, don't want to see the spring logo
		SpringApplication app = new SpringApplication(DataMonitor.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}

	// Put your logic here.
	@Override
	public void run(String... args) throws Exception {
		 // Do something...

		if (args.length > 0) {
			System.out.println(monitorService.getMessage(args[0].toString()));
		} else {
			System.out.println(monitorService.getMessage());
		}
		exit(0);
	}

}