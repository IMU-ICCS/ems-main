/**
 */
package eu.paasage.upperware.metamodel.types;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.TypesPackage
 * @generated
 */
public interface TypesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesFactory eINSTANCE = eu.paasage.upperware.metamodel.types.impl.TypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Integer Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Value Upperware</em>'.
	 * @generated
	 */
	IntegerValueUpperware createIntegerValueUpperware();

	/**
	 * Returns a new object of class '<em>Long Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Long Value Upperware</em>'.
	 * @generated
	 */
	LongValueUpperware createLongValueUpperware();

	/**
	 * Returns a new object of class '<em>Float Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float Value Upperware</em>'.
	 * @generated
	 */
	FloatValueUpperware createFloatValueUpperware();

	/**
	 * Returns a new object of class '<em>Double Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Double Value Upperware</em>'.
	 * @generated
	 */
	DoubleValueUpperware createDoubleValueUpperware();

	/**
	 * Returns a new object of class '<em>String Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Value Upperware</em>'.
	 * @generated
	 */
	StringValueUpperware createStringValueUpperware();

	/**
	 * Returns a new object of class '<em>Boolean Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Value Upperware</em>'.
	 * @generated
	 */
	BooleanValueUpperware createBooleanValueUpperware();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TypesPackage getTypesPackage();

} //TypesFactory
