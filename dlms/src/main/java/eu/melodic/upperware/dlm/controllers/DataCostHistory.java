package eu.melodic.upperware.dlm.controllers;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class DataCostHistory {

	@Id
	private String id;
	private String dataSrcName;
	private int dlmCostValue;
	//private Date registered;

	public DataCostHistory() {

	}

	public DataCostHistory(String id, String dataSrcName, int dlmCostValue) {
		super();
		this.id = id;
		this.dataSrcName = dataSrcName;
		this.dlmCostValue = dlmCostValue;
	//	this.registered = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataSrcName() {
		return dataSrcName;
	}

	public void setDataSrcName(String daatSrcName) {
		this.dataSrcName = daatSrcName;
	}

	public int getDlmCostValue() {
		return dlmCostValue;
	}

	public void setDlmCostValue(int dlmCostValue) {
		this.dlmCostValue = dlmCostValue;
	}

//	public Date getRegistered() {
//		return registered;
//	}
//
//	public void setRegistered(Date registered) {
//		this.registered = registered;
//	}

	

}
