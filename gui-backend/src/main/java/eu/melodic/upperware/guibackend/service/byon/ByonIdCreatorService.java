package eu.melodic.upperware.guibackend.service.byon;

import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ByonIdCreatorService {
    public void addIdForByonDefinition(ByonDefinition byonDefinition, List<ByonDefinition> byonDefinitionsList) {

        Long lastId = byonDefinitionsList.stream()
                .map(ByonDefinition::getId)
                .max(Long::compareTo)
                .orElse(0L);
        byonDefinition.setId(lastId + 1);
    }
}
