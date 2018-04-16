/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.CountryUpperwareImpl#getContinent <em>Continent</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CountryUpperwareImpl extends LocationUpperwareImpl implements CountryUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CountryUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.COUNTRY_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContinentUpperware getContinent() {
		return (ContinentUpperware)eGet(TypesPaasagePackage.Literals.COUNTRY_UPPERWARE__CONTINENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContinent(ContinentUpperware newContinent) {
		eSet(TypesPaasagePackage.Literals.COUNTRY_UPPERWARE__CONTINENT, newContinent);
	}

} //CountryUpperwareImpl
