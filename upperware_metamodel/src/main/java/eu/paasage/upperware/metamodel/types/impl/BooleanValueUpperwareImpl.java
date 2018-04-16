/**
 */
package eu.paasage.upperware.metamodel.types.impl;

import eu.paasage.upperware.metamodel.types.BooleanValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Boolean Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.impl.BooleanValueUpperwareImpl#isValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BooleanValueUpperwareImpl extends ValueUpperwareImpl implements BooleanValueUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanValueUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.BOOLEAN_VALUE_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValue() {
		return (Boolean)eGet(TypesPackage.Literals.BOOLEAN_VALUE_UPPERWARE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(boolean newValue) {
		eSet(TypesPackage.Literals.BOOLEAN_VALUE_UPPERWARE__VALUE, newValue);
	}

} //BooleanValueUpperwareImpl
