package org.ow2.paasage.camel.srl.adapter.utils;

import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.KeyValue;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Frank on 19.07.2016.
 */
public class ExternalReferenceHelper {
    public static KeyValue getExternalReference(CDOObject o, String prefix){
        final String exId;
        final String exKey;
        final Method getName;

        try {
            getName = o.getClass().getMethod("getName");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Could not check for getName method of class. (1)");
        }

        if (o.cdoID() != null) {
            exKey = "CDOID";
            exId = o.cdoID().toString();
        } else {
            exKey = "CAMEL";
            try {
                if(prefix != null){
                        exId = prefix + "_" +  getName.invoke(o);
                } else {
                    exId = (String)getName.invoke(o); /* TODO if CDO is not available this ID might not by
                                                              TODO unique through different model instances */
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Could not check for getName method of class. (2)");
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException("Could not check for getName method of class. (3)");
            }
        }

        return new KeyValue(exKey, exId);
    }
}
