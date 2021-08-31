package eu.melodic.upperware.activemqtorest.activemq.extraction;

import eu.melodic.upperware.activemqtorest.MorphemicTopicsMatcher;
import eu.melodic.upperware.activemqtorest.entry.MqBaseEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class MqSeverityExtractor extends MqDataEntryBaseExtractor implements IMqDataEntryExtractor {

    @Override
    public boolean isApplicable(ActiveMQMessage activeMQMessage) {
        return MorphemicTopicsMatcher.isSloSeverityValueTopic(activeMQMessage.getJMSDestination().toString());
    }

    @Override
    public Optional<MqBaseEntry> extractMqDataEntry(ActiveMQMessage activeMQMessage) {
        log.warn("Saving Slo severity values to InfluxDv is not supported");
        return Optional.empty();
    }
}
