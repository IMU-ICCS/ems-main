package eu.functionizer.functionizertestingtool.model;

import org.junit.platform.engine.TestExecutionResult;

public enum TestResultEnum {
        SUCCESS,
        PARTIAL_FAILURE,
        IGNORED,
        FAILURE;


        public static TestResultEnum fromTestExecutionResult(TestExecutionResult result) {
            if (result.getStatus() == TestExecutionResult.Status.SUCCESSFUL) {
                return SUCCESS;
            } else {
                return FAILURE;
            }
        }
}
