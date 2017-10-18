package eu.melodic.upperware.dlm.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// TODO: I will save the hardcoded daat into the mysql as history of the related to the daat moveemnt

//@RestController
public class DataCostDB {

	@Autowired
	private  DataCostService dataCostService;
	
	
	@RequestMapping("/cost")
	public List<DataCostHistory> getAllCosts() {
		// TODO: Connect with the database
		return dataCostService.getAllCosts(); 	
		}
	
	@RequestMapping("/cost/{id}")
	public DataCostHistory getDataCost(@PathVariable String id) {
		return dataCostService.getDataCost(id);
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cost")
	public void newDataCost(@RequestBody DataCostHistory costHistory) {
		dataCostService.newDataCost(costHistory);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/cost/{id}")
	public void updateDataCost(@RequestBody DataCostHistory costHistory, @PathVariable String id) {
		dataCostService.updateDataCost(id, costHistory);
	}

}
