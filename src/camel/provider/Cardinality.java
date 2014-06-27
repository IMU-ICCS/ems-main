/**
 */
package camel.provider;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cardinality</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Cardinality#getCardinalityMin <em>Cardinality Min</em>}</li>
 *   <li>{@link camel.provider.Cardinality#getCardinalityMax <em>Cardinality Max</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getCardinality()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Cardinality_Min_Less_Equal_Than_Max'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Cardinality_Min_Less_Equal_Than_Max='\n\t\t\tcardinalityMin >= 0 and cardinalityMax >= 0 and cardinalityMin <= cardinalityMax'"
 * @extends CDOObject
 * @generated
 */
public interface Cardinality extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Cardinality Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cardinality Min</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cardinality Min</em>' attribute.
	 * @see #setCardinalityMin(int)
	 * @see camel.provider.ProviderPackage#getCardinality_CardinalityMin()
	 * @model required="true"
	 * @generated
	 */
	int getCardinalityMin();

	/**
	 * Sets the value of the '{@link camel.provider.Cardinality#getCardinalityMin <em>Cardinality Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cardinality Min</em>' attribute.
	 * @see #getCardinalityMin()
	 * @generated
	 */
	void setCardinalityMin(int value);

	/**
	 * Returns the value of the '<em><b>Cardinality Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cardinality Max</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cardinality Max</em>' attribute.
	 * @see #setCardinalityMax(int)
	 * @see camel.provider.ProviderPackage#getCardinality_CardinalityMax()
	 * @model required="true"
	 * @generated
	 */
	int getCardinalityMax();

	/**
	 * Sets the value of the '{@link camel.provider.Cardinality#getCardinalityMax <em>Cardinality Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cardinality Max</em>' attribute.
	 * @see #getCardinalityMax()
	 * @generated
	 */
	void setCardinalityMax(int value);

} // Cardinality
