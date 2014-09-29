package eu.paasage.camel.examples;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;

import eu.paasage.camel.examples.model.SensAppModel;


public class SensAppCDO {

	public static void main(String[] args) {
		// initialize and activate a container
		final IManagedContainer container = ContainerUtil.createContainer();
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		// CDONet4jUtil.prepareContainer(container);
		container.activate();

		// create a Net4j TCP connector
		final IConnector connector = (IConnector) TCPUtil.getConnector(
				container, "localhost:2036");

		// create the session configuration
		CDONet4jSessionConfiguration config = CDONet4jUtil
				.createNet4jSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName("repo1");

		// create the actual session with the repository
		CDONet4jSession cdoSession = config.openNet4jSession();

		// obtain a transaction object
		CDOTransaction transaction = cdoSession.openTransaction();

		// create a CDO resource object
		CDOResource resource = transaction
				.getOrCreateResource("/sensAppResource");
		
		EObject camelModel = SensAppModel.createSensAppModel();
		try {
			resource.getContents().add(camelModel);
			transaction.commit();
		} catch (ConcurrentAccessException e) {
			e.printStackTrace();
		} catch (CommitException e) {
			e.printStackTrace();
		}
	}
}
