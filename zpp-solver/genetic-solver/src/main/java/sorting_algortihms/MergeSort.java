package sorting_algortihms;

import comparators.StochasticRankingComparator;
import jenetics_implementation.GeneImpl;
import io.jenetics.Phenotype;
import io.jenetics.util.MSeq;

import java.util.Comparator;
import java.util.List;

/*
    Simple merge sort that sorts using our comparator.
 */
public class MergeSort {
    private static Comparator<Phenotype<GeneImpl, Double>> comparator = new StochasticRankingComparator(0);

    private static void merge(List<Phenotype<GeneImpl, Double>> list, int from, int mid, int to) {
        int pointerLeft = 0, pointerRight = 0;
        List<Phenotype<GeneImpl, Double>> left, right;

        left = list.subList(from, mid);
        right = list.subList(mid, to);

        while (pointerLeft + pointerRight < to - from) {
            if (pointerLeft == mid - from) {
                list.set(pointerLeft + pointerRight, right.get(pointerRight));
                pointerRight++;
            }
            else if (pointerRight == to - mid) {
                list.set(pointerLeft + pointerRight, left.get(pointerLeft));
                pointerLeft++;
            }
            else if (comparator.compare(left.get(pointerLeft), right.get(pointerRight)) > 0) {
                list.set(pointerLeft + pointerRight, left.get(pointerLeft));
                pointerLeft++;
            }
            else {
                list.set(pointerLeft + pointerRight, right.get(pointerRight));
                pointerRight++;
            }
        }
    }

    private static void sort(List<Phenotype<GeneImpl, Double>> list, int from, int to) {
        if (to - from < 2)
            return;

        int mid = (to + from) / 2;
        sort(list, from, mid);
        sort(list, mid, to);

        merge(list, from, mid, to);
    }

    public static void sort(MSeq<Phenotype<GeneImpl, Double>> seq) {
        sort(seq.asList(), 0, seq.size());
    }
}
