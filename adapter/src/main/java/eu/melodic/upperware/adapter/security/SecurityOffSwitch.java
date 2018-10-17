package eu.melodic.upperware.adapter.security;

import eu.paasage.upperware.security.authapi.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
@ConditionalOnProperty(value = SecurityConstants.MELODIC_SECURITY_ENABLED_PROPERTY, havingValue = "false")
public class SecurityOffSwitch {

    public SecurityOffSwitch() {
        log.info("Running WITHOUT security");
    }
}
