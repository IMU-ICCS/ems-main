/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static java.lang.String.format;

@Slf4j
public abstract class AbstractDefaultGraphGenerator<T> implements GraphGenerator<T> {

    void addVertex(MelodicGraph<Task, DefaultEdge> graph, Task task) {
        log.debug("Adding vertex {}", task);
        graph.addVertex(task);
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, T from, U to) {
        addEdge(graph, from, to, ()-> true);
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, T from, U to, BooleanSupplier booleanSupplier) {
        if (booleanSupplier.getAsBoolean()) {
            Objects.requireNonNull(from, "From task is null");
            Objects.requireNonNull(to, "To task is null");
            log.debug("Adding edge from {} to {}", from, to);
            log.debug("Adding edge from data {} to data {}", from.getData().getName(), to.getData().getName());

            graph.addEdge(from, to);
        }
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, T from, List<U> to) {
        addEdge(graph, from, to, () -> true);
    }

    <T extends Task, U extends Task> void addWaitEdge(MelodicGraph<Task, DefaultEdge> graph, Supplier<T> from, List<U> to, BooleanSupplier booleanSupplier) {
        if (booleanSupplier.getAsBoolean()) {
            T t = from.get();
            to.forEach(u -> addEdge(graph, u, t, booleanSupplier));
        } else {
            log.debug("Supplier is not invoked");
        }
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, T from, List<U> to, BooleanSupplier booleanSupplier) {
        to.forEach(u -> addEdge(graph, from, u, booleanSupplier));
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, List<T> from, List<U> to, BiPredicate<T, U> biPredicate) {
        for (T t : from) {
            boolean wasSet = false;
            for (U u : to) {
                if (biPredicate.test(t, u)){
                    addEdge(graph, t, u);
                    wasSet = true;
                }
            }
            if (CONFIG.equals(graph.getType()) && !wasSet) {
                throw new IllegalStateException(
                        format("Missing obligatory node of graph - dependency between %s and %s was not set", taskToString(t), tasksToString(to)));
            }
        }
    }

    <T extends Task, U extends Task> void addReverseEdge(MelodicGraph<Task, DefaultEdge> graph, List<T> from, List<U> to, BiPredicate<T, U> biPredicate) {
        for (U u : to) {
            boolean wasSet = false;
            for (T t : from) {
                if (biPredicate.test(t, u)){
                    addEdge(graph, t, u);
                    wasSet = true;
                }
            }
            if (CONFIG.equals(graph.getType()) && !wasSet) {
                throw new IllegalStateException(
                        format("Missing obligatory node of graph - dependency between %s and %s was not set", taskToString(u), tasksToString(from)));
            }
        }
    }

    <T extends Task> String tasksToString(List<T> tasks){
        return tasks
                .stream()
                .map(this::taskToString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    <T extends Task> String taskToString(T task){
        return task.getClass().getSimpleName() + "{" + task.getData().getName() + "}";
    }

}
