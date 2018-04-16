/**
 */
package eu.paasage.upperware.metamodel.types.impl;

import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Integer Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.impl.IntegerValueUpperwareImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntegerValueUpperwareImpl extends NumericValueUpperwareImpl implements IntegerValueUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IntegerValueUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.INTEGER_VALUE_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return (Integer)eGet(TypesPackage.Literals.INTEGER_VALUE_UPPERWARE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(int newValue) {
		eSet(TypesPackage.Literals.INTEGER_VALUE_UPPERWARE__VALUE, newValue);
	}

} //IntegerValueUpperwareImpl
