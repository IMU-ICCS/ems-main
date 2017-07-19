/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.lib;

import java.io.IOException;

import org.apache.log4j.Logger;

import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.api.ISender;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.FileTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaaSagePropertyManager;

/**
 * This class provides the functionality to send the id of the generate models to the standar output
 * @author danielromero
 *
 */
public class FileSystemSender implements ISender 
{
	
	
	/*
	 * ATTRIBUTES
	 */
	
	private Logger logger= GenerationOrchestrator.getLogger();
	
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.profiler.cp.generator.model.api.ISender#sendPaasageConfigurationFiles(eu.paasage.upperware.metamodel.application.PaasageConfiguration)
	 */

	@Override
	public void sendPaasageConfigurationFiles(PaasageConfiguration pc) {
		
		sendPaasageConfigurationFiles(pc.getId());
	}
	
	@Override
	public void sendPaasageConfigurationFiles(String id) {
		
		
		if(PaaSagePropertyManager.getInstance().getCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME)!=null && !PaaSagePropertyManager.getInstance().getCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME).equals(""))
		{
			String content=CDODatabaseProxy.CDO_SERVER_PATH+id;
				
			try {
				FileTool.saveFile(PaaSagePropertyManager.getInstance().getCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME), content);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("FileSystemSender - sendPaasageConfigurationFiles - Problems creating the file "+PaaSagePropertyManager.getInstance().getCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME));
			}
		}
		else
			logger.warn("FileSystemSender - sendPaasageConfigurationFiles - The file for saving the model id was not specified");
	}

}
