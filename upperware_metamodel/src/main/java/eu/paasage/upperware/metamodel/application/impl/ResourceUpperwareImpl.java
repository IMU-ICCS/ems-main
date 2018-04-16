/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.ResourceUpperware;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ResourceUpperwareImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ResourceUpperwareImpl extends PaaSageCPElementImpl implements ResourceUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.RESOURCE_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueUpperware getValue() {
		return (NumericValueUpperware)eGet(ApplicationPackage.Literals.RESOURCE_UPPERWARE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValueUpperware newValue) {
		eSet(ApplicationPackage.Literals.RESOURCE_UPPERWARE__VALUE, newValue);
	}

} //ResourceUpperwareImpl
