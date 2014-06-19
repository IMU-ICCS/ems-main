/**
 */
package camel;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.Unit#getDimensionType <em>Dimension Type</em>}</li>
 *   <li>{@link camel.Unit#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getUnit()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correctUnit'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correctUnit='\n\t\t\t\t\t\tcheckUnit()'"
 * @extends CDOObject
 * @generated
 */
public interface Unit extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Dimension Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.UnitDimensionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimension Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension Type</em>' attribute.
	 * @see camel.UnitDimensionType
	 * @see #setDimensionType(UnitDimensionType)
	 * @see camel.CamelPackage#getUnit_DimensionType()
	 * @model required="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot derivation='if (self.oclIsTypeOf(TimeIntervalUnit)) then UnitDimensionType::TIME_INTERVAL\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(StorageUnit)) then UnitDimensionType::STORAGE\n\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(MonetaryUnit)) then UnitDimensionType::COST\n\t\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(ThroughputUnit)) then UnitDimensionType::THROUGHPUT\n\t\t\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(RequestUnit)) then UnitDimensionType::REQUEST_NUM\n\t\t\t\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(Unitless)) then UnitDimensionType::UNITLESS\n\t\t\t\t\t\t\t\t\t\t\t\t\t\telse null\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	UnitDimensionType getDimensionType();

	/**
	 * Sets the value of the '{@link camel.Unit#getDimensionType <em>Dimension Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension Type</em>' attribute.
	 * @see camel.UnitDimensionType
	 * @see #getDimensionType()
	 * @generated
	 */
	void setDimensionType(UnitDimensionType value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.UnitType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see camel.UnitType
	 * @see #setUnit(UnitType)
	 * @see camel.CamelPackage#getUnit_Unit()
	 * @model required="true"
	 * @generated
	 */
	UnitType getUnit();

	/**
	 * Sets the value of the '{@link camel.Unit#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see camel.UnitType
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(UnitType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='if (dimensionType = UnitDimensionType::TIME_INTERVAL) then \n\t\t\t\t\t\t\t\tif (unit = UnitType::MILLISECONDS or unit = UnitType::SECONDS or unit = UnitType::MINUTES or unit = UnitType::HOURS or unit = UnitType::DAYS or unit = UnitType::WEEKS or unit = UnitType::MONTHS) then true\n\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t  else \n\t\t\t\t\t\t\t  \tif (dimensionType = UnitDimensionType::STORAGE) then\n\t\t\t\t\t\t\t  \t\tif (unit = UnitType::BYTES or unit = UnitType::KILOBYTES or unit = UnitType::MEGABYTES or unit = UnitType::GIGABYTES) then true\n\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::COST) then\n\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::EUROS or unit = UnitType::DOLLARS or unit = UnitType::POUNDS) then true\n\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::THROUGHPUT) then\n\t\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::BYTES_PER_SECOND or unit = UnitType::REQUESTS_PER_SECOND) then true\n\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::REQUEST_NUM) then\n\t\t\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::REQUESTS) then true else false endif\n\t\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::UNITLESS) then\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::PERCENTAGE) then true\n\t\t\t\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean checkUnit();

} // Unit
