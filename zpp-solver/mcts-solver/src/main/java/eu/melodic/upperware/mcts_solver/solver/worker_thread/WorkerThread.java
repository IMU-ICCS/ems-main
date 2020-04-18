package eu.melodic.upperware.mcts_solver.solver.worker_thread;

import cp_wrapper.solution.CpSolution;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSingleTreeSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.OneToManyChannel;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.SolutionBuffer;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.FinalizationMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.Message;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.TemperatureMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils.messages.UtilityMessage;
import eu.melodic.upperware.mcts_solver.solver.utils.tree_printer.TreePrinter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class WorkerThread {
    private int pid;
    private int iterations;
    private SolutionBuffer solutionBuffer;
    private OneToManyChannel<Message, UtilityMessage> messageChannel;
    private MCTSSingleTreeSolver mctsSingleTreeSolver;
    private final boolean SAVE_TREE;

    private static final String RESOURCES_DIR = "zpp-solver/testing_module/src/main/resources/";

    public void workerRun() {
        boolean end = false;
        getInitialTemperature();
        while (!end) {
            log.info("Started MCTS worker with pid: {} for {} iterations", pid, iterations);
            sendSolution(pid, mctsSingleTreeSolver.solve());
            end = receiveMessageFromCoordinator();
        }
        log.info("MCTS worker " + pid + " has finished");
        if (SAVE_TREE) {
            saveResults();
        }
    }

    private boolean receiveMessageFromCoordinator() {
        log.info("MCTS worker {} has finished {} iterations", pid, iterations);
        Message message = messageChannel.workerReceive(pid);
        if (isFinalizationMessage(message)) {
            return true;
        } else if (isTemperatureMessage(message)) {
            mctsSingleTreeSolver.setSelectorCoefficient(((TemperatureMessage) message).getTemperature());
            log.info("Setting new temperature to {}", ((TemperatureMessage) message).getTemperature());
            return false;
        } else {
            throw new RuntimeException("Unrecognized message type!");
        }
    }

    private void getInitialTemperature() {
        Message message = messageChannel.workerReceive(pid);
        if (isTemperatureMessage(message)) {
            mctsSingleTreeSolver.setSelectorCoefficient(((TemperatureMessage) message).getTemperature());
            log.info("Setting new temperature to {}", ((TemperatureMessage) message).getTemperature());
        } else {
            throw new RuntimeException("Unrecognized message type!");
        }
    }

    private boolean isTemperatureMessage(Message message) {
        return message instanceof TemperatureMessage;
    }

    private boolean isFinalizationMessage(Message message) {
        return message instanceof FinalizationMessage;
    }

    private void sendSolution(int pid, CpSolution solution) {
        solutionBuffer.enqueue(solution);
        messageChannel.workerSend(new UtilityMessage(solution.getUtility(), pid));
    }

    private void saveResults() {
        log.info("Worker {} saves the tree...", pid);
        Node root = mctsSingleTreeSolver.getMctsTree().getRoot();
        try {
            TreePrinter.saveTreeDataToFile(root, RESOURCES_DIR + "tree" + pid, RESOURCES_DIR + "nodes" + pid, mctsSingleTreeSolver.getMctsWrapper());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
