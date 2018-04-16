package eu.paasage.upperware.profiler.generator.communication;

import eu.paasage.camel.CamelModel;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

public interface CdoService {

    CDOTransaction openTransaction();

    void closeTransaction(CDOTransaction tr);

    CamelModel getCamelModel(String name, CDOTransaction tr);

}
