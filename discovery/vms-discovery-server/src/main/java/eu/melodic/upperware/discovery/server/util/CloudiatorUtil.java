/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.server.util;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientBuilder;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
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
	static { try { java.util.logging.LogManager.getLogManager().readConfiguration( CloudiatorUtil.class.getResourceAsStream("/logging.properties") ); } catch (IOException ex) { throw new RuntimeException(ex); } }
	
	public static final String DEFAULT_CLOUDIATOR_PROPERTIES = "/cloudiator.properties";
	private static CloudiatorUtil instance = null;
	
	private Client client = null;
	
	protected CloudiatorUtil() {
		try {
			try (InputStream in = getClass().getResourceAsStream(DEFAULT_CLOUDIATOR_PROPERTIES)) {
				Properties cfg = new Properties();
				cfg.load(in);
				
				String colosseumEndpoint = cfg.getProperty("colosseum.endpoint");
				String email = cfg.getProperty("colosseum.auth.email");
				String tenant = cfg.getProperty("colosseum.auth.tenant");
				String password = cfg.getProperty("colosseum.auth.password");
				this.client = ClientBuilder.getNew().url(colosseumEndpoint).credentials(email, tenant, password).build();
			}
		} catch (IOException ex) {
			log.error("EXCEPTION when reading properties from file: {}", DEFAULT_CLOUDIATOR_PROPERTIES, ex);
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
		System.out.printf("Looking up VM info using IP address:\n\t %s %s\n", searchIpAddress, searchIpAddressIsPublic ? "/ PUBLIC" : "/ PUBLIC-or-PRIVATE" );
		
		// Find VM instance using IP address
		long foundVm = -1;
		List<IpAddress> ipAddressList = client.controller(IpAddress.class).getList();
		for (IpAddress ipAddr : ipAddressList) {
			if (ipAddr.getIp().equals(searchIpAddress) && (!searchIpAddressIsPublic || searchIpAddressIsPublic && ipAddr.getIpType().equals("PUBLIC"))) {
				foundVm = ipAddr.getVirtualMachine();
				break;
			}
		}
		System.out.printf("Found VM with Id:\n\t %d\n", foundVm);
		
		if (foundVm<0) {
			System.err.println("** Could not find IP address: "+searchIpAddress);
			return null;
		}
		
		// Retrieve VM instance info
		VirtualMachine vm = client.controller(VirtualMachine.class).get(foundVm);
		if (vm==null) {
			System.err.println("** Could not find VM with IP address: "+searchIpAddress);
			return null;
		} else {
			System.out.printf("VM:\n\t id=%d,\n\t name=%s,\n\t remote-state=%s,\n\t location=%d,\n\t cloud=%d,\n\t provider-id=%s\n",
					vm.getId(), vm.getName(), vm.getRemoteState(), vm.getLocation(), vm.getCloud(), vm.getProviderId());
		}
		
		VmCloudInfo response = new VmCloudInfo();
		response.ipAddress = searchIpAddress;
		response.vm = vm;
		
		// Retrieve Location info
		if (vm.getLocation()>=0) {
			Location loc = client.controller(Location.class).get(vm.getLocation());
			if (loc==null) {
				System.err.println("** Could not find Location with id: "+vm.getLocation()+"  -->  VM with IP address: "+searchIpAddress);
			} else {
				System.out.printf("Location:\n\t id=%d,\n\t name=%s,\n\t parent-id=%d\n",
						loc.getId(), loc.getName(), loc.getParent());
				response.location = loc;
			}
		} else {
			System.err.println("** No Location info in VM with IP address: "+searchIpAddress);
		}
		
		// Retrieve Cloud info
		if (vm.getCloud()>=0) {
			Cloud cloud = client.controller(Cloud.class).get(vm.getCloud());
			if (cloud==null) {
				System.err.println("** Could not find Cloud with id: "+vm.getCloud()+"  -->  VM with IP address: "+searchIpAddress);
			} else {
				System.out.printf("Cloud:\n\t id=%d,\n\t name=%s,\n\t endpoint=%s\n",
						cloud.getId(), cloud.getName(), cloud.getEndpoint());
				System.out.printf("Cloud Provider:\n\t %s\n", getCloudProviderByEndpoint(cloud.getEndpoint()));
				response.cloud = cloud;
				response.providerName = getCloudProviderByEndpoint(cloud.getEndpoint());
			}
		} else {
			System.err.println("** No Cloud info in VM with IP address: "+searchIpAddress);
		}
		
		return response;
    }
	
	protected static String getCloudProviderByEndpoint(String endpoint) {
		if (endpoint.endsWith("amazonaws.com")) return "AWS";
		return endpoint;
	}
	
	public static class VmCloudInfo {
		public String ipAddress;
		public VirtualMachine vm;
		public Location location;
		public Cloud cloud;
		public String providerName;
	}
}
