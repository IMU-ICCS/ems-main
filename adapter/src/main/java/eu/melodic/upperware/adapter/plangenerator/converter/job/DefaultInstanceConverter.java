package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.Configuration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterTaskInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultInstanceConverter implements InterfaceConverter<Configuration, AdapterTaskInterface> {

    @Override
    public boolean isInstance(Configuration configuration) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public AdapterTaskInterface convert(Configuration configuration) {
        return new AdapterTaskInterface();
    }
}
