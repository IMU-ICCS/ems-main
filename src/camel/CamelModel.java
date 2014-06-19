/**
 */
package camel;

import camel.cerif.CerifModel;

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
 *   <li>{@link camel.CamelModel#getCerifModels <em>Cerif Models</em>}</li>
 *   <li>{@link camel.CamelModel#getUnits <em>Units</em>}</li>
 *   <li>{@link camel.CamelModel#getActions <em>Actions</em>}</li>
 *   <li>{@link camel.CamelModel#getNodes <em>Nodes</em>}</li>
 *   <li>{@link camel.CamelModel#getObjects <em>Objects</em>}</li>
 *   <li>{@link camel.CamelModel#getRequirements <em>Requirements</em>}</li>
 *   <li>{@link camel.CamelModel#getPolicies <em>Policies</em>}</li>
 *   <li>{@link camel.CamelModel#getVmInfos <em>Vm Infos</em>}</li>
 *   <li>{@link camel.CamelModel#getIndependentVmInfos <em>Independent Vm Infos</em>}</li>
 *   <li>{@link camel.CamelModel#getVmType <em>Vm Type</em>}</li>
 *   <li>{@link camel.CamelModel#getApplications <em>Applications</em>}</li>
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
	 * Returns the value of the '<em><b>Cerif Models</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.CerifModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cerif Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cerif Models</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_CerifModels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<CerifModel> getCerifModels();

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
	 * The list contents are of type {@link camel.ScalabilityPolicy}.
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
	 * Returns the value of the '<em><b>Independent Vm Infos</b></em>' containment reference list.
	 * The list contents are of type {@link camel.CloudIndependentVMInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Independent Vm Infos</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Independent Vm Infos</em>' containment reference list.
	 * @see camel.CamelPackage#getCamelModel_IndependentVmInfos()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<CloudIndependentVMInfo> getIndependentVmInfos();

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

} // CamelModel
