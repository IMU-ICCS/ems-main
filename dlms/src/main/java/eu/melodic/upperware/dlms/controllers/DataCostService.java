/*
 * Copyright (C) 2017 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.dlms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.melodic.upperware.dlms.store.DataCostHistory;
import eu.melodic.upperware.dlms.store.DataCostRepository;

@Service
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
