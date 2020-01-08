package implementation;

import cPGeneticWrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import lombok.Getter;


public class OurChromosome implements Chromosome<OurGene> {
    @Getter
    private ISeq<OurGene> genes;
    private Integer length;
    private boolean isFeasible;
    private double utility;
    private int brokenConstraints;
    private Boolean valid;
    private ACPGeneticWrapper cpGeneticWrapper;

    public OurChromosome(ISeq<OurGene> genes, Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        this.genes = genes;
        this.length = length;
        this.cpGeneticWrapper = cpGeneticWrapper;
        this.isFeasible = cpGeneticWrapper.getIsFeasible(genes);
        this.utility = cpGeneticWrapper.calculateUtility(genes);
        this.brokenConstraints = cpGeneticWrapper.countViolatedConstraints(genes);
        this.valid = checkIfValid(genes);
    }

    @Override
    public Chromosome<OurGene> newInstance(ISeq<OurGene> iSeq) {
        return new OurChromosome(iSeq, iSeq.length(), cpGeneticWrapper);
    }

    @Override
    public OurGene getGene(int i) {
        return genes.get(i);
    }

    @Override
    public int length() {
        return genes.length();
    }

    @Override
    public ISeq<OurGene> toSeq() {
        return genes;
    }

    @Override
    public Chromosome<OurGene> newInstance() {
        return of(length);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    private boolean checkIfValid(ISeq<OurGene> genes) {
        for (OurGene gene : genes)
            if (!gene.isValid())
                return false;
        return true;
    }

    // Creates randomly generated Chromosome.
    public OurChromosome of(Integer length) {
        return of(length, cpGeneticWrapper);
    }

    // Creates randomly generated Chromosome. Static version of above method.
    public static OurChromosome of(Integer length, ACPGeneticWrapper cpGeneticWrapper) {
        ISeq<OurGene> genes = OurGene.seq(length, cpGeneticWrapper);
        return new OurChromosome(genes, length, cpGeneticWrapper);
    }

    // Creates randomly generated sequence of Chromosomes.
    public ISeq<OurChromosome> of(Integer length, Integer size) {
        return of(length, size, cpGeneticWrapper);
    }

    public static ISeq<OurChromosome> of(Integer length, Integer size, ACPGeneticWrapper cpGeneticWrapper) {
        return MSeq.<OurChromosome>ofLength(length)
                .fill(() -> of(size, cpGeneticWrapper))
                .toISeq();
    }

    public double getUtility() {
        return utility;
    }

    public int getBrokenConstraints() {
        return brokenConstraints;
    }

    public boolean getIsFeasible() {
        return isFeasible;
    }
}
