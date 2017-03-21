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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * Helper class for generating the file using velocity
 * @author danielromero
 *
 */
public class VelocityTool 
{
	/*
	 * Constants
	 */
	//Log
	private static Logger logger= Logger.getLogger("paasage-converters-log");
	
	
	//Velocity
	private static final String VELOCITY_TEMPLATES_HOME_PROPERTIES="velocityTemplates";
	
	private static final String VELOCITY_LOADER_PROPERTY="velocityProperty";
	
	/**
	 * Creates the file by using the specified template and parameters
	 * @param var			Template parameters
	 * @param templateFile	Path to the template
	 * @param targetFile	Output file
	 */
	public static void createFileFromTemplate(Map var, File templateFile, File targetFile)
	{		
		Velocity.setProperty("resource.loader", "file");
		Velocity.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		
		if(System.getProperty(VELOCITY_LOADER_PROPERTY)!=null)
		{
			Velocity.setProperty("file.resource.loader.class", System.getProperty(VELOCITY_LOADER_PROPERTY));
		}
		
		Velocity.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		Velocity.setProperty("runtime.log.logsystem.log4j.category", "velocity");
		Velocity.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		Velocity.init(); 
		
        VelocityContext context = new VelocityContext(var);
        
        Template template =  null;

        try
        {
        	if(templateFile.exists())
        		logger.debug("VelocityTool- createFileFromTemplate - Exist! "); 
        	logger.debug("VelocityTool- createFileFromTemplate - Template path "+templateFile.getPath()); 
            template = Velocity.getTemplate(templateFile.getPath());
            
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(targetFile));

            if ( template != null)
                template.merge(context, writer);
            
            writer.flush();
            writer.close();
        }
        catch( ResourceNotFoundException rnfe )
        {
        	rnfe.printStackTrace(); 
            logger.error("VelocityTool- createFileFromTemplate - cannot find template " + templateFile );
        }
        catch( ParseErrorException pee )
        {
            logger.error("VelocityTool- createFileFromTemplate - Syntax error in template " + templateFile + ":" + pee );
        } catch (FileNotFoundException e) {
        	e.printStackTrace(); 
        	logger.error("VelocityTool- createFileFromTemplate - cannot find the target file location " + targetFile.getParent() );
		} catch (IOException e) {
			
			logger.error("VelocityTool- createFileFromTemplate - cannot create the target file " + targetFile );
			
		}




	}

}
