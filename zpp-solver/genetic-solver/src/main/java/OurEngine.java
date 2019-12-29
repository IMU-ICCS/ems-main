import cPGeneticWrapper.CPGeneticWrapper;
import cp_wrapper.CPWrapper;
import implementation.OurChromosome;
import implementation.OurGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.util.function.Function;

public class OurEngine {

    private Integer dummy = 10;


    public void main(String[] args) {
        CPWrapper cpWrapper = new CPWrapper();
        CPGeneticWrapper cpGeneticWrapper = new CPGeneticWrapper(cpWrapper);

        Factory<Genotype<OurGene>> gtf =
                Genotype.of(OurChromosome.of(dummy, dummy, cpGeneticWrapper));

        Function<Genotype<OurGene>, Double> fun = chromosomes -> 0.5;

        final Engine<OurGene, Double> engine = Engine.builder(fun, gtf).build();

        System.out.println(engine.stream().limit(100).collect(EvolutionResult.toBestGenotype()));
    }
}
