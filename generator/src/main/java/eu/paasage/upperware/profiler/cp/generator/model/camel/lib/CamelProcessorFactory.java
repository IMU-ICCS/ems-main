/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.camel.lib;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.api.IProcessorFactory;
import eu.paasage.upperware.profiler.cp.generator.model.lib.ModelProcessor;

/**
 * This class provides the functionality for creating model processors that deals with camel models. 
 * @author danielromero
 *
 */
public class CamelProcessorFactory implements IProcessorFactory 
{
	

	/*
	 * CONSTRUCTOR
	 */
	public CamelProcessorFactory() 
	{
		CamelPackage.eINSTANCE.eClass(); 
	}

	/*
	 * METHODS
	 */
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.profiler.cp.generator.model.api.IProcessorFactory#loadModel(java.lang.String)
	 */
	@Override
	public ModelProcessor loadModel(String modelPath) 
	{
		CamelModelProcessor modelProcessor= new CamelModelProcessor(null); 
		
		modelProcessor.setValid(false); 

		IDatabaseProxy proxy= CDODatabaseProxy.getInstance();
		
		CamelModel camelModel= proxy.getCamelModel(modelPath); 
		
		modelProcessor= new CamelModelProcessor(camelModel); 
		modelProcessor.setValid(true);
		
		return modelProcessor;
	}

}
