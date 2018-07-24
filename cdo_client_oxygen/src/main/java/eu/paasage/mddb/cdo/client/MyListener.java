/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.client;

import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.session.CDOSessionInvalidationEvent;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;

import java.util.List;

public class MyListener implements IListener{
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyListener.class);
	
	/* Listens to CDOSessionInvalidationEvent and reports new, updated & deleted objects
	 * in the context of any successful execution of any transaction
	 * @see org.eclipse.net4j.util.event.IListener#notifyEvent(org.eclipse.net4j.util.event.IEvent)
	 */
	
	public void notifyEvent(IEvent arg0) {
		// TODO Auto-generated method stub
		log.info("EVENT: {}", arg0);
		if (arg0 instanceof CDOSessionInvalidationEvent){
			CDOSessionInvalidationEvent e = (CDOSessionInvalidationEvent)arg0;
			List<CDOIDAndVersion> objs = e.getNewObjects();
			for (CDOIDAndVersion id: objs){
				log.info("Got new object with id: {}", id.getID());
			}
			objs = e.getDetachedObjects();
			for (CDOIDAndVersion id: objs){
				log.info("Got deleted object with id: {}", id.getID());
			}
			List<CDORevisionKey> modObjs = e.getChangedObjects();
			for (CDORevisionKey id: modObjs){
				log.info("Got changed object with id: {}",  id.getID());
			}
	    }
	}
	
}

