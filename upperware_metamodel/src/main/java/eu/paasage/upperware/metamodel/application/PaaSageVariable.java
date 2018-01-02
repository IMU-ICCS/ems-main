/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedComponent <em>Related Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedProvider <em>Related Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PaaSageVariable extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Paasage Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paasage Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paasage Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
	 * @see #setPaasageType(VariableElementTypeEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_PaasageType()
	 * @model
	 * @generated
	 */
	VariableElementTypeEnum getPaasageType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paasage Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
	 * @see #getPaasageType()
	 * @generated
	 */
	void setPaasageType(VariableElementTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Related Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Component</em>' reference.
	 * @see #setRelatedComponent(ApplicationComponent)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_RelatedComponent()
	 * @model
	 * @generated
	 */
	ApplicationComponent getRelatedComponent();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedComponent <em>Related Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Component</em>' reference.
	 * @see #getRelatedComponent()
	 * @generated
	 */
	void setRelatedComponent(ApplicationComponent value);

	/**
	 * Returns the value of the '<em><b>Cp Variable Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cp Variable Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cp Variable Id</em>' attribute.
	 * @see #setCpVariableId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_CpVariableId()
	 * @model required="true"
	 * @generated
	 */
	String getCpVariableId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cp Variable Id</em>' attribute.
	 * @see #getCpVariableId()
	 * @generated
	 */
	void setCpVariableId(String value);

	/**
	 * Returns the value of the '<em><b>Related Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Provider</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Provider</em>' reference.
	 * @see #setRelatedProvider(Provider)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_RelatedProvider()
	 * @model
	 * @generated
	 */
	Provider getRelatedProvider();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedProvider <em>Related Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Provider</em>' reference.
	 * @see #getRelatedProvider()
	 * @generated
	 */
	void setRelatedProvider(Provider value);

	/**
	 * Returns the value of the '<em><b>Related Virtual Machine Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Virtual Machine Profile</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Virtual Machine Profile</em>' reference.
	 * @see #setRelatedVirtualMachineProfile(VirtualMachineProfile)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_RelatedVirtualMachineProfile()
	 * @model
	 * @generated
	 */
	VirtualMachineProfile getRelatedVirtualMachineProfile();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Virtual Machine Profile</em>' reference.
	 * @see #getRelatedVirtualMachineProfile()
	 * @generated
	 */
	void setRelatedVirtualMachineProfile(VirtualMachineProfile value);

} // PaaSageVariable
