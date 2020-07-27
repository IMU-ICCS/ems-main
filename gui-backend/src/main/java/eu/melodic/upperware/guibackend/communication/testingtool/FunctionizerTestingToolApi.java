package eu.melodic.upperware.guibackend.communication.testingtool;

import eu.passage.upperware.commons.model.testing.FunctionizerTestResult;

public interface FunctionizerTestingToolApi {
    FunctionizerTestResult runTests(String token);
}
