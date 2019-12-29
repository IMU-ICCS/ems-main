package implementation;

import cPGeneticWrapper.CPGeneticWrapper;
import io.jenetics.Gene;
import io.jenetics.util.ISeq;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OurGene implements Gene<Integer, OurGene> {
    private Integer value;
    private Integer index;
    private CPGeneticWrapper cpGeneticWrapper;

    @Override
    public Integer getAllele() {
        return value;
    }

    @Override
    public OurGene newInstance() {
        return of(index);
    }

    @Override
    public OurGene newInstance(Integer k) {
        return new OurGene(k, index, cpGeneticWrapper);
    }

    @Override
    public boolean isValid() {
        return cpGeneticWrapper.isValid(value, index);
    }

    private OurGene of(Integer index) {
        return new OurGene(cpGeneticWrapper.generateRandomValue(index), index, cpGeneticWrapper);
    }

    public ISeq<OurGene> seq(Integer length) {
        return seq(length, cpGeneticWrapper);
    }

    static ISeq<OurGene> seq(Integer length, CPGeneticWrapper cpGeneticWrapper) {
        List<OurGene> list = new ArrayList<>();

        for (int i = 0; i < length; i++)
            list.add(new OurGene(cpGeneticWrapper.generateRandomValue(i), i, cpGeneticWrapper));
        return ISeq.<OurGene>of(list);
    }
}