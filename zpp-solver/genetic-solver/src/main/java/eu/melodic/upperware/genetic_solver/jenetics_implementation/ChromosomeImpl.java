package eu.melodic.upperware.genetic_solver.jenetics_implementation;

import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import lombok.Getter;

/*
    Chromosome is a population's individual. Phenotype and Genotype are just it's wrappers.
    Chromosome consists of sequence of genes.
 */
public class ChromosomeImpl implements Chromosome<GeneImpl> {
    @Getter
    private ISeq<GeneImpl> genes;
    private Integer length;
    @Getter
    private boolean isFeasible;
    @Getter
    private double utility;
    @Getter
    private int brokenConstraints;
    private ACPGeneticWrapper cpGeneticWrapper;

    public ChromosomeImpl(ISeq<GeneImpl> genes, Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        this.genes = genes;
        this.length = length;
        this.cpGeneticWrapper = cpGeneticWrapper;
        this.utility = cpGeneticWrapper.calculateUtility(genes);
        this.brokenConstraints = cpGeneticWrapper.countViolatedConstraints(genes);
        this.isFeasible = (brokenConstraints == 0);
    }

    @Override
    public Chromosome<GeneImpl> newInstance(ISeq<GeneImpl> iSeq) {
        return new ChromosomeImpl(iSeq, iSeq.length(), cpGeneticWrapper);
    }

    @Override
    public GeneImpl getGene(int i) {
        return genes.get(i);
    }

    @Override
    public int length() {
        return genes.length();
    }

    @Override
    public ISeq<GeneImpl> toSeq() {
        return genes;
    }

    @Override
    public Chromosome<GeneImpl> newInstance() {
        return of(length);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    // Creates randomly generated Chromosome.
    private ChromosomeImpl of(Integer length) {
        return of(length, cpGeneticWrapper);
    }

    // Creates randomly generated Chromosome. Static version of above method.
    private static ChromosomeImpl of(Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        ISeq<GeneImpl> genes = GeneImpl.createSequenceOfGenes(length, cpGeneticWrapper);
        return new ChromosomeImpl(genes, length, cpGeneticWrapper);
    }

    // Creates randomly generated sequence of Chromosomes.
    public static ISeq<ChromosomeImpl> of(Integer length, Integer size, ACPGeneticWrapper cpGeneticWrapper) {
        return MSeq.<ChromosomeImpl>ofLength(length)
                .fill(() -> of(size, cpGeneticWrapper))
                .toISeq();
    }
}
