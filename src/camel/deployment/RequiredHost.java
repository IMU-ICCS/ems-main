/**
 */
package camel.deployment;

import camel.provider.Attribute;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Host</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.RequiredHost#getDemands <em>Demands</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getRequiredHost()
 * @model
 * @generated
 */
public interface RequiredHost extends HostingPort {
	/**
	 * Returns the value of the '<em><b>Demands</b></em>' reference list.
	 * The list contents are of type {@link camel.provider.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Demands</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Demands</em>' reference list.
	 * @see camel.deployment.DeploymentPackage#getRequiredHost_Demands()
	 * @model
	 * @generated
	 */
	EList<Attribute> getDemands();

} // RequiredHost
