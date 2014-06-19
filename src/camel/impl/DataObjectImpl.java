/**
 */
package camel.impl;

import camel.CamelPackage;
import camel.DataObject;

import camel.cerif.impl.ResourceImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.DataObjectImpl#getReplication <em>Replication</em>}</li>
 *   <li>{@link camel.impl.DataObjectImpl#getPartitioning <em>Partitioning</em>}</li>
 *   <li>{@link camel.impl.DataObjectImpl#getConsistency <em>Consistency</em>}</li>
 *   <li>{@link camel.impl.DataObjectImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataObjectImpl extends ResourceImpl implements DataObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.DATA_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReplication() {
		return (String)eGet(CamelPackage.Literals.DATA_OBJECT__REPLICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplication(String newReplication) {
		eSet(CamelPackage.Literals.DATA_OBJECT__REPLICATION, newReplication);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPartitioning() {
		return (String)eGet(CamelPackage.Literals.DATA_OBJECT__PARTITIONING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartitioning(String newPartitioning) {
		eSet(CamelPackage.Literals.DATA_OBJECT__PARTITIONING, newPartitioning);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConsistency() {
		return (String)eGet(CamelPackage.Literals.DATA_OBJECT__CONSISTENCY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConsistency(String newConsistency) {
		eSet(CamelPackage.Literals.DATA_OBJECT__CONSISTENCY, newConsistency);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(CamelPackage.Literals.DATA_OBJECT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CamelPackage.Literals.DATA_OBJECT__NAME, newName);
	}

} //DataObjectImpl
