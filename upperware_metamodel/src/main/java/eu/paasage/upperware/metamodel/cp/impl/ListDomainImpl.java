/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.ListDomain;

import eu.paasage.upperware.metamodel.types.StringValueUpperware;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>List Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl#getValues <em>Values</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListDomainImpl extends DomainImpl implements ListDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.LIST_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<StringValueUpperware> getValues() {
		return (EList<StringValueUpperware>)eGet(CpPackage.Literals.LIST_DOMAIN__VALUES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValueUpperware getValue() {
		return (StringValueUpperware)eGet(CpPackage.Literals.LIST_DOMAIN__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(StringValueUpperware newValue) {
		eSet(CpPackage.Literals.LIST_DOMAIN__VALUE, newValue);
	}

} //ListDomainImpl
