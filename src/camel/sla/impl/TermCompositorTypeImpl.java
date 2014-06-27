/**
 */
package camel.sla.impl;

import camel.sla.GuaranteeTermType;
import camel.sla.SlaPackage;
import camel.sla.TermCompositorType;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Term Compositor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.TermCompositorTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link camel.sla.impl.TermCompositorTypeImpl#getExactlyOne <em>Exactly One</em>}</li>
 *   <li>{@link camel.sla.impl.TermCompositorTypeImpl#getOneOrMore <em>One Or More</em>}</li>
 *   <li>{@link camel.sla.impl.TermCompositorTypeImpl#getAll <em>All</em>}</li>
 *   <li>{@link camel.sla.impl.TermCompositorTypeImpl#getServiceReference <em>Service Reference</em>}</li>
 *   <li>{@link camel.sla.impl.TermCompositorTypeImpl#getGuaranteeTerm <em>Guarantee Term</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TermCompositorTypeImpl extends CDOObjectImpl implements TermCompositorType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TermCompositorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.TERM_COMPOSITOR_TYPE;
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
	public String getId() {
		return (String)eGet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<TermCompositorType> getExactlyOne() {
		return (EList<TermCompositorType>)eGet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__EXACTLY_ONE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<TermCompositorType> getOneOrMore() {
		return (EList<TermCompositorType>)eGet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__ONE_OR_MORE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<TermCompositorType> getAll() {
		return (EList<TermCompositorType>)eGet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__ALL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EObject> getServiceReference() {
		return (EList<EObject>)eGet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__SERVICE_REFERENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<GuaranteeTermType> getGuaranteeTerm() {
		return (EList<GuaranteeTermType>)eGet(SlaPackage.Literals.TERM_COMPOSITOR_TYPE__GUARANTEE_TERM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkRecursiveness(final TermCompositorType tct1, final TermCompositorType tct2, final EList<TermCompositorType> context) {
		System.out.println("Checking recursiveness for TermCompositorType: " + tct1.getId());
				for (TermCompositorType tct: tct1.getAll()){
						System.out.println("Checking candidate: " + tct.getId());
						EList<TermCompositorType> context2 = null;
						if (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>();
						else context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>(context); 
						context2.add(tct);
						if ((context == null || (context != null && !context.contains(tct))) && (tct.getId().equals(tct2.getId()) || checkRecursiveness(tct,tct2,context2))){
							System.out.println("FOUND IN ALL");
							return Boolean.TRUE;
						}
				}
				for (TermCompositorType tct: tct1.getOneOrMore()){
					System.out.println("Checking candidate: " + tct.getId());
					EList<TermCompositorType> context2 = null;
					if (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>();
					else context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>(context); 
					context2.add(tct);
					if ((context == null || (context != null && !context.contains(tct))) && (tct.getId().equals(tct2.getId()) || checkRecursiveness(tct,tct2,context2))){
						System.out.println("FOUND IN ALL");
						return Boolean.TRUE;
					}
				}
				for (TermCompositorType tct: tct1.getExactlyOne()){
					System.out.println("Checking candidate: " + tct.getId());
					EList<TermCompositorType> context2 = null;
					if (context == null) context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>();
					else context2 = new org.eclipse.emf.common.util.BasicEList<TermCompositorType>(context); 
					context2.add(tct);
					if ((context == null || (context != null && !context.contains(tct))) && (tct.getId().equals(tct2.getId()) || checkRecursiveness(tct,tct2,context2))){
						System.out.println("FOUND IN ALL");
						return Boolean.TRUE;
					}
				}
				System.out.println("Did not find any recursive matching for TermCompositorType: " + tct1.getId());
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
			case SlaPackage.TERM_COMPOSITOR_TYPE___CHECK_RECURSIVENESS__TERMCOMPOSITORTYPE_TERMCOMPOSITORTYPE_ELIST:
				return checkRecursiveness((TermCompositorType)arguments.get(0), (TermCompositorType)arguments.get(1), (EList<TermCompositorType>)arguments.get(2));
		}
		return super.eInvoke(operationID, arguments);
	}

} //TermCompositorTypeImpl
