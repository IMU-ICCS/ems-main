package eu.paasage.upperware.solvertodeployment.utils;

import camel.core.Attribute;
import camel.core.CamelModel;
import camel.core.CoreFactory;
import camel.deployment.SoftwareComponentInstance;
import camel.type.StringValue;
import camel.type.TypeFactory;
import com.google.gson.Gson;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
public class ProviderEnricherServiceImpl implements ProviderEnricherService {

    private Gson gson = new Gson();

    @Override
    public void enrichSoftwareComponentInstance(SoftwareComponentInstance softwareComponentInstance, NodeCandidate nodeCandidate, String constraintProblemId, CamelModel camelModel) {
        EList<Attribute> attributes = softwareComponentInstance.getAttributes();
        log.info("Start enriching SoftwareComponentInstance: {}", softwareComponentInstance.getName());
        attributes.add(createAttribute("nodeCandidate", gson.toJson(nodeCandidate)));
        log.info("Finish enriching SoftwareComponentInstance: {}", softwareComponentInstance.getName());
    }

    private Attribute createAttribute(String attributeName, String attributeValue) {
        log.info("Adding attribute {} with value: {}", attributeName, attributeValue);
        Attribute strAttribute = CoreFactory.eINSTANCE.createAttribute();
        strAttribute.setName(attributeName);
        strAttribute.setValue(createStringValue(attributeValue));
        return strAttribute;
    }

    private StringValue createStringValue(String attributeValue) {
        StringValue stringValue = TypeFactory.eINSTANCE.createStringValue();
        stringValue.setValue(attributeValue);
        return stringValue;
    }
}