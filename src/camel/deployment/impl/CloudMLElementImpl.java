/**
 */
package camel.deployment.impl;

import camel.deployment.CloudMLElement;
import camel.deployment.ComputationalResource;
import camel.deployment.DeploymentPackage;

import camel.provider.Attribute;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud ML Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.CloudMLElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.deployment.impl.CloudMLElementImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link camel.deployment.impl.CloudMLElementImpl#getResources <em>Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CloudMLElementImpl extends CDOObjectImpl implements CloudMLElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CloudMLElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.CLOUD_ML_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(DeploymentPackage.Literals.CLOUD_ML_ELEMENT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(DeploymentPackage.Literals.CLOUD_ML_ELEMENT__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Attribute> getProperties() {
		return (EList<Attribute>)eGet(DeploymentPackage.Literals.CLOUD_ML_ELEMENT__PROPERTIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ComputationalResource> getResources() {
		return (EList<ComputationalResource>)eGet(DeploymentPackage.Literals.CLOUD_ML_ELEMENT__RESOURCES, true);
	}

} //CloudMLElementImpl
