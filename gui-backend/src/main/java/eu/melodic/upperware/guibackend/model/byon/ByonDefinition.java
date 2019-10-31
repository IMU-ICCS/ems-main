package eu.melodic.upperware.guibackend.model.byon;

import io.github.cloudiator.rest.model.NewNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ByonDefinition extends NewNode {
    private long id;
}
