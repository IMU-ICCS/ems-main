/**
 */
package camel.organisation;

import camel.Action;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.ResourceGroup#getName <em>Name</em>}</li>
 *   <li>{@link camel.organisation.ResourceGroup#getContainsResource <em>Contains Resource</em>}</li>
 *   <li>{@link camel.organisation.ResourceGroup#getLevel <em>Level</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getResourceGroup()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Resource_Group_Not_Any_Cycle'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Resource_Group_Not_Any_Cycle='\n\t\t\tnot(self.checkRecursiveness(self,self.oclAsType(Resource))) and self.containsResource->forAll(p1, p2 | (p1.oclIsTypeOf(ResourceGroup) and p2.oclIsTypeOf(ResourceGroup) and p1 <> p2) implies not(p1.oclAsType(ResourceGroup).checkRecursiveness(p1.oclAsType(ResourceGroup),p2)))'"
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
	 * @see camel.organisation.OrganisationPackage#getResourceGroup_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.organisation.ResourceGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Contains Resource</b></em>' reference list.
	 * The list contents are of type {@link camel.organisation.Resource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contains Resource</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contains Resource</em>' reference list.
	 * @see camel.organisation.OrganisationPackage#getResourceGroup_ContainsResource()
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
	 * @see camel.organisation.OrganisationPackage#getResourceGroup_Level()
	 * @model required="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot derivation='if (self.containsResource->select(p | p.oclIsTypeOf(ResourceGroup))->size() = 0) then 1 else (self.containsResource->select(p | p.oclIsTypeOf(ResourceGroup) and (self.containsResource->forAll(p1 | p1.oclIsTypeOf(ResourceGroup) implies p.oclAsType(ResourceGroup).level >= p1.oclAsType(ResourceGroup).level)))->asOrderedSet()->first().oclAsType(ResourceGroup).level + 1) endif'"
	 * @generated
	 */
	int getLevel();

	/**
	 * Sets the value of the '{@link camel.organisation.ResourceGroup#getLevel <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Level</em>' attribute.
	 * @see #getLevel()
	 * @generated
	 */
	void setLevel(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model actionsMany="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='self.containsResource->forAll(p | (p.oclIsTypeOf(ResourceGroup) and p.oclAsType(ResourceGroup).allowsActionsOnResources(actions)) or not(p.oclIsTypeOf(ResourceGroup)) and p.oclAsType(Resource).allowsActions(actions))'"
	 * @generated
	 */
	boolean allowsActionsOnResources(EList<Action> actions);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Checking recursiveness for ResourceGroup: \" + rg);\n\t\tResourceGroup rgCheck = (ResourceGroup)resource;\n\t\tfor (Resource res: rg.getContainsResource()){\n\t\t\tif (res instanceof ResourceGroup){\n\t\t\t\tResourceGroup rg2 = (ResourceGroup)res;\n\t\t\t\tif (rg2.getName().equals(rgCheck.getName())) return Boolean.TRUE;\n\t\t\t\tif (checkRecursiveness(rg2,resource)) return Boolean.TRUE;\n\t\t\t}\n\t\t}\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkRecursiveness(ResourceGroup rg, Resource resource);

} // ResourceGroup
