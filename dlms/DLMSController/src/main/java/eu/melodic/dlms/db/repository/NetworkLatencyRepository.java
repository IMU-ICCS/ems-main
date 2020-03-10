package eu.melodic.dlms.db.repository;

import eu.melodic.dlms.db.model.NetworkLatencyKey;
import eu.melodic.dlms.db.model.NetworkLatencyMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface NetworkLatencyRepository extends JpaRepository<NetworkLatencyMatrix, NetworkLatencyKey> {
    @Query(value = "SELECT min(networkLatencyMillis) FROM NetworkLatencyMatrix")
    BigDecimal minNetworkLatencyMillis();

    @Query(value = "SELECT max(networkLatencyMillis) FROM NetworkLatencyMatrix")
    BigDecimal maxNetworkLatencyMillis();
}
