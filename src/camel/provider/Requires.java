/**
 */
package camel.provider;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requires</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Requires#getScopeFrom <em>Scope From</em>}</li>
 *   <li>{@link camel.provider.Requires#getScopeTo <em>Scope To</em>}</li>
 *   <li>{@link camel.provider.Requires#getCardFrom <em>Card From</em>}</li>
 *   <li>{@link camel.provider.Requires#getCardTo <em>Card To</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getRequires()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='at_least_one_value_in_requires cardFrom_To_Conformance'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot at_least_one_value_in_requires='\n\t\t\t\t\t\tscopeFrom <> null or scopeTo <> null' cardFrom_To_Conformance='\n\t\t\t\t\t\t(cardFrom <> null implies (cardFrom.cardinalityMin >= from.featureCardinality.cardinalityMin and cardFrom.cardinalityMax <= from.featureCardinality.cardinalityMax)) \n\t\t\t\t\t\tand \n\t\t\t\t\t\t(cardTo <> null implies (cardTo.cardinalityMin >= to.featureCardinality.cardinalityMin and cardTo.cardinalityMax <= to.featureCardinality.cardinalityMax))'"
 * @generated
 */
public interface Requires extends Constraint {
	/**
	 * Returns the value of the '<em><b>Scope From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope From</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope From</em>' containment reference.
	 * @see #setScopeFrom(Scope)
	 * @see camel.provider.ProviderPackage#getRequires_ScopeFrom()
	 * @model containment="true"
	 * @generated
	 */
	Scope getScopeFrom();

	/**
	 * Sets the value of the '{@link camel.provider.Requires#getScopeFrom <em>Scope From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope From</em>' containment reference.
	 * @see #getScopeFrom()
	 * @generated
	 */
	void setScopeFrom(Scope value);

	/**
	 * Returns the value of the '<em><b>Scope To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope To</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope To</em>' containment reference.
	 * @see #setScopeTo(Scope)
	 * @see camel.provider.ProviderPackage#getRequires_ScopeTo()
	 * @model containment="true"
	 * @generated
	 */
	Scope getScopeTo();

	/**
	 * Sets the value of the '{@link camel.provider.Requires#getScopeTo <em>Scope To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope To</em>' containment reference.
	 * @see #getScopeTo()
	 * @generated
	 */
	void setScopeTo(Scope value);

	/**
	 * Returns the value of the '<em><b>Card From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Card From</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Card From</em>' containment reference.
	 * @see #setCardFrom(FeatCardinality)
	 * @see camel.provider.ProviderPackage#getRequires_CardFrom()
	 * @model containment="true"
	 * @generated
	 */
	FeatCardinality getCardFrom();

	/**
	 * Sets the value of the '{@link camel.provider.Requires#getCardFrom <em>Card From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Card From</em>' containment reference.
	 * @see #getCardFrom()
	 * @generated
	 */
	void setCardFrom(FeatCardinality value);

	/**
	 * Returns the value of the '<em><b>Card To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Card To</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Card To</em>' containment reference.
	 * @see #setCardTo(FeatCardinality)
	 * @see camel.provider.ProviderPackage#getRequires_CardTo()
	 * @model containment="true"
	 * @generated
	 */
	FeatCardinality getCardTo();

	/**
	 * Sets the value of the '{@link camel.provider.Requires#getCardTo <em>Card To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Card To</em>' containment reference.
	 * @see #getCardTo()
	 * @generated
	 */
	void setCardTo(FeatCardinality value);

} // Requires
