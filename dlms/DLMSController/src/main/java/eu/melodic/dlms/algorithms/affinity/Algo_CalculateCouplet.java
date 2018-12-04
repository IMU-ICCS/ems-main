package eu.melodic.dlms.algorithms.affinity;

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
public class Algo_CalculateCouplet {
	protected final ApplicationComponentRepository acRepository;
	protected final DataSourceRepository dsRepository;
	protected final ApplicationComponentDataSourceDataRepository acDsDataRepository;
	protected final ApplicationComponentDataSourceAffinityRepository acDsAffinityRepository;

	// configuration parameter
	@Getter	@Setter
	protected int paraNumRecords;
	@Getter	@Setter
	private String functionName;

	// can be modified to tune the best parameter settings
	protected int MIN_RANGE = 0;
	protected int MAX_RANGE = 1;
	protected double WT_READ = 0.5;
	protected double WT_AI = 1;
	protected double WT_DATA_TRANSFER = 0.5;

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

}
