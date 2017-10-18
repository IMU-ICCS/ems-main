package eu.melodic.upperware.dlm.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class DataCostService {

	@Autowired
	private DataCostRepository dataCostRepository;

	public List<DataCostHistory> getAllCosts() {

		List<DataCostHistory> dataCostHistories = new ArrayList<>();
		dataCostRepository.findAll().forEach(dataCostHistories::add);
		return dataCostHistories;
	}

	public DataCostHistory getDataCost(String id) {
		// Filtering based on the id
		// return dataCostHistory.stream().filter(c ->
		// c.getId().equals(id)).findFirst().get();
		return dataCostRepository.findOne(id);
	}

	public void newDataCost(DataCostHistory costHistory) {
		dataCostRepository.save(costHistory);

	}

	public void updateDataCost(String id, DataCostHistory costHistory) {
		// for (int i = 0; i< dataCostHistory.size(); i++) {
		// DataSrcHistory c = dataCostHistory.get(i);
		// if(c.getId().equals(id)) {
		// dataCostHistory.set(i, costHistory);
		// return;
		// }
		dataCostRepository.save(costHistory);

	}

}
