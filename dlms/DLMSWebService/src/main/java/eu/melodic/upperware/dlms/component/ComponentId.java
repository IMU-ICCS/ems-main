package eu.melodic.upperware.dlms.component;

import eu.melodic.upperware.dlms.exception.DLMSException;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Java class to obtain the component id of the current machine using cloudiator
 */
@Slf4j
@Component
@AllArgsConstructor
public class ComponentId {

	private NodeApi nodeApi;
	private ProcessApi processApi;

	public Optional<String> findComponentId(String ipAddress) throws ApiException {
		List<CloudiatorProcess> processes = this.processApi.getProcesses(null);
		List<Node> nodes = this.nodeApi.findNodes();

		return getNodeForIP(nodes, ipAddress)
				.map(Node::getId)
				.map(nodeId -> getComponentId(nodeId, processes))
				.filter(Optional::isPresent)
				.map(Optional::get);
	}

	private static Optional<Node> getNodeForIP(List<Node> nodes, String ipAddress) {
		return nodes
				.stream()
				.filter(node -> isSameIp(node.getIpAddresses(), ipAddress))
				.findFirst();
	}

	/**
	 * Match the node if with the process id to get the component name
	 */
	private static Optional<String> getComponentId(String nodeId, List<CloudiatorProcess> processes) {

		return processes.stream()
				.filter(p -> isSameProcess(p, nodeId))
				.map(CloudiatorProcess::getTask)
				.findFirst();
	}

	private static boolean isSameProcess(CloudiatorProcess cloudiatorProcess, String nodeId) {
		if (cloudiatorProcess instanceof SingleProcess) {
			return ((SingleProcess) cloudiatorProcess).getNode().equals(nodeId);
		} else if (cloudiatorProcess instanceof ClusterProcess){
			return ((ClusterProcess) cloudiatorProcess).getNodes()
					.stream()
					.anyMatch(nodeId::equals);
		}
		throw new DLMSException(String.format("CloudiatorProcess is neither SingleProcess nor ClusterProcess but %s", cloudiatorProcess.getClass().getSimpleName()));
	}

	/**
	 * Does node have the same ip address
	 */
	private static boolean isSameIp(List<IpAddress> ipAddresses, String ipAddress) {
		return ipAddresses
				.stream()
				.anyMatch(
						ipAddress1 -> isRelevantIp(ipAddress1) && StringUtils.equals(ipAddress1.getValue(), ipAddress));
	}

	/**
	 * We are interested in V4 and public ip address
	 */
	private static boolean isRelevantIp(IpAddress ipAddress) {
		return (ipAddress.getIpVersion().equals(IpVersion.V4)
				&& ipAddress.getIpAddressType().equals(IpAddressType.PUBLIC_IP));
	}

}
