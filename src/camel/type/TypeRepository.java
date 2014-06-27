/**
 */
package camel.type;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.type.TypeRepository#getDataTypes <em>Data Types</em>}</li>
 *   <li>{@link camel.type.TypeRepository#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.type.TypePackage#getTypeRepository()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface TypeRepository extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Data Types</b></em>' containment reference list.
	 * The list contents are of type {@link camel.type.ValueType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Types</em>' containment reference list.
	 * @see camel.type.TypePackage#getTypeRepository_DataTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<ValueType> getDataTypes();

	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link camel.type.Value}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see camel.type.TypePackage#getTypeRepository_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<Value> getValues();

} // TypeRepository
