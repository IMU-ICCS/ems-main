/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.ConfigurationUpperware;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.Parameter;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl#getSolution <em>Solution</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl#getValue <em>Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl#getGoal <em>Goal</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigurationUpperwareImpl extends NumericExpressionImpl implements ConfigurationUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.CONFIGURATION_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter getSolution() {
		return (Parameter)eGet(CpPackage.Literals.CONFIGURATION_UPPERWARE__SOLUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolution(Parameter newSolution) {
		eSet(CpPackage.Literals.CONFIGURATION_UPPERWARE__SOLUTION, newSolution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueUpperware getValue() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.CONFIGURATION_UPPERWARE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValueUpperware newValue) {
		eSet(CpPackage.Literals.CONFIGURATION_UPPERWARE__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Goal getGoal() {
		return (Goal)eGet(CpPackage.Literals.CONFIGURATION_UPPERWARE__GOAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoal(Goal newGoal) {
		eSet(CpPackage.Literals.CONFIGURATION_UPPERWARE__GOAL, newGoal);
	}

} //ConfigurationUpperwareImpl
