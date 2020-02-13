package eu.melodic.upperware.genetic_solver.jenetics_implementation;

import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.ACPGeneticWrapper;
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
public class GeneImpl implements Gene<Integer, GeneImpl> {
    private Integer value;
    private Integer index;
    private ACPGeneticWrapper cpGeneticWrapper;

    @Override
    public Integer getAllele() {
        return value;
    }

    @Override
    public GeneImpl newInstance() {
        return of(index);
    }

    @Override
    public GeneImpl newInstance(Integer k) {
        return new GeneImpl(k, index, cpGeneticWrapper);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    private GeneImpl of(Integer index) {
        return new GeneImpl(cpGeneticWrapper.generateRandomValue(index), index, cpGeneticWrapper);
    }

    public static ISeq<GeneImpl> createSequenceOfGenes(Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        List<GeneImpl> list = new ArrayList<>();

        for (int i = 0; i < length; i++)
            list.add(new GeneImpl(cpGeneticWrapper.generateRandomValue(i), i, cpGeneticWrapper));
        return ISeq.of(list);
    }
}