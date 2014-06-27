/**
 */
package camel.deployment;

import camel.provider.Attribute;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cloud ML Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * abstract class NamedElement
 * {
 * attribute name : String { id };
 * }
 * class CloudMLProperty extends NamedElement
 * {
 * attribute value : String;
 * }
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.CloudMLElement#getName <em>Name</em>}</li>
 *   <li>{@link camel.deployment.CloudMLElement#getProperties <em>Properties</em>}</li>
 *   <li>{@link camel.deployment.CloudMLElement#getResources <em>Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getCloudMLElement()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='different_properties_in_CloudMLElement'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot different_properties_in_CloudMLElement='\n\t\t\t\t\t\tproperties->forAll(p1, p2 | p1 <> p2 implies p1.name <> p2.name)'"
 * @extends CDOObject
 * @generated
 */
public interface CloudMLElement extends CDOObject {
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
	 * @see camel.deployment.DeploymentPackage#getCloudMLElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.deployment.CloudMLElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link camel.provider.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getCloudMLElement_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attribute> getProperties();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.ComputationalResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getCloudMLElement_Resources()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComputationalResource> getResources();

} // CloudMLElement
