/**
 */
package camel.scalability.impl;

import camel.execution.ExecutionContext;

import camel.scalability.EventPattern;
import camel.scalability.ScalabilityPackage;
import camel.scalability.SimpleEvent;
import camel.scalability.Timer;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.EventPatternImpl#getTimer <em>Timer</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EventPatternImpl extends EventImpl implements EventPattern {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.EVENT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Timer getTimer() {
		return (Timer)eGet(ScalabilityPackage.Literals.EVENT_PATTERN__TIMER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimer(Timer newTimer) {
		eSet(ScalabilityPackage.Literals.EVENT_PATTERN__TIMER, newTimer);
	}

	/**
	 * The cached invocation delegate for the '{@link #includesEvent(camel.scalability.SimpleEvent) <em>Includes Event</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #includesEvent(camel.scalability.SimpleEvent)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate INCLUDES_EVENT_SIMPLE_EVENT__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean includesEvent(SimpleEvent e) {
		try {
			return (Boolean)INCLUDES_EVENT_SIMPLE_EVENT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{e}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #includesLeftEvent(camel.scalability.SimpleEvent) <em>Includes Left Event</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #includesLeftEvent(camel.scalability.SimpleEvent)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate INCLUDES_LEFT_EVENT_SIMPLE_EVENT__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean includesLeftEvent(SimpleEvent e) {
		try {
			return (Boolean)INCLUDES_LEFT_EVENT_SIMPLE_EVENT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{e}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #includesRightEvent(camel.scalability.SimpleEvent) <em>Includes Right Event</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #includesRightEvent(camel.scalability.SimpleEvent)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate INCLUDES_RIGHT_EVENT_SIMPLE_EVENT__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean includesRightEvent(SimpleEvent e) {
		try {
			return (Boolean)INCLUDES_RIGHT_EVENT_SIMPLE_EVENT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{e}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #relatedToExecutionContext(camel.execution.ExecutionContext) <em>Related To Execution Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #relatedToExecutionContext(camel.execution.ExecutionContext)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate RELATED_TO_EXECUTION_CONTEXT_EXECUTION_CONTEXT__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean relatedToExecutionContext(ExecutionContext ec) {
		try {
			return (Boolean)RELATED_TO_EXECUTION_CONTEXT_EXECUTION_CONTEXT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{ec}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #leftRelatedToExecutionContext(camel.execution.ExecutionContext) <em>Left Related To Execution Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #leftRelatedToExecutionContext(camel.execution.ExecutionContext)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate LEFT_RELATED_TO_EXECUTION_CONTEXT_EXECUTION_CONTEXT__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean leftRelatedToExecutionContext(ExecutionContext ec) {
		try {
			return (Boolean)LEFT_RELATED_TO_EXECUTION_CONTEXT_EXECUTION_CONTEXT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{ec}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #rightRelatedToExecutionContext(camel.execution.ExecutionContext) <em>Right Related To Execution Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #rightRelatedToExecutionContext(camel.execution.ExecutionContext)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate RIGHT_RELATED_TO_EXECUTION_CONTEXT_EXECUTION_CONTEXT__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean rightRelatedToExecutionContext(ExecutionContext ec) {
		try {
			return (Boolean)RIGHT_RELATED_TO_EXECUTION_CONTEXT_EXECUTION_CONTEXT__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{ec}));
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
			case ScalabilityPackage.EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT:
				return includesEvent((SimpleEvent)arguments.get(0));
			case ScalabilityPackage.EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT:
				return includesLeftEvent((SimpleEvent)arguments.get(0));
			case ScalabilityPackage.EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT:
				return includesRightEvent((SimpleEvent)arguments.get(0));
			case ScalabilityPackage.EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT:
				return relatedToExecutionContext((ExecutionContext)arguments.get(0));
			case ScalabilityPackage.EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT:
				return leftRelatedToExecutionContext((ExecutionContext)arguments.get(0));
			case ScalabilityPackage.EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT:
				return rightRelatedToExecutionContext((ExecutionContext)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //EventPatternImpl
