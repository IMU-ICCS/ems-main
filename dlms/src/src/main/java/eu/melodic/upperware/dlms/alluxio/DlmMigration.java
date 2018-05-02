/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.dlms.alluxio;

import alluxio.AlluxioURI;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;

// Below code for porting an application when data access moves from HDFS to S3. 
// data moves from HDFS to S3, the only part of the application that needs to be updated is the <url> to mount, 
// which can be read from configuration

public class DlmMigration { 

// mount a storage system (HDFS or S3) to Alluxio
FileSystem fileSystem = FileSystem.Factory.get();
// URL must be placed inside the configuration file of Alluxio
fileSystem.mount("/mnt/data", <url>;)

// reading data
AlluxioURI uri = new AlluxioURI("/mnt/data/...");
FileInStream is = fileSystem.openFile(uri);
is.close();

// perform computation

// writing data
AlluxioURI uri = new AlluxioURI("/mnt/data/...");
FileOutStream os = fileSystem.createFile(uri);
os.close();
}