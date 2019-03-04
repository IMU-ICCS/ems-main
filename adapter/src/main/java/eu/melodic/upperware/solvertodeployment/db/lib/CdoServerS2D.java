package eu.melodic.upperware.solvertodeployment.db.lib;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;

public interface CdoServerS2D {

    int saveNewDeploymentInstanceModel(CDOTransaction transaction, String camelModelID) throws CommitException;
}
