/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.util;

import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
@Slf4j
public abstract class AbstractCdoHelper {

    protected static int counter = 0;
    protected final HashSet<String> LOCKS = new HashSet<>();
    protected int id;
    protected CDOClientXImpl cdoClient;

    public AbstractCdoHelper() {
        id = ++counter;
        this.cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));
        log.debug("AbstractCdoHelper.<init>():  ** NEW HELPER INSTANCE #{} **", id);
    }

    protected void lockObject(String objectId, String caller) throws ConcurrentAccessException {
        synchronized (LOCKS) {
            if (!LOCKS.contains(objectId)) {
                LOCKS.add(objectId);
            } else {
                throw new ConcurrentAccessException(caller + "->lockObject: Resource is locked: " + objectId);
                //return null;
            }
        }
        log.debug("{}->lockObject(): ACQUIRED LOCK ON: helper-id={}, object-id={}", caller, id, objectId);
    }

    protected void releaseObject(String objectId, String caller) {
        synchronized (LOCKS) {
            LOCKS.remove(objectId);
        }
        log.debug("{}->releaseObject(): RELEASED LOCK ON: helper-id={}, cp-path={}", caller, id, objectId);
    }

    public void processInTransaction(String lockName, Processor processor) throws ConcurrentAccessException {
        // lock resource
        String callerName = lockName;
        lockObject(lockName, callerName);

        CDOSessionX session = null;
        CDOTransaction transaction = null;
        try {
            // open transaction
            this.cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));
            session = cdoClient.getSession();
            transaction = session.openTransaction();

            // call model processor passing transaction as argument
            processor.process(transaction);

            // commit changes
            transaction.commit();
            transaction = null;

        } catch (Exception ex) {
            log.error("{}: EXCEPTION: helper-id={}, Exception={}", callerName, id, ex);
        } finally {
            if (transaction != null) {
                transaction.rollback();
                transaction.close();
            }

            // release resource
            releaseObject(lockName, callerName);
        }
    }

    @FunctionalInterface
    public static interface Processor {
        public void process(CDOTransaction transaction);
    }
}
