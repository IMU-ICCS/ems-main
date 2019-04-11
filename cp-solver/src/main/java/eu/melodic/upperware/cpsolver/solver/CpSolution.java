package eu.melodic.upperware.cpsolver.solver;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class CpSolution {

    private Map<String, IntVar> intVars = new HashMap<>();
    private Map<String, RealVar> realVars = new HashMap<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("intVars", intVars.values())
                .append("realVars", realVars.values())
                .toString();
    }
}
