package eu.melodic.dlms.algorithms.affinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.algorithms.affinity.dbModel.DataSource;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

}
