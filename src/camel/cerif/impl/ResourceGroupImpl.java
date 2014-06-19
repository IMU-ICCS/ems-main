/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.Resource;
import camel.cerif.ResourceGroup;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.ResourceGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.impl.ResourceGroupImpl#getContainsResource <em>Contains Resource</em>}</li>
 *   <li>{@link camel.cerif.impl.ResourceGroupImpl#getLevel <em>Level</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceGroupImpl extends ResourceImpl implements ResourceGroup {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.RESOURCE_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(CerifPackage.Literals.RESOURCE_GROUP__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CerifPackage.Literals.RESOURCE_GROUP__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Resource> getContainsResource() {
		return (EList<Resource>)eGet(CerifPackage.Literals.RESOURCE_GROUP__CONTAINS_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLevel() {
		return (Integer)eGet(CerifPackage.Literals.RESOURCE_GROUP__LEVEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLevel(int newLevel) {
		eSet(CerifPackage.Literals.RESOURCE_GROUP__LEVEL, newLevel);
	}

} //ResourceGroupImpl
