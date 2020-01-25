package implementation;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.Selector;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import io.jenetics.util.Seq;
import lombok.AllArgsConstructor;
import sorting_algortihms.MergeSort;

import java.util.Comparator;

/*
    Important part of this selector is that it can use custom comparators.
 */
@AllArgsConstructor
public class ImplSelector implements Selector<ImplGene, Double> {
    private Comparator<Phenotype<ImplGene, Double>> comparator;

    @Override
    public ISeq<Phenotype<ImplGene, Double>> select(
            final Seq<Phenotype<ImplGene, Double>> population,
            final int count,
            final Optimize opt
    ) {
        requireNonNull(population, "Population");
        requireNonNull(opt, "Optimization");
        if (count < 0) {
            throw new IllegalArgumentException(format(
                    "Selection count must be greater or equal then zero, but was %s",
                    count
            ));
        }

        final MSeq<Phenotype<ImplGene, Double>> selection = MSeq
                .ofLength(population.isEmpty() ? 0 : count);

        if (count > 0 && !population.isEmpty()) {
            final MSeq<Phenotype<ImplGene, Double>> copy = population.asISeq().copy();
            MergeSort.sort(copy);

            int size = count;
            do {
                final int length = min(copy.size(), size);
                for (int i = 0; i < length; ++i) {
                    selection.set((count - size) + i, copy.get(i));
                }

                size -= length;
            } while (size > 0);
        }

        return selection.toISeq();
    }
}

