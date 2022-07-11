package eu.melodic.upperware.adapter.service.Instance_no_provider.communication;

import com.google.gson.Gson;
import eu.melodic.upperware.adapter.service.CamelInstanceNamingService;
import eu.melodic.upperware.adapter.service.Instance_no_provider.communication.model.CheckIfComponentBusyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class BusyInstanceMqListener implements MessageListener {
    private final String topicName;
    private final Gson gson;

    private final ConcurrentHashMap<String, List<Integer>> busyInstancesByComponentName;
    private final ConcurrentHashMap<String, List<Integer>> idleInstancesByComponentName;

    @Override
    public void onMessage(Message message) {
        try {
            log.debug("Listener of topic {}: Received message: {}", topicName, message);
            String payload;
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                payload = textMessage.getText();
            } else if (message instanceof ActiveMQBytesMessage) {
                ActiveMQBytesMessage bytesMessage = (ActiveMQBytesMessage) message;
                payload = new String(bytesMessage.getContent().data);
            } else {
                throw new RuntimeException(String.format("Could not receive message, " + "unknown JMS message type: %s", message.getClass().getName()));
            }
            log.debug("Listener of topic {}: payload={}", topicName, payload);
            processMessage(payload);
        } catch (JMSException e) {
            log.error("Caught: {}", e.toString());
        }
    }

    private void processMessage(String payload) {
        log.debug("Listener of topic {}: Converting event payload to CheckIfComponentBusyMessage instance...", topicName);
        CheckIfComponentBusyMessage checkIfComponentBusyMessage = gson.fromJson(payload, CheckIfComponentBusyMessage.class);
        log.info("Listener of topic {}: CheckIfComponentBusyMessage instance: {}", topicName, checkIfComponentBusyMessage.toString());

        String softwareComponentInstanceName = checkIfComponentBusyMessage.getComponentInstanceName();
        String softwareComponentName = CamelInstanceNamingService.getSoftwareComponentNameFromInstanceName(softwareComponentInstanceName);
        Integer softwareComponentInstanceNo = CamelInstanceNamingService.getInstanceNumberFromInstanceName(softwareComponentInstanceName);
        log.debug("Saving instanceNo: {} for component: {}", softwareComponentInstanceNo, softwareComponentInstanceName);
        switch (checkIfComponentBusyMessage.getComponentStatus()) {
            case BUSY: {
                this.busyInstancesByComponentName.compute(softwareComponentName, (key, list) -> {
                    if (list == null) {
                        return new LinkedList<>();
                    } else {
                        list.add(softwareComponentInstanceNo);
                        return list;
                    }
                });
                break;
            }
            case IDLE: {
                this.idleInstancesByComponentName.compute(softwareComponentName, (key, list) -> {
                    if (list == null) {
                        return new LinkedList<>();
                    } else {
                        list.add(softwareComponentInstanceNo);
                        return list;
                    }
                });
                break;
            } case NOT_DEFINED: {
                break;
            }
            default: {
                log.error("Received message contains not recognised component status");
                break;
            }
        }
    }
}
