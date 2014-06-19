/**
 */
package camel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see camel.CamelPackage
 * @generated
 */
public interface CamelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CamelFactory eINSTANCE = camel.impl.CamelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	CamelModel createCamelModel();

	/**
	 * Returns a new object of class '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action</em>'.
	 * @generated
	 */
	Action createAction();

	/**
	 * Returns a new object of class '<em>Physical Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Physical Node</em>'.
	 * @generated
	 */
	PhysicalNode createPhysicalNode();

	/**
	 * Returns a new object of class '<em>Data Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Object</em>'.
	 * @generated
	 */
	DataObject createDataObject();

	/**
	 * Returns a new object of class '<em>Requirement Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Requirement Group</em>'.
	 * @generated
	 */
	RequirementGroup createRequirementGroup();

	/**
	 * Returns a new object of class '<em>Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Requirement</em>'.
	 * @generated
	 */
	Requirement createRequirement();

	/**
	 * Returns a new object of class '<em>Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scalability Policy</em>'.
	 * @generated
	 */
	ScalabilityPolicy createScalabilityPolicy();

	/**
	 * Returns a new object of class '<em>VM Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VM Info</em>'.
	 * @generated
	 */
	VMInfo createVMInfo();

	/**
	 * Returns a new object of class '<em>Cloud Independent VM Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cloud Independent VM Info</em>'.
	 * @generated
	 */
	CloudIndependentVMInfo createCloudIndependentVMInfo();

	/**
	 * Returns a new object of class '<em>VM Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VM Type</em>'.
	 * @generated
	 */
	VMType createVMType();

	/**
	 * Returns a new object of class '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application</em>'.
	 * @generated
	 */
	Application createApplication();

	/**
	 * Returns a new object of class '<em>Monetary Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Monetary Unit</em>'.
	 * @generated
	 */
	MonetaryUnit createMonetaryUnit();

	/**
	 * Returns a new object of class '<em>Storage Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Storage Unit</em>'.
	 * @generated
	 */
	StorageUnit createStorageUnit();

	/**
	 * Returns a new object of class '<em>Time Interval Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Interval Unit</em>'.
	 * @generated
	 */
	TimeIntervalUnit createTimeIntervalUnit();

	/**
	 * Returns a new object of class '<em>Throughput Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Throughput Unit</em>'.
	 * @generated
	 */
	ThroughputUnit createThroughputUnit();

	/**
	 * Returns a new object of class '<em>Request Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Request Unit</em>'.
	 * @generated
	 */
	RequestUnit createRequestUnit();

	/**
	 * Returns a new object of class '<em>Unitless</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unitless</em>'.
	 * @generated
	 */
	Unitless createUnitless();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CamelPackage getCamelPackage();

} //CamelFactory
