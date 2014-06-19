/**
 */
package camel.cerif;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.ResourceGroup#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.ResourceGroup#getContainsResource <em>Contains Resource</em>}</li>
 *   <li>{@link camel.cerif.ResourceGroup#getLevel <em>Level</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getResourceGroup()
 * @model
 * @generated
 */
public interface ResourceGroup extends Resource {
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
	 * @see camel.cerif.CerifPackage#getResourceGroup_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.cerif.ResourceGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Contains Resource</b></em>' reference list.
	 * The list contents are of type {@link camel.cerif.Resource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contains Resource</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contains Resource</em>' reference list.
	 * @see camel.cerif.CerifPackage#getResourceGroup_ContainsResource()
	 * @model required="true"
	 * @generated
	 */
	EList<Resource> getContainsResource();

	/**
	 * Returns the value of the '<em><b>Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Level</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Level</em>' attribute.
	 * @see #setLevel(int)
	 * @see camel.cerif.CerifPackage#getResourceGroup_Level()
	 * @model derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot derivation='if (self.containsResource->select(p | p.oclIsTypeOf(ResourceGroup))->size() = 0) then 1 else (self.containsResource->select(p | p.oclIsTypeOf(ResourceGroup) and (self.containsResource->forAll(p1 | p1.oclIsTypeOf(ResourceGroup) implies p.oclAsType(ResourceGroup).level >= p1.oclAsType(ResourceGroup).level)))->asOrderedSet()->first().oclAsType(ResourceGroup).level + 1) endif'"
	 * @generated
	 */
	int getLevel();

	/**
	 * Sets the value of the '{@link camel.cerif.ResourceGroup#getLevel <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Level</em>' attribute.
	 * @see #getLevel()
	 * @generated
	 */
	void setLevel(int value);

} // ResourceGroup
