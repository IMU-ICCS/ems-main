package eu.melodic.upperware.adapter.graphlogger;

import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by pszkup on 09.11.17.
 */
public interface GraphLogger {

    void logGraph(DirectedGraph<Task, DefaultEdge> taskGraph);

    void logCount(DirectedGraph<Task, DefaultEdge> taskGraph);

    void logCycles(DirectedGraph<Task, DefaultEdge> taskGraph);
}
