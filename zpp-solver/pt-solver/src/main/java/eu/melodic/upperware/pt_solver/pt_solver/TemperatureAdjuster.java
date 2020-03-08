package eu.melodic.upperware.pt_solver.pt_solver;

import cp_wrapper.utility_provider.ParallelUtilityProviderImpl;
import cp_wrapper.utils.constraint.Constraint;
import eu.melodic.upperware.pt_solver.pt_solver.utils.TemperatureAdjusterThreadData;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.search.stopcriteria.MaxRuntime;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class TemperatureAdjuster {
    private int timeLimit;
    private int numThreads;
    private List<UtilityGeneratorApplication> utilityGenerators;
    private ConstraintProblem cp;

    private final int MIN_TMP_DEFAULT = 100;
    private final int MAX_TMP_DEFAULT = 100000;
    private final int MIN_TIME_FOR_CHOSEN_TMP = 10;
    private final List<Integer> maxTmps = Arrays.asList(100, 1000, 10000, 100000, 50000, 5000);
    private final List<Integer> minTmps = Arrays.asList(100, 1000, 10000, 100000, 50000, 5000, 10, 1);

    private final List<Pair<List<VariableValueDTO>, Double>> solutions = Collections.synchronizedList(new ArrayList<>());
    private final List<TemperatureAdjusterThreadData> threadsInfo = Collections.synchronizedList(new ArrayList<>());

    public TemperatureAdjuster(int timeLimit, int numThreads, List<UtilityGeneratorApplication> utilityGenerators, ConstraintProblem cp) {
        this.timeLimit = timeLimit;
        this.numThreads = numThreads;
        this.utilityGenerators = utilityGenerators;
        this.cp = cp;
    }

    public Pair<List<VariableValueDTO>, Double> solve() {
        int secondsPerTestThread = getSecondsPerThread();
        int maxTmp = MAX_TMP_DEFAULT;
        int minTmp = MIN_TMP_DEFAULT;
        if (secondsPerTestThread != 0) {
            Pair<Integer, Integer> optimalMinMaxTmp = searchTemperatureSpace(secondsPerTestThread);
            maxTmp = optimalMinMaxTmp.getValue1();
            minTmp = optimalMinMaxTmp.getValue0();
        }
        int residualTimeLimit = getResidualTimeLimit(secondsPerTestThread);
        log.info("Setting min, max temperatures to: "+ minTmp + " " + maxTmp);
        PTSolver solver = new PTSolver(minTmp, maxTmp, numThreads, cp, new ParallelUtilityProviderImpl(utilityGenerators));
        solutions.add(solver.solve(new MaxRuntime(residualTimeLimit, TimeUnit.SECONDS)));
        return getBestSolution();
    }

    private int getResidualTimeLimit(int secondsPerThread) {
        return timeLimit - secondsPerThread;
    }

    private Pair<List<VariableValueDTO>, Double> getBestSolution() {
        AtomicReference<Pair<List<VariableValueDTO>, Double>> best = new AtomicReference<>(solutions.get(0));
        solutions.forEach( solution -> {
            if (solution.getValue1() > best.get().getValue1()) {
                best.set(solution);
            }
        });
        return best.get();
    }
    /*
        Decide whether there's time to search temperature space.
        If there's time - return number of seconds
        TODO
     */
    private int getSecondsPerThread() {
        int differentTemperatures = maxTmps.size() + minTmps.size();
        if (timeLimit - differentTemperatures < MIN_TIME_FOR_CHOSEN_TMP) {
            return 0;
        } else {
            return 1;
        }
    }

    private Pair<Integer, Integer> searchTemperatureSpace(int secondsPerThread) {

        for (int i = 0; i < maxTmps.size(); i+= numThreads) {
            testTemperatures(secondsPerThread, i, Math.min(maxTmps.size(), i + numThreads), maxTmps, true, 0);
        }
        int maxTmp = getBestTemperature();

        for (int i = 0; i < minTmps.size(); i+= numThreads) {
            testTemperatures(secondsPerThread, i, Math.min(minTmps.size(), i + numThreads), minTmps, false, maxTmp);
        }

        int minTmp = getBestTemperature();
        return new Pair<> (minTmp, maxTmp);
    }

    private int getBestTemperature() {
        final TemperatureAdjusterThreadData[] best = {threadsInfo.get(0)};
        threadsInfo.forEach( info -> {
            if (info.getSolution().getValue1() > best[0].getSolution().getValue1()) {
                best[0] = info;
            }
        });
        return best[0].getTemperature();
    }

    private void testTemperatures(int secondsPerThread, int leftIndex, int rightIndex, List<Integer> temperatures, boolean testOfMaxTemperature, int maxTemperature) {
        List<Thread> threads = new ArrayList<>();
        temperatures.subList(leftIndex, rightIndex).forEach(tmp -> {
            Thread thread = new Thread( () -> {
                PTSolver solver = testOfMaxTemperature ?
                        new PTSolver(tmp, tmp, 1, cp, new ParallelUtilityProviderImpl(utilityGenerators))
                        : new PTSolver(tmp, maxTemperature, 1, cp, new ParallelUtilityProviderImpl(utilityGenerators));
                Pair<List<VariableValueDTO>, Double> solution = solver.solve(new MaxRuntime(secondsPerThread, TimeUnit.SECONDS));
                solutions.add(solution);
                threadsInfo.add(new TemperatureAdjusterThreadData(solution, tmp));
            });
            threads.add(thread);
        });
        threads.forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }





}
