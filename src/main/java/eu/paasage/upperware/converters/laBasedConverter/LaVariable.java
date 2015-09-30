/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.converters.laBasedConverter;

/**
 * This class represents a LaBasedReasoner Variable
 * @author danielromero
 *
 */
public class LaVariable 
{
	
	/**
	 * Attributes
	 */
	//Id
	public int id; 
	
	//The label of the variable
	public String label; 
	
	//The name of the variable
	public String name; 
	
	//The domain initialization 
	public String domainInit; 
	
	//The domain type
	public String domainType;
	
	//The multi range name related to the variable
	public String multiRangeName; 
	
	
	//The initial value
	public String initialValue; 
	
	//Indicates if the var has an initial value
	public boolean hasInitValue; 
	
	
	/**
	 * Constructor with parameters
	 * @param label The label
	 * @param name 	The name
	 * @param initialValue 	The initial value
	 * @param hasInitialValue Indicates if the variable has an initial value
	 */
	public LaVariable(String label, String name, String initialValue, boolean hasInitialValue)
	{
		this.hasInitValue= hasInitialValue; 
		
		this.initialValue= initialValue; 
		
		this.label= label; 
		
		this.name= name; 
	}
	
	/**
	 * Constructor with parameters
	 * @param label The label
	 * @param name 	The name
	 */
	public LaVariable(String label, String name)
	{
		this.label= label; 
		
		this.name= name; 
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * 
	 * @return label
	 */
	public String getLabel() 
	{
		return label;
	}
	
	/**
	 * 
	 * @return name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * 
	 * @return domain initialization
	 */
	public String getDomainInit() 
	{
		return domainInit;
	}

	/**
	 * 
	 * @param domainInit The domain
	 */
	public void setDomainInit(String domainInit) 
	{
		this.domainInit = domainInit;
	}

	/**
	 * 
	 * @return domain type
	 */
	public String getDomainType() 
	{
		return domainType;
	}

	/**
	 * 
	 * @param domainType The domain type
	 */
	public void setDomainType(String domainType) 
	{
		this.domainType = domainType;
	}

	/**
	 * 
	 * @return multirangeName 
	 */
	public String getMultiRangeName() 
	{
		return multiRangeName;
	}

	/**
	 * 
	 * @param multiRangeName The range name
	 */
	public void setMultiRangeName(String multiRangeName) 
	{
		this.multiRangeName = multiRangeName;
	}

	/**
	 * 
	 * @return initialValue
	 */
	public String getInitialValue() 
	{
		return initialValue;
	}

	/**
	 * 
	 * @param initialValue The initial value
	 */
	public void setInitialValue(String initialValue) 
	{
		this.initialValue = initialValue;
	}

	/**
	 * 
	 * @return hasInitValue
	 */
	public boolean isHasInitValue() 
	{
		return hasInitValue;
	}

	/**
	 * 
	 * @param hasInitValue true if initialValue!=null else false
	 */
	public void setHasInitValue(boolean hasInitValue) 
	{
		this.hasInitValue = hasInitValue;
	} 
	
	
	

}
