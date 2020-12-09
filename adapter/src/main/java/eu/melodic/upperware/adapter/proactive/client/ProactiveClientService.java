package eu.melodic.upperware.adapter.proactive.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProactiveClientService {

    //private final PAGateway paGateway;

    public ProactiveClientService() {
        Configuration config = new BaseConfiguration();
        try {
            // load ProActive configuration
            config = ProactiveClientConfiguration.loadPAConfiguration();
            log.info("in ProactiveClientService");
            throw new ConfigurationException("test");
        } catch (ConfigurationException ce) {
            log.error("ERROR: " + ce.toString());
        }
        //paGateway = new PAGateway(config.getString(ProactiveClientConfiguration.REST_URL));

    }
}
