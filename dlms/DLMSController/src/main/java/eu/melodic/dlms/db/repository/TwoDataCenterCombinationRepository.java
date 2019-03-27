package eu.melodic.dlms.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.melodic.dlms.db.model.TwoDCKey;
import eu.melodic.dlms.db.model.TwoDataCenterCombination;

@Repository
public interface TwoDataCenterCombinationRepository extends JpaRepository<TwoDataCenterCombination, Long> {
	List<TwoDataCenterCombination> findAll();
	boolean existsByTwoDCKey(TwoDCKey twodckey);
	TwoDataCenterCombination findByTwoDCKey(TwoDCKey twodckey);
}
