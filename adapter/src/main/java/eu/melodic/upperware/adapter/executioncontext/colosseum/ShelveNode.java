package eu.melodic.upperware.adapter.executioncontext.colosseum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShelveNode extends ShelveElement {

    private String nodeName;

    public ShelveNode(String id, String queueId, String nodeName) {
        super(id, queueId);
        this.nodeName = nodeName;
    }
}
