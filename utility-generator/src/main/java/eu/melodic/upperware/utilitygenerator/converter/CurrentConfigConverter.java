package eu.melodic.upperware.utilitygenerator.converter;

import camel.metric.impl.MetricVariableImpl;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@Slf4j
public class CurrentConfigConverter {


    public static Collection<Element> convertCurrentConfig(Collection<VariableDTO> variablesFromConstraintProblem,
            Collection<MetricVariableImpl> variablesFromCamel, Collection<Element> deployedSolution){

        Collection<Element> currentConfigVariables = new ArrayList<>();


        variablesFromCamel.stream()
                .filter(MetricVariableImpl::isCurrentConfiguration)
                .forEach(actVar -> currentConfigVariables
                        .add(new IntElement(actVar.getName(), getVariableValue(actVar, deployedSolution, variablesFromConstraintProblem))));
        return currentConfigVariables;
    }


//    private static VariableType getVariableType(MetricVariable m){
//        return VariableType.get(m.getMetricTemplate().getAttribute().getAnnotations().get(0).getId());
//    }

    private static int getVariableValue(camel.metric.impl.MetricVariableImpl metric, Collection<Element> deployedSolution, Collection<VariableDTO> variables){

        log.info("getVariableValue: for metric = " + metric.getName());
        log.info("component  " + metric.getComponent().getName());
        log.info("annotations: " + metric.getMetricTemplate().getAttribute().getAnnotations().get(0).getId());

        VariableDTO matchingVariable = variables.stream()
                .filter(variable -> variable.getType().equals(getVariableType(metric)) && variable.getComponentId().equals(metric.getComponent().getName()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Variable with name " + metric.getName() + " does not match with variable from Constraint Problem"));

        return (int) deployedSolution.stream()
                .filter(solutionElement -> solutionElement.getName().equals(matchingVariable.getId()))
                .findAny()
                .orElseThrow(()-> new IllegalStateException("Variable with name " + matchingVariable.getId() + "does not match with deployed solution")).getValue();
    }

    //fixme!!
    public static VariableType getVariableType(MetricVariableImpl metric){

        String annotation = metric.getMetricTemplate().getAttribute().getAnnotations().get(0).getId();
        log.info("Found annotation: " + annotation);
        VariableType resultType = Stream.of(VariableType.values())
                .filter(type -> annotation.contains(type.getName()))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("Wrong annotation - Melodic does not support that"));
        log.info("Found type: " + resultType);
        return resultType;


    }
}

