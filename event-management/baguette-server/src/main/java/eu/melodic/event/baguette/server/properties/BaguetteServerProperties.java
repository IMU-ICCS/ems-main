/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.properties;

import eu.passage.upperware.commons.passwords.IdentityPasswordEncoder;
import eu.passage.upperware.commons.passwords.PasswordEncoder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "baguette.server")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.baguette-server.properties")
@Slf4j
public class BaguetteServerProperties {
    @NotNull
    @Size(min = 1, message = "Please provide a valid Coordinator class (use Fully-Qualified Class Name)")
    @Value("${baguette.server.coordinator.class}")
    private String coordinatorClass;

    @Value("${baguette.server.registration-window:30000}")
    @Min(-1)
    private long registrationWindow;
    @Value("${baguette.server.num-of-instances:-1}")
    @Min(-1)
    private int numberOfInstances;
    @Value("${baguette.server.num-of-segments:-1}")
    @Min(-1)
    private int NumberOfSegments;

    @Value("#{ '${baguette.server.address}'!='' ? '${baguette.server.address}' : T(eu.melodic.event.util.NetUtil).getPublicIpAddress() }")
    private String serverAddress;
    @Value("${baguette.server.port:2222}")
    @Min(value = 1, message = "Valid server ports are between 1 and 65535. Please prefer ports higher than 1023.")
    @Max(value = 65535, message = "Valid server ports are between 1 and 65535. Please prefer ports higher than 1023.")
    private int serverPort;
    @Value("${baguette.server.key.file:hostkey.ser}")
    private String serverKeyFile;
    @Value("${baguette.server.heartbeat:false}")
    private boolean heartbeatEnabled;
    @Value("${baguette.server.heartbeat.period:60000}")
    @Min(-1)
    private long heartbeatPeriod;

    @Value("${baguette.server.debug.client-address-override-allowed:false}")
    private boolean clientAddressOverrideAllowed;
    @Value("${baguette.server.client-id-format}")
    private String clientIdFormat;
    @Value("${baguette.server.client-id-format.escape:~}")
    private String clientIdFormatEscape;

    private PasswordEncoder passwordEncoder = _createPasswordEncoder();
    private final CredentialsMap credentials = new CredentialsMap(passwordEncoder);

    private PasswordEncoder _createPasswordEncoder() {
        return new IdentityPasswordEncoder();
    }

    //XXX:TODO: Probably move inner class to melodic-commons
    /**
     *  HashMap with toString() method overidden in order to password encodes entry values.
     *  Used to store credentials
     */
    public static class CredentialsMap extends HashMap<String,String> {
        @Getter @Setter
        private PasswordEncoder passwordEncoder = new IdentityPasswordEncoder();

        public CredentialsMap(PasswordEncoder pe) { this.passwordEncoder = pe; }

        public String toString() {
            Map<String,String> temp = new HashMap<>();
            entrySet().stream().forEach(e -> temp.put(e.getKey(), passwordEncoder.encode(e.getValue())));
            return temp.toString();
        }
    }
}
