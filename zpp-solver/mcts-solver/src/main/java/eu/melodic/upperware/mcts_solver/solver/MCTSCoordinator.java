package eu.melodic.upperware.mcts_solver.solver;

import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.OneToManyChannel;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.SolutionBuffer;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.FinalizationMessage;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.Message;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.TemperatureMessage;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.UtilityMessage;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.ArrayList;
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


    public Pair<List<VariableValueDTO>, Double> solve(int timeLimit, List<MCTSWrapper> mctsWrappers) {
        if (mctsWrappers.size() != numThreads) {
            throw new RuntimeException("DSD");
        }
        List<Thread> threads = startWorkers(mctsWrappers);
        long startTime = System.nanoTime();
        long endTime = startTime;
        while ((endTime - startTime)/1000000 <= 1000 * timeLimit) {
            setTemperatures();
            endTime = System.nanoTime();
        }
        stopWorkers();
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return solutionBuffer.getBestSolution();
    }

    private List<Thread> startWorkers(List<MCTSWrapper> mctsWrappers) {
        double temperatureDifference = (maxTemperature - minTemperature) / numThreads;
        return IntStream.range(0, numThreads).mapToObj(pid -> {
            Thread thread = new Thread( () -> workerRun(pid, minTemperature + pid*temperatureDifference, mctsWrappers.get(pid), iterations));
            thread.start();
            return thread;
        }).collect(Collectors.toList());
    }

    private void workerRun(int pid, double startingTemperature, MCTSWrapper mctsWrapper, int iterations) {
        boolean end = false;
        MCTSSolver mctsSolver = new MCTSSolver(startingTemperature, 10, iterations, mctsWrapper);
        while (!end) {
            log.info("Started MCTS worker with pid: {} for {} iterations", pid, iterations);
            Pair<List<VariableValueDTO>, Double> solution = mctsSolver.solve();
            solutionBuffer.enqueue(solution);
            messageChannel.workerSend(new UtilityMessage(solution.getValue1(), pid));
            Message message = messageChannel.workerReceive(pid);
            if (message instanceof FinalizationMessage) {
                end = true;
            } else if (message instanceof TemperatureMessage) {
                mctsSolver.setSelectorCoefficient(((TemperatureMessage) message).getTemperature());
                log.info("MCTS worker {} has finished {} iterations. Setting new temperature to {}", pid, iterations, ((TemperatureMessage) message).getTemperature());
            } else {
                throw new RuntimeException("ASDAS");
            }
        }
        log.info("MCTS worker " + pid + " has finished");
    }

    private void setTemperatures() {
        List<UtilityMessage> results = new ArrayList<>();
        //IntStream.range(0, numThreads).forEach( pid -> results.add((UtilityMessage) messageChannel.coordinatorReceive()));
        for (int i = 0;i < numThreads; i++) {
            UtilityMessage message = messageChannel.coordinatorReceive();
            results.add(message);
        }
        results.sort(Collections.reverseOrder());

        double tempDiff = (maxTemperature - minTemperature) / numThreads;
        double temp = minTemperature;
        for (int i = 0; i < numThreads; i++) {
            messageChannel.coordinatorSend(new TemperatureMessage(temp), results.get(i).getPid());
            temp += tempDiff;
        }
    }

    private void stopWorkers() {
        IntStream.range(0, numThreads).forEach(pid -> messageChannel.coordinatorSend(new FinalizationMessage(), pid));
    }
}
