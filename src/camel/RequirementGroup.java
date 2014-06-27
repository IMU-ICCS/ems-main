/**
 */
package camel;

import camel.organisation.User;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirement Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.RequirementGroup#getPosedBy <em>Posed By</em>}</li>
 *   <li>{@link camel.RequirementGroup#getRequirement <em>Requirement</em>}</li>
 *   <li>{@link camel.RequirementGroup#getOnApplication <em>On Application</em>}</li>
 *   <li>{@link camel.RequirementGroup#getRequirementOperator <em>Requirement Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getRequirementGroup()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RequirementGroup_Members_Posed_By_Same_User applications_in_sub_groups_in_group requirement_group_no_conflict_policies requirements_in_group_refer_to_group_applications'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot RequirementGroup_Members_Posed_By_Same_User='\n\t\t\t\t\t\t\tself.requirement-> forAll(p | (p.oclIsTypeOf(RequirementGroup) implies p.oclAsType(RequirementGroup).posedBy = self.posedBy) and (not(p.oclIsTypeOf(RequirementGroup)) implies self.posedBy.hasRequirement->includes(p)))' applications_in_sub_groups_in_group='\n\t\t\t\t\t\t\tself.requirement->forAll(p | p.oclIsTypeOf(RequirementGroup) implies p.oclAsType(RequirementGroup).onApplication->forAll(a | self.onApplication->includes(a)))' requirement_group_no_conflict_policies='\n\t\t\t\t\t\t\t\tif (self.requirementOperator = RequirementOperatorType::AND) then self.requirement->forAll(p1, p2 | (p1 <> p2 and p1.oclIsKindOf(camel::scalability::ScalabilityPolicy) and p2.oclIsKindOf(camel::scalability::ScalabilityPolicy) and p1.oclType() = p2.oclType()) implies\n\t\t\t\t\t\t\t\t\tif (p1.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy)) then p1.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> p2.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm\n\t\t\t\t\t\t\t\t\telse p1.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> p2.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t) else true endif' requirements_in_group_refer_to_group_applications='\n\t\t\t\t\t\t\t\tif (onApplication->notEmpty()) then\n\t\t\t\t\t\t\t\t\trequirement->forAll(p | p.oclIsTypeOf(camel::sla::ServiceLevelObjectiveType) implies onApplication->includes(p.oclAsType(camel::sla::ServiceLevelObjectiveType).customServiceLevel.metric.objectBinding.executionContext.ofApplication))\n\t\t\t\t\t\t\t\telse true\n\t\t\t\t\t\t\t\tendif'"
 * @generated
 */
public interface RequirementGroup extends Requirement {
	/**
	 * Returns the value of the '<em><b>Posed By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posed By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posed By</em>' reference.
	 * @see #setPosedBy(User)
	 * @see camel.CamelPackage#getRequirementGroup_PosedBy()
	 * @model required="true"
	 * @generated
	 */
	User getPosedBy();

	/**
	 * Sets the value of the '{@link camel.RequirementGroup#getPosedBy <em>Posed By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posed By</em>' reference.
	 * @see #getPosedBy()
	 * @generated
	 */
	void setPosedBy(User value);

	/**
	 * Returns the value of the '<em><b>Requirement</b></em>' reference list.
	 * The list contents are of type {@link camel.Requirement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requirement</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirement</em>' reference list.
	 * @see camel.CamelPackage#getRequirementGroup_Requirement()
	 * @model required="true"
	 * @generated
	 */
	EList<Requirement> getRequirement();

	/**
	 * Returns the value of the '<em><b>On Application</b></em>' reference list.
	 * The list contents are of type {@link camel.Application}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Application</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Application</em>' reference list.
	 * @see camel.CamelPackage#getRequirementGroup_OnApplication()
	 * @model
	 * @generated
	 */
	EList<Application> getOnApplication();

	/**
	 * Returns the value of the '<em><b>Requirement Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.RequirementOperatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requirement Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirement Operator</em>' attribute.
	 * @see camel.RequirementOperatorType
	 * @see #setRequirementOperator(RequirementOperatorType)
	 * @see camel.CamelPackage#getRequirementGroup_RequirementOperator()
	 * @model required="true"
	 * @generated
	 */
	RequirementOperatorType getRequirementOperator();

	/**
	 * Sets the value of the '{@link camel.RequirementGroup#getRequirementOperator <em>Requirement Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requirement Operator</em>' attribute.
	 * @see camel.RequirementOperatorType
	 * @see #getRequirementOperator()
	 * @generated
	 */
	void setRequirementOperator(RequirementOperatorType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model contextMany="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Checking recursiveness for RequirementGroup: \" + rg1);\r\n\t\tfor (Requirement r2: rg1.getRequirement()){\r\n\t\t\tEList<RequirementGroup> context2 = null;\r\n\t\t\tif (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<RequirementGroup>();\r\n\t\t\telse context2 = new org.eclipse.emf.common.util.BasicEList<RequirementGroup>(context);\r\n\t\t\tif (!resources){\r\n\t\t\t\tif (r2 instanceof RequirementGroup){\r\n\t\t\t\t\tRequirementGroup rg2 = (RequirementGroup)r2;\r\n\t\t\t\t\tif (context == null || !context.contains(rg2)){\r\n\t\t\t\t\t\tcontext2.add(rg2);\r\n\t\t\t\t\t\tif (rg2.getId().equals(r.getId())) return Boolean.TRUE;\r\n\t\t\t\t\t\tif (checkRecursiveness(rg2,r,resources,context2)) return Boolean.TRUE;\r\n\t\t\t\t\t}\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t\telse{\r\n\t\t\t\tif (r.getId().equals(r2.getId())) return true;\r\n\t\t\t\tif (r2 instanceof RequirementGroup){\r\n\t\t\t\t\tRequirementGroup rg2 = (RequirementGroup)r2;\r\n\t\t\t\t\tif (context == null || !context.contains(rg2)){\r\n\t\t\t\t\t\tcontext2.add(rg2);\r\n\t\t\t\t\t\tif (checkRecursiveness(rg2,r,resources,context2)) return Boolean.TRUE;\r\n\t\t\t\t\t}\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkRecursiveness(RequirementGroup rg1, Requirement r, boolean resources, EList<RequirementGroup> context);

} // RequirementGroup
