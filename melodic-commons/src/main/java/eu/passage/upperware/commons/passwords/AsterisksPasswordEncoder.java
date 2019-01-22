package eu.passage.upperware.commons.passwords;

public class AsterisksPasswordEncoder extends ConstantPasswordEncoder {

    public AsterisksPasswordEncoder() {
        super("************");
    }

    @Override
    public String encode(String password) {
        return super.encode(password);
    }
}
