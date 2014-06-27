/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.OS;
import types.typesPaasage.OSArchitectureEnum;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>OS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.OSImpl#getName <em>Name</em>}</li>
 *   <li>{@link types.typesPaasage.impl.OSImpl#getVers <em>Vers</em>}</li>
 *   <li>{@link types.typesPaasage.impl.OSImpl#getArchitecture <em>Architecture</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OSImpl extends PaaSageCPElementImpl implements OS {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.OS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(TypesPaasagePackage.Literals.OS__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(TypesPaasagePackage.Literals.OS__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVers() {
		return (String)eGet(TypesPaasagePackage.Literals.OS__VERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVers(String newVers) {
		eSet(TypesPaasagePackage.Literals.OS__VERS, newVers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OSArchitectureEnum getArchitecture() {
		return (OSArchitectureEnum)eGet(TypesPaasagePackage.Literals.OS__ARCHITECTURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArchitecture(OSArchitectureEnum newArchitecture) {
		eSet(TypesPaasagePackage.Literals.OS__ARCHITECTURE, newArchitecture);
	}

} //OSImpl
