package cp_wrapper.utils.numeric_value_impl;

import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class LongValue implements NumericValue, IntValueInterface{
    long value;

    public long getValue() {
        return value;
    }

    public int getIntValue() { return (int) value;}

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(NumericValue value) {
        if (value instanceof LongValue) {
            return this.value == ((LongValue) value).getValue();
        } else if (value instanceof IntegerValue) {
            return this.value == (long) ((IntegerValue) value).getValue();
        }
        return false;
    }
}
