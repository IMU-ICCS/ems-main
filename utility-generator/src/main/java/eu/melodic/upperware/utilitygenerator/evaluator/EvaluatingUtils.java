/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.model.IntVar;
import eu.melodic.upperware.utilitygenerator.model.RealVar;
import eu.melodic.upperware.utilitygenerator.model.Var;
import eu.melodic.upperware.utilitygenerator.model.VariableDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.cache.NodeCandidatePredicates.*;
import static java.lang.String.format;

@Slf4j
class EvaluatingUtils {


    /* mapping variables from solution and cp model */

    static Collection<String> getVariableNames(String componentId, List<VariableDTO> variables) {

        return variables.stream()
                .filter(variable -> componentId.equals(variable.getComponentId()))
                .map(VariableDTO::getId)
                .collect(Collectors.toList());
    }

    static String getVariableName(String componentId, VariableType type, List<VariableDTO> variables) {
        return variables.stream()
                .filter(v -> ((componentId.equals(v.getComponentId())) && type.equals(v.getType())))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable with type %s for component %s does not exist", type, componentId)))
                .getId();
    }


    static Map<String, Integer> getCardinalitiesForComponent(Collection<IntVar> newConfiguration, List<VariableDTO> variables) {

        Map<String, Integer> cardinalitiesForComponent = new HashMap<>();

        Collection<VariableDTO> cardinalities = variables.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .collect(Collectors.toList());

        newConfiguration.forEach(intVar -> cardinalities
                .stream()
                .filter(c -> intVar.getName().equals(c.getId()))
                .findFirst()
                .ifPresent(variable -> cardinalitiesForComponent.put(variable.getComponentId(), intVar.getValue())));
        return cardinalitiesForComponent;
    }

    static int getProviderValue(String componentId, List<VariableDTO> variables, Collection<IntVar> newConfigurationInt) {

        String provider = getVariableName(componentId, VariableType.PROVIDER, variables);
        return newConfigurationInt.stream()
                .filter(intVar -> provider.equals(intVar.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", provider)))
                .getValue();

    }

    private static VariableType getVariableType(String name, List<VariableDTO> variables) {
        return variables.stream()
                .filter(variable -> name.equals(variable.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", name)))
                .getType();
    }


    //todo - for real var
    //todo saving only important variables
    static Collection<Var> convertSolution(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {

        return newConfigurationInt.stream()
                .map(intVar -> new IntVar(intVar.getName(), intVar.getValue()))
                .collect(Collectors.toList());
    }

    static Predicate<NodeCandidate>[] makePredicatesFromSolution(String componentId, Collection<IntVar> newConfigurationInt,
            Collection<RealVar> newConfigurationReal, List<VariableDTO> variables) {

        Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);

        List<IntVar> variablesIntForComponent = newConfigurationInt.stream()
                .filter(intVar -> variableNamesForComponent.contains(intVar.getName()))
                .collect(Collectors.toList());

        List<RealVar> variablesRealForComponent = newConfigurationReal.stream()
                .filter(realVar -> variableNamesForComponent.contains(realVar.getName()))
                .collect(Collectors.toList());

        List<Predicate<NodeCandidate>> predicates = new ArrayList<>();


        for (IntVar var : variablesIntForComponent) {
            VariableType type = getVariableType(var.getName(), variables);

            switch (type) {
                case RAM:
                    log.debug("Creating getRamPredicate for value {}", (long) var.getValue());
                    predicates.add(getRamPredicate((long) var.getValue()));
                    break;
                case CARDINALITY:
                    break;
                case PROVIDER:
                    break;
                case CPU:
                    break;
                case CORES:
                    log.debug("Creating getCoresPredicate for value {}", var.getValue());
                    predicates.add(getCoresPredicate(var.getValue()));
                    break;
                case OS:
                    log.debug("Creating getOsPredicate for value {}", var.getValue());
                    predicates.add(getOsPredicate(var.getValue()));
                    break;
                case LOCATION:
                    break;

            }
        }

        for (RealVar var : variablesRealForComponent) {
            VariableType type = getVariableType(var.getName(), variables);
            switch (type) {
                case CPU:
                    break;
                case STORAGE:
                    //predicates.add(getStoragePredicate(var.)) todo
                    break;
                case LOCATION:
                    break;
            }
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    //converting actual deployed solution to list of Node Candidates with cardinality
    //todo
//    static Collection<Component> convertActualDeployment(EList<VariableValue> actualSolution, List<NodeCandidate> nodeCandidates) {
//
//        String componentId = "";//fixme
//
//        //for one component only
//        List<VariableValue> variablesForOneComponent = actualSolution
//                .stream()
//                .filter(vv -> componentId.equals(vv.getVariable().getComponentId()))
//                .collect(Collectors.toList());
//        List<NodeCandidate> nodeCandidatesForOneComponent = nodeCandidates;
//        int cardinality = 1;
//
//        for (VariableValue vv : variablesForOneComponent) {
//            Variable variable = vv.getVariable();
//            VariableType type = variable.getVariableType();
//
//            switch (type) {
//                case RAM:
//                    nodeCandidatesForOneComponent = nodeCandidatesForOneComponent
//                            .stream()
//                            .filter(nc -> nc.getHardware().getRam().equals(((LongValueUpperwareImpl) vv.getValue()).getValue()))
//                            .collect(Collectors.toList());
//                    break;
//                case CARDINALITY:
//                    cardinality = ((IntegerValueUpperwareImpl) vv.getValue()).getValue();
//                    break;
//
//            }
//        }
//        return Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidatesForOneComponent), cardinality));
//    }

//    static Solution findLastSolution(EList<Solution> solutions) {
//        return CollectionUtils.isNotEmpty(solutions) ? solutions.get(solutions.size()-1) : null;
//    }


    /* ---------------------for tests -to delete later -----------------------------*/

    static NodeCandidate findTheCheapestNodeCanidate(List<NodeCandidate> nodeCandidates) {
        Objects.requireNonNull(nodeCandidates);
        NodeCandidate theCheapest = nodeCandidates.get(0);
        for (NodeCandidate nc : nodeCandidates) {
            if (theCheapest.getPrice() > nc.getPrice()) {
                theCheapest = nc;
            }
        }
        return theCheapest;
    }


}
