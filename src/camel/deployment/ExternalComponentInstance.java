/**
 */
package camel.deployment;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Component Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.ExternalComponentInstance#getIps <em>Ips</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getExternalComponentInstance()
 * @model
 * @generated
 */
public interface ExternalComponentInstance extends ComponentInstance {
	/**
	 * Returns the value of the '<em><b>Ips</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ips</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ips</em>' attribute list.
	 * @see camel.deployment.DeploymentPackage#getExternalComponentInstance_Ips()
	 * @model
	 * @generated
	 */
	EList<String> getIps();

} // ExternalComponentInstance
