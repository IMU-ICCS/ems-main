package eu.melodic.dlms.metric.receiver.metricvalue;

import java.util.Date;
import java.util.Objects;

import eu.melodic.dlms.db.model.ApplicationComponent;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceData;
import eu.melodic.dlms.db.model.ControllerDataSource;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.ControllerDataSourceRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MetricValueRegistryAcDs<T> {
	private final ApplicationComponentRepository acRepository;
	private final ControllerDataSourceRepository dsRepository;
	private final ApplicationComponentDataSourceDataRepository acDsDataRepository;
	@Getter
	@Setter
	private MetricValueEventAcDsDataRead eventRead;
	@Getter
	@Setter
	private MetricValueEventAcDsDataWrite eventWrite;

	/**
	 * For data read
	 */
	public void saveMetricValuesEventRead() {
		storeApplicationComponent(this.eventRead.getAc());
		storeDataSource(this.eventRead.getDs());
		storeAcDsData(this.eventRead.getAc(), this.eventRead.getDs(), this.eventRead.getAmountRead(),
				new Date(this.eventRead.getTimeStamp()), true);
	}

	/**
	 * For data write
	 */
	public void saveMetricValuesEventWrite() {
		storeApplicationComponent(this.eventWrite.getAc());
		storeDataSource(this.eventWrite.getDs());
		storeAcDsData(this.eventWrite.getAc(), this.eventWrite.getDs(), this.eventWrite.getAmountWrite(),
				new Date(this.eventWrite.getTimeStamp()), false);
	}

	/**
	 * Store the application component if it does not exist
	 */
	public void storeApplicationComponent(String name) {
		if (!acRepository.existsByName(name)) {
			ApplicationComponent ac = new ApplicationComponent(name);
			acRepository.save(ac);
		}
	}

	/**
	 * Store the data source
	 */
	public void storeDataSource(String name) {
		if (!dsRepository.existsByName(name)) {
			ControllerDataSource ds = new ControllerDataSource(name);
			dsRepository.save(ds);
		}
	}

	/**
	 * Store the data read/write
	 */
	public void storeAcDsData(String acName, String dsName, Long data, Date timestamp, boolean isDataRead) {
		Long acId = null, dsId = null;
		if (acRepository.existsByName(acName))
			acId = acRepository.findByName(acName).getId();
		if (dsRepository.existsByName(dsName))
			dsId = dsRepository.findByName(dsName).getId();
		if (Objects.isNull(acId) || Objects.isNull(dsId))
			log.info("acId or dsId is null");
		ApplicationComponentDataSourceData acDs = new ApplicationComponentDataSourceData(acId, dsId, data, timestamp,
				isDataRead);
		acDsDataRepository.save(acDs);
	}

}
