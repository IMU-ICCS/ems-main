/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Metric Relationship</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl#getMetricId <em>Metric Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentMetricRelationshipImpl extends CDOObjectImpl implements ComponentMetricRelationship {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentMetricRelationshipImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponent getComponent() {
		return (ApplicationComponent)eGet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(ApplicationComponent newComponent) {
		eSet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__COMPONENT, newComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMetricId() {
		return (String)eGet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__METRIC_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetricId(String newMetricId) {
		eSet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__METRIC_ID, newMetricId);
	}

} //ComponentMetricRelationshipImpl
