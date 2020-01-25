package implementation;

import cp_genetic_wrapper.ACPGeneticWrapper;
import io.jenetics.Gene;
import io.jenetics.util.ISeq;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
    ImplGene is a representation of variable assignment. It contains either index (for list defined variables) or
    value (for range defined variables). It can produce other genes.
 */
@AllArgsConstructor
public class ImplGene implements Gene<Integer, ImplGene> {
    private Integer value;
    private Integer index;
    private ACPGeneticWrapper cpGeneticWrapper;

    @Override
    public Integer getAllele() {
        return value;
    }

    @Override
    public ImplGene newInstance() {
        return of(index);
    }

    @Override
    public ImplGene newInstance(Integer k) {
        return new ImplGene(k, index, cpGeneticWrapper);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    private ImplGene of(Integer index) {
        return new ImplGene(cpGeneticWrapper.generateRandomValue(index), index, cpGeneticWrapper);
    }

    public static ISeq<ImplGene> seq(Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        List<ImplGene> list = new ArrayList<>();

        for (int i = 0; i < length; i++)
            list.add(new ImplGene(cpGeneticWrapper.generateRandomValue(i), i, cpGeneticWrapper));
        return ISeq.of(list);
    }
}