/**
 */
package camel.impl;

import camel.CamelPackage;
import camel.VMType;

import camel.organisation.impl.ResourceImpl;

import camel.provider.Constraint;
import camel.provider.Feature;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VM Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.VMTypeImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link camel.impl.VMTypeImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link camel.impl.VMTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VMTypeImpl extends ResourceImpl implements VMType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VMTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.VM_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getFeature() {
		return (Feature)eGet(CamelPackage.Literals.VM_TYPE__FEATURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(Feature newFeature) {
		eSet(CamelPackage.Literals.VM_TYPE__FEATURE, newFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Constraint> getConstraints() {
		return (EList<Constraint>)eGet(CamelPackage.Literals.VM_TYPE__CONSTRAINTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(CamelPackage.Literals.VM_TYPE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CamelPackage.Literals.VM_TYPE__NAME, newName);
	}

} //VMTypeImpl
