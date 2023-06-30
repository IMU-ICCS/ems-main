package eu.melodic.event.models.resources;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import eu.melodic.event.models.interfaces.PullSensor;
import eu.melodic.event.models.interfaces.PushSensor;
import eu.melodic.event.models.interfaces.Sensor;
import java.io.IOException;
import java.lang.Object;
import java.lang.String;
import java.util.Arrays;
import java.util.Map;

public class SensorDeserializer extends StdDeserializer<Sensor> {
  public SensorDeserializer() {
    super(Sensor.class);}

  private boolean looksLikePushSensor(Map<String, Object> map) {
    return map.keySet().containsAll(Arrays.asList("port"));
  }

  private boolean looksLikePullSensor(Map<String, Object> map) {
    return map.keySet().containsAll(Arrays.asList("className","configuration","interval"));
  }

  public Sensor deserialize(JsonParser jsonParser, DeserializationContext jsonContext) throws IOException, JsonProcessingException {
    ObjectMapper mapper  = new ObjectMapper();
    Map<String, Object> map = mapper.readValue(jsonParser, Map.class);
    if ( looksLikePushSensor(map) ) return new Sensor(mapper.convertValue(map, PushSensor.class));
    if ( looksLikePullSensor(map) ) return new Sensor(mapper.convertValue(map, PullSensor.class));
    throw new IOException("Can't figure out type of object" + map);
  }
}
