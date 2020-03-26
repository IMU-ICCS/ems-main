package eu.melodic.upperware.mcts_solver.solver.concurrency_utils;

import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class OneToManyChannel<M> {
    private int numberOfWorkers;
    private MessageChannel<M> workerToCoordinatorChannel = new MessageChannel<>();
    private List<MessageChannel<M>> coordinatorToWorkerChannel = new ArrayList<>();

    public OneToManyChannel(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
        IntStream.range(0, numberOfWorkers).forEach(worker -> coordinatorToWorkerChannel.add(new MessageChannel<M>()));
    }

    public void workerSend(M message) {
        workerToCoordinatorChannel.send(message);
    }

    public M workerReceive(int pid) {
        return coordinatorToWorkerChannel.get(pid).receive();
    }

    public void coordinatorSend(M message, int receiverPid) {
        coordinatorToWorkerChannel.get(receiverPid).send(message);
    }

    public M coordinatorReceive() {
        return workerToCoordinatorChannel.receive();
    }
}
