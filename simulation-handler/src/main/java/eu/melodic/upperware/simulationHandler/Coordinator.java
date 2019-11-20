package eu.melodic.upperware.simulationHandler;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.interfaces.simulationHandler.KeyValuePair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.*;

@Slf4j
@Service
public class Coordinator implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    private Map<String,String> mvvToCurrentConfigVarsMap;

    //private MetricValueRegistry metricValueRegistry;

    private Map<String, MetricValueSender> metricValueSenders = null;

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public void sendMetricsToMetaSolver(List<KeyValuePair> metricValueEvents) {
        //In this version we even don't to look inside received metrics. We simply push them forward
        if((metricValueEvents.size() == 0) || (metricValueSenders == null)) {
            log.info("MetaSolver.Coordinator: sendMetricsToMetaSolver(): nothing to send or no activeMQs defined");
        } else {

            for (KeyValuePair pair : metricValueEvents) {
                try {
                    metricValueSenders.get(pair.getKey()).publishEvent(pair.getValue());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateSubscriptions(Set<Map> subscriptions) {
        log.info("MetaSolver.Coordinator: updateSubscriptions(): subscriptions={}", subscriptions);
        resetMetricValueSenders();

        for (Map p : subscriptions) {
            String type = (String) p.get("type");
            if (!"MVV".equals(type)) {
                log.info("SimulationHandler.Coordinator: updateSubscriptions(): aborting not MVV Topic type: {}", type);
            } else {

                String url = (String) p.get("url");
                String username = (String) p.get("username");
                String password = (String) p.get("password");
                String certificate = (String) p.get("certificate");
                String topicName = (String) p.get("topic");
                String clientId = (String) p.get("client-id");

                log.info("SimulationController.Coordinator: updateSubscriptions(): Subscribing to topic: url={}, username={}, topic={}, client-id={}, type={}", url, username, topicName, clientId, type);

                metricValueSenders.put(topicName, new MetricValueSender(url, topicName, username, password));

                log.info("SimulationHandler.Coordinator: updateSubscriptions(): Subscribed to topic: {}", topicName);
            }
        }
        log.info("SimulationHandler.Coordinator: updateSubscriptions(): Subscribing to current topics finished");
    }

    public Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("simulationHandler");
        watermark.setSystem("simulationHandler");
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }

    void setMvvMap(Map<String,String> mvvMap) {
        log.info("MetaSolver.Coordinator: setMvvMap(): map={}", mvvMap);
        mvvToCurrentConfigVarsMap = mvvMap;
        log.info("MetaSolver.Coordinator: setMvvMap(): 'mvvToCurrentConfigVarsMap' updated");
    }

    private void resetMetricValueSenders() {
        this.metricValueSenders = new HashMap<>();
    }

}
