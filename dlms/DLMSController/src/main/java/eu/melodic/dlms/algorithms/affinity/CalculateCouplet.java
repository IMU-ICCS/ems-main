package eu.melodic.dlms.algorithms.affinity;

import java.util.ArrayList;
import java.util.List;

import eu.melodic.dlms.db.model.AcDsKey;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceAffinity;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceAffinityRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.ControllerDataSourceRepository;

public abstract class CalculateCouplet extends Algo_AffinityAwareness{
	protected long minDataTransPrediction = Long.MAX_VALUE;
	protected long maxDataTransPrediction = 0;
	protected long minNumTransPrediction = Long.MAX_VALUE;
	protected long maxNumTransPrediction = 0;
	
	public CalculateCouplet(ApplicationComponentRepository acRepository, ControllerDataSourceRepository dsRepository,
			ApplicationComponentDataSourceDataRepository acDSDataRepository,
			ApplicationComponentDataSourceAffinityRepository acDsAffinityRepository, int paraNumRecords) {
		super(acRepository, dsRepository, acDSDataRepository, acDsAffinityRepository);
		this.paraNumRecords = paraNumRecords;
	}
	
	/**
	 * Save the affinities
	 */
	protected void saveAffinity(List<AppComDataSrc> appComDataSrcList) {
		List<ApplicationComponentDataSourceAffinity> acDsAffinityList = new ArrayList<>();
		for (AppComDataSrc appComDataSrc : appComDataSrcList) {
			ApplicationComponentDataSourceAffinity acDsAffinity = new ApplicationComponentDataSourceAffinity(
					new AcDsKey(appComDataSrc.getAppCompId(), appComDataSrc.getDataSrcId()),
					appComDataSrc.getCoupletValue());
			acDsAffinityList.add(acDsAffinity);			
		}
		acDsAffinityRepository.saveAll(acDsAffinityList);
	}
	
	protected abstract List<AppComDataSrc> computePrediction();

	/**
	 * Compute the couplet value
	 */
	protected void calculateCouple(List<AppComDataSrc> appComDataSrcList) {
		for (AppComDataSrc appComDataSrc : appComDataSrcList) {
			double norDataTransfer = normalizeDataTransfer(appComDataSrc.getExpDataTransfer());
			appComDataSrc.setNormExpDataTransfer(norDataTransfer);

			double norNumTransfer = normalizeNumTransfer(appComDataSrc.getNumTransfer());
			appComDataSrc.setNormNumTransfer(norNumTransfer);

			// final couple value
			double coupletVal = calculateCouplet(appComDataSrc.getNormExpDataTransfer(),
					appComDataSrc.getNormNumTransfer());
			appComDataSrc.setCoupletValue(coupletVal);
		}
	}

	/**
	 * Normalize the value for data transfer
	 */
	protected double normalizeDataTransfer(long val) {
		double retVal = (val - this.minDataTransPrediction)
				* ((MAX_RANGE - MIN_RANGE) / (double) (this.maxDataTransPrediction - this.minDataTransPrediction))
				+ MIN_RANGE;
		return retVal;
	}

	/**
	 * Normalize the value for number of transfer
	 */
	protected double normalizeNumTransfer(long val) {
		double retVal = (val - this.minNumTransPrediction)
				* ((MAX_RANGE - MIN_RANGE) / (double) (this.maxNumTransPrediction - this.minNumTransPrediction))
				+ MIN_RANGE;
		return retVal;
	}

	/**
	 * Calculate the final couplet value
	 */
	protected double calculateCouplet(double dataTransferVal, double numTransferVal) {
		double retVal = 0;
		retVal = WT_DATA_TRANSFER * dataTransferVal + (1 - WT_DATA_TRANSFER) * numTransferVal;
		return retVal;
	}

	
}
