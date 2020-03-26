package eu.melodic.upperware.mcts_solver.solver.worker_thread;

import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.OneToManyChannel;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.SolutionBuffer;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.FinalizationMessage;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.Message;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.TemperatureMessage;
import eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages.UtilityMessage;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSolver;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class WorkerThread {
    private int pid;
    private int iterations;
    private SolutionBuffer solutionBuffer;
    private OneToManyChannel<Message, UtilityMessage> messageChannel;
    private MCTSSolver mctsSolver;

    public void workerRun() {
        boolean end = false;
        getInitialTemperature();
        while (!end) {
            log.info("Started MCTS worker with pid: {} for {} iterations", pid, iterations);
            sendSolution(pid, mctsSolver.solve());
            end = receiveMessageFromCoordinator();
        }
        log.info("MCTS worker " + pid + " has finished");
    }

    private boolean receiveMessageFromCoordinator() {
        log.info("MCTS worker {} has finished {} iterations", pid, iterations);
        Message message = messageChannel.workerReceive(pid);
        if (isFinalizationMessage(message)) {
            return true;
        } else if (isTemperatureMessage(message)) {
            mctsSolver.setSelectorCoefficient(((TemperatureMessage) message).getTemperature());
            log.info("Setting new temperature to {}", ((TemperatureMessage) message).getTemperature());
            return false;
        } else {
            throw new RuntimeException("Unrecognized message type!");
        }
    }

    private void getInitialTemperature() {
        Message message = messageChannel.workerReceive(pid);
        if (isTemperatureMessage(message)) {
            mctsSolver.setSelectorCoefficient(((TemperatureMessage) message).getTemperature());
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

    private void sendSolution(int pid, Pair<List<VariableValueDTO>, Double> solution) {
        solutionBuffer.enqueue(solution);
        messageChannel.workerSend(new UtilityMessage(solution.getValue1(), pid));
    }
}
