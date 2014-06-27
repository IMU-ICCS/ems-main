/**
 */
package camel.security;

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
 *   <li>{@link camel.security.SecurityModel#getSecControls <em>Sec Controls</em>}</li>
 *   <li>{@link camel.security.SecurityModel#getSecReq <em>Sec Req</em>}</li>
 *   <li>{@link camel.security.SecurityModel#getSecProps <em>Sec Props</em>}</li>
 *   <li>{@link camel.security.SecurityModel#getSecMetric <em>Sec Metric</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.security.SecurityPackage#getSecurityModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface SecurityModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Sec Controls</b></em>' containment reference list.
	 * The list contents are of type {@link camel.security.SecurityControl}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sec Controls</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sec Controls</em>' containment reference list.
	 * @see camel.security.SecurityPackage#getSecurityModel_SecControls()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<SecurityControl> getSecControls();

	/**
	 * Returns the value of the '<em><b>Sec Req</b></em>' containment reference list.
	 * The list contents are of type {@link camel.security.SecurityRequirement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sec Req</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sec Req</em>' containment reference list.
	 * @see camel.security.SecurityPackage#getSecurityModel_SecReq()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<SecurityRequirement> getSecReq();

	/**
	 * Returns the value of the '<em><b>Sec Props</b></em>' containment reference list.
	 * The list contents are of type {@link camel.security.SecurityProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sec Props</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sec Props</em>' containment reference list.
	 * @see camel.security.SecurityPackage#getSecurityModel_SecProps()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<SecurityProperty> getSecProps();

	/**
	 * Returns the value of the '<em><b>Sec Metric</b></em>' containment reference list.
	 * The list contents are of type {@link camel.security.SecurityMetric}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sec Metric</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sec Metric</em>' containment reference list.
	 * @see camel.security.SecurityPackage#getSecurityModel_SecMetric()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<SecurityMetric> getSecMetric();

} // SecurityModel
