/**
 */
package camel.deployment;

import camel.provider.Attribute;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Provided Host</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.ProvidedHost#getOffers <em>Offers</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getProvidedHost()
 * @model
 * @generated
 */
public interface ProvidedHost extends HostingPort {
	/**
	 * Returns the value of the '<em><b>Offers</b></em>' reference list.
	 * The list contents are of type {@link camel.provider.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offers</em>' reference list.
	 * @see camel.deployment.DeploymentPackage#getProvidedHost_Offers()
	 * @model
	 * @generated
	 */
	EList<Attribute> getOffers();

} // ProvidedHost
