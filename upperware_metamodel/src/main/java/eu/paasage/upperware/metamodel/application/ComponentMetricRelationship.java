/**
 */
package eu.paasage.upperware.metamodel.application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Metric Relationship</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getComponent <em>Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getMetricId <em>Metric Id</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getComponentMetricRelationship()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ComponentMetricRelationship extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(ApplicationComponent)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getComponentMetricRelationship_Component()
	 * @model required="true"
	 * @generated
	 */
	ApplicationComponent getComponent();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(ApplicationComponent value);

	/**
	 * Returns the value of the '<em><b>Metric Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Id</em>' attribute.
	 * @see #setMetricId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getComponentMetricRelationship_MetricId()
	 * @model
	 * @generated
	 */
	String getMetricId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getMetricId <em>Metric Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric Id</em>' attribute.
	 * @see #getMetricId()
	 * @generated
	 */
	void setMetricId(String value);

} // ComponentMetricRelationship
