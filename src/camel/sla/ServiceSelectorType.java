/**
 */
package camel.sla;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Selector Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.ServiceSelectorType#getServiceName <em>Service Name</em>}</li>
 *   <li>{@link camel.sla.ServiceSelectorType#getServiceReference <em>Service Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getServiceSelectorType()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correct_service_reference_and_name_SSType'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correct_service_reference_and_name_SSType='\n\t\t\t\t\t/* concrete service only at VM level, PaaS needs to be covered also \052/\n\t\t\t\t\tif (serviceReference.oclIsTypeOf(camel::deployment::Component)) then serviceName = serviceReference.oclAsType(camel::deployment::Component).name \n\t\t\t\t\telse \n\t\t\t\t\t\tif (serviceReference.oclIsTypeOf(camel::VMType)) then serviceName = serviceReference.oclAsType(camel::VMType).name\n\t\t\t\t\t\telse false endif\n\t\t\t\t\tendif'"
 * @extends CDOObject
 * @generated
 */
public interface ServiceSelectorType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Service Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Name</em>' attribute.
	 * @see #setServiceName(String)
	 * @see camel.sla.SlaPackage#getServiceSelectorType_ServiceName()
	 * @model required="true"
	 * @generated
	 */
	String getServiceName();

	/**
	 * Sets the value of the '{@link camel.sla.ServiceSelectorType#getServiceName <em>Service Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Name</em>' attribute.
	 * @see #getServiceName()
	 * @generated
	 */
	void setServiceName(String value);

	/**
	 * Returns the value of the '<em><b>Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Reference</em>' reference.
	 * @see #setServiceReference(EObject)
	 * @see camel.sla.SlaPackage#getServiceSelectorType_ServiceReference()
	 * @model required="true"
	 * @generated
	 */
	EObject getServiceReference();

	/**
	 * Sets the value of the '{@link camel.sla.ServiceSelectorType#getServiceReference <em>Service Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Reference</em>' reference.
	 * @see #getServiceReference()
	 * @generated
	 */
	void setServiceReference(EObject value);

} // ServiceSelectorType
