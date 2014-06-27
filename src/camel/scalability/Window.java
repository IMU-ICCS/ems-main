/**
 */
package camel.scalability;

import camel.TimeIntervalUnit;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Window</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.Window#getUnit <em>Unit</em>}</li>
 *   <li>{@link camel.scalability.Window#getWindowType <em>Window Type</em>}</li>
 *   <li>{@link camel.scalability.Window#getSizeType <em>Size Type</em>}</li>
 *   <li>{@link camel.scalability.Window#getMeasurementSize <em>Measurement Size</em>}</li>
 *   <li>{@link camel.scalability.Window#getTimeSize <em>Time Size</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getWindow()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='window_positive_params window_right_params_exist'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot window_positive_params='\n\t\t\t\t\t\t(measurementSize >= 0) and (timeSize >= 0)' window_right_params_exist='\n\t\t\t\t\t\t(self.sizeType = WindowSizeType::MEASUREMENTS_ONLY implies (unit = null and timeSize = 0 and measurementSize > 0)) and (self.sizeType = WindowSizeType::TIME_ONLY implies (unit <> null and timeSize > 0 and measurementSize = 0)) and ((self.sizeType = WindowSizeType::FIRST_MATCH or self.sizeType = WindowSizeType::BOTH_MATCH) implies (timeSize > 0 and unit <> null and measurementSize > 0))'"
 * @extends CDOObject
 * @generated
 */
public interface Window extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' reference.
	 * @see #setUnit(TimeIntervalUnit)
	 * @see camel.scalability.ScalabilityPackage#getWindow_Unit()
	 * @model
	 * @generated
	 */
	TimeIntervalUnit getUnit();

	/**
	 * Sets the value of the '{@link camel.scalability.Window#getUnit <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(TimeIntervalUnit value);

	/**
	 * Returns the value of the '<em><b>Window Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.WindowType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window Type</em>' attribute.
	 * @see camel.scalability.WindowType
	 * @see #setWindowType(WindowType)
	 * @see camel.scalability.ScalabilityPackage#getWindow_WindowType()
	 * @model required="true"
	 * @generated
	 */
	WindowType getWindowType();

	/**
	 * Sets the value of the '{@link camel.scalability.Window#getWindowType <em>Window Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window Type</em>' attribute.
	 * @see camel.scalability.WindowType
	 * @see #getWindowType()
	 * @generated
	 */
	void setWindowType(WindowType value);

	/**
	 * Returns the value of the '<em><b>Size Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.WindowSizeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size Type</em>' attribute.
	 * @see camel.scalability.WindowSizeType
	 * @see #setSizeType(WindowSizeType)
	 * @see camel.scalability.ScalabilityPackage#getWindow_SizeType()
	 * @model required="true"
	 * @generated
	 */
	WindowSizeType getSizeType();

	/**
	 * Sets the value of the '{@link camel.scalability.Window#getSizeType <em>Size Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size Type</em>' attribute.
	 * @see camel.scalability.WindowSizeType
	 * @see #getSizeType()
	 * @generated
	 */
	void setSizeType(WindowSizeType value);

	/**
	 * Returns the value of the '<em><b>Measurement Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Measurement Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Measurement Size</em>' attribute.
	 * @see #setMeasurementSize(long)
	 * @see camel.scalability.ScalabilityPackage#getWindow_MeasurementSize()
	 * @model
	 * @generated
	 */
	long getMeasurementSize();

	/**
	 * Sets the value of the '{@link camel.scalability.Window#getMeasurementSize <em>Measurement Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Measurement Size</em>' attribute.
	 * @see #getMeasurementSize()
	 * @generated
	 */
	void setMeasurementSize(long value);

	/**
	 * Returns the value of the '<em><b>Time Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Size</em>' attribute.
	 * @see #setTimeSize(long)
	 * @see camel.scalability.ScalabilityPackage#getWindow_TimeSize()
	 * @model
	 * @generated
	 */
	long getTimeSize();

	/**
	 * Sets the value of the '{@link camel.scalability.Window#getTimeSize <em>Time Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Size</em>' attribute.
	 * @see #getTimeSize()
	 * @generated
	 */
	void setTimeSize(long value);

} // Window
