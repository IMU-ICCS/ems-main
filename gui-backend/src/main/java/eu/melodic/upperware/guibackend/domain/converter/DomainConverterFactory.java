package eu.melodic.upperware.guibackend.domain.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DomainConverterFactory {
    private final GenericConverter<?, ?> cloudConverter;
    private final GenericConverter<?, ?> imageConverter;
    private final GenericConverter<?, ?> nodeConverter;

    public DomainConverterFactory(@Qualifier("cloudConverter") GenericConverter<?, ?> cloudConverter,
                                  @Qualifier("imageConverter") GenericConverter<?, ?> imageConverter,
                                  @Qualifier("nodeConverter") GenericConverter<?, ?> nodeConverter) {
        this.cloudConverter = cloudConverter;
        this.imageConverter = imageConverter;
        this.nodeConverter = nodeConverter;
    }

    public GenericConverter<?, ?> getCloudConverter() {
        return this.cloudConverter;
    }

    public GenericConverter<?, ?> getImageConverter() {
        return this.imageConverter;
    }

    public GenericConverter<?, ?> getNodeConverter() {
        return this.nodeConverter;
    }
}
