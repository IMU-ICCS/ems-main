/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.CloudMLElementUpperware;
import eu.paasage.upperware.metamodel.application.RequiredFeature;

import eu.paasage.upperware.metamodel.types.typesPaasage.CommunicationTypeUpperware;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Required Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#getProvidedBy <em>Provided By</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#getCommunicationType <em>Communication Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#isOptional <em>Optional</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#isContaiment <em>Contaiment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredFeatureImpl extends CDOObjectImpl implements RequiredFeature {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequiredFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.REQUIRED_FEATURE;
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
	public String getFeature() {
		return (String)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__FEATURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(String newFeature) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__FEATURE, newFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudMLElementUpperware getProvidedBy() {
		return (CloudMLElementUpperware)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__PROVIDED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedBy(CloudMLElementUpperware newProvidedBy) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__PROVIDED_BY, newProvidedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunicationTypeUpperware getCommunicationType() {
		return (CommunicationTypeUpperware)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__COMMUNICATION_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommunicationType(CommunicationTypeUpperware newCommunicationType) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__COMMUNICATION_TYPE, newCommunicationType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOptional() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__OPTIONAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOptional(boolean newOptional) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__OPTIONAL, newOptional);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isContaiment() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__CONTAIMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContaiment(boolean newContaiment) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__CONTAIMENT, newContaiment);
	}

} //RequiredFeatureImpl
