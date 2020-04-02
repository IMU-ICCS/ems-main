package eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessageChannel<M> {
    private final BlockingQueue<M> queue = new LinkedBlockingDeque<>();

    public void send(M message) {
        queue.add(message);
    }

    public M receive() throws InterruptedException {
        return queue.take();
    }
}
