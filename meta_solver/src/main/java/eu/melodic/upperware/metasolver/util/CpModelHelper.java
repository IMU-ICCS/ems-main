/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.util;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Function;

@Component
@Slf4j
public class CpModelHelper extends AbstractCdoHelper {

    protected ConstraintProblem getConstraintProblemAtPath(CDOTransaction transaction, String cpModelPath) {
        CDOResource resource = transaction.getResource(cpModelPath);
        return (ConstraintProblem) resource.getContents().get(0);
    }

    public boolean updateCpModelWithMetricValues(String applicationId, String cpModelPath, Map<String, String> metricValues) throws ConcurrentAccessException {
        log.debug("CpModelHelper.updateCpModelWithMetricValues(): BEGIN: helper-id={}, app-id={}, cp-path={}, mvv={}", id, applicationId, cpModelPath, metricValues);

        return
                processInTransaction(cpModelPath, "updateCpModelWithMetricValues()", transaction -> {
                    // retrieve CP model
                    ConstraintProblem cpModel = getConstraintProblemAtPath(transaction, cpModelPath);

                    // check if all metric variable names in CP model exist in 'metricValues' map
                    /*EList<MetricVariable> cpMetricVarList = cpModel.getMetricVariables();
                    boolean allfound = true;
                    for (MetricVariable mv : cpMetricVarList) {
                        log.info("CpModelHelper.updateCpModelWithMetricValues():  Found Metric Variable: id={}, type={}", mv.getId(), mv.getType());
                        if (!metricValues.containsKey(mv.getId())) {
                            log.error("CpModelHelper.updateCpModelWithMetricValues(): NOT FOUND Metric Variable: id={}", mv.getId());
                            allfound = false;
                            //XXX: -OR- ???
                            // any missing variables must be added with a default value (WHERE CAN WE FIND 'default metric variable values'??)
                        }
                    }
                    if (!allfound) {
                        log.debug("CpModelHelper.updateCpModelWithMetricValues(): END: helper-id={}, message=Missing MVV", id);
                        return false;
                    }*/


                    // add metric variable values for all (extracted) metric variable names
                    EList<CpMetric> cpMetricList = cpModel.getCpMetrics();
                    for (CpMetric c : cpMetricList) {
                        String mvName = c.getId().trim();
                        String mvValue = metricValues.get(mvName);
                        if (mvValue != null && !mvValue.isEmpty()) {
                            log.info("Updating metric: {} with value: {} in CP model.", mvName, mvValue);
                            BasicTypeEnum type = c.getType();
                            NumericValueUpperware newVal = null;
                            switch (type) {
                                case INTEGER:
                                    newVal = TypesFactory.eINSTANCE.createIntegerValueUpperware();
                                    ((IntegerValueUpperware) newVal).setValue((int) Double.parseDouble(mvValue));
                                    break;
                                case FLOAT:
                                    newVal = TypesFactory.eINSTANCE.createFloatValueUpperware();
                                    ((FloatValueUpperware) newVal).setValue((float) Double.parseDouble(mvValue));
                                    break;
                                case DOUBLE:
                                    newVal = TypesFactory.eINSTANCE.createDoubleValueUpperware();
                                    ((DoubleValueUpperware) newVal).setValue(Double.parseDouble(mvValue));
                                    break;
                                case LONG:
                                    newVal = TypesFactory.eINSTANCE.createLongValueUpperware();
                                    ((LongValueUpperware) newVal).setValue((long) Double.parseDouble(mvValue));
                                    break;
                            }
                            c.setValue(newVal);
                        } else {
                            log.debug("Skipped metric update (no value): {}", mvName);
                        }
                    }

                    log.debug("CpModelHelper.updateCpModelWithMetricValues(): END: helper-id={}", id);
                    return true;

                }, false);
    }

    public double[] getSolutionUtilities(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.debug("CpModelHelper.getSolutionUtilities(): BEGIN: helper-id={}, app-id={}, cp-path={}", id, applicationId, cpModelPath);

        return
                processInTransaction(cpModelPath, "getSolutionUtilities()", transaction -> {
                    // retrieve CP model
                    ConstraintProblem cpModel = getConstraintProblemAtPath(transaction, cpModelPath);

                    // get solutions list
                    EList<Solution> solutions = cpModel.getSolution();
                    int size = solutions.size();

                    double[] retUv = new double[2];

                    // No solutions in CP model
                    if (size == 0) {
                        retUv[0] = retUv[1] = -2;
                        return retUv;
                    }

                    // get deployed and candidate solution positions in list
                    int depPos = cpModel.getDeployedSolutionId();
                    int newPos = cpModel.getCandidateSolutionId();

                    // get deployed solution's utility value, if a deployed solution exists
                    if (depPos < size && depPos >= 0) {
                        Solution depSol = solutions.get(depPos);
                        retUv[0] = ((DoubleValueUpperware) depSol.getUtilityValue()).getValue();
                    } else
                        retUv[0] = -1;

                    // get new solution's utility value
                    if (newPos < size && newPos >= 0) {
                        Solution newSol = solutions.get(newPos);
                        retUv[1] = ((DoubleValueUpperware) newSol.getUtilityValue()).getValue();
                    } else
                        retUv[1] = -1;

                    log.debug("CpModelHelper.getSolutionUtilities(): END: helper-id={}, solution-utilities={}", id, retUv);
                    return retUv;
                }, null);
    }

    public Pair<Integer,Integer> updateSolutionIdsInCpModel(String applicationId, String cpModelPath, boolean success) throws ConcurrentAccessException {
        log.debug("CpModelHelper.updateSolutionIdsInCpModel(): BEGIN: helper-id={}, app-id={}, cp-path={}, success={}", id, applicationId, cpModelPath, success);

        return
                processInTransaction(cpModelPath, "updateSolutionIdsInCpModel()", transaction -> {
                    // retrieve CP model
                    ConstraintProblem cpModel = getConstraintProblemAtPath(transaction, cpModelPath);

                    // get current solution Ids
                    int depSolPos = cpModel.getDeployedSolutionId();
                    int canSolPos = cpModel.getCandidateSolutionId();
                    log.debug("updateSolutionIdsInCpModel(): depSolPos={}, canSolPos={}", depSolPos, canSolPos);

                    // update solution Ids
                    if (success && canSolPos >= 0) {
                        // set deployed solution id to candidate solution id
                        cpModel.setDeployedSolutionId(canSolPos);
                        log.trace("updateSolutionIdsInCpModel(): deployed solution id set: {}", canSolPos);
                    } else if (success) {
                        log.warn("updateSolutionIdsInCpModel(): No candidate solution found");
                    }
                    // clear candidate solution id
                    cpModel.setCandidateSolutionId(-1);
                    log.trace("updateSolutionIdsInCpModel(): candidate solution id cleared: -1");

                    // get new solution Ids
                    Pair<Integer, Integer> retPos = Pair.<Integer, Integer>of(cpModel.getDeployedSolutionId(), cpModel.getCandidateSolutionId());
                    log.debug("updateSolutionIdsInCpModel(): new solution id's: {}", retPos);

                    log.debug("CpModelHelper.updateSolutionIdsInCpModel(): END: helper-id={}, solution-id's={}", id, retPos);
                    return retPos;
                }, null);
    }

    public int findAndSetCandidateSolutionIdInCpModel(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.debug("CpModelHelper.findAndSetCandidateSolutionIdInCpModel(): BEGIN: helper-id={}, app-id={}, cp-path={}", id, applicationId, cpModelPath);

        return
                processInTransaction(cpModelPath, "findAndSetCandidateSolutionIdInCpModel()", transaction -> {
                    // retrieve CP model
                    ConstraintProblem cpModel = getConstraintProblemAtPath(transaction, cpModelPath);

                    // get current candidate solution Id
                    int oldPos = cpModel.getCandidateSolutionId();
                    log.debug("CpModelHelper.findAndSetCandidateSolutionIdInCpModel(): helper-id={}, old-candidate-solution-position={}", id, oldPos);

                    // find new candidate solution id
                    EList<Solution> solutions = cpModel.getSolution();
                    int size = solutions.size();
                    int position = size - 1;

                    // update candidate solution Id
                    cpModel.setCandidateSolutionId(position);
                    log.debug("CpModelHelper.findAndSetCandidateSolutionIdInCpModel(): helper-id={}, new-candidate-solution-position={}", id, position);

                    // get new candidate solution id
                    int newId = cpModel.getCandidateSolutionId();
                    log.debug("CpModelHelper.findAndSetCandidateSolutionIdInCpModel(): END: helper-id={}, new-candidate-id={}", id, newId);
                    return newId;

                }, -2);
    }

    public void copyVarValuesFromDeployedSolution(String applicationId, String cpModelPath, Map<String,String> fromToMap) throws ConcurrentAccessException {
        log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): BEGIN: helper-id={}, app-id={}, cp-path={}, from-to-map={}", id, applicationId, cpModelPath, fromToMap);

        processInTransaction(cpModelPath, "copyVarValuesFromDeployedSolution()", transaction -> {
            // retrieve CP model
            ConstraintProblem cpModel = getConstraintProblemAtPath(transaction, cpModelPath);

            // get solutions list
            EList<Solution> solutions = cpModel.getSolution();
            int size = solutions.size();

            // No solutions in CP model
            if (size == 0) {
                log.warn("CpModelHelper.copyVarValuesFromDeployedSolution(): CP model contains no solutions");
                return null;
            }

            // get deployed solution position in list
            int depPos = cpModel.getDeployedSolutionId();
            log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): Deployed solution id: {}", depPos);
            if (depPos >= size || depPos < 0) {
                log.warn("CpModelHelper.copyVarValuesFromDeployedSolution(): Invalid deployed solution id: size={}, id={}", size, depPos);
                return null;
            }

            // find deployed solution
            Solution depSol = solutions.get(depPos);
            log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): Deployed solution: id={}, timestamp={}, utility={}",
                    depPos, depSol.getTimestamp(), depSol.getUtilityValue());

            // get variable values from deployed solution
            final EList<CpVariableValue> valuesList = depSol.getVariableValue();
            Map<String,CpVariableValue> valuesMap = valuesList.stream()
                    .collect(Collectors.toMap(o -> o.getVariable().getId(), Function.identity()));
            log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): Deployed solution Variable Values: {}", valuesMap.keySet());

            // get target metrics from cp model
            final EList<CpMetric> cpMetrics = cpModel.getCpMetrics();
			log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): CP model Metric names: {}",
                    cpMetrics.stream().map(CPElement::getId).collect(Collectors.toList()));
            Map<String,CpMetric> metricsMap = cpMetrics.stream()
                    .collect(Collectors.toMap(CPElement::getId, Function.identity()));
            log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): CP model Metrics: {}", metricsMap.keySet());

            // Copy values
            for (Map.Entry<String,String> e : fromToMap.entrySet()) {
                String fromName = e.getKey();
                String toName = e.getValue();
                log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): Coping from {} to {}", fromName, toName);

                // From variable value
                final CpVariableValue fromVarValue = valuesMap.get(fromName);
                if (fromVarValue==null) {
                    log.warn("CpModelHelper.copyVarValuesFromDeployedSolution():   From Var.Value not found: {}", fromName);
                    continue;
                }
                final NumericValueUpperware fromValue = fromVarValue.getValue();

                // get Target metric
                CpMetric toMetric = metricsMap.get(toName);
                if (toMetric==null) {
                    log.warn("CpModelHelper.copyVarValuesFromDeployedSolution():   To Metric not found: {}", toName);
                    continue;
                }

                // create a copy of the value
                double value = numericValueUpperwareToDouble(fromValue);
                log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): Value: {} {}", numericValueUpperwareType(fromValue), value);

                NumericValueUpperware toValue = createNumericValueUpperware(numericValueUpperwareType(fromValue), value);
                log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): toValue: {}", toValue);

                // Copy value (clone) to Target metric
                toMetric.setValue(toValue);

                log.info("CpModelHelper.copyVarValuesFromDeployedSolution(): Copied from {} to {}: value={}", fromName, toName, numericValueUpperwareToDouble(fromValue));
            }

            return null;
        });

        log.debug("CpModelHelper.copyVarValuesFromDeployedSolution(): END: helper-id={}", id);
    }

    public static double numericValueUpperwareToDouble(NumericValueUpperware value) {
        return numericValueUpperwareToNumber(value).doubleValue();
    }

    public static Number numericValueUpperwareToNumber(NumericValueUpperware value) {
        if (value==null) throw new IllegalArgumentException("Argument is null");
        if (value instanceof IntegerValueUpperware) return ((IntegerValueUpperware)value).getValue();
        if (value instanceof FloatValueUpperware) return ((FloatValueUpperware)value).getValue();
        if (value instanceof DoubleValueUpperware) return ((DoubleValueUpperware)value).getValue();
        if (value instanceof LongValueUpperware) return ((LongValueUpperware)value).getValue();
        throw new IllegalArgumentException("Argument is not Integer/Float/Double/LongValueUpperware: "+value.getClass());
    }

    public static BasicTypeEnum numericValueUpperwareType(NumericValueUpperware value) {
        if (value==null) throw new IllegalArgumentException("Argument is null");
        if (value instanceof IntegerValueUpperware) return BasicTypeEnum.INTEGER;
        if (value instanceof FloatValueUpperware) return BasicTypeEnum.FLOAT;
        if (value instanceof DoubleValueUpperware) return BasicTypeEnum.DOUBLE;
        if (value instanceof LongValueUpperware) return BasicTypeEnum.LONG;
        throw new IllegalArgumentException("Argument is not Integer/Float/Double/LongValueUpperware: "+value.getClass());
    }

    public static NumericValueUpperware createNumericValueUpperware(BasicTypeEnum type, double value) {
        if (type==null) throw new IllegalArgumentException("Argument #0 is null");
        NumericValueUpperware newValue = null;
        switch (type) {
            case INTEGER:
                IntegerValueUpperware intValue = TypesFactory.eINSTANCE.createIntegerValueUpperware();
                intValue.setValue((int)value);
                newValue = intValue;
                break;
            case DOUBLE:
                DoubleValueUpperware doubleValue = TypesFactory.eINSTANCE.createDoubleValueUpperware();
                doubleValue.setValue(value);
                newValue = doubleValue;
                break;
            case FLOAT:
                FloatValueUpperware floatValue = TypesFactory.eINSTANCE.createFloatValueUpperware();
                floatValue.setValue((float)value);
                newValue = floatValue;
                break;
            case LONG:
                LongValueUpperware longValue = TypesFactory.eINSTANCE.createLongValueUpperware();
                longValue.setValue((long)value);
                newValue = longValue;
                break;
            default:
                throw new IllegalArgumentException("Argument #0 is not Integer/Float/Double/LongValueUpperware: "+type);
        }
        return newValue;
    }
}
