/**
 */
package eu.paasage.upperware.metamodel.application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Elasticity Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getAction <em>Action</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ElasticityRule extends CDOObject {
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference.
	 * @see #setAction(ActionUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule_Action()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ActionUpperware getAction();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getAction <em>Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' containment reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(ActionUpperware value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(ConditionUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ConditionUpperware getCondition();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(ConditionUpperware value);

} // ElasticityRule
