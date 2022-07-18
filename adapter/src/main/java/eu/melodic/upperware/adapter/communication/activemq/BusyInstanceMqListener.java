package eu.melodic.upperware.adapter.communication.activemq;

import com.google.gson.Gson;
import eu.melodic.upperware.adapter.communication.activemq.model.CheckIfComponentBusyMessage;
import eu.melodic.upperware.adapter.service.CamelInstanceNamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;



/**
    This listener is responsible for receiving messages from EMS on activemq. It gathers information if
    the instances of each component are busy or idle.
    Maps 'busyInstancesByComponentName' and 'idleInstancesByComponentName'
    are autowired to 'BusyFirstInstanceNoProvider' class as well where they will be used
    during (re)deploying of the application.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BusyInstanceMqListener implements MessageListener {
    private final Gson gson;

    private final ConcurrentHashMap<String, List<Integer>> busyInstancesByComponentName;
    private final ConcurrentHashMap<String, List<Integer>> idleInstancesByComponentName;

    @Override
    public void onMessage(Message message) {
        try {
            log.debug("Listener of topic {}: Received message: {}", message.getJMSDestination().toString(), message);
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
            log.debug("Listener of topic {}: payload={}", message.getJMSDestination().toString(), payload);
            processMessage(payload);
        } catch (JMSException e) {
            log.error("Caught: {}", e.toString());
        }
    }

    private void processMessage(String payload) {
        log.debug("Converting event payload to CheckIfComponentBusyMessage instance...");
        CheckIfComponentBusyMessage checkIfComponentBusyMessage = gson.fromJson(payload, CheckIfComponentBusyMessage.class);
        log.info("CheckIfComponentBusyMessage instance: {}", checkIfComponentBusyMessage.toString());

        String softwareComponentInstanceName = checkIfComponentBusyMessage.getComponentInstanceName();
        String softwareComponentName = CamelInstanceNamingService.getSoftwareComponentNameFromInstanceName(softwareComponentInstanceName);
        Integer softwareComponentInstanceNo = CamelInstanceNamingService.getInstanceNumberFromInstanceName(softwareComponentInstanceName);
        log.debug("Saving instanceNo: {} for component: {}", softwareComponentInstanceNo, softwareComponentInstanceName);
        switch (checkIfComponentBusyMessage.getInstanceStatus()) {
            case BUSY: {
                this.busyInstancesByComponentName.compute(softwareComponentName, (key, list) -> {
                    if (list == null) {
                        return new LinkedList<>(Collections.singletonList(softwareComponentInstanceNo));
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
                        return new LinkedList<>(Collections.singletonList(softwareComponentInstanceNo));
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
