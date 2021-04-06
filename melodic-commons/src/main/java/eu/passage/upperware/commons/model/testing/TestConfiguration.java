package eu.passage.upperware.commons.model.testing;

import lombok.Data;

import java.util.List;

@Data
public class TestConfiguration {
    private List<FunctionTestConfiguration> tests;
}
