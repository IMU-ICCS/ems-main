package cp_wrapper.utils.runtime_limits;

public class TimeRuntimeLimit {
    private final long NANO_TO_MILLISECONDS = 1000000;
    private final long MILLISECONDS_TO_SECONDS = 1000;
    private int timeLimit;
    private long startTime;

    public TimeRuntimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void startCounting() {
        startTime = System.nanoTime();
    }

    public boolean limitExceeded() {
        long endTime = System.nanoTime();
        return (endTime - startTime) / NANO_TO_MILLISECONDS >= timeLimit * MILLISECONDS_TO_SECONDS;
    }
}