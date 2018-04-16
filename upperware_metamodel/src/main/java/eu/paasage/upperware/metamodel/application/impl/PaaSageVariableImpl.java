/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getPaasageType <em>Paasage Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getRelatedComponent <em>Related Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getCpVariableId <em>Cp Variable Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getRelatedProvider <em>Related Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PaaSageVariableImpl extends CDOObjectImpl implements PaaSageVariable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaaSageVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PAA_SAGE_VARIABLE;
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
	public VariableElementTypeEnum getPaasageType() {
		return (VariableElementTypeEnum)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__PAASAGE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPaasageType(VariableElementTypeEnum newPaasageType) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__PAASAGE_TYPE, newPaasageType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponent getRelatedComponent() {
		return (ApplicationComponent)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedComponent(ApplicationComponent newRelatedComponent) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_COMPONENT, newRelatedComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCpVariableId() {
		return (String)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__CP_VARIABLE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpVariableId(String newCpVariableId) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__CP_VARIABLE_ID, newCpVariableId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Provider getRelatedProvider() {
		return (Provider)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedProvider(Provider newRelatedProvider) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_PROVIDER, newRelatedProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualMachineProfile getRelatedVirtualMachineProfile() {
		return (VirtualMachineProfile)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedVirtualMachineProfile(VirtualMachineProfile newRelatedVirtualMachineProfile) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE, newRelatedVirtualMachineProfile);
	}

} //PaaSageVariableImpl
