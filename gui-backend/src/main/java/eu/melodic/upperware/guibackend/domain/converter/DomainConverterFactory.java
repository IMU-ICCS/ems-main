package eu.melodic.upperware.guibackend.domain.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DomainConverterFactory {
    private final GenericConverter<?, ?> cloudConverter;
    private final GenericConverter<?, ?> imageConverter;

    public DomainConverterFactory(@Qualifier("cloudConverter") GenericConverter<?, ?> cloudConverter,
                                  @Qualifier("imageConverter") GenericConverter<?, ?> imageConverter) {
        this.cloudConverter = cloudConverter;
        this.imageConverter = imageConverter;
    }

    public GenericConverter<?, ?> getCloudConverter() {
        return this.cloudConverter;
    }

    public GenericConverter<?, ?> getImageConverter() {
        return this.imageConverter;
    }
}
