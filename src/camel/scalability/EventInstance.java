/**
 */
package camel.scalability;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.EventInstance#getStatus <em>Status</em>}</li>
 *   <li>{@link camel.scalability.EventInstance#getLayer <em>Layer</em>}</li>
 *   <li>{@link camel.scalability.EventInstance#getOnEvent <em>On Event</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getEventInstance()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Event_Instance_Same_Layer_of_Metric_As_In_Event'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Event_Instance_Same_Layer_of_Metric_As_In_Event='\n\t\t\t\tif (self.onEvent.oclIsTypeOf(NonFunctionalEvent)) then self.equalLayer(self.layer,self.onEvent.oclAsType(NonFunctionalEvent).condition.metric.hasTemplate.layer) else true endif'"
 * @extends CDOObject
 * @generated
 */
public interface EventInstance extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.StatusType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see camel.scalability.StatusType
	 * @see #setStatus(StatusType)
	 * @see camel.scalability.ScalabilityPackage#getEventInstance_Status()
	 * @model required="true"
	 * @generated
	 */
	StatusType getStatus();

	/**
	 * Sets the value of the '{@link camel.scalability.EventInstance#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see camel.scalability.StatusType
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(StatusType value);

	/**
	 * Returns the value of the '<em><b>Layer</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.LayerType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layer</em>' attribute.
	 * @see camel.scalability.LayerType
	 * @see #setLayer(LayerType)
	 * @see camel.scalability.ScalabilityPackage#getEventInstance_Layer()
	 * @model
	 * @generated
	 */
	LayerType getLayer();

	/**
	 * Sets the value of the '{@link camel.scalability.EventInstance#getLayer <em>Layer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layer</em>' attribute.
	 * @see camel.scalability.LayerType
	 * @see #getLayer()
	 * @generated
	 */
	void setLayer(LayerType value);

	/**
	 * Returns the value of the '<em><b>On Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Event</em>' reference.
	 * @see #setOnEvent(SimpleEvent)
	 * @see camel.scalability.ScalabilityPackage#getEventInstance_OnEvent()
	 * @model required="true"
	 * @generated
	 */
	SimpleEvent getOnEvent();

	/**
	 * Sets the value of the '{@link camel.scalability.EventInstance#getOnEvent <em>On Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Event</em>' reference.
	 * @see #getOnEvent()
	 * @generated
	 */
	void setOnEvent(SimpleEvent value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * attribute id : String { id };
	 * <!-- end-model-doc -->
	 * @model l1Required="true" l2Required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='if (l1 = LayerType::SaaS) then (if (l2 = LayerType::SaaS) then true else false endif) \n\t\t\t\t\t\telse\n\t\t\t\t\t\t\t(if (l1 = LayerType::PaaS) then \n\t\t\t\t\t\t\t\t(if (l2 = LayerType::PaaS) then true else false endif)\n\t\t\t\t\t\t\t\telse (if (l2 = LayerType::IaaS) then true else false endif)\n\t\t\t\t\t\t\t\tendif)\n\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean equalLayer(LayerType l1, LayerType l2);

} // EventInstance
