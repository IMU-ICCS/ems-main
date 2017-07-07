/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Parameter#getSolution <em>Solution</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Parameter#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getParameter()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Parameter extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Solution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solution</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solution</em>' reference.
	 * @see #setSolution(Solution)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getParameter_Solution()
	 * @model
	 * @generated
	 */
	Solution getSolution();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Parameter#getSolution <em>Solution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solution</em>' reference.
	 * @see #getSolution()
	 * @generated
	 */
	void setSolution(Solution value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getParameter_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Parameter#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Parameter
