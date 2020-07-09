package eu.passage.upperware.commons.model;

import eu.passage.upperware.commons.model.byon.ByonDefinition;
import eu.passage.upperware.commons.model.provider.CloudDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuiYamlData {

    private List<CloudDefinition> cloudDefinitions;
    private List<ByonDefinition> byonDefinitions;
}
