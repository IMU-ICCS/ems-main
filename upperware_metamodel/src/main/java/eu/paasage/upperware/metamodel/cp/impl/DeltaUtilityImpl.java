/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.DeltaUtility;
import eu.paasage.upperware.metamodel.cp.Parameter;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delta Utility</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.DeltaUtilityImpl#getSolutions <em>Solutions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.DeltaUtilityImpl#getSelectedSolution <em>Selected Solution</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeltaUtilityImpl extends FunctionImpl implements DeltaUtility {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeltaUtilityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.DELTA_UTILITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Parameter> getSolutions() {
		return (EList<Parameter>)eGet(CpPackage.Literals.DELTA_UTILITY__SOLUTIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter getSelectedSolution() {
		return (Parameter)eGet(CpPackage.Literals.DELTA_UTILITY__SELECTED_SOLUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelectedSolution(Parameter newSelectedSolution) {
		eSet(CpPackage.Literals.DELTA_UTILITY__SELECTED_SOLUTION, newSelectedSolution);
	}

} //DeltaUtilityImpl
