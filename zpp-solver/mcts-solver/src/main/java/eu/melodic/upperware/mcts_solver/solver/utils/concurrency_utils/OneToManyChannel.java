package eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class OneToManyChannel<MessageToWorker, MessageToCoordinator> {
    private MessageChannel<MessageToCoordinator> workerToCoordinatorChannel = new MessageChannel<>();
    private List<MessageChannel<MessageToWorker>> coordinatorToWorkerChannel = new ArrayList<>();

    public OneToManyChannel(int numberOfWorkers) {
        IntStream.range(0, numberOfWorkers).forEach(worker -> coordinatorToWorkerChannel.add(new MessageChannel<>()));
    }

    public void workerSend(MessageToCoordinator message) {
        workerToCoordinatorChannel.send(message);
    }

    public MessageToWorker workerReceive(int pid) {
        try {
            return coordinatorToWorkerChannel.get(pid).receive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void coordinatorSend(MessageToWorker message, int receiverPid) {
        coordinatorToWorkerChannel.get(receiverPid).send(message);
    }

    public MessageToCoordinator coordinatorReceive() {
        try {
            return workerToCoordinatorChannel.receive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
