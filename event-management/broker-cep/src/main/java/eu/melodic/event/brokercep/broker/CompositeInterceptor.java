/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.broker;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.inteceptor.MessageInterceptor;
import org.apache.activemq.broker.inteceptor.MessageInterceptorRegistry;
import org.apache.activemq.command.Message;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CompositeInterceptor implements MessageInterceptor {
    private final List<MessageInterceptor> interceptors = new ArrayList<>();

    public CompositeInterceptor(final MessageInterceptorRegistry registry, ApplicationContext applicationContext) {
        /*BrokerCepProperties brokerCepProperties = applicationContext.getBean(BrokerCepProperties.class);
        List<String> interceptorClasses = brokerCepProperties.getMessageInterceptors();
        log.debug("CompositeInterceptor: interceptor-classes: {}", interceptorClasses);
        if (interceptorClasses!=null) {
            interceptorClasses.forEach(ic -> {
                String[] part = ic.split(":", 2);
                String clazz = part[1];
                log.info("CompositeInterceptor: Initializing interceptor: {}", clazz);
                MessageInterceptor newInterceptor = null;
                try {
                    newInterceptor = (MessageInterceptor) Class.forName(ic)
                            .getConstructor(MessageInterceptorRegistry.class, ApplicationContext.class)
                            .newInstance(registry, applicationContext);
                } catch (Exception ignored) { }
                if (newInterceptor==null) {
                    try {
                        newInterceptor = (MessageInterceptor) Class.forName(ic)
                                .getConstructor(MessageInterceptorRegistry.class)
                                .newInstance(registry);
                    } catch (Exception e) {
                        throw new RuntimeException("Interceptor initialization exception", e);
                    }
                }
                interceptors.add(newInterceptor);
                log.info("CompositeInterceptor: Interceptor initialized: {}", ic);
            });
        }*/
        interceptors.add(new SourceAddressMessageUpdateInterceptor(registry));
        interceptors.add(new MessageForwarderInterceptor(registry, applicationContext));
    }

    @Override
    public void intercept(ProducerBrokerExchange producerBrokerExchange, Message message) {
        log.debug("CompositeInterceptor:  Message: {}", message);
        interceptors.forEach(
                interceptor -> interceptor.intercept(producerBrokerExchange, message));
    }
}
