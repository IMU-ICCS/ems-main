/**
 */
package camel.sla.impl;

import camel.scalability.MetricCondition;

import camel.sla.AgreementContextType;
import camel.sla.AgreementType;
import camel.sla.SlaPackage;
import camel.sla.TermTreeType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agreement Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.AgreementTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementTypeImpl#getAgreementId <em>Agreement Id</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementTypeImpl#getContext <em>Context</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementTypeImpl#getTerms <em>Terms</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementTypeImpl#getSlos <em>Slos</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AgreementTypeImpl extends CDOObjectImpl implements AgreementType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgreementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.AGREEMENT_TYPE;
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
		return (String)eGet(SlaPackage.Literals.AGREEMENT_TYPE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(SlaPackage.Literals.AGREEMENT_TYPE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAgreementId() {
		return (String)eGet(SlaPackage.Literals.AGREEMENT_TYPE__AGREEMENT_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgreementId(String newAgreementId) {
		eSet(SlaPackage.Literals.AGREEMENT_TYPE__AGREEMENT_ID, newAgreementId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgreementContextType getContext() {
		return (AgreementContextType)eGet(SlaPackage.Literals.AGREEMENT_TYPE__CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(AgreementContextType newContext) {
		eSet(SlaPackage.Literals.AGREEMENT_TYPE__CONTEXT, newContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermTreeType getTerms() {
		return (TermTreeType)eGet(SlaPackage.Literals.AGREEMENT_TYPE__TERMS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerms(TermTreeType newTerms) {
		eSet(SlaPackage.Literals.AGREEMENT_TYPE__TERMS, newTerms);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricCondition> getSlos() {
		return (EList<MetricCondition>)eGet(SlaPackage.Literals.AGREEMENT_TYPE__SLOS, true);
	}

} //AgreementTypeImpl
