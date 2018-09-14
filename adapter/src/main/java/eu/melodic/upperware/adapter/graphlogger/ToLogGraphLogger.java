package eu.melodic.upperware.adapter.graphlogger;

import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by pszkup on 09.11.17.
 */

@Slf4j
@Service
@AllArgsConstructor
public class ToLogGraphLogger implements GraphLogger {

    @Override
    public void logGraph(DirectedGraph<Task, DefaultEdge> taskGraph) {

        List<Pair<Task, Task>> result = new ArrayList<>();

        List<GNode> nodes = new ArrayList<>();

        DirectedNeighborIndex<Task, DefaultEdge> neighbors = new DirectedNeighborIndex<>(taskGraph);
        TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator<>(taskGraph);

        while (it.hasNext()) {
            Task task = it.next();
            Set<Task> dependentTasks = neighbors.predecessorsOf(task);

            if (dependentTasks.isEmpty()) {
                result.add(Pair.of(null, task));
            } else {
                dependentTasks.forEach(dependentTask -> result.add(Pair.of(dependentTask, task)));
            }
        }

        for (Pair<Task, Task> pair : result) {
            Optional<GNode> nodeWithParent = getNodeWithParent(pair.getLeft(), nodes);
            if (nodeWithParent.isPresent()){
                nodeWithParent.get().getChildren().add(pair.getRight());
            } else {
                GNode newGNode = new GNode();
                newGNode.setParent(pair.getLeft());
                newGNode.getChildren().add(pair.getRight());
                nodes.add(newGNode);
            }
        }

        log.info("GRAPH");
        for (GNode gNode: nodes){
            log.info(gNode.toString());
        }
    }

    @Override
    public void logCount(DirectedGraph<Task, DefaultEdge> taskGraph) {
        TopologicalOrderIterator<Task, DefaultEdge> it1 = new TopologicalOrderIterator<>(taskGraph);

        int counter = 0;
        while (it1.hasNext()){
            it1.next();
            counter++;
        }
        log.info("GRAPH COUNT: {}", counter);
    }

    @Override
    public void logCycles(DirectedGraph<Task, DefaultEdge> taskGraph) {
        CycleDetector<Task, DefaultEdge> cycleDetector = new CycleDetector<>(taskGraph);
        boolean hasCycles = cycleDetector.detectCycles();
        log.info("GRAPH HAS CYCLES: {}", hasCycles);
        if (hasCycles) {
            log.info("CYCLE BEGIN:");
            cycleDetector.findCycles().forEach(task -> log.info("Task {}", task));
            log.info("CYCLE END!");
        }
    }

    private Optional<GNode> getNodeWithParent(Task right, List<GNode> nodes) {
        return nodes.stream()
                .filter(gNode -> gNode.getParent() != null && gNode.getParent().equals(right))
                .findFirst();
    }
}
