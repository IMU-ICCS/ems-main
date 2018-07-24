/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.security.server.security;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class WebSecurityContext {

    @Bean
    public MelodicSecurityProperties melodicSecurityProperties() {
        return new MelodicSecurityProperties();
    }

}
