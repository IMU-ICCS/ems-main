package eu.melodic.upperware.mcts_solver.solver.utils;


public class MaxRuntime {
    private final long NANO_TO_MILLISECONDS = 1000000;
    private final long MILLISECONDS_TO_SECONDS = 1000;
    private int timeLimit;
    private long startTime;

    public MaxRuntime(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public boolean limitExceeded() {
        long endTime = System.nanoTime();
        return (endTime - startTime) / NANO_TO_MILLISECONDS >= timeLimit * MILLISECONDS_TO_SECONDS;
    }
}
