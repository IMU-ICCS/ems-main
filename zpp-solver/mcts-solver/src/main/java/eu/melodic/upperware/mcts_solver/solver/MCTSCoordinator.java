package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.solution.CpSolution;
import cp_wrapper.utils.runtime_limits.TimeRuntimeLimit;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.OneToManyChannel;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.SolutionBuffer;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.FinalizationMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.Message;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.TemperatureMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.UtilityMessage;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactory;
import eu.melodic.upperware.mcts_solver.solver.worker_thread.WorkerThread;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class MCTSCoordinator {
    private int numThreads;
    private double minTemperature;
    private double maxTemperature;
    private int iterations;
    private int nodeCountLimit;
    private OneToManyChannel<Message, UtilityMessage> messageChannel;
    private SolutionBuffer solutionBuffer = new SolutionBuffer();
    private AvailablePolicies policyType;


    public MCTSCoordinator(int numThreads, double minTemperature, double maxTemperature, int iterations, int nodeCountLimit, AvailablePolicies policyType) {
        this.numThreads = numThreads;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.iterations = iterations;
        this.nodeCountLimit = nodeCountLimit;
        this.messageChannel =  new OneToManyChannel<>(numThreads);
        this.policyType = policyType;
    }

    public CpSolution solve(int timeLimit, MCTSWrapperFactory mctsWrapperFactory) throws InterruptedException {
        List<MCTSWrapper> mctsWrappers = IntStream.range(0, numThreads)
                .mapToObj(thread -> mctsWrapperFactory.create())
                .collect(Collectors.toList());
        TimeRuntimeLimit timeRuntimeLimit = new TimeRuntimeLimit(timeLimit);
        List<Thread> threads = startWorkers(mctsWrappers);
        timeRuntimeLimit.startCounting();
        sendStartingTemperatures();
        while (!timeRuntimeLimit.limitExceeded()) {
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

                MCTSSolver mctsSolver =  new MCTSSolver(minTemperature , 10, iterations, nodeCountLimit, mctsWrappers.get(pid), policyType);

                WorkerThread workerThread = new WorkerThread(pid, iterations, solutionBuffer, messageChannel, mctsSolver);
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
