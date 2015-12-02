/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.core;

import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ActionError;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.exception.PlanGenerationException;
//import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.Plan;
import eu.paasage.upperware.plangenerator.model.Plan;
import eu.paasage.upperware.adapter.adaptationmanager.input.CDOClientUtil;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;
//import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.PlanGenerator;
import eu.paasage.upperware.plangenerator.PlanGenerator;//Shirley's PlanGenerator
import eu.paasage.upperware.adapter.adaptationmanager.validation.IValidator;
import eu.paasage.upperware.adapter.adaptationmanager.input.MyCDOClient;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.GraphUtilities;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.cdo.view.CDOView;

import java.util.List;

import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.emf.cdo.session.*;

import java.util.ArrayList;

import org.eclipse.emf.cdo.common.revision.*;
import org.eclipse.net4j.util.event.INotifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.EdgeReversedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import eu.paasage.camel.CamelModel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Coordinator {
	public boolean flag = false;
	ExecInterfacer execInterfacer;
	IValidator validator;
	PlanGenerator planGenerator;
	DeploymentModel currentModel = null;
	ReasonerInterfacer reasonerInterfacer;
	Map<String, Object> state = new HashMap<String, Object>();

	private static Map<String, Object> handles = new HashMap<String, Object>();
	private static Map<String, Object> execWareObjects = new HashMap<String, Object>();
	private static Multimap<String, Object> holdDependents = HashMultimap
			.create();

	private final static Logger LOGGER = Logger.getLogger(Coordinator.class
			.getName());

	private static DirectedGraph<ConfigurationTask, DefaultEdge> taskPlan;
	private static DirectedGraph<Action, DefaultEdge> graph;// for Action
															// dependencies
	private static DirectedNeighborIndex<Action, DefaultEdge> neigh = null;

	private static ThreadExecutor executor;// for parallel execution
	private static DirectedGraph<Action, DefaultEdge> invGraph;

	public Map<String, Object> getState() {
		return state;
	}

	public Coordinator(ReasonerInterfacer reasonerInterfacer,
			ExecInterfacer execInterfacer, IValidator validator,
			PlanGenerator planGenerator) {
		super();
		this.reasonerInterfacer = reasonerInterfacer;
		this.execInterfacer = execInterfacer;
		this.validator = validator;
		this.planGenerator = planGenerator;

		initializeHandlers(execInterfacer);
	}

	public Coordinator(ReasonerInterfacer reasonerInterfacer,
			ExecInterfacer execInterfacer, IValidator validator) {
		super();
		this.reasonerInterfacer = reasonerInterfacer;
		this.execInterfacer = execInterfacer;
		this.validator = validator;

		initializeHandlers(execInterfacer);
	}

	public static synchronized void initializeHandlers(
			ExecInterfacer execInterfacer) {
		handles.put("execInterfacer", execInterfacer);
		handles.put("execWareObjects", execWareObjects);
		handles.put("dependents", holdDependents);
		// initializeHoldDependencies();
	}

	public static synchronized Object getHandle(String handleKey) {
		return handles.get(handleKey);
	}

	public static synchronized void initializeNeighbourDependencies() {
		synchronized (graph) {
			if (graph.vertexSet().size() == 0)
				return;

			for (Action act : graph.vertexSet()) {
				System.out.print("\nTasks dependent on " + act.toString()
						+ " #" + neigh.successorsOf(act).size());
				for (Action dep : neigh.successorsOf(act)) {
					System.out.print(" ==>" + dep.toString());
				}
				if (!neigh.successorsOf(act).isEmpty())
					holdDependents.put(act.toString(), null);
			}
		}
	}

	public static synchronized void setNeighbourDependencies(Action act) {

		for (Action pred : neigh.predecessorsOf(act)) {
			if (holdDependents.containsKey(pred.toString())) {
				holdDependents.remove(pred.toString(), null);
				String actionClassData = "Object_created@" + act.toString()
						+ " ";
				holdDependents.put(pred.toString(), actionClassData);
				LOGGER.log(Level.WARNING, "PRED " + pred.toString() + " "
						+ actionClassData);
			}
		}
	}

	public static synchronized Collection<Object> getNeighbourDependencies(
			Action act) {
		Collection<Object> col = holdDependents.get(act.toString());
		if (col == null)
			return null;
		else
			return col;
	}

	public static synchronized Collection<Action> getDependentActions(Action act) {
		return neigh.successorsOf(act);
	}

	public static synchronized Collection<Action> getDependentOnActions(
			Action act) {
		return neigh.predecessorsOf(act);
	}

	public static synchronized boolean putObject(String key, Object obj) {
		boolean status = true;
		try {
			execWareObjects.put(key, obj);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = false;
			return status;
		}
		return status;
	}

	public static synchronized boolean removeObject(String key) {
		boolean status = true;
		try {
			execWareObjects.remove(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = false;
			return status;
		}
		return status;
	}

	public static synchronized Object getObject(String key) {
		return execWareObjects.get(key);
	}

	public static synchronized boolean containsObject(String key) {
		return execWareObjects.containsKey(key);
	}

	public static synchronized void printObjects() {
		System.out.println("The objects present are: ");
		for (String key : execWareObjects.keySet())
			System.out.print(" " + key);
	}

	public void runStep() {
		Map<String, Object> outputMap = new HashMap<String, Object>();
		DeploymentModel targetModel = reasonerInterfacer
				.getDeploymentModel(false);

		Plan plan = null;
		try {
			plan = planGenerator.generate(currentModel, targetModel);
		} catch (PlanGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelComparatorException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// validate plan
		if (!validator.validate(plan))
			throw new AssertionError();
		// execute plan
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("execInterfacer", execInterfacer);
		/*
		 * //Commenting for integrating Shirley's Plan Generator for (Action a :
		 * plan.getActions()){ try { a.execute(inputMap, outputMap); } catch
		 * (ActionError e) { e.printStackTrace(); } inputMap.putAll(outputMap);
		 * } state.putAll(outputMap);
		 */
		List<ConfigurationTask> tasks = plan.getTasks();
	}

	public boolean startThreaded() {

		LOGGER.log(Level.INFO, "Start of threaded execution");

		Map<String, Object> outputMap = new HashMap<String, Object>();

		// DeploymentModel targetModel =
		// reasonerInterfacer.getDeploymentModel(false);//Commented to replace
		// live transaction below

//		int dmIndex = 1;// Simple deployment model location

		reasonerInterfacer.openTransaction();

		int ndm = reasonerInterfacer.getDeploymentModelsSize();

		DeploymentModel targetModel = null;
		if (ndm < 2) {
			System.out.println("Not enough deployment models in CAMEL model");
			System.exit(0);
		} else if (ndm == 2) {
			currentModel = null;
			targetModel = reasonerInterfacer.getLiveDeploymentModel(1);
		} else {
			currentModel = reasonerInterfacer.getLiveDeploymentModel(ndm - 2);
			targetModel = reasonerInterfacer.getLiveDeploymentModel(ndm - 1);
			LOGGER.log(Level.INFO, "Reconfiguration between two deployment models");
		}

		
//	DeploymentModel targetModel = reasonerInterfacer
//				.getLiveDeploymentModel(dmIndex);// Comment to stop getting live
													// model

		// CDOClientUtil mycdo = new CDOClientUtil(null);//comment not to load
		// local files
		// DeploymentModel targetModel =
		// CDOClientUtil.tryLoadTwoFiles("/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/PGexamples/test.xmi",
		// "/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/PGexamples/upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi");
		// DeploymentModel targetModel =
		// mycdo.tryLoadTwoFiles("/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/test.xmi",
		// "/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi");

		// Integration for Shirley's plangenerator
		taskPlan = GraphUtilities.generatePlanGraph(currentModel, targetModel);

		reasonerInterfacer.closeTransaction();// closing the live transaction
												// after plan generated

		DirectedGraph<Action, DefaultEdge> g = GraphUtilities
				.taskGraphToActions(taskPlan, execInterfacer);

		/*
		 * //GENERATING DEPENDENCIES OF ACTIONS AS A GRAPH DirectedGraph<Action,
		 * DefaultEdge> g = planGenerator.generatePlanGraph(currentModel,
		 * targetModel);
		 * 
		 * // Plan plan = planGenerator.generate(currentModel, targetModel); //
		 * // // validate plan // if (!validator.validate(plan)) // throw new
		 * AssertionError();
		 */

		// execute plan in parallel now

		LOGGER.log(Level.INFO, "Starting threaded Action execution");

		int cpus = Runtime.getRuntime().availableProcessors();

		executor = new ThreadExecutor(cpus, 60, new LinkedBlockingQueue<Runnable>());
		
		graph = g;

		synchronized (graph) {
			neigh = new DirectedNeighborIndex<Action, DefaultEdge>(graph);

			initializeNeighbourDependencies();

			// dependencies();

			schedule();
			LOGGER.log(Level.INFO, "End of Scheduling thread actions");
			return true;
		}
	}

	private static void schedule() {

		LOGGER.log(Level.INFO, "Scheduling threaded Action execution");

		if (graph.vertexSet().size() == 0) {
			executor.shutdown();
		}

		synchronized (graph) {

			GraphIterator<Action, DefaultEdge> iterator = new DepthFirstIterator<Action, DefaultEdge>(
					graph);

			while (iterator.hasNext()) {
				Action task = (Action) iterator.next();
				executor.execute(task);
			}

			// executor.getKeepAliveTime(TimeUnit.MINUTES);
			// executor.allowCoreThreadTimeOut(true);

			executor.shutdown();

    		try {
    				//executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    				//executor.awaitTermination(1, TimeUnit.SECONDS);
    				executor.awaitTermination(100000000, TimeUnit.MICROSECONDS);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    				System.out.println("Tasks not completed successfully");
    			} finally{
    				//System.exit(0);
    				executor.shutdown();
    				LOGGER.log(Level.INFO, "Shutdown Executor thread");
    			}
		}

	}
	
	
	private static void scheduleSerial() {

		LOGGER.log(Level.INFO, "Scheduling serial Action execution");

		invGraph = new DefaultDirectedGraph<Action, DefaultEdge>(
				DefaultEdge.class);
		synchronized (graph) {
			Graphs.addGraphReversed(invGraph, graph);
		}
		GraphIterator<Action, DefaultEdge> iterator = new TopologicalOrderIterator<Action, DefaultEdge>(
				invGraph);

		int i=1;
		while (iterator.hasNext()) {
			Action task = (Action) iterator.next();
			task.run();
			LOGGER.log(Level.INFO, "Run task #" + i + " : "+ task.toString());
			i++;
		}

		LOGGER.log(Level.INFO, "End of serial execution");

	}


	

	public void getCurrentFromCDO() {
		currentModel = reasonerInterfacer.getDeploymentModel(true);
	}

	public void terminate() {
		this.reasonerInterfacer.close();
		this.execInterfacer.close();
	}

	public DeploymentModel getCurrentModel() {
		return currentModel;
	}

	public void setCurrentModel(DeploymentModel current) {
		currentModel = current;
	}

	private static boolean completedDependencies(Action task) {

		synchronized (graph) {
			if (graph.vertexSet().size() == 0)
				return true;

			int count = graph.outgoingEdgesOf(task).size();
			
			System.out.println("Dependency count " + count);
			printDependencies(task);
			
			if(count == 0)
				return true;
		}
		return false;
	}

	private static void dependencies() {

		synchronized (graph) {
			if (graph.vertexSet().size() == 0)
				return;

			for (Action act : graph.vertexSet()) {
				System.out.print("\nTasks dependent on " + act.toString());
				for (Action dep : neigh.successorsOf(act)) {
					System.out.print("==>" + dep.toString());
				}
			}
		}
	}

	private static void printDependencies(Action task) {

		synchronized (graph) {
			if (graph.vertexSet().size() == 0)
				return;

			int count = graph.outgoingEdgesOf(task).size();
			if (count == 0) {
				System.out.println("Dependent tasks of " + task.toString());
				for (DefaultEdge edge : graph.outgoingEdgesOf(task)) {
					System.out
							.print(" " + graph.getEdgeTarget(edge).toString());
				}
				return;
			}
		}
	}

	private static boolean deleteTask(Action task) {
		setNeighbourDependencies(task);
		synchronized (graph) {
			// System.out.println("Deleting task " + task.getClass() +
			// " from graph. Id: " + task.toString());
			LOGGER.log(Level.INFO, "Deleting task " + task.getClass()
					+ " from graph. Id: " + task.toString());
			return graph.removeVertex(task);
		}
	}

	private class ThreadExecutor extends ThreadPoolExecutor {

		public ThreadExecutor(int corePoolSize, long keepAliveSeconds,
				BlockingQueue workQueue) {
			super(corePoolSize, corePoolSize, keepAliveSeconds,
					TimeUnit.SECONDS, workQueue);
		}

		@Override
		protected void beforeExecute(Thread thread, Runnable runTask) {
 
			//Task task = (Task) runTask;
			Action task = (Action) runTask;
			//System.out.println("Task " + task.toString() + " by processor " + thread.getId());
			LOGGER.log(Level.INFO, "Task " + task.toString() + " by processor " + thread.getId());
//			LOG.info("Starting task: " + task.getName());
			
			while(!completedDependencies(task))
				System.out.println("Task " + task.toString() + " waiting to complete dependencies");
			
			//System.out.println(task.toString() + " dependencies complete");

			LOGGER.log(Level.INFO, task.toString() + " dependencies complete");
			super.beforeExecute(thread, runTask);
		}

		@Override
		public void execute(Runnable runTask) {
			super.execute(runTask);
			Action task = (Action) runTask;
			task.run();
			LOGGER.log(Level.INFO, "Ran Task " + task.toString());
		}

		@Override
		protected void afterExecute(Runnable runTask, Throwable e) {
			super.afterExecute(runTask, e);

			if (e == null) {
				//completed((Task) runTask);
			//	completed((DefaultAction) runTask);
				System.out.println("In afterExecute e is null");
				deleteTask((Action) runTask);
			} else {
				// failed((Task) runTask, e);
				// failed((DefaultAction) runTask, e);
				System.out.println("In afterExecute e is not null");
			}
		}

	}
}
