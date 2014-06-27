/**
 */
package camel;

import camel.deployment.DeploymentModel;

import camel.execution.ExecutionModel;

import camel.organisation.OrganisationModel;

import camel.provider.ProviderModel;

import camel.scalability.ScalabilityModel;
import camel.scalability.ScalabilityPolicy;

import camel.security.SecurityModel;

import camel.sla.AgreementType;

import camel.type.TypeRepository;

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
 *   <li>{@link camel.CamelModel#getOrganisationModels <em>Organisation Models</em>}</li>
 *   <li>{@link camel.CamelModel#getScalabilityModels <em>Scalability Models</em>}</li>
 *   <li>{@link camel.CamelModel#getProviderModels <em>Provider Models</em>}</li>
 *   <li>{@link camel.CamelModel#getSecurityModels <em>Security Models</em>}</li>
 *   <li>{@link camel.CamelModel#getDeploymentModels <em>Deployment Models</em>}</li>
 *   <li>{@link camel.CamelModel#getProvenanceModels <em>Provenance Models</em>}</li>
 *   <li>{@link camel.CamelModel#getTypeRepositories <em>Type Repositories</em>}</li>
 *   <li>{@link camel.CamelModel#getAgreementModels <em>Agreement Models</em>}</li>
 *   <li>{@link camel.CamelModel#getUnits <em>Units</em>}</li>
 *   <li>{@link camel.CamelModel#getActions <em>Actions</em>}</li>
 *   <li>{@link camel.CamelModel#getNodes <em>Nodes</em>}</li>
 *   <li>{@link camel.CamelModel#getObjects <em>Objects</em>}</li>
 *   <li>{@link camel.CamelModel#getRequirements <em>Requirements</em>}</li>
 *   <li>{@link camel.CamelModel#getPolicies <em>Policies</em>}</li>
 *   <li>{@link camel.CamelModel#getApplications <em>Applications</em>}</li>
 *   <li>{@link camel.CamelModel#getVmInfos <em>Vm Infos</em>}</li>
 *   <li>{@link camel.CamelModel#getVmType <em>Vm Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getCamelModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CamelModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Organisation Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.OrganisationModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organisation Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organisation Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_OrganisationModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<OrganisationModel> getOrganisationModels();

	/**
	 * Returns the value of the '<em><b>Scalability Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.ScalabilityModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scalability Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scalability Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_ScalabilityModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ScalabilityModel> getScalabilityModels();

	/**
	 * Returns the value of the '<em><b>Provider Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.provider.ProviderModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_ProviderModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ProviderModel> getProviderModels();

	/**
	 * Returns the value of the '<em><b>Security Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.security.SecurityModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_SecurityModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<SecurityModel> getSecurityModels();

	/**
	 * Returns the value of the '<em><b>Deployment Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.DeploymentModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_DeploymentModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<DeploymentModel> getDeploymentModels();

	/**
	 * Returns the value of the '<em><b>Provenance Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.execution.ExecutionModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provenance Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provenance Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_ProvenanceModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ExecutionModel> getProvenanceModels();

	/**
	 * Returns the value of the '<em><b>Type Repositories</b></em>' containment reference list.
	 * The list contents are of type {@link camel.type.TypeRepository}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Repositories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Repositories</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_TypeRepositories()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<TypeRepository> getTypeRepositories();

	/**
	 * Returns the value of the '<em><b>Agreement Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.AgreementType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agreement Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agreement Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_AgreementModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<AgreementType> getAgreementModels();

	/**
	 * Returns the value of the '<em><b>Units</b></em>' containment reference list.
	 * The list contents are of type {@link camel.Unit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Units</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Units</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Units()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Unit> getUnits();

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Actions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Action> getActions();

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link camel.PhysicalNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Nodes()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<PhysicalNode> getNodes();

	/**
	 * Returns the value of the '<em><b>Objects</b></em>' containment reference list.
	 * The list contents are of type {@link camel.DataObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objects</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Objects()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<DataObject> getObjects();

	/**
	 * Returns the value of the '<em><b>Requirements</b></em>' containment reference list.
	 * The list contents are of type {@link camel.Requirement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requirements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirements</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Requirements()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Requirement> getRequirements();

	/**
	 * Returns the value of the '<em><b>Policies</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.ScalabilityPolicy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policies</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Policies()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ScalabilityPolicy> getPolicies();

	/**
	 * Returns the value of the '<em><b>Applications</b></em>' containment reference list.
	 * The list contents are of type {@link camel.Application}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Applications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Applications</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_Applications()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Application> getApplications();

	/**
	 * Returns the value of the '<em><b>Vm Infos</b></em>' containment reference list.
	 * The list contents are of type {@link camel.VMInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Infos</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Infos</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_VmInfos()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<VMInfo> getVmInfos();

	/**
	 * Returns the value of the '<em><b>Vm Type</b></em>' containment reference list.
	 * The list contents are of type {@link camel.VMType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Type</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Type</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_VmType()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<VMType> getVmType();

} // CamelModel
