package eu.functionizer.functionizertestingtool.service.test;

public enum Stage {
    START("TEST FACTORY INITIALIZATION"),
    LOAD_CONFIG("LOAD TEST CONFIG FILE"),
    FETCH_CLOUDIATOR("FETCH CLOUDIATOR DATA"),
    FETCH_CREDENTIALS("FETCH CLOUD CREDENTIALS"),
    GATHER_FUNCTION_DATA("GATHER FUNCTION CLOUD DATA"),
    BUILD_AZURE_CLIENT("BUILD AZURE CLIENT"),
    BUILD_AWS_LAMBDA_CLIENT("BUILD AWS LAMBDA CLIENT"),
    GET_AZURE_FUNCTION_KEY("GET AZURE FUNCTION KEY"),
    END("COLLECTING TESTS FINISH");

    private final String name;

    Stage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
