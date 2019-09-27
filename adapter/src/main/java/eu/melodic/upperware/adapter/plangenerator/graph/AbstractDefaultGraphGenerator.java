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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

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

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, List<T> from, U to, BiPredicate<T, U> biPredicate) {
        addEdge(graph, from, Collections.singletonList(to), biPredicate);
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, T from, List<U> to, BiPredicate<T, U> biPredicate) {
        addEdge(graph, Collections.singletonList(from), to, biPredicate);
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, List<T> from, List<U> to, BiPredicate<T, U> biPredicate) {
        for (T f : from) {
            for (U t : to) {
                addEdge(graph, f, t, biPredicate);
            }
        }
    }

    <T extends Task, U extends Task> void addEdge(MelodicGraph<Task, DefaultEdge> graph, T from, U to, BiPredicate<T, U> biPredicate) {
        if (biPredicate.test(from, to)) {
            addEdge(graph, from, to);
        }
    }

}
