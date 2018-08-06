/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.Domain;
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
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.CpVariableImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.CpVariableImpl#getVariableType <em>Variable Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.CpVariableImpl#getComponentId <em>Component Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CpVariableImpl extends NumericExpressionImpl implements CpVariable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CpVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.CP_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain getDomain() {
		return (Domain)eGet(CpPackage.Literals.CP_VARIABLE__DOMAIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(Domain newDomain) {
		eSet(CpPackage.Literals.CP_VARIABLE__DOMAIN, newDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableType getVariableType() {
		return (VariableType)eGet(CpPackage.Literals.CP_VARIABLE__VARIABLE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariableType(VariableType newVariableType) {
		eSet(CpPackage.Literals.CP_VARIABLE__VARIABLE_TYPE, newVariableType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentId() {
		return (String)eGet(CpPackage.Literals.CP_VARIABLE__COMPONENT_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentId(String newComponentId) {
		eSet(CpPackage.Literals.CP_VARIABLE__COMPONENT_ID, newComponentId);
	}

} //CpVariableImpl
