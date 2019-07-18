package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class DefaultDiffCalculator<T, R> implements DiffCalculator<T, R> {
    
    @Override
    public Map<R, DividedElement<T>> calculateDiff(List<T> newElements, List<T> oldElements,
                                                BiPredicate<T, T> predicate, Function<T, R> toGroupFunction) {

        Map<R, DividedElement<T>> result = new HashMap<>();

        List<R> elementsToGroup = Stream.concat(newElements.stream(), oldElements.stream()).map(toGroupFunction).collect(toList());
        for (R elementToGroup : elementsToGroup) {
            Predicate<T> tPredicate = element -> toGroupFunction.apply(element).equals(elementToGroup);

            List<T> newElementsByTask = getFiltered(newElements, tPredicate);
            List<T> oldElementsByTask = getFiltered(oldElements, tPredicate);

            DividedElement dividedElement = DividedElement.<T>builder()
                    .toCreate(getDataToCreate(newElementsByTask, oldElementsByTask, predicate))
                    .toDelete(getDataToDelete(newElementsByTask, oldElementsByTask, predicate))
                    .toRemain(getDataToRemain(newElementsByTask, oldElementsByTask, predicate))
                    .build();

            result.put(elementToGroup, dividedElement);
        }

        return result;
    }

    private List<T> getFiltered(Collection<T> elements, Predicate<T> tPredicate){
        return elements.stream().filter(tPredicate).collect(toList());
    }

    private List<T> getDataToCreate(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return getData(p1, p2, biPredicate);
    }

    private List<T> getDataToDelete(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        //we need to change arguments
        return getData(p2, p1, biPredicate);
    }

    private List<T> getDataToRemain(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return p1.stream()
                .filter(oldReq -> p2.stream().anyMatch(newReq -> biPredicate.test(newReq, oldReq)))
                .collect(toList());
    }

    private List<T> getData(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return p1.stream()
                .filter(oldReq -> p2.stream().noneMatch(newReq -> biPredicate.test(newReq, oldReq)))
                .collect(toList());
    }

}
