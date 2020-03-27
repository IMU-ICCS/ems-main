package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.solution.CpSolution;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.OneToManyChannel;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.SolutionBuffer;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.FinalizationMessage;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.Message;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.TemperatureMessage;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.UtilityMessage;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactory;
import eu.melodic.upperware.mcts_solver.solver.utils.MaxRuntimeLimit;
import eu.melodic.upperware.mcts_solver.solver.worker_thread.WorkerThread;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

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
    private OneToManyChannel<Message, UtilityMessage> messageChannel;
    private SolutionBuffer solutionBuffer = new SolutionBuffer();

    public MCTSCoordinator(int numThreads, double minTemperature, double maxTemperature, int iterations) {
        this.numThreads = numThreads;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.iterations = iterations;
        this.messageChannel =  new OneToManyChannel<>(numThreads);
    }

    public CpSolution solve(int timeLimit, MCTSWrapperFactory mctsWrapperFactory) throws InterruptedException {
        List<MCTSWrapper> mctsWrappers = IntStream.range(0, numThreads)
                .mapToObj(thread -> mctsWrapperFactory.create())
                .collect(Collectors.toList());
        MaxRuntimeLimit maxRuntimeLimit = new MaxRuntimeLimit(timeLimit);
        List<Thread> threads = startWorkers(mctsWrappers);
        maxRuntimeLimit.startCounting();
        sendStartingTemperatures();
        while (!maxRuntimeLimit.limitExceeded()) {
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
                MCTSSolver mctsSolver =  new MCTSSolver(minTemperature , 10, iterations, mctsWrappers.get(pid));
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
