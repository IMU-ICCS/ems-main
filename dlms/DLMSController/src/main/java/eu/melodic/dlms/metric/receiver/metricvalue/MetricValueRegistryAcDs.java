package eu.melodic.dlms.metric.receiver.metricvalue;

import java.util.Date;

import eu.melodic.dlms.db.model.ApplicationComponent;
import eu.melodic.dlms.db.model.ApplicationComponentDataSourceData;
import eu.melodic.dlms.db.model.DataSource;
import eu.melodic.dlms.db.repository.ApplicationComponentDataSourceDataRepository;
import eu.melodic.dlms.db.repository.ApplicationComponentRepository;
import eu.melodic.dlms.db.repository.DataSourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MetricValueRegistryAcDs<T> {
	private final ApplicationComponentRepository acRepository;
	private final DataSourceRepository dsRepository;
	private final ApplicationComponentDataSourceDataRepository acDsDataRepository;
	private MetricValueEventAcDsDataRead eventRead;
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
	public void storeApplicationComponent(Long id) {
		if (!acRepository.existsById(id)) {
			ApplicationComponent ac = new ApplicationComponent(id);
			acRepository.save(ac);
		}
	}

	/**
	 * Store the data source
	 */
	public void storeDataSource(Long id) {
		if (!dsRepository.existsById(id)) {
			DataSource ds = new DataSource(id);
			dsRepository.save(ds);
		}
	}

	/**
	 * Store the data read/write
	 */
	public void storeAcDsData(Long acId, Long dsId, Long data, Date timestamp, boolean isDataRead) {
		ApplicationComponentDataSourceData acDs = new ApplicationComponentDataSourceData(acId, dsId, data, timestamp,
				isDataRead);
		acDsDataRepository.save(acDs);

	}

	public MetricValueEventAcDsDataRead getEventRead() {
		return eventRead;
	}

	public void setEventRead(MetricValueEventAcDsDataRead eventRead) {
		this.eventRead = eventRead;
	}

	public MetricValueEventAcDsDataWrite getEventWrite() {
		return eventWrite;
	}

	public void setEventWrite(MetricValueEventAcDsDataWrite eventWrite) {
		this.eventWrite = eventWrite;
	}

}
