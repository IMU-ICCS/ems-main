package com.example.latency.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.w3c.dom.Document;

import com.example.latency.model.DataCenter;
import com.example.latency.model.Location;
import com.example.latency.model.Region;
import com.example.latency.repository.CloudProviderRepository;
import com.example.latency.repository.DataCenterLatencyBandwidthRepository;
import com.example.latency.repository.DataCenterRepository;
import com.example.latency.repository.RegionRepository;

//to enable the autowire functionality
@SpringBootApplication
@EnableJpaAuditing
public class LocationController {

	@Autowired
	private CloudProviderRepository cloudProviderRepository;
	@Autowired
	private DataCenterRepository dataCenterRepository;
	@Autowired
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;
	@Autowired
	private RegionRepository regionRepository;

	// store the longitude and latitude in a hashmap
	Map<String, Location> locationMap = new HashMap<String, Location>();

	public double findLocation(String dc1Name, String dc2Name) throws Exception {
		double retDistance = 0;

		DataCenter dataCenter1 = getDataCenter(dc1Name);
		DataCenter dataCenter2 = getDataCenter(dc2Name);

		Region region1 = getRegion(dataCenter1.getRegionId());
		Region region2 = getRegion(dataCenter2.getRegionId());

		// in form latitude and longitude
		Double latLongs1[] = getLatLongPositions(region1.getLocation());

		// in form latitude and longitude
		Double latLongs2[] = getLatLongPositions(region2.getLocation());

		// find distance in kms between two places using coordinates
		retDistance = computeLatLong(latLongs1[0], latLongs1[1], latLongs2[0], latLongs2[1]);
		return retDistance;
	}

	// get latitude and longitude from API
	public Double[] getLatLongPositions(String address) throws Exception {
		Location location = new Location();

		if (!locationMap.containsKey(address)) {

			int responseCode = 0;
			String api = "http://maps.googleapis.com/maps/api/geocode/xml?address="
					+ URLEncoder.encode(address, "UTF-8") + "&sensor=true";
			URL url = new URL(api);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
			if (responseCode == 200) {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				;
				Document document = builder.parse(httpConnection.getInputStream());
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				XPathExpression expr = xpath.compile("/GeocodeResponse/status");
				String status = (String) expr.evaluate(document, XPathConstants.STRING);
				if (status.equals("OK")) {
					expr = xpath.compile("//geometry/location/lat");
					String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
					expr = xpath.compile("//geometry/location/lng");
					String longitude = (String) expr.evaluate(document, XPathConstants.STRING);

					location.setLatitude(Double.parseDouble(latitude));
					location.setLongitude(Double.parseDouble(longitude));
					locationMap.put(address, location);
//					System.out.println(Double.parseDouble(latitude) + "  " + Double.parseDouble(longitude));
					return new Double[] { Double.parseDouble(latitude), Double.parseDouble(longitude) };
				} else {
//					System.out.println(status + " ");
//					Thread.sleep(3000);
//					getLatLongPositions(address);
					throw new Exception("Error from the API: " + status);
				}
			}
		} else {
			location = locationMap.get(address);
			return new Double[] { location.getLatitude(), location.getLongitude() };
		}
		return null;
	}

	// get distance in kilometers
	public double computeLatLong(double lat1, double lon1, double lat2, double lon2) {
		double ret = 0;
		double theta = lon1 - lon2;
		ret = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		ret = Math.acos(ret);
		ret = rad2deg(ret);
		ret = ret * 60 * 1.1515;

		ret = ret * 1.609344;

		return ret;
	}

	// return the datacenter based on name
	public DataCenter getDataCenter(String name) {
		DataCenter dataCenter = new DataCenter();
		Optional<DataCenter> dc = dataCenterRepository.findByName(name);
		if (dc.isPresent())
			dataCenter = dc.get();
		else
			System.out.println("No datacenter found with that name");
		return dataCenter;
	}

	// return the region based on the datacenter id
	public Region getRegion(long id) {
		Region region = new Region();
		Optional<Region> r = regionRepository.findById(id);
		if (r.isPresent())
			region = r.get();
		else
			System.out.println("No region found with the cloudproviderid");
		return region;
	}

// This function converts decimal degrees to radians
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

//	This function converts radians to decimal degrees
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
