package implementation;

import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.Selector;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import io.jenetics.util.Seq;

import java.util.Comparator;

import static java.util.Collections.reverseOrder;

public class OurSelector implements Selector<OurGene, Double> {
    @Override
    public ISeq<Phenotype<OurGene, Double>> select(Seq<Phenotype<OurGene, Double>> seq, int size, Optimize optimize) {
        MSeq<Phenotype<OurGene, Double>> mseq = seq.asMSeq();
        Comparator<Phenotype<OurGene, Double>> comparator = new StochasticRankingComparator(0);
        mseq.sort(reverseOrder(comparator));

        return mseq.subSeq(size).toISeq();
    }
}
