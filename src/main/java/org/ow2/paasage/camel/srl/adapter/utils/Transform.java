/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.utils;

import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.*;
import eu.paasage.camel.metric.ComparisonOperatorType;
import eu.paasage.camel.metric.MetricFunctionType;
import eu.paasage.camel.scalability.BinaryPatternOperatorType;

/**
 * Created by Frank on 19.05.2015.
 */
public class Transform {
    public static FormulaOperator operator(MetricFunctionType mft) {
        switch (mft) {
            case PLUS:
                return FormulaOperator.SUM;
            case MINUS:
                return FormulaOperator.MINUS;
            case TIMES:
                return FormulaOperator.MULTIPLY;
            case DIV:
                return FormulaOperator.DIV;
            case MODULO:
                return FormulaOperator.MODULO;
            case MEAN:
                return FormulaOperator.AVG;
            case STD:
                return FormulaOperator.STD;
            case COUNT:
                return FormulaOperator.COUNT;
            case MIN:
                return FormulaOperator.MIN;
            case MAX:
                return FormulaOperator.MAX;
            case PERCENTILE:
                return FormulaOperator.PERCENTILE;
            case DERIVATIVE:
                return FormulaOperator.DERIVATIVE;
            case MODE:
                return FormulaOperator.MODE;
            case MEDIAN:
                return FormulaOperator.MEDIAN;
            default: throw new RuntimeException("MetricFunctionType is not implemented");
        }
    }

    public static FormulaOperator condition(ComparisonOperatorType comparisonOperator) {
        switch (comparisonOperator) {
            case GREATER_THAN: return FormulaOperator.GT;
            case GREATER_EQUAL_THAN: return FormulaOperator.GT; /*TODO*/
            default: throw new RuntimeException("ComparisonOperatorType is not imlpemented");
        }
    }

    public static FormulaOperator binary(BinaryPatternOperatorType operator) {
        switch (operator) {
            case AND: return FormulaOperator.AND;
            case OR: return FormulaOperator.OR;
            default: throw new RuntimeException("BinaryPatternOperator is not implemented");
        }
    }
}
