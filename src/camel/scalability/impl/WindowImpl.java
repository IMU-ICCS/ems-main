/**
 */
package camel.scalability.impl;

import camel.TimeIntervalUnit;

import camel.scalability.ScalabilityPackage;
import camel.scalability.Window;
import camel.scalability.WindowSizeType;
import camel.scalability.WindowType;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Window</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.WindowImpl#getUnit <em>Unit</em>}</li>
 *   <li>{@link camel.scalability.impl.WindowImpl#getWindowType <em>Window Type</em>}</li>
 *   <li>{@link camel.scalability.impl.WindowImpl#getSizeType <em>Size Type</em>}</li>
 *   <li>{@link camel.scalability.impl.WindowImpl#getMeasurementSize <em>Measurement Size</em>}</li>
 *   <li>{@link camel.scalability.impl.WindowImpl#getTimeSize <em>Time Size</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WindowImpl extends CDOObjectImpl implements Window {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WindowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.WINDOW;
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
	public TimeIntervalUnit getUnit() {
		return (TimeIntervalUnit)eGet(ScalabilityPackage.Literals.WINDOW__UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnit(TimeIntervalUnit newUnit) {
		eSet(ScalabilityPackage.Literals.WINDOW__UNIT, newUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowType getWindowType() {
		return (WindowType)eGet(ScalabilityPackage.Literals.WINDOW__WINDOW_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindowType(WindowType newWindowType) {
		eSet(ScalabilityPackage.Literals.WINDOW__WINDOW_TYPE, newWindowType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowSizeType getSizeType() {
		return (WindowSizeType)eGet(ScalabilityPackage.Literals.WINDOW__SIZE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSizeType(WindowSizeType newSizeType) {
		eSet(ScalabilityPackage.Literals.WINDOW__SIZE_TYPE, newSizeType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMeasurementSize() {
		return (Long)eGet(ScalabilityPackage.Literals.WINDOW__MEASUREMENT_SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMeasurementSize(long newMeasurementSize) {
		eSet(ScalabilityPackage.Literals.WINDOW__MEASUREMENT_SIZE, newMeasurementSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getTimeSize() {
		return (Long)eGet(ScalabilityPackage.Literals.WINDOW__TIME_SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeSize(long newTimeSize) {
		eSet(ScalabilityPackage.Literals.WINDOW__TIME_SIZE, newTimeSize);
	}

} //WindowImpl
