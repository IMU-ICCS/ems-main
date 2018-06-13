package eu.paasage.mddb.cdo.client.exp;

import org.eclipse.emf.ecore.EObject;

public interface CDOClientX {

    CDOSessionX getSession();

    /* This method is used to export a model or instance of EObject in general into a XMI file.
     * The model/EObject must have been either created programmatically or obtained via
     * issuing a query. The method takes as input two parameters: (a) the query results
     * as an EObject to be exported, (b) the path of the file to be created.
     * Please note that this method should be called only when a respective CDO transaction
     * has been opened - otherwise an exception will be thrown. Any exception leads to the
     * return of a false value and the generation of a respective error entry in the log file;
     * otherwise, a value of true is returned.
     */

    boolean exportModel(EObject model, String filePath);
}
