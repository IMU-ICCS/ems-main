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

import java.io.InputStream;

import org.apache.log4j.Logger;

import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IDimensionValueEstimator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import fr.inria.paasage.saloon.price.model.lib.EstimatorsManager;

/**
 * This class provide the functionlity to estimate the value of a deployment for a fiven paasage confoguration
 * @author danielromero
 *
 */
public class ResponseTimeEstimator implements IDimensionValueEstimator 
{
	/*
	 * ATTRIBUTES
	 */
	/*
	 * The manager of estimators
	 */
	protected EstimatorsManager manager; 
	
	/*
	 * The logger
	 */
	protected static Logger logger= GenerationOrchestrator.getLogger(); 
	
	/*
	 * The database proxy
	 */
	private IDatabaseProxy database; 
	
	
	/*
	 * CONSTRUCTOR
	 */
	/**
	 * Constructor with parameters
	 * @param priceFile The file defining the prices
	 * @param database The database proxy
	 */
	public ResponseTimeEstimator(InputStream priceFile, IDatabaseProxy database)
	{
		manager= new EstimatorsManager(priceFile); 
		this.database= database; 
	}
	
	/*
	 * METHODS
	 */
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IDimensionValueEstimator#estimateDimensionValue(eu.paasage.upperware.metamodel.application.PaasageConfiguration)
	 */
	public void estimateDimensionValue(PaasageConfiguration configuration) 
	{
		
/*		for(VirtualMachineProfile vmp:configuration.getVmProfiles())
		{
			for(ProviderCost pc: vmp.getProvider())
			{	
				Provider provider= pc.getProvider();
				
				logger.debug("CostEstimator - estimateDimensionValue - Defining the price for provider "+provider.getId()); 
				
				ProviderModel fm= loadProviderPM(provider, configuration); 
				
				logger.debug("CostEstimator - estimateDimensionValue - Defining the price for provider "+provider.getId()+ " with FM "+fm.getRootFeature().getName()); 
				
				double price=manager.estimatePrice(fm); 
				
				logger.debug("CostEstimator - estimateDimensionValue - The computed price "+price); 
				
				pc.setCost(price); 
			}	

		}*/
	}
	
	/**
	 * Loads a model related to a given provider
	 * @param provider The provider
	 * @param pc The paasage configuration
	 * @return The provider model
	 */
	protected ProviderModel loadProviderPM(Provider provider, PaasageConfiguration pc)
	{
		
		return database.loadPM(pc, provider); 
		
	}

}
