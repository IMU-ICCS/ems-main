package eu.melodic.upperware.adapter.executioncontext.colosseum;

import eu.melodic.upperware.adapter.exception.AdapterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import static java.lang.String.format;

@Getter
public class ShelveElement {

    private String id;
    private String queueId;

    ShelveElement(String id, String queueId) {
        this.id = id;
        this.queueId = extractId(queueId);
    }

    private String extractId(String locationId){
        if (StringUtils.isNotBlank(locationId)) {
            return locationId.substring(locationId.lastIndexOf("/") + 1);
        }
        throw new AdapterException(format("Could not get id from location %s", locationId));
    }

}
