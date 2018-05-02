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
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Alluxio provides two different Filesystem APIs, a native API and a Hadoop compatible API

public class FileMove {
	private static final Logger LOG = LoggerFactory.getLogger(FileMove.class);

	private void basicFileTests() throws Exception {
		LOG.info("Running tests");
		FileSystem fs = FileSystem.Factory.get();
		int listSize = fs.listStatus(new AlluxioURI("/home/user1/test.txt")).size();
		if (listSize != 1) {
			throw new RuntimeException("Expected 1 path to exist at the root, but found " + listSize);
		}
		FileOutStream outStream = fs.createFile(new AlluxioURI("/home/user/test.txt"));
		outStream.write("abc".getBytes());
		outStream.close();
		FileInStream inStream = fs.openFile(new AlluxioURI("/home/user/test.txt"));
		String result = IOUtils.toString(inStream);
		if (!result.equals("abc")) {
			throw new RuntimeException("Expected abc but got " + result);
		}
		LOG.info("Tests passed");
	}

	public static void main(String[] args) throws Exception {
		FileMove test = new FileMove();
		test.basicFileTests();
	}

}
