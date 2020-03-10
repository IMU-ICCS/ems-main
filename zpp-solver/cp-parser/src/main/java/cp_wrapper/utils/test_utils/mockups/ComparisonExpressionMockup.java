package cp_wrapper.utils.test_utils.mockups;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.Expression;
import org.eclipse.emf.cdo.CDOLock;
import org.eclipse.emf.cdo.CDOObjectHistory;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.security.CDOPermission;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;

import java.lang.reflect.InvocationTargetException;

public class ComparisonExpressionMockup implements ComparisonExpression {
    Expression exp1;
    Expression exp2;
    ComparatorEnum comparator;
    @Override
    public Expression getExp1() {
        return exp1;
    }

    @Override
    public void setExp1(Expression value) {
        exp1 = value;
    }

    @Override
    public Expression getExp2() {
        return exp2;
    }

    @Override
    public void setExp2(Expression value) {
        exp2 = value;
    }

    @Override
    public ComparatorEnum getComparator() {
        return comparator;
    }

    @Override
    public void setComparator(ComparatorEnum value) {
        comparator = value;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String value) {

    }

    @Override
    public CDOID cdoID() {
        return null;
    }

    @Override
    public CDOState cdoState() {
        return null;
    }

    @Override
    public boolean cdoConflict() {
        return false;
    }

    @Override
    public boolean cdoInvalid() {
        return false;
    }

    @Override
    public CDOView cdoView() {
        return null;
    }

    @Override
    public CDORevision cdoRevision() {
        return null;
    }

    @Override
    public CDORevision cdoRevision(boolean b) {
        return null;
    }

    @Override
    public CDOPermission cdoPermission() {
        return null;
    }

    @Override
    public CDOResource cdoResource() {
        return null;
    }

    @Override
    public CDOResource cdoDirectResource() {
        return null;
    }

    @Override
    public CDOLock cdoReadLock() {
        return null;
    }

    @Override
    public CDOLock cdoWriteLock() {
        return null;
    }

    @Override
    public CDOLock cdoWriteOption() {
        return null;
    }

    @Override
    public CDOLockState cdoLockState() {
        return null;
    }

    @Override
    public void cdoPrefetch(int i) {

    }

    @Override
    public void cdoReload() {

    }

    @Override
    public CDOObjectHistory cdoHistory() {
        return null;
    }

    @Override
    public EClass eClass() {
        return null;
    }

    @Override
    public Resource eResource() {
        return null;
    }

    @Override
    public EObject eContainer() {
        return null;
    }

    @Override
    public EStructuralFeature eContainingFeature() {
        return null;
    }

    @Override
    public EReference eContainmentFeature() {
        return null;
    }

    @Override
    public EList<EObject> eContents() {
        return null;
    }

    @Override
    public TreeIterator<EObject> eAllContents() {
        return null;
    }

    @Override
    public boolean eIsProxy() {
        return false;
    }

    @Override
    public EList<EObject> eCrossReferences() {
        return null;
    }

    @Override
    public Object eGet(EStructuralFeature eStructuralFeature) {
        return null;
    }

    @Override
    public Object eGet(EStructuralFeature eStructuralFeature, boolean b) {
        return null;
    }

    @Override
    public void eSet(EStructuralFeature eStructuralFeature, Object o) {

    }

    @Override
    public boolean eIsSet(EStructuralFeature eStructuralFeature) {
        return false;
    }

    @Override
    public void eUnset(EStructuralFeature eStructuralFeature) {

    }

    @Override
    public Object eInvoke(EOperation eOperation, EList<?> eList) throws InvocationTargetException {
        return null;
    }

    @Override
    public EList<Adapter> eAdapters() {
        return null;
    }

    @Override
    public boolean eDeliver() {
        return false;
    }

    @Override
    public void eSetDeliver(boolean b) {

    }

    @Override
    public void eNotify(Notification notification) {

    }
}
