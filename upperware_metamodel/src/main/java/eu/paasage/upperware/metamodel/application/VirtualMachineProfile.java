/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Virtual Machine Profile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getSize <em>Size</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getMemory <em>Memory</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getStorage <em>Storage</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getCpu <em>Cpu</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getOs <em>Os</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getProviderDimension <em>Provider Dimension</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getLocation <em>Location</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getImage <em>Image</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getRelatedCloudVMId <em>Related Cloud VM Id</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile()
 * @model
 * @generated
 */
public interface VirtualMachineProfile extends CloudMLElementUpperware {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
	 * @see #setSize(VMSizeEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Size()
	 * @model required="true"
	 * @generated
	 */
	VMSizeEnum getSize();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
	 * @see #getSize()
	 * @generated
	 */
	void setSize(VMSizeEnum value);

	/**
	 * Returns the value of the '<em><b>Memory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory</em>' containment reference.
	 * @see #setMemory(Memory)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Memory()
	 * @model containment="true"
	 * @generated
	 */
	Memory getMemory();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getMemory <em>Memory</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory</em>' containment reference.
	 * @see #getMemory()
	 * @generated
	 */
	void setMemory(Memory value);

	/**
	 * Returns the value of the '<em><b>Storage</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Storage</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Storage</em>' containment reference.
	 * @see #setStorage(Storage)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Storage()
	 * @model containment="true"
	 * @generated
	 */
	Storage getStorage();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getStorage <em>Storage</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Storage</em>' containment reference.
	 * @see #getStorage()
	 * @generated
	 */
	void setStorage(Storage value);

	/**
	 * Returns the value of the '<em><b>Cpu</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cpu</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cpu</em>' containment reference.
	 * @see #setCpu(CPU)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Cpu()
	 * @model containment="true"
	 * @generated
	 */
	CPU getCpu();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getCpu <em>Cpu</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu</em>' containment reference.
	 * @see #getCpu()
	 * @generated
	 */
	void setCpu(CPU value);

	/**
	 * Returns the value of the '<em><b>Os</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os</em>' containment reference.
	 * @see #setOs(OS)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Os()
	 * @model containment="true"
	 * @generated
	 */
	OS getOs();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getOs <em>Os</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os</em>' containment reference.
	 * @see #getOs()
	 * @generated
	 */
	void setOs(OS value);

	/**
	 * Returns the value of the '<em><b>Provider Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.ProviderDimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Dimension</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Dimension</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_ProviderDimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProviderDimension> getProviderDimension();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' reference.
	 * @see #setLocation(LocationUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Location()
	 * @model
	 * @generated
	 */
	LocationUpperware getLocation();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getLocation <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' reference.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(LocationUpperware value);

	/**
	 * Returns the value of the '<em><b>Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' containment reference.
	 * @see #setImage(ImageUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Image()
	 * @model containment="true"
	 * @generated
	 */
	ImageUpperware getImage();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getImage <em>Image</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' containment reference.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(ImageUpperware value);

	/**
	 * Returns the value of the '<em><b>Related Cloud VM Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Cloud VM Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Cloud VM Id</em>' attribute.
	 * @see #setRelatedCloudVMId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_RelatedCloudVMId()
	 * @model required="true"
	 * @generated
	 */
	String getRelatedCloudVMId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getRelatedCloudVMId <em>Related Cloud VM Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Cloud VM Id</em>' attribute.
	 * @see #getRelatedCloudVMId()
	 * @generated
	 */
	void setRelatedCloudVMId(String value);

} // VirtualMachineProfile
