/**
 */
package application;

import cp.ComparisonExpression;
import cp.Expression;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paasage Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.PaasageConfiguration#getId <em>Id</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getGoals <em>Goals</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getVariables <em>Variables</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getRules <em>Rules</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getComponents <em>Components</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getProviders <em>Providers</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getVmProfiles <em>Vm Profiles</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getAuxExpressions <em>Aux Expressions</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getVms <em>Vms</em>}</li>
 *   <li>{@link application.PaasageConfiguration#getMonitoredDimensions <em>Monitored Dimensions</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getPaasageConfiguration()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PaasageConfiguration extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see application.ApplicationPackage#getPaasageConfiguration_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link application.PaasageConfiguration#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Goals</b></em>' containment reference list.
	 * The list contents are of type {@link application.PaaSageGoal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goals</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Goals()
	 * @model containment="true"
	 * @generated
	 */
	EList<PaaSageGoal> getGoals();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link cp.ComparisonExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComparisonExpression> getConstraints();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link application.PaaSageVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<PaaSageVariable> getVariables();

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link application.ElasticityRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<ElasticityRule> getRules();

	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference list.
	 * The list contents are of type {@link application.ApplicationComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Components()
	 * @model containment="true"
	 * @generated
	 */
	EList<ApplicationComponent> getComponents();

	/**
	 * Returns the value of the '<em><b>Providers</b></em>' containment reference list.
	 * The list contents are of type {@link application.Provider}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Providers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Providers</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Providers()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Provider> getProviders();

	/**
	 * Returns the value of the '<em><b>Vm Profiles</b></em>' containment reference list.
	 * The list contents are of type {@link application.VirtualMachineProfile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Profiles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Profiles</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_VmProfiles()
	 * @model containment="true"
	 * @generated
	 */
	EList<VirtualMachineProfile> getVmProfiles();

	/**
	 * Returns the value of the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link cp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aux Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aux Expressions</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_AuxExpressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getAuxExpressions();

	/**
	 * Returns the value of the '<em><b>Vms</b></em>' containment reference list.
	 * The list contents are of type {@link application.VirtualMachine}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vms</em>' containment reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_Vms()
	 * @model containment="true"
	 * @generated
	 */
	EList<VirtualMachine> getVms();

	/**
	 * Returns the value of the '<em><b>Monitored Dimensions</b></em>' reference list.
	 * The list contents are of type {@link application.Dimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Monitored Dimensions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Monitored Dimensions</em>' reference list.
	 * @see application.ApplicationPackage#getPaasageConfiguration_MonitoredDimensions()
	 * @model
	 * @generated
	 */
	EList<Dimension> getMonitoredDimensions();

} // PaasageConfiguration
