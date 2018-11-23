/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.util;

import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class CpModelHelper {
	
	protected HashSet<String> LOCKS = new HashSet<>();
	
	protected static int counter = 0;

	private int id;
    private CDOClientXImpl cdoClient;
	
	public CpModelHelper() {
		id = ++counter;
        this.cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));
		//log.debug("CpModelHelper.<init>():  ** NEW HELPER INSTANCE #{} **", id);
	}
	
	public Map<String,Double> getMetricVariableValues(String cpModelPath, Set<String> variableNames) throws ConcurrentAccessException {
		log.debug("CpModelHelper.getMetricVariableValues(): BEGIN: helper-id={}, cp-model-path={}, variables={}", id, cpModelPath, variableNames);
		
		// lock resource
		lockCpModel(cpModelPath, "getMetricVariableValues()");

        CDOSessionX session = null;
		CDOTransaction transaction = null;
		
		Map<String,Double> results = new HashMap<>();
		try {
			// retrieve CP model (open transaction)
            session = cdoClient.getSession();
            transaction = session.openTransaction();
			CDOResource resource = transaction.getResource(cpModelPath);
			ConstraintProblem cpModel = (ConstraintProblem) resource.getContents().get(0);	// one element in list - 0: ConstraintProblem  (see cp_generator, class :
																							// eu.paasage.upperware.profiler.generator.orchestrator.GenerationOrchestrator)
			
			// check if all metric variable names in CP model exist in 'metricValues' map
			EList<Solution> solutions = cpModel.getSolution();
			int solId = cpModel.getDeployedSolutionId();
			if (solutions.size()>0 && solId>=0) {
				solutions.get(solId).getVariableValue().stream().forEach(cvv -> {
					log.debug("CpModelHelper.getMetricVariableValues():  Got Metric Variable Value (MVV) from CP model: {}", cvv);
					String varId = cvv.getVariable().getId();
					log.debug("CpModelHelper.getMetricVariableValues():  Metric Variable: id={}, type={}", varId, cvv.getVariable().getVariableType());
					if (variableNames.contains(varId)) {
						log.debug("CpModelHelper.getMetricVariableValues():  Found Metric Variable: id={}, class={}", varId, cvv.getClass().getName());
						NumericValueUpperware value = cvv.getValue();
						double doubleVal;
						if (BooleanValueUpperware.class.isAssignableFrom(value.getClass())) results.put(varId, new Double(doubleVal = ((BooleanValueUpperware)value).isValue()?1d:0d));
						else if (IntegerValueUpperware.class.isAssignableFrom(value.getClass())) results.put(varId, new Double(doubleVal = ((IntegerValueUpperware)value).getValue()));
						else if (LongValueUpperware.class.isAssignableFrom(value.getClass())) results.put(varId, new Double(doubleVal = ((LongValueUpperware)value).getValue()));
						else if (FloatValueUpperware.class.isAssignableFrom(value.getClass())) results.put(varId, new Double(doubleVal = ((FloatValueUpperware)value).getValue()));
						else if (DoubleValueUpperware.class.isAssignableFrom(value.getClass())) results.put(varId, new Double(doubleVal = ((DoubleValueUpperware)value).getValue()));
						else throw new IllegalArgumentException("Encountered Non-numeric Metric Variable: "+varId+", class="+value.getClass().getName());
						log.info("CpModelHelper.getMetricVariableValues():  Metric Variable Value: {} = {}", varId, doubleVal);
					}
				});
				if (results.keySet().size()!=variableNames.size()) {
					HashSet<String> missingVars = new HashSet<String>(variableNames);
					missingVars.removeAll(results.keySet());
					log.error("CpModelHelper.getMetricVariableValues(): ERROR: Not found values for all Metric Variables: {}", missingVars);
					throw new IllegalArgumentException("Not found values for all Metric Variables: "+missingVars);
				}
			} else {
				log.warn("CpModelHelper.getMetricVariableValues(): CP model does not contain any solutions or no solution have been deployed: cp-model-path={}, deployed-solution-id={}", cpModelPath, solId);
			}

		} catch (Exception ex) {
			log.error("CpModelHelper.getMetricVariableValues(): EXCEPTION: helper-id={}, Exception={}", id, ex);
			throw new RuntimeException("helper-id="+id, ex);
		} finally {
			if (transaction!=null) transaction.close();
			if (session!=null) session.getSession().close();

			// release resource
			releaseCpModel(cpModelPath, "getMetricVariableValues()");
		}
		
		// return timestamp
		log.debug("CpModelHelper.getMetricVariableValues(): END: Metric Variable Values: {}", results);
		return results;
	}
	
	// ------------------------------------------------------------------------

	protected void lockCpModel(String cpModelPath, String caller) throws ConcurrentAccessException {
		synchronized (LOCKS) {
			if (! LOCKS.contains(cpModelPath)) {
				LOCKS.add(cpModelPath);
			} else {
				throw new ConcurrentAccessException("CpModelHelper."+caller+"->lockCpModel: Resource is locked: "+cpModelPath);
				//return null;
			}
		}
		log.debug("CpModelHelper.{}->lockCpModel(): ACQUIRED LOCK ON: helper-id={}, cp-path={}", caller, id, cpModelPath);
	}
	
	protected void releaseCpModel(String cpModelPath, String caller) {
		synchronized (LOCKS) {
			LOCKS.remove(cpModelPath);
		}
		log.debug("CpModelHelper.{}->releaseCpModel(): RELEASED LOCK ON: helper-id={}, cp-path={}", caller, id, cpModelPath);
	}
	
	// ------------------------------------------------------------------------
	
//XXX:DEL??: after development
	public static void main(String[] args) throws Exception {
		CpModelHelper helper = new CpModelHelper();
		helper.loadCpModel(args[0], args[1]);
	}
	
//XXX:DEL??: after development
	public void loadCpModel(String pathName, String cpModelPath) {
        CDOSessionX session = null;
		CDOTransaction transaction = null;
		try {
			log.info("CpModelHelper.loadCpModel(): BEGIN: helper-id={}, cp-model-file={}, cdo-path={}", id, pathName, cpModelPath);
			
			final org.eclipse.emf.ecore.resource.ResourceSet rs = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
			rs.getPackageRegistry().put(TypesPackage.eNS_URI, TypesPackage.eINSTANCE);
			rs.getPackageRegistry().put(CpPackage.eNS_URI, CpPackage.eINSTANCE);
			org.eclipse.emf.ecore.resource.Resource res = rs.getResource(org.eclipse.emf.common.util.URI.createFileURI(pathName), true);
			EList<org.eclipse.emf.ecore.EObject> contents = res.getContents();
			
			session = cdoClient.getSession();
            transaction = session.openTransaction();
			
			CDOResource resource = transaction.getOrCreateResource(cpModelPath);
			resource.getContents().addAll(contents);
			
			transaction.commit();
			log.info("CpModelHelper.loadCpModel(): END: helper-id={}", id);
			
		} catch (Exception ex) {
			log.error("CpModelHelper.loadCpModel(): EXCEPTION: helper-id={}, Exception={}", id, ex);
			throw new RuntimeException("helper-id="+id, ex);
		} finally {
			if (transaction!=null) transaction.close();
			if (session!=null) session.getSession().close();
		}
	}
}
