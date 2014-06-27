/**
 */
package camel.type.impl;

import camel.type.TypePackage;
import camel.type.TypeRepository;
import camel.type.Value;
import camel.type.ValueType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.type.impl.TypeRepositoryImpl#getDataTypes <em>Data Types</em>}</li>
 *   <li>{@link camel.type.impl.TypeRepositoryImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypeRepositoryImpl extends CDOObjectImpl implements TypeRepository {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypePackage.Literals.TYPE_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ValueType> getDataTypes() {
		return (EList<ValueType>)eGet(TypePackage.Literals.TYPE_REPOSITORY__DATA_TYPES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Value> getValues() {
		return (EList<Value>)eGet(TypePackage.Literals.TYPE_REPOSITORY__VALUES, true);
	}

} //TypeRepositoryImpl
