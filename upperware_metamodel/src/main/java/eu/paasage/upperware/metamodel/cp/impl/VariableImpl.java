/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.VariableType;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getLocationId <em>Location Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getProviderId <em>Provider Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getVmId <em>Vm Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getOsImageId <em>Os Image Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getHardwareId <em>Hardware Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getVariableType <em>Variable Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getComponentId <em>Component Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableImpl extends NumericExpressionImpl implements Variable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain getDomain() {
		return (Domain)eGet(CpPackage.Literals.VARIABLE__DOMAIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(Domain newDomain) {
		eSet(CpPackage.Literals.VARIABLE__DOMAIN, newDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocationId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__LOCATION_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocationId(String newLocationId) {
		eSet(CpPackage.Literals.VARIABLE__LOCATION_ID, newLocationId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProviderId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__PROVIDER_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProviderId(String newProviderId) {
		eSet(CpPackage.Literals.VARIABLE__PROVIDER_ID, newProviderId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVmId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__VM_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVmId(String newVmId) {
		eSet(CpPackage.Literals.VARIABLE__VM_ID, newVmId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOsImageId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__OS_IMAGE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOsImageId(String newOsImageId) {
		eSet(CpPackage.Literals.VARIABLE__OS_IMAGE_ID, newOsImageId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHardwareId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__HARDWARE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHardwareId(String newHardwareId) {
		eSet(CpPackage.Literals.VARIABLE__HARDWARE_ID, newHardwareId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableType getVariableType() {
		return (VariableType)eGet(CpPackage.Literals.VARIABLE__VARIABLE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariableType(VariableType newVariableType) {
		eSet(CpPackage.Literals.VARIABLE__VARIABLE_TYPE, newVariableType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__COMPONENT_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentId(String newComponentId) {
		eSet(CpPackage.Literals.VARIABLE__COMPONENT_ID, newComponentId);
	}

} //VariableImpl
