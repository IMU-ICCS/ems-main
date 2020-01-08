package implementation;

import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.Selector;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import io.jenetics.util.Seq;
import lombok.AllArgsConstructor;

import java.util.Comparator;

import static java.util.Collections.reverseOrder;

@AllArgsConstructor
public class OurSelector implements Selector<OurGene, Double> {
    private Comparator<Phenotype<OurGene, Double>> comparator;

    @Override
    public ISeq<Phenotype<OurGene, Double>> select(Seq<Phenotype<OurGene, Double>> seq, int size, Optimize optimize) {
        MSeq<Phenotype<OurGene, Double>> mseq = seq.asMSeq();
        mseq.sort(reverseOrder(comparator));

        return mseq.subSeq(size).toISeq();
    }
}
