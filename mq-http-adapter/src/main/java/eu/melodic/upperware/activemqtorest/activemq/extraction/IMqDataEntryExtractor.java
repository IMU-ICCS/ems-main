package eu.melodic.upperware.activemqtorest.activemq.extraction;

import java.util.Optional;

import org.apache.activemq.command.ActiveMQMessage;

import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;


public interface IMqDataEntryExtractor {
	boolean isApplicable(ActiveMQMessage activeMQMessage);

	Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage);
}
