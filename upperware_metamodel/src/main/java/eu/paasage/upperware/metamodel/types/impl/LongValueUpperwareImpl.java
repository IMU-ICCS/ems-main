/**
 */
package eu.paasage.upperware.metamodel.types.impl;

import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Long Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.impl.LongValueUpperwareImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LongValueUpperwareImpl extends NumericValueUpperwareImpl implements LongValueUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LongValueUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.LONG_VALUE_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getValue() {
		return (Long)eGet(TypesPackage.Literals.LONG_VALUE_UPPERWARE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(long newValue) {
		eSet(TypesPackage.Literals.LONG_VALUE_UPPERWARE__VALUE, newValue);
	}

} //LongValueUpperwareImpl
