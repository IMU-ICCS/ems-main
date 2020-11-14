/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.cluster;

import io.atomix.cluster.ClusterMembershipEvent;
import io.atomix.cluster.Member;
import io.atomix.cluster.MemberId;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.cluster.discovery.NodeDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.protocols.backup.partition.PrimaryBackupPartitionGroup;
import io.atomix.protocols.raft.partition.RaftPartitionGroup;
import io.atomix.utils.net.Address;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClusterManager extends AbstractLogBase {

	private static final String NODE_NAME_PREFIX = "node_";

	private ClusterManagerProperties properties;
	private BrokerUtil.NodeCallback callback;
	private ClusterCLI cli;

	private MemberScoreFunction scoreFunction = new MemberScoreFunction("-1");

	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private Address localAddress = null;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private NodeDiscoveryProvider bootstrapDiscoveryProvider = null;
	@Setter(AccessLevel.NONE)
	private Atomix atomix = null;
	@Setter(AccessLevel.NONE)
	private BrokerUtil brokerUtil = null;

	// ------------------------------------------------------------------------

	public synchronized ClusterCLI getCli() {
		if (cli==null)
			cli = new ClusterCLI(this);
		return cli;
	}

	public Atomix getAtomix() {
		if (atomix==null) throw new IllegalStateException("Not initialized");
		return atomix;
	}

	public BrokerUtil getBrokerUtil() {
		if (brokerUtil==null) throw new IllegalStateException("Not initialized");
		return brokerUtil;
	}

	public Set<Member> getMembers() {
		return getAtomix().getMembershipService().getMembers();
	}

	public Member getLocalMember() {
		return getAtomix().getMembershipService().getLocalMember();
	}

	public Address getLocalAddress() {
		return getLocalMember().address();
	}

	public Properties getLocalMemberProperties() {
		return getAtomix().getMembershipService().getLocalMember().properties();
	}

	public void setCallback(BrokerUtil.NodeCallback callback) {
		this.callback = callback;
		if (brokerUtil!=null) brokerUtil.setCallback(callback);
	}

	// ------------------------------------------------------------------------

	public boolean isInitialized() {
		return atomix!=null;
	}

	public void initialize() {
		initialize(properties, callback);
	}

	public void initialize(ClusterManagerProperties p) {
		initialize(p, this.callback);
	}

	public void initialize(ClusterManagerProperties p, BrokerUtil.NodeCallback callback) {
		assert (p!=null);

		// Store properties
		this.properties = p;
		this.callback = callback;

		// Initialize member scoring function
		this.scoreFunction = properties.getScore()!=null
				? MemberScoreFunction.builder()
						.formula(properties.getScore().getFormula())
						.defaultScore(properties.getScore().getDefaultScore())
						.argumentDefaults(properties.getScore().getDefaultArgs())
						.throwExceptions(properties.getScore().isThrowException())
						.build()
				: this.scoreFunction;

		// Get local address and port
		localAddress = properties.getLocalNode().getAddress();
		boolean hasLocalAddress = localAddress!=null;
		if (!hasLocalAddress)
			localAddress = getLocalAddressFromMembersList(properties.getMemberAddresses());

		// Initialize Membership provider
		bootstrapDiscoveryProvider = buildNodeDiscoveryProvider(
				properties.getMemberAddresses(), !hasLocalAddress);

		// Create Atomix and Join/start cluster
		atomix = buildAtomix(properties, localAddress, bootstrapDiscoveryProvider);
		brokerUtil = new BrokerUtil(this, callback);
	}

	public void joinCluster() {
		joinCluster(getProperties().isElectionOnJoin());
	}

	public void joinCluster(boolean startElection) {
		// Start/Join cluster
		log_info("CLM: Joining cluster...");
		long startTm = System.currentTimeMillis();
		atomix.start().join();
		long endTm = System.currentTimeMillis();
		log_debug("CLM: Joined cluster in {}ms", endTm-startTm);

		// Populate default local member properties
		Member localMember = atomix.getMembershipService().getLocalMember();
		String addrStr = localMember.address().host()+":"+localMember.address().port();
		atomix.getMembershipService().getLocalMember().properties().setProperty("address", addrStr);
		atomix.getMembershipService().getLocalMember().properties().setProperty("uuid", UUID.randomUUID().toString());
		atomix.getMembershipService().getLocalMember().properties().setProperty(BrokerUtil.BROKER_PROPERTY, BrokerUtil.STATUS_CANDIDATE);

		// Add membership listener
		atomix.getMembershipService().addListener(event -> {
			log_debug("CLM: {}: node={}", event.type(), event.subject());
			if (event.type()!=ClusterMembershipEvent.Type.REACHABILITY_CHANGED) {
				if (event.type()!=ClusterMembershipEvent.Type.METADATA_CHANGED) {
					log_info("CLM: {}: node={}", event.type(), event.subject().id().id());
					brokerUtil.checkBroker();
				}
			}
		});

		// Add broker message listener
		atomix.getCommunicationService().subscribe(BrokerUtil.BROKER_MESSAGE_TOPIC, m -> {
			brokerUtil.processBrokerMessage(m);
			return CompletableFuture.completedFuture("ok");
		});

		// Start election if no broker exists
		if (startElection) {
			brokerUtil.setDontInit(false);
			brokerUtil.checkBroker();
		}
	}

	public void leaveCluster() {
		// Leave cluster
		log_info("CLM: Leaving cluster...");
		long startTm = System.currentTimeMillis();
		atomix.stop().join();
		long endTm = System.currentTimeMillis();
		log_debug("CLM: Left cluster in {}ms", endTm-startTm);
		atomix = null;
		brokerUtil = null;
	}

	// ------------------------------------------------------------------------

	private String createMemberName(int port) { return NODE_NAME_PREFIX+port; }
	private String createMemberName(String address) { return NODE_NAME_PREFIX+address; }

	private Node createNode(String address, String port) { return createNode(address, Integer.parseInt(port)); }
	private Node createNode(String address, int port) { return createNode(address+":"+port); }
	private Node createNode(String address) {
		return Node.builder()
				.withId(createMemberName(address))
				.withAddress(Address.from(address))
				.build();
	}
	private Node createNode(ClusterManagerProperties.NodeProperties nodeProperties) {
		String nodeId = nodeProperties.getId();
		if (StringUtils.isBlank(nodeId))
			nodeId = createMemberName(nodeProperties.getAddress().port());
		return Node.builder()
				.withId(nodeId)
				.withAddress(nodeProperties.getAddress())
				.build();
	}

	private Address getLocalAddressFromMembersList(List<String> addresses) {
		return getAddressFromString(addresses.get(0));
	}

	public static Address getAddressFromString(String localAddressStr) {
		Address localAddress;
		localAddressStr = localAddressStr.trim();
		if (StringUtils.isBlank(localAddressStr)) {
			localAddress = Address.local();
		} else
		if (StringUtils.isNumeric(localAddressStr)) {
			localAddress = Address.from(Integer.parseInt(localAddressStr));
		} else {
			localAddress = Address.from(localAddressStr);
		}
		return localAddress;
	}

	private NodeDiscoveryProvider buildNodeDiscoveryProvider(List<String> addresses, boolean skipFirst) {
		return buildNodeDiscoveryProviderFromProperties(addresses.stream()
				.map(ClusterManager::getAddressFromString)
				.map(address -> new ClusterManagerProperties.NodeProperties(null, address, null))
				.collect(Collectors.toList()), skipFirst);
	}

	private NodeDiscoveryProvider buildNodeDiscoveryProviderFromProperties(List<ClusterManagerProperties.NodeProperties> nodePropertiesList, boolean skipFirst) {
		List<Node> nodes = new ArrayList<>();
		boolean first = skipFirst;
		for (ClusterManagerProperties.NodeProperties nodeProperties : nodePropertiesList) {
			if (first) {
				first = false;
				continue;
			}
			Node node = createNode(nodeProperties);
			nodes.add(node);
		}
		log_info("CLM: Building Atomix: Other members: {}", nodes);
		return BootstrapDiscoveryProvider.builder()
				.withNodes(nodes)
				//.withHeartbeatInterval(Duration.ofSeconds(5))
				//.withFailureThreshold(2)
				//.withFailureTimeout(Duration.ofSeconds(1))
				.build();
	}

	private MemberId[] getMemberIds(Set<Node> nodes) {
		List<MemberId> memberIdList = new ArrayList<>();
		for (Node node : nodes)
			memberIdList.add(MemberId.from(node.id().id()));
		return memberIdList.toArray(new MemberId[0]);
	}

	private Member[] getMembers(Set<Node> nodes) {
		List<Member> memberList = new ArrayList<>();
		for (Node node : nodes)
			memberList.add(Member.builder()
					.withId(node.id().id())
					.withAddress(node.address())
					.build());
		return memberList.toArray(new Member[0]);
	}

	private Atomix buildAtomix(ClusterManagerProperties properties, Address localAddress, NodeDiscoveryProvider bootstrapDiscoveryProvider) {
		if (localAddress==null)
			localAddress = Address.local();

		// Configuring local cluster member
		AtomixBuilder atomixBuilder = Atomix.builder();

		// Cluster id
		String clusterId = properties.getClusterId();
		if (StringUtils.isNotBlank(clusterId)) {
			log_info("CLM: Building Atomix: Cluster-id: {}", clusterId);
			atomixBuilder.withClusterId(clusterId);
		}

		// Local member id and address
		String memId = properties.getLocalNode().getId();
		memId = StringUtils.isBlank(memId) ? createMemberName(localAddress.port()) : memId;
		MemberId localMemberId = MemberId.from(memId);
		log_info("CLM: Building Atomix: Local-Member-Id: {}", localMemberId);
		log_info("CLM: Building Atomix: Local-Member-Address: {}", localAddress);
		atomixBuilder
				.withMemberId(localMemberId)
				.withAddress(localAddress)
				.withProperties(properties.getLocalNode().getProperties());

		// Configure Management and Partition groups
		boolean usePBInMg = properties.isUsePBInMg();
		boolean usePBInPg = properties.isUsePBInPg();
		String mgName = properties.getMgName();
		String pgName = properties.getPgName();
		if (StringUtils.isBlank(mgName)) mgName = "system";
		if (StringUtils.isBlank(pgName)) pgName = "data";
		log_debug("CLM: Building Atomix: Cluster Groups: mg-type-PB={}, pg-type-PB={}, mg-name={}, pg-name={}",
				usePBInMg, usePBInPg, mgName, pgName);
		atomixBuilder
				.withManagementGroup(usePBInMg
						? PrimaryBackupPartitionGroup.builder(mgName)
								.withNumPartitions(1)
								//.withMemberGroupStrategy(MemberGroupStrategy.NODE_AWARE)
								.build()
						: RaftPartitionGroup.builder(mgName)
								.withNumPartitions(1)
								.withMembers(getMemberIds(bootstrapDiscoveryProvider.getNodes()))
								//.withMembers(getMembers(bootstrapDiscoveryProvider.getNodes()))
								//.withDataDirectory(new File("raft-mg"))
								//.withMemberGroupStrategy(MemberGroupStrategy.NODE_AWARE)
								.build()
				)
				.withPartitionGroups(usePBInPg
						? PrimaryBackupPartitionGroup.builder(pgName)
								.withNumPartitions(8)
								//.withMemberGroupStrategy(MemberGroupStrategy.NODE_AWARE)
								.build()
						: RaftPartitionGroup.builder(pgName)
								.withNumPartitions(8)
								.withMembers(getMemberIds(bootstrapDiscoveryProvider.getNodes()))
								//.withMembers(getMembers(bootstrapDiscoveryProvider.getNodes()))
								//.withDataDirectory(new File("raft-pg"))
								//.withMemberGroupStrategy(MemberGroupStrategy.NODE_AWARE)
								.build()
				);

		// Configure Bootstrap Discovery Provider
		atomixBuilder
				//.withMulticastEnabled()
				.withMembershipProvider(bootstrapDiscoveryProvider);

		// Configure TLS for messaging
		log_info("CLM: Building Atomix: TLS={}", properties.getTls());
		atomixBuilder
				.withTlsEnabled(properties.getTls().isEnabled())
				.withKeyStore(properties.getTls().getKeystore())
				.withKeyStorePassword(properties.getTls().getKeystorePassword())
				.withTrustStore(properties.getTls().getTruststore())
				.withTrustStorePassword(properties.getTls().getTruststorePassword());

		return atomixBuilder.build();
	}
}
