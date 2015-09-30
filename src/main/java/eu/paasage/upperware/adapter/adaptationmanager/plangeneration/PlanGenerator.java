/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.plangeneration;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ApplicationCreationAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ApplicationInstallationAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.CommunicationAdditionAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ComponentAdditionAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.SimpleDeploymentAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ComponentRemovalAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.CommunicationRemovalAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.VMAdditionAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.VMRemovalAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.HostingAdditionAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.HostingRemovalAction;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.RequiredHostInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;

import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.ModelComparator;

import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class PlanGenerator {

	private final static Logger LOGGER = Logger.getLogger(PlanGenerator.class
			.getName());


	boolean simpleInitialDeployment=true;
	ExecInterfacer execInterfacer;
	
	public PlanGenerator(boolean simpleInitialDeployment, ExecInterfacer execInterfacer) {
		this.simpleInitialDeployment=simpleInitialDeployment;
		this.execInterfacer = execInterfacer;
	}
	
	public PlanGenerator() {
	}
	
	public DirectedGraph<Action, DefaultEdge> generatePlanGraph(DeploymentModel current, DeploymentModel targetModel) {
		
		//this will hold the dependency among the actions as graph
		DirectedGraph<Action, DefaultEdge> graph = new DefaultDirectedGraph<Action, DefaultEdge>(DefaultEdge.class);
		
		ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> alist = new ArrayList<Action>();
		if (current != null) {
			System.out.println("current is not null");
			ModelComparator mc = new ModelComparator(current, targetModel);
			mc.compareModels();
			
			
			boolean isDifferent;
			if (mc.getRemovedCommmunications().isEmpty() && mc.getAddedCommunications().isEmpty() && mc.getRemovedComponents().isEmpty() && mc.getAddedComponents().isEmpty() && mc.getRemovedHostings().isEmpty() && mc.getAddedHostings().isEmpty()){
				System.out.println("Current and Target plans are the same as each other!");
				isDifferent = false;
			}
			else{
				isDifferent = true;
				System.out.println("Current and Target Plans are different!");
				System.out.println("            Number of Removed Components     " + mc.getRemovedComponents().size());
				System.out.println("            Number of Added Components     " + mc.getAddedComponents().size());
				System.out.println("            Number of Removed Communications     " + mc.getRemovedCommmunications().size());
				System.out.println("            Number of Added Communications     " + mc.getAddedCommunications().size());
				System.out.println("            Number of Removed Hostings     " + mc.getRemovedHostings().size());
				System.out.println("            Number of Added Hostings     " + mc.getAddedHostings().size());
			}
			
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> remComm = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> remHost = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> remComp = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> remVM = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> addComm = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> addHost = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> addComp = new ArrayList<Action>();
			ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> addVM = new ArrayList<Action>();
			
			simpleInitialDeployment = false;
			//alist.add(new ApplicationCreationAction(targetModel));
			//alist.add(new ApplicationInstallationAction());
			int j = 0;
			List<VMInstance> vmsList = new ArrayList<VMInstance>();
			vmsList = mc.getAddedVMs();
			while( j < vmsList.size()){
				alist.add(new VMAdditionAction(vmsList.get(j)));
				addVM.add(new VMAdditionAction(vmsList.get(j)));
				j++;
			}
			j = 0;
			vmsList = mc.getRemovedVMs();
			while( j < vmsList.size()){
				alist.add(new VMRemovalAction(vmsList.get(j)));
				remVM.add(new VMRemovalAction(vmsList.get(j)));
				j++;
			}
			j = 0;
			List<InternalComponentInstance> componentsList = new ArrayList<InternalComponentInstance>();
			componentsList = mc.getAddedComponents();
			while( j < componentsList.size()){
				alist.add(new ComponentAdditionAction((InternalComponentInstance) componentsList.get(j)));
				addComp.add(new ComponentAdditionAction((InternalComponentInstance) componentsList.get(j)));
				j++;
			}
			j = 0;
			componentsList = mc.getRemovedComponents();
			while( j < componentsList.size()){
				alist.add(new ComponentRemovalAction(componentsList.get(j)));
				remComp.add(new ComponentRemovalAction(componentsList.get(j)));
				j++;
			}
			j = 0;
			List<CommunicationInstance> communicationsList = new ArrayList<CommunicationInstance>();
			communicationsList = mc.getAddedCommunications();
			while( j < communicationsList.size()){
				alist.add(new CommunicationAdditionAction(communicationsList.get(j)));
				addComm.add(new CommunicationAdditionAction(communicationsList.get(j)));
				j++;
			}
			communicationsList = mc.getRemovedCommmunications();
			j = 0;
			while( j < communicationsList.size()){
				alist.add(new CommunicationRemovalAction(communicationsList.get(j)));
				remComm.add(new CommunicationRemovalAction(communicationsList.get(j)));
				j++;
			}
			j = 0;
			List<HostingInstance> hostingsList = new ArrayList<HostingInstance>();
			hostingsList = mc.getAddedHostings();
			while( j < hostingsList.size()){
				alist.add(new HostingAdditionAction(hostingsList.get(j)));
				addHost.add(new HostingAdditionAction(hostingsList.get(j)));
				j++;
			}
			hostingsList = mc.getRemovedHostings();
			j = 0;
			while( j < hostingsList.size()){
				alist.add(new HostingRemovalAction(hostingsList.get(j)));
				remHost.add(new HostingRemovalAction(hostingsList.get(j)));
				j++;
			}
			
			// Add code to handle dynamic configuration
			
			
			//Adding the dependencies as graph
			//Adding non-dependent Actions to graph			
			/*
			** OUR EXAMPLE HAS THESE. SO CREATING ANOTHER SAMPLE DEPENDENCY FOR TEST 
			Number of Removed Components     0<
            Number of Added Components     0<
            Number of Removed Communications     1<
            Number of Added Communications     2
            Number of Removed Hostings     1<
            Number of Added Hostings     3
			 */
			for(Action commRA : remComm)//non dependent action, adding as Vertex
				graph.addVertex(commRA);
			
			for(Action hostRA : remHost)//non dependent action, adding as Vertex
				graph.addVertex(hostRA);
			
			for(Action compRA : remComp){//dependent Component Removal action, adding as Vertex
				graph.addVertex(compRA);
				//adding dependency on Communication and Hosting removal as edges
				for(Action commRA : remComm)
					graph.addEdge(compRA, commRA);
				for(Action hostRA : remHost)
					graph.addEdge(compRA, hostRA);
			}
			
			for(Action vmRA : remVM){//dependent VM Removal action, adding as Vertex
				graph.addVertex(vmRA);
				//adding dependency on Component removal as edges
				for(Action compRA : remComp)
					graph.addEdge(vmRA, compRA);
			}
			
			for(Action compAA : addComp){//dependent Component Addition action, adding as Vertex
				graph.addVertex(compAA);
				//adding dependency on Component removal as edges
				for(Action compRA : remComp)
					graph.addEdge(compAA, compRA);
			}
			
			for(Action hostAA : addHost){//dependent Hosting Addition action, adding as Vertex
				graph.addVertex(hostAA);
				//adding dependency on Component addition as edges
				for(Action compAA : addComp)
					graph.addEdge(hostAA, compAA);
				for(Action hostRA : remHost)
					graph.addEdge(hostAA, hostRA);
			}
			
			for(Action commAA : addComm){//dependent Communication Addition action, adding as Vertex
				graph.addVertex(commAA);
				//adding dependency on Component addition as edges
				for(Action compAA : addComp)
					graph.addEdge(commAA, compAA);
				for(Action commRA : remComm)
					graph.addEdge(commAA, commRA);
				for(Action hostAA : addHost)
					graph.addEdge(commAA, hostAA);
			}
			
			for(Action vmAA : addVM){//dependent VM Addition action, adding as Vertex
				graph.addVertex(vmAA);
				//adding dependency on Component and VM addition as edges
				for(Action compAA : addComp)
					graph.addEdge(vmAA, compAA);
				for(Action hostAA : addHost)
					graph.addEdge(vmAA, hostAA);
			}
			
/*			//ANOTHER SAMPLE DEPENDENCY 
			for(Action commRA : remComm)//non dependent action, adding as Vertex
				graph.addVertex(commRA);
			
			for(Action hostRA : remHost)//non dependent action, adding as Vertex
				graph.addVertex(hostRA);
			
			for(Action compRA : remComp){//dependent Component Removal action, adding as Vertex
				graph.addVertex(compRA);
				//adding dependency on Communication and Hosting removal as edges
				for(Action commRA : remComm)
					graph.addEdge(compRA, commRA);
				for(Action hostRA : remHost)
					graph.addEdge(compRA, hostRA);
			}
			
			for(Action vmRA : remVM){//dependent VM Removal action, adding as Vertex
				graph.addVertex(vmRA);
				//adding dependency on Component removal as edges
				for(Action compRA : remComp)
					graph.addEdge(vmRA, compRA);
			}
			
			for(Action compAA : addComp){//dependent Component Addition action, adding as Vertex
				graph.addVertex(compAA);
				//adding dependency on Component removal as edges
				for(Action compRA : remComp)
					graph.addEdge(compAA, compRA);
			}
			
			for(Action hostAA : addHost){//dependent Hosting Addition action, adding as Vertex
				graph.addVertex(hostAA);
				//adding dependency on Component addition as edges
				for(Action compAA : addComp)
					graph.addEdge(hostAA, compAA);
			}
			
			for(Action commAA : addComm){//dependent Communication Addition action, adding as Vertex
				graph.addVertex(commAA);
				//adding dependency on Component addition as edges
				for(Action compAA : addComp)
					graph.addEdge(commAA, compAA);
			}
			
			for(Action vmAA : addVM){//dependent VM Addition action, adding as Vertex
				graph.addVertex(vmAA);
				//adding dependency on Component and VM addition as edges
				for(Action compAA : addComp)
					graph.addEdge(vmAA, compAA);
				for(Action hostAA : addHost)
					graph.addEdge(vmAA, hostAA);
			}
*/

		}

		if (simpleInitialDeployment) {
			LOGGER.log(Level.INFO, "Current plan is null! Starting: Simple initial deployment");
			Action action = new SimpleDeploymentAction(targetModel, execInterfacer);
			alist.add(action);
			graph.addVertex(action);
			//Plan result=new Plan(alist);
			LOGGER.log(Level.INFO, "Generated plan with "+alist.size()+" action(s)");
			//return result;
			return graph;
		}

		LOGGER.log(Level.INFO, "Generated graph with "+alist.size()+" action(s)");
		return graph;		
	}
	
	public Plan generate(DeploymentModel current, DeploymentModel targetModel) {


		ArrayList<eu.paasage.upperware.adapter.adaptationmanager.actions.Action> alist = new ArrayList<Action>();
		if (current != null) {
			System.out.println("current is not null");
			ModelComparator mc = new ModelComparator(current, targetModel);
			mc.compareModels();
			
			
			boolean isDifferent;
			if (mc.getRemovedCommmunications().isEmpty() && mc.getAddedCommunications().isEmpty() && mc.getRemovedComponents().isEmpty() && mc.getAddedComponents().isEmpty() && mc.getRemovedHostings().isEmpty() && mc.getAddedHostings().isEmpty()){
				System.out.println("Current and Target plans are the same as each other!");
				isDifferent = false;
			}
			else{
				isDifferent = true;
				System.out.println("Current and Target Plans are different!");
				System.out.println("            Number of Removed Components     " + mc.getRemovedComponents().size());
				System.out.println("            Number of Added Components     " + mc.getAddedComponents().size());
				System.out.println("            Number of Removed Communications     " + mc.getRemovedCommmunications().size());
				System.out.println("            Number of Added Communications     " + mc.getAddedCommunications().size());
				System.out.println("            Number of Removed Hostings     " + mc.getRemovedHostings().size());
				System.out.println("            Number of Added Hostings     " + mc.getAddedHostings().size());
			}
			
			
			simpleInitialDeployment = false;
			//alist.add(new ApplicationCreationAction(targetModel));
			//alist.add(new ApplicationInstallationAction());
			int j = 0;
			List<VMInstance> vmsList = new ArrayList<VMInstance>();
			vmsList = mc.getAddedVMs();
			while( j < vmsList.size()){
				alist.add(new VMAdditionAction(vmsList.get(j)));
				j++;
			}
			j = 0;
			vmsList = mc.getRemovedVMs();
			while( j < vmsList.size()){
				alist.add(new VMRemovalAction(vmsList.get(j)));
				j++;
			}
			j = 0;
			List<InternalComponentInstance> componentsList = new ArrayList<InternalComponentInstance>();
			componentsList = mc.getAddedComponents();
			while( j < componentsList.size()){
				alist.add(new ComponentAdditionAction((InternalComponentInstance) componentsList.get(j)));
				j++;
			}
			j = 0;
			componentsList = mc.getRemovedComponents();
			while( j < componentsList.size()){
				alist.add(new ComponentRemovalAction(componentsList.get(j)));
				j++;
			}
			j = 0;
			List<CommunicationInstance> communicationsList = new ArrayList<CommunicationInstance>();
			communicationsList = mc.getAddedCommunications();
			while( j < communicationsList.size()){
				alist.add(new CommunicationAdditionAction(communicationsList.get(j)));
				j++;
			}
			communicationsList = mc.getRemovedCommmunications();
			j = 0;
			while( j < communicationsList.size()){
				alist.add(new CommunicationRemovalAction(communicationsList.get(j)));
				j++;
			}
			j = 0;
			List<HostingInstance> hostingsList = new ArrayList<HostingInstance>();
			hostingsList = mc.getAddedHostings();
			while( j < hostingsList.size()){
				alist.add(new HostingAdditionAction(hostingsList.get(j)));
				j++;
			}
			hostingsList = mc.getRemovedHostings();
			j = 0;
			while( j < hostingsList.size()){
				alist.add(new HostingRemovalAction(hostingsList.get(j)));
				j++;
			}
			
			// Add code to handle dynamic configuration
			
		}
		if (simpleInitialDeployment) {
			LOGGER.log(Level.INFO, "Current plan is null! Starting: Simple initial deployment");
			Action action = new SimpleDeploymentAction(targetModel, execInterfacer);
			alist.add(action);
			Plan result=new Plan(alist);
			LOGGER.log(Level.INFO, "Generated plan with "+alist.size()+" action(s)");
			return result;
		}
		/*	
		alist.add(new ApplicationCreationAction(targetModel));
                   for (InternalComponentInstance c : ModelUtil
                                   .getInternalComponentInstances(targetModel)) {
                           alist.add(new ComponentAdditionAction(c));
                   }
                   for (CommunicationInstance cmm : targetModel.getCommunicationInstances()) {
              		alist.add(new CommunicationAdditionAction(cmm));
                   }
                   alist.add(new ApplicationInstallationAction());
		
		return new Plan(alist);
		*/
		Plan result=new Plan(alist);
		LOGGER.log(Level.INFO, "Generated plan with "+alist.size()+" actions");
		return result;
	}
}
