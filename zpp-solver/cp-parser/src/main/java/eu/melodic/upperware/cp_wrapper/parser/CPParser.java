package eu.melodic.upperware.cp_wrapper.parser;
/*
    This class is responsible for transforming ConstraintProblem interface into
    CPParser class. The only work that is done here is transforming ComparisonExpression interface
    into less abstract ArConstraint instance.
 */

import eu.melodic.upperware.cp_wrapper.utils.constraint.Constraint;
import eu.melodic.upperware.cp_wrapper.utils.constraint.ConstraintImpl;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

import java.util.List;
import java.util.stream.Collectors;

public class CPParser {
    private CPParsedData cpParsedData;

    public CPParser() {
        cpParsedData = new CPParsedData();
    }

    public CPParsedData parse(ConstraintProblem cp) {
        parseConstants(cp);
        parseMetrics(cp);
        parseVariables(cp);
        parseConstraints(cp);
        cpParsedData.init();
        return cpParsedData;
    }

    private void parseConstants(ConstraintProblem cp) {
        cpParsedData.postConstants(cp.getConstants());
    }

    private void parseMetrics(ConstraintProblem cp) {
        cpParsedData.postMetrics(cp.getCpMetrics());
    }

    private void parseVariables(ConstraintProblem cp) {
        cpParsedData.postVariables(cp.getCpVariables());
    }

    private void parseConstraints(ConstraintProblem cp) {
        List<Constraint> constraints =
                cp.getConstraints().stream()
                .map(compExp -> new ConstraintImpl(compExp.getComparator(), compExp.getExp1(), compExp.getExp2()))
                .collect(Collectors.toList());
        cpParsedData.postConstraints(constraints);
    }
}
