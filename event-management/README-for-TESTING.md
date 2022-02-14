# <u>Testing of New EMS Features</u>


## New features of EMS

- Support for **Resource-Limited (RL)** nodes, like edge devices or small VMs
- Support for **Self-Healing** monitoring topology (partially implemented)


## Definitions
We distinguish between ***Resource-Limited (RL)*** nodes and ***Normal or Non-RL*** nodes.

- **Normal nodes** are VMs have enough resources, where an EMS client will be installed, along with JRE8 and Netdata.
- **RL nodes** are VMs with few resources, where only Netdata will be installed.
- Currently, EMS will classify a VM as an RL node if:
    * it has 1 or 2 cores, or
    * it has 2GB of RAM or less, or
    * it has Total Disk space 1GB or less, or
    * its architecture name starts with `ARM`  (it will normally be `x86_64`).
    * Thresholds can be changed in `eu.melodic.event.baguette-client-install.properties` file.


We also distinguish between ***Monitoring Topologies***:

- **2-LEVEL Monitoring Topology**: Nodes send their metrics directly to EMS server.

    * Includes an EMS server, and any number of Normal and/or RL nodes.
    * No clustering occurs in 2-LEVEL topologies, hence Aggregator role is not used.
    * CAMEL Metric Models will only use `GLOBAL` and `PER_INSTANCE` groupings or no groupings at all (`GLOBAL` and `PER_INSTANCE` are then implied).

- **3-LEVEL Monitoring Topology**: Nodes send their metrics to cluster-wide Aggregators, then Aggregators send (composite) metrics to EMS server.

    * Includes an EMS server, Aggregators (one per cluster), and Normal and/or RL nodes.
    * Nodes are groupped into clusters. Each cluster has a node with the Aggregator role.
    * Only Normal nodes can be Aggregators.
    * There must be exactly one Aggregator per cluster.
    * Each cluster must have at least one Normal node (in order to become Aggregator).
    * CAMEL Metric Model will use `GLOBAL`, `PER_ZONE` / `PER_REGION` / `PER_CLOUD`, and `PER_INSTANCE` groupings.

  Clustering of nodes is used for faster failure detection, as well as distribution of load:
    - Only 3-LEVEL topologies are clustered.
    - 2-LEVEL topologies are not clustered.

  Currently, nodes are clustered based on their:
    - Availability Zone or Region or Cloud Service Provider, or
    - assigned to a default cluster.


------


## A) <u>Support for Resource-Limited nodes</u>
	Feature Quick Notes:
	- EMS server will NOT install EMS client and JRE8 in RL nodes.
	- EMS server will install Netda in RL nodes.
	- EMS server or an Aggregator will periodically query Netdata agents of RL nodes for metrics.
	- Normal nodes will periodically query their Local Netdata agent for metrics.



### <u>Test Cases</u>

**A.1) Metrics collection from RL nodes in a 2-LEVEL topology**

```
Test Case Quick Notes:
- EMS server MUST log when it collects metrics from RL nodes.
- EMS server MUST *NOT* log or collect metrics from Normal (Non-RL) nodes.
- Normal nodes MUST log when they collect metrics from their Local Netdata agents. (The Log records are slightly different).
```
**You need a CAMEL model:**

* with two Requirement Sets:
    - for Normal nodes: 4 cores, 4GB RAM, >1 GB Disk, and
    - for RL nodes: 1-2 cores, or <2GB RAM, or <1GB Disk
* with 1-2 COMPONENTS using Requirement Set #1 (Normal nodes)
* with 1-2 COMPONENTS with Requirement Set #2 (RL nodes)
* with no Groupings in Metric Model

**After Application deployment you need to check the logs of:**

* ***EMS server***, for log messages about collecting metrics from RL-nodes' Netdata agents. E.g.

  ```
  e.m.e.c.c.netdata.NetdataCollector       : Collectors::Netdata: Collecting metrics from remote nodes (without EMS client): [192.168.32.2, 192.168.32.4]
  e.m.e.c.c.netdata.NetdataCollector       : Collectors::Netdata:   Collecting data from url: http://192.168.32.2:19999/api/v1/allmetrics?format=json
  e.m.e.c.c.netdata.NetdataCollector       : Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  e.m.e.c.c.netdata.NetdataCollector       : Collectors::Netdata:   Collecting data from url: http://192.168.32.4:19999/api/v1/allmetrics?format=json
  e.m.e.c.c.netdata.NetdataCollector       : Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  ```

* ***Normal nodes***, for log messages about collecting metrics from their Local Netdata agent

  ```
  Collectors::Netdata: Collecting metrics from local node...
  Collectors::Netdata:   Collecting data from url: http://127.0.0.1:19999/api/v1/allmetrics?format=json
  Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  ```



**A.2)  Metrics collection from RL nodes in a 3-LEVEL topology**

```
Test Case Quick Notes:
- The Aggregator (it is a Normal node) MUST log each time it collects metrics from RL nodes in its cluster.
- The Aggregator MUST *NOT* log or collect metrics from Normal (Non-RL) nodes in its cluster.
- Normal nodes (including Aggregator) MUST log each time they collect metrics from their Local Netdata agents. (The Log records are slightly different).
```
**You need a CAMEL model:**

* with two Requirement Sets:
    - for Normal nodes: 4 cores, 4GB RAM, >1 GB Disk, and
    - for RL nodes: 1-2 cores, or <2GB RAM, or <1GB Disk
* with 1-2 COMPONENTS with Requirement Set #1 (Normal nodes)
* with 1-2 COMPONENTS with Requirement Set #2 (RL nodes)
* with three (3) Groupings used in the Metric Model  (`GLOBAL`, `PER_ZONE`, `PER_INSTANCE`)

**After Application deployment you need to check the logs of:**

* ***EMS server***, for NO logs related collecting metrics from any Netdata agent
* ***Aggregator node(s)***, for logs about collecting metrics from the Netdata agents of RL nodes, in the same cluster. E.g.

  ```
  Collectors::Netdata: Collecting metrics from local node...
  Collectors::Netdata:   Collecting data from url: http://127.0.0.1:19999/api/v1/allmetrics?format=json
  Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  Collectors::Netdata: Collecting metrics from remote nodes (without EMS client): [192.168.96.2, 192.168.96.5]
  Collectors::Netdata:   Collecting data from url: http://192.168.96.2:19999/api/v1/allmetrics?format=json
  Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  Collectors::Netdata:   Collecting data from url: http://192.168.96.5:19999/api/v1/allmetrics?format=json
  Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  ```

* ***Normal nodes*** (including Aggregator node), for logs about collecting metrics from their Local Netdata agents. E.g.

  ```
  Collectors::Netdata: Collecting metrics from local node...
  Collectors::Netdata:   Collecting data from url: http://127.0.0.1:19999/api/v1/allmetrics?format=json
  Collectors::Netdata:     Metrics: extracted=0, published=0, failed=0
  ```



------

## B) <u>Support for monitoring SELF-HEALING</u>
	Feature Quick Notes:
	- Self-Healing refers to recovering the monitoring software running at the nodes.
	- In Normal nodes, specifically refers to recovering of EMS client and/or Netdata agent.
	- In RL nodes, refers to recovering Netdata agent only.



#### Design Choices

1. Each EMS client (in a Normal node) is responsible for recovering the Local Netdata agent, collocated with it.
2. When clustering is used (i.e. in a 3-level topology), Aggregator is responsible for recovering other nodes in its cluster, both Normal and RL.
3. When clustering is not used (i.e. in a 2-level topology), EMS server is responsible for recovering nodes (both Normal and RL).



#### Self-Healing actions

We distinguish between monitoring topologies:

* **2-LEVEL Monitoring topology:** Only EMS server and nodes (Normal & RL) are used. No Aggregators or clustering.

    * EMS server will try to recover any <u>*Normal node*</u> that disconnects and not reconnects after a configured period of time.

      ***Condition:***

        * EMS client disconnects and not re-connects after X seconds

      ***Recovery steps taken by EMS server:***

        * SSH to node (assuming it is a VM)
        * Kill EMS client (if is still running)
        * Launch EMS client
        * Close SSH connection
        * Wait for a configured period of time for recovered EMS client to reconnect to EMS server
        * After that period of time, the process is repeated (up to configured number of retries, and it then gives up).

    * EMS server will try to recovery any <u>*RL node*</u> with inaccessible Netdata agent.

      ***Condition:***

        * X consecutive connection failures to Netdata agent occur.

      ***Recovery steps taken by EMS server:***

        * SSH to node (assuming it is a VM)
        * Kill Netdata (if it is still running)
        * Launch Netdata
        * Close SSH connection
        * Reset the consecutive failures counter.


* **3-LEVEL Monitoring topology:** EMS server, Aggregators (one per cluster), and Nodes in clusters exist. Use of clustering.

    * <u>Aggregator</u> will try to recover any <u>*Normal node*</u> that leaves the cluster and not joins back in a configured period of time.

      ***Condition:***

        * EMS client leaves cluster and not joins back after X seconds

      ***Recovery steps taken by Aggregators:***

        * Contact EMS server to get node's credentials
        * SSH to node (assuming it is a VM)
        * Kill EMS client (if it is still running)
        * Launch EMS client
        * Close SSH connection
        * Wait for a configured period of time for EMS client to join back to cluster
        * After that period of time the process is repeated (up to a configured number of retries, and then it gives up and notifies EMS server)
        * When EMS client join to cluster or in case of giving up, the node credentials are cleared from Aggregator's cache.

    * <u>Aggregator</u> will try to recover any <u>*RL node*</u> with inaccessible Netdata agent.

      ***Condition:***

        * X consecutive connection failures to Netdata agent occur.

      ***Recovery steps taken by Aggregators:***

        * Contact EMS server to get node's credentials
        * SSH to node (assuming it is a VM)
        * Kill Netdata agent (if it is still running)
        * Launch Netdata agent
        * Close SSH connection
        * Reset the consecutive failures counter
        * On successful connection to Netdata agent the node credentials are cleared from Aggregator cache.


* **2-LEVEL or 3-LEVEL Monitoring topology**

    * Any Normal node will try to recover its Local Netdata agent, if it becomes inaccessible.

      ***Condition:***

        * X consecutive connection failures to Local Netdata agent occur.

      ***Recovery steps (taken by NORMAL node):***

        * Kill Netdata agent (if it is still running)
        * Launch Netdata agent
        * Reset the consecutive failures counter



### <u>Test Cases for 2-LEVEL topology</u>

> ***PREREQUISITE:***
>
> **You need a CAMEL model with a 2-LEVEL topology:**
>
> * with two Requirement Sets:
    >   - for Normal nodes: 4 cores, 4GB RAM, >1 GB Disk, and
>   - for RL nodes: 1-2 cores, or <2GB RAM, or <1GB Disk
> * with 1-2 COMPONENTS with Requirement Set #1 (Normal nodes)
> * with 1-2 COMPONENTS with Requirement Set #2 (RL nodes)
> * with no Groupings used in Metric Model.
>
> This CAMEL model is ***common*** for the following test cases, unless another CAMEL model is specified.
>
> CAMEL model MUST be re-deployed after each test case execution.



**B.1.a)  Successful recovery of an EMS client in a Normal node**

```
Test Case Quick Notes:
- Kill EMS client of any Normal node.
- The EMS server will recover the killed EMS client after a configured period of time.
- Check EMS server log messages for disconnection, recovery actions and re-connection.
```

**After Application deployment...**

* Connect to a Normal node and ***kill*** EMS client

**Next, check the logs of:**

* ***EMS server***, for messages reporting an EMS client disconnection, the recovery attempt(s) and EMS client re-connection.
* ***Normal node where EMS client killed***, for EMS client's logs indicating its restart.
* ***Other Normal nodes***, for NO logs indicating failure or recovery attempts.



**B.1.b)  Failed recovery of EMS client in a Normal node**

```
Test Case Quick Notes:
- Kill the VM of any Normal node.
- The EMS server will try to connect to the affected VM but fail.
- After a configured number of retries EMS server will give up.
```

**After Application deployment...**

* Terminate the VM of a Normal node

**Next, check the logs of:**

* ***EMS server***, for messages reporting an EMS client disconnection, failed recovery attempts and giving up recovery
* ***Normal nodes that operate***, for NO logs indicating any failure or recovery attempts



**B.2.a)  Successful recovery of a Netdata agent in a RL node**

```
Test Case Quick Notes:
- Kill Netdata agent of any RL node.
- The EMS server will recover the killed Netdata agent after a configured period of time.
- Check EMS server log messages reporting failures to collect metrics, recovery actions, and successful metrics collection.
```

**After Application deployment...**

* Connect to a RL node and kill Netdata agent.

**Next, check the logs of:**

* ***EMS server***, for logs reporting connection failure to a Netdata agent, and recovery actions.
* ***RL node with killed Netdata***, check if the Netdata processes have started again.
* ***Normal nodes (that operate)***, for NO Logs indicating failure or recovery attempts.



**B.2.b)  Failed recovery of a Netdata agent in a RL node**

```
Test Case Quick Notes:
- Kill the VM of any RL node.
- The EMS server will try to connect to the affected VM but fail.
- After a configured number of retries EMS server will give up.
```

**After Application deployment...**

* Terminate the VM of a RL node

**You need to check the logs of:**

* ***EMS server***, for logs reporting connection failure to a Netdata agent, and then a number of failed attempts to connect to VM.
* ***Normal nodes (that operate)***, for NO logs indicating connection failures or recovery actions.



**B.3)  Successful recovery of a Netdata agent in a Normal node**

```
Test Case Quick Notes:
- Kill Netdata agent of any Normal node.
- The EMS client of the node will recover the killed Netdata agent after a configured period of time.
- Check EMS client's logs for messages reporting failures to collect metrics, recovery actions, and successful metrics collection.
```

**After Application deployment...**

* Connect to a Normal node and kill Netdata agent.

**Next, check the logs of:**

* ***EMS server***, for No log messages indicating connection failures to Netdata, or recovery actions.
* ***Normal node with killed Netdata***, check if the Netdata processes have started again. Also check EMS client's log messages reporting failed metric collections, recovery actions, and successful metric collection.
* ***Normal nodes (that operate)***, for NO logs indicating connection failures or recovery actions.



### <u>Test Cases for 3-LEVEL topology</u>

> ***PREREQUISITE:***
>
> **You need a CAMEL model for 3-LEVEL topology:**
>
> * with two Requirement Sets:
    >   - for Normal nodes: 4 cores, 4GB RAM, >1 GB Disk, and
>   - for RL nodes: 1-2 cores, or <2GB RAM, or <1GB Disk,
> * with 1-2 COMPONENTS with Requirement Set #1 (Normal nodes)
> * with 1-2 COMPONENTS with Requirement Set #2 (RL nodes)
> * with three (3) Groupings used in the Metric Model  (`GLOBAL`, `PER_ZONE`, `PER_INSTANCE`).
>
> This CAMEL model is ***common*** for the following test cases, unless another CAMEL model is specified.
>
> CAMEL model MUST be re-deployed after each test case execution.



**B.4.a)  Successful recovery of an EMS client in a clustered Normal node**

```
Test Case Quick Notes:
- Kill EMS client of any Normal node except the Aggregator.
- The Aggregator will recover the killed EMS client after a configured period of time.
- Check Aggregator log messages for node leaving cluster, recovery actions, and node joining back.
```

**After Application deployment...**

* Connect to a Normal node, except Aggregator, and ***kill*** EMS client

**Next, check the logs of:**

* ***EMS server***, for NO logs related to EMS client leave and recovery.
* ***Aggregator***, for log messages about, (i) EMS client leaving cluster, (ii) recovery actions, and (iii) EMS client joining back to the cluster.
* ***Normal node whose EMS client killed***, for EMS client's logs indicating its restart.
* ***Other Normal nodes***, for logs about, (i) EMS client leaving cluster, (ii) EMS client joining to cluster, but NO logs about recovery actions.



**B.4.b)  Failed recovery of an EMS client in a clustered Normal node**

```
Test Case Quick Notes:
- Kill the VM of any Normal node, except Aggregator.
- The Aggregator will try to connect to the affected VM but fail.
- After a configured number of retries Aggregator will give up.
```

**After Application deployment...**

* Terminate the VM of a Normal node, except the Aggregator's

**Next, check the logs of:**

* ***EMS server***, for a recovery Give up message from Aggregator
* ***Aggregator***, for messages reporting, (i) an EMS client left cluster, (ii) a number of failed connection attempts to the VM, and (iii) a recovery give up message.
* ***Normal nodes that operate***, for logs about EMS client leaving cluster, and NO logs about recovery actions or EMS client joining back.



**B.5.a)  Successful recovery of EMS client of the cluster Aggregator**

```
Test Case Quick Notes:
- Kill EMS client of the Aggregator.
- The cluster nodes will elect a new Aggregator. Check logs of any cluster node.
- The new Aggregator will recover the killed EMS client after a configured period of time.
- Check new Aggregator log messages for node leaving cluster, being elected as Aggregator, recovery actions, and node joining back.
- Old Aggregator will join back as a Normal node.
```

**After Application deployment...**

* Connect to the Aggregator node, and ***kill*** EMS client.

**Next, check the logs of:**

* ***EMS server***, for message about Aggregator change.
* ***Old Aggregator node whose EMS client killed***, for EMS client's logs indicating its restart (as a `PER_INSTANCE` node).
* ***Other Normal nodes***, for log messages about, (i) EMS client leaving cluster, (ii) Aggregator election, (iii) EMS client joining to cluster, but NO logs about recovery actions.
* ***New Aggregator***, for log messages about, (i) EMS client leaving cluster, (ii) being elected as Aggregator, (iii) recovery actions, and (iv) EMS client joining to cluster.



**B.5.b)  Failed recovery of EMS client of the cluster Aggregator**

```
Test Case Quick Notes:
- Kill the VM of the Aggregator.
- The cluster nodes will elect a new Aggregator. Check logs of any cluster node.
- The new Aggregator will try to connect to the affected VM but fail.
- After a configured number of retries new Aggregator will give up.
```

**After Application deployment...**

* Terminate the VM of the Aggregator's

**Next, check the logs of:**

* ***EMS server***, for a message about Aggregator change, and one about Giving recovery, up from the new Aggregator.
* ***Normal nodes that operate***, for log messages about, (i) EMS client leaving cluster, (ii) Aggregator election, but NO logs about recovery actions, or EMS client joining back to cluster.
* ***New Aggregator***, for messages reporting, (i) an EMS client left cluster, (ii) being elected as Aggregator, (iii) a number of failed connection attempts to the VM, and (iv) a recovery give up message.



**B.6.a)  Successful recovery of Netdata agent in a clustered RL node**

```
Test Case Quick Notes:
- Kill Netdata agent of any RL node.
- The Aggregator will recover the killed Netdata agent after a configured period of time.
- Check Aggregator log messages reporting failures to collect metrics, recovery actions, and successful metrics collection.
```

**After Application deployment...**

* Connect to a RL node and ***kill*** Netdata agent.

**Next, check the logs of:**

* ***EMS server***, for NO logs indicating a Netdata failure and recovery.
* ***Aggregator***, for logs reporting, (i) connection failures to a Netdata agent, (ii) recovery actions, and (iii) successful connection to Netdata agent and collection of metrics.
* ***RL node with killed Netdata***, check if the Netdata processes have started again.
* ***Normal nodes (that operate)***, for NO logs indicating connection failures or recovery action.



**B.6.b)  Failed recovery of Netdata agent in a clustered RL node**

```
Test Case Quick Notes:
- Kill the VM of any RL node.
- The EMS server will try to connect to the affected VM but fail.
- After a configured number of retries EMS server will give up.
```

**After Application deployment...**

* Terminate the VM of a RL node

**You need to check the logs of:**

* ***EMS server***, for NO logs indicating a Netdata failure and recovery, BUT reporting a recovery give up from Aggregator.
* ***Aggregator***, for logs reporting (i) connection failures to a Netdata agent, (ii) a number of failed attempts to connect to VM, and (iii) a recovery give up message.
* ***Normal nodes (that operate)***, for NO logs indicating connection failures or recovery actions.



**B.7)  Successful recovery of Netdata agent in a clustered Normal node (including Aggregator)**

```
Test Case Quick Notes:
- Kill Netdata agent of any Normal node.
- The EMS client of the affected node will recover the killed Netdata agent after a configured period of time.
- Check EMS client's log for messages reporting failures to collect metrics, recovery actions, and successful metrics collection.
```

**After Application deployment...**

* Connect to a Normal node and ***kill*** Netdata agent.

**Next, check the logs of:**

* ***EMS server***, for No log messages indicating connection failures to a Netdata agent or recovery actions.
* ***Aggregator***, for No log messages indicating connection failures to a Netdata agent or recovery actions.
* ***Normal node with killed Netdata***, check if the Netdata processes have started again. Also check EMS client's log messages reporting failed metric collection attempts, recovery actions, and successful metric collection.
* ***Other Normal nodes (that operate)***, for NO logs indicating connection failures or recovery actions.



------

## Limitations and Bugs

* Clustering is never used for 2-level monitoring topologies.
* ***Bug:*** EMS clients do not give up after many recovery failures. -- No message is sent to EMS server for failed recoveries.
* When no Normal nodes (and hence no Aggregator) exist in a cluster, no one will collect metrics from the (orphan) RL nodes.
* When no Normal nodes (and hence no Aggregator) exist in a cluster, no one will recover the (orphan) RL nodes.
* If EMS server fails no one will recover it.
* Metric messages are not cached/redirected, if the next node has failed.
