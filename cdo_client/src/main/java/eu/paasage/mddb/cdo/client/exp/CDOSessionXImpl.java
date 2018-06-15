package eu.paasage.mddb.cdo.client.exp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class CDOSessionXImpl implements CDOSessionX {

    private CDOSession session;
    private boolean logging;

    /* This method can be used to open a CDO transaction and return it to
     * the developer/user. The developer/user should not forget to close
     * the respective cdo transaction in the end.
     */
    @Override
    public CDOTransaction openTransaction(){
        CDOTransaction trans = session.openTransaction();
        if (logging) log.info("Opened transaction!");
        return trans;
    }

    @Override
    public void closeTransaction(CDOTransaction tr) {
        if (tr != null) {
            if (logging) log.info("Transaction is not null");
            if (tr.isClosed()) {
                if (logging) log.info("Closing transaction");
                tr.close();
                if (logging) log.info("Transaction has been closed!");
            } else {
                if (logging) log.info("Transaction is already closed");
            }
        } else {
            if (logging) log.info("Transaction is null");
        }
    }

    /* This method can be used to open a CDO view and return it to
     * the developer/user. The developer/user should not forget to close
     * the respective cdo view in the end.
     */
    @Override
    public CDOView openView(){
        CDOView view = session.openView();
        if (logging) log.info("Opened view!");
        return view;
    }

    @Override
    public void storeModels(List<EObject> models, String resourceName) {
        CDOTransaction trans = openTransaction();
        CDOResource cdo = trans.getOrCreateResource(resourceName);
        EList<EObject> list = cdo.getContents();

        list.addAll(models);

        try {
            trans.commit();
            trans.close();
        } catch (Exception e) {
            log.error("Problem during saving {} models under path: {}", models.size(), resourceName, e);
        }
    }

    @Override
    public void closeSession(){
        if (session != null && !session.isClosed()){
            session.close();
        }
    }

}
