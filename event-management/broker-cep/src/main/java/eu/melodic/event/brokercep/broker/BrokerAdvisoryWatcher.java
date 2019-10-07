/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.broker;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.*;

@Service
@Slf4j
public class BrokerAdvisoryWatcher implements MessageListener, InitializingBean {
	@Autowired
	private BrokerService brokerService;	// Added in order to ensure that BrokerService will be instantiated first
	@Autowired
	private ActiveMQConnectionFactory connectionFactory;

	private Connection connection;
	private Session session;

	@Override
	public void afterPropertiesSet() {
		initialize();
	}
	
	protected void initialize() {
		log.debug("BrokerAdvisoryWatcher.init(): Initializing instance...");
		try {
			this.connection = connectionFactory.createConnection();
			this.connection.start();
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("ActiveMQ.Advisory.>");
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener( this );
			log.debug("BrokerAdvisoryWatcher.init(): Initializing instance... done");
		} catch (Exception ex) {
			log.error("BrokerAdvisoryWatcher.init(): EXCEPTION: ", ex);
		}
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			log.trace("BrokerAdvisoryWatcher.onMessage(): {}", message);
			ActiveMQMessage mesg = (ActiveMQMessage) message;
			ActiveMQDestination messageDestination = mesg.getDestination();
			log.trace("BrokerAdvisoryWatcher.onMessage(): advisory-message-source={}", messageDestination);
			
			DataStructure ds = mesg.getDataStructure();
			log.trace("BrokerAdvisoryWatcher.onMessage(): advisory-message-data-structure={}", ds==null ? null : ds.getClass().getSimpleName());
			if (ds!=null) {
				// Advisory event
				processAdvisoryMessage(ds);
			} else {
				// Non-advisory event
				processPlainMessage(mesg);
			}
		} catch (Exception ex) {
			log.error("BrokerAdvisoryWatcher.onMessage(): EXCEPTION: ", ex);
		}
	}

	private void processPlainMessage(ActiveMQMessage mesg) throws JMSException {
		if (mesg instanceof TextMessage) {
			TextMessage txtMesg = (TextMessage) mesg;
			String topicName = mesg.getDestination().getPhysicalName();
			log.info("BrokerAdvisoryWatcher.onMessage(): Text Message received: topic={}, message={}", topicName, txtMesg.getText());
		} else {
			String topicName = mesg.getDestination().getPhysicalName();
			log.info("BrokerAdvisoryWatcher.onMessage(): Non-text Message received: topic={}, type={}", topicName, mesg.getClass().getName());
		}
	}

	private void processAdvisoryMessage(DataStructure ds) throws JMSException {
		if (ds instanceof DestinationInfo) {
			DestinationInfo info = (DestinationInfo) ds;
			ActiveMQDestination destination = info.getDestination();
			boolean isAdd = info.isAddOperation();
			boolean isDel = info.isRemoveOperation();
			log.debug("BrokerAdvisoryWatcher.onMessage(): Received a DestinationInfo message: destination={}, is-queue={}, is-topic={}, is-add={}, is-del={}",
					destination, destination.isQueue(), destination.isTopic(), isAdd, isDel);

			// Subscribe to topic
			if (isAdd) {
				String topicName = destination.getPhysicalName();
				log.info("BrokerAdvisoryWatcher.onMessage(): Subscribing to topic: {}", topicName);

				MessageConsumer consumer = session.createConsumer(destination);
				consumer.setMessageListener(this);
			}
			/*if (isDel) {
				String topicName = destination.getPhysicalName();
				log.info("BrokerAdvisoryWatcher.onMessage(): Leaving topic: {}", topicName);
			}*/

		} else {
			log.trace("BrokerAdvisoryWatcher.onMessage(): Message ignored");
		}
	}
}