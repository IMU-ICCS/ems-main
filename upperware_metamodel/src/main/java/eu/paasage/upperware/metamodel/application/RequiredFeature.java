/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.typesPaasage.CommunicationTypeUpperware;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getFeature <em>Feature</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getProvidedBy <em>Provided By</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getCommunicationType <em>Communication Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isOptional <em>Optional</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isContaiment <em>Contaiment</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface RequiredFeature extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' attribute.
	 * @see #setFeature(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Feature()
	 * @model required="true"
	 * @generated
	 */
	String getFeature();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getFeature <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' attribute.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(String value);

	/**
	 * Returns the value of the '<em><b>Provided By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided By</em>' reference.
	 * @see #setProvidedBy(CloudMLElementUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_ProvidedBy()
	 * @model required="true"
	 * @generated
	 */
	CloudMLElementUpperware getProvidedBy();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getProvidedBy <em>Provided By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided By</em>' reference.
	 * @see #getProvidedBy()
	 * @generated
	 */
	void setProvidedBy(CloudMLElementUpperware value);

	/**
	 * Returns the value of the '<em><b>Communication Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.CommunicationTypeUpperware}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Communication Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Communication Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CommunicationTypeUpperware
	 * @see #setCommunicationType(CommunicationTypeUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_CommunicationType()
	 * @model required="true"
	 * @generated
	 */
	CommunicationTypeUpperware getCommunicationType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getCommunicationType <em>Communication Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Communication Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CommunicationTypeUpperware
	 * @see #getCommunicationType()
	 * @generated
	 */
	void setCommunicationType(CommunicationTypeUpperware value);

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Optional()
	 * @model required="true"
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Contaiment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contaiment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contaiment</em>' attribute.
	 * @see #setContaiment(boolean)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Contaiment()
	 * @model required="true"
	 * @generated
	 */
	boolean isContaiment();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isContaiment <em>Contaiment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contaiment</em>' attribute.
	 * @see #isContaiment()
	 * @generated
	 */
	void setContaiment(boolean value);

} // RequiredFeature
