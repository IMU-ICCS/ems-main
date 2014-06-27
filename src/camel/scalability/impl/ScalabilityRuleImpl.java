/**
 */
package camel.scalability.impl;

import camel.Action;

import camel.organisation.Entity;

import camel.scalability.Event;
import camel.scalability.ScalabilityPackage;
import camel.scalability.ScalabilityPolicy;
import camel.scalability.ScalabilityRule;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.ScalabilityRuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityRuleImpl#getRelatedEvent <em>Related Event</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityRuleImpl#getMapsToActions <em>Maps To Actions</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityRuleImpl#getDefinedBy <em>Defined By</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityRuleImpl#getInvariantPolicies <em>Invariant Policies</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScalabilityRuleImpl extends CDOObjectImpl implements ScalabilityRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScalabilityRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.SCALABILITY_RULE;
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
	public String getName() {
		return (String)eGet(ScalabilityPackage.Literals.SCALABILITY_RULE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(ScalabilityPackage.Literals.SCALABILITY_RULE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event getRelatedEvent() {
		return (Event)eGet(ScalabilityPackage.Literals.SCALABILITY_RULE__RELATED_EVENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedEvent(Event newRelatedEvent) {
		eSet(ScalabilityPackage.Literals.SCALABILITY_RULE__RELATED_EVENT, newRelatedEvent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Action> getMapsToActions() {
		return (EList<Action>)eGet(ScalabilityPackage.Literals.SCALABILITY_RULE__MAPS_TO_ACTIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Entity> getDefinedBy() {
		return (EList<Entity>)eGet(ScalabilityPackage.Literals.SCALABILITY_RULE__DEFINED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ScalabilityPolicy> getInvariantPolicies() {
		return (EList<ScalabilityPolicy>)eGet(ScalabilityPackage.Literals.SCALABILITY_RULE__INVARIANT_POLICIES, true);
	}

} //ScalabilityRuleImpl
