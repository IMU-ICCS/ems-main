package eu.melodic.upperware.dlm.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MonitorService {
	// Simple example shows how a command line spring application can execute an
	// injected bean service. Also demonstrates how you can use @Value to inject
	// command line args ('--name=whatever') or application properties

	 @Value("${name:Monitoring}")
	    private String name;

	    public String getMessage() {
	        return getMessage(name);
	    }

	    public String getMessage(String name) {
	        return "DLMS " + name;
	    }

	}
