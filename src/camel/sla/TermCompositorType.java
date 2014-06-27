/**
 */
package camel.sla;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Term Compositor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.TermCompositorType#getId <em>Id</em>}</li>
 *   <li>{@link camel.sla.TermCompositorType#getExactlyOne <em>Exactly One</em>}</li>
 *   <li>{@link camel.sla.TermCompositorType#getOneOrMore <em>One Or More</em>}</li>
 *   <li>{@link camel.sla.TermCompositorType#getAll <em>All</em>}</li>
 *   <li>{@link camel.sla.TermCompositorType#getServiceReference <em>Service Reference</em>}</li>
 *   <li>{@link camel.sla.TermCompositorType#getGuaranteeTerm <em>Guarantee Term</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getTermCompositorType()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='TermCompositorType_Non_Recurs correct_service_reference'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot TermCompositorType_Non_Recurs='\n\t\t\t\t\t\tnot(self.checkRecursiveness(self,self,null)) and self.exactlyOne->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null))) and self.oneOrMore->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null))) and self.all->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null)))' correct_service_reference='\n\t\t\t\t\t/* concrete service only at VM level, PaaS needs to be covered also \052/\n\t\t\t\t\tserviceReference->forAll(p | (p.oclIsKindOf(camel::deployment::Component) or p.oclIsTypeOf(camel::VMType)))'"
 * @extends CDOObject
 * @generated
 */
public interface TermCompositorType extends CDOObject {
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
	 * @see camel.sla.SlaPackage#getTermCompositorType_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link camel.sla.TermCompositorType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Exactly One</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.TermCompositorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exactly One</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exactly One</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getTermCompositorType_ExactlyOne()
	 * @model containment="true"
	 * @generated
	 */
	EList<TermCompositorType> getExactlyOne();

	/**
	 * Returns the value of the '<em><b>One Or More</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.TermCompositorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One Or More</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One Or More</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getTermCompositorType_OneOrMore()
	 * @model containment="true"
	 * @generated
	 */
	EList<TermCompositorType> getOneOrMore();

	/**
	 * Returns the value of the '<em><b>All</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.TermCompositorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getTermCompositorType_All()
	 * @model containment="true"
	 * @generated
	 */
	EList<TermCompositorType> getAll();

	/**
	 * Returns the value of the '<em><b>Service Reference</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Reference</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Reference</em>' reference list.
	 * @see camel.sla.SlaPackage#getTermCompositorType_ServiceReference()
	 * @model
	 * @generated
	 */
	EList<EObject> getServiceReference();

	/**
	 * Returns the value of the '<em><b>Guarantee Term</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.GuaranteeTermType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guarantee Term</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guarantee Term</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getTermCompositorType_GuaranteeTerm()
	 * @model containment="true"
	 * @generated
	 */
	EList<GuaranteeTermType> getGuaranteeTerm();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model contextMany="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Checking recursiveness for TermCompositorType: \" + tct1.getId());\r\n\t\tfor (TermCompositorType tct: tct1.getAll()){\r\n\t\t\t\tSystem.out.println(\"Checking candidate: \" + tct.getId());\r\n\t\t\t\tEList<TermCompositorType> context2 = null;\r\n\t\t\t\tif (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>();\r\n\t\t\t\telse context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>(context); \r\n\t\t\t\tcontext2.add(tct);\r\n\t\t\t\tif ((context == null || (context != null && !context.contains(tct))) && (tct.getId().equals(tct2.getId()) || checkRecursiveness(tct,tct2,context2))){\r\n\t\t\t\t\tSystem.out.println(\"FOUND IN ALL\");\r\n\t\t\t\t\treturn Boolean.TRUE;\r\n\t\t\t\t}\r\n\t\t}\r\n\t\tfor (TermCompositorType tct: tct1.getOneOrMore()){\r\n\t\t\tSystem.out.println(\"Checking candidate: \" + tct.getId());\r\n\t\t\tEList<TermCompositorType> context2 = null;\r\n\t\t\tif (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>();\r\n\t\t\telse context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>(context); \r\n\t\t\tcontext2.add(tct);\r\n\t\t\tif ((context == null || (context != null && !context.contains(tct))) && (tct.getId().equals(tct2.getId()) || checkRecursiveness(tct,tct2,context2))){\r\n\t\t\t\tSystem.out.println(\"FOUND IN ALL\");\r\n\t\t\t\treturn Boolean.TRUE;\r\n\t\t\t}\r\n\t\t}\r\n\t\tfor (TermCompositorType tct: tct1.getExactlyOne()){\r\n\t\t\tSystem.out.println(\"Checking candidate: \" + tct.getId());\r\n\t\t\tEList<TermCompositorType> context2 = null;\r\n\t\t\tif (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>();\r\n\t\t\telse context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>(context); \r\n\t\t\tcontext2.add(tct);\r\n\t\t\tif ((context == null || (context != null && !context.contains(tct))) && (tct.getId().equals(tct2.getId()) || checkRecursiveness(tct,tct2,context2))){\r\n\t\t\t\tSystem.out.println(\"FOUND IN ALL\");\r\n\t\t\t\treturn Boolean.TRUE;\r\n\t\t\t}\r\n\t\t}\r\n\t\tSystem.out.println(\"Did not find any recursive matching for TermCompositorType: \" + tct1.getId());\r\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkRecursiveness(TermCompositorType tct1, TermCompositorType tct2, EList<TermCompositorType> context);

} // TermCompositorType
