package implementation;

import cPGeneticWrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import lombok.Getter;

/*
    Chromosome is a population's individual. Phenotype and Genotype are just it's wrappers.
    Chromosome consists of sequence of genes.
 */
public class ImplChromosome implements Chromosome<ImplGene> {
    @Getter
    private ISeq<ImplGene> genes;
    private Integer length;
    @Getter
    private boolean isFeasible;
    @Getter
    private double utility;
    @Getter
    private int brokenConstraints;
    private ACPGeneticWrapper cpGeneticWrapper;

    public ImplChromosome(ISeq<ImplGene> genes, Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        this.genes = genes;
        this.length = length;
        this.cpGeneticWrapper = cpGeneticWrapper;
        this.utility = cpGeneticWrapper.calculateUtility(genes);
        this.brokenConstraints = cpGeneticWrapper.countViolatedConstraints(genes);
        this.isFeasible = (brokenConstraints == 0);
    }

    @Override
    public Chromosome<ImplGene> newInstance(ISeq<ImplGene> iSeq) {
        return new ImplChromosome(iSeq, iSeq.length(), cpGeneticWrapper);
    }

    @Override
    public ImplGene getGene(int i) {
        return genes.get(i);
    }

    @Override
    public int length() {
        return genes.length();
    }

    @Override
    public ISeq<ImplGene> toSeq() {
        return genes;
    }

    @Override
    public Chromosome<ImplGene> newInstance() {
        return of(length);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    // Creates randomly generated Chromosome.
    public ImplChromosome of(Integer length) {
        return of(length, cpGeneticWrapper);
    }

    // Creates randomly generated Chromosome. Static version of above method.
    public static ImplChromosome of(Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        ISeq<ImplGene> genes = ImplGene.seq(length, cpGeneticWrapper);
        return new ImplChromosome(genes, length, cpGeneticWrapper);
    }

    // Creates randomly generated sequence of Chromosomes.
    public static ISeq<ImplChromosome> of(Integer length, Integer size, ACPGeneticWrapper cpGeneticWrapper) {
        return MSeq.<ImplChromosome>ofLength(length)
                .fill(() -> of(size, cpGeneticWrapper))
                .toISeq();
    }
}
