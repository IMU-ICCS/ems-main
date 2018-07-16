package eu.paasage.mddb.cdo.client.exp;

import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public interface CDOSessionX {
    /* This method can be used to open a CDO transaction and return it to
     * the developer/user. The developer/user should not forget to close
     * the respective cdo transaction in the end.
     */
    CDOTransaction openTransaction();

    void closeTransaction(CDOTransaction tr);

    /* This method can be used to open a CDO view and return it to
     * the developer/user. The developer/user should not forget to close
     * the respective cdo view in the end.
     */
    CDOView openView();

    void storeModels(EObject model, String resourceName);

    void closeSession();

    CDOSession getSession();
}
