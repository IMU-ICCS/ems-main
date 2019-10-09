package eu.melodic.upperware.activemqtorest.plugin;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.net4j.util.StringUtil;
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
		Long timestamp = Long.valueOf((new Date()).getTime());

		List<IpAddress> ipAddresses = node.getIpAddresses();

		Optional<String> privateIpAddress = Optional.empty();
		Optional<String> publicIpAddress = Optional.empty();

		for (IpAddress ipAddress : ipAddresses) {
			if (ipAddress.getIpAddressType().equals(IpAddressType.PRIVATE_IP)) {
				privateIpAddress = Optional.of(ipAddress.getValue());
			} else if (ipAddress.getIpAddressType().equals(IpAddressType.PUBLIC_IP)) {
				publicIpAddress = Optional.of(ipAddress.getValue());
			}
		}

		Optional<String> countryCode = Optional.empty();
		if (publicIpAddress.isPresent()) {
			countryCode = Optional.of(iIpGeoCoder.getCountryCode(publicIpAddress.get()));
		}

		Point point = Point.measurement("_node")
				.time(timestamp, TimeUnit.MILLISECONDS)
				.addField("name", node.getName())
				.addField("id", node.getId())
				.addField("state", node.getState().getValue())
				.tag("state", node.getState().getValue())
				.addField("user", node.getUserId())
				.addField("privateIp", privateIpAddress.orElse(StringUtil.EMPTY))
				.addField("publicIp", publicIpAddress.orElse(StringUtil.EMPTY))
				.addField("countryCode", countryCode.orElse(""))
				.tag("countryCode", countryCode.orElse(""))
				.addField("totalCount", totalCount)
				.build();
		return point;
	}

	@Override
	public boolean isReady() {
		return influxDbConnector.isReady();
	}

}
