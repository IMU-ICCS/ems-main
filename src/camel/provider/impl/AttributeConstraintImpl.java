/**
 */
package camel.provider.impl;

import camel.provider.Attribute;
import camel.provider.AttributeConstraint;
import camel.provider.ProviderPackage;

import camel.type.Value;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.AttributeConstraintImpl#getFrom <em>From</em>}</li>
 *   <li>{@link camel.provider.impl.AttributeConstraintImpl#getTo <em>To</em>}</li>
 *   <li>{@link camel.provider.impl.AttributeConstraintImpl#getFromValue <em>From Value</em>}</li>
 *   <li>{@link camel.provider.impl.AttributeConstraintImpl#getToValue <em>To Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeConstraintImpl extends CDOObjectImpl implements AttributeConstraint {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getFrom() {
		return (Attribute)eGet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(Attribute newFrom) {
		eSet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__FROM, newFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getTo() {
		return (Attribute)eGet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(Attribute newTo) {
		eSet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__TO, newTo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getFromValue() {
		return (Value)eGet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__FROM_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromValue(Value newFromValue) {
		eSet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__FROM_VALUE, newFromValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getToValue() {
		return (Value)eGet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__TO_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToValue(Value newToValue) {
		eSet(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT__TO_VALUE, newToValue);
	}

} //AttributeConstraintImpl
