package eu.passage.upperware.commons.model.testing;

import lombok.Data;

@Data
public class TestCase {
    private String functionName;
    private String event;
    private String expectedOutput;
    private String region;
}
