/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.validation;

/**
 * This class contains the information required about the entities and used by the {@link ApplicationController}
 * @author Arnab Sinha
 *
 */

public class MonitorEntity{
	
	Type type;
	int execWareId;
	String state;
	
	MonitorEntity(Type type, int id){
		this.type = type;
		this.execWareId = id;
		this.state = null;
	}
	
	void setStateOK(){
		this.state = "OK";
	}
	
	void setStateError(){
		this.state = "Error";
	}
	
	boolean isStateOK(){
		if(this.state != null && this.state.equalsIgnoreCase("OK"))
			return true;
		else
			return false;
	}
	
	boolean isStateError(){
		if(this.state != null && this.state.equalsIgnoreCase("Error"))
			return true;
		else
			return false;
	}
	
	Type getEntityType(){return this.type;}
	int getexecWareId(){return this.execWareId;}
	
	public enum Type{
		virtualMachine,
		instance;
	}
}

