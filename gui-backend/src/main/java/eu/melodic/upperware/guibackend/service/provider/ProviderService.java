package eu.melodic.upperware.guibackend.service.provider;

import eu.melodic.upperware.guibackend.model.provider.*;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderService {

    private GuiBackendProperties guiBackendProperties;

    // todo get from DB
    public List<CloudDefinition> getCloudDefinitionsForAllProviders() {
        CloudDefinition cloudDefinitionForAws = createCloudDefinitionForAws();
        CloudDefinition cloudDefinitionForOpenStack = createCloudDefinitionForOpenStack();
        return Arrays.asList(cloudDefinitionForAws, cloudDefinitionForOpenStack);
    }

    // todo get from DB
    public CloudDefinition getCloudDefinition(int cloudDefId) {
        return getCloudDefinitionsForAllProviders().get(cloudDefId);
    }

    private CloudDefinition createCloudDefinitionForOpenStack() {
        return CloudDefinition.builder()
                .id(1)
                .endpoint(guiBackendProperties.getOpenStack().getEndpoint())
                .cloudType(CloudType.PRIVATE)
                .api(new Api(1L, "openstack4j"))
                .credential(new Credential(1L, guiBackendProperties.getOpenStack().getUser(), guiBackendProperties.getOpenStack().getSecret()))
                .cloudConfiguration(createCloudConfigurationForOpenStack())
                .build();
    }

    private CloudConfiguration createCloudConfigurationForOpenStack() {
        return CloudConfiguration.builder()
                .nodeGroup(guiBackendProperties.getOpenStack().getNodeGroup())
                .properties(createPropertiesForOpenStack())
                .build();
    }

    private CloudDefinition createCloudDefinitionForAws() {
        return CloudDefinition.builder()
                .id(2)
                .endpoint(guiBackendProperties.getAws().getEndpoint())
                .cloudType(CloudType.PUBLIC)
                .api(new Api(2L, "aws-ec2"))
                .credential(new Credential(2L, guiBackendProperties.getAws().getUser(), guiBackendProperties.getAws().getSecret()))
                .cloudConfiguration(createCloudConfigurationForAws())
                .build();
    }

    private CloudConfiguration createCloudConfigurationForAws() {
        return CloudConfiguration.builder()
                .nodeGroup(guiBackendProperties.getAws().getNodeGroup())
                .properties(createPropertiesForAws())
                .build();
    }

    private List<ParentProperty> createPropertiesForAws() {
        SingleProperty singlePropertyGenom1 = new SingleProperty(1, "sword.ec2.ami.cc.query", "image-id=ami-08a0a7bee3f024aeb");
        SingleProperty singlePropertyGenom2 = new SingleProperty(2, "sword.ec2.ami.query", "image-id=ami-08a0a7bee3f024aeb");

        ParentProperty parentProperty1 = ParentProperty.builder()
                .name("genom-ami")
                .id(1)
                .properties(Arrays.asList(singlePropertyGenom1, singlePropertyGenom2))
                .build();

        SingleProperty fcrProperty1 = new SingleProperty(3, "sword.ec2.ami.cc.query", "image-id=ami-052835130f4a2f8e3");
        SingleProperty fcrProperty2 = new SingleProperty(4, "sword.ec2.ami.query", "image-id=ami-052835130f4a2f8e3");
        ParentProperty parentProperty2 = ParentProperty.builder()
                .name("fcr-ami")
                .id(2)
                .properties(Arrays.asList(fcrProperty1, fcrProperty2))
                .build();

        SingleProperty functionizerProperty1 = new SingleProperty(5, "sword.ec2.ami.cc.query", "image-id=ami-0fe36488fc77abc96");
        SingleProperty functionizerProperty2 = new SingleProperty(6, "sword.ec2.ami.query", "image-id=ami-0fe36488fc77abc96");
        ParentProperty parentProperty3 = ParentProperty.builder()
                .name("functionizer-ami")
                .id(3)
                .properties(Arrays.asList(functionizerProperty1, functionizerProperty2))
                .build();


        SingleProperty ubuntu18property1 = new SingleProperty(7, "sword.ec2.ami.cc.query", "image-id=ami-00035f41c82244dab");
        SingleProperty ubuntu18property2 = new SingleProperty(8, "sword.ec2.ami.query", "image-id=ami-00035f41c82244dab");
        ParentProperty parentProperty4 = ParentProperty.builder()
                .name("UBUNTU 18.04")
                .id(4)
                .properties(Arrays.asList(ubuntu18property1, ubuntu18property2))
                .build();

        SingleProperty ubuntu16property1 = new SingleProperty(9, "sword.ec2.ami.cc.query", "image-id=ami-09f0b8b3e41191524");
        SingleProperty ubuntu16property2 = new SingleProperty(10, "sword.ec2.ami.query", "image-id=ami-09f0b8b3e41191524");
        ParentProperty parentProperty5 = ParentProperty.builder()
                .name("UBUNTU 16.04")
                .id(5)
                .properties(Arrays.asList(ubuntu16property1, ubuntu16property2))
                .build();
        return Arrays.asList(parentProperty1, parentProperty2, parentProperty3, parentProperty4, parentProperty5);
    }

    private List<ParentProperty> createPropertiesForOpenStack() {
        SingleProperty property1 = new SingleProperty(11, "sword.openstack4j.defaultNetwork", "065f36e5-3249-49fa-9673-58b12ee37c06");
        SingleProperty property2 = new SingleProperty(12, "sword.providerId.blacklist", "beta,cindertest,db-docker-vm-evaluation,hdd,internal,nova,ssd,noisy-neighbour,noisy-neighbour-ctrl");

        ParentProperty parentProperty1 = ParentProperty.builder()
                .name("network-property")
                .id(6)
                .properties(Arrays.asList(property1, property2))
                .build();

        return Collections.singletonList(parentProperty1);
    }
}
