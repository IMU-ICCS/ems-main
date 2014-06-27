/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.Continent;
import types.typesPaasage.Country;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.CountryImpl#getContinent <em>Continent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CountryImpl extends LocationImpl implements Country {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CountryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.COUNTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Continent getContinent() {
		return (Continent)eGet(TypesPaasagePackage.Literals.COUNTRY__CONTINENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContinent(Continent newContinent) {
		eSet(TypesPaasagePackage.Literals.COUNTRY__CONTINENT, newContinent);
	}

} //CountryImpl
