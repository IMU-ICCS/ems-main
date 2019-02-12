package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.Configuration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterTaskInterface;

public interface InterfaceConverter<T extends Configuration, V extends AdapterTaskInterface> {

    String DOCKER_TAG = "docker";

    boolean isInstance(Configuration configuration);

    V convert(T configuration);

}
