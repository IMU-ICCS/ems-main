/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Interface
 * @author: ferox
 */

package e.melodic.upperware.dlms;

import java.net.URI;
import java.util.List;

import e.melodic.upperware.dlms.DataSource;

public interface DLMSService {
	DataSource getDataSourceById(long id);
	List<DataSource> getAllDataSources();
	void deleteById(long id);
	public URI addDataSource(DataSource ds);
}
