package eu.melodic.upperware.adapter.service;

import eu.melodic.models.services.adapter.*;
import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiffResponseConverter {

    public DifferenceResponse convert(Map<String, DividedElement<AdapterRequirement>> stringDividedElementMap){
        DifferenceResponse result = new DifferenceResponseImpl();
        result.setDiff(createTouples(stringDividedElementMap));
        return result;
    }

    private List<Touple> createTouples(Map<String, DividedElement<AdapterRequirement>> stringDividedElementMap){
        List<Touple> touples = new ArrayList<>();
        stringDividedElementMap.forEach((s, element) -> touples.add(createTouple(s, element)));
        return touples;
    }

    private Touple createTouple(String key, DividedElement<AdapterRequirement> element) {
        Touple result = new ToupleImpl();
        result.setKey(key);
        result.setValue(createToupleValue(element));
        return result;
    }

    private ToupleValue createToupleValue(DividedElement<AdapterRequirement> element) {
        ToupleValue result = new ToupleValueImpl();
        result.setToRemain(createInstanceDetails(element.getToRemain()));
        result.setToDelete(createInstanceDetails(element.getToDelete()));
        result.setToCreate(createInstanceDetails(element.getToCreate()));
        return result;
    }

    private List<InstanceDetails> createInstanceDetails(List<AdapterRequirement> requirements) {
        return requirements.stream().map(adapterRequirement -> {
            InstanceDetails instanceDetails = new InstanceDetailsImpl();
            instanceDetails.setName(StringUtils.removeFirst(adapterRequirement.getName(), "AdapterRequirement_"));
            return instanceDetails;
        }).collect(Collectors.toList());
    }

}
