/**
 */
package camel;

import camel.organisation.DataCenter;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VM Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.VMInfo#getOfVM <em>Of VM</em>}</li>
 *   <li>{@link camel.VMInfo#getDataCenter <em>Data Center</em>}</li>
 *   <li>{@link camel.VMInfo#getCostPerHour <em>Cost Per Hour</em>}</li>
 *   <li>{@link camel.VMInfo#getCostUnit <em>Cost Unit</em>}</li>
 *   <li>{@link camel.VMInfo#getBenchmarkRate <em>Benchmark Rate</em>}</li>
 *   <li>{@link camel.VMInfo#getEvaluatedOn <em>Evaluated On</em>}</li>
 *   <li>{@link camel.VMInfo#getClassifiedOn <em>Classified On</em>}</li>
 *   <li>{@link camel.VMInfo#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getVMInfo()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correct_param_vals'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correct_param_vals='\n\t\t\t\t\t\tself.costPerHour >= 0 and self.benchmarkRate >= 0'"
 * @extends CDOObject
 * @generated
 */
public interface VMInfo extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Of VM</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of VM</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of VM</em>' reference.
	 * @see #setOfVM(VMType)
	 * @see camel.CamelPackage#getVMInfo_OfVM()
	 * @model required="true"
	 * @generated
	 */
	VMType getOfVM();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getOfVM <em>Of VM</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of VM</em>' reference.
	 * @see #getOfVM()
	 * @generated
	 */
	void setOfVM(VMType value);

	/**
	 * Returns the value of the '<em><b>Data Center</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Center</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Center</em>' reference.
	 * @see #setDataCenter(DataCenter)
	 * @see camel.CamelPackage#getVMInfo_DataCenter()
	 * @model
	 * @generated
	 */
	DataCenter getDataCenter();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getDataCenter <em>Data Center</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Center</em>' reference.
	 * @see #getDataCenter()
	 * @generated
	 */
	void setDataCenter(DataCenter value);

	/**
	 * Returns the value of the '<em><b>Cost Per Hour</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cost Per Hour</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cost Per Hour</em>' attribute.
	 * @see #setCostPerHour(double)
	 * @see camel.CamelPackage#getVMInfo_CostPerHour()
	 * @model
	 * @generated
	 */
	double getCostPerHour();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getCostPerHour <em>Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cost Per Hour</em>' attribute.
	 * @see #getCostPerHour()
	 * @generated
	 */
	void setCostPerHour(double value);

	/**
	 * Returns the value of the '<em><b>Cost Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cost Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cost Unit</em>' reference.
	 * @see #setCostUnit(MonetaryUnit)
	 * @see camel.CamelPackage#getVMInfo_CostUnit()
	 * @model required="true"
	 * @generated
	 */
	MonetaryUnit getCostUnit();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getCostUnit <em>Cost Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cost Unit</em>' reference.
	 * @see #getCostUnit()
	 * @generated
	 */
	void setCostUnit(MonetaryUnit value);

	/**
	 * Returns the value of the '<em><b>Benchmark Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Benchmark Rate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Benchmark Rate</em>' attribute.
	 * @see #setBenchmarkRate(double)
	 * @see camel.CamelPackage#getVMInfo_BenchmarkRate()
	 * @model
	 * @generated
	 */
	double getBenchmarkRate();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getBenchmarkRate <em>Benchmark Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Benchmark Rate</em>' attribute.
	 * @see #getBenchmarkRate()
	 * @generated
	 */
	void setBenchmarkRate(double value);

	/**
	 * Returns the value of the '<em><b>Evaluated On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evaluated On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evaluated On</em>' attribute.
	 * @see #setEvaluatedOn(Date)
	 * @see camel.CamelPackage#getVMInfo_EvaluatedOn()
	 * @model required="true"
	 * @generated
	 */
	Date getEvaluatedOn();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getEvaluatedOn <em>Evaluated On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evaluated On</em>' attribute.
	 * @see #getEvaluatedOn()
	 * @generated
	 */
	void setEvaluatedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Classified On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classified On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classified On</em>' attribute.
	 * @see #setClassifiedOn(Date)
	 * @see camel.CamelPackage#getVMInfo_ClassifiedOn()
	 * @model required="true"
	 * @generated
	 */
	Date getClassifiedOn();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getClassifiedOn <em>Classified On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classified On</em>' attribute.
	 * @see #getClassifiedOn()
	 * @generated
	 */
	void setClassifiedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.CamelPackage#getVMInfo_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.VMInfo#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // VMInfo
