package CPWrapper;

import CPWrapper.Parser.CPParsedData;
import CPWrapper.Parser.CPParser;
import CPWrapper.Utils.HeuristicVariableOrderer;
import CPWrapper.Utils.VariableOrderer;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;


public class CPWrapper {
    private VariableOrderer variableOrderer;
    private CPParsedData cpParsedData;
    private UtilityGeneratorApplication utilityGenerator;

    public void parse(ConstraintProblem constraintProblem, UtilityGeneratorApplication utility) {
        CPParser cpParser = new CPParser();
        cpParsedData = cpParser.parse(constraintProblem);
        this.utilityGenerator = utility;
        this.variableOrderer = new HeuristicVariableOrderer(cpParsedData.getConstraintGraph());
    }

    public void setVariableOrdering(VariableOrderer vO) {
        this.variableOrderer = vO;
    }




    private void orderVariablesRandomly() {
        //TODO
    }
}
