/**
 */
package camel.organisation.impl;

import camel.Action;

import camel.organisation.OrganisationPackage;
import camel.organisation.Resource;
import camel.organisation.ResourceGroup;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.ResourceGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.organisation.impl.ResourceGroupImpl#getContainsResource <em>Contains Resource</em>}</li>
 *   <li>{@link camel.organisation.impl.ResourceGroupImpl#getLevel <em>Level</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceGroupImpl extends ResourceImpl implements ResourceGroup {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.RESOURCE_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(OrganisationPackage.Literals.RESOURCE_GROUP__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(OrganisationPackage.Literals.RESOURCE_GROUP__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Resource> getContainsResource() {
		return (EList<Resource>)eGet(OrganisationPackage.Literals.RESOURCE_GROUP__CONTAINS_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLevel() {
		return (Integer)eGet(OrganisationPackage.Literals.RESOURCE_GROUP__LEVEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLevel(int newLevel) {
		eSet(OrganisationPackage.Literals.RESOURCE_GROUP__LEVEL, newLevel);
	}

	/**
	 * The cached invocation delegate for the '{@link #allowsActionsOnResources(org.eclipse.emf.common.util.EList) <em>Allows Actions On Resources</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #allowsActionsOnResources(org.eclipse.emf.common.util.EList)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate ALLOWS_ACTIONS_ON_RESOURCES_ELIST__EINVOCATION_DELEGATE = ((EOperation.Internal)OrganisationPackage.Literals.RESOURCE_GROUP___ALLOWS_ACTIONS_ON_RESOURCES__ELIST).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean allowsActionsOnResources(EList<Action> actions) {
		try {
			return (Boolean)ALLOWS_ACTIONS_ON_RESOURCES_ELIST__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{actions}));
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
	public boolean checkRecursiveness(final ResourceGroup rg, final Resource resource) {
		System.out.println("Checking recursiveness for ResourceGroup: " + rg);
				ResourceGroup rgCheck = (ResourceGroup)resource;
				for (Resource res: rg.getContainsResource()){
					if (res instanceof ResourceGroup){
						ResourceGroup rg2 = (ResourceGroup)res;
						if (rg2.getName().equals(rgCheck.getName())) return Boolean.TRUE;
						if (checkRecursiveness(rg2,resource)) return Boolean.TRUE;
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
			case OrganisationPackage.RESOURCE_GROUP___ALLOWS_ACTIONS_ON_RESOURCES__ELIST:
				return allowsActionsOnResources((EList<Action>)arguments.get(0));
			case OrganisationPackage.RESOURCE_GROUP___CHECK_RECURSIVENESS__RESOURCEGROUP_RESOURCE:
				return checkRecursiveness((ResourceGroup)arguments.get(0), (Resource)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ResourceGroupImpl
