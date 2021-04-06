package eu.passage.upperware.commons.model.provider;

public enum Provider {
    AWS_EC2("aws-ec2"),
    OPEN_STACK("openstack4j"),
    GOOGLE("google-compute-engine"),
    AZURE("azure"),
    OKTAWAVE("oktawave");

    public final String value;

    Provider(String value) {
        this.value = value;
    }
}
