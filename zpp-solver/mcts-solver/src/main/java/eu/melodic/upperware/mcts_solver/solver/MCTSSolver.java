package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.solution.CpSolution;

import cp_wrapper.utils.runtime_limits.RuntimeLimit;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.OneToManyChannel;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.SolutionBuffer;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.FinalizationMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.Message;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.TemperatureMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.UtilityMessage;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSingleTreeSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactory;
import eu.melodic.upperware.mcts_solver.solver.worker_thread.WorkerThread;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class MCTSSolver {
    private int numThreads;
    private double minTemperature;
    private double maxTemperature;
    private int iterations;
    private final static int NODE_COUNT_LIMIT = 2000000;
    private OneToManyChannel<Message, UtilityMessage> messageChannel;
    private SolutionBuffer solutionBuffer = new SolutionBuffer();
    private AvailablePolicies policyType;
    private final boolean SAVE_TREE;

    public MCTSSolver(int numThreads, double minTemperature, double maxTemperature, int iterations, AvailablePolicies policyType, boolean saveTree) {
        this.numThreads = numThreads;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.iterations = iterations;
        this.messageChannel =  new OneToManyChannel<>(numThreads);
        this.policyType = policyType;
        this.SAVE_TREE = saveTree;
    }

    public CpSolution solve(int timeLimit, MCTSWrapperFactory mctsWrapperFactory) throws InterruptedException {
        List<MCTSWrapper> mctsWrappers = IntStream.range(0, numThreads)
                .mapToObj(thread -> mctsWrapperFactory.create())
                .collect(Collectors.toList());

        RuntimeLimit runtimeLimit = new RuntimeLimit(timeLimit);
        List<Thread> threads = startWorkers(mctsWrappers);
        runtimeLimit.startCounting();
        sendStartingTemperatures();
        while (!runtimeLimit.limitExceeded()) {
            setTemperatures(getWorkersUtilities());
        }
        stopWorkers();
        for (Thread thread : threads) {
            thread.join();
        }
        return solutionBuffer.getBestSolution();
    }

    private List<Thread> startWorkers(List<MCTSWrapper> mctsWrappers) {
        return IntStream.range(0, numThreads).mapToObj(pid -> {
            Thread thread = new Thread( () -> {
                MCTSSingleTreeSolver mctsSingleTreeSolver =  new MCTSSingleTreeSolver(minTemperature , 10, iterations, NODE_COUNT_LIMIT / numThreads, mctsWrappers.get(pid), policyType);
                WorkerThread workerThread = new WorkerThread(pid, iterations, solutionBuffer, messageChannel, mctsSingleTreeSolver, SAVE_TREE);
                workerThread.workerRun();
            });
            thread.start();
            return thread;
        }).collect(Collectors.toList());
    }

    private void sendStartingTemperatures() {
        setTemperatures(IntStream.range(0, numThreads).mapToObj(pid -> new UtilityMessage(0.0, pid))
                .collect(Collectors.toList()));
    }

    private List<UtilityMessage> getWorkersUtilities() {
        return IntStream.range(0, numThreads).mapToObj(pid -> messageChannel.coordinatorReceive())
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    private void setTemperatures(List<UtilityMessage> results) {
        double tempDiff = (maxTemperature - minTemperature) / numThreads;
        IntStream.range(0, numThreads).forEach(thread -> messageChannel.coordinatorSend(new TemperatureMessage(minTemperature + thread*tempDiff), results.get(thread).getPid()));
    }

    private void stopWorkers() {
        IntStream.range(0, numThreads).forEach(pid -> messageChannel.coordinatorSend(new FinalizationMessage(), pid));
    }
}
