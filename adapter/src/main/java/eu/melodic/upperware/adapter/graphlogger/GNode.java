package eu.melodic.upperware.adapter.graphlogger;

import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pszkup on 16.10.17.
 */

@Getter
@Setter
public class GNode {

    public Task parent;

    public List<Task> children = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(this.getParent() != null ? this.getParent() + "@" + Integer.toHexString(hashCode()) : " --- ").append("\n");
        this.getChildren().forEach(task -> sb.append("\t").append(task).append("@").append(Integer.toHexString(task.hashCode())).append("\n"));
        return sb.toString();
    }
}
