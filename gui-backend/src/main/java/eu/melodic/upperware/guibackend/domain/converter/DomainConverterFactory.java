package eu.melodic.upperware.guibackend.domain.converter;

import org.springframework.stereotype.Service;

@Service
public class DomainConverterFactory {
    private final GenericConverter<?, ?> cloudConverter;
    private final GenericConverter<?, ?> imageConverter;
    private final GenericConverter<?, ?> hardwareConverter;
    private final GenericConverter<?, ?> locationConverter;
    private final GenericConverter<?, ?> nodeConverter;

    public DomainConverterFactory() {
        cloudConverter = new ProactiveCloudConverter();
        locationConverter = new ProactiveLocationConverter();
        imageConverter = new ProactiveImageConverter((ProactiveLocationConverter) locationConverter);
        hardwareConverter = new ProactiveHardwareConverter((ProactiveLocationConverter) locationConverter);
        nodeConverter = new ProactiveNodeConverter();
    }

    public GenericConverter<?, ?> getCloudConverter() {
        return this.cloudConverter;
    }

    public GenericConverter<?, ?> getImageConverter() {
        return this.imageConverter;
    }

    public GenericConverter<?, ?> getHardwareConverter() {
        return this.hardwareConverter;
    }

    public GenericConverter<?, ?> getLocationConverter() {
        return this.locationConverter;
    }

    public GenericConverter<?, ?> getNodeConverter() {
        return this.nodeConverter;
    }
}
