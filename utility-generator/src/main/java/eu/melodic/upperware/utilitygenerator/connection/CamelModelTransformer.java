package eu.melodic.upperware.utilitygenerator.connection;

import camel.core.CamelModel;
import camel.metric.MetricVariable;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.impl.OptimisationRequirementImpl;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class CamelModelTransformer {

    private CamelModel model;
    private MetricTypeModelImpl metricModel;
    private Collection<MetricVariableImpl> metricVariables;

    public CamelModelTransformer(String path){
        this.model = (CamelModel) CDOClient.loadModel(path);

        this.metricModel = (MetricTypeModelImpl) model.getMetricModels().get(0);
        this.metricVariables = new ArrayList<>();

        metricModel.getMetrics().stream().filter(m -> m instanceof MetricVariable).forEach(m-> metricVariables.add((MetricVariableImpl) m));
        log.info("size of metric variable = " + metricVariables.size());
    }

    public String getUtilityFormula(){

        RequirementModel requirementModel = model.getRequirementModels().get(0);
        OptimisationRequirement optimisationRequirement = (OptimisationRequirement) requirementModel
                .getRequirements()
                .stream()
                .filter(r -> r instanceof OptimisationRequirementImpl)
                .findAny()
                .orElseThrow(()-> new IllegalStateException("optimization requirement is obligatory"));

        return optimisationRequirement.getMetricVariable().getFormula();
    }

     //on node candidates
    public Collection<NodeCandidateAttribute> getAttributesOfNodeCandidates(){
        Collection<NodeCandidateAttribute> at = new ArrayList<>();
        metricVariables.stream().filter(MetricVariableImpl::isOnNodeCandidates).forEach(a-> at.add(new NodeCandidateAttribute(a.getName())));
        return at;
    }


    //fixme - not every argument is needed in function
    public Collection<Argument> getArgumentsFromCamelModel(){

        return metricModel
                .getMetrics()
                .stream()
                .map(m -> new Argument(m.getName()))
                .collect(Collectors.toList());
    }





}
