package eu.melodic.upperware.utilitygenerator.model.function;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NodeCandidateAttribute {

    String name;
    final String type = "cost"; //todo - different types - for now only cost
    @Setter
    double value;

    public NodeCandidateAttribute(String name, double value){
        this.name = name;
        this.value = value;
    }
    public NodeCandidateAttribute(String name){
        this.name = name;
    }
}
