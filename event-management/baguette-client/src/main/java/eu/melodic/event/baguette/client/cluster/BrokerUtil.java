/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.cluster;

import io.atomix.cluster.Member;
import io.atomix.core.Atomix;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BrokerUtil extends AbstractLogBase {
    public final static String BROKER_MESSAGE_TOPIC = "BROKER";
    public final static String BROKER_PROPERTY = "broker";
    public final static String STATUS_BROKER = "broker";
    public final static String STATUS_CANDIDATE = "candidate";
    public final static String STATUS_NOT_CANDIDATE = "off";
    public final static String STATUS_INITIALIZING = "initializing";
    public final static String STATUS_RETIRING = "retiring";

    protected final static Collection<String> BROKER_STATUSES = Arrays.asList(STATUS_BROKER, "is-broker", "current-broker", STATUS_RETIRING);
    protected final static Collection<String> CANDIDATE_STATUSES = Arrays.asList("capable", "yes", STATUS_CANDIDATE, STATUS_BROKER, "is-broker", "current-broker");
    protected final static Collection<String> NON_CANDIDATE_STATUSES = Arrays.asList("no", "", STATUS_NOT_CANDIDATE);

    protected final static String MESSAGE_ELECTION = "election";
    protected final static String MESSAGE_APPOINT = "appoint";
    protected final static String MESSAGE_INITIALIZE = "initialize";
    protected final static String MESSAGE_READY = "ready";
    private static final String MARKER_NEW_CONFIGURATION = "New config: ";

    private final Atomix atomix;
    private final ClusterManager clusterManager;

    @Getter @Setter
    private NodeCallback callback;
    @Getter @Setter
    private boolean dontInit = true;    // Don't run election until elect/appoint is explicitly invoked at a node

    public BrokerUtil(ClusterManager clusterManager, NodeCallback callback) {
        this.clusterManager = clusterManager;
        this.atomix = clusterManager.getAtomix();
        this.callback = callback;
    }

    void processBrokerMessage(Object m) {
        if (m == null) return;
        String message = m.toString();
        log_info("BRU: **** Broker message received: {}", message);

        dontInit = false;
        String messageType = message.split(" ", 2)[0];
        if (MESSAGE_ELECTION.equalsIgnoreCase(messageType)) {
            // Get excluded nodes (if any)
            List<String> excludes = Arrays.stream(message.split(" "))
                    .filter(StringUtils::isNotBlank)
                    .map(String::trim)
                    .filter(s -> s.startsWith("-"))
                    .map(s -> s.substring(1))
                    .collect(Collectors.toList());
            // Start election
            log_info("BRU: **** BROKER: Starting Broker election: ");
            election(excludes);
        } else if (MESSAGE_APPOINT.equalsIgnoreCase(messageType)) {
            String newBrokerId = message.split(" ", 2)[1];
            appointment(newBrokerId);
        } else if (MESSAGE_INITIALIZE.equalsIgnoreCase(messageType)) {
            String newBrokerId = message.split(" ", 2)[1];
            log_info("BRU: **** BROKER: New Broker initializes: {}", newBrokerId);
            // Back off if i am also initializing but have a lower score or command order
            if (callback!=null)
                callback.backOff();
        } else if (MESSAGE_READY.equalsIgnoreCase(messageType)) {
            String[] part = message.split(" ", 3);
            String brokerId = part[1];
            String newConfig = part[2];
            // Strip 'New config.' marker
            if (newConfig.startsWith(MARKER_NEW_CONFIGURATION)) {
                newConfig = newConfig.substring(MARKER_NEW_CONFIGURATION.length()).trim();
            } else {
                log_error("BRU: !!!!  BUG: New configuration not properly marked: {}  !!!!", newConfig);
            }
            log_info("BRU: **** BROKER: New Broker is ready: {}, New config: {}", brokerId, newConfig);

            // If i am not the new Broker then reset our broker status
            Member local = atomix.getMembershipService().getLocalMember();
            String localStatus = local.properties().getProperty(BROKER_PROPERTY, "");
            log_debug("BRU: Nodes: local={}, broker={}", local.id().id(), brokerId);
            if (BROKER_STATUSES.contains(localStatus))
                if (!local.id().id().equals(brokerId)) {
                    // Temporarily make node unavailable for being elected as Broker, until step down completes
                    local.properties().setProperty(BROKER_PROPERTY, STATUS_NOT_CANDIDATE);

                    // Step down
                    log_info("BRU: Old broker steps down: {}", local.id().id());
                    if (callback!=null)
                        callback.stepDown();

                    // After step down, and if node hasn't retired, node status changes to 'candidate'
                    if (!"retiring".equalsIgnoreCase(localStatus))
                        local.properties().setProperty(BROKER_PROPERTY, STATUS_CANDIDATE);
                }

            // Store new configuration
            //if (! local.properties().getProperty("configuration", "").equals(newConfig))
            local.properties().setProperty("configuration", newConfig);
            log_info("BRU: Node configuration updated: {}", newConfig);
            if (callback!=null) {
                callback.setConfiguration(newConfig);
            }
        } else
            log_warn("BRU:    BROKER: Unknown message received: {}", message);
    }

    public void startElection() {
        log_info("BRU: Broker election requested: broadcasting election message...");
        atomix.getCommunicationService().broadcast(BROKER_MESSAGE_TOPIC, MESSAGE_ELECTION);
        election(null);
    }

    public void election(List<String> excludeNodes) {
        // Find the new Brokering node
        if (excludeNodes == null) excludeNodes = Collections.emptyList();
        final List<String> excludes = excludeNodes;
        Member broker = atomix.getMembershipService().getMembers().stream()
                .filter(m -> m.isActive() && m.isReachable())
                .filter(m -> !excludes.contains(m.id().id()))
                .filter(m -> CANDIDATE_STATUSES.contains(m.properties().getProperty(BROKER_PROPERTY, "")))
                .map(m -> new MemberWithScore(m, clusterManager.getScoreFunction()))
                .peek(ms -> log_info("BRU: Member-Score: {} => {}  {}", ms.getMember().id().id(), ms.getScore(),
                        ms.getMember().properties().getProperty("uuid", null)))
                .max(MemberWithScore::compareTo)
                .orElse(MemberWithScore.NULL_MEMBER)
                .getMember();
        log_info("BRU: Broker: {}", broker != null ? broker.id().id() : null);

        // If local node is the selected broker...
        if (atomix.getMembershipService().getLocalMember().equals(broker)) {
            appointment(broker.id().id());
        }
    }

    public void appointment(String appointedNodeId) {
        // Check i am appointed
        dontInit = false;
        Member local = atomix.getMembershipService().getLocalMember();
        if (! local.id().id().equals(appointedNodeId)) {
            log_debug("BRU: I am not appointed: me={} <> appointed={}", local.id().id(), appointedNodeId);
            return;
        }

        // Check if i am already a broker
        String localStatus = local.properties().getProperty(BROKER_PROPERTY, "");
        if (BROKER_STATUSES.contains(localStatus)) {
            if ("retiring".equalsIgnoreCase(localStatus)) {
                log_error("BRU: !!!! BUG: RETIRING BROKER HAS BEEN ELECTED AGAIN !!!!");
            } else {
                log_info("BRU: Broker elected again");
            }
        } else {
            // Notify others that this node starts initializing as Broker
            log_info("BRU: Node will become Broker. Initializing...");
            atomix.getCommunicationService().broadcast(BROKER_MESSAGE_TOPIC, MESSAGE_INITIALIZE + " " + local.id().id());
            atomix.getMembershipService().getLocalMember().properties().setProperty(BROKER_PROPERTY, STATUS_INITIALIZING);

            // Start initializing as Broker...
            if (callback!=null)
                callback.initialize();

            // Update node status to Broker
            local.properties().setProperty(BROKER_PROPERTY, STATUS_BROKER);
            log_info("BRU: Node is ready to act as Broker. Ready");
        }

        // Notify others that this node is ready to serve as Broker
        String brokerId = local.id().id();
        String newConf = MARKER_NEW_CONFIGURATION +
                (callback!=null ? callback.getConfiguration(local) : "");
        atomix.getCommunicationService().broadcastIncludeSelf(BROKER_MESSAGE_TOPIC, MESSAGE_READY + " " + brokerId + " " + newConf);
    }

    public void appoint(String brokerId) {
        // Check if already a broker
        if (getBrokers().stream().anyMatch(m -> m.id().id().equals(brokerId))) {
            log_info("BRU: Node is already a broker: {}", brokerId);
            if (STATUS_RETIRING.equals(getNodeStatus(brokerId)))
                setNodeStatus(brokerId, STATUS_BROKER);
            return;
        }

        // Check if not a candidate
        String brokerStatus = getNodeStatus(brokerId);
        log_debug("BRU: Node status: {}", brokerStatus);
        if (!CANDIDATE_STATUSES.contains(brokerStatus)) {
            log_info("BRU: Node is not a broker candidate: {}", brokerId);
            return;
        }

        // Broadcast appointment message
        atomix.getCommunicationService().broadcastIncludeSelf(BROKER_MESSAGE_TOPIC, MESSAGE_APPOINT + " " + brokerId);
        log_info("BRU: Broker appointment broadcast: {}", brokerId);
    }

    public void retire() {
        String localStatus = atomix.getMembershipService().getLocalMember().properties().getProperty(BROKER_PROPERTY, "");
        if (BROKER_STATUSES.contains(localStatus)) {
            if ("retiring".equalsIgnoreCase(localStatus)) {
                log_info("BRU: Already retiring");
            } else {
                atomix.getMembershipService().getLocalMember().properties().setProperty(BROKER_PROPERTY, STATUS_RETIRING);
                log_info("BRU: Broker retires: broadcasting election message...");
                atomix.getCommunicationService().broadcast(BROKER_MESSAGE_TOPIC, MESSAGE_ELECTION + " -" + atomix.getMembershipService().getLocalMember().id().id());
                election(Collections.singletonList(atomix.getMembershipService().getLocalMember().id().id()));
            }
        } else
            log_info("BRU: Not a Broker");
    }

    public List<Member> getBrokers() {
        return atomix.getMembershipService().getMembers().stream()
                .filter(m -> m.isActive() && m.isReachable())
                .filter(m -> BROKER_STATUSES.contains(m.properties().getProperty(BROKER_PROPERTY, "")))
                .collect(Collectors.toList());
    }

    public String getLocalStatus() {
        return getNodeStatus(atomix.getMembershipService().getLocalMember());
    }

    public void setLocalStatus(String status) {
        setNodeStatus(atomix.getMembershipService().getLocalMember(), status);
    }

    public String getNodeStatus(Member member) {
        return member.properties().getProperty(BROKER_PROPERTY, "");
    }

    public void setNodeStatus(Member member, String status) {
        member.properties().setProperty(BROKER_PROPERTY, status);
    }

    public String getNodeStatus(String memberId) {
        Member member = getMemberById(memberId);
        if (member != null)
            return getNodeStatus(member);
        return null;
    }

    public void setNodeStatus(String memberId, String status) {
        Member member = getMemberById(memberId);
        if (member != null)
            setNodeStatus(member, status);
    }

    private Member getMemberById(String id) {
        return atomix.getMembershipService().getMembers().stream()
                .filter(m -> m.isActive() && m.isReachable())
                .filter(m -> m.id().id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void setCandidate() {
        String localStatus = atomix.getMembershipService().getLocalMember().properties().getProperty(BROKER_PROPERTY, "");
        if (!BROKER_STATUSES.contains(localStatus) && !CANDIDATE_STATUSES.contains(localStatus)) {
            atomix.getMembershipService().getLocalMember().properties().setProperty(BROKER_PROPERTY, STATUS_CANDIDATE);
            log_info("BRU: Node becomes Broker candidate");
        } else
            log_info("BRU: Node is already Broker candidate");
    }

    public void clearCandidate() {
        String localStatus = atomix.getMembershipService().getLocalMember().properties().getProperty(BROKER_PROPERTY, "");
        if (BROKER_STATUSES.contains(localStatus)) {
            log_warn("BRU: Node is the Broker. Select 'retire' instead");
            return;
        }
        if (CANDIDATE_STATUSES.contains(localStatus)) {
            atomix.getMembershipService().getLocalMember().properties().setProperty(BROKER_PROPERTY, STATUS_NOT_CANDIDATE);
            log_info("BRU: Node removed from Broker candidates");
        } else
            log_info("BRU: Node is not Broker candidate");
    }

    public List<MemberWithScore> getCandidates() {
        return atomix.getMembershipService().getMembers().stream()
                .filter(m -> m.isActive() && m.isReachable())
                .filter(m -> CANDIDATE_STATUSES.contains(m.properties().getProperty(BROKER_PROPERTY, "")))
                .map(m -> new MemberWithScore(m, clusterManager.getScoreFunction()))
                .collect(Collectors.toList());
    }

    public List<MemberWithScore> getAllNodes() {
        return atomix.getMembershipService().getMembers().stream()
                .filter(m -> m.isActive() && m.isReachable())
                .map(m -> new MemberWithScore(m, clusterManager.getScoreFunction()))
                .collect(Collectors.toList());
    }

    public void checkBroker() {
        if (dontInit) return;

        List<Member> brokers = getBrokers();
        if (brokers.size() != 1) {
            log_info("BRU: Brokers after cluster change: {}", brokers);

            // Check if any node is initializing as broker (then don't start election)
            if (getAllNodes().stream()
                    .noneMatch(m -> STATUS_INITIALIZING.equals(getNodeStatus(m.getMember()))))
            {
                startElection();
            }
        }
    }

    public interface NodeCallback {
        void initialize();
        void stepDown();
        void backOff();
        String getConfiguration(Member local);
        void setConfiguration(String newConfig);
    }
}
