package eu.melodic.upperware.adapter.plangenerator.graph.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DividedElement <T>{
    private List<T> toCreate;
    private List<T> toRemain;
    private List<T> toDelete;
}
