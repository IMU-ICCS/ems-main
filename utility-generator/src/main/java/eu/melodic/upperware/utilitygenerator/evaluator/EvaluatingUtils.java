/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.model.VariableDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;
import solver.variables.IntVar;
import solver.variables.RealVar;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.cache.NodeCandidatePredicates.*;
import static java.lang.String.format;

@Slf4j
class EvaluatingUtils {


  /* mapping variables from solution and cp model */


    //todo move to CPModelTool
    static Collection<String> getVariableNames(String componentId, List<VariableDTO> variables) {

        return variables.stream()
                .filter(variable -> componentId.equals(variable.getComponentId()))
                .map(VariableDTO::getId)
                .collect(Collectors.toList());
    }

    //todo better exception? move to CPModelTool
    static String getVariableName(String componentId, VariableType type, List<VariableDTO> variables) {
        return variables.stream()
                .filter(v -> ((componentId.equals(v.getComponentId())) && type.equals(v.getType())))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Variable with type %s for component %s does not exist"))
                .getId();
    }


    static Map<String, Integer> getCardinalitiesForComponent(IntVar[] newConfiguration, List<VariableDTO> variables) {

        Map<String, Integer> cardinalitiesForComponent = new HashMap<>();

        Collection<VariableDTO> cardinalities = variables.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .collect(Collectors.toList());

        Arrays.stream(newConfiguration)
                .forEach(intVar -> cardinalities
                        .stream()
                        .filter(c -> intVar.getName().equals(c.getId()))
                        .findFirst()
                        .ifPresent(variable -> cardinalitiesForComponent.put(variable.getComponentId(), intVar.getValue())));
        return cardinalitiesForComponent;
    }

    //todo: better exception
    static int getProviderValue(String componentId, List<VariableDTO> variables, IntVar[] newConfigurationInt) {

        String provider = getVariableName(componentId, VariableType.PROVIDER, variables);
        return Arrays.stream(newConfigurationInt)
                .filter(intVar -> provider.equals(intVar.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", provider)))
                .getValue();

    }

    //todo: better exception move to CPModelTool
    private static VariableType getVariableType(String name, List<VariableDTO> variables) {
        return variables.stream()
                .filter(variable -> name.equals(variable.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", name)))
                .getType();
    }


    static Predicate<NodeCandidate>[] makePredicatesFromSolution(String componentId,
            IntVar[] newConfigurationInt, RealVar[] newConfigurationReal, List<VariableDTO> variables) {

        Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);

        List<IntVar> variablesIntForComponent = Arrays.stream(newConfigurationInt)
                .filter(intVar -> variableNamesForComponent.contains(intVar.getName()))
                .collect(Collectors.toList());

        List<RealVar> variablesRealForComponent = Arrays.stream(newConfigurationReal)
                .filter(realVar -> variableNamesForComponent.contains(realVar.getName()))
                .collect(Collectors.toList());

        List<Predicate<NodeCandidate>> predicates = new ArrayList<>();


        for (IntVar var : variablesIntForComponent) {
            VariableType type = getVariableType(var.getName(), variables);

            switch (type) {
                case RAM:
                    log.info("Creating getRamPredicate for value {}", (long) var.getValue());
                    predicates.add(getRamPredicate((long) var.getValue()));
                    break;
                case CARDINALITY:
                    break;
                case PROVIDER:
                    break;
                case CPU:
                    break;
                case CORES:
                    log.info("Creating getCoresPredicate for value {}", var.getValue());
                    predicates.add(getCoresPredicate(var.getValue()));
                    break;
                case OS:
                    log.info("Creating getOsPredicate for value {}", var.getValue());
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
