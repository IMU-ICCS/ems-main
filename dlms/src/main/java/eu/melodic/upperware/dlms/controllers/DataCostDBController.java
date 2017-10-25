/*
 * Copyright (C) 2017 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.dlms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.melodic.upperware.dlms.store.DataCostHistory;

// TODO: I will save the hardcoded daat into the mysql as history of the related to the daat moveemnt

@RestController
public class DataCostDBController {

	@Autowired
	private DataCostService dataCostService;

	@RequestMapping("/cost")
	public List<DataCostHistory> getAllCosts() {
		// TODO: Connect with the database
		return dataCostService.getAllCosts();
	}

	@RequestMapping("/cost/{id}")
	public DataCostHistory getDataCost(@PathVariable String id) {
		return dataCostService.getDataCost(id);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/cost")
	public void newDataCost(@RequestBody DataCostHistory costHistory) {
		dataCostService.newDataCost(costHistory);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/cost/{id}")
	public void updateDataCost(@RequestBody DataCostHistory costHistory, @PathVariable String id) {
		dataCostService.updateDataCost(id, costHistory);
	}

}
