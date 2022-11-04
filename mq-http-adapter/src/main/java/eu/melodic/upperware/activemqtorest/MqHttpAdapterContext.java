package eu.melodic.upperware.activemqtorest;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import eu.melodic.upperware.activemqtorest.proactive.ProactiveClientServiceForMqHttpAdapter;
import eu.melodic.upperware.activemqtorest.proactive.ProactiveClientServiceForMqHttpAdapterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * 2019 CAS Software AG
*/

@Configuration
public class MqHttpAdapterContext {

    @Bean
    public ProactiveClientServiceForMqHttpAdapter proactiveClientServiceForMqHttpAdapter(ProactiveClientConnectorService proactiveClientConnectorService) {
        return new ProactiveClientServiceForMqHttpAdapterImpl(proactiveClientConnectorService);
    }
}
