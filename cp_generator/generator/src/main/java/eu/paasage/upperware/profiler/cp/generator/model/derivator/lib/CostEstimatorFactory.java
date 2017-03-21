/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.derivator.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IDimensionValueEstimator;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IEstimatorFactory;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.FileTool;

/**
 * This class creates cost estimators
 * @author danielromero
 *
 */
public class CostEstimatorFactory implements IEstimatorFactory 
{
	
	/*
	 * CONSTRUCTOR
	 */
	public CostEstimatorFactory()
	{
		
	}
	
	/*
	 * METHODS
	 */
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IEstimatorFactory#createEstimator(eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy)
	 */
	public IDimensionValueEstimator createEstimator(IDatabaseProxy database) 
	{
		
		File cloudPricingFile= new File(Constants.CONFIG_FILES_DEFAULT_PATH,"cloudPricing.txt"); 
		
		InputStream is= null; 
		
		if(!cloudPricingFile.isFile())
		{	
			is= FileTool.getInputStreamFromFileName(Constants.WAR_CONFIG_PATH+"cloudPricing.txt"); 
						
		} else
			try {
				is= new FileInputStream(cloudPricingFile);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			
		
		return new CostEstimator(is, database);
	}

}
