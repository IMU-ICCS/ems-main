/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.REST;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.eclipsesource.json.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecutionwareError;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer.User;
import eu.paasage.upperware.adapter.adaptationmanager.core.AdaptationManager;

public class ExecInterfacer {

	private final static Logger LOGGER = Logger.getLogger(ExecInterfacer.class
			.getName());

	public static final String SIMPLEDEPLOY_PATH = "/api/highlevel";
	public static final String APPLICATIONCREATION_PATH = "/api/application";
	public static final String APPLICATIONINSTALLATION_PATH = "/api/installation";
	public static final String CLEANING_PATH="/api/clean";
	public static final String LOGIN_PATH = "/login";
	public static final String DEFAULT_USER_EMAIL = "john.doe@example.com";
	public static final String DEFAULT_USER_PASSWORD = "admin";
	
	public static final String APPLICATIONUNINSTALLATION_PATH = "/api/uninstallation";
	public static final String APPLICATIONREMOVAL_PATH = "/api/remove";
	public static final String LOGOUT_PATH = "/logout";
	public static final String COMPONENTADDITION_PATH = "/api/addcomponent";
	public static final String COMPONENTREMOVAL_PATH = "/api/removecomponent";
	public static final String COMMUNICATIONADDITION_PATH = "/api/addcommunication";
	public static final String COMMUNICATIONREMOVAL_PATH = "/api/removecommunication";
	public static final String HOSTINGADDITION_PATH = "/api/addhosting/";
	public static final String HOSTINGREMOVAL_PATH = "/api/removehosting/";
	public static final String VMADDITION_PATH = "/api/addvm/";
	public static final String VMREMOVAL_PATH = "/api/removevm/";
	
	JsonObject deployed = null;
	private String baseUrl;
	private CloseableHttpClient client = HttpClients.custom()
			.setDefaultCookieStore(new BasicCookieStore()).build();
	
	private RepresentationFactory representationFactory = new StandardRepresentationFactory()
	.withFlag(RepresentationFactory.COALESCE_ARRAYS);
	
	
	//New API actions and class members (updated 10 April 2015)
//	private User execUser;
//	//private String endpoint;
//	public static final String API_LOGIN = "/api/login";
//	public static final String API_API = "/api/api";
//	public static final String API_APPLICATION = "/api/application";
//	public static final String API_APPLICATIONCOMPONENT = "/api/applicationComponent";
//	public static final String API_CLOUD = "/api/cloud";
//	public static final String API_CLOUDAPI = "/api/cloudApi";
//	public static final String API_CLOUDHARDWARE = "/api/cloudHardware";
//	public static final String API_CLOUDIMAGE = "/api/cloudImage";
//	public static final String API_CLOUDLOCATION = "/api/cloudLocation";
//	public static final String API_CREDENTIAL = "/api/credential";
//	public static final String API_FRONTENDUSER = "/api/frontendUser";
//	public static final String API_HARDWARE = "/api/hardware";
//	public static final String API_IMAGE = "/api/image";
//	public static final String API_INSTANCE = "/api/instance";
//	public static final String API_LIFECYCLECOMPONENT = "/api/lifecycleComponent";
//	public static final String API_USERCREDENTIAL = "/api/userCredential";
//	public static final String API_VIRTUALMACHINE = "/api/virtualMachine";
//	public static final String API_VIRTUALMACHINETEMPLATE = "/api/virtualMachineTemplate";
	
	
	//New API actions and class members (updated 25 November 2015)
	//Not required ones are commented
	private User execUser;
	private ArrayList<Cloud> clouds = new ArrayList<Cloud>();
	public static final String API_LOGIN = "/api/login";
	public static final String API_API = "/api/api";
	public static final String API_APPLICATION = "/api/application";//DONE
	public static final String API_APPLICATIONCOMPONENT = "/api/ac";//DONE
	public static final String API_APPLICATIONINSTANCE = "/api/applicationInstance";//DONE
	public static final String API_CLOUD = "/api/cloud";//need to get its id for /location - DONE
	public static final String API_CLOUDCREDENTIAL = "/api/cloudCredential";
	public static final String API_PORTPROV = "/api/portProv";
	public static final String API_PORTREQ = "/api/portReq";
	public static final String API_COMMUNICATION = "/api/communication";//DONE
	public static final String API_COMMUNICATIONCHANNEL = "/api/communicationChannel";
	//public static final String API_FRONTENDGROUP = "/api/fg";
	//public static final String API_FRONTENDUSER = "/api/frontendUser";
	//public static final String API_GEOLOCATION = "/api/geo";
	public static final String API_HARDWARE = "/api/hardware";
	//public static final String API_HARDWAREOFFER = "/api/hardwareOffer";
	public static final String API_IMAGE = "/api/image";//DONE Need to do getter from multiple locations
	public static final String API_OSVENDOR = "/api/osVendor";
	public static final String API_OS = "/api/os";
	public static final String API_INSTANCE = "/api/instance";//DONE
	//public static final String API_IPADDRESS = "/api/ip";
	public static final String API_LOCATION = "/api/location";//DONE search using /cloud and /cloudUuid => int getSpecificLocation(...)
	public static final String API_LIFECYCLECOMPONENT = "/api/lifecycleComponent";//DONE
	//public static final String API_OPERATINGSYSTEM = "/api/os";
	//public static final String API_OPERATINGSYSTEMVENDOR = "/api/osVendor";
	public static final String API_VIRTUALMACHINE = "/api/virtualMachine";//DONE
	public static final String API_VIRTUALMACHINETEMPLATE = "/api/vmt";//DONE
	
	
	
	public ExecInterfacer(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public ExecInterfacer() {
		//Properties properties = AdaptationManager.getProperties();
		Properties properties = AdaptationManager.loadAndGetProperties();
		this.baseUrl = properties.getProperty("ExecutionwareURL");
		String uname = properties.getProperty("ExecutionwareUname");
		String pass = properties.getProperty("ExecutionwarePwd");
		String tenant = properties.getProperty("ExecutionwareTenant");
		setCloudCredentials(properties);
		try {
			login(uname, pass, tenant);
		} catch (ExecutionwareError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.baseUrl == null || uname==null || pass==null) {
			LOGGER.log(Level.WARNING,
					"ExecutionwareURL/ExecutionwareUname/ExecutionwarePwd property(s) not set; error reaching with Executionware");
		}
	}
	
	
	//Methods for reading Cloud Provider credentials from property file	
    private Set<Object> getAllKeys(Properties prop){
        Set<Object> keys = prop.keySet();
        return keys;
    }

    public int setCloudCredentials(Properties prop){
    	int count = 0;
    	
    	Set<Object> keys = getAllKeys(prop);
        for(Object k:keys){        	
            String CNameKey = (String)k;
            
            int CNamePos = CNameKey.toLowerCase().indexOf("-uname");            
            if(CNamePos > -1){
            	
            	String cloudProvName = CNameKey.substring(0, CNamePos);
            	String cloudUName = prop.getProperty(CNameKey);
            	String cloudPass = null;
            	String cloudEndpoint = null;
            	
            	for(Object l:keys){
            		
            		String key = (String)l;
            		int CPassPos = key.toLowerCase().indexOf(cloudProvName.toLowerCase());
            		if(CPassPos==0 && key.toLowerCase().indexOf("-pass")>-1)
            			cloudPass = prop.getProperty(key);
            		
            		if(CPassPos==0 && key.toLowerCase().indexOf("-endpoint")>-1)
            			cloudEndpoint = prop.getProperty(key);
            	}
            	
            	if(!cloudProvName.equalsIgnoreCase("") && !cloudUName.equalsIgnoreCase("") && cloudPass!=null && cloudEndpoint!=null){
            		clouds.add(new Cloud(cloudProvName, cloudUName, cloudPass, cloudEndpoint));
            		LOGGER.log(Level.INFO, "Retrieved & stored from Adapter Property file " + cloudProvName + " " + cloudUName + " " + cloudPass + " " + cloudEndpoint);
            		count++;
            	}
            }
        }
        LOGGER.log(Level.INFO, "Retrieved from Adapter Property file " + count + " cloud credentials");
    	return count;
    }
    
    public String getCloudUname(String provider){
    	for (Cloud cld: clouds){
    		if(((Cloud)cld).getCloudProvName().toLowerCase().equalsIgnoreCase(provider.toLowerCase())){
    			String uname = ((Cloud)cld).getCloudUname();
    			LOGGER.log(Level.INFO, "Cloud Provider: " + provider + " Username: " + uname);
    			return uname;
    		}
    	}
    	LOGGER.log(Level.WARNING, "Cloud Provider: " + provider + " NOT FOUND");
    	return "";
    }
    
    public String getCloudPass(String provider){
    	for (Cloud cld: clouds){
    		if(((Cloud)cld).getCloudProvName().toLowerCase().equalsIgnoreCase(provider.toLowerCase())){
    			String pass = ((Cloud)cld).getCloudPass();
    			LOGGER.log(Level.INFO, "Cloud Provider: " + provider + " Pass: " + pass);
    			return pass;
    		}
    	}
    	LOGGER.log(Level.WARNING, "Cloud Provider: " + provider + " NOT FOUND");
    	return "";
    }
    
    public String getCloudEndpoint(String provider){
    	for (Cloud cld: clouds){
    		if(((Cloud)cld).getCloudProvName().toLowerCase().equalsIgnoreCase(provider.toLowerCase())){
    			String endpoint = ((Cloud)cld).getCloudEndpoint();
    			LOGGER.log(Level.INFO, "Cloud Provider: " + provider + " Endpoint: " + endpoint);
    			return endpoint;
    		}
    	}
    	LOGGER.log(Level.WARNING, "Cloud Provider: " + provider + " NOT FOUND");
    	return "";
    }
	

	public JsonObject getDeployed() {
		return deployed;
	}

	public void simpleDeploy(JsonObject model) throws ExecutionwareError {
		
		String content= model.toString();
		// Pretty print content using gson
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		JsonParser parser = new JsonParser();
		JsonElement je = parser.parse(content);
		String prettyContent= gson.toJson(je);
		LOGGER.log(Level.INFO,
				"Sending POST with following content:\n"
						+ prettyContent);
		this.deployed = model;
		if (this.baseUrl == null)
			return;

		String url = baseUrl + SIMPLEDEPLOY_PATH;
		HttpPost post = new HttpPost(url);
		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(content);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		reqEntity.setContentType("text/plain");
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int sc = response.getStatusLine().getStatusCode();
		System.out.println("Response Status code is: " + sc);
		LOGGER.log(Level.INFO,
				"Received response with status code:" + sc);
	}

	public void login() throws ExecutionwareError {

		LOGGER.log(Level.INFO, "Login: sending POST");
		
		URI uri;
		try {
			uri = new URI(baseUrl + API_LOGIN);
			HttpUriRequest login = null;
			login = RequestBuilder.post().setUri(uri)
					.addParameter("email", DEFAULT_USER_EMAIL)
					.addParameter("password", DEFAULT_USER_PASSWORD).build();
			CloseableHttpResponse response = client.execute(login);

			int responseCode = response.getStatusLine().getStatusCode();

			if (responseCode != 200) {
				LOGGER.log(Level.SEVERE, "Login: problem logging in to " + uri);
				throw new ExecutionwareError();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String createApplication(String applicationName)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Creating application " + applicationName);
		String url = baseUrl + APPLICATIONCREATION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep = representationFactory.newRepresentation()
				.withProperty("cloudifyName", applicationName)
				.withProperty("displayName", applicationName);
		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Creating application: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		ContentRepresentation representation = null;
		try {
			representation = representationFactory.readRepresentation(
					RepresentationFactory.HAL_JSON, new InputStreamReader(
							response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String applicationId = representation.getLinkByRel("self").getHref();
		return applicationId;
	}

	public void installApplication(String applicationId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Installing application " + applicationId);
		String url = baseUrl + APPLICATIONINSTALLATION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("application", applicationId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Installing application: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	public void clean()
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Cleaning executionware");
		if (this.baseUrl == null)
			return;
		String url = baseUrl + APPLICATIONINSTALLATION_PATH;
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Cleaning Executionware: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	public String removeApplication(String applicationName)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Removing application " + applicationName);
		String url = baseUrl + APPLICATIONREMOVAL_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep = representationFactory.newRepresentation()
				.withProperty("cloudifyName", applicationName)
				.withProperty("displayName", applicationName);
		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Removing application: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		ContentRepresentation representation = null;
		try {
			representation = representationFactory.readRepresentation(
					RepresentationFactory.HAL_JSON, new InputStreamReader(
							response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String applicationId = representation.getLinkByRel("self").getHref();
		return applicationId; 
	}
	
	
	
	public void logout() throws ExecutionwareError {

		LOGGER.log(Level.INFO, "Logout: sending POST");

		URI uri;
		try {
			uri = new URI(baseUrl + LOGOUT_PATH);
			HttpUriRequest logout = null;
			logout = RequestBuilder.post().setUri(uri)
					.addParameter("email", DEFAULT_USER_EMAIL).build();
			CloseableHttpResponse response = client.execute(logout);

			int responseCode = response.getStatusLine().getStatusCode();

			if (responseCode != 200) {
				LOGGER.log(Level.SEVERE, "Logout: problem logging out from " + uri);
				throw new ExecutionwareError();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uninstallApplication(String applicationId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Uninstalling application " + applicationId);
		String url = baseUrl + APPLICATIONUNINSTALLATION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("application", applicationId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Uninstalling application: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	public void removeVM(String vmId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Removing VM " + vmId);
		String url = baseUrl + VMREMOVAL_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("vm1", vmId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Removing VM: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	public String addVM(String vmName)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Adding VM " + vmName);
		String url = baseUrl + VMADDITION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation()
				.withProperty("Name", vmName)
				.withProperty("displayName", vmName);
		
		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		System.out.println("respCode is: " + responseCode);
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding VM: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		ContentRepresentation representation = null;
		try {
			representation = representationFactory.readRepresentation(
					RepresentationFactory.HAL_JSON, new InputStreamReader(
							response.getEntity().getContent()));			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String vmId = representation.getLinkByRel("self").getHref(); 
		return vmId;
	}
	
	public void removeHosting(String hostingId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Removing Hosting " + hostingId);
		String url = baseUrl + HOSTINGREMOVAL_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("hosting1", hostingId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		if (this.baseUrl == null)
			return;
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Removing Hosting: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	public String addHosting(String hostingName)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Adding Hosting " + hostingName);
		String url = baseUrl + HOSTINGADDITION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation()
				.withProperty("Name", hostingName)
				.withProperty("displayName", hostingName); 
						
		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		if (this.baseUrl == null)
			return "hostingName";
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding Hosting: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		ContentRepresentation representation = null;
		try {
			representation = representationFactory.readRepresentation(
					RepresentationFactory.HAL_JSON, new InputStreamReader(
							response.getEntity().getContent()));			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String hostingId = representation.getLinkByRel("self").getHref(); 
		return hostingId;
	}
	
	public void removeComponent(String componentId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Removing Component " + componentId);
		String url = baseUrl + HOSTINGREMOVAL_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("component1", componentId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		if (this.baseUrl == null)
			return;
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Removing Component: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	public String addComponent(String applicationId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Adding component " + applicationId);
		String url = baseUrl + COMPONENTADDITION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("self", applicationId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			System.out.println(reqEntity);		
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		if (this.baseUrl == null)
			return "applicationId";
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding component: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		ContentRepresentation representation = null;
		try {
			representation = representationFactory.readRepresentation(
					RepresentationFactory.HAL_JSON, new InputStreamReader(
							response.getEntity().getContent()));			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String componentId = representation.getLinkByRel("self").getHref();
		return componentId;
	}

	public String addCommunication(String cmmName)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Adding communication " + cmmName);
		String url = baseUrl + COMMUNICATIONADDITION_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation()
				.withProperty("Name", cmmName)
				.withProperty("displayName", cmmName);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		if (this.baseUrl == null)
			return "cmmName";
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding communication: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		ContentRepresentation representation = null;
		try {
			representation = representationFactory.readRepresentation(
					RepresentationFactory.HAL_JSON, new InputStreamReader(
							response.getEntity().getContent()));				
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String commId = representation.getLinkByRel("self").getHref();
		return commId;
	}
	
	public void removeCommunication(String commId)
			throws ExecutionwareError {
		LOGGER.log(Level.INFO, "Removing Communication " + commId);
		String url = baseUrl + HOSTINGREMOVAL_PATH;
		HttpPost post = new HttpPost(url);
		Representation rep=null;
		rep = representationFactory.newRepresentation().withLink("communication1", commId);

		StringEntity reqEntity = null;
		try {
			reqEntity = new StringEntity(
					rep.toString(RepresentationFactory.HAL_JSON));
			reqEntity.setContentType("application/hal+json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("****POSTED: "+rep.toString(RepresentationFactory.HAL_JSON));
		if (this.baseUrl == null)
			return;
		post.setEntity(reqEntity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Removing Communication: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
	}
	
	/*
	 *************************************************************************
	 * New methods for ExecutionWare
	 *************************************************************************
	*/
	
	@SuppressWarnings("deprecation")//for HttpParams & HttpConnectionParams
	public void login(String name, String pass, String tenant) throws ExecutionwareError{

		/*
		 * Authentication Actions
		*/
		
		LOGGER.log(Level.INFO, "Login: sending POST1");
		
		//Request Parameters
		JSONObject credentials = new JSONObject();
        credentials.put("email", name);
        credentials.put("password", pass);
        credentials.put("tenant", tenant);

        
        HttpPost hur = new HttpPost(baseUrl + API_LOGIN);
        hur.addHeader("content-type", "application/json");
        hur.addHeader("accept","application/json");
        try {
			hur.setEntity(new StringEntity(credentials.toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try{
        	// set the connection timeout value to 30 seconds (30000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
        	
	        @SuppressWarnings({ "deprecation", "resource" })
			HttpClient httpClient = new DefaultHttpClient(httpParams);
	        HttpResponse resp = httpClient.execute(hur);
	        HttpEntity respEntity = resp.getEntity();
	        
	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();

        	if(resp.getStatusLine().getStatusCode()==200){
        		
        		execUser =  new User(name, pass, tenant);
	            JSONObject result = (JSONObject)parser.parse(respString);
	            //JSONObject access = (JSONObject)result.get("access");
	            execUser.setCreatedOn((Long)result.get("createdOn"));
	            execUser.setExpiresAt((Long)result.get("expiresAt"));
	            execUser.setToken((String)result.get("token"));
	            execUser.setuserId((Long)result.get("userId"));
	            LOGGER.log(Level.INFO, "Login: success for " + execUser.getUserName() + " id " + execUser.getUserId() + " token: " + execUser.getToken());

        	} else{
				LOGGER.log(Level.SEVERE, "Login: problem logging in to " + baseUrl + API_LOGIN);
				//throw new ExecutionwareError();
        	}
        }catch(Exception ex){ex.printStackTrace();}
	}
	
	public void logout(String name) throws ExecutionwareError {

		LOGGER.log(Level.INFO, "Logout: sending POST");
		
		if(execUser.getUserName().equals(name)){
			execUser = null;
			LOGGER.log(Level.INFO, "Logout: logged out " + name);
		} else{
			LOGGER.log(Level.SEVERE, "Logout: problem logging out. Name: " + name + " not found/logged in");
			throw new ExecutionwareError();
		}
	}
	
	public void renewToken(){
		/*try {
			login(execUser.getUserName(), execUser.getPass(), execUser.getTenant());
		} catch (ExecutionwareError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		/*
		 * Authentication Actions
		*/
		
		LOGGER.log(Level.INFO, "Renewing token");
		
		//Request Parameters
		JSONObject credentials = new JSONObject();
        credentials.put("email", execUser.getUserName());
        credentials.put("password", execUser.getPass());
        credentials.put("tenant", execUser.getTenant());

        
        HttpPost hur = new HttpPost(baseUrl + API_LOGIN);
        hur.addHeader("content-type", "application/json");
        hur.addHeader("accept","application/json");
        try {
			hur.setEntity(new StringEntity(credentials.toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try{
        	
	        @SuppressWarnings({ "deprecation", "resource" })
			HttpClient httpClient = new DefaultHttpClient();
	        HttpResponse resp = httpClient.execute(hur);
	        HttpEntity respEntity = resp.getEntity();
	        
	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();

        	if(resp.getStatusLine().getStatusCode()==200){
        		
        		//execUser =  new User(name, pass, tenant);
	            JSONObject result = (JSONObject)parser.parse(respString);
	            //JSONObject access = (JSONObject)result.get("access");
	            execUser.setCreatedOn((Long)result.get("createdOn"));
	            execUser.setExpiresAt((Long)result.get("expiresAt"));
	            execUser.setToken((String)result.get("token"));
	            execUser.setuserId((Long)result.get("userId"));
	            LOGGER.log(Level.INFO, "Renewed token: username " + execUser.getUserName() + " " + execUser.getToken());

        	} else{
				LOGGER.log(Level.SEVERE, "Renewing token: problem logging in to " + baseUrl + API_LOGIN);
				//throw new ExecutionwareError();
        	}
        }catch(Exception ex){ex.printStackTrace();}
		
		//LOGGER.log(Level.INFO, "Renewed token for " + execUser.getUserName());
	}
	
	public String getUserName(){
		return execUser.getUserName();
	}
	
	private HttpResponse postRequest(String apiExt, Header inHeader, JSONObject inBody) throws IOException{
		
        HttpPost hur = new HttpPost(baseUrl + apiExt);
        hur.addHeader("content-type", "application/json");
        //hur.addHeader("accept","application/json");
        
        if(inHeader != null)
        	hur.addHeader(inHeader);

        if(inBody != null)
        	hur.setEntity(new StringEntity(inBody.toString()));
        
        renewToken();
        //Providing token and the user id for authentication
        renewToken();
        hur.addHeader("X-Auth-Token", execUser.getToken());
        hur.addHeader("X-Auth-UserId", String.valueOf(execUser.getUserId()));
        hur.addHeader("X-Tenant", execUser.getTenant());

        HttpResponse resp = null;
        try{
        
	        HttpClient httpClient = new DefaultHttpClient();
	        resp = httpClient.execute(hur);

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        return resp;
        
	}
	
	private HttpResponse putRequest(String apiExt, Header inHeader, JSONObject inBody) throws IOException{
		
        HttpPut hur = new HttpPut(baseUrl + apiExt);
        hur.addHeader("content-type", "application/json");
        
        if(inHeader != null)
        	hur.addHeader(inHeader);
        
        if(inBody != null)
        	hur.setEntity(new StringEntity(inBody.toString()));
        
        renewToken();
        //Providing token and the user id for authentication
        hur.addHeader("X-Auth-Token", execUser.getToken());
        hur.addHeader("X-Auth-UserId", String.valueOf(execUser.getUserId()));
        hur.addHeader("X-Tenant", execUser.getTenant());

        HttpResponse resp = null;
        
        try{
        
	        HttpClient httpClient = new DefaultHttpClient();
	        resp = httpClient.execute(hur);

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        
        return resp;
	}
	
	private HttpResponse getRequest(String apiExt, Header inHeader) throws IOException{
		
        HttpGet hur = new HttpGet(baseUrl + apiExt);
        
        if(inHeader != null)
        	hur.addHeader(inHeader);
        
        renewToken();
        //Providing token and the user id for authentication
        hur.addHeader("X-Auth-Token", execUser.getToken());
        hur.addHeader("X-Auth-UserId", String.valueOf(execUser.getUserId()));
        hur.addHeader("X-Tenant", execUser.getTenant());

        HttpResponse resp = null;
        
        try{
        
	        HttpClient httpClient = new DefaultHttpClient();
	        resp = httpClient.execute(hur);

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        return resp;
	}

	private HttpResponse deleteRequest(String apiExt, Header inHeader) throws IOException{

        HttpDelete hur = new HttpDelete(baseUrl + apiExt);

        if(inHeader != null)
        	hur.addHeader(inHeader);

        renewToken();
        //Providing token and the user id for authentication
        hur.addHeader("X-Auth-Token", execUser.getToken());
        hur.addHeader("X-Auth-UserId", String.valueOf(execUser.getUserId()));
        hur.addHeader("X-Tenant", execUser.getTenant());

        HttpResponse resp = null;

        try{

	        HttpClient httpClient = new DefaultHttpClient();
	        resp = httpClient.execute(hur);

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        return resp;
	}
	
	public String getMatchingJSONArrayHref(JSONArray jArr, String name){
		
		Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			if(jObj.get("name").toString().toLowerCase().contains(name.toLowerCase()) ||
					name.toLowerCase().contains(jObj.get("name").toString().toLowerCase())){
				JSONArray links= (JSONArray) jObj.get("link");
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					return (String) jObjLinks.get("href");
				}
			}
		}
		return "";
	}
	
	public String getJSONArrayHref(JSONArray jArr, String name){
		
		Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			if(jObj.get("name").toString().equalsIgnoreCase(name)){
				JSONArray links= (JSONArray) jObj.get("link");
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					return (String) jObjLinks.get("href");
				}
			}
		}
		return "";
	}
	
	/**
	 * Don't forget to typecast the return object to the value type
	 * @param jArr the JSON array of the response
	 * @param name of the JSON object block in the array
	 * @param key in the JSON
	 * @return a Object. Please typecast according to the value of the key
	 */
	public Object getJSONArrayKey(JSONArray jArr, String name, String key){
		
		Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			if(jObj.get("name").equals(name)){
				return jObj.get(key);
			}
		}
		return "";
	}
	
	public String trimResponseID(String resp){
		return resp.substring(resp.lastIndexOf('/')+1);
	}
	
/**
 * returns remoteState of a particular resource
 * @param API_RESOURCE the url of a particular resource to query
 * @return true if OK else false
 */
	private boolean queryStateOK(String API_RESOURCE){

		boolean status = false;
		
		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = null;
		HttpEntity respEntity = null;
		try {
			resp = getRequest(API_RESOURCE, null);
			respEntity = resp.getEntity();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException npEx){
			LOGGER.log(Level.SEVERE, "Could not get the resource " + API_RESOURCE);
			npEx.printStackTrace();
		}
        
        String respString;
        JSONParser parser = new JSONParser();
        JSONObject result = null;        
        
		try {
			respString = EntityUtils.toString(respEntity);
	    	if(resp.getStatusLine().getStatusCode()==200){
	    		
	    		result = (JSONObject)parser.parse(respString);
	    		//result = new JSONObject(respString);
//	    		jArr = (JSONArray)parser.parse(respString);
	            
	            String remoteState = (String)result.get("remoteState");
	            
	            if(remoteState != null && remoteState.toLowerCase().equalsIgnoreCase("OK"))
	            	status = true;
	    	}
		} catch (org.apache.http.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return status;
	}
	
	
	public String createAPI(String name) throws ExecutionwareError{
		
		boolean status = false;
		
		HttpResponse resp = null;
		
		try{

			JSONObject inBody = new JSONObject();
			if(name.toLowerCase().contains("openstack")){
				inBody.put("name", "openstack-nova");
		        inBody.put("internalProviderName", "openstack-nova");
			}else if(name.toLowerCase().contains("flexiant")){
				inBody.put("name", "flexiant");
		        inBody.put("internalProviderName", "flexiant");
			}else if(name.toLowerCase().contains("amazon")){
				inBody.put("name", "amazon");
		        inBody.put("internalProviderName", "amazon");
			}

	        resp = postRequest(API_API, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;
	        
        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New API is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		
		int responseCode = resp.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding API: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		
		//return cloudId;
		return "";
	}
	
	public JSONArray getAPIs() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_API, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public String createCloudCredential(String userName, String password, Integer cloud, Integer tenant) throws ExecutionwareError{
		
		boolean status = false;
		
		HttpResponse resp = null;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("user", userName);
	        inBody.put("secret", password);
	        inBody.put("cloud", cloud);
	        inBody.put("tenant", tenant);

	        resp = postRequest(API_CLOUDCREDENTIAL, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;
	        
        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New credential is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		
		int responseCode = resp.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding credential: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		
		//return cloudId;
		return "";
	}

	public String createCloud(String name, String endpoint, Integer api) throws ExecutionwareError{
		
		boolean status = false;
		
		HttpResponse resp = null;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("name", name);
	        inBody.put("endpoint", endpoint);
	        inBody.put("api", api);

	        resp = postRequest(API_CLOUD, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;
	        
        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New cloud is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		
		int responseCode = resp.getStatusLine().getStatusCode();
		if ((responseCode < 200) || (responseCode >= 300)) {
			LOGGER.log(Level.SEVERE,
					"Adding Cloud: Failed with response code "
							+ responseCode);
			throw new ExecutionwareError();
		}
		
		//return cloudId;
		return "";
	}
	
	public boolean updateCloud(int cloudId, String newName) throws IOException, ParseException{
		
		boolean status = false;

		//Header inHeader = new BasicHeader(name, value);
		
		JSONObject inBody = new JSONObject();
		inBody.put("cloud_id", cloudId);
		inBody.put("name", newName);
		
		HttpResponse resp = putRequest(API_CLOUD + "/" + Integer.toString(cloudId), null, inBody);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
            result = (JSONObject)parser.parse(respString);
            status = true;
            
    	}
    	return status;
	}
	
	public JSONArray getClouds() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_CLOUD, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteCloud(int cloudId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("cloud_id", Integer.toString(cloudId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_CLOUD + "/" + Integer.toString(cloudId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
            status = true;
    	}
    	return status;
	}

	public String createApp(String appName){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("name", appName);

	        HttpResponse resp = postRequest(API_APPLICATION, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			//System.out.println("New app is located at " + href);
        			LOGGER.log(Level.INFO, "New app is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){
        	LOGGER.log(Level.SEVERE, "Error creating Application " + appName);
        	ex.printStackTrace();
    	}
		return "";
		//return status;
	}
	
	public JSONArray getApps() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_APPLICATION, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean updateApp(int applicationId,String newAppName) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("application_id", Integer.toString(applicationId));
		
		JSONObject inBody = new JSONObject();
        inBody.put("name", newAppName);
		
		HttpResponse resp = putRequest(API_APPLICATION + "/" + Integer.toString(applicationId), inHeader, inBody);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Updated Application id " + applicationId);
            status = true;
    	}
    	return status;
	}
	
	public boolean deleteApp(int applicationId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("application_id", Integer.toString(applicationId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_APPLICATION + "/" + Integer.toString(applicationId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted Application id " + applicationId);
            status = true;
    	}
    	return status;
	}
	
	public String createLifecycleComponent(String compName, String download, String install, String start, String stop){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("name", compName);
	        inBody.put("download", download);
	        inBody.put("install", install);
	        inBody.put("start", start);
	        inBody.put("stop", stop);

	        HttpResponse resp = postRequest(API_LIFECYCLECOMPONENT, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New lifecycle Component is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getLifecycleComponents() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_LIFECYCLECOMPONENT, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean updateLifecycleComponent(int LCId, String compName, String download, String install, String start, String stop){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("name", compName);
	        inBody.put("download", download);
	        inBody.put("install", install);
	        inBody.put("start", start);
	        inBody.put("stop", stop);

	        HttpResponse resp = putRequest(API_LIFECYCLECOMPONENT + "/" + LCId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("Updated lifecycle Component is located at " + API_LIFECYCLECOMPONENT + "/" + LCId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	
	public boolean deleteLifecycleComponent(int lifecycleComponentId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("lifecycleComponent_id", Integer.toString(lifecycleComponentId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_LIFECYCLECOMPONENT + "/" + Integer.toString(lifecycleComponentId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted Lifecycle Component id " + lifecycleComponentId);
            status = true;
    	}
    	return status;
	}
	
	public String createVirtualMachineTemplate(int cloud, int image, int location, int hardware){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("cloud", Integer.toString(cloud));
	        inBody.put("image", Integer.toString(image));
	        inBody.put("location", Integer.toString(location));
	        inBody.put("hardware", Integer.toString(hardware));

	        HttpResponse resp = postRequest(API_VIRTUALMACHINETEMPLATE, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New virtual machine template entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getVirtualMachineTemplates() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_VIRTUALMACHINETEMPLATE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteVirtualMachineTemplate(int virtualMachineTemplateId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("vmt_id", Integer.toString(virtualMachineTemplateId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_VIRTUALMACHINETEMPLATE + "/" + Integer.toString(virtualMachineTemplateId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted virtual machine template id " + virtualMachineTemplateId);
            status = true;
    	}
    	return status;
	}
	
/*	public String createCloudImage(int cloud, int image, String cloudUuid){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("cloud", Integer.toString(cloud));
	        inBody.put("image", Integer.toString(image));
	        inBody.put("cloudUuid", cloudUuid);

	        HttpResponse resp = postRequest("/api/cloudImage", null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("links");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New CloudImage entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getCloudImages() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest("/api/cloudImage", null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteCloudImage(int cloudImageId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("cloudImage_id", Integer.toString(cloudImageId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest("/api/cloudImage/"+Integer.toString(cloudImageId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted CloudImage entity id " + cloudImageId);
            status = true;
    	}
    	return status;
	}*/
	
	public String createCloudLocation(int cloud, int location, String cloudUuid){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("cloud", Integer.toString(cloud));
	        inBody.put("location", Integer.toString(location));
	        inBody.put("cloudUuid", cloudUuid);

	        HttpResponse resp = postRequest("/api/cloudLocation", null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New CloudLocation entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getCloudLocations() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest("/api/cloudLocation", null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteCloudLocation(int cloudLocationId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("cloudLocation_id", Integer.toString(cloudLocationId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest("/api/cloudLocation/"+Integer.toString(cloudLocationId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted CloudLocation entity id " + cloudLocationId);
            status = true;
    	}
    	return status;
	}
	
	public String createCloudHardware(int cloud, int hardware, String cloudUuid){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("cloud", Integer.toString(cloud));
	        inBody.put("hardware", Integer.toString(hardware));
	        inBody.put("cloudUuid", cloudUuid);

	        HttpResponse resp = postRequest("/api/cloudHardware", null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New CloudHardware entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getCloudHardwares() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest("/api/cloudHardware", null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteCloudHardware(int cloudHardwareId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("cloudHardware_id", Integer.toString(cloudHardwareId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest("/api/cloudHardware/"+Integer.toString(cloudHardwareId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted CloudHardware entity id " + cloudHardwareId);
            status = true;
    	}
    	return status;
	}
	
	public String createHardware(int numberOfCpu, int mbOfRam, int localDiskSpace){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("numberOfCpu", Integer.toString(numberOfCpu));
	        inBody.put("mbOfRam", Integer.toString(mbOfRam));
	        inBody.put("localDiskSpace", Integer.toString(localDiskSpace));

	        HttpResponse resp = postRequest(API_HARDWARE, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New hardware entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getHardwares() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_HARDWARE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	/**
	 * Gets hardware value based on params
	 * @param cloud Id
	 * @param cloudProviderId for the hardware to search
	 * @return hardware value if found, else -1
	 * @throws IOException ioexception
	 * @throws ParseException parseexception
	 */
	public int getSpecificHardware(int cloud, String cloudProviderId) throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_HARDWARE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
		Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			//if(Integer.parseInt((String) jObj.get("cloud"))==cloud && jObj.get("cloudProviderId")==cloudProviderId){
			if(Integer.parseInt(jObj.get("cloud").toString())==cloud && ((String) jObj.get("cloudProviderId")).equalsIgnoreCase(cloudProviderId)){
				
			//if(jObj.get("name").equals(name)){
				JSONArray links= (JSONArray) jObj.get("link");
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					String hard = (String) jObjLinks.get("href");
					return Integer.parseInt(hard.substring(hard.lastIndexOf('/')+1));
				}
			}
		}
		return -1;
    	
    	//System.out.println(respString);
    	//return jArr;
	}
	
	public boolean deleteHardware(int hardwareId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("hardware_id", Integer.toString(hardwareId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_HARDWARE+Integer.toString(hardwareId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted hardware entity id " + hardwareId);
            status = true;
    	}
    	return status;
	}
	
	public String createImage(String name, String cloud, String imageOffer, String cloudUuid, int[] locations, int[] cloudCredentials, int operatingSystem){
		
		boolean status = false;

		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", name);
	        inBody.put("cloud", cloud);
	        inBody.put("imageOffer", imageOffer);
	        inBody.put("cloudUuid", cloudUuid);
	        inBody.put("locations", locations.toString());
	        inBody.put("cloudCredentials", cloudCredentials.toString());
	        inBody.put("operatingSystem", operatingSystem);

	        HttpResponse resp = postRequest(API_IMAGE, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        //{"operatingsystem":["The given operatingsystem is invalid."]}
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New image entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getImages() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_IMAGE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	/**
	 * Gets image value based on params
	 * @param cloud id of ExecWare
	 * @param cloudProviderId of the image to search for
	 * @return image value if found, else -1
	 * @throws IOException ioexception
	 * @throws ParseException parseexception
	 */
	public int getSpecificImage(int cloud, String cloudProviderId/*, String locationID*/) throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_IMAGE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
		Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			
			/*boolean status = false;*/
			
/*			Commented because location is a value and not array
			JSONArray locations = (JSONArray) jObj.get("locations");
			
			for(int i=0; i<locations.size(); i++){
				//JSONObject value = (JSONObject) locations.get(i);
				//System.out.println("The object " + value);
				String Loc = locations.get(i).toString();
				System.out.println("The object " + Loc);
				if(Loc.equalsIgnoreCase(locationID.toString()))
					status = true;
			}*/
			
/*			//Removed the location filtering - not required (parent & assignable)
			String loctn = jObj.get("location").toString();
			if(loctn.equalsIgnoreCase(locationID.toString()))
				status = true;*/
			
			/*Iterator<JSONObject> jArrLocsIt = locations.iterator();
			while (jArrLocsIt.hasNext()){
				System.out.println("The object " + (jArrLocsIt.next()).toString());
				String Loc = (jArrLocsIt.next()).toString();
				if(Loc.equalsIgnoreCase(locationID.toString()))
					status = true;
			}*/
			
			//if(Integer.parseInt((String) jObj.get("cloud"))==cloud && jObj.get("cloudProviderId")==cloudProviderId){
			if(Integer.parseInt(jObj.get("cloud").toString())==cloud && ((String) jObj.get("cloudProviderId")).equalsIgnoreCase(cloudProviderId)/* && status*/){
				
			//if(jObj.get("name").equals(name)){
				JSONArray links= (JSONArray) jObj.get("link");
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					String img = (String) jObjLinks.get("href");
					System.out.println("Found Image " + img);
					return Integer.parseInt(img.substring(img.lastIndexOf('/')+1));
				}
			}
		}
		return -1;
    	
    	//System.out.println(respString);
    	//return jArr;
	}
	
	public boolean updateOSandLoginForSpecificImage(String imgID, String OSVendorType, String login, String OSArchitecture, String OSVersion) throws IOException, ParseException{
		boolean status = false;
		
		JSONObject imgJObj = null;
		
		String vendorID = "", defLogin = "", OSID = "";
		
		HttpResponse resp = getRequest(API_IMAGE + "/" + imgID, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject specImg = null;
        JSONArray jArr = null;
        Iterator<JSONObject> jArrIt;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
    		specImg = (JSONObject)parser.parse(respString);
//    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	if(specImg.get("operatingSystem")!=null && (!specImg.get("operatingSystem").toString().equalsIgnoreCase(""))){
			status = true;
			return status;
		} else{
			imgJObj = specImg;
			status = true;
		} 
    	
		/*String imgOS = specImg.get("operatingSystem").toString();
		
		if((!imgOS.equalsIgnoreCase(null)) || (!imgOS.equalsIgnoreCase(""))){
			status = true;
			return status;
		} else{
			imgJObj = specImg;
			status = true;
		}*/   	

    	
    	
/*    	Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			
			String imgOS = jObj.get("operatingSystem").toString();
			
			if((!imgOS.equalsIgnoreCase(null)) || (!imgOS.equalsIgnoreCase(""))){
				status = true;
				return status;
			} else{
				imgJObj = jObj;
				status = true;
			}
		}*/
		
		//fetching the OS Vendor
		
		resp = getRequest(API_OSVENDOR, null);
        respEntity = resp.getEntity();
        
        respString = EntityUtils.toString(respEntity);
        parser = new JSONParser();
        //JSONObject result = null;
        jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
		jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			
			if(jObj.get("operatingSystemVendorType").toString().equalsIgnoreCase(OSVendorType)){
				
				JSONArray links= (JSONArray) jObj.get("link");
				defLogin = jObj.get("defaultLoginName").toString();
				
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					String vend = (String) jObjLinks.get("href");
					System.out.println("Found vendor " + OSVendorType + " id: " + vend);
					vendorID = vend.substring(vend.lastIndexOf('/')+1);
					status = status && true;
				}
			}
		}
		
		//fetching the OS
		resp = getRequest(API_OS, null);
        respEntity = resp.getEntity();
        
        respString = EntityUtils.toString(respEntity);
        parser = new JSONParser();
        //JSONObject result = null;
        jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
		jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			
			if(jObj.get("operatingSystemVendor").toString().equalsIgnoreCase(vendorID) && jObj.get("operatingSystemArchitecture").toString().equalsIgnoreCase(OSArchitecture) && jObj.get("version").toString().equalsIgnoreCase(OSVersion)){
				
				JSONArray links= (JSONArray) jObj.get("link");
				
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					String os = (String) jObjLinks.get("href");
					System.out.println("Found OS for type: " + OSVendorType +" version: " + OSVersion + " Arch: " + OSArchitecture + " id: " + os);
					OSID = os.substring(os.lastIndexOf('/')+1);
					status = status && true;
				}
			}
		}
		
		if(status == true){//update the image with OS and LoginUsername
			
			
			JSONObject inBody = new JSONObject();
			inBody.put("remoteId", imgJObj.get("remoteId"));
			inBody.put("cloudProviderId", imgJObj.get("cloudProviderId"));
			inBody.put("name", imgJObj.get("name"));
			inBody.put("cloud", imgJObj.get("cloud"));
			inBody.put("location", imgJObj.get("location"));
			inBody.put("cloudCredentials", imgJObj.get("cloudCredentials"));
			inBody.put("operatingSystem", Integer.parseInt(OSID));
			
			if(login != null){
				if(!login.equalsIgnoreCase(defLogin))
					inBody.put("defaultLoginUsername", login);
				else
					inBody.put("defaultLoginUsername", imgJObj.get("defaultLoginUsername"));
			}
			
			resp = putRequest(API_IMAGE + "/" + imgID, null, inBody);
	        respEntity = resp.getEntity();
	        
	        respString = EntityUtils.toString(respEntity);
	        parser = new JSONParser();
	        JSONObject result = null;
	        
	    	if(resp.getStatusLine().getStatusCode()==200){
	        	
	            result = (JSONObject)parser.parse(respString);
	            status = status && true;
	            
	    	} else{
	    		status = false;
	    	}
		}
		
		return status;
	}
	
	public boolean deleteImage(int imageId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("image_id", Integer.toString(imageId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_IMAGE + "/" + Integer.toString(imageId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted image id " + imageId);
            status = true;
    	}
    	return status;
	}
	
	public JSONArray getLocations() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_LOCATION, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	/**
	 * Gets location value based on params
	 * @param cloud id of ExecWare
	 * @param cloudProviderId of the location to search for
	 * @return location value if found, else -1
	 * @throws IOException ioexception
	 * @throws ParseException parseexception
	 */
	public int getSpecificLocation(int cloud, String cloudProviderId) throws IOException, ParseException{
		
		System.out.println("cloud cloudProviderId " + cloud + " " + cloudProviderId);

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_LOCATION, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	cloudProviderId = cloudProviderId.substring(1, cloudProviderId.length()-1);//eliminating the leading and trailing quotes
    	
		Iterator<JSONObject> jArrIt = jArr.iterator();
		while(jArrIt.hasNext()){
			JSONObject jObj = (JSONObject) jArrIt.next();
			//if(Integer.parseInt((String) jObj.get("cloud"))==cloud && jObj.get("cloudProviderId")==cloudProviderId){
			
			//debug lines to test if statement
			int fetchedCloud = Integer.parseInt(jObj.get("cloud").toString());
			String fetchedCloudProviderId = ((String) jObj.get("cloudProviderId")).trim();
/*			if(fetchedCloud==cloud)
				System.out.println("Fetched Cloud # equal");
			if(fetchedCloudProviderId.equalsIgnoreCase(cloudProviderId.trim()))
				System.out.println("Fetched CloudProviderId equal : " + fetchedCloudProviderId + " " + cloudProviderId.trim());*/
			if(fetchedCloud==cloud && fetchedCloudProviderId.equalsIgnoreCase(cloudProviderId.trim())){
			//if(Integer.parseInt(jObj.get("cloud").toString())==cloud && ((String) jObj.get("cloudProviderId")).equalsIgnoreCase(cloudProviderId)){
				
			//if(jObj.get("name").equals(name)){
				JSONArray links= (JSONArray) jObj.get("link");
				Iterator<JSONObject> jArrLinksIt = links.iterator();
				while (jArrLinksIt.hasNext()){
					JSONObject jObjLinks = (JSONObject) jArrLinksIt.next();
					String loc = (String) jObjLinks.get("href");
					return Integer.parseInt(loc.substring(loc.lastIndexOf('/')+1));
				}
			}
		}
		return -1;
    	
    	//System.out.println(respString);
    	//return jArr;
	}
	
	public String createApplicationInstance(int application){
		
		boolean status = false;
		//String API_APPLICATIONINSTANCE = "/api/applicationInstance";
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("application", application);

	        HttpResponse resp = postRequest(API_APPLICATIONINSTANCE, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        //{"virtualMachineTemplate":["The virtual machine template is required."]}
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New ApplicationInstanceComponent entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}

	public boolean updateApplicationInstance(int applicationInstanceId, int application){
		
		boolean status = false;
		//String API_APPLICATIONINSTANCE = "/api/applicationInstance";
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("application", application);

	        HttpResponse resp = putRequest(API_APPLICATIONINSTANCE + "/" + Integer.toString(applicationInstanceId), null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        //{"virtualMachineTemplate":["The virtual machine template is required."]}
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("Updated ApplicationInstanceComponent entity is located at " + href);
        			//return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}

	
	public JSONArray getApplicationInstances() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_APPLICATIONINSTANCE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteApplicationInstance(String applicationInstanceId){
		
		boolean status = false;

		//Header inHeader = new BasicHeader("applicationInstance_id", Integer.toString(applicationInstanceId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = null;
		try {
			resp = deleteRequest(API_APPLICATIONINSTANCE + "/" + Integer.parseInt(applicationInstanceId), null);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpEntity respEntity = resp.getEntity();
        
        try {
			String respString = EntityUtils.toString(respEntity);
		} catch (org.apache.http.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted ApplicationInstance entity id " + applicationInstanceId);
            status = true;
    	}
    	return status;
	}
	
	public String createApplicationComponent(int application, int lifeCycleComponent, int virtualMachineTemplate){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("application", application);
	        inBody.put("component", lifeCycleComponent);
	        inBody.put("virtualMachineTemplate", virtualMachineTemplate);

	        HttpResponse resp = postRequest(API_APPLICATIONCOMPONENT, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        //{"virtualMachineTemplate":["The virtual machine template is required."]}
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New ApplicationComponent entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	
	public boolean updateApplicationComponent(int ACId, int application, int lifeCycleComponent, int virtualMachineTemplate){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("application", application);
	        inBody.put("component", lifeCycleComponent);
	        inBody.put("virtualMachineTemplate", virtualMachineTemplate);

	        HttpResponse resp = putRequest(API_APPLICATIONCOMPONENT + "/" + ACId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        //{"virtualMachineTemplate":["The virtual machine template is required."]}
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("Updated ApplicationComponent entity is located at " + API_APPLICATIONCOMPONENT + "/" + ACId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	
	public JSONArray getApplicationComponents() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_APPLICATIONCOMPONENT, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteApplicationComponent(int applicationComponentId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("applicationComponent_id", Integer.toString(applicationComponentId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_APPLICATIONCOMPONENT + "/" + Integer.toString(applicationComponentId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted ApplicationComponent entity id " + applicationComponentId);
            status = true;
    	}
    	return status;
	}
	
	public String createProviderPort(String portName, int providerAppComponent, int providerPort){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", portName);
	        inBody.put("applicationComponent", providerAppComponent);
	        inBody.put("port", providerPort);

	        HttpResponse resp = postRequest(API_PORTPROV, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New provider port entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	
	public JSONArray getProviderPort() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_PORTPROV, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean updateProviderPort(int provPortId, String portName, int providerAppComponent, int providerPort){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", portName);
	        inBody.put("applicationComponent", providerAppComponent);
	        inBody.put("port", providerPort);

	        HttpResponse resp = putRequest(API_PORTPROV + "/" + provPortId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("Updated provider port entity is located at " + API_PORTPROV + "/" + provPortId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	public boolean deleteProviderPort(int providerPortId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("portProv_id", Integer.toString(providerPortId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_PORTPROV + "/" + Integer.toString(providerPortId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted provider port entity id " + providerPortId);
            status = true;
    	}
    	return status;
	}
	
	public String createConsumerPort(String portName, int consumerAppComponent, int consumerPort, String isMandatory, String requiredPortstartCmd){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", portName);
	        inBody.put("applicationComponent", consumerAppComponent);
	        //inBody.put("port", consumerPort);//NOT required - replicated from provider port by ExecWare
	        inBody.put("isMandatory", isMandatory);
	        inBody.put("updateAction", requiredPortstartCmd);

	        HttpResponse resp = postRequest(API_PORTREQ, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New consumer port entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	
	public JSONArray getConsumerPort() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_PORTREQ, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean updateConsumerPort(int consPortId, String portName, int consumerAppComponent, int consumerPort, String isMandatory, String requiredPortstartCmd){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", portName);
	        inBody.put("applicationComponent", consumerAppComponent);
	        //inBody.put("port", consumerPort);//NOT required - replicated from provider port by ExecWare
	        inBody.put("isMandatory", isMandatory);
	        inBody.put("updateAction", requiredPortstartCmd);

	        HttpResponse resp = putRequest(API_PORTREQ + "/" + consPortId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("Updated consumer port entity is located at " + API_PORTREQ + "/" + consPortId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	public boolean deleteConsumerPort(int consumerPortId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("portReq_id", Integer.toString(consumerPortId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_PORTREQ + "/" + Integer.toString(consumerPortId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted consumer port entity id " + consumerPortId);
            status = true;
    	}
    	return status;
	}
	
	
	public String createCommunication(int providedPort, int requiredPort){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("providedPort", providedPort);
	        inBody.put("requiredPort", requiredPort);

	        HttpResponse resp = postRequest(API_COMMUNICATION, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New communication entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public boolean updateCommunication(int commId, int providedPort, int requiredPort){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("providedPort", providedPort);
	        inBody.put("requiredPort", requiredPort);

	        HttpResponse resp = postRequest(API_COMMUNICATION + "/" + commId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("New communication entity is located at " + API_COMMUNICATION + "/" + commId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	public String createCommunication(int providerAppComponent, int consumerAppComponent, int port){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("provider", providerAppComponent);
	        inBody.put("consumer", consumerAppComponent);
	        inBody.put("port", port);

	        HttpResponse resp = postRequest(API_COMMUNICATION, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New communication entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getCommunications() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_COMMUNICATION, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	
	public boolean updateCommunication(int commId, int providerAppComponent, int consumerAppComponent, int port){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("provider", providerAppComponent);
	        inBody.put("consumer", consumerAppComponent);
	        inBody.put("port", port);

	        HttpResponse resp = postRequest(API_COMMUNICATION + "/" + commId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("New communication entity is located at " + API_COMMUNICATION + "/" + commId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	public boolean deleteCommunication(int communicationId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("communication_id", Integer.toString(communicationId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_COMMUNICATION + "/" + Integer.toString(communicationId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted communication entity id " + communicationId);
            status = true;
    	}
    	return status;
	}
	
	public String createCommunicationChannel(int providerInstance, int consumerInstance, int communicationId){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
	        inBody.put("provider", providerInstance);
	        inBody.put("consumer", consumerInstance);
	        inBody.put("communication", communicationId);

	        HttpResponse resp = postRequest(API_COMMUNICATIONCHANNEL, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New communication channel entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getCommunicationChannels() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_COMMUNICATIONCHANNEL, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	public boolean deleteCommunicationChannel(int communicationChannelId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("communicationChannel_id", Integer.toString(communicationChannelId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_COMMUNICATIONCHANNEL + "/" + Integer.toString(communicationChannelId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted communication channel entity id " + communicationChannelId);
            status = true;
    	}
    	return status;
	}
	
	public String createVirtualMachine(String name, int cloud, int image, int hardware, int location){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", name);
	        inBody.put("cloud", cloud);
	        inBody.put("image", image);
	        inBody.put("hardware", hardware);
	        inBody.put("location", location);
	        
	        //delete after bugfix by Daniel
	        //inBody.put("remoteId", name);
	        //inBody.put("cloudProviderId", "regionOne");	        	

	        HttpResponse resp = postRequest(API_VIRTUALMACHINE, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New virtual machine entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	
	public boolean updateVirtualMachine(int virtualMachineId, String name, int cloud, int image, int hardware, int location){
		
		boolean status = false;
		
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("name", name);
	        inBody.put("cloud", cloud);
	        inBody.put("image", image);
	        inBody.put("hardware", hardware);
	        inBody.put("location", location);
	        
	        //delete after bugfix by Daniel
	        //inBody.put("remoteId", name);
	        //inBody.put("cloudProviderId", "regionOne");	        	

	        HttpResponse resp = postRequest(API_VIRTUALMACHINE + "/" + Integer.toString(virtualMachineId), null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("Updated virtual machine entity is located at " + API_VIRTUALMACHINE + "/" + Integer.toString(virtualMachineId));
        		status = true;
        		
        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	
	public JSONArray getVirtualMachines() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_VIRTUALMACHINE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	/**
	 * returns the remoteState of a particular VM
	 * @param virtualMachineId VM id for state query
	 * @return true if OK else false
	 */
	public boolean queryStateOKVM(int virtualMachineId){
		return queryStateOK(API_VIRTUALMACHINE + "/" + virtualMachineId);
	}

	
	public boolean deleteVirtualMachine(int virtualMachineId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("virtualMachine_id", Integer.toString(virtualMachineId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_VIRTUALMACHINE + "/" + Integer.toString(virtualMachineId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted virtual machine id " + virtualMachineId);
            status = true;
    	}
    	return status;
	}
	
	public String createInstance(int applicationInstance, int applicationComponent, int virtualMachine){
		
		boolean status = false;
		//String API_INSTANCE = "/api/instance";
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("applicationInstance", Integer.toString(applicationInstance));
	        inBody.put("applicationComponent", Integer.toString(applicationComponent));
	        inBody.put("virtualMachine", Integer.toString(virtualMachine));

	        HttpResponse resp = postRequest(API_INSTANCE, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

	            //JSONObject result = (JSONObject)parser.parse(respString);
	            //execUser.setCreatedOn((long)result.get("createdOn"));

        		try {
        			obj = parser.parse(new String(respString));
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		JSONObject jObj = (JSONObject) obj;
        		
        		// loop array
        		JSONArray links = (JSONArray) jObj.get("link");
        		Iterator<JSONObject> iterator = links.iterator();
        		while (iterator.hasNext()) {
        			JSONObject factObj = (JSONObject) iterator.next();
        			String href = (String) factObj.get("href");
        			System.out.println("New instance entity is located at " + href);
        			return href;
        		}
        		//System.out.println("New cloud id is located at " + cloudId);
        		status = true;

        	}
        }catch(Exception ex){ex.printStackTrace();}
		return "";
		//return status;
	}
	
	public JSONArray getInstances() throws IOException, ParseException{

		//Header inHeader = new BasicHeader(name, value);
		
		HttpResponse resp = getRequest(API_INSTANCE, null);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        //JSONObject result = null;
        JSONArray jArr = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	
    		//result = new JSONObject(respString);
//            result = (JSONObject)parser.parse(respString);
    		jArr = (JSONArray)parser.parse(respString);
    	}
    	
    	System.out.println(respString);
    	return jArr;
	}
	
	/**
	 * returns remoteState of a particular Instance
	 * @param instanceId id of the VM for state query
	 * @return true if OK else false
	 */
	public boolean queryStateOKInstance(int instanceId){
		return queryStateOK(API_INSTANCE + "/" + instanceId);
	}
	
	
	public boolean updateInstance(int instanceId, int applicationInstance, int applicationComponent, int virtualMachine){
		
		boolean status = false;
		//String API_INSTANCE = "/api/instance";
		try{

			JSONObject inBody = new JSONObject();
			inBody.put("applicationInstance", Integer.toString(applicationInstance));
	        inBody.put("applicationComponent", Integer.toString(applicationComponent));
	        inBody.put("virtualMachine", Integer.toString(virtualMachine));

	        HttpResponse resp = putRequest(API_INSTANCE + "/" + instanceId, null, inBody);
	        HttpEntity respEntity = resp.getEntity();

	        String respString = EntityUtils.toString(respEntity);
	        JSONParser parser = new JSONParser();
	        Object obj = null;

        	if(resp.getStatusLine().getStatusCode()==200){

        		System.out.println("Updated instance entity is located at " + API_INSTANCE + "/" + instanceId);
        		status = true;
        	}
        }catch(Exception ex){ex.printStackTrace();}
		//return "";
		return status;
	}
	
	public boolean deleteInstance(int instanceId) throws IOException, ParseException{
		
		boolean status = false;

		Header inHeader = new BasicHeader("instance_id", Integer.toString(instanceId));
		
		//JSONObject inBody = new JSONObject();
		//inBody.put("cloud_id", cloudId);
		
		HttpResponse resp = deleteRequest(API_INSTANCE + Integer.toString(instanceId), inHeader);
        HttpEntity respEntity = resp.getEntity();
        
        String respString = EntityUtils.toString(respEntity);
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
    	if(resp.getStatusLine().getStatusCode()==200){
        	System.out.println("Deleted instance id " + instanceId);
            status = true;
    	}
    	return status;
	}
	
	class User{

		String name;
		String pass;
		String tenant;
		
		//token details
		private long createdOn;
		private long expiresAt;
		private String token;
		private long userId;
		
		User(String name, String pass, String tenant){
			this.name = new String(name);
			this.pass = new String(pass);
			this.tenant = new String(tenant);
		}
		
		void setCreatedOn(long createdOn){
			this.createdOn = createdOn;
		}
		
		void setExpiresAt(long expiresAt){
			this.expiresAt = expiresAt;
		}
		
		void setToken(String token){
			this.token = new String(token);
		}
		
		public String getToken(){
			return token;
		}
		
		public void setuserId(long userId){
			this.userId = userId;
		}
		
		public void setTenant(String tenant){
			this.tenant = tenant;
		}
		
		protected String getUserName(){
			return name;
		}
		
		protected String getPass(){
			return pass;
		}
		
		protected String getTenant(){
			return this.tenant;
		}
		
		public long getUserId(){
			return userId;
		}
	}
	
	class Cloud{

		String providerName;
		String uname;
		String pass;
		String endpoint;
		
		Cloud(String providerName, String uname, String pass, String endpoint){
			this.providerName = providerName;
			this.uname = new String(uname);
			this.pass = new String(pass);
			if(endpoint.equalsIgnoreCase("optional"))
				this.endpoint = endpoint;
		}
		
		protected String getCloudProvName() {
			return this.providerName;
		}
		
		protected String getCloudUname() {
			return this.uname;
		}
		
		protected String getCloudPass(){
			return this.pass;
		}
		
		protected String getCloudEndpoint(){
			return this.endpoint;
		}
	}
}
