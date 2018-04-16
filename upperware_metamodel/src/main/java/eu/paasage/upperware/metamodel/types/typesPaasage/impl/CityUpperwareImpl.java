/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>City Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.CityUpperwareImpl#getCountry <em>Country</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CityUpperwareImpl extends LocationUpperwareImpl implements CityUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CityUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.CITY_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CountryUpperware getCountry() {
		return (CountryUpperware)eGet(TypesPaasagePackage.Literals.CITY_UPPERWARE__COUNTRY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountry(CountryUpperware newCountry) {
		eSet(TypesPaasagePackage.Literals.CITY_UPPERWARE__COUNTRY, newCountry);
	}

} //CityUpperwareImpl
