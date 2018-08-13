///* This Source Code Form is subject to the terms of the Mozilla Public
// * License, v. 2.0. If a copy of the MPL was not distributed with this
// * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
//
//package eu.paasage.upperware.solvertodeployment.utils;
//
//import camel.core.Attribute;
//import camel.deployment.*;
//import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
//import eu.paasage.upperware.solvertodeployment.lib.S2DException;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.emf.common.util.BasicEList;
//import org.eclipse.emf.common.util.EList;
//
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Slf4j
//public class SolverToDeployementHelper {
//
//	//////////////////////////////////////////////////////////////////////////////////////
//	// Internal Component Instance
//	//////////////////////////////////////////////////////////////////////////////////////
//
//	public static EList<InternalComponentInstance> createSoftwareComponentInstance(String componentName, DeploymentModel deploymentModel, int nb) throws S2DException {
//		InternalComponent internalComponent = findSoftwareComponent(deploymentModel, componentName);
//		return IntStream.range(0, nb)
//				.mapToObj(value -> CloudMLHelper.createSCInstance(internalComponent))
//				.collect(Collectors.toCollection(BasicEList::new));
//	}
//
//	public static InternalComponent findSoftwareComponent(DeploymentModel deploymentModel, String componentName) throws S2DException {
//		return deploymentModel.getInternalComponents().stream()
//				.filter(internalComponent -> internalComponent.getName().equalsIgnoreCase(componentName))
//				.findFirst()
//				.orElseThrow(() -> new S2DException("Unable to find "+ componentName+ " component in camel model"));
//	}
//
//	//////////////////////////////////////////////////////////////////////////////////////
//	// Hosting Instance
//	//////////////////////////////////////////////////////////////////////////////////////
//
//	public static Hosting findHosting(InternalComponent component, DeploymentModel deploymentModel) throws S2DException {
//		return deploymentModel.getHostings()
//				.stream()
//				.filter(h -> h.getRequiredHost().getName().equals(component.getRequiredHost().getName()))
//				.findFirst()
//				.orElseThrow(() -> new S2DException("Unable to find hosting for application component name :" + component.getName() + " . Seems to have error in original model"));
//	}
//
//	public static HostingInstance createHostingInstance(VMInstance vmInstance, InternalComponentInstance internalComponentInstance, DeploymentModel deploymentModel) throws S2DException {
//		InternalComponent internalComponent = (InternalComponent) internalComponentInstance.getType();
//		Hosting hosting = findHosting(internalComponent, deploymentModel);
//
//		HostingInstance hostingInstance = CloudMLHelper.buildNewHostingInstance(internalComponentInstance.getType().getName(), vmInstance, internalComponentInstance, hosting);
//		if(hostingInstance == null) {
//			throw new S2DException("Unable to find hosting for application component name" + internalComponentInstance.getName());
//		}
//		return hostingInstance;
//	}
//
//	//////////////////////////////////////////////////////////////////////////////////////
//	// VM Instance
//	//////////////////////////////////////////////////////////////////////////////////////
//
//	public static EList<VMInstance> searchAndCreateVMInstance(ProviderModel providerModel, VM result, int cardinality) throws S2DException {
//
//		EList<VMInstance> vmInstances = new BasicEList<>();
//		for(int i=0; i<cardinality; i++) {
//			VMInstance vmInstanceResult = CloudMLHelper.createVMInstance(result);
//			Attribute attribute = CloudMLHelper.findVMType(providerModel);
//			vmInstanceResult.setVmType(attribute);
//			vmInstanceResult.setVmTypeValue(attribute.getValue());
//			vmInstances.add(vmInstanceResult);
//		}
//		return vmInstances;
//	}
//
//}
