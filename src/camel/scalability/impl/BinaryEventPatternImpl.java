/**
 */
package camel.scalability.impl;

import camel.scalability.BinaryEventPattern;
import camel.scalability.BinaryPatternOperatorType;
import camel.scalability.Event;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binary Event Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.BinaryEventPatternImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link camel.scalability.impl.BinaryEventPatternImpl#getRight <em>Right</em>}</li>
 *   <li>{@link camel.scalability.impl.BinaryEventPatternImpl#getLowerOccurrenceBound <em>Lower Occurrence Bound</em>}</li>
 *   <li>{@link camel.scalability.impl.BinaryEventPatternImpl#getUpperOccurrenceBound <em>Upper Occurrence Bound</em>}</li>
 *   <li>{@link camel.scalability.impl.BinaryEventPatternImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BinaryEventPatternImpl extends EventPatternImpl implements BinaryEventPattern {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BinaryEventPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.BINARY_EVENT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event getLeft() {
		return (Event)eGet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__LEFT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeft(Event newLeft) {
		eSet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__LEFT, newLeft);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event getRight() {
		return (Event)eGet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__RIGHT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRight(Event newRight) {
		eSet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__RIGHT, newRight);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLowerOccurrenceBound() {
		return (Integer)eGet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__LOWER_OCCURRENCE_BOUND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerOccurrenceBound(int newLowerOccurrenceBound) {
		eSet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__LOWER_OCCURRENCE_BOUND, newLowerOccurrenceBound);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getUpperOccurrenceBound() {
		return (Integer)eGet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__UPPER_OCCURRENCE_BOUND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperOccurrenceBound(int newUpperOccurrenceBound) {
		eSet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__UPPER_OCCURRENCE_BOUND, newUpperOccurrenceBound);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryPatternOperatorType getOperator() {
		return (BinaryPatternOperatorType)eGet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(BinaryPatternOperatorType newOperator) {
		eSet(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN__OPERATOR, newOperator);
	}

} //BinaryEventPatternImpl
