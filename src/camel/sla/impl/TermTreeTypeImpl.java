/**
 */
package camel.sla.impl;

import camel.sla.SlaPackage;
import camel.sla.TermCompositorType;
import camel.sla.TermTreeType;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Term Tree Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.TermTreeTypeImpl#getAll <em>All</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TermTreeTypeImpl extends CDOObjectImpl implements TermTreeType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TermTreeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.TERM_TREE_TYPE;
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
	public TermCompositorType getAll() {
		return (TermCompositorType)eGet(SlaPackage.Literals.TERM_TREE_TYPE__ALL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAll(TermCompositorType newAll) {
		eSet(SlaPackage.Literals.TERM_TREE_TYPE__ALL, newAll);
	}

} //TermTreeTypeImpl
