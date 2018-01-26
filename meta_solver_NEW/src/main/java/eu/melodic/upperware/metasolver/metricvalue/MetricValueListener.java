/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.metricvalue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import javax.jms.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetricValueListener implements MessageListener {
	
	private MessageConsumer consumer;
	private Topic topic;
	private TopicType type;
	private MetricValueRegistry<Object> registry;
	private Gson gson;
	
	public MetricValueListener(MessageConsumer consumer, Topic topic, TopicType type, MetricValueRegistry<Object> registry) {
		this.consumer = consumer;
		this.topic = topic;
		this.registry = registry;
		gson = new Gson();
	}
	
	public void onMessage(Message message) { 
		try {
			log.debug("Listener of topic {}: Received message: ", topic.getTopicName());
			if (message instanceof TextMessage) { 
				// Extract Topic name and payload from message
				TextMessage textMessage = (TextMessage) message; 
				String metricName = textMessage.getStringProperty("topic_name");
				String payload = textMessage.getText();
				log.debug("Metric: {}", metricName);
				log.debug("Payload:\n{}", payload);
				
				switch (type) {
					case MVV:
						log.debug("Listener of topic {}: Got an MVV event: ", topic.getTopicName());
						if (metricName!=null && ! (metricName=metricName.trim()).isEmpty()) {
							// Extract key-value pairs from message payload
							// ...using MetricValueEvent
							MetricValueEvent event = gson.fromJson(payload, MetricValueEvent.class);
							log.debug("EVENT : {}", event);
							// ...using Map
							/*Type type = new TypeToken<Map<String,Object>>(){}.getType();
							Map<String,Object> metricValueMap = gson.fromJson(payload, type);
							metricValueMap.forEach((x,y)-> System.out.println("\t" + x + " : " + y + " (" + y.getClass().getName() +")"));*/
							
							// Cache Metric Value in registry
							registry.setMetricValue( metricName, event.getMetric_value() );
							log.info("Metric Value set: name='{}', value='{}', topic={}", metricName, event.getMetric_value(), topic.getTopicName());
						} else {
							log.warn("Missing property: 'topic_name'");
						}
						break;
					case SCALE:
						log.debug("Listener of topic {}: Got a SCALE event: ", topic.getTopicName());
						log.error(">>>>   SCALE EVENT: **NOT YET IMPLEMENTED **");
						break;
					default:
						log.debug("Listener of topic {}: Got a UNKNOWN event: Ignoring it", topic.getTopicName());
				}
			} else {
				log.warn("Unsupported message type: {}", message.getClass().getName());
			}
		} catch (JMSException e) { 
			log.error("Caught: {}", e); 
			//e.printStackTrace(); 
		} 
	}

}
