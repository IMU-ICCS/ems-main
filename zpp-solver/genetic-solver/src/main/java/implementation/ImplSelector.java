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

/*
    Important part of this selector is that it can use custom comparators.
 */
@AllArgsConstructor
public class ImplSelector implements Selector<ImplGene, Double> {
    private Comparator<Phenotype<ImplGene, Double>> comparator;

    @Override
    public ISeq<Phenotype<ImplGene, Double>> select(Seq<Phenotype<ImplGene, Double>> seq, int size, Optimize optimize) {
        MSeq<Phenotype<ImplGene, Double>> mseq = seq.asMSeq().copy();
        mseq.sort(comparator);

        return mseq.subSeq(size).toISeq();
    }
}
