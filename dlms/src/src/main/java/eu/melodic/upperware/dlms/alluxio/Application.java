/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.dlms.alluxio;

import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import alluxio.AlluxioURI;
import alluxio.client.WriteType;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;
import alluxio.client.file.options.CreateFileOptions;
import alluxio.exception.AlluxioException;
import alluxio.exception.FileAlreadyExistsException;
import alluxio.exception.InvalidPathException;

// The class will use Alluxio to create a file
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner()
			throws FileAlreadyExistsException, InvalidPathException, AlluxioException {
		try {
			String string = "TEST";
			// Convert to byte[]
			byte[] bytes = string.getBytes();
			FileSystem fs = FileSystem.Factory.get();
			AlluxioURI path = new AlluxioURI("/test.txt");
			// Create a file and get its output stream
			CreateFileOptions options = CreateFileOptions.defaults().setWriteType(WriteType.CACHE_THROUGH);
			FileOutStream out = fs.createFile(path, options);
			System.out.println("done");
			// Write data
			out.write(bytes);
			// Close and complete file
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
