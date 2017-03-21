/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.inria.paasage.saloon.price.model.tools;

import java.io.File;

public interface Constants 
{	
	public static final String CONFIG_FILES_DEFAULT_PATH= File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator;
	
	public static final String CONFIG_FILES_DEFAULT_PATH_TEST= File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator;
	
	public static final double DEFAULT_PRICE_VM=1; 

}
