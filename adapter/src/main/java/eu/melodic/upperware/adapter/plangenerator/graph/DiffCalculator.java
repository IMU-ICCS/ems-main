package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface DiffCalculator<T , R> {

    Map<R, DividedElement<T>> calculateDiff(List<T> newData, List<T> oldData,
                                         BiPredicate<T, T> predicate, Function<T, R> toGroupFunction);

}
