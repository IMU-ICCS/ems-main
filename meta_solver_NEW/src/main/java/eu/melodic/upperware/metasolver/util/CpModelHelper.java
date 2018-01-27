/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.util;

import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;

import java.util.HashSet;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;

import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
//import eu.paasage.upperware.metamodel.cp.DeltaUtility;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.Solution;
//import eu.paasage.upperware.metamodel.cp.*;
//import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;

// From: eu.paasage.mddb.cdo.client.CDOClient
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.ecore.EPackage;

import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.Model;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
//import eu.paasage.camel.dsl.CamelDslStandaloneSetup;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.ExecutionPackage;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.location.LocationFactory;
import eu.paasage.camel.location.LocationModel;
import eu.paasage.camel.location.LocationPackage;
import eu.paasage.camel.metric.MetricModel;
import eu.paasage.camel.metric.MetricPackage;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.DataCenter;
import eu.paasage.camel.organisation.ExternalIdentifier;
import eu.paasage.camel.organisation.OrganisationFactory;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.organisation.PaaSageCredentials;
import eu.paasage.camel.organisation.Role;
import eu.paasage.camel.organisation.RoleAssignment;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.organisation.UserGroup;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.ScalabilityPackage;
import eu.paasage.camel.security.SecurityModel;
import eu.paasage.camel.security.SecurityPackage;
import eu.paasage.camel.requirement.RequirementPackage;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.camel.unit.UnitModel;
import eu.paasage.camel.unit.UnitPackage;

import eu.paasage.camel.Application;
//import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricContext;
import eu.paasage.camel.metric.Property;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

@Component
@Slf4j
public class CpModelHelper {
	
	protected HashSet<String> LOCKS = new HashSet<>();
	
	protected static int counter = 0;
	
    @Autowired
	private MetaSolverProperties properties;
	
	private int id;
	private CDONet4jSession cdoSession;
	
	public CpModelHelper() {
		id = ++counter;
		//log.debug("CpModelHelper.<init>():  ** NEW INSTANCE #{} **", id);
	}
	
	public boolean updateCpModelWithMetricValues(String applicationId, String cpModelPath, Map<String,String> metricValues) {
		log.debug("CpModelHelper.updateCpModelWithMetricValues(): BEGIN: helper-id={}, app-id={}, cp-path={}, mvv={}", id, applicationId, cpModelPath, metricValues);
		
		// lock resource
		synchronized (LOCKS) {
			if (! LOCKS.contains(cpModelPath)) {
				LOCKS.add(cpModelPath);
			} else
				//throw new ConcurrentAccessException("CpModelHelper.updateCpModelWithMetricValues: Resource is locked: "+cpModelPath);
				return false;
		}
		log.debug("CpModelHelper.updateCpModelWithMetricValues(): ACQUIRED LOCK ON: helper-id={}, cp-path={}", id, cpModelPath);
		
		CDOTransaction transaction = null;
		try {
			// retrieve CP model (open transaction)
			transaction = cdoSession.openTransaction();
			CDOResource resource = transaction.getResource(cpModelPath);
			ConstraintProblem cpModel = (ConstraintProblem)resource.getContents().get(0);
			
			// check if all metric variable names in CP model exist in 'metricValues' map
			/*EList<MetricVariable> cpMetricVarList = cpModel.getMetricVariables();
			boolean allfound = true;
			for (MetricVariable mv : cpMetricVarList) {
				log.info("CpModelHelper.updateCpModelWithMetricValues():  Found Metric Variable: id={}, type={}", mv.getId(), mv.getType());
				if (!metricValues.containsKey(mv.getId())) {
					log.error("CpModelHelper.updateCpModelWithMetricValues(): NOT FOUND Metric Variable: id={}", mv.getId());
					allfound = false;
					//XXX: -OR- ???
					// any missing variables must be added with a default value (WHERE CAN WE FIND 'default metric variable values'??)
				}
			}
			if (!allfound) {
				log.debug("CpModelHelper.updateCpModelWithMetricValues(): END: helper-id={}, message=Missing MVV", id);
				return false;
			}*/
			
			
			// add metric variable values for all (extracted) metric variable names
			//XXX: R1.5 hack: metric variable are stored as Constants in CP model, with their Id's prefixed with 'METRIC_'
			EList<Constant> cpConstList = cpModel.getConstants();
			for (Constant c : cpConstList) {
				String id = c.getId().trim();
				if (id.startsWith("METRIC_") && !id.startsWith("METRIC_UTILITYTYPE_")) {
					String mvName = id.substring("METRIC_".intern().length());
					String mvValue = metricValues.get(mvName);
					if (mvValue!=null && !mvValue.isEmpty()) {
						BasicTypeEnum type = c.getType();
						NumericValueUpperware newVal = null;
						switch (type) {
							case INTEGER: newVal = TypesFactory.eINSTANCE.createIntegerValueUpperware(); ((IntegerValueUpperware)newVal).setValue(Integer.parseInt(mvValue)); break;
							case FLOAT:	  newVal = TypesFactory.eINSTANCE.createFloatValueUpperware(); ((FloatValueUpperware)newVal).setValue(Float.parseFloat(mvValue)); break;
							case DOUBLE:  newVal = TypesFactory.eINSTANCE.createDoubleValueUpperware(); ((DoubleValueUpperware)newVal).setValue(Double.parseDouble(mvValue)); break;
							case LONG:    newVal = TypesFactory.eINSTANCE.createLongValueUpperware(); ((LongValueUpperware)newVal).setValue(Long.parseLong(mvValue)); break;
						}
						c.setValue(newVal);
					}
				}
			}

			
			// commit changes
			transaction.commit();
			transaction = null;
		} catch (Exception ex) {
			log.error("CpModelHelper.updateCpModelWithMetricValues(): EXCEPTION: helper-id={}, Exception={}", id, ex);
			return false;
		} finally {
			if (transaction!=null) transaction.rollback();
			
			// release resource
			synchronized (LOCKS) {
				LOCKS.remove(cpModelPath);
			}
			log.debug("CpModelHelper.updateCpModelWithMetricValues(): RELEASED LOCK ON: helper-id={}, cp-path={}", id, cpModelPath);
		}
		
		// return timestamp
		log.debug("CpModelHelper.updateCpModelWithMetricValues(): END: helper-id={}", id);
		return true;
	}
	
	public double[] getSolutionUtilities(String applicationId, String cpModelPath) {
		log.debug("CpModelHelper.getSolutionUtilities(): BEGIN: helper-id={}, app-id={}, cp-path={}", id, applicationId, cpModelPath);
		
		// lock resource
		synchronized (LOCKS) {
			if (! LOCKS.contains(cpModelPath)) {
				LOCKS.add(cpModelPath);
			} else
				//throw new ConcurrentAccessException("CpModelHelper.getSolutionUtilities: Resource is locked: "+cpModelPath);
				return null;
		}
		log.debug("CpModelHelper.getSolutionUtilities(): ACQUIRED LOCK ON: helper-id={}, cp-path={}", id, cpModelPath);
		
		CDOView view = null;
		try {
			// retrieve CP model (open view)
			view = cdoSession.openView();
			CDOResource resource = view.getResource(cpModelPath);
			ConstraintProblem cpModel = (ConstraintProblem)resource.getContents().get(0);
			
			// get solutions list
			EList<Solution> solutions = cpModel.getSolution();
			int size = solutions.size();
			
			if (size==0) return null;	// No solutions found
			
			double[] retUv = new double[2];
			
			// get deployed solution's utility value, if a deployed solution exists
			if (size>1) {
				Solution depSol = solutions.get( size-2 );
				retUv[0] = ((DoubleValueUpperware)depSol.getUtilityValue()).getValue();
			} else 
				retUv[0] = -1;
			
			// get new solution's utility value
			Solution newSol = solutions.get( size-1 );
			retUv[1] = ((DoubleValueUpperware)newSol.getUtilityValue()).getValue();
			
			log.debug("CpModelHelper.getSolutionUtilities(): END: helper-id={}", id);
			return retUv;
			
		} catch (Exception ex) {
			log.error("CpModelHelper.getSolutionUtilities(): EXCEPTION: helper-id={}, Exception={}", id, ex);
			return null;
		} finally {
			if (view!=null) view.close();
			
			// release resource
			synchronized (LOCKS) {
				LOCKS.remove(cpModelPath);
			}
			log.debug("CpModelHelper.getSolutionUtilities(): RELEASED LOCK ON: helper-id={}, cp-path={}", id, cpModelPath);
		}
	}
	
	// ------------------------------------------------------------------------
	
	@PostConstruct
	public void connect() {
		//log.debug("CpModelHelper.connect(): #{}", id);
		CpPackage.eINSTANCE.eClass();
		cdoSession = openCdoSession();
	}
	
	@PreDestroy
	public void disconnect() {
		log.info("CpModelHelper.disconnect(): #{}", id);
		cdoSession.close();
	}

	protected CDONet4jSession openCdoSession() {
		//XXX: TODO: replace with config from 'eu.paasage.mddb.cdo.client.properties'
		log.debug("CpModelHelper.openCdoSession: CDO configuration: {}", properties.getCdo());
		MetaSolverProperties.CdoConfig config = properties.getCdo();
		
		String host = config.getHost().trim();
		int port = config.getPort();
		String connectionStr = host+":"+port;
		String repoName = config.getRepositoryName();
		boolean auth = config.isSecure();
		String username = config.getUsername();
		String password = config.getPassword();
		
		return openCdoSession(connectionStr, repoName, false, username, password);
	}
	
	protected CDONet4jSession openCdoSession(String connectionStr, String repoName) {
		return openCdoSession(connectionStr, repoName, false, null, null);
	}
	
	protected CDONet4jSession openCdoSession(String connectionStr, String repoName, boolean requiresAuth, String cdoUsername, String cdoPassword) {
		// initialize and activate a container
		final IManagedContainer container = ContainerUtil.createContainer();
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		// CDONet4jUtil.prepareContainer(container);
		container.activate();

		// create a Net4j TCP connector
		final IConnector connector = (IConnector) TCPUtil.getConnector(container, connectionStr);

		// create the session configuration
		CDONet4jSessionConfiguration config = CDONet4jUtil.createNet4jSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName(repoName);

		// setup authentication
		if (requiresAuth) {
			log.info("** CDO server requires authentication - Username: %s\n", cdoUsername);
			org.eclipse.net4j.util.security.PasswordCredentialsProvider credentialsProvider = new org.eclipse.net4j.util.security.PasswordCredentialsProvider(cdoUsername, cdoPassword);
			config.setCredentialsProvider(credentialsProvider);
		}

		// create the actual session with the repository
		CDONet4jSession cdoSession = config.openNet4jSession();

		// register CAMEL packages
		cdoSession.getPackageRegistry().putEPackage(EresourcePackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(CamelPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(ScalabilityPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(DeploymentPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(OrganisationPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(ProviderPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(SecurityPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(ExecutionPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(TypePackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(RequirementPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(MetricPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(UnitPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(LocationPackage.eINSTANCE);

		return cdoSession;
	}
}
