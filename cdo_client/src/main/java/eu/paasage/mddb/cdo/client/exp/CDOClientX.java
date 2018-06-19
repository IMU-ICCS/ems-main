package eu.paasage.mddb.cdo.client.exp;

import org.eclipse.emf.ecore.EObject;

public interface CDOClientX {

    /* This method is used to load a model from a particular xmi resource. The model
     * can then be stored to the CDO Server/Repository. The method takes as input
     * the path (as a String) where the XML file resides.
     */
    EObject loadModel(String pathName);

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

    /* This method is used to save a model into the file system in a specific path given as input
     * The input parameters are: the model to store and the file path to store it in the file system.
     * The output indicates whether the model saving was successful or not. The log file must be
     * inspected in the latter negative case.
     */
    boolean saveModel(EObject model, String pathName);
}
