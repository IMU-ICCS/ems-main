package eu.melodic.upperware.cpsolver.solver.parser;

import eu.melodic.upperware.cpsolver.solver.CpSolution;
import lombok.Builder;
import lombok.Getter;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.solver.variables.impl.FixedIntVarImpl;
import org.chocosolver.solver.variables.impl.FixedRealVarImpl;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
public class SolverParsedData {

    private Model model;

    private Map<String, IntVar> intVars = new HashMap<>();
    private Map<String, RealVar> realVars = new HashMap<>();

    public List<CpSolution> solve() {
        Solver solver = model.getSolver();
        List<CpSolution> result = new ArrayList<>();
        while (solver.solve()) {
            result.add(createCpSolution());
        }
        return result;
    }

    public List<CpSolution> solve(int timeLimit) {
        Solver solver = model.getSolver();
        solver.limitTime(timeLimit + "s");
        List<CpSolution> result = new ArrayList<>();
        Clock clock = Clock.systemDefaultZone();
        long startTime = clock.millis();
        long currentTime = startTime;
        while (currentTime - startTime <= 1000*timeLimit && solver.solve()) {
            result.add(createCpSolution());
            solver.limitTime(timeLimit + "s");
            currentTime = clock.millis();
        }
        return result;
    }

    private CpSolution createCpSolution() {

        Map<String, IntVar> intResult = intVars.values()
                .stream()
                .collect(Collectors.toMap(Variable::getName, intVar -> new FixedIntVarImpl(intVar.getName(), intVar.getValue(), this.model)));

        Map<String, RealVar> realResult = realVars.values()
                .stream()
                .filter(realVar -> !realVar.getName().startsWith("(real)"))
                .collect(Collectors.toMap(Variable::getName, realVar -> new FixedRealVarImpl(realVar.getName(), realVar.getLB(), this.model)));

        return CpSolution.builder()
                .intVars(intResult)
                .realVars(realResult)
                .build();
    }

}
