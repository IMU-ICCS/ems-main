package eu.melodic.upperware.cpsolver.solver.parser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.continuous.arithmetic.CArExpression;
import org.chocosolver.solver.expression.discrete.arithmetic.ArExpression;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
public class SolverData {

    @Setter(AccessLevel.NONE)
    private Model model;

    private Map<String, IntVar> intVars = new HashMap<>();
    private Map<String, RealVar> realVars = new HashMap<>();

    private Map<String, IntVar> intConstants = new HashMap<>();
    private Map<String, RealVar> realConstants = new HashMap<>();

    private Map<String, IntVar> intMetrics = new HashMap<>();
    private Map<String, RealVar> realMetrics = new HashMap<>();

    private Map<String, ArExpression> auxExpressions = new HashMap<>();
    private Map<String, CArExpression> realAuxExpressions = new HashMap<>();

    SolverData(Model model) {
        this.model = model;
    }

    SolverParsedData toSolverParsedData() {
        return SolverParsedData.builder()
                .model(this.model)
                .intVars(this.intVars)
                .realVars(this.realVars)
                .build();
    }

    @Override
    public String toString() {
        return "SolverData{" +
                "  intVars=" + toIntString(intVars) +
                ", realVars=" + toRealString(realVars) +
                ", intConstants=" + toIntString(intConstants) +
                ", realConstants=" + toRealString(realConstants) +
                ", intMetrics=" + toIntString(intMetrics) +
                ", realMetrics=" + toRealString(realMetrics) +
                '}';
    }

    private String toIntString(Map<String, IntVar> map) {
        return map.entrySet().stream().map(stringIntVarEntry -> "{" + stringIntVarEntry.getKey() + "," + stringIntVarEntry.getValue().getValue() + "}").collect(Collectors.joining(", ", "[", "]"));
    }

    private String toRealString(Map<String, RealVar> map) {
        return map.entrySet().stream().map(stringIntVarEntry -> "{" + stringIntVarEntry.getKey() + "," + stringIntVarEntry.getValue().getLB() + "}").collect(Collectors.joining(", ", "[", "]"));
    }

}
