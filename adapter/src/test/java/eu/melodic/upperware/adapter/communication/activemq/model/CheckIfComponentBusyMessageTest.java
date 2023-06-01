package eu.melodic.upperware.adapter.communication.activemq.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.melodic.upperware.adapter.service.Instance_no_provider.InstanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CheckIfComponentBusyMessageTest {

    private Gson gson;


    @BeforeEach
    void setGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
    }

    @Test
    void testDeserialisationSimpleMessage() {
        String payload = "{ 'instanceName'= 'testName', 'metricValue' = '1.0'}";
        CheckIfComponentBusyMessage message = gson.fromJson(payload, CheckIfComponentBusyMessage.class);
        assertEquals("testName", message.getComponentInstanceName());
        assertEquals(InstanceStatus.BUSY, message.getInstanceStatus());
    }

    @Test
    void testDeserialisationNoNotNeededField() {
        String payload = "{ 'metricValue' = '0.0'}";
        CheckIfComponentBusyMessage message = gson.fromJson(payload, CheckIfComponentBusyMessage.class);
        assertNull(message.getComponentInstanceName());
        assertEquals(InstanceStatus.IDLE, message.getInstanceStatus());
    }

}
