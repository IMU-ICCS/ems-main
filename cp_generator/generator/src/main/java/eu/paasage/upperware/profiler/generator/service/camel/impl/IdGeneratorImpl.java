package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.profiler.generator.service.camel.IdGenerator;

public class IdGeneratorImpl implements IdGenerator {

    private String prefix;
    private int constraintsCount = 0;

    public IdGeneratorImpl(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String generate(){
        return prefix + constraintsCount++;
    }

    @Override
    public void reset() {
        constraintsCount = 0;
    }
}
