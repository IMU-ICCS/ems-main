/**
 */
package camel.scalability.impl;

import camel.scalability.EventInstance;
import camel.scalability.LayerType;
import camel.scalability.ScalabilityPackage;
import camel.scalability.SimpleEvent;
import camel.scalability.StatusType;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.EventInstanceImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link camel.scalability.impl.EventInstanceImpl#getLayer <em>Layer</em>}</li>
 *   <li>{@link camel.scalability.impl.EventInstanceImpl#getOnEvent <em>On Event</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventInstanceImpl extends CDOObjectImpl implements EventInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.EVENT_INSTANCE;
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
	public StatusType getStatus() {
		return (StatusType)eGet(ScalabilityPackage.Literals.EVENT_INSTANCE__STATUS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType newStatus) {
		eSet(ScalabilityPackage.Literals.EVENT_INSTANCE__STATUS, newStatus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LayerType getLayer() {
		return (LayerType)eGet(ScalabilityPackage.Literals.EVENT_INSTANCE__LAYER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayer(LayerType newLayer) {
		eSet(ScalabilityPackage.Literals.EVENT_INSTANCE__LAYER, newLayer);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleEvent getOnEvent() {
		return (SimpleEvent)eGet(ScalabilityPackage.Literals.EVENT_INSTANCE__ON_EVENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnEvent(SimpleEvent newOnEvent) {
		eSet(ScalabilityPackage.Literals.EVENT_INSTANCE__ON_EVENT, newOnEvent);
	}

	/**
	 * The cached invocation delegate for the '{@link #equalLayer(camel.scalability.LayerType, camel.scalability.LayerType) <em>Equal Layer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #equalLayer(camel.scalability.LayerType, camel.scalability.LayerType)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate EQUAL_LAYER_LAYER_TYPE_LAYER_TYPE__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.EVENT_INSTANCE___EQUAL_LAYER__LAYERTYPE_LAYERTYPE).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean equalLayer(LayerType l1, LayerType l2) {
		try {
			return (Boolean)EQUAL_LAYER_LAYER_TYPE_LAYER_TYPE__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(2, new Object[]{l1, l2}));
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
			case ScalabilityPackage.EVENT_INSTANCE___EQUAL_LAYER__LAYERTYPE_LAYERTYPE:
				return equalLayer((LayerType)arguments.get(0), (LayerType)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //EventInstanceImpl
