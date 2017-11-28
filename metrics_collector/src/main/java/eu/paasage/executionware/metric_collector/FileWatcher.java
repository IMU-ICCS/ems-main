/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.cdo.common.id.CDOID;

public class FileWatcher implements Runnable{
	private long delay;
	private File file;
	private long timestamp = 0;
	private MetricCollection mc;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileWatcher.class);
	
	public FileWatcher(File file, long delay, MetricCollection mc){
		this.file = file;
		this.delay = delay;
		this.mc = mc;
	}
	
	public void run() {
		while (true){
			long timeStamp = file.lastModified();
			if (this.timestamp < timeStamp){
				getCommands();
				this.timestamp = timeStamp;
			}
			try{
				Thread.sleep(delay);
			}
			catch(InterruptedException e){
				return;
			}
		}
	}
	
	public synchronized void getCommands(){
		ObjectInputStream br = null;
		try{
			br = new ObjectInputStream(new FileInputStream(file));
			String command = br.readUTF();
			if (command.equals("readMetrics")){
				/*int num = br.readInt();
				CDOID[] ids = new CDOID[num];
				for (int i = 0; i < num; i++){
					ids[i]=(CDOID)br.readObject();
				}*/
				Set<CDOID> ids = (Set<CDOID>)br.readObject();
				CDOID ecID = (CDOID)br.readObject();
				logger.info("FileWatcher: calling readMetrics method");
				mc.readMetrics(ids, ecID);
				br.close();
			}
			else if (command.equals("deleteMetrics")){
				CDOID ecID = (CDOID)br.readObject();
				logger.info("FileWatcher: calling deleteMetrics method");
				mc.deleteMetrics(ecID);
				br.close();
			}
			else if (command.equals("updateMetrics")){
				int num = br.readInt();
				Set<CDOID> ids = new HashSet<CDOID>();
				for (int i = 0; i < num; i++){
					ids.add((CDOID)br.readObject());
				}
				CDOID ecID = (CDOID)br.readObject();
				logger.info("FileWatcher: calling updateMetrics method");
				mc.updateMetrics(ids, ecID);
				br.close();
			}
			else if (command.equals("terminate")){
				br.close();
				logger.info("FileWatcher: calling terminate method");
				mc.terminate();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			if (br != null){
				try{
					br.close();
				}
				catch(Exception e2){
					
				}
			}
		}
	}

}
