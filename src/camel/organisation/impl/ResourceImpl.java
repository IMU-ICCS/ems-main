/**
 */
package camel.organisation.impl;

import camel.Action;

import camel.organisation.OrganisationPackage;
import camel.organisation.Resource;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ResourceImpl extends CDOObjectImpl implements Resource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.RESOURCE;
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
	 * The cached invocation delegate for the '{@link #ofClass(org.eclipse.emf.ecore.EClass) <em>Of Class</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ofClass(org.eclipse.emf.ecore.EClass)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate OF_CLASS_ECLASS__EINVOCATION_DELEGATE = ((EOperation.Internal)OrganisationPackage.Literals.RESOURCE___OF_CLASS__ECLASS).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean ofClass(EClass x) {
		try {
			return (Boolean)OF_CLASS_ECLASS__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{x}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #allowsActions(org.eclipse.emf.common.util.EList) <em>Allows Actions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #allowsActions(org.eclipse.emf.common.util.EList)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate ALLOWS_ACTIONS_ELIST__EINVOCATION_DELEGATE = ((EOperation.Internal)OrganisationPackage.Literals.RESOURCE___ALLOWS_ACTIONS__ELIST).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean allowsActions(EList<Action> acts) {
		try {
			return (Boolean)ALLOWS_ACTIONS_ELIST__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{acts}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case OrganisationPackage.RESOURCE___OF_CLASS__ECLASS:
				return ofClass((EClass)arguments.get(0));
			case OrganisationPackage.RESOURCE___ALLOWS_ACTIONS__ELIST:
				return allowsActions((EList<Action>)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ResourceImpl
