/**
 */
package camel.provider;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.ProviderModel#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link camel.provider.ProviderModel#getRootFeature <em>Root Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getProviderModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProviderModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link camel.provider.Constraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see camel.provider.ProviderPackage#getProviderModel_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Root Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Feature</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Feature</em>' containment reference.
	 * @see #setRootFeature(Feature)
	 * @see camel.provider.ProviderPackage#getProviderModel_RootFeature()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Feature getRootFeature();

	/**
	 * Sets the value of the '{@link camel.provider.ProviderModel#getRootFeature <em>Root Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Feature</em>' containment reference.
	 * @see #getRootFeature()
	 * @generated
	 */
	void setRootFeature(Feature value);

} // ProviderModel
