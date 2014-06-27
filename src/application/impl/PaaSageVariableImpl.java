/**
 */
package application.impl;

import application.ApplicationPackage;
import application.CloudML_Element;
import application.PaaSageVariable;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.VariableElementTypeEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.PaaSageVariableImpl#getPaasageType <em>Paasage Type</em>}</li>
 *   <li>{@link application.impl.PaaSageVariableImpl#getRelatedElements <em>Related Elements</em>}</li>
 *   <li>{@link application.impl.PaaSageVariableImpl#getCpVariableId <em>Cp Variable Id</em>}</li>
 * </ul>
 * </p>
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
	@SuppressWarnings("unchecked")
	public EList<CloudML_Element> getRelatedElements() {
		return (EList<CloudML_Element>)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_ELEMENTS, true);
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

} //PaaSageVariableImpl
