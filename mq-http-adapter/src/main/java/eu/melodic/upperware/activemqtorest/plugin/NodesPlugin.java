package eu.melodic.upperware.activemqtorest.plugin;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.melodic.upperware.activemqtorest.influxdb.InfluxDbConnector;
import eu.melodic.upperware.activemqtorest.influxdb.geolocation.IIpGeoCoder;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.model.IpAddress;
import io.github.cloudiator.rest.model.IpAddressType;
import io.github.cloudiator.rest.model.Node;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NodesPlugin implements IPlugin {

	@Autowired
	private IIpGeoCoder iIpGeoCoder;

	@Autowired
	private NodeApi nodeApi;

	@Autowired
	private InfluxDbConnector influxDbConnector;

	@Override
	public String getName() {
		return "Cloudiator Nodes Plugin";
	}

	@Override
	public void execute() {
		List<Node> nodes = null;
		try {
			nodes = nodeApi.findNodes();
			log.debug("Found {} nodes", nodes.size());
		} catch (ApiException e) {
			log.error("Error while using node API", e);
		}

		if (nodes == null) {
			return;
		}

		for (Node node : nodes) {
			log.debug("Processing node '{}'", node.getName());
			Point point = buildPoint(node, nodes.size());
			influxDbConnector.writeNonRetainableDataPoint(point);
		}
	}

	private Point buildPoint(Node node, int totalCount) {
		final Optional<String> privateIpAddress = getIpAddress(node.getIpAddresses(), IpAddressType.PRIVATE_IP);
		final Optional<String> publicIpAddress = getIpAddress(node.getIpAddresses(), IpAddressType.PUBLIC_IP);
		final Optional<String> countryCode = publicIpAddress.map(iIpGeoCoder::getCountryCode);

		Point point = Point.measurement("_node")
				.time(new Date().getTime(), TimeUnit.MILLISECONDS)
				.addField("name", node.getName())
				.addField("id", node.getId())
				.addField("state", node.getState().getValue())
				.tag("state", node.getState().getValue())
				.addField("user", node.getUserId())
				.addField("privateIp", privateIpAddress.orElse(StringUtils.EMPTY))
				.addField("publicIp", publicIpAddress.orElse(StringUtils.EMPTY))
				.addField("countryCode", countryCode.orElse(StringUtils.EMPTY))
				.tag("countryCode", countryCode.orElse(StringUtils.EMPTY))
				.addField("totalCount", totalCount)
				.build();
		return point;
	}

	private Optional<String> getIpAddress(List<IpAddress> ipAddresses, IpAddressType ipAddressType) {
		return ipAddresses.stream()
				.filter(ipAddress -> ipAddressType.equals(ipAddress.getIpAddressType()))
				.findFirst()
				.map(IpAddress::getValue);
	}

	@Override
	public boolean isReady() {
		return influxDbConnector.isReady();
	}

}
