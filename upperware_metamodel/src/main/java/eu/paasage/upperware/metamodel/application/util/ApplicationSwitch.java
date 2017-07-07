/**
 */
package eu.paasage.upperware.metamodel.application.util;

import eu.paasage.upperware.metamodel.application.*;

import eu.paasage.upperware.metamodel.cp.BooleanExpression;
import eu.paasage.upperware.metamodel.cp.CPElement;
import eu.paasage.upperware.metamodel.cp.Expression;

import eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage
 * @generated
 */
public class ApplicationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ApplicationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationSwitch() {
		if (modelPackage == null) {
			modelPackage = ApplicationPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ApplicationPackage.PAASAGE_CONFIGURATION: {
				PaasageConfiguration paasageConfiguration = (PaasageConfiguration)theEObject;
				T result = casePaasageConfiguration(paasageConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.VIRTUAL_MACHINE: {
				VirtualMachine virtualMachine = (VirtualMachine)theEObject;
				T result = caseVirtualMachine(virtualMachine);
				if (result == null) result = casePaaSageCPElement(virtualMachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.VIRTUAL_MACHINE_PROFILE: {
				VirtualMachineProfile virtualMachineProfile = (VirtualMachineProfile)theEObject;
				T result = caseVirtualMachineProfile(virtualMachineProfile);
				if (result == null) result = caseCloudMLElementUpperware(virtualMachineProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.CLOUD_ML_ELEMENT_UPPERWARE: {
				CloudMLElementUpperware cloudMLElementUpperware = (CloudMLElementUpperware)theEObject;
				T result = caseCloudMLElementUpperware(cloudMLElementUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.RESOURCE_UPPERWARE: {
				ResourceUpperware resourceUpperware = (ResourceUpperware)theEObject;
				T result = caseResourceUpperware(resourceUpperware);
				if (result == null) result = casePaaSageCPElement(resourceUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.MEMORY: {
				Memory memory = (Memory)theEObject;
				T result = caseMemory(memory);
				if (result == null) result = caseResourceUpperware(memory);
				if (result == null) result = casePaaSageCPElement(memory);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.STORAGE: {
				Storage storage = (Storage)theEObject;
				T result = caseStorage(storage);
				if (result == null) result = caseResourceUpperware(storage);
				if (result == null) result = casePaaSageCPElement(storage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.CPU: {
				CPU cpu = (CPU)theEObject;
				T result = caseCPU(cpu);
				if (result == null) result = caseResourceUpperware(cpu);
				if (result == null) result = casePaaSageCPElement(cpu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.PROVIDER: {
				Provider provider = (Provider)theEObject;
				T result = caseProvider(provider);
				if (result == null) result = casePaaSageCPElement(provider);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.APPLICATION_COMPONENT: {
				ApplicationComponent applicationComponent = (ApplicationComponent)theEObject;
				T result = caseApplicationComponent(applicationComponent);
				if (result == null) result = caseCloudMLElementUpperware(applicationComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.ELASTICITY_RULE: {
				ElasticityRule elasticityRule = (ElasticityRule)theEObject;
				T result = caseElasticityRule(elasticityRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.ACTION_UPPERWARE: {
				ActionUpperware actionUpperware = (ActionUpperware)theEObject;
				T result = caseActionUpperware(actionUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.CONDITION_UPPERWARE: {
				ConditionUpperware conditionUpperware = (ConditionUpperware)theEObject;
				T result = caseConditionUpperware(conditionUpperware);
				if (result == null) result = caseBooleanExpression(conditionUpperware);
				if (result == null) result = caseExpression(conditionUpperware);
				if (result == null) result = caseCPElement(conditionUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.PAA_SAGE_VARIABLE: {
				PaaSageVariable paaSageVariable = (PaaSageVariable)theEObject;
				T result = casePaaSageVariable(paaSageVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.PAA_SAGE_GOAL: {
				PaaSageGoal paaSageGoal = (PaaSageGoal)theEObject;
				T result = casePaaSageGoal(paaSageGoal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.REQUIRED_FEATURE: {
				RequiredFeature requiredFeature = (RequiredFeature)theEObject;
				T result = caseRequiredFeature(requiredFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.DIMENSION: {
				Dimension dimension = (Dimension)theEObject;
				T result = caseDimension(dimension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.PROVIDER_DIMENSION: {
				ProviderDimension providerDimension = (ProviderDimension)theEObject;
				T result = caseProviderDimension(providerDimension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.IMAGE_UPPERWARE: {
				ImageUpperware imageUpperware = (ImageUpperware)theEObject;
				T result = caseImageUpperware(imageUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackage.COMPONENT_METRIC_RELATIONSHIP: {
				ComponentMetricRelationship componentMetricRelationship = (ComponentMetricRelationship)theEObject;
				T result = caseComponentMetricRelationship(componentMetricRelationship);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Paasage Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Paasage Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaasageConfiguration(PaasageConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Virtual Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Virtual Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVirtualMachine(VirtualMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Virtual Machine Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Virtual Machine Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVirtualMachineProfile(VirtualMachineProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cloud ML Element Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cloud ML Element Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCloudMLElementUpperware(CloudMLElementUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceUpperware(ResourceUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Memory</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Memory</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemory(Memory object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Storage</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Storage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStorage(Storage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CPU</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CPU</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCPU(CPU object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProvider(Provider object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationComponent(ApplicationComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Elasticity Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Elasticity Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElasticityRule(ElasticityRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionUpperware(ActionUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionUpperware(ConditionUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Paa Sage Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Paa Sage Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaaSageVariable(PaaSageVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Paa Sage Goal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Paa Sage Goal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaaSageGoal(PaaSageGoal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Required Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Required Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequiredFeature(RequiredFeature object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDimension(Dimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Provider Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Provider Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProviderDimension(ProviderDimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Image Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Image Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImageUpperware(ImageUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Component Metric Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Component Metric Relationship</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComponentMetricRelationship(ComponentMetricRelationship object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Paa Sage CP Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Paa Sage CP Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaaSageCPElement(PaaSageCPElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CP Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CP Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCPElement(CPElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanExpression(BooleanExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ApplicationSwitch
