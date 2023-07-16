package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sensor extends Component {
    private String configurationStr;
    private boolean isPush;

    //XXX:TODO: Try to remove???
    public Map<String, String> additionalProperties;

    public boolean isPullSensor() {
        return !isPush;
    }

    public boolean isPushSensor() {
        return isPush;
    }

    public PullSensor pullSensor() {
        if (this instanceof PullSensor)
            return (PullSensor) this;
        throw new IllegalArgumentException("Not a Pull sensor: " + this.getName());
    }

    public PushSensor pushSensor() {
        if (this instanceof PushSensor)
            return (PushSensor) this;
        throw new IllegalArgumentException("Not a Push sensor: " + this.getName());
    }
}
