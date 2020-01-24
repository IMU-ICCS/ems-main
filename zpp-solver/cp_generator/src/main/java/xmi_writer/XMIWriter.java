package xmi_writer;

import constraint_problem_data.ConstraintProblemData_;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.paasage.upperware.metamodel.cp.VariableType;
import expressions.*;
import utils.NamesProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XMIWriter {

    private List<String> constants = new ArrayList<>();
    private List<String> variables = new ArrayList<>();
    private List<String> constraints = new ArrayList<>();
    private List<String> auxExpressions = new ArrayList<>();
    private Map<String, Integer> variableNameToIndex = new HashMap<>();

    public void writeToFile(ConstraintProblemData_ cp, String filePath) throws IOException {
        cp.getVariables().forEach( variable -> postVariable(variable, cp.getVariableDomain(variable.getVariableName())));
        cp.getConstraints().forEach( constraint -> postConstraint(constraint));
        writeContents(buildFileContent(), filePath);
    }

    private String buildFileContent() {
        StringBuilder result = new StringBuilder("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n" +
                "<cp:ConstraintProblem xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:cp=\"http://www.paasage.eu/eu/paasage/upperware/metamodel/cp\" xmlns:types=\"http://www.paasage.eu/eu/paasage/upperware/metamodel/types\" id=\"SAMPLECP\">\n");
        constants.forEach(result::append);
        variables.forEach(result::append);
        constraints.forEach(result::append);
        auxExpressions.forEach(result::append);
        result.append("</cp:ConstraintProblem>\n");
        return result.toString();
    }

    private void writeContents(String content, String filePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(content.getBytes());
    }

    private int postConstant(double constant) {
        int id = constants.size();
        constants.add(getConstantString(constant, Integer.toString(id)));
        return id;
    }

    private String getConstantString(Double constant, String id) {
        return "<constants id=\" "  + id + "\">\n" +
                "<value xsi:type=\"types:FloatValueUpperware\" value=\"" + constant.toString() +"\"/>\n" +
                "</constants>";
    }

    private void postVariable(VariableExpression variable, List<NumericValueInterface> domain) {
        variableNameToIndex.put(variable.getVariableName(), variables.size());
        if (domain == null) {
            System.out.println("Null variable is " + variable.getVariableName());
        }
        String component = NamesProvider.getComponentName(NamesProvider.variableNameToComponent(variable.getVariableName()));
        VariableType type = NamesProvider.variableNameToType(variable.getVariableName());
        String variableType = NamesProvider.variableTypeToString(type);
        String result = "<cpVariables id=\"" + variable.getVariableName() + "\" variableType=\"" + variableType
                    + "\" componentId=\"" + component + "\">\n";
             result += createIntegerDomain(domain);
         result += "</cpVariables>\n";
         variables.add(result);
    }

    private String createIntegerDomain(List<NumericValueInterface> values) {
        StringBuilder result = new StringBuilder("<domain xsi:type=\"cp:NumericListDomain\">\n");
        values.forEach(
                value -> {
                    result.append("<values xsi:type=\"types:IntegerValueUpperware\" value=\"" + ((Integer)value.getIntValue()).toString()
                            + "\"/>\n");
                }
        );
        result.append("</domain>\n");
        return result.toString();
    }

    private void postConstraint(Constraint constraint) {
        Integer id = constraints.size();
        String leftId = parseAuxExpressionElement(constraint.getExpression());
        String rightId = parseAuxExpressionElement(constraint.getConstant());
        String comparator = NamesProvider.comparatorTypeToName(constraint.getComparator());
        String result = "<constraints id=\"" + id.toString() + "\" exp1=\"//@" + leftId
                + "\" exp2=\"//@" + rightId + "\" comparator=\"" + comparator + "\"/>\n";
        constraints.add(result);
    }

    private String parseAuxExpressionElement(Expression expression) {
        if (expression instanceof ConstantExpression) {
            return "constants." + ( (Integer) postConstant(((ConstantExpression) expression).getValue().getDoubleValue())).toString();
        } else if (expression instanceof VariableExpression) {
            return "cpVariables." + variableNameToIndex.get(((VariableExpression) expression).getVariableName()).toString();
        } else return "auxExpressions." + postAuxExpression((ComposedExpression) expression);
    }

    private String postAuxExpression(ComposedExpression expression) {
        String leftId = parseAuxExpressionElement(expression.getLeftExpr());
        String rightId = parseAuxExpressionElement(expression.getRightExpr());
        Integer id = auxExpressions.size();
        String result = "<auxExpressions xsi:type=\"cp:ComposedExpression\" id=\"aux_expression_" + id.toString() +
                "\" expressions=\"//@" + leftId + " //@" + rightId + "\" operator=\"" + NamesProvider.operatorTypeToName(expression.getOper())
                + "\"/>\n";
        auxExpressions.add(result);
        return id.toString();
    }
}
