/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.dlms.metric.receiver.metricvalue;

import javax.jms.JMSException;
import javax.jms.Message;
//import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.DataCenterRepository;
import eu.melodic.dlms.db.repository.DataSourceRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import eu.melodic.dlms.db.repository.TwoDataCentersRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetricValueListener implements MessageListener {

	private final CloudProviderRepository cpRepository;
	private final DataCenterRepository dcRepository;
	private final TwoDataCentersRepository twoDcRepository;
	private final RegionRepository regionRepository;
	private final ApplicationComponentRepository acRepository;
	private final DataSourceRepository dsRepository;
	private final ApplicationComponentDataSourceDataRepository acDsDataRepository;

	private Topic topic;
	private String topicName;
	private TopicType type;
	private Gson gson;

	public MetricValueListener( Topic topic, TopicType type,
			CloudProviderRepository cpRepository,
			DataCenterRepository dcRepository, RegionRepository regionRepository,
			TwoDataCentersRepository twoDcRepository, ApplicationComponentRepository acRepository, DataSourceRepository dsRepository, ApplicationComponentDataSourceDataRepository acDsDataRepository) throws JMSException {
		log.debug("MetricValueListener.<init>: type={}", type);

		this.topic = topic;
		this.topicName = topic.getTopicName();
		this.type = type;
		
		this.cpRepository = cpRepository;
		this.dcRepository = dcRepository;
		this.regionRepository = regionRepository;
		this.twoDcRepository = twoDcRepository;
		this.acRepository = acRepository;
		this.dsRepository = dsRepository;
		this.acDsDataRepository = acDsDataRepository;
		
		gson = new Gson();
	}

	public void onMessage(Message message) {
		try {
			log.debug("Listener of topic {}: Received message: ", topic.getTopicName());
			if (message instanceof TextMessage) {
				// Extract Topic name and payload from message
				TextMessage textMessage = (TextMessage) message;
				// String metricName = textMessage.getStringProperty("topic_name");
				String metricName = topicName;
				String payload = textMessage.getText();
 
				switch (type) {
				case LATENCY_BANDWIDTH:
					log.debug("Listener of topic {}: Got a datacenter event: ", topicName);
					processMetricValueEventDataCenter(metricName, payload);
					break;					
				case BYTES_READ:
					log.debug("Listener of topic {}: Got a data read event: ", topicName);
					processMetricValueEventAcDsDataRead(metricName, payload);
					break;
				case BYTES_WRITTEN:
					log.debug("Listener of topic {}: Got a data write event: ", topicName);
					processMetricValueEventAcDsDataWrite(metricName, payload);
					break;
				default:
					log.warn("Listener of topic {}: Got a UNKNOWN event: Ignoring it", topicName);
				}
			} else {
				log.warn("Unsupported message type: {}", message.getClass().getName());
			}
		} catch (JMSException e) {
			log.error("Caught: {}", e);
		}
	}

	/**
	 * Between two datacenters
	 */
	protected void processMetricValueEventDataCenter(String metricName, String payload) {
		if (StringUtils.isNotBlank(metricName)) {
			// Extract key-value pairs from message payload
			// using MetricValueEvent
			log.debug("Listener of topic {}: Converting event payload to MetricValueEvent instance...", topicName);
			MetricValueEventDataCenter event = gson.fromJson(payload, MetricValueEventDataCenter.class);
			log.debug("Listener of topic {}: MetricValueEvent instance: {}", topicName, event);
			MetricValueRegistryDataCenter<Object> metricValRegistryDC = new MetricValueRegistryDataCenter<Object>(this.cpRepository, this.dcRepository, this.regionRepository, this.twoDcRepository, event);
			metricValRegistryDC.saveMetricValues();
		} else {
			log.warn("Missing property: 'topic_name'");
		}
	}
	/**
	 * Data read between application component and datasource
	 */
	protected void processMetricValueEventAcDsDataRead(String metricName, String payload) {
		if (StringUtils.isNotBlank(metricName)) {
			// Extract key-value pairs from message payload
			// using MetricValueEvent
			log.debug("Listener of topic {}: Converting event payload to MetricValueEvent instance...", topicName);
			MetricValueEventAcDsDataRead eventRead = gson.fromJson(payload, MetricValueEventAcDsDataRead.class);
			log.debug("Listener of topic {}: MetricValueEvent instance: {}", topicName, eventRead);
			MetricValueRegistryAcDs<Object> metricValRegistryAcDsRead = new MetricValueRegistryAcDs<Object>(this.acRepository, this.dsRepository, this.acDsDataRepository);
			metricValRegistryAcDsRead.setEventRead(eventRead);
			metricValRegistryAcDsRead.saveMetricValuesEventRead();
		} else {
			log.warn("Missing property: 'topic_name'");
		}
	}
	
	/**
	 * Data write between application component and data source
	 */
	protected void processMetricValueEventAcDsDataWrite(String metricName, String payload) {
		if (StringUtils.isNotBlank(metricName)) {
			// Extract key-value pairs from message payload
			log.debug("Listener of topic {}: Converting event payload to MetricValueEvent instance...", topicName);
			MetricValueEventAcDsDataWrite eventWrite = gson.fromJson(payload, MetricValueEventAcDsDataWrite.class);
			log.debug("Listener of topic {}: MetricValueEvent instance: {}", topicName, eventWrite);
			MetricValueRegistryAcDs<Object> metricValRegistryAcDsWrite = new MetricValueRegistryAcDs<Object>(this.acRepository, this.dsRepository, this.acDsDataRepository);
			metricValRegistryAcDsWrite.setEventWrite(eventWrite);
			metricValRegistryAcDsWrite.saveMetricValuesEventWrite();
		} else {
			log.warn("Missing property: 'topic_name'");
		}
	}


	protected void processScaleEvent(String metricName, String payload) {
		log.debug("Listener of topic {}: Calling coordinator to start Scaling process...", topicName);
//		coordinator.requestStartProcessForScaling();
	}
}