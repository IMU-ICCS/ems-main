/**
 */
package camel.scalability.impl;

import camel.scalability.MetricCondition;
import camel.scalability.NonFunctionalEvent;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Non Functional Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.NonFunctionalEventImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link camel.scalability.impl.NonFunctionalEventImpl#isIsViolation <em>Is Violation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NonFunctionalEventImpl extends SimpleEventImpl implements NonFunctionalEvent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NonFunctionalEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.NON_FUNCTIONAL_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricCondition getCondition() {
		return (MetricCondition)eGet(ScalabilityPackage.Literals.NON_FUNCTIONAL_EVENT__CONDITION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(MetricCondition newCondition) {
		eSet(ScalabilityPackage.Literals.NON_FUNCTIONAL_EVENT__CONDITION, newCondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsViolation() {
		return (Boolean)eGet(ScalabilityPackage.Literals.NON_FUNCTIONAL_EVENT__IS_VIOLATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsViolation(boolean newIsViolation) {
		eSet(ScalabilityPackage.Literals.NON_FUNCTIONAL_EVENT__IS_VIOLATION, newIsViolation);
	}

} //NonFunctionalEventImpl
