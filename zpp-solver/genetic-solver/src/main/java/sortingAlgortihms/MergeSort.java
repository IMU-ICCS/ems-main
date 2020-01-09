package sortingAlgortihms;

import comparators.StochasticRankingComparator;
import implementation.ImplGene;
import io.jenetics.Phenotype;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort {
    private static Comparator<Phenotype<ImplGene, Double>> comparator = new StochasticRankingComparator(0.1);

    public static void merge(List<Phenotype<ImplGene, Double>> list, int from, int mid, int to) {
        int ptr1 = 0, ptr2 = 0;
        List<Phenotype<ImplGene, Double>> left, right;


        left = list.subList(from, mid);
        right = list.subList(mid, to);

        while (ptr1 + ptr2 < to - from) {
            if (ptr1 == mid - from) {
                list.set(ptr1 + ptr2, right.get(ptr2));
                ptr2++;
            }
            else if (ptr2 == to - mid) {
                list.set(ptr1 + ptr2, left.get(ptr1));
                ptr1++;
            }
            else if (comparator.compare(left.get(ptr1), right.get(ptr2)) > 0) {
                list.set(ptr1 + ptr2, left.get(ptr1));
                ptr1++;
            }
            else {
                list.set(ptr1 + ptr2, right.get(ptr2));
                ptr2++;
            }
        }
    }

    public static void sort(List<Phenotype<ImplGene, Double>> list, int from, int to) {
        if (to - from < 2)
            return;

        int mid = (to + from) / 2;
        sort(list, from, mid);
        sort(list, mid, to);

        merge(list, from, mid, to);
    }

    public static void sort(MSeq<Phenotype<ImplGene, Double>> seq) {
        sort(seq.asList(), 0, seq.size());
    }
}
