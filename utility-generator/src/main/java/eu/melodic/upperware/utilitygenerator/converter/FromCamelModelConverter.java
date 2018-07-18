/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import camel.core.CamelModel;
import camel.metric.CompositeMetric;
import camel.metric.Metric;
import camel.metric.MetricVariable;
import camel.metric.RawMetric;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.impl.OptimisationRequirementImpl;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class FromCamelModelConverter {

    private CamelModel model;
    private MetricTypeModelImpl metricModel;
    private Collection<MetricVariableImpl> metricVariables;

    public FromCamelModelConverter(String path){
        this.model = (CamelModel) CDOClient.loadModel(path);
        this.metricModel = (MetricTypeModelImpl) model.getMetricModels().get(0);

        this.metricVariables = new ArrayList<>();
        metricModel.getMetrics().stream()
                .filter(m -> m instanceof MetricVariable)
                .forEach(m-> metricVariables.add((MetricVariableImpl) m));
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
        metricVariables.stream().filter(MetricVariableImpl::isOnNodeCandidates).forEach(a-> at.add(new NodeCandidateAttribute(a.getName(), a.getComponent().getName())));
        return at;
    }

    public Collection<Metric> getMetrics(){
        return metricModel.getMetrics().stream().filter(m -> m instanceof RawMetric || m instanceof CompositeMetric).collect(Collectors.toList());
    }

    public Collection<MetricVariableImpl> getCurrentConfigMetricVariables(){
        return metricVariables.stream().filter(MetricVariableImpl::isCurrentConfiguration).collect(Collectors.toList());
    }
}
