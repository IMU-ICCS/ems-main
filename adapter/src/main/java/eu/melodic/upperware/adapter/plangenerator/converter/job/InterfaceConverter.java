package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.Configuration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterTaskInterface;

public interface InterfaceConverter<T extends Configuration, V extends AdapterTaskInterface> {

    V convert(T configuration);

}
