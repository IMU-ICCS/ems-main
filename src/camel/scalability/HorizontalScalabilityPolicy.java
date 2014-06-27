/**
 */
package camel.scalability;

import camel.deployment.InternalComponent;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Horizontal Scalability Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.HorizontalScalabilityPolicy#getMinInstances <em>Min Instances</em>}</li>
 *   <li>{@link camel.scalability.HorizontalScalabilityPolicy#getMaxInstances <em>Max Instances</em>}</li>
 *   <li>{@link camel.scalability.HorizontalScalabilityPolicy#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getHorizontalScalabilityPolicy()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ScalabilityPolicy_min_max_enforcement'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ScalabilityPolicy_min_max_enforcement='\n\t\t\t\t\t\t\t self.minInstances > 0 and self.maxInstances > 0 and self.minInstances <= self.maxInstances'"
 * @generated
 */
public interface HorizontalScalabilityPolicy extends ScalabilityPolicy {
	/**
	 * Returns the value of the '<em><b>Min Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Instances</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Instances</em>' attribute.
	 * @see #setMinInstances(int)
	 * @see camel.scalability.ScalabilityPackage#getHorizontalScalabilityPolicy_MinInstances()
	 * @model required="true"
	 * @generated
	 */
	int getMinInstances();

	/**
	 * Sets the value of the '{@link camel.scalability.HorizontalScalabilityPolicy#getMinInstances <em>Min Instances</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Instances</em>' attribute.
	 * @see #getMinInstances()
	 * @generated
	 */
	void setMinInstances(int value);

	/**
	 * Returns the value of the '<em><b>Max Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Instances</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Instances</em>' attribute.
	 * @see #setMaxInstances(int)
	 * @see camel.scalability.ScalabilityPackage#getHorizontalScalabilityPolicy_MaxInstances()
	 * @model required="true"
	 * @generated
	 */
	int getMaxInstances();

	/**
	 * Sets the value of the '{@link camel.scalability.HorizontalScalabilityPolicy#getMaxInstances <em>Max Instances</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Instances</em>' attribute.
	 * @see #getMaxInstances()
	 * @generated
	 */
	void setMaxInstances(int value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(InternalComponent)
	 * @see camel.scalability.ScalabilityPackage#getHorizontalScalabilityPolicy_Component()
	 * @model
	 * @generated
	 */
	InternalComponent getComponent();

	/**
	 * Sets the value of the '{@link camel.scalability.HorizontalScalabilityPolicy#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(InternalComponent value);

} // HorizontalScalabilityPolicy
