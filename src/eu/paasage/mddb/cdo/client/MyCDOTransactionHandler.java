package eu.paasage.mddb.cdo.client;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOCommitContext;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.ecore.EObject;

public class MyCDOTransactionHandler implements org.eclipse.emf.cdo.transaction.CDOTransactionHandler{
	
	public MyCDOTransactionHandler(){
	}
	
	public void attachingObject(CDOTransaction transaction, CDOObject object) {
		// TODO Auto-generated method stub
		
	}

	public void detachingObject(CDOTransaction transaction, CDOObject object) {
		// TODO Auto-generated method stub
		
	}

	public void modifyingObject(CDOTransaction transaction, CDOObject object,
			CDOFeatureDelta featureDelta) {
		// TODO Auto-generated method stub
		
	}

	public void committingTransaction(CDOTransaction transaction,
			CDOCommitContext commitContext) {
		// TODO Auto-generated method stub
		for (EObject obj: commitContext.getNewObjects().values()){
			if (!(obj instanceof CDOResource) && !OCLValidation.validate(obj)) throw new RuntimeException();
		}
		
		for (EObject obj: commitContext.getDirtyObjects().values()){
			System.out.println("Dirty object: " + obj);
			if (!(obj instanceof CDOResource) && !OCLValidation.validate(obj)) throw new RuntimeException();
		}
	}

	public void committedTransaction(CDOTransaction transaction,
			CDOCommitContext commitContext) {
	}

	public void rolledBackTransaction(CDOTransaction transaction) {
		// TODO Auto-generated method stub
		
	}

}
