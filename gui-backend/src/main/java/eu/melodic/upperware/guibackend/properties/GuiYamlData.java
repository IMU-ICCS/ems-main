package eu.melodic.upperware.guibackend.properties;

import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
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
