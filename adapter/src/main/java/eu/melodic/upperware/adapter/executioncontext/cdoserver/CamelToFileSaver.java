package eu.melodic.upperware.adapter.executioncontext.cdoserver;

import camel.core.CamelModel;

import java.util.function.Function;

public interface CamelToFileSaver {

    void toFile(CamelModel camelModel);
    void toFile(CamelModel camelModel, Function<CamelModel, String> fileNameFunction);
    void toFile(CamelModel camelModel, String filePath);

    void toFile(String resourceName);
    void toFile(String resourceName, Function<CamelModel, String> fileNameFunction);
    void toFile(String resourceName, String filePath);

}
