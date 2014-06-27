/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.InternalComponent;
import camel.deployment.RequiredCommunication;
import camel.deployment.RequiredHost;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Internal Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.InternalComponentImpl#getRequiredCommunications <em>Required Communications</em>}</li>
 *   <li>{@link camel.deployment.impl.InternalComponentImpl#getCompositeInternalComponents <em>Composite Internal Components</em>}</li>
 *   <li>{@link camel.deployment.impl.InternalComponentImpl#getRequiredHost <em>Required Host</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InternalComponentImpl extends ComponentImpl implements InternalComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InternalComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.INTERNAL_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RequiredCommunication> getRequiredCommunications() {
		return (EList<RequiredCommunication>)eGet(DeploymentPackage.Literals.INTERNAL_COMPONENT__REQUIRED_COMMUNICATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<InternalComponent> getCompositeInternalComponents() {
		return (EList<InternalComponent>)eGet(DeploymentPackage.Literals.INTERNAL_COMPONENT__COMPOSITE_INTERNAL_COMPONENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredHost getRequiredHost() {
		return (RequiredHost)eGet(DeploymentPackage.Literals.INTERNAL_COMPONENT__REQUIRED_HOST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredHost(RequiredHost newRequiredHost) {
		eSet(DeploymentPackage.Literals.INTERNAL_COMPONENT__REQUIRED_HOST, newRequiredHost);
	}

	/**
	 * The cached invocation delegate for the '{@link #contains(camel.deployment.InternalComponent, camel.deployment.InternalComponent) <em>Contains</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #contains(camel.deployment.InternalComponent, camel.deployment.InternalComponent)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate CONTAINS_INTERNAL_COMPONENT_INTERNAL_COMPONENT__EINVOCATION_DELEGATE = ((EOperation.Internal)DeploymentPackage.Literals.INTERNAL_COMPONENT___CONTAINS__INTERNALCOMPONENT_INTERNALCOMPONENT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean contains(InternalComponent ic, InternalComponent rc) {
		try {
			return (Boolean)CONTAINS_INTERNAL_COMPONENT_INTERNAL_COMPONENT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(2, new Object[]{ic, rc}));
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DeploymentPackage.INTERNAL_COMPONENT___CONTAINS__INTERNALCOMPONENT_INTERNALCOMPONENT:
				return contains((InternalComponent)arguments.get(0), (InternalComponent)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //InternalComponentImpl
