/**
 */
package camel.deployment.impl;

import camel.deployment.Component;
import camel.deployment.DeploymentPackage;
import camel.deployment.ProvidedCommunication;
import camel.deployment.ProvidedHost;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.ComponentImpl#getProvidedCommunications <em>Provided Communications</em>}</li>
 *   <li>{@link camel.deployment.impl.ComponentImpl#getProvidedHosts <em>Provided Hosts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ComponentImpl extends CloudMLElementImpl implements Component {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProvidedCommunication> getProvidedCommunications() {
		return (EList<ProvidedCommunication>)eGet(DeploymentPackage.Literals.COMPONENT__PROVIDED_COMMUNICATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProvidedHost> getProvidedHosts() {
		return (EList<ProvidedHost>)eGet(DeploymentPackage.Literals.COMPONENT__PROVIDED_HOSTS, true);
	}

} //ComponentImpl
