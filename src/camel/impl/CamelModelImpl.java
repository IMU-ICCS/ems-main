/**
 */
package camel.impl;

import camel.Action;
import camel.Application;
import camel.CamelModel;
import camel.CamelPackage;
import camel.CloudIndependentVMInfo;
import camel.DataObject;
import camel.PhysicalNode;
import camel.Requirement;
import camel.ScalabilityPolicy;
import camel.Unit;
import camel.VMInfo;
import camel.VMType;

import camel.cerif.CerifModel;

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
 *   <li>{@link camel.impl.CamelModelImpl#getCerifModels <em>Cerif Models</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getUnits <em>Units</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getObjects <em>Objects</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getRequirements <em>Requirements</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getPolicies <em>Policies</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getVmInfos <em>Vm Infos</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getIndependentVmInfos <em>Independent Vm Infos</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getVmType <em>Vm Type</em>}</li>
 *   <li>{@link camel.impl.CamelModelImpl#getApplications <em>Applications</em>}</li>
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
	public EList<CerifModel> getCerifModels() {
		return (EList<CerifModel>)eGet(CamelPackage.Literals.CAMEL_MODEL__CERIF_MODELS, true);
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
	public EList<VMInfo> getVmInfos() {
		return (EList<VMInfo>)eGet(CamelPackage.Literals.CAMEL_MODEL__VM_INFOS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CloudIndependentVMInfo> getIndependentVmInfos() {
		return (EList<CloudIndependentVMInfo>)eGet(CamelPackage.Literals.CAMEL_MODEL__INDEPENDENT_VM_INFOS, true);
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Application> getApplications() {
		return (EList<Application>)eGet(CamelPackage.Literals.CAMEL_MODEL__APPLICATIONS, true);
	}

} //CamelModelImpl
