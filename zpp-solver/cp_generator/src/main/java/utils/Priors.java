package utils;

import eu.paasage.upperware.metamodel.cp.OperatorEnum;

public class Priors {
    public static double getOperationPrior(OperatorEnum oper) {
        switch (oper) {
            case PLUS:
            case MINUS:
                return 0.3;
            case TIMES:
                return 0.2;
            case DIV:
                return 0.15;
            default: //EQ
                return 0.05;
        }
    }
}
