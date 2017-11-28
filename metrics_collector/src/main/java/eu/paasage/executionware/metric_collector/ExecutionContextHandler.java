/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.CompositeMetricInstance;
import eu.paasage.camel.metric.MetricApplicationBinding;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.executionware.metric_collector.MetricCollector.Mode;
import eu.paasage.executionware.metric_collector.pubsub.PublicationServer;
import eu.paasage.mddb.cdo.client.CDOClient;

public class ExecutionContextHandler implements Runnable{
	private ThreadPoolExecutor tpe;
	private CDOID ID;
	private static final int CORE_POOL_SIZE = 10, MAX_POOL_SIZE = 20, ALIVE_TIME = 100;
	private Hashtable<CDOID,MetricHandler> metricToHandler;
	private HashSet<CDOID> metricIDs, localMetricIDs;
	private Hashtable<CDOID,HashSet<CDOID>> childToParent;
	private volatile boolean run = true;
	private String host,port;
	private MetricCollector.Mode mode;
	private MetricCollector.DBType dbType;
	private PublicationServer server;
	private CDOListener listener;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Test.class);
	
	public ExecutionContextHandler(Set<CDOID> metricIDs, CDOID ID, String host, String port, MetricCollector.Mode mode, MetricCollector.DBType dbType, PublicationServer server){
		this.ID = ID;
		this.host = host;
		this.port = port;
		this.mode = mode;
		this.dbType = dbType;
		this.server = server;
		if (metricIDs != null && metricIDs.size() != 0){
			tpe = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,ALIVE_TIME,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CORE_POOL_SIZE));
			metricToHandler = new Hashtable<CDOID,MetricHandler>();
			expandMetricIDs(metricIDs);
		}
		else run = false;
	}
	
	
	
	private void expandMetricID(CDOView view, CDOID id, boolean rootMetric, HashSet<CDOID> metricIDs, boolean publish){
		CompositeMetricInstance mi = (CompositeMetricInstance)view.getObject(id);
		EList<MetricInstance> instances = mi.getComposingMetricInstances();
		for (MetricInstance instance: instances){
			if (instance instanceof CompositeMetricInstance){
				CompositeMetricInstance cmi = (CompositeMetricInstance)instance;
				CDOID child = instance.cdoID();
				if (mode == MetricCollector.Mode.LOCAL || cmi.getObjectBinding() instanceof MetricApplicationBinding){
					logger.info("Got in with mode: " + mode + " metric: " + id + " instance: " + child);
					HashSet<CDOID> parents = childToParent.get(child);
					if (parents == null) parents = new HashSet<CDOID>();
					parents.add(id);
					childToParent.put(child, parents);
					if (metricIDs.contains(child)) expandMetricID(view,child,true,metricIDs,publish);
					else expandMetricID(view,child,false,metricIDs,publish);
				}
				else if (publish){
					if (CDOUtils.canPush(child,view)){
						logger.info("Can push: " + child);
						localMetricIDs.add(child);
					}
					//logger.info("Just publishing metric instance: " + child);
					//expandMetricID(view,child,false,metricIDs,publish);
				}
			}
		}
		boolean inserted = this.metricIDs.add(id);
		if (inserted) spawnMetricThread(id,rootMetric,view);
	}
	
	//Expand IDs with child aggregated metrics and spawn all threads
	private void expandMetricIDs(Set<CDOID> metricIDs){
		this.metricIDs = new HashSet<CDOID>();
		boolean publish = (mode == MetricCollector.Mode.GLOBAL && server != null); 
		if (publish) localMetricIDs = new HashSet<CDOID>();
		childToParent = new Hashtable<CDOID,HashSet<CDOID>>();
		HashSet<CDOID> comparisonIDs = new HashSet<CDOID>();
		
		CDOClient client = new CDOClient();
		CDOView view = client.openView();
		for (CDOID id: metricIDs){
			comparisonIDs.add(id);
		}
		for (CDOID id: metricIDs){
			expandMetricID(view,id,true,comparisonIDs,publish);
		}
		view.close();
		client.closeSession();
		if (publish && !localMetricIDs.isEmpty()){
			logger.info("Creating listeners for metric ids: " + localMetricIDs);
			listener = new CDOListener(server,localMetricIDs);
			tpe.execute(listener);
		}
	}
	
	private void spawnMetricThread(CDOID id, boolean rootMetric, CDOView view){
		boolean push = false;
		if (mode == Mode.GLOBAL) push = CDOUtils.canPush(id,view);
		MetricHandler mh = null;
		if (push){
			logger.info("Can push: " + id);
			mh = new MetricHandler(id,ID,!rootMetric,host,port,mode,dbType,server);
		}
		else mh = new MetricHandler(id,ID,!rootMetric,host,port,mode,dbType,null);
		tpe.execute(mh);
		metricToHandler.put(id,mh);
	}

	public void run() {
		//Run until terminated
		while (run){
			try{
				Thread.sleep(10000);
			}
			catch(Exception e){
				logger.error("ExecutionContextHandler was interrupted while sleeping",e);
				//e.printStackTrace();
			}
		}
	}
	
	public void terminate(){
		logger.info("Terminating all metric handlers for ExecutionHandler: " + ID);
		//Just terminate all MetricHandlers
		for (MetricHandler mh: metricToHandler.values()){
			mh.terminate();
		}
		tpe.shutdownNow();
		run = false;
	}
	
	private void updateMetricID(CDOView view, CDOID id){
		MetricHandler mh = metricToHandler.remove(id);
		mh.terminate();
		this.metricIDs.remove(id);
		CompositeMetricInstance mi = (CompositeMetricInstance)view.getObject(id);
		EList<MetricInstance> instances = mi.getComposingMetricInstances();
		for (MetricInstance instance: instances){
			if (instance.getMetric() instanceof CompositeMetric){
				CDOID child = instance.cdoID();
				HashSet<CDOID> parents = childToParent.get(child);
				if (parents != null){
					parents.remove(id);
					if (parents.isEmpty()){
						childToParent.remove(child);
						updateMetricID(view,id);
					}
				}
			}
		}
	}
	
	public void update(Set<CDOID> metricIDs){
		CDOClient client = new CDOClient();
		CDOView view = client.openView();
		for (CDOID s: metricIDs){
			updateMetricID(view,s);
		}
		view.close();
		client.closeSession();
		expandMetricIDs(metricIDs);
	}
	
	public CDOID getID(){
		return ID;
	}
	
	public boolean equals(Object o){
		if (o instanceof ExecutionContextHandler){
			ExecutionContextHandler ech = (ExecutionContextHandler)o;
			if (ID.equals(ech.getID())) return true;
		}
		return false;
	}
	
	public int hashCode(){
		return ID.hashCode();
	}
}
