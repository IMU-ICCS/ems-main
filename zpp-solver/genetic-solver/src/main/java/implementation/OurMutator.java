package implementation;

import cPGeneticWrapper.ACPGeneticWrapper;
import io.jenetics.Chromosome;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.ISeq;
import lombok.Getter;
import java.util.Random;

public class OurMutator extends Mutator<OurGene, Double> {
    @Getter
    private int guesses;
    private ACPGeneticWrapper geneticWrapper;

    public OurMutator(int guesses, ACPGeneticWrapper geneticWrapper) {
        super();
        this.guesses = guesses;
        this.geneticWrapper = geneticWrapper;
    }

    public OurMutator(double probability, int guesses, ACPGeneticWrapper geneticWrapper) {
        super(probability);
        this.guesses = guesses;
        this.geneticWrapper = geneticWrapper;

    }

    @Override
    protected MutatorResult<Chromosome<OurGene>> mutate(
         final Chromosome<OurGene> chromosome,
         final double p,
         final Random random) {
        if (((OurChromosome) chromosome).getBrokenConstraints() == 0)
            return super.mutate(chromosome, p, random);

        ISeq<OurGene> genes = chromosome.toSeq();
        int index = geneticWrapper.calculateHeuristicBest(genes);


        /* This is probably dumb but we're gonna generate random values for this variable some number of times
           and see which value is the best.
         */
        int best = genes.get(index).getAllele();
        double bestUtility = ((OurChromosome) chromosome).getUtility();
        for (int i = 0; i < guesses; i++) {
            genes.asMSeq().set(index, genes.get(index).newInstance());
            double tmp = geneticWrapper.calculateUtility(genes);
            if (tmp > bestUtility) {
                best = genes.get(index).getAllele();
                bestUtility = tmp;
            }
        }

        genes.asMSeq().set(index, genes.get(index).newInstance(best));

        return MutatorResult.of(new OurChromosome(genes, genes.length(), geneticWrapper));
    }
}
