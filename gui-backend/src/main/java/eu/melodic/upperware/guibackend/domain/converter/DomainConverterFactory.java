package eu.melodic.upperware.guibackend.domain.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DomainConverterFactory {
    private final GenericConverter<?, ?> cloudConverter;
    private final GenericConverter<?, ?> imageConverter;
    private final GenericConverter<?, ?> hardwareConverter;
    private final GenericConverter<?, ?> locationConverter;
    private final GenericConverter<?, ?> nodeConverter;
    private final GenericConverter<?, ?> jobConverter;
    private final GenericConverter<?, ?> monitorConverter;

    public DomainConverterFactory(@Qualifier("locationConverter") GenericConverter<?, ?> locationConverter,
                                  @Qualifier("hardwareConverter") GenericConverter<?, ?> hardwareConverter,
                                  @Qualifier("imageConverter") GenericConverter<?, ?> imageConverter,
                                  @Qualifier("cloudConverter") GenericConverter<?, ?> cloudConverter,
                                  @Qualifier("nodeConverter") GenericConverter<?, ?> nodeConverter,
                                  @Qualifier("jobConverter") GenericConverter<?, ?> jobConverter,
                                  @Qualifier("monitorConverter") GenericConverter<?, ?> monitorConverter) {
        this.cloudConverter = cloudConverter;
        this.locationConverter = locationConverter;
        this.imageConverter = imageConverter;
        this.hardwareConverter = hardwareConverter;
        this.nodeConverter = nodeConverter;
        this.jobConverter =jobConverter;
        this.monitorConverter = monitorConverter;
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

    public GenericConverter<?, ?> getJobConverter() {
        return this.jobConverter;
    }

    public GenericConverter<?, ?> getMonitorConverter() {
        return this.monitorConverter;
    }
}
