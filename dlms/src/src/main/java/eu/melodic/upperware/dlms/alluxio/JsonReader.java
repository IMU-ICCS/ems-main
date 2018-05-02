/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.dlms.alluxio;

// worker is up at http://localhost:30000/metrics/json
// Master at http://localhost:19999/metrics/json

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

//The file is for mostly handing the code for alluxio
// 1. It will read the metric servlet
// 2. Parse it and make the Json format
// 3. Send to Mule
// Note: Mule.zip is not working correctly so postman stub is used.
// All are defined in applicatio.properties file

public class JsonReader extends Thread {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static void main(String args[]) throws IOException, JSONException {

		// Getting the values from the application.properties file
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
			properties.load(resourceStream);
			// System.out.println(properties.getProperty("metric.servlet"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Extract the data from the servlet
		JSONObject json = readJsonFromUrl(properties.getProperty("metric.servlet"));
		// making the hashmap for storing the Json object
		HashMap<String, String> hmap = new HashMap<String, String>();
		// For testing purpose use the localhost which must be replaced by VM IP
		InetAddress myHost = InetAddress.getLocalHost();
		String localhost = myHost.getHostName();

		/**********************************
		 * Write the Query of interest ***
		 *********************************/
		// query for master.CapacityFree
		JSONObject gauges = json.getJSONObject("gauges");
		JSONObject val = gauges.getJSONObject("worker." + localhost + ".CapacityTotal");

		// String string = (val.toString()).replaceAll("[\\[\\](){}]", "");
		String[] parts = ((val.toString()).replaceAll("[\\[\\](){}]", "")).split(":");
		String part2 = parts[1]; // value
		// adding it to hashmap
		hmap.put("master.CapacityFree", part2);

		// query for master.CapacityFree
		JSONObject counters = json.getJSONObject("gauges");
		val = counters.getJSONObject("worker." + localhost + ".CapacityUsed");
		parts = ((val.toString()).replaceAll("[\\[\\](){}]", "")).split(":");
		part2 = parts[1];
		// adding it to hashmap
		hmap.put("master.CompleteFileOps", part2);

		/********************************************************/

		/* Display content using Iterator */
		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			System.out.print("key is: " + mentry.getKey());
			System.out.println(" & Value is: " + mentry.getValue());
		}

		// converting hashmap to Json again
		JSONObject map2json = new JSONObject(hmap);
		System.out.println(map2json);

		/**********************************
		 * Sending the data to the ESPER *
		 *********************************/
		// sending the data to the ESPER
		// URL is mock from postman.
		// Send json messages using postman to the IP:  147.102.17.105:8083
		// -- Need to confirm
		HttpURLConnection espercon = (HttpURLConnection) ((new URL(properties.getProperty("sink.esper"))
				.openConnection()));
		espercon.setDoOutput(true);
		espercon.setRequestProperty("Content-Type", "application/json");
		espercon.setRequestProperty("Accept", "application/json");
		espercon.setRequestMethod("POST");
		espercon.connect();

		OutputStream os = espercon.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		/* Sending the HashMap data to the Esper */
		osw.write(map2json.toString());
		osw.flush();
		osw.close(); // connection close
	}
}