/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.util;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientBuilder;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *  Colosseum Utilities
 *
 *  INSTEAD OF DOCUMENTATION USE COLOSSEUM CLIENT CODE:
 *  Colosseum Client code:
 *		https://github.com/cloudiator/colosseum-client/tree/master/src/main/java/de/uniulm/omi/cloudiator/colosseum/client
 */
@Slf4j
public class CloudiatorUtil 
{
	//static { try { java.util.logging.LogManager.getLogManager().readConfiguration( CloudiatorUtil.class.getResourceAsStream("/logging.properties") ); } catch (IOException ex) { throw new RuntimeException(ex); } }
	
	public static final String DEFAULT_CLOUDIATOR_PROPERTIES = "/cloudiator.properties";
	public static final String DEFAULT_PROVIDER_ENDPOINT_PATTERNS = "/provider-endpoint-patterns.txt";
	public static final String DEFAULT_PROVIDER_LOCATION_PATTERNS = "/provider-location-patterns.txt";
	
	private static Object _CLASS_LOCK = new Object();
	private static CloudiatorUtil instance = null;
	protected static Vector<PatternPair> providerEndpointPatterns;
	protected static Vector<PatternPair> providerLocationPatterns;
	
	private Properties config = null;
	private Client client = null;
	
	protected CloudiatorUtil() {
		try {
			try (InputStream in = getClass().getResourceAsStream(DEFAULT_CLOUDIATOR_PROPERTIES)) {
				Properties cfg = new Properties();
				cfg.load(in);
				this.config = cfg;
				
				String colosseumEndpoint = cfg.getProperty("colosseum.endpoint");
				String email = cfg.getProperty("colosseum.auth.email");
				String tenant = cfg.getProperty("colosseum.auth.tenant");
				String password = cfg.getProperty("colosseum.auth.password");
				this.client = ClientBuilder.getNew().url(colosseumEndpoint).credentials(email, tenant, password).build();
			}
		} catch (IOException ex) {
			log.error("EXCEPTION when reading properties from file: {}", DEFAULT_CLOUDIATOR_PROPERTIES, ex);
		}
		
		synchronized (_CLASS_LOCK) {
			if (providerEndpointPatterns!=null) {
				loadPatterns();
			}
		}
	}
	
	protected CloudiatorUtil(String colosseumEndpoint, String email, String tenant, String password) {
		this.client = ClientBuilder.getNew().url(colosseumEndpoint).credentials(email, tenant, password).build();
	}
	
	public static CloudiatorUtil getInstance() {
		if (instance==null) instance = new CloudiatorUtil();
		return instance;
	}
	
	public static CloudiatorUtil getInstance(String colosseumEndpoint, String email, String tenant, String password) {
		if (instance==null) instance = new CloudiatorUtil(colosseumEndpoint, email, tenant, password);
		return instance;
	}
	
	
	public VmCloudInfo findVmInfoUsingIpAddress(String searchIpAddress, boolean searchIpAddressIsPublic) {
		log.info("Looking up VM info using IP address:\n\t {} {}", searchIpAddress, searchIpAddressIsPublic ? "/ PUBLIC" : "/ PUBLIC-or-PRIVATE" );
		
		// Find VM instance using IP address
		long foundVm = -1;
		List<IpAddress> ipAddressList = client.controller(IpAddress.class).getList();
		for (IpAddress ipAddr : ipAddressList) {
			if (ipAddr.getIp().equals(searchIpAddress) && (!searchIpAddressIsPublic || searchIpAddressIsPublic && "PUBLIC".equals(ipAddr.getIpType()))) {
				foundVm = ipAddr.getVirtualMachine();
				break;
			}
		}
		log.info("Found VM with Id:\n\t {}\n", foundVm);
		
		if (foundVm<0) {
			log.error("** Could not find IP address: {}", searchIpAddress);
			return null;
		}
		
		// Retrieve VM instance info
		VirtualMachine vm = client.controller(VirtualMachine.class).get(foundVm);
		if (vm==null) {
			log.error("** Could not find VM with IP address: {}", searchIpAddress);
			return null;
		} else {
			log.info("VM:\n\t id={},\n\t name={},\n\t remote-state={},\n\t location={},\n\t cloud={},\n\t provider-id={}\n",
					vm.getId(), vm.getName(), vm.getRemoteState(), vm.getLocation(), vm.getCloud(), vm.getProviderId());
		}
		
		VmCloudInfo response = new VmCloudInfo();
		response.ipAddress = searchIpAddress;
		response.vm = vm;
		
		// Retrieve Cloud info
		if (vm.getCloud()>=0) {
			Cloud cloud = client.controller(Cloud.class).get(vm.getCloud());
			if (cloud==null) {
				log.error("** Could not find Cloud with id: {}  -->  VM with IP address: {}", vm.getCloud(), searchIpAddress);
			} else {
				log.info("Cloud:\n\t id={},\n\t name={},\n\t endpoint={}\n",
						cloud.getId(), cloud.getName(), cloud.getEndpoint());
				log.info("Cloud Provider:\n\t {}\n", getCloudProviderByEndpoint(cloud.getEndpoint()));
				response.cloud = cloud;
				response.providerName = getCloudProviderByEndpoint(cloud.getEndpoint());
				log.info("\t provider-name={}\n", response.providerName);
			}
		} else {
			log.error("** No Cloud info in VM with IP address: {}", searchIpAddress);
		}
		
		// Retrieve Location info
		if (vm.getLocation()>=0) {
			Location loc = client.controller(Location.class).get(vm.getLocation());
			if (loc==null) {
				log.error("** Could not find Location with id: {}  -->  VM with IP address: {}", vm.getLocation(), searchIpAddress);
			} else {
				log.info("Location:\n\t id={},\n\t name={},\n\t parent-id={}\n",
						loc.getId(), loc.getName(), loc.getParent());
				response.location = loc;
				if (loc.getName()!=null) {
					response.locationName = getCloudLocationByName(loc.getName());
					log.info("\t location-name={}\n", response.locationName);
				}
			}
		} else {
			log.error("** No Location info in VM with IP address: {}", searchIpAddress);
		}
		
		return response;
    }
	
	// Methods for translating Endpoints to Human readable strings (using patterns from config files)
	protected static void loadPatternsFromFile(String file, Vector<PatternPair> config) {
		try {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(CloudiatorUtil.class.getResourceAsStream(file)))) {
				config.clear();
				String line;
				int ln = 0;
				while ((line = in.readLine()) != null) {
					ln++;
					log.debug("loadPatterns: File={}: Line={}. {}", file, ln, line);
					line = line.trim();
					if (!line.isEmpty() && line.charAt(0)!='#') {
						int p1 = line.lastIndexOf(" ");
						int p2 = line.lastIndexOf("\t");
						int p = (p1>p2) ? p1 : p2;
						if (p>0) {
							String patStr = line.substring(0,p).trim();
							String patName= line.substring(p).trim();
							log.debug("loadPatterns: File={}: Pattern={}: Provider={}", file, patStr, patName);
							PatternPair pp = new PatternPair( Pattern.compile(patStr), patName);
							config.add(pp);
						}
					}
				}
			}
		} catch (IOException ex) {
			log.error("EXCEPTION when reading patterns from file: {}", file, ex);
		}
	}
	
	protected static void loadPatterns() {
		providerEndpointPatterns = new Vector<PatternPair>();
		providerLocationPatterns = new Vector<PatternPair>();
		loadPatternsFromFile(DEFAULT_PROVIDER_ENDPOINT_PATTERNS, providerEndpointPatterns);
		loadPatternsFromFile(DEFAULT_PROVIDER_LOCATION_PATTERNS, providerLocationPatterns);
	}
	
	protected static String getCloudProviderByEndpoint(String endpoint) {
		_checkIfCloudPatternsAreLoaded();
		return _getCloudPatternByString(endpoint, providerEndpointPatterns);
	}
	
	protected static String getCloudLocationByName(String name) {
		_checkIfCloudPatternsAreLoaded();
		return _getCloudPatternByString(name, providerLocationPatterns);
	}
	
	protected static void _checkIfCloudPatternsAreLoaded() {
		synchronized (_CLASS_LOCK) {
			if (providerEndpointPatterns==null) {
				loadPatterns();
			}
		}
	}
	
	protected static String _getCloudPatternByString(String endpoint, Vector<PatternPair> config) {
		Vector<PatternPair> copy = (Vector<PatternPair>)config.clone();
		for (PatternPair pp : copy) {
			if (pp.pattern.matcher(endpoint).matches()) return pp.name;
		}
		return null;
	}
	
	// Member classes
	protected static class PatternPair {
		public Pattern pattern;
		public String name;
		public PatternPair(Pattern pat, String name) {
			this.pattern = pat;
			this.name = name;
		}
	}
	
	public static class VmCloudInfo {
		public String ipAddress;
		public VirtualMachine vm;
		public Location location;
		public Cloud cloud;
		public String providerName;
		public String locationName;
	}
	
	// ==================================================================================
	
/*	protected final static String CLOUDIATOR_DB_QUERY_VM_INFO =
			"SELECT vm.id AS vm_id, vm.name AS vm_name, ip.ip AS vm_ip, cl.name AS vm_cloud, " +
			"	vm.generatedLoginUsername AS vm_username, vm.generatedLoginPassword AS vm_password, vm.generatedPrivateKey AS vm_private_key, " +
			"	loc.id AS loc_id, loc.name AS loc_name, loc.locationScope AS loc_scope, " +
			"	hw.id AS hw_id, hw.name AS hw_name, hwo.localDiskSpace AS hwo_disk, hwo.mbOfRam AS hwo_memory, hwo.numberOfCores AS hwo_cores, " +
			"	im.id AS im_id, im.name AS im_name, " +
			"	os.id AS os_id, os.operatingSystemArchitecture AS os_arch, os.operatingSystemFamily AS os_family, os.version AS os_version " +
			"FROM VirtualMachine vm1 " +
			"	INNER JOIN VirtualMachine vm " +
			"		ON vm1.id = vm.id " +
			"	INNER JOIN IpAddress ip " +
			"		ON vm.id=ip.virtualMachine_id " +
			"		AND ip.ipType = 'PUBLIC' " +
			"	INNER JOIN Cloud cl " +
			"		ON vm.cloud_id = cl.id " +
			"	LEFT JOIN Location loc " +
			"		ON vm.location_id = loc.id " +
			"	INNER JOIN Hardware hw " +
			"		ON vm.hardware_id = hw.id " +
			"	LEFT JOIN HardwareOffer hwo " +
			"		ON hw.hardwareOffer_id = hwo.id " +
			"	INNER JOIN Image im " +
			"		ON vm.image_id = im.id " +
			"	LEFT JOIN OperatingSystem os " +
			"		ON im.operatingSystem_id = os.id " +
			"WHERE vm.owner_id = ? " +
			"ORDER BY vm_id ";
	
	public List<DbVmInfo> getVmInfo() {
		String dbDriver = config.getProperty("colosseum.db.driver");
		String dbUrl = config.getProperty("colosseum.db.url");
		String dbUser = config.getProperty("colosseum.db.username");
		String dbPass = config.getProperty("colosseum.db.password");
		String dbName = config.getProperty("colosseum.db.database");
		
		int ownerId = Integer.parseInt( config.getProperty("colosseum.owner-id") );
		Connection con = null;
		
		Vector<DbVmInfo> info = new Vector<DbVmInfo>();
		try {
			Class.forName(dbDriver);
			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			
			PreparedStatement stmt=con.prepareStatement( CLOUDIATOR_DB_QUERY_VM_INFO );
			stmt.setInt( 1, ownerId );
			ResultSet rs = stmt.executeQuery();
			
			//ResultSetMetaData metadata = rs.getMetaData();
			//int colCount = metadata.getColumnCount();
			while(rs.next()) {
				/*System.out.printf("VIRTUAL MACHINE: %s  %s\n", rs.getString("vm_id"), rs.getString("vm_name"));
				for (int i=1; i<=colCount; i++) {
					System.out.printf("\t%s:\t%s\n", metadata.getColumnLabel(i), rs.getString(i));
				}*/
/*				info.add( new DbVmInfo(rs) );
			}
			log.debug("getVmInfo: {}", info);
			
		} catch(Exception e) {
			log.error("getVmInfo: EXCEPTION: {}", e);
		} finally {
			if (con!=null) {
				try { con.close(); } catch (Exception ex) { log.error("getVmInfo: EXCEPTION while closing connection: {}", ex); }
				con = null;
			}
		}
		return info;
	}
	
	@ToString(exclude={"vmPassword","vmPrivateKey"}, includeFieldNames=true)
	public static class DbVmInfo {
		public long vmId;
		public String vmName;
		public String vmIpAddress;
		public String vmCloud;
		public String vmUsername;
		public String vmPassword;
		public String vmPrivateKey;
		public long locId;
		public String locName;
		public String locScope;
		public long hwId;
		public String hwName;
		public double hwoDisk;
		public double hwoMemory;
		public int hwoCores;
		public long imId;
		public String imName;
		public long osId;
		public String osArch;
		public String osFamily;
		public String osVersion;
		
		public DbVmInfo() {}
		public DbVmInfo(ResultSet rs) throws SQLException {
			vmId = rs.getLong("vm_id");
			vmName = rs.getString("vm_name");
			vmIpAddress = rs.getString("vm_ip");
			vmCloud = rs.getString("vm_cloud");
			vmUsername = rs.getString("vm_username");
			vmPassword = rs.getString("vm_password");
			vmPrivateKey = rs.getString("vm_private_key");
			locId = rs.getLong("loc_id");
			locName = rs.getString("loc_name");
			locScope = rs.getString("loc_scope");
			hwId = rs.getLong("hw_id");
			hwName = rs.getString("hw_name");
			hwoDisk = rs.getDouble("hwo_disk");
			hwoMemory = rs.getDouble("hwo_memory");
			hwoCores = rs.getInt("hwo_cores");
			imId = rs.getLong("im_id");
			imName = rs.getString("im_name");
			osId = rs.getLong("os_id");
			osArch = rs.getString("os_arch");
			osFamily = rs.getString("os_family");
			osVersion = rs.getString("os_version");
		}
	}*/
}
