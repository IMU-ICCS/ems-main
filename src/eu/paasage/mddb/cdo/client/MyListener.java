package eu.paasage.mddb.cdo.client;

import java.util.List;

import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.session.CDOSessionInvalidationEvent;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;

public class MyListener implements IListener{
	private static org.apache.log4j.Logger logger;
	
	/* Listens to CDOSessionInvalidationEvent and reports new, updated & deleted objects
	 * in the context of any successful execution of any transaction
	 * @see org.eclipse.net4j.util.event.IListener#notifyEvent(org.eclipse.net4j.util.event.IEvent)
	 */
	
	static {
    	logger = org.apache.log4j.Logger.getLogger(MyListener.class);
	}
	
	public void notifyEvent(IEvent arg0) {
		// TODO Auto-generated method stub
		logger.info("EVENT: " + arg0);
		if (arg0 instanceof CDOSessionInvalidationEvent){
			CDOSessionInvalidationEvent e = (CDOSessionInvalidationEvent)arg0;
			List<CDOIDAndVersion> objs = e.getNewObjects();
			for (CDOIDAndVersion id: objs){
				logger.info("Got new object with id: " + id.getID());
			}
			objs = e.getDetachedObjects();
			for (CDOIDAndVersion id: objs){
				logger.info("Got deleted object with id: " + id.getID());
			}
			List<CDORevisionKey> modObjs = e.getChangedObjects();
			for (CDORevisionKey id: modObjs){
				logger.info("Got changed object with id: " + id.getID());
			}
	    }
	}
	
}

