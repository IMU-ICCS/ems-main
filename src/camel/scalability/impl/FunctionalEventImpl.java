/**
 */
package camel.scalability.impl;

import camel.scalability.FunctionalEvent;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Functional Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.FunctionalEventImpl#getFunctionalType <em>Functional Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionalEventImpl extends SimpleEventImpl implements FunctionalEvent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionalEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.FUNCTIONAL_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFunctionalType() {
		return (String)eGet(ScalabilityPackage.Literals.FUNCTIONAL_EVENT__FUNCTIONAL_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionalType(String newFunctionalType) {
		eSet(ScalabilityPackage.Literals.FUNCTIONAL_EVENT__FUNCTIONAL_TYPE, newFunctionalType);
	}

} //FunctionalEventImpl
