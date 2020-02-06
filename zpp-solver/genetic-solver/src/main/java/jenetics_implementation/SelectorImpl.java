package jenetics_implementation;

import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.Selector;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import io.jenetics.util.Seq;
import lombok.AllArgsConstructor;
import sorting_algortihms.MergeSort;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/*
    Important part of this selector is that it can use custom comparators.
 */
@AllArgsConstructor
public class SelectorImpl implements Selector<GeneImpl, Double> {
    @Override
    public ISeq<Phenotype<GeneImpl, Double>> select(final Seq<Phenotype<GeneImpl, Double>> population, final int count,
                                                    final Optimize opt) {
        requireNonNull(population, "Population");
        requireNonNull(opt, "Optimization");
        if (count < 0) {
            throw new IllegalArgumentException(format(
                    "Selection count must be greater or equal then zero, but was %s",
                    count
            ));
        }

        // Container for selected chromosomes.
        final MSeq<Phenotype<GeneImpl, Double>> selection = MSeq
                .ofLength(population.isEmpty() ? 0 : count);

        if (count > 0 && !population.isEmpty()) {
            // Make a copy of population and sort it.
            final MSeq<Phenotype<GeneImpl, Double>> copy = population.asISeq().copy();
            MergeSort.sort(copy);

            final int length = min(copy.size(), count);

            // Put *length* best chromosomes into selection container.
            for (int i = 0; i < length; ++i) {
                selection.set(i, copy.get(i));
            }
        }

        return selection.toISeq();
    }
}

