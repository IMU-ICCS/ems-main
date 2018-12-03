/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerutil;

import eu.melodic.event.brokerutil.properties.BrokerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class BrokerConsumer implements MessageListener, InitializingBean {
	@Autowired
	private BrokerProperties properties;
	@Autowired
	private BrokerConfig brokerConfig;
	@Autowired
	private ActiveMQConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;
	private Set<String> addedDestinations = new HashSet<String>();
	
	@Override
	public void afterPropertiesSet() {
		initialize();
	}
	
	protected void initialize() {
		log.debug("BrokerConsumer.init(): Initializing Broker-CEP consumer instance...");
		try {
			connection = (properties.getBrokerUsername()!=null)
					? connectionFactory.createConnection( properties.getBrokerUsername(), properties.getBrokerPassword() )
					: connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			log.debug("BrokerConsumer.init(): Initializing Broker-CEP consumer instance... done");
		} catch (Exception ex) {
			log.error("BrokerConsumer.init(): EXCEPTION: ", ex);
		}
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			log.trace("BrokerConsumer.onMessage(): {}", message);
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage mesg = (ActiveMQObjectMessage) message;
				ActiveMQDestination messageDestination = mesg.getDestination();
				log.debug("BrokerConsumer.onMessage(): Message received: source={}, payload={}",
					messageDestination.getPhysicalName(), mesg.getObject());
				
				// Send message to Esper
				if (mesg.getObject() instanceof Map) {
					/*(Map<String,Object>)mesg.getObject()
					messageDestination.getPhysicalName()*/
				} else {
					//mesg.getObject()
				}
			} else
			if (message instanceof ActiveMQTextMessage) {
				ActiveMQTextMessage mesg = (ActiveMQTextMessage) message;
				ActiveMQDestination messageDestination = mesg.getDestination();
				log.debug("BrokerConsumer.onMessage(): Message received: source={}, payload={}, mime={}",
					messageDestination.getPhysicalName(), mesg.getText(), mesg.getJMSXMimeType());
				
				// Send message to Esper
				/*mesg.getText()
				messageDestination.getPhysicalName()*/
			} else {
				log.warn("BrokerConsumer.onMessage(): Message ignored: type={}", message.getClass().getName());
			}
		} catch (Exception ex) {
			log.error("BrokerConsumer.onMessage(): EXCEPTION: ", ex);
		}
	}
}