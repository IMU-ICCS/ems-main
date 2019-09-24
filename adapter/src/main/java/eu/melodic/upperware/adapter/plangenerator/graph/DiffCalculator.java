package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface DiffCalculator<T , R> {

    Map<R, DividedElement<T>> calculateDiff(Collection<T> newData, Collection<T> oldData,
                                         BiPredicate<T, T> predicate, Function<T, R> toGroupFunction);

    void print(String name, Map<R, DividedElement<T>> result);

    List<T> getToRemain(Map<R, DividedElement<T>> map);
    List<T> getToDelete(Map<R, DividedElement<T>> map);
    List<T> getToCreate(Map<R, DividedElement<T>> map);
}
