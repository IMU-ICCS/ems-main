/**
 */
package eu.paasage.upperware.metamodel.types.util;

import eu.paasage.upperware.metamodel.types.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.TypesPackage
 * @generated
 */
public class TypesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesSwitch() {
		if (modelPackage == null) {
			modelPackage = TypesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TypesPackage.VALUE_UPPERWARE: {
				ValueUpperware valueUpperware = (ValueUpperware)theEObject;
				T result = caseValueUpperware(valueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NUMERIC_VALUE_UPPERWARE: {
				NumericValueUpperware numericValueUpperware = (NumericValueUpperware)theEObject;
				T result = caseNumericValueUpperware(numericValueUpperware);
				if (result == null) result = caseValueUpperware(numericValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INTEGER_VALUE_UPPERWARE: {
				IntegerValueUpperware integerValueUpperware = (IntegerValueUpperware)theEObject;
				T result = caseIntegerValueUpperware(integerValueUpperware);
				if (result == null) result = caseNumericValueUpperware(integerValueUpperware);
				if (result == null) result = caseValueUpperware(integerValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.LONG_VALUE_UPPERWARE: {
				LongValueUpperware longValueUpperware = (LongValueUpperware)theEObject;
				T result = caseLongValueUpperware(longValueUpperware);
				if (result == null) result = caseNumericValueUpperware(longValueUpperware);
				if (result == null) result = caseValueUpperware(longValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FLOAT_VALUE_UPPERWARE: {
				FloatValueUpperware floatValueUpperware = (FloatValueUpperware)theEObject;
				T result = caseFloatValueUpperware(floatValueUpperware);
				if (result == null) result = caseNumericValueUpperware(floatValueUpperware);
				if (result == null) result = caseValueUpperware(floatValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DOUBLE_VALUE_UPPERWARE: {
				DoubleValueUpperware doubleValueUpperware = (DoubleValueUpperware)theEObject;
				T result = caseDoubleValueUpperware(doubleValueUpperware);
				if (result == null) result = caseNumericValueUpperware(doubleValueUpperware);
				if (result == null) result = caseValueUpperware(doubleValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING_VALUE_UPPERWARE: {
				StringValueUpperware stringValueUpperware = (StringValueUpperware)theEObject;
				T result = caseStringValueUpperware(stringValueUpperware);
				if (result == null) result = caseValueUpperware(stringValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BOOLEAN_VALUE_UPPERWARE: {
				BooleanValueUpperware booleanValueUpperware = (BooleanValueUpperware)theEObject;
				T result = caseBooleanValueUpperware(booleanValueUpperware);
				if (result == null) result = caseValueUpperware(booleanValueUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueUpperware(ValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericValueUpperware(NumericValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerValueUpperware(IntegerValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Long Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Long Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLongValueUpperware(LongValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFloatValueUpperware(FloatValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleValueUpperware(DoubleValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringValueUpperware(StringValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Value Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanValueUpperware(BooleanValueUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TypesSwitch
