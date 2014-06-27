/**
 */
package camel.execution.impl;

import camel.Application;

import camel.execution.ApplicationMeasurement;
import camel.execution.ExecutionPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Measurement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.ApplicationMeasurementImpl#getForApplication <em>For Application</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationMeasurementImpl extends MeasurementImpl implements ApplicationMeasurement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationMeasurementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.APPLICATION_MEASUREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Application getForApplication() {
		return (Application)eGet(ExecutionPackage.Literals.APPLICATION_MEASUREMENT__FOR_APPLICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForApplication(Application newForApplication) {
		eSet(ExecutionPackage.Literals.APPLICATION_MEASUREMENT__FOR_APPLICATION, newForApplication);
	}

} //ApplicationMeasurementImpl
