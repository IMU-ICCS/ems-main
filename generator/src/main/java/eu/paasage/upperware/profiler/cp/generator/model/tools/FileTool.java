/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.ModelFileInfo;

/**
 * This class provides auxiliary methods to deal with system files
 * @author danielromero
 *
 */
public class FileTool 
{
	/*
	 * Logger
	 */
	protected static Logger logger= GenerationOrchestrator.getLogger(); 
	
	
	/**
	 * Unzip a zip file
	 * @param destinationPath Path to unzip the file
	 * @param file The zip file
	 */
	public static void unzipFile(String destinationPath, File file)
	{
		try
        {
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            zipinputstream = new ZipInputStream(new FileInputStream(file));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) 
            { 
                //for each entry to be extracted
                String entryName = zipentry.getName();

                int n;
                FileOutputStream fileoutputstream;
                
                if(zipentry.isDirectory())
                {
                	File newFile= new File(destinationPath+File.separator+entryName); 
                	newFile.mkdirs(); 
                }
                else
                {
                             
	                fileoutputstream = new FileOutputStream(destinationPath+File.separator+entryName);   
	                copyInputStream(zipinputstream, fileoutputstream);
	               
	                fileoutputstream.flush(); 
	                fileoutputstream.close(); 
                }    
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }//while

            zipinputstream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	/**
	 * Copies a file in a new file
	 * @param in Input file
	 * @param out Output file
	 * @throws IOException If problems with the streams
	 */
	protected static void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		int n=0; 
		byte[] buf = new byte[1024];
		while ((n = in.read(buf, 0, 1024)) > -1)
            out.write(buf, 0, n);
	}
	
	/**
	 * Deletes a given file. If it is a directory, its content is also deleted
	 * @param file The file to be deleted
	 */
	public static void deleteFile(File file)
	{
		if(file.exists())
			if(file.isDirectory())
			{
				File[] files= file.listFiles(); 
				
				for(File f: files)
				{
					deleteFile(f); 
				}
				
				file.delete(); 
				
			}
			else
				file.delete(); 
	}
	
    /**
     * Utility method to create contribution zip file.
     * 
     * @param inFile The directory to zip.
     * @param outFile The output file name.
     * @throws IOException If problems with the streams
     */
    public static void zip(File inFile, File outFile) throws IOException {
        final int buffer = 2048;
        FileOutputStream dest = new FileOutputStream(outFile);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

        try {
            byte data[] = new byte[buffer];
            List<String> list = listFiles(inFile, "");

            for (String file : list) {
                FileInputStream fis = new FileInputStream(new File(inFile, file));
                BufferedInputStream origin = new BufferedInputStream(fis, buffer);
                ZipEntry entry = new ZipEntry(file);
                out.putNextEntry(entry);

                try {
                    int count;
                    while ((count = origin.read(data, 0, buffer)) != -1) {
                        out.write(data, 0, count);
                    }
                } finally {
                    origin.close();
                    fis.close();
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            out.close();
            dest.close();
        }
    }
    
    /**
     * List files recursively. Does not include directories.
     * 
     * @param directory The directory to list.
     * @param currentDirName The current directory name.
     * @return A list of paths relative to the directory parameter.
     */
    public static List<String> listFiles(File directory, String currentDirName)
    {
        // List of files / directories
        List<String> files = new ArrayList<String>();
        // Go over entries
        for (File entry : directory.listFiles())
        {
                
            if (entry.isDirectory()) {
                StringBuilder sb  = new StringBuilder(currentDirName);
                sb.append(entry.getName()).append(File.separator); 
                files.addAll( listFiles(entry, sb.toString()) );
            } else {
                files.add(currentDirName + entry.getName());
            }
        }
        
        // Return collection of files
        return files;
    }
    
    /**
     * Retrieves all files with a given extension 
     * @param directory The directory for searching the files
     * @param extension The file extension
     * @return The list of files with the extension
     */
    public static List<File> listFilesByExtension(File directory, String extension)
    {
    	List<File> files = new ArrayList<File>();
    	
    	if(directory.listFiles()!=null)
	    	for (File entry : directory.listFiles())
	        {
	                
	            if (entry.isDirectory()) {
	                List<File> aux= listFilesByExtension(entry, extension);
	                
	                files.addAll( aux );
	            } else if(entry.getName().endsWith(extension)) 
	            {
	                files.add( entry);
	            }
	        }
	        
        // Return collection of files
        return files;
    }
    
    /**
     * Searches a file with the given name
     * @param directory The directory for searching the file
     * @param fileName The file name
     * @return The file or null if it does not exist
     */
    public static File searchFileByName(File directory, String fileName)
    {
    	    	
    	if(directory.listFiles()!=null)
	    	for (File entry : directory.listFiles())
	        {
	                
	            if (entry.isDirectory()) {
	                File aux= searchFileByName(entry, fileName);
	                
	                if(aux!=null)
	                	return aux; 
	                
	                
	            } else if(entry.getName().equals(fileName)) 
	            {
	                return entry; 
	            }
	        }
	        
        // File not found
        return null;
    }
    
    /**
     * Reads the contents of a file into a byte array.
     * 
     * @param file - the file to read, must not be null
     * @return the file contents
     * @throws IOException - in case of an I/O error
     */
    public static byte[] getBytesFromFile(File file) throws IOException
    {
        InputStream inputStream = new FileInputStream(file);
        
        try
        {
         // Get the size of the file
            long length = file.length();
            
            if (length > Integer.MAX_VALUE)
            {
                throw new IOException("The file you are trying to read is too large, length :"+length+", length max : "+Integer.MAX_VALUE);
            }

            // Create the byte array to hold the data
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = inputStream.read(bytes, offset, bytes.length - offset);
            while (offset < bytes.length && numRead >= 0)
            {
                offset += numRead;
                numRead = inputStream.read(bytes, offset, bytes.length - offset);
            }
            
            // Ensure all the bytes have been read in
            if (offset < bytes.length)
            {
                throw new IOException("Could not completely read file " + file.getName());
            }
            
            return bytes;
        }
        finally
        {
         // Close the input stream and return bytes
            inputStream.close();
        }
    }
    
    /**
     * Copies a directory with all its content
     * @param sourceFile The file to be copied
     * @param sourcePath The path related to the file
     * @param targetPath The target directory
     * @throws IOException If problems copying files
     */
    public static void copyFilesRecursively(File sourceFile, File sourcePath, File targetPath) throws IOException{
        //Defines no filter
        FileFilter noFilter = new FileFilter() {
            public boolean accept(File file) {
                return true;
            }
        };
        
        copyFilesRecursively(sourceFile, sourcePath, targetPath, noFilter);
    }
    
    /**
     * Copies a directory with all its content
     * @param sourceFile The file to be copied
     * @param sourcePath The path related to the file
     * @param targetPath The target directory
     * @param fileFilter The filter to skip some files
     * @throws IOException If problems copying the files
     */
    public static void copyFilesRecursively(File sourceFile, File sourcePath, File targetPath, FileFilter fileFilter) throws IOException {
        try {
            copy(sourceFile,  new File(targetPath.getCanonicalPath() + sourceFile.getCanonicalPath().replace(sourcePath.getCanonicalPath(),"")));
        } catch (IOException e) {
            throw new IOException("It is not possible to copy one or more files ("+ sourceFile.getName() +"). Error: " + e.getMessage() );
        }
        if (sourceFile.isDirectory()) {
            for (File child : sourceFile.listFiles(fileFilter)) {
                copyFilesRecursively(child, sourcePath, targetPath, fileFilter);
            }
        }

    }
    /**
     * Copies a given file into another file
     * @param src The file to be copied
     * @param dst The file to be created with the copy
     * @throws IOException If problems copying the file
     */
    public static void copy(File src, File dst) throws IOException 
    {
        
        //Don't copy if it's a directory
        if (src.isDirectory()) {
            dst.mkdir();
            return;
        }
        
        InputStream inputStream = new FileInputStream(src);
        OutputStream outputStream = new FileOutputStream(dst);
        
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        inputStream.close();
        outputStream.close();
    }
    
    /**
     * Saves the file with a given content and name
     * @param fileName The file name
     * @param content The file content
     * @throws IOException If problems saving the file
     */
	public static void saveFile(String fileName, String content) throws IOException
	{		
		Writer out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
	    try {
	      out.write(content);
	    }
	    finally {
	      out.close();
	    }
	}
	
	/**
	 * Saves the file with the given
	 * @param fileInputStream The stream that contains the file content
	 * @param fileName the file path
	 */
	public static void saveFile(InputStream fileInputStream, String fileName) {
		 
		try {
            OutputStream outpuStream = new FileOutputStream(new File(fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
 
            outpuStream = new FileOutputStream(new File(fileName));
            while ((read = fileInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
 
            e.printStackTrace();
        }
		 
	}
	
	/**
	 * Creates a directory 
	 * @param dir The name of the directory
	 * @throws IOException If problems creating the directory 
	 */
	public static void createDirectory(String dir) throws IOException
	{
		File file= new File(dir); 
		
		if(!file.exists())
		{	
			file.mkdirs(); 
		}
		
	}
	
	/**
	 * Loads a file content into a string
	 * @param file The file name
	 * @param encoding The file encoding
	 * @return The string that contains the file content
	 */
	public static String loadFileIntoString(String file, String encoding)
	{
		StringBuilder text = new StringBuilder();
		 Scanner scanner=null;
		try {
			scanner = new Scanner(new FileInputStream(file), encoding);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    try {
		    while (scanner.hasNextLine())
		    {
		    	text.append(scanner.nextLine() + "\n");
		    }
		    
		   return text.toString(); 
	    }
	    finally{
	      scanner.close();
	    }
	}
	
	/**
	 * Loads a properties file
	 * @param file The properties file
	 * @return The properties file
	 */
	public static Properties loadPropertiesFile(File file)
	{
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			Properties props= new Properties(); 
			props.load(fis);    
	        fis.close();
	        return props; 
		} catch (FileNotFoundException e) {
			e.printStackTrace();  
			
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		return null; 
	}
	
    /******
    application/x-compress      z
    application/x-compressed    tgz
    application/x-gtar          gtar
    application/x-gzip          gz
    application/x-tar           tar
    application/zip             zip
    ******/
	/**
	 * Fin the content type
	 * @param fileName The name of the file
	 * @return The type of the content
	 */
    public static String findContentType(String fileName)
    {
        String type = null;
        if (fileName.endsWith(".gz") == true) {
            type = "application/x-gzip";
        }
        else if (fileName.endsWith(".zip") == true) {
            type = "application/zip";
        }
        else {
            type = "application/octet-stream";
        }

        return type;
    }
    
    /**
     * Creates an input stream from a file
     * @param fileName The file name
     * @return The input stream 
     */
	public static InputStream getInputStreamFromFileName(String fileName)
	{
		//ClassLoader classLoader = FileTool.class.getClass().getClassLoader();
		
		InputStream is= FileTool.class.getClass().getResourceAsStream(fileName); 
		
		return is; 
	}
	
	/**
	 * Create an input stream from a file using current path as root
	 * @param fileName The file name
	 * @return The input stream
	 */
	public static InputStream getInputStreamFromLocalFile(String fileName)
	{
		
		
		InputStream is= null;
		try {
			is = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return is; 
	}
	
	/**
	 * Creates a list of model file info by using a zip file
	 * @param zipFile The zip file
	 * @param workingDir The working directory to unzip the file
	 * @return List of models that are in the zip file
	 */
	public static List<ModelFileInfo> processZipFile(File zipFile, File workingDir)
	{
		List<ModelFileInfo> modelInfos= new ArrayList<ModelFileInfo>(); 
		
		FileTool.unzipFile(workingDir.getAbsolutePath(), zipFile); 
		
		File desriptorFile= FileTool.searchFileByName(workingDir, Constants.PAASAGE_MODEL_DESCRIPTOR_FILE); 
		
		if(desriptorFile!=null)
		{	
		
			Properties descriptorAsProperties= FileTool.loadPropertiesFile(desriptorFile); 
			
			Set<Object> keys= descriptorAsProperties.keySet(); 
			
			for(Object key: keys)
			{
				String fileName= descriptorAsProperties.getProperty((String) key); 
				
				File currentFile= FileTool.searchFileByName(workingDir, fileName); 
				
				if(currentFile!=null)
				{
					ModelFileInfo mfi= new ModelFileInfo(currentFile.getAbsolutePath(), (String) key); 
				
					modelInfos.add(mfi); 
				}
				else
					logger.warn("FileTool- processZipFile - The file "+ fileName +" does not exist in the zip file!"); 
				
				
						
			}
		}
		else
			logger.error("FileTool- processZipFile - The PaaSage descriptor file does not exist in the zip file!"); 
		
		return modelInfos; 
	}


}
