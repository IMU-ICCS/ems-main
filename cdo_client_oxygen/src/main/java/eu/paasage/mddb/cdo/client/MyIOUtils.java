/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.client;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MyIOUtils {

	private static final int BUFFER = 2048;
	private static int id = 1;
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyIOUtils.class);

	
	public static boolean createZipArchive(String srcFolder, String suffix, boolean subDirs, OutputStream output) {

	    try {
	        BufferedInputStream origin = null;

	        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(output));
	        byte data[] = new byte[BUFFER];

	        File subDir = new File(srcFolder);
	        File subdirList[] = subDir.listFiles();
	        for(File f:subdirList)
	        {
	                // get a list of files from current directory
	                //File f = new File(srcFolder + File.separator + sd);
	                if(f.isDirectory())
	                {
	                	if (subDirs){
		                    File files[] = f.listFiles();
	
		                    for (int i = 0; i < files.length; i++) {
		                        log.debug("Adding: {}", files[i].getName());
		                        FileInputStream fi = new FileInputStream(files[i]);
		                        origin = new BufferedInputStream(fi, BUFFER);
		                        ZipEntry entry = new ZipEntry(f.getName() + File.separator + files[i].getName());
		                        out.putNextEntry(entry);
		                        int count;
		                        while ((count = origin.read(data, 0, BUFFER)) != -1) {
		                            out.write(data, 0, count);
		                            //out.flush();
		                        }
		                        out.closeEntry();
		                        origin.close();
	
		                    }
	                	}
	                }
	                else //it is just a file
	                {
	                	if (!f.getName().endsWith(suffix)) continue;
	                	log.debug("Adding: {} canRead: {}", f.getName(), f.canRead());
	                    FileInputStream fi = new FileInputStream(f);
	                    origin = new BufferedInputStream(fi, BUFFER);
	                    ZipEntry entry = new ZipEntry(f.getName());
	                    out.putNextEntry(entry);
	                    int count;
	                    int totalCount = 0;
	                    while ((count = origin.read(data, 0, BUFFER)) != -1) {
	                        out.write(data, 0, count);
	                        //out.flush();
	                        totalCount = totalCount + count;
	                        log.debug("Read inc. {} bytes for: {}", count, f.getName());
	                    }
	                    log.debug("Read totally {} bytes for: {}", totalCount, f.getName());
	                    entry.setSize(totalCount);
	                    out.closeEntry();
	                    origin.close();

	                }
	        }
	        out.finish();
	        out.close();
	    } catch (Exception e) {
	    	log.error("createZipArchive threw exception: ", e);
	        return false;
	    }
	    return true;
	} 
	
	public static boolean deleteFiles(String folder, String suffix){
		File dir = new File(folder);
        File dirList[] = dir.listFiles();
        try{
	        for(File f:dirList)
	        {
	        	if (f.getName().endsWith(suffix)){
	        		f.delete();
	        	}
	        }
	        return true;
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return false;
	}
	
	public static void loadInputStream(File f, InputStream is){
		BufferedReader bis = new BufferedReader(new InputStreamReader(is));
		try{
			PrintWriter dos = new PrintWriter(new FileOutputStream(f));
			String s = bis.readLine();
			while (s != null){
				dos.println(s);
				s = bis.readLine();
			}
			dos.flush();
			dos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
