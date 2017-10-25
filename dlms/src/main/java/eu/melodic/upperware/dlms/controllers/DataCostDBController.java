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
import eu.melodic.models.interfaces.dlms.*;

import eu.melodic.upperware.dlms.store.DataCostHistory;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

// TODO: I will save the hardcoded daat into the mysql as history of the related to the daat moveemnt

@RestController
public class DataCostDBController {

	@Autowired
	private DataCostService dataCostService;

	@RequestMapping(value = "/dataPool/transferCost", method = GET, produces = APPLICATION_JSON_VALUE)
	public DataPoolTransferCostResponse transferCost() {
		//TODO: do the logic and build reponse object
		DataPoolTransferCostResponseImpl response = new DataPoolTransferCostResponseImpl();
		response.setDataPoolId("somePoolId");
		response.setCost("500");

		return response;

	}

	@RequestMapping(value = "/dataPool", method = POST, produces = APPLICATION_JSON_VALUE)
	public void transferCost(@RequestBody DataPoolRequestImpl request) {
		//retrieve field's values and do some logic

		System.out.println("Received POST dataPool request with data: " +request.getDataPoolId() +":" +request.getVolume());
		//TODO: do the logic
	}

	@RequestMapping(value = "/update", method = POST, produces = APPLICATION_JSON_VALUE)
	public void transferCost(@RequestBody DataPoolUpdateRequestImpl request) {
		//retrieve field's values and do some logic
		System.out.println("Received POST dataPool UPDATE request with data: " +request.getDataPoolId() +":" +request.getVolume());
		//TODO: do the logic
	}



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
