/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.broker;

import eu.melodic.event.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.inteceptor.MessageInterceptor;
import org.apache.activemq.broker.inteceptor.MessageInterceptorRegistry;
import org.apache.activemq.command.Message;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SourceAddressMessageUpdateInterceptor implements MessageInterceptor {
    private final MessageInterceptorRegistry registry;
    private final String sourceAddressPropertyName = "producer-host";

    public SourceAddressMessageUpdateInterceptor(final MessageInterceptorRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void intercept(ProducerBrokerExchange producerBrokerExchange, Message message) {
        log.debug("SourceAddressMessageUpdateInterceptor:  Message: {}", message);
        try {
            // get remote address from connection
            Connection conn = producerBrokerExchange.getConnectionContext().getConnection();
            log.debug("SourceAddressMessageUpdateInterceptor:  Connection: {}", conn);
            String address = conn.getRemoteAddress();
            log.debug("SourceAddressMessageUpdateInterceptor:  Producer address: {}", address);

            // extract remote host address
            if (StringUtils.isNotBlank(address)) {
                address = StringUtils.substringsBetween(address, "//", ":") [0];
            }
            log.debug("SourceAddressMessageUpdateInterceptor:  Producer host: {}", address);

            // check if host address is local
            boolean isLocal = StringUtils.isBlank(address) || NetUtil.isLocalAddress(address.trim());
            if (isLocal) {
                log.debug("SourceAddressMessageUpdateInterceptor:  Producer host is local. Getting our public IP address");
                address = NetUtil.getPublicIpAddress();
                log.debug("SourceAddressMessageUpdateInterceptor:  Producer host (public): {}", address);
            } else {
                log.debug("SourceAddressMessageUpdateInterceptor:  Producer host is not local. Ok");
            }

            // get message remote address old value (if any)
            String oldAddress = (String) message.getProperty(sourceAddressPropertyName);
            log.debug("SourceAddressMessageUpdateInterceptor:  Producer host property in message: {}", oldAddress);

            // set new remote address value, if needed
            if (StringUtils.isBlank(oldAddress) && StringUtils.isNotBlank(address)) {
                log.debug("SourceAddressMessageUpdateInterceptor:  Setting producer host property in message: host={}, message={}", address, message);
                message.setProperty(sourceAddressPropertyName, address);
                log.debug("SourceAddressMessageUpdateInterceptor:  Set producer host property in message: host={}, message={}", address, message);
            }

            registry.injectMessage(producerBrokerExchange, message);

        } catch (Exception e) {
            log.error("SourceAddressMessageUpdateInterceptor:  EXCEPTION: ", e);
        }
    }
}
