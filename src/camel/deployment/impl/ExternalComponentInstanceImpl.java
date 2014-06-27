/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.ExternalComponentInstance;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Component Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.ExternalComponentInstanceImpl#getIps <em>Ips</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExternalComponentInstanceImpl extends ComponentInstanceImpl implements ExternalComponentInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExternalComponentInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.EXTERNAL_COMPONENT_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<String> getIps() {
		return (EList<String>)eGet(DeploymentPackage.Literals.EXTERNAL_COMPONENT_INSTANCE__IPS, true);
	}

} //ExternalComponentInstanceImpl
