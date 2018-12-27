package eu.melodic.upperware.utilitygenerator.cdo.cp_model;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@Getter
@AllArgsConstructor
public class CPModelHandler {


    private Collection<VariableDTO> variables;
    private Collection<MetricDTO> metrics;
    private Collection<VariableValueDTO> deployedSolution;
    private NodeCandidates nodeCandidates;

    public CPModelHandler(Collection<VariableDTO> variables, Collection<MetricDTO> metrics, NodeCandidates nodeCandidates){
        this.variables = variables;
        this.metrics = metrics;
        this.nodeCandidates = nodeCandidates;
    }
}
