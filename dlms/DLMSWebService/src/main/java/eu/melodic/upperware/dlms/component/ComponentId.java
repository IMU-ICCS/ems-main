package eu.melodic.upperware.dlms.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cloudiator.rest.ApiClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Java class to obtain the component id of the current machine using cloudiator
 */
@Slf4j
@AllArgsConstructor
@Component
public class ComponentId {

	@Autowired
    private final ApiClient apiClient;
    
	public String findComponentId(String ipAddress) {
		final String endPoint = apiClient.getBasePath();
		final String PROCESS_URL = endPoint + "/process";
		final String NODE_URL = endPoint + "/node";
		
		List<Process> processes = readProcess(PROCESS_URL);
		List<Node> nodes = readNode(NODE_URL);

		if (processes != null && nodes != null) {
			String nodeId = getNodeId(nodes, ipAddress);
			if (nodeId != null) {
				return (getComponentId(nodeId, processes));
			}
		}
		log.debug("Ip addresses {} from the deployed machine did not match with any component id", ipAddress);
		return null;
	}

	private static String getNodeId(List<Node> nodes, String ipAddress) {
		for (Node node : nodes) {
			if (isSameIp(node.getIpAddresses(), ipAddress)) {
				return node.getId();
			}
		}
		log.debug("There was no node id matching the public ip address of the deployed machine");
		return null;
	}

	/**
	 * Match the node if with the process id to get the component name
	 */
	private static String getComponentId(String nodeId, List<Process> processes) {
		for (Process process : processes) {
			if (isSameProcess(process.getId(), nodeId)) {
				return process.getTask();
			}
		}
		log.debug("There was no task matching the node id");
		return null;
	}

	private static boolean isSameProcess(String processId, String nodeId) {
		return (StringUtils.equals(processId, nodeId));
	}

	/**
	 * Does node have the same ip address
	 */
	private static boolean isSameIp(List<IpAddress> ipAddresses, String ipAddress) {
		for (IpAddress ipAdd : ipAddresses) {
			if (isRelevantIp(ipAdd)) {
				if (StringUtils.equals(ipAdd.getValue(), ipAddress)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * We are interested in V4 and public ip address
	 */
	private static boolean isRelevantIp(IpAddress ipAddress) {
		return (ipAddress.getIpVersion().equals(IpVersion.V4)
				&& ipAddress.getIpAddressType().equals(IpAddressType.PUBLIC_IP));
	}

	/**
	 * Retrieve process using the url from cloudiator
	 */
	private static List<Process> readProcess(String url) {
		List<Process> processes = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			processes = mapper.readValue(url, new TypeReference<ArrayList<Process>>() {
			});

			log.info("Process was successfully retrieved from the provided url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("There was a problem while parsing Process from the cloudiator url");
		}
		return processes;
	}

	/**
	 * Retrieve node using the url from cloudiator
	 */
	private static List<Node> readNode(String url) {
		List<Node> nodes = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			nodes = mapper.readValue(url, new TypeReference<ArrayList<Node>>() {
			});

			log.info("Process was successfully retrieved from the provided url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("There was a problem while parsing Node from the cloudiator url");
		}
		return nodes;
	}

}
