package eu.melodic.upperware.dlms.component;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Component;

import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;
import io.github.cloudiator.rest.model.CloudiatorProcess;
import io.github.cloudiator.rest.model.IpAddress;
import io.github.cloudiator.rest.model.IpAddressType;
import io.github.cloudiator.rest.model.IpVersion;
import io.github.cloudiator.rest.model.Node;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Java class to obtain the component id of the current machine using cloudiator
 */
@Slf4j
@Component
@AllArgsConstructor
public class ComponentId {

	private NodeApi nodeApi;
	private ProcessApi processApi;

	public String findComponentId(String ipAddress) {
		List<CloudiatorProcess> processes = getProcessList(this.processApi);
		List<Node> nodes = getNodeList(this.nodeApi);

		if (processes != null && nodes != null) {
			String nodeId = getNodeId(nodes, ipAddress);
			if (nodeId != null) {
				return (getComponentId(nodeId, processes));
			}
		}
		log.debug("Ip addresses {} from the deployed machine did not match with any component id", ipAddress);
		return null;
	}

	public List<Node> getNodeList(NodeApi nodeApi) {
		try {
			return nodeApi.findNodes();
		} catch (ApiException e) {
			log.error("Error by getting nodes list: ", e);
		}
		return null;
	}

	public List<CloudiatorProcess> getProcessList(ProcessApi processApi) {
		try {
			return processApi.getProcesses(null);
		} catch (ApiException e) {
			log.error("Error by getting Cloudiator processes list: ", e);
		}
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
	private static String getComponentId(String nodeId, List<CloudiatorProcess> processes) {
		for (CloudiatorProcess process : processes) {
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

}
