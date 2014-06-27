/**
 */
package camel.impl;

import camel.Application;
import camel.CamelPackage;
import camel.Requirement;
import camel.RequirementGroup;
import camel.RequirementOperatorType;

import camel.organisation.User;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirement Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.RequirementGroupImpl#getPosedBy <em>Posed By</em>}</li>
 *   <li>{@link camel.impl.RequirementGroupImpl#getRequirement <em>Requirement</em>}</li>
 *   <li>{@link camel.impl.RequirementGroupImpl#getOnApplication <em>On Application</em>}</li>
 *   <li>{@link camel.impl.RequirementGroupImpl#getRequirementOperator <em>Requirement Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequirementGroupImpl extends RequirementImpl implements RequirementGroup {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequirementGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.REQUIREMENT_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User getPosedBy() {
		return (User)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__POSED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPosedBy(User newPosedBy) {
		eSet(CamelPackage.Literals.REQUIREMENT_GROUP__POSED_BY, newPosedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Requirement> getRequirement() {
		return (EList<Requirement>)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__REQUIREMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Application> getOnApplication() {
		return (EList<Application>)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__ON_APPLICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementOperatorType getRequirementOperator() {
		return (RequirementOperatorType)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__REQUIREMENT_OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequirementOperator(RequirementOperatorType newRequirementOperator) {
		eSet(CamelPackage.Literals.REQUIREMENT_GROUP__REQUIREMENT_OPERATOR, newRequirementOperator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkRecursiveness(final RequirementGroup rg1, final Requirement r, final boolean resources, final EList<RequirementGroup> context) {
		System.out.println("Checking recursiveness for RequirementGroup: " + rg1);
				for (Requirement r2: rg1.getRequirement()){
					EList<RequirementGroup> context2 = null;
					if (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<RequirementGroup>();
					else context2 = new org.eclipse.emf.common.util.BasicEList<RequirementGroup>(context);
					if (!resources){
						if (r2 instanceof RequirementGroup){
							RequirementGroup rg2 = (RequirementGroup)r2;
							if (context == null || !context.contains(rg2)){
								context2.add(rg2);
								if (rg2.getId().equals(r.getId())) return Boolean.TRUE;
								if (checkRecursiveness(rg2,r,resources,context2)) return Boolean.TRUE;
							}
						}
					}
					else{
						if (r.getId().equals(r2.getId())) return true;
						if (r2 instanceof RequirementGroup){
							RequirementGroup rg2 = (RequirementGroup)r2;
							if (context == null || !context.contains(rg2)){
								context2.add(rg2);
								if (checkRecursiveness(rg2,r,resources,context2)) return Boolean.TRUE;
							}
						}
					}
				}
				return Boolean.FALSE;
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
			case CamelPackage.REQUIREMENT_GROUP___CHECK_RECURSIVENESS__REQUIREMENTGROUP_REQUIREMENT_BOOLEAN_ELIST:
				return checkRecursiveness((RequirementGroup)arguments.get(0), (Requirement)arguments.get(1), (Boolean)arguments.get(2), (EList<RequirementGroup>)arguments.get(3));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RequirementGroupImpl
