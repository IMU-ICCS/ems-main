/**
 */
package eu.paasage.upperware.metamodel.types.impl;

import eu.paasage.upperware.metamodel.types.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesFactoryImpl extends EFactoryImpl implements TypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypesFactory init() {
		try {
			TypesFactory theTypesFactory = (TypesFactory)EPackage.Registry.INSTANCE.getEFactory(TypesPackage.eNS_URI);
			if (theTypesFactory != null) {
				return theTypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TypesPackage.INTEGER_VALUE_UPPERWARE: return (EObject)createIntegerValueUpperware();
			case TypesPackage.LONG_VALUE_UPPERWARE: return (EObject)createLongValueUpperware();
			case TypesPackage.FLOAT_VALUE_UPPERWARE: return (EObject)createFloatValueUpperware();
			case TypesPackage.DOUBLE_VALUE_UPPERWARE: return (EObject)createDoubleValueUpperware();
			case TypesPackage.STRING_VALUE_UPPERWARE: return (EObject)createStringValueUpperware();
			case TypesPackage.BOOLEAN_VALUE_UPPERWARE: return (EObject)createBooleanValueUpperware();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPackage.BASIC_TYPE_ENUM:
				return createBasicTypeEnumFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPackage.BASIC_TYPE_ENUM:
				return convertBasicTypeEnumToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerValueUpperware createIntegerValueUpperware() {
		IntegerValueUpperwareImpl integerValueUpperware = new IntegerValueUpperwareImpl();
		return integerValueUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LongValueUpperware createLongValueUpperware() {
		LongValueUpperwareImpl longValueUpperware = new LongValueUpperwareImpl();
		return longValueUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatValueUpperware createFloatValueUpperware() {
		FloatValueUpperwareImpl floatValueUpperware = new FloatValueUpperwareImpl();
		return floatValueUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleValueUpperware createDoubleValueUpperware() {
		DoubleValueUpperwareImpl doubleValueUpperware = new DoubleValueUpperwareImpl();
		return doubleValueUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValueUpperware createStringValueUpperware() {
		StringValueUpperwareImpl stringValueUpperware = new StringValueUpperwareImpl();
		return stringValueUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanValueUpperware createBooleanValueUpperware() {
		BooleanValueUpperwareImpl booleanValueUpperware = new BooleanValueUpperwareImpl();
		return booleanValueUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicTypeEnum createBasicTypeEnumFromString(EDataType eDataType, String initialValue) {
		BasicTypeEnum result = BasicTypeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBasicTypeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPackage getTypesPackage() {
		return (TypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypesPackage getPackage() {
		return TypesPackage.eINSTANCE;
	}

} //TypesFactoryImpl
