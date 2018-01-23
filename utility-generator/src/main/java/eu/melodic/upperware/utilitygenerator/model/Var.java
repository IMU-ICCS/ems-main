package eu.melodic.upperware.utilitygenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Var<T> {

    private String name;
    private T value;
}
