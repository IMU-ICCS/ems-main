/*
 * Copyright (c) 2015 INRIA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.mapping;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.runtime.tree.DOTTreeGenerator;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import com.eclipsesource.json.JsonObject;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.jgraph.components.labels.MultiLineVertexRenderer;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ApplicationAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ApplicationInstanceAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.CommunicationAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.CommunicationInstanceAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.HostingAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.HostingInstanceAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.InternalComponentAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.InternalComponentInstanceAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.VMAction;
import eu.paasage.upperware.adapter.adaptationmanager.actions.VMInstanceAction;
import eu.paasage.upperware.plangenerator.PlanGenerator;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.exception.PlanGenerationException;
import eu.paasage.upperware.plangenerator.model.Plan;
import eu.paasage.upperware.plangenerator.model.task.ApplicationInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.ApplicationTask;
import eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask;
import eu.paasage.upperware.plangenerator.model.task.ComponentInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.ComponentTypeTask;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.model.task.VMInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.VMTypeTask;
import eu.paasage.upperware.plangenerator.type.TaskType;

import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgraph.JGraph;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.jgrapht.ext.JGraphModelAdapter;

public final class GraphUtilities {
	
	private static final Logger LOGGER = Logger
			.getLogger(GraphUtilities.class.getName());

	private GraphUtilities(){
		// TODO Auto-generated constructor stub
		throw new RuntimeException("Non instantiable class");
	}
	
	public static DirectedGraph<ConfigurationTask, DefaultEdge> generatePlanGraph(DeploymentModel currentModel, DeploymentModel targetModel){
		
		PlanGenerator generator = null;
		
		if(currentModel != null && targetModel != null)//condition for reconfiguration
			generator = new PlanGenerator();
		else if(currentModel == null && targetModel != null)//condition for simple deployment
			generator = new PlanGenerator(true);
		
		Plan newPlan = null;
		DirectedGraph<ConfigurationTask, DefaultEdge> graph = new DefaultDirectedGraph<ConfigurationTask, DefaultEdge>(DefaultEdge.class);
		
		try {
			newPlan = generator.generate(currentModel, targetModel);
		} catch (PlanGenerationException e) {
			// TODO Auto-generated catch block
			System.out.println("Plan generation exception");
			e.printStackTrace();
		} catch (ModelComparatorException e) {
			// TODO: handle exception
			System.out.println("Model comparator exception");
			e.printStackTrace();
		}
		
		if(newPlan == null)
			return graph;
		
		List<ConfigurationTask> tasks = newPlan.getTasks();
		
		for(ConfigurationTask task : tasks){
			
			//Searching for the node task in the graph. Adding if not present.
			ConfigurationTask nodeTask = searchGraphTask(graph, task);
			if(nodeTask == null){
				graph.addVertex(task);
				nodeTask = task;
			}
			
			//Now searching for the dependencies and adding and creating edges
			for(ConfigurationTask dTask : task.getDependencies()){
				
				ConfigurationTask depTask = searchGraphTask(graph, dTask);
				if(depTask == null){
					graph.addVertex(dTask);
					depTask = dTask;
				}
				
				graph.addEdge(nodeTask, depTask);
			}
		}

		return graph;
	}
	
/*	public static DirectedGraph<V, E> copyGraph(DirectedGraph<V, E> src){
		DirectedGraph<V, E> dest = new DefaultDirectedGraph<V, E>();
		
		return dest;
	}*/
	
	/**
	 * fetches the {@linkplain eu.paasage.upperware.plangenerator.model.task.ConfigurationTask} object from the graph using name 
	 * @param graph to be searched in
	 * @param task to search
	 * @return the object ConfigurationTask if found else null
	 */
	public static ConfigurationTask searchGraphTask(DirectedGraph<ConfigurationTask, DefaultEdge> graph, ConfigurationTask task){
		
		Set<ConfigurationTask> tasks = graph.vertexSet();
		for(ConfigurationTask cTask : tasks){
			if(cTask.getName().equals(task.getName()))
				return cTask;
		}
		
		return null;
	}
	
	/**
	 * fetches the {@linkplain eu.paasage.upperware.adapter.adaptationmanager.actions.Action} object from the graph using the stored task
	 * @param graph containing the Action dependencies
	 * @param task stored within the Action node
	 * @return the node Action if found else null
	 */
	public static Action searchGraphAction(DirectedGraph<Action, DefaultEdge> graph, ConfigurationTask task){
		
		Set<Action> actions = graph.vertexSet();
		//System.out.println("Breakpoint1 #" + actions.size());
		for(Action action : actions){
			//System.out.println("Breakpoint2 #" + action.toString() + " " + action.task.toString() + " " + task.toString());
			//System.out.println("Breakpoint2 #" + action.toString());
			//if(action.task == task){
			if(action.getTask()==null)
				LOGGER.log(Level.WARNING, "PROBLEM!!");
			if(action.getTask().equals(task)){
				//LOGGER.log(Level.INFO, "Found Task " + action.task.toString());
				//LOGGER.log(Level.INFO, "Found Task " + action.getTask().toString() + " " + action.toString());
				return action;
			}
		}
		
		return null;//CHECK WITH THIS... SENDING NULL SOMETIMES
	}
	
	public static DirectedGraph<Action, DefaultEdge> taskGraphToActions(DirectedGraph<ConfigurationTask, DefaultEdge> taskGraph, ExecInterfacer execInterfacer){
		
		DirectedGraph<Action, DefaultEdge> actionGraph = new DefaultDirectedGraph<Action, DefaultEdge>(DefaultEdge.class);
		
		GraphIterator<ConfigurationTask, DefaultEdge> iteratorAdd = new DepthFirstIterator<ConfigurationTask, DefaultEdge>(taskGraph);
		
		while (iteratorAdd.hasNext()){
			ConfigurationTask task = (ConfigurationTask) iteratorAdd.next();
			
			JsonObject obj = task.getJsonModel();
			
			Action node = null;
			
			if((task instanceof ApplicationTask) /*|| obj.get("objType").asString().equalsIgnoreCase("application")*/){
				LOGGER.log(Level.INFO, "Type detected ApplicationTask");
				//System.out.println(obj.toString());
				
				node = new ApplicationAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if((task instanceof ApplicationInstanceTask) /*|| obj.get("objType").asString().equalsIgnoreCase("applicationInstance")*/){
				LOGGER.log(Level.INFO, "Type detected ApplicationInstanceTask");
				//System.out.println(obj.toString());
				
				node = new ApplicationInstanceAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if((task instanceof ComponentTypeTask) /*|| obj.get("objType").asString().equalsIgnoreCase("internalComponent")*/){
				LOGGER.log(Level.INFO, "Type detected ComponentTypeTask");
				//System.out.println(obj.toString());
				
				node = new InternalComponentAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if((task instanceof ComponentInstanceTask) /*|| obj.get("objType").asString().equalsIgnoreCase("internalComponentInstance")*/){
				LOGGER.log(Level.INFO, "Type detected ComponentInstanceTask");
				//System.out.println(obj.toString());
				
				node = new InternalComponentInstanceAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if((task instanceof VMTypeTask) /*|| obj.get("objType").asString().equalsIgnoreCase("VM")*/){
				LOGGER.log(Level.INFO, "Type detected VMTypeTask");
				//System.out.println(obj.toString());
				
				node = new VMAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if((task instanceof VMInstanceTask) /*|| obj.get("objType").asString().equalsIgnoreCase("vmInstance")*/){
				LOGGER.log(Level.INFO, "Type detected VMInstanceTask");
				//System.out.println(obj.toString());
				
				node = new VMInstanceAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if((task instanceof CommunicationTypeTask) /*|| obj.get("objType").asString().equalsIgnoreCase("communication")*/){
				LOGGER.log(Level.INFO, "Type detected CommunicationTypetask");
				//System.out.println(obj.toString());
				
				node = new CommunicationAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}/*else if(obj.get("objType").asString().equalsIgnoreCase("communicationInstance")){//GONE from ExecWare; So commented
				LOGGER.log(Level.INFO, "Type detected CommunicationInstanceTask");
				//System.out.println(obj.toString());
				
				node = new CommunicationInstanceAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if(obj.get("objType").asString().equalsIgnoreCase("hosting")){//Doesn't exist in Execware
				LOGGER.log(Level.INFO, "Type detected HostingTypeTask");
				//System.out.println(obj.toString());
				
				node = new HostingAction(task, execInterfacer);
				actionGraph.addVertex(node);
				
			}else if(obj.get("objType").asString().equalsIgnoreCase("hostingInstance")){//Doesn't exist in Execware
				LOGGER.log(Level.INFO, "Type detected HostingInstanceTask");
				//System.out.println(obj.toString());
				
				node = new HostingInstanceAction(task, execInterfacer);
				actionGraph.addVertex(node);
			}*/
			
/*			if(task.getClass() == ApplicationTask.class){
				LOGGER.log(Level.INFO, "Type detected ApplicationTask.class");
				System.out.println(obj.toString());
				
			} else if(task.getClass() == ApplicationInstanceTask.class){
				LOGGER.log(Level.INFO, "Type detected ApplicationInstanceTask");
				System.out.println(obj.toString());
				
			}*/
			
			//Now searching for the dependencies and adding and creating edges
/*			for(ConfigurationTask dTask : task.getDependencies()){
				LOGGER.log(Level.INFO, "Dependency " + dTask.toString());
				Action depAction = searchGraphAction(actionGraph, dTask);
				if(depAction == null)
					LOGGER.log(Level.WARNING, "PROBLEM!! depAction is null");
				LOGGER.log(Level.INFO, "Dependency Action " + depAction.toString());
				if(depAction != null && node != null){//ERROR IN LOGIC HERE.... CHECK...
					actionGraph.addVertex(depAction);//ERROR IN LOGIC HERE.... CHECK...
					actionGraph.addEdge(node, depAction);
				}
				//graph.addEdge(nodeTask, depTask);
			}*/
		}
		
		GraphIterator<ConfigurationTask, DefaultEdge> iteratorLink = new DepthFirstIterator<ConfigurationTask, DefaultEdge>(taskGraph);
		
		while (iteratorLink.hasNext()){
			ConfigurationTask task = (ConfigurationTask) iteratorLink.next();
			
			JsonObject obj = task.getJsonModel();
			
			Action node = searchGraphAction(actionGraph, task);
			
			//Now searching for the dependencies and adding and creating edges
			for(ConfigurationTask dTask : task.getDependencies()){
				LOGGER.log(Level.INFO, "Dependency " + dTask.toString());
				Action depAction = searchGraphAction(actionGraph, dTask);
				if(depAction == null)
					LOGGER.log(Level.WARNING, "PROBLEM!! depAction is null");
				//LOGGER.log(Level.INFO, "Dependency Action " + depAction.toString());
				if(depAction != null && node != null){//ERROR IN LOGIC HERE.... CHECK...
					//actionGraph.addVertex(depAction);//ERROR IN LOGIC HERE.... CHECK...
/*					if(task.getTaskType() == TaskType.DELETE)
						actionGraph.addEdge(depAction, node);
					else*/
						actionGraph.addEdge(node, depAction);
					LOGGER.log(Level.INFO, "Added dependency : " + node.toString() + " " + depAction.toString());
				}
			}
		}
		
		return actionGraph;
	}
	
	public static BufferedImage graphToImage(DirectedGraph<Action, DefaultEdge> graph){
		
		JGraphModelAdapter<Action, DefaultEdge> graphModel = new JGraphModelAdapter<Action, DefaultEdge>(graph);
		JGraph myjgraph = new JGraph (graphModel);
		System.out.println("Size is " + graphModel.toString());
		BufferedImage img = myjgraph.getImage(Color.WHITE, 0);
		if(img == null)
			LOGGER.log(Level.INFO, "Image generated is null");
		
		return img;
	}
	
	public static void viewImage(DirectedGraph<Action, DefaultEdge> graph){
		final JFrame frame = new JFrame();
		frame.setSize(800, 600);
		ListenableDirectedGraph<Action, DefaultEdge> lisGraph = new ListenableDirectedGraph<Action, DefaultEdge>(graph);
		
		JGraph jgraph = new JGraph(new JGraphModelAdapter<Action, DefaultEdge>(lisGraph));
		//jgraph.putClientProperty(MultiLineVertexRenderer.CLIENTPROPERTY_SHOWFOLDINGICONS, Boolean.FALSE);
		jgraph.setBackground(Color.decode( "#FAFBFF" ));
		frame.getContentPane().add(jgraph);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Are you sure to close this window?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		            return;
		        }
		    }
		});
		
		while (true) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public static void exportImage(DirectedGraph<Action, DefaultEdge> graph){
		DOTExporter exporter = new DOTExporter();
		String targetDirectory = "/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/";
		new File(targetDirectory);
		try {
			exporter.export(new FileWriter(targetDirectory + "initial-graph.dot"), graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
