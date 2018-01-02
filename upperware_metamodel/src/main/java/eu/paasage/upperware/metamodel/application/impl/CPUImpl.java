/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.CPU;

import eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CPU</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.CPUImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.CPUImpl#getCores <em>Cores</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CPUImpl extends ResourceUpperwareImpl implements CPU {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CPUImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CPU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrequencyEnum getFrequency() {
		return (FrequencyEnum)eGet(ApplicationPackage.Literals.CPU__FREQUENCY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequency(FrequencyEnum newFrequency) {
		eSet(ApplicationPackage.Literals.CPU__FREQUENCY, newFrequency);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCores() {
		return (Integer)eGet(ApplicationPackage.Literals.CPU__CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCores(int newCores) {
		eSet(ApplicationPackage.Literals.CPU__CORES, newCores);
	}

} //CPUImpl
