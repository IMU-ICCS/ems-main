/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension;
import eu.paasage.upperware.metamodel.cp.Parameter;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Normalised Utility Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.NormalisedUtilityDimensionImpl#getSolutions <em>Solutions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.NormalisedUtilityDimensionImpl#getGoal <em>Goal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NormalisedUtilityDimensionImpl extends CpFunctionImpl implements NormalisedUtilityDimension {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NormalisedUtilityDimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.NORMALISED_UTILITY_DIMENSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Parameter> getSolutions() {
		return (EList<Parameter>)eGet(CpPackage.Literals.NORMALISED_UTILITY_DIMENSION__SOLUTIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Goal getGoal() {
		return (Goal)eGet(CpPackage.Literals.NORMALISED_UTILITY_DIMENSION__GOAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoal(Goal newGoal) {
		eSet(CpPackage.Literals.NORMALISED_UTILITY_DIMENSION__GOAL, newGoal);
	}

} //NormalisedUtilityDimensionImpl
