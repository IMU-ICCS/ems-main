package cp_wrapper.mockups;

import eu.paasage.upperware.metamodel.cp.NumericListDomain;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class NumericListDomainImplMockup implements NumericListDomain {
    private EList<NumericValueUpperware> values = new BasicEList<>();

    @Override
    public EList<NumericValueUpperware> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        for (int i = 0; i < values.size(); i++) {
            this.values.add(new NumericValueUpperwareImplMockup(values.get(i)));
        }
    }

    @Override
    public BasicTypeEnum getType() {
        return null;
    }

    @Override
    public void setType(BasicTypeEnum value) {

    }

    @Override
    public NumericValueUpperware getValue() {
        return null;
    }

    @Override
    public void setValue(NumericValueUpperware value) {

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
