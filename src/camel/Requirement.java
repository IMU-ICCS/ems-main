/**
 */
package camel;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.Requirement#getPriority <em>Priority</em>}</li>
 *   <li>{@link camel.Requirement#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getRequirement()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='non_negative_priorities_for_requirement'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot non_negative_priorities_for_requirement='\n\t\t\t\t\tself.priority >= 0.0'"
 * @extends CDOObject
 * @generated
 */
public interface Requirement extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(double)
	 * @see camel.CamelPackage#getRequirement_Priority()
	 * @model
	 * @generated
	 */
	double getPriority();

	/**
	 * Sets the value of the '{@link camel.Requirement#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(double value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see camel.CamelPackage#getRequirement_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link camel.Requirement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // Requirement
