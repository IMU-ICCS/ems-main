/**
 */
package camel.deployment.util;

import camel.deployment.*;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see camel.deployment.DeploymentPackage
 * @generated
 */
public class DeploymentValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final DeploymentValidator INSTANCE = new DeploymentValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.deployment";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return DeploymentPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case DeploymentPackage.CLOUD_ML_ELEMENT:
				return validateCloudMLElement((CloudMLElement)value, diagnostics, context);
			case DeploymentPackage.DEPLOYMENT_MODEL:
				return validateDeploymentModel((DeploymentModel)value, diagnostics, context);
			case DeploymentPackage.COMPONENT:
				return validateComponent((Component)value, diagnostics, context);
			case DeploymentPackage.INTERNAL_COMPONENT:
				return validateInternalComponent((InternalComponent)value, diagnostics, context);
			case DeploymentPackage.EXTERNAL_COMPONENT:
				return validateExternalComponent((ExternalComponent)value, diagnostics, context);
			case DeploymentPackage.VM:
				return validateVM((VM)value, diagnostics, context);
			case DeploymentPackage.COMPONENT_GROUP:
				return validateComponentGroup((ComponentGroup)value, diagnostics, context);
			case DeploymentPackage.COMPUTATIONAL_RESOURCE:
				return validateComputationalResource((ComputationalResource)value, diagnostics, context);
			case DeploymentPackage.COMMUNICATION:
				return validateCommunication((Communication)value, diagnostics, context);
			case DeploymentPackage.COMMUNICATION_PORT:
				return validateCommunicationPort((CommunicationPort)value, diagnostics, context);
			case DeploymentPackage.PROVIDED_COMMUNICATION:
				return validateProvidedCommunication((ProvidedCommunication)value, diagnostics, context);
			case DeploymentPackage.REQUIRED_COMMUNICATION:
				return validateRequiredCommunication((RequiredCommunication)value, diagnostics, context);
			case DeploymentPackage.HOSTING:
				return validateHosting((Hosting)value, diagnostics, context);
			case DeploymentPackage.HOSTING_PORT:
				return validateHostingPort((HostingPort)value, diagnostics, context);
			case DeploymentPackage.PROVIDED_HOST:
				return validateProvidedHost((ProvidedHost)value, diagnostics, context);
			case DeploymentPackage.REQUIRED_HOST:
				return validateRequiredHost((RequiredHost)value, diagnostics, context);
			case DeploymentPackage.IMAGE:
				return validateImage((Image)value, diagnostics, context);
			case DeploymentPackage.COMPONENT_INSTANCE:
				return validateComponentInstance((ComponentInstance)value, diagnostics, context);
			case DeploymentPackage.INTERNAL_COMPONENT_INSTANCE:
				return validateInternalComponentInstance((InternalComponentInstance)value, diagnostics, context);
			case DeploymentPackage.EXTERNAL_COMPONENT_INSTANCE:
				return validateExternalComponentInstance((ExternalComponentInstance)value, diagnostics, context);
			case DeploymentPackage.VM_INSTANCE:
				return validateVMInstance((VMInstance)value, diagnostics, context);
			case DeploymentPackage.COMMUNICATION_INSTANCE:
				return validateCommunicationInstance((CommunicationInstance)value, diagnostics, context);
			case DeploymentPackage.COMMUNICATION_PORT_INSTANCE:
				return validateCommunicationPortInstance((CommunicationPortInstance)value, diagnostics, context);
			case DeploymentPackage.PROVIDED_COMMUNICATION_INSTANCE:
				return validateProvidedCommunicationInstance((ProvidedCommunicationInstance)value, diagnostics, context);
			case DeploymentPackage.REQUIRED_COMMUNICATION_INSTANCE:
				return validateRequiredCommunicationInstance((RequiredCommunicationInstance)value, diagnostics, context);
			case DeploymentPackage.HOSTING_INSTANCE:
				return validateHostingInstance((HostingInstance)value, diagnostics, context);
			case DeploymentPackage.HOSTING_PORT_INSTANCE:
				return validateHostingPortInstance((HostingPortInstance)value, diagnostics, context);
			case DeploymentPackage.PROVIDED_HOST_INSTANCE:
				return validateProvidedHostInstance((ProvidedHostInstance)value, diagnostics, context);
			case DeploymentPackage.REQUIRED_HOST_INSTANCE:
				return validateRequiredHostInstance((RequiredHostInstance)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCloudMLElement(CloudMLElement cloudMLElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)cloudMLElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)cloudMLElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(cloudMLElement, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the different_properties_in_CloudMLElement constraint of '<em>Cloud ML Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CLOUD_ML_ELEMENT__DIFFERENT_PROPERTIES_IN_CLOUD_ML_ELEMENT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tproperties->forAll(p1, p2 | p1 <> p2 implies p1.name <> p2.name)";

	/**
	 * Validates the different_properties_in_CloudMLElement constraint of '<em>Cloud ML Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCloudMLElement_different_properties_in_CloudMLElement(CloudMLElement cloudMLElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.CLOUD_ML_ELEMENT,
				 cloudMLElement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "different_properties_in_CloudMLElement",
				 CLOUD_ML_ELEMENT__DIFFERENT_PROPERTIES_IN_CLOUD_ML_ELEMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDeploymentModel(DeploymentModel deploymentModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)deploymentModel, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)deploymentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(deploymentModel, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponent(Component component, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)component, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)component, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(component, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponent_provided_component_ports_should_point_to_component(component, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the provided_component_ports_should_point_to_component constraint of '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String COMPONENT__PROVIDED_COMPONENT_PORTS_SHOULD_POINT_TO_COMPONENT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tprovidedCommunications->forAll(p | p.component = self) \n" +
		"\t\t\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\t\t\tprovidedHosts->forAll(p | p.component = self)";

	/**
	 * Validates the provided_component_ports_should_point_to_component constraint of '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponent_provided_component_ports_should_point_to_component(Component component, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.COMPONENT,
				 component,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "provided_component_ports_should_point_to_component",
				 COMPONENT__PROVIDED_COMPONENT_PORTS_SHOULD_POINT_TO_COMPONENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponent(InternalComponent internalComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)internalComponent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponent_provided_component_ports_should_point_to_component(internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponent_recursion_in_parts_of_component(internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponent_requiredHost_owner_is_self(internalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponent_requiredCommunications_owner_is_self(internalComponent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the recursion_in_parts_of_component constraint of '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT__RECURSION_IN_PARTS_OF_COMPONENT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tnot(self.contains(self,self))";

	/**
	 * Validates the recursion_in_parts_of_component constraint of '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponent_recursion_in_parts_of_component(InternalComponent internalComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.INTERNAL_COMPONENT,
				 internalComponent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "recursion_in_parts_of_component",
				 INTERNAL_COMPONENT__RECURSION_IN_PARTS_OF_COMPONENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the requiredHost_owner_is_self constraint of '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT__REQUIRED_HOST_OWNER_IS_SELF__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\trequiredHost.component = self";

	/**
	 * Validates the requiredHost_owner_is_self constraint of '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponent_requiredHost_owner_is_self(InternalComponent internalComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.INTERNAL_COMPONENT,
				 internalComponent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "requiredHost_owner_is_self",
				 INTERNAL_COMPONENT__REQUIRED_HOST_OWNER_IS_SELF__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the requiredCommunications_owner_is_self constraint of '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT__REQUIRED_COMMUNICATIONS_OWNER_IS_SELF__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\trequiredCommunications->forAll(p | p.component = self)";

	/**
	 * Validates the requiredCommunications_owner_is_self constraint of '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponent_requiredCommunications_owner_is_self(InternalComponent internalComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.INTERNAL_COMPONENT,
				 internalComponent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "requiredCommunications_owner_is_self",
				 INTERNAL_COMPONENT__REQUIRED_COMMUNICATIONS_OWNER_IS_SELF__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExternalComponent(ExternalComponent externalComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)externalComponent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(externalComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponent_provided_component_ports_should_point_to_component(externalComponent, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVM(VM vm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)vm, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)vm, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(vm, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponent_provided_component_ports_should_point_to_component(vm, diagnostics, context);
		if (result || diagnostics != null) result &= validateVM_one_alternative_provided(vm, diagnostics, context);
		if (result || diagnostics != null) result &= validateVM_correct_input(vm, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the one_alternative_provided constraint of '<em>VM</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String VM__ONE_ALTERNATIVE_PROVIDED__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\t(vmType <> null and (minRam = 0 and maxRam = 0 and ramUnit = null and minStorage = 0 and maxStorage = 0 and storageUnit = null and minCPU = 0 and maxCPU = 0 and minCores = 0 and maxCores = 0)) or (vmType = null and (((minRam > 0 or maxRam > 0) and ramUnit <> null) or ((minStorage > 0 or maxStorage > 0) and storageUnit <> null) or (minCPU > 0 or maxCPU > 0) or (minCores > 0 or maxCores > 0)))";

	/**
	 * Validates the one_alternative_provided constraint of '<em>VM</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVM_one_alternative_provided(VM vm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.VM,
				 vm,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "one_alternative_provided",
				 VM__ONE_ALTERNATIVE_PROVIDED__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the correct_input constraint of '<em>VM</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String VM__CORRECT_INPUT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tminRam >= 0 and maxRam >= 0 and minCores >= 0 and maxCores >= 0 and minStorage >= 0 and maxStorage >= 0 and minCPU >= 0 and maxCPU >= 0";

	/**
	 * Validates the correct_input constraint of '<em>VM</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVM_correct_input(VM vm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.VM,
				 vm,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_input",
				 VM__CORRECT_INPUT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentGroup(ComponentGroup componentGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)componentGroup, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)componentGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(componentGroup, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComputationalResource(ComputationalResource computationalResource, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)computationalResource, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)computationalResource, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(computationalResource, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunication(Communication communication, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)communication, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)communication, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(communication, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationPort(CommunicationPort communicationPort, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)communicationPort, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)communicationPort, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(communicationPort, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProvidedCommunication(ProvidedCommunication providedCommunication, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)providedCommunication, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)providedCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(providedCommunication, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequiredCommunication(RequiredCommunication requiredCommunication, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)requiredCommunication, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)requiredCommunication, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(requiredCommunication, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHosting(Hosting hosting, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)hosting, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)hosting, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(hosting, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHostingPort(HostingPort hostingPort, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)hostingPort, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)hostingPort, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(hostingPort, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProvidedHost(ProvidedHost providedHost, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)providedHost, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)providedHost, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(providedHost, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequiredHost(RequiredHost requiredHost, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)requiredHost, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)requiredHost, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(requiredHost, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImage(Image image, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)image, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)image, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(image, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentInstance(ComponentInstance componentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)componentInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_instance_ports_belong_to_instance(componentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_port_instances_of_correct_type(componentInstance, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the component_instance_ports_belong_to_instance constraint of '<em>Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String COMPONENT_INSTANCE__COMPONENT_INSTANCE_PORTS_BELONG_TO_INSTANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tprovidedCommunicationInstances->forAll(p | p.componentInstance = self)\n" +
		"\t\t\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\t\t\tprovidedHostInstances->forAll(p | p.componentInstance = self)";

	/**
	 * Validates the component_instance_ports_belong_to_instance constraint of '<em>Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentInstance_component_instance_ports_belong_to_instance(ComponentInstance componentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.COMPONENT_INSTANCE,
				 componentInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "component_instance_ports_belong_to_instance",
				 COMPONENT_INSTANCE__COMPONENT_INSTANCE_PORTS_BELONG_TO_INSTANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the component_port_instances_of_correct_type constraint of '<em>Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String COMPONENT_INSTANCE__COMPONENT_PORT_INSTANCES_OF_CORRECT_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tprovidedCommunicationInstances->forAll(p | type.providedCommunications->includes(p.type))\n" +
		"\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\tprovidedHostInstances->forAll(p | type.providedHosts->includes(p.type))";

	/**
	 * Validates the component_port_instances_of_correct_type constraint of '<em>Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentInstance_component_port_instances_of_correct_type(ComponentInstance componentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.COMPONENT_INSTANCE,
				 componentInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "component_port_instances_of_correct_type",
				 COMPONENT_INSTANCE__COMPONENT_PORT_INSTANCES_OF_CORRECT_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponentInstance(InternalComponentInstance internalComponentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)internalComponentInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_instance_ports_belong_to_instance(internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_port_instances_of_correct_type(internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponentInstance_internal_component_instance_ports_belong_to_instance(internalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponentInstance_internal_component_port_instances_of_correct_type(internalComponentInstance, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the internal_component_instance_ports_belong_to_instance constraint of '<em>Internal Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT_INSTANCE__INTERNAL_COMPONENT_INSTANCE_PORTS_BELONG_TO_INSTANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\trequiredCommunicationInstances->forAll(p | p.componentInstance = self)\n" +
		"\t\t\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\t\t\trequiredHostInstance.componentInstance = self";

	/**
	 * Validates the internal_component_instance_ports_belong_to_instance constraint of '<em>Internal Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponentInstance_internal_component_instance_ports_belong_to_instance(InternalComponentInstance internalComponentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.INTERNAL_COMPONENT_INSTANCE,
				 internalComponentInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "internal_component_instance_ports_belong_to_instance",
				 INTERNAL_COMPONENT_INSTANCE__INTERNAL_COMPONENT_INSTANCE_PORTS_BELONG_TO_INSTANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the internal_component_port_instances_of_correct_type constraint of '<em>Internal Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT_INSTANCE__INTERNAL_COMPONENT_PORT_INSTANCES_OF_CORRECT_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\ttype.oclIsKindOf(InternalComponent)\n" +
		"\t\t\t\t\t\tand \n" +
		"\t\t\t\t\t\trequiredCommunicationInstances->forAll(p | type.oclAsType(InternalComponent).requiredCommunications->includes(p.type))\n" +
		"\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\trequiredHostInstance.type = type.oclAsType(InternalComponent).requiredHost";

	/**
	 * Validates the internal_component_port_instances_of_correct_type constraint of '<em>Internal Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponentInstance_internal_component_port_instances_of_correct_type(InternalComponentInstance internalComponentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.INTERNAL_COMPONENT_INSTANCE,
				 internalComponentInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "internal_component_port_instances_of_correct_type",
				 INTERNAL_COMPONENT_INSTANCE__INTERNAL_COMPONENT_PORT_INSTANCES_OF_CORRECT_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExternalComponentInstance(ExternalComponentInstance externalComponentInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)externalComponentInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_instance_ports_belong_to_instance(externalComponentInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_port_instances_of_correct_type(externalComponentInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVMInstance(VMInstance vmInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)vmInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_instance_ports_belong_to_instance(vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateComponentInstance_component_port_instances_of_correct_type(vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateVMInstance_correct_type_for_vm_instance(vmInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateVMInstance_vm_instance_type_map_to_VMInfo(vmInstance, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the correct_type_for_vm_instance constraint of '<em>VM Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String VM_INSTANCE__CORRECT_TYPE_FOR_VM_INSTANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\ttype.oclIsTypeOf(VM)";

	/**
	 * Validates the correct_type_for_vm_instance constraint of '<em>VM Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVMInstance_correct_type_for_vm_instance(VMInstance vmInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.VM_INSTANCE,
				 vmInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_type_for_vm_instance",
				 VM_INSTANCE__CORRECT_TYPE_FOR_VM_INSTANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the vm_instance_type_map_to_VMInfo constraint of '<em>VM Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String VM_INSTANCE__VM_INSTANCE_TYPE_MAP_TO_VM_INFO__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\t(type.oclIsTypeOf(VM) and type.oclAsType(VM).vmType <> null) implies hasInfo.ofVM = type.oclAsType(VM).vmType";

	/**
	 * Validates the vm_instance_type_map_to_VMInfo constraint of '<em>VM Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVMInstance_vm_instance_type_map_to_VMInfo(VMInstance vmInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.VM_INSTANCE,
				 vmInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "vm_instance_type_map_to_VMInfo",
				 VM_INSTANCE__VM_INSTANCE_TYPE_MAP_TO_VM_INFO__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationInstance(CommunicationInstance communicationInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)communicationInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(communicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCommunicationInstance_communication_instance_correct_port_instances(communicationInstance, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the communication_instance_correct_port_instances constraint of '<em>Communication Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String COMMUNICATION_INSTANCE__COMMUNICATION_INSTANCE_CORRECT_PORT_INSTANCES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\trequiredCommunicationInstance.type = type.requiredCommunication\n" +
		"\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\tprovidedCommunicationInstance.type = type.providedCommunication";

	/**
	 * Validates the communication_instance_correct_port_instances constraint of '<em>Communication Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationInstance_communication_instance_correct_port_instances(CommunicationInstance communicationInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.COMMUNICATION_INSTANCE,
				 communicationInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "communication_instance_correct_port_instances",
				 COMMUNICATION_INSTANCE__COMMUNICATION_INSTANCE_CORRECT_PORT_INSTANCES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationPortInstance(CommunicationPortInstance communicationPortInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)communicationPortInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)communicationPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(communicationPortInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProvidedCommunicationInstance(ProvidedCommunicationInstance providedCommunicationInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)providedCommunicationInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)providedCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(providedCommunicationInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequiredCommunicationInstance(RequiredCommunicationInstance requiredCommunicationInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)requiredCommunicationInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)requiredCommunicationInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(requiredCommunicationInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHostingInstance(HostingInstance hostingInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)hostingInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(hostingInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateHostingInstance_containment_instance_correct_port_instance(hostingInstance, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the containment_instance_correct_port_instance constraint of '<em>Hosting Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String HOSTING_INSTANCE__CONTAINMENT_INSTANCE_CORRECT_PORT_INSTANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tprovidedHostInstance.type = type.providedHost\n" +
		"\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\trequiredHostInstance.type = type.requiredHost";

	/**
	 * Validates the containment_instance_correct_port_instance constraint of '<em>Hosting Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHostingInstance_containment_instance_correct_port_instance(HostingInstance hostingInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(DeploymentPackage.Literals.HOSTING_INSTANCE,
				 hostingInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "containment_instance_correct_port_instance",
				 HOSTING_INSTANCE__CONTAINMENT_INSTANCE_CORRECT_PORT_INSTANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHostingPortInstance(HostingPortInstance hostingPortInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)hostingPortInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)hostingPortInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(hostingPortInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProvidedHostInstance(ProvidedHostInstance providedHostInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)providedHostInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)providedHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(providedHostInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequiredHostInstance(RequiredHostInstance requiredHostInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)requiredHostInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)requiredHostInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateCloudMLElement_different_properties_in_CloudMLElement(requiredHostInstance, diagnostics, context);
		return result;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //DeploymentValidator
