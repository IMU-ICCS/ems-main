/*
 * Melodic EU Project
 * DLMS WebService
 * Data Source Repository
 * @author: ferox
 */


package e.melodic.upperware.dlms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e.melodic.upperware.dlms.DataSource;

@Repository("dataSourceRepository")
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

}
