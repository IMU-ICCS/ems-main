/**
 */
package eu.paasage.upperware.metamodel.application.util;

import eu.paasage.upperware.metamodel.application.*;

import eu.paasage.upperware.metamodel.cp.BooleanExpression;
import eu.paasage.upperware.metamodel.cp.CPElement;
import eu.paasage.upperware.metamodel.cp.Expression;

import eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage
 * @generated
 */
public class ApplicationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ApplicationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ApplicationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationSwitch<Adapter> modelSwitch =
		new ApplicationSwitch<Adapter>() {
			@Override
			public Adapter casePaasageConfiguration(PaasageConfiguration object) {
				return createPaasageConfigurationAdapter();
			}
			@Override
			public Adapter caseVirtualMachine(VirtualMachine object) {
				return createVirtualMachineAdapter();
			}
			@Override
			public Adapter caseVirtualMachineProfile(VirtualMachineProfile object) {
				return createVirtualMachineProfileAdapter();
			}
			@Override
			public Adapter caseCloudMLElementUpperware(CloudMLElementUpperware object) {
				return createCloudMLElementUpperwareAdapter();
			}
			@Override
			public Adapter caseResourceUpperware(ResourceUpperware object) {
				return createResourceUpperwareAdapter();
			}
			@Override
			public Adapter caseMemory(Memory object) {
				return createMemoryAdapter();
			}
			@Override
			public Adapter caseStorage(Storage object) {
				return createStorageAdapter();
			}
			@Override
			public Adapter caseCPU(CPU object) {
				return createCPUAdapter();
			}
			@Override
			public Adapter caseProvider(Provider object) {
				return createProviderAdapter();
			}
			@Override
			public Adapter caseApplicationComponent(ApplicationComponent object) {
				return createApplicationComponentAdapter();
			}
			@Override
			public Adapter caseElasticityRule(ElasticityRule object) {
				return createElasticityRuleAdapter();
			}
			@Override
			public Adapter caseActionUpperware(ActionUpperware object) {
				return createActionUpperwareAdapter();
			}
			@Override
			public Adapter caseConditionUpperware(ConditionUpperware object) {
				return createConditionUpperwareAdapter();
			}
			@Override
			public Adapter casePaaSageVariable(PaaSageVariable object) {
				return createPaaSageVariableAdapter();
			}
			@Override
			public Adapter casePaaSageGoal(PaaSageGoal object) {
				return createPaaSageGoalAdapter();
			}
			@Override
			public Adapter caseRequiredFeature(RequiredFeature object) {
				return createRequiredFeatureAdapter();
			}
			@Override
			public Adapter caseDimension(Dimension object) {
				return createDimensionAdapter();
			}
			@Override
			public Adapter caseProviderDimension(ProviderDimension object) {
				return createProviderDimensionAdapter();
			}
			@Override
			public Adapter caseImageUpperware(ImageUpperware object) {
				return createImageUpperwareAdapter();
			}
			@Override
			public Adapter caseComponentMetricRelationship(ComponentMetricRelationship object) {
				return createComponentMetricRelationshipAdapter();
			}
			@Override
			public Adapter casePaaSageCPElement(PaaSageCPElement object) {
				return createPaaSageCPElementAdapter();
			}
			@Override
			public Adapter caseCPElement(CPElement object) {
				return createCPElementAdapter();
			}
			@Override
			public Adapter caseExpression(Expression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseBooleanExpression(BooleanExpression object) {
				return createBooleanExpressionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration <em>Paasage Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration
	 * @generated
	 */
	public Adapter createPaasageConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.VirtualMachine <em>Virtual Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachine
	 * @generated
	 */
	public Adapter createVirtualMachineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile <em>Virtual Machine Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile
	 * @generated
	 */
	public Adapter createVirtualMachineProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware <em>Cloud ML Element Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.CloudMLElementUpperware
	 * @generated
	 */
	public Adapter createCloudMLElementUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ResourceUpperware <em>Resource Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ResourceUpperware
	 * @generated
	 */
	public Adapter createResourceUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.Memory <em>Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.Memory
	 * @generated
	 */
	public Adapter createMemoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.Storage <em>Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.Storage
	 * @generated
	 */
	public Adapter createStorageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.CPU <em>CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.CPU
	 * @generated
	 */
	public Adapter createCPUAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.Provider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.Provider
	 * @generated
	 */
	public Adapter createProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent
	 * @generated
	 */
	public Adapter createApplicationComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ElasticityRule <em>Elasticity Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ElasticityRule
	 * @generated
	 */
	public Adapter createElasticityRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ActionUpperware <em>Action Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ActionUpperware
	 * @generated
	 */
	public Adapter createActionUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware <em>Condition Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ConditionUpperware
	 * @generated
	 */
	public Adapter createConditionUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable <em>Paa Sage Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable
	 * @generated
	 */
	public Adapter createPaaSageVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal <em>Paa Sage Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal
	 * @generated
	 */
	public Adapter createPaaSageGoalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.RequiredFeature <em>Required Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature
	 * @generated
	 */
	public Adapter createRequiredFeatureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ProviderDimension <em>Provider Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ProviderDimension
	 * @generated
	 */
	public Adapter createProviderDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ImageUpperware <em>Image Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ImageUpperware
	 * @generated
	 */
	public Adapter createImageUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship <em>Component Metric Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.application.ComponentMetricRelationship
	 * @generated
	 */
	public Adapter createComponentMetricRelationshipAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement <em>Paa Sage CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement
	 * @generated
	 */
	public Adapter createPaaSageCPElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.CPElement <em>CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.CPElement
	 * @generated
	 */
	public Adapter createCPElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanExpression
	 * @generated
	 */
	public Adapter createBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ApplicationAdapterFactory
