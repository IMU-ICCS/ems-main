package eu.paasage.upperware.profiler.generator.communication;

import eu.paasage.camel.CamelModel;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

public interface CdoService {

//    CDOTransaction openTransaction();
//
//    void closeTransaction(CDOTransaction tr);

    CamelModel getCamelModel(String name, CDOTransaction tr);

    CDOSessionX openSession();

    void saveModels(PaasageConfiguration pc, ConstraintProblem cp, CDOSessionX cdoSessionX);
}
