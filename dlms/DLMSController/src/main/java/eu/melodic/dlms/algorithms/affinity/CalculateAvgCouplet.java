package eu.melodic.dlms.algorithms.affinity;

import java.util.ArrayList;
import java.util.List;

import eu.melodic.dlms.db.model.AcDsKey;
import eu.melodic.dlms.db.model.ApplicationComponent;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceAffinity;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceData;
import eu.melodic.dlms.db.model.DataSource;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceAffinityRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.DataSourceRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Algorithm logic based on deliverable D2.5
 */
@Slf4j
public class CalculateAvgCouplet extends Algo_CalculateCouplet {
	private long minDataTransPrediction = Long.MAX_VALUE;
	private long maxDataTransPrediction = 0;
	private long minNumTransPrediction = Long.MAX_VALUE;
	private long maxNumTransPrediction = 0;

	public CalculateAvgCouplet(ApplicationComponentRepository acRepository, DataSourceRepository dsRepository,
			ApplicationComponentDataSourceDataRepository acDSDataRepository,
			ApplicationComponentDataSourceAffinityRepository acDsAffinityRepository, int paraNumRecords) {
		super(acRepository, dsRepository, acDSDataRepository, acDsAffinityRepository);
		this.paraNumRecords = paraNumRecords;
	}

	/**
	 * Starting method
	 */
	public void run() {
		// first compute prediction
		List<AppComDataSrc> appComDataSrcList = computePrediction();
		// calculate the couple value
		calculateCouple(appComDataSrcList);
		saveAffinity(appComDataSrcList);
		log.info("Algo_CalculateAffinity has successfully executed");
	}

	/**
	 * Save the affinities
	 */
	public void saveAffinity(List<AppComDataSrc> appComDataSrcList) {
		List<ApplicationComponentDataSourceAffinity> acDsAffinityList = new ArrayList<>();
		for (AppComDataSrc appComDataSrc : appComDataSrcList) {
			ApplicationComponentDataSourceAffinity acDsAffinity = new ApplicationComponentDataSourceAffinity(
					new AcDsKey(appComDataSrc.getAppCompId(), appComDataSrc.getDataSrcId()),
					appComDataSrc.getCoupletValue());
			acDsAffinityList.add(acDsAffinity);			
		}
		acDsAffinityRepository.saveAll(acDsAffinityList);
	}

	/**
	 * Prediction of data transfer
	 */
	public List<AppComDataSrc> computePrediction() {
		List<ApplicationComponent> acList = acRepository.findAll();
		List<DataSource> dsList = dsRepository.findAll();

		List<AppComDataSrc> appComDataSrcList = new ArrayList<>();

		for (ApplicationComponent acListItem : acList) {
			for (DataSource dsListItem : dsList) {
				List<ApplicationComponentDataSourceData> acDSDataList = acDsDataRepository
						.findByAppCompIdAndDataSourceIdDataHigherZero(acListItem.getId(), dsListItem.getId(),
								this.paraNumRecords);
				if (acDSDataList.size() > 0) {
					long predVal = dataPrediction(acDSDataList);
					long predNumTransfer = numTransferPrediction(acDSDataList);
					AppComDataSrc appComDataSrc = new AppComDataSrc(acListItem.getId(), dsListItem.getId(), predVal,
							predNumTransfer);

					appComDataSrcList.add(appComDataSrc);
				}
			}
		}
		return appComDataSrcList;
	}

	/**
	 * Calculate the sum of data transfers
	 */
	public long dataPrediction(List<ApplicationComponentDataSourceData> acDSDataList) {
		long predVal = (long) (WT_READ * sumRecordsDataReadEqWt(acDSDataList))
				+ (long) ((1 - WT_READ) * sumRecordsDataWriteEqWt(acDSDataList));
		this.maxDataTransPrediction = this.maxDataTransPrediction > predVal ? this.maxDataTransPrediction : predVal;
		this.minDataTransPrediction = this.minDataTransPrediction < predVal ? this.minDataTransPrediction : predVal;
		return predVal;
	}

	/**
	 * Sum records higher than 0 for data read
	 */
	public long sumRecordsDataReadEqWt(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = acDSDataList.stream()
				.filter(dataRead->dataRead.getDataRead()!=null && dataRead.getDataRead()>0)
				.map(ApplicationComponentDataSourceData::getDataRead)
				.mapToLong(val -> WT_AI * val)
				.sum();
		return retTotal;
	}

	/**
	 * Sum records higher than 0 for data write
	 */
	public long sumRecordsDataWriteEqWt(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = acDSDataList.stream()
				.filter(dataWrite->dataWrite.getDataWrite()!=null && dataWrite.getDataWrite()>0)
				.map(ApplicationComponentDataSourceData::getDataWrite)
				.mapToLong(dataWrite -> WT_AI * dataWrite)
				.sum();
		return retTotal;
	}

	/**
	 * Calculate sum of number of transfer
	 */
	public long numTransferPrediction(List<ApplicationComponentDataSourceData> acDSDataList) {
		long predVal = 0L;
		predVal = (long) (WT_READ * sumNumTransferRead(acDSDataList))
				+ (long) ((1 - WT_READ) * sumNumTransferWrite(acDSDataList));
		this.maxNumTransPrediction = this.maxNumTransPrediction > predVal ? this.maxNumTransPrediction : predVal;
		this.minNumTransPrediction = this.minNumTransPrediction < predVal ? this.minNumTransPrediction : predVal;
		return predVal;
	}

	/**
	 * Calculate the sum of data transfers for read
	 */
	public long sumNumTransferRead(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = acDSDataList.stream()
				.filter(acDSData -> acDSData.getDataRead()!=null &&  acDSData.getDataRead() > 0)
				.mapToLong(applicationComponentDataSourceData -> WT_AI)
				.sum();
		return retTotal;
	}

	/**
	 * Calculate the sum of data transfers for write
	 */
	public long sumNumTransferWrite(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = acDSDataList.stream()
				.filter(acDSData -> acDSData.getDataWrite()!=null && acDSData.getDataWrite() > 0)
				.mapToLong(applicationComponentDataSourceData -> WT_AI)
				.sum();
		return retTotal;
	}

	/**
	 * Compute the couple value
	 */
	public void calculateCouple(List<AppComDataSrc> appComDataSrcList) {
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
	public double normalizeDataTransfer(long val) {
		double retVal = (val - this.minDataTransPrediction)
				* ((MAX_RANGE - MIN_RANGE) / (double) (this.maxDataTransPrediction - this.minDataTransPrediction))
				+ MIN_RANGE;
		return retVal;
	}

	/**
	 * Normalize the value for number of transfer
	 */
	public double normalizeNumTransfer(long val) {
		double retVal = (val - this.minNumTransPrediction)
				* ((MAX_RANGE - MIN_RANGE) / (double) (this.maxNumTransPrediction - this.minNumTransPrediction))
				+ MIN_RANGE;
		return retVal;
	}

	/**
	 * Calculate the final couplet value
	 */
	public double calculateCouplet(double dataTransferVal, double numTransferVal) {
		double retVal = 0;
		retVal = WT_DATA_TRANSFER * dataTransferVal + (1 - WT_DATA_TRANSFER) * numTransferVal;
		return retVal;
	}

}
