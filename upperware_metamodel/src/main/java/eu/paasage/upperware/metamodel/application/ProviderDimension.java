/**
 */
package eu.paasage.upperware.metamodel.application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Provider Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getValue <em>Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getProvider <em>Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getMetricID <em>Metric ID</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getProviderDimension()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProviderDimension extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(double)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getProviderDimension_Value()
	 * @model
	 * @generated
	 */
	double getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(double value);

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' reference.
	 * @see #setProvider(Provider)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getProviderDimension_Provider()
	 * @model required="true"
	 * @generated
	 */
	Provider getProvider();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getProvider <em>Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(Provider value);

	/**
	 * Returns the value of the '<em><b>Metric ID</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric ID</em>' attribute.
	 * @see #setMetricID(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getProviderDimension_MetricID()
	 * @model default=""
	 * @generated
	 */
	String getMetricID();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getMetricID <em>Metric ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric ID</em>' attribute.
	 * @see #getMetricID()
	 * @generated
	 */
	void setMetricID(String value);

} // ProviderDimension
