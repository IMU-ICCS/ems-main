/**
 */
package cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comparison Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.ComparisonExpression#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link cp.ComparisonExpression#getExp2 <em>Exp2</em>}</li>
 *   <li>{@link cp.ComparisonExpression#getComparator <em>Comparator</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getComparisonExpression()
 * @model
 * @generated
 */
public interface ComparisonExpression extends BooleanExpression {
	/**
	 * Returns the value of the '<em><b>Exp1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exp1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exp1</em>' reference.
	 * @see #setExp1(Expression)
	 * @see cp.CpPackage#getComparisonExpression_Exp1()
	 * @model required="true"
	 * @generated
	 */
	Expression getExp1();

	/**
	 * Sets the value of the '{@link cp.ComparisonExpression#getExp1 <em>Exp1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp1</em>' reference.
	 * @see #getExp1()
	 * @generated
	 */
	void setExp1(Expression value);

	/**
	 * Returns the value of the '<em><b>Exp2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exp2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exp2</em>' reference.
	 * @see #setExp2(Expression)
	 * @see cp.CpPackage#getComparisonExpression_Exp2()
	 * @model required="true"
	 * @generated
	 */
	Expression getExp2();

	/**
	 * Sets the value of the '{@link cp.ComparisonExpression#getExp2 <em>Exp2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp2</em>' reference.
	 * @see #getExp2()
	 * @generated
	 */
	void setExp2(Expression value);

	/**
	 * Returns the value of the '<em><b>Comparator</b></em>' attribute.
	 * The literals are from the enumeration {@link cp.ComparatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparator</em>' attribute.
	 * @see cp.ComparatorEnum
	 * @see #setComparator(ComparatorEnum)
	 * @see cp.CpPackage#getComparisonExpression_Comparator()
	 * @model required="true"
	 * @generated
	 */
	ComparatorEnum getComparator();

	/**
	 * Sets the value of the '{@link cp.ComparisonExpression#getComparator <em>Comparator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comparator</em>' attribute.
	 * @see cp.ComparatorEnum
	 * @see #getComparator()
	 * @generated
	 */
	void setComparator(ComparatorEnum value);

} // ComparisonExpression
