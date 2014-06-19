/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.ExternalIdentifier;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Identifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.ExternalIdentifierImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link camel.cerif.impl.ExternalIdentifierImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.impl.ExternalIdentifierImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExternalIdentifierImpl extends CDOObjectImpl implements ExternalIdentifier {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExternalIdentifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.EXTERNAL_IDENTIFIER;
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
	public String getIdentifier() {
		return (String)eGet(CerifPackage.Literals.EXTERNAL_IDENTIFIER__IDENTIFIER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdentifier(String newIdentifier) {
		eSet(CerifPackage.Literals.EXTERNAL_IDENTIFIER__IDENTIFIER, newIdentifier);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(CerifPackage.Literals.EXTERNAL_IDENTIFIER__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CerifPackage.Literals.EXTERNAL_IDENTIFIER__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eGet(CerifPackage.Literals.EXTERNAL_IDENTIFIER__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eSet(CerifPackage.Literals.EXTERNAL_IDENTIFIER__DESCRIPTION, newDescription);
	}

} //ExternalIdentifierImpl
