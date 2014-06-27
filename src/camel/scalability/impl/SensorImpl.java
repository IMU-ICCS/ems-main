/**
 */
package camel.scalability.impl;

import camel.scalability.ScalabilityPackage;
import camel.scalability.Sensor;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sensor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.SensorImpl#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link camel.scalability.impl.SensorImpl#isIsPush <em>Is Push</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SensorImpl extends CDOObjectImpl implements Sensor {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SensorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.SENSOR;
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
	public String getConfiguration() {
		return (String)eGet(ScalabilityPackage.Literals.SENSOR__CONFIGURATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfiguration(String newConfiguration) {
		eSet(ScalabilityPackage.Literals.SENSOR__CONFIGURATION, newConfiguration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsPush() {
		return (Boolean)eGet(ScalabilityPackage.Literals.SENSOR__IS_PUSH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsPush(boolean newIsPush) {
		eSet(ScalabilityPackage.Literals.SENSOR__IS_PUSH, newIsPush);
	}

} //SensorImpl
