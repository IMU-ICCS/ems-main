package eu.paasage.executionware.metric_collector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
@EnableAsync
@ComponentScan(basePackages = {"eu.paasage.executionware.metric_collector"})
public class MetricsCollectorRestApp {


    public static void main(String[] args) {

        log.info("MetricsCollectorRestApp is starting...");

        SpringApplication.run(MetricsCollectorRestApp.class, args);

        TimeZone aDefault = TimeZone.getDefault();
        log.info("ID: {}, displayName: {}", aDefault.getID(), aDefault.getDisplayName());

        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        log.info("Timestamp {}, Date for pattern '{}' is {}", System.currentTimeMillis(), pattern, new SimpleDateFormat(pattern).format(new Date()));
        log.info("MetricsCollectorRestApp started...");
    }
}


