/**
 */
package camel.impl;

import camel.Action;
import camel.Application;
import camel.CamelModel;
import camel.CamelPackage;
import camel.DataObject;
import camel.PhysicalNode;
import camel.Requirement;
import camel.Unit;
import camel.VMInfo;
import camel.VMType;

import camel.deployment.DeploymentModel;

import camel.execution.ExecutionModel;

import camel.organisation.OrganisationModel;

import camel.provider.ProviderModel;

import camel.scalability.ScalabilityModel;
import camel.scalability.ScalabilityPolicy;

import camel.security.SecurityModel;

import camel.sla.AgreementType;

import camel.type.TypeRepository;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.CamelModelImpl#getOrganisationModels <em>Organisation Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getScalabilityModels <em>Scalability Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getProviderModels <em>Provider Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getSecurityModels <em>Security Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getDeploymentModels <em>Deployment Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getProvenanceModels <em>Provenance Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getTypeRepositories <em>Type Repositories</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getAgreementModels <em>Agreement Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getUnits <em>Units</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getObjects <em>Objects</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getRequirements <em>Requirements</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getPolicies <em>Policies</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getApplications <em>Applications</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getVmInfos <em>Vm Infos</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getVmType <em>Vm Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CamelModelImpl extends CDOObjectImpl implements CamelModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CamelModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.CAMEL_MODEL;
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
	@SuppressWarnings("unchecked")
	public EList<OrganisationModel> getOrganisationModels() {
		return (EList<OrganisationModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__ORGANISATION_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ScalabilityModel> getScalabilityModels() {
		return (EList<ScalabilityModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__SCALABILITY_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProviderModel> getProviderModels() {
		return (EList<ProviderModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__PROVIDER_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SecurityModel> getSecurityModels() {
		return (EList<SecurityModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__SECURITY_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<DeploymentModel> getDeploymentModels() {
		return (EList<DeploymentModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__DEPLOYMENT_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ExecutionModel> getProvenanceModels() {
		return (EList<ExecutionModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__PROVENANCE_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<TypeRepository> getTypeRepositories() {
		return (EList<TypeRepository>)eGet(CamelPackage.Literals.CAMEL_MODEL__TYPE_REPOSITORIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<AgreementType> getAgreementModels() {
		return (EList<AgreementType>)eGet(CamelPackage.Literals.CAMEL_MODEL__AGREEMENT_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Unit> getUnits() {
		return (EList<Unit>)eGet(CamelPackage.Literals.CAMEL_MODEL__UNITS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Action> getActions() {
		return (EList<Action>)eGet(CamelPackage.Literals.CAMEL_MODEL__ACTIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<PhysicalNode> getNodes() {
		return (EList<PhysicalNode>)eGet(CamelPackage.Literals.CAMEL_MODEL__NODES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<DataObject> getObjects() {
		return (EList<DataObject>)eGet(CamelPackage.Literals.CAMEL_MODEL__OBJECTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Requirement> getRequirements() {
		return (EList<Requirement>)eGet(CamelPackage.Literals.CAMEL_MODEL__REQUIREMENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ScalabilityPolicy> getPolicies() {
		return (EList<ScalabilityPolicy>)eGet(CamelPackage.Literals.CAMEL_MODEL__POLICIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Application> getApplications() {
		return (EList<Application>)eGet(CamelPackage.Literals.CAMEL_MODEL__APPLICATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<VMInfo> getVmInfos() {
		return (EList<VMInfo>)eGet(CamelPackage.Literals.CAMEL_MODEL__VM_INFOS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<VMType> getVmType() {
		return (EList<VMType>)eGet(CamelPackage.Literals.CAMEL_MODEL__VM_TYPE, true);
	}

} //CamelModelImpl
