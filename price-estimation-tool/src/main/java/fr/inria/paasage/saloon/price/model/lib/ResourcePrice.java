/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.inria.paasage.saloon.price.model.lib;

public class ResourcePrice 
{
	protected int quantity; 
	
	protected double price;
	

	public ResourcePrice(int quantity, double price) 
	{
		super();
		this.quantity = quantity;
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	} 
	
	
	public double getResourcePriceForQuantity(int theQuantity)
	{
		return (theQuantity*price)/quantity; 
	}
	

}
