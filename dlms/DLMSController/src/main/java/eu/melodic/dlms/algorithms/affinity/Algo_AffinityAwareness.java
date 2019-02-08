package eu.melodic.dlms.algorithms.affinity;

import eu.melodic.dlms.db.model.AcDsKey;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceAffinityRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.DataSourceRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Algo_AffinityAwareness {
	protected final ApplicationComponentRepository acRepository;
	protected final DataSourceRepository dsRepository;
	protected final ApplicationComponentDataSourceDataRepository acDsDataRepository;
	protected final ApplicationComponentDataSourceAffinityRepository acDsAffinityRepository;

	// configuration parameter
	@Getter
	@Setter
	protected int paraNumRecords;
	@Getter
	@Setter
	private String functionName;

	// can be modified to tune the best parameter settings
	protected int MIN_RANGE = 0;
	protected int MAX_RANGE = 1;
	protected double WT_READ = 0.5;
	protected double WT_AI = 1;
	protected double WT_DATA_TRANSFER = 0.5;

	/**
	 * Compute affinity between all the application component and data sources
	 */

	public int computeAffinity() {
		switch (functionName) {
		case "average":
			CalculateAvgCouplet calculateAvgAffinity = new CalculateAvgCouplet(acRepository, dsRepository,
					acDsDataRepository, acDsAffinityRepository, paraNumRecords);
			calculateAvgAffinity.run();
			break;
		case "latestHigher":
			// TODO
			break;
		case "realTimePrediction":
			// TODO
			break;
		default:
			log.debug("Invalid function for affinity selected");
			return -1;
		}
		return 0;
	}

	/**
	 * Calculate the affinity between application component and datasources
	 */
	public double calculateAffinity(String from, String to) {
		double val = -1;
		// historical execution data exists between two components
		if (connectionExist(new AcDsKey(from, to)))
			val = getAffinity(new AcDsKey(from, to));
		// if connection exists the other way
		else if (connectionExist(new AcDsKey(to, from)))
			val = getAffinity(new AcDsKey(to, from));
		return val;
	}

	/**
	 * Check historical execution data between application component and datasource
	 */
	public boolean connectionExist(AcDsKey acDsKey) {
		return acDsAffinityRepository.existsByAcDsKey(acDsKey);
	}

	/**
	 * Get the affinity between an application component and a datasource
	 */
	public double getAffinity(AcDsKey acDsKey) {
		return acDsAffinityRepository.findByAcDsKey(acDsKey).getAffinity();
	}

}
