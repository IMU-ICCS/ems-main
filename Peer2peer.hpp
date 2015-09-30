/*==============================================================================
  Peer-to-peer communication architecture

  This is an architecture for a distributed actor system [1]. An Actor is a 
  self-contained object that executes some actions and exchange information with
  other actors through messages. For performance, or other reasons, the actors
  may be distributed across multiple physical nodes. This distribution should
  be transparent for the actors so that a message from an actor on one node 
  needs to reach the destination actor even if this is located on another node.
  
  DISCOVERY
  
  It is assumed that inside each node there is a message broker that ensures 
  that messages addressed for actors on the same node reaches its destination 
  actor. If the address of a message is not known, the message should be 
  forwarded to the Network Actor being the node's external interface to actors
  located on remote peer nodes. The first task of the network actor when it 
  receives a new message will be to discover on which remote node the addressee 
  is located. Since this is a peer-to-peer architecture the result should be 
  the establishment of a direct connection between the two nodes having the two 
  actors that need to communicate.
  
  MESSAGE PASSING
  
  Once the communication has been established between the two nodes, the message
  and all future messages will be forwarded directly to the remote node, and 
  internally on that node to the receiving actor. The actors should not know 
  that the other actor is remote. Furthermore, only the two nodes involved 
  in the communication should know about it, and this communication should not
  disturb or interrupt any other node in any way.
  
  COMMUNICATION LIBRARY
  
  The fundamental mechanism for implementing communication in networks based 
  on the Internet Protocol (IP) is through Sockets. However, sockets are fairly
  low level mechanisms, and message passing is quite common so multiple 
  libraries abstracting over the sockets have been developed. One rather low-
  level is the Boost Asynchronous Input-Output (Boost::ASIO) library [2]. This
  library leaves the link level protocol completely up to the programmer, which
  would be fine if all the actors are encoded in the same framework and in the
  same programming language. However, this poses an unnecessary restriction on
  distributed actor systems. 
  
  At the next level of abstraction there is simple message queues like the 
  ZeroMQ [3] defining sockets for typical communication patterns. Application 
  programmer interfaces are provided in most programming languages, and the 
  link level protocol is efficient compared to many other message passing 
  libraries as a fairly recent evaluation by Dworak et al. [4] shows.
  
  An even higher level of abstraction is offered by frameworks with a message
  broker taking care of both the peer discovery and the message forwarding. 
  The most attractive solution here is ActiveMQ [5], which also supports a 
  wide range of programming languages. It is an implementation of the Advanced 
  Message Queuing Protocol (AMQP) [6], whose link level protocol has been 
  adopted as an OASIS standard, and recently adopted by ISO and IEC standard 
  19464 [7].
  
  Owing to the desire of having a pure peer-to-peer communication without the
  need for an additional message broker server, the current implementation is
  based on ZeroMQ.
  
  REFERENCES
  
  [1] Carl Hewitt, Peter Bishop, and Richard Steiger: "A Universal Modular
      ACTOR Formalism for Artificial Intelligence", Proceedings of the 3rd 
      International Joint Conference on Artificial Intelligence, San Francisco, 
      CA, USA, pp. 235–245, Morgan Kaufmann Publishers, 1973
  [2] http://www.boost.org/doc/libs/1_55_0/doc/html/boost_asio.html
  [3] http://zeromq.org/
  [4] http://accelconf.web.cern.ch/accelconf/icalepcs2011/papers/frbhmult05.pdf
  [5] http://activemq.apache.org/
  [6] http://en.wikipedia.org/wiki/AMQP
  [7] http://www.iso.org/iso/home/store/catalogue_tc/catalogue_detail.htm?csnumber=64955
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
==============================================================================*/

#ifndef PEER_2_PEER
#define PEER_2_PEER

#include <string>
#include <map>
#include <set>
#include <thread>

// The ZeroMQ library is written in C, and the below code is based on the C++
// API that must be cloned from https://github.com/zeromq/zmqpp
// Note that library ZeroMQ itself is often included as a package with various
// Linux distributions.

#include <zmqpp.hpp>

// All the code is defined in a separate namespace to reduce naming conflicts 
// with any similarly named classes

namespace Peer2peer 
{

/******************************************************************************
 DISCOVERY
 
 The main issue in peer-to-peer discovery is to bootstrap the connections: How 
 does a new peer know about the others, and how does it connect to the others?
 As outlined above, this framework distinguish between the discovery phase 
 for new actors, and the message passing between actors. It is no problem 
 with a centralised approach for the discovery since the traffic volume will 
 be limited (typically one discovery per sender wanting to talk to a particular
 actor = in the worst case N*N if all actors want to send messages to all other
 actors). Thus, one of the peers assumes the role of a Coordinator for the 
 discovery operations.
 
 The protocol is then as follows:
 
 1) A message for an actor not on the same node as the sender generates a 
    discovery request. This request contains the ID of the addressee and the
    IP address of the node hosting the sender.
 
 2) The Coordinator saves this message. This is important because new peer 
    nodes can join at any time, and the node hosting the requested actor may 
    not yet be connected. Thus, when a new node connects, all pending discovery
    requests will be retransmitted.
    
 3) The Coordinator forwards the discovery message to all connected peers.
 
 4) The node that has the actor being requested subscribes to the message 
    channel of the node hosting the actor that wants to send a message. This
    is a pure peer-to-peer operation only involving the two nodes hosting the 
    communicating actors. Furthermore, the way the publish socket works on the 
    sender node, the sender node will not be notified about the new 
    subscription to its message publishing channel.
    
 5) The node that has the actor being requested sends a confirmation request
    back to the Coordinator.
    
 6) The Coordinator notes that this discovery request has been successfully 
    handled, and deletes its cached copy of the request.
    
 7) The Coordinator forwards the confirmation message to all nodes, and as
    the confirmation message contains the ID of the addressee the node 
    initiating the request for a discovery of this actor will know that the 
    remote node hosting the actor has now subscribed to the sender node's 
    message channel.
    
 8) The Sender node publishes the actual message for the remote actor on its
    message channel.
    
 9) Finally, the pending request for this remote actor can be marked as handled,
    and deleted from the queue of pending discovery requests.
    
 This protocol is illustrated in the Discovery figure. Note that it is 
 asynchronous, and it can take time from the discovery request is sent from a
 node until it receives the confirmation of a subscription if the node hosting
 the desired actor is not connected at the time of the request.
 
 In addition to this there is a handshake port used when nodes come on-line 
 or when they go off-line. 

******************************************************************************/

// The Actor ID tag is in this version just a string, but if the actors can be
// identified differently this could be changed to anything that supports 
// comparison (sorting)

typedef std::string	ActorIDtag;

// The discovery mechanism uses a fixed TCP port on all nodes, including the 
// coordinator. This should be changed to a port not used by other protocols.

const std::string DiscoveryPort = "4242";

// When new nodes come online they connect to the coordination node on its
// handshake port. It should be the port above the DiscoveryPort, and the ports
// used should be in a consequtive range.

const std::string HandshakePort = "4243";

// The first class in the discovery part is the one created by the coordinator
// peer. All the other peers connect to the input channel of the coordinator,
// and the coordinator confirms by connecting to the peer's discovery channel. 

class DiscoveryCoordinator
{
public:
  
  // The discovery phase supports a set of commands. Currently only the 
  // discovery is supported, although in the future also dynamic creation or 
  // removal of actors can be implemented. The syntax of a request message
  // shoud be "DISCOVERY <ActorID> <Requester IP>" so that the remote node
  // hosting the requesteded <ActorID> can respond with a subscription to 
  // the <Requester IP>'s actor message channel. The successful discovery 
  // is acknowledged with a message "CONFIRM <ActorID> <Host IP>".
  
  enum class Command
  {
    Discover, // Which node has an actor with <this actor address>?
    Confirm,  // ...it is here on <this IP address>
    ShutDown, // Job done - all nodes should terminate.
    OnLine,   // Used by a node to tell that it is on-line
    OffLine,  // used by a node to tell that it is disconnecting
    Invalid   // The received command was not valid
  };
  
private:
  
  // This class maintains two sockets: One used to publish requests to all
  // other nodes, and one to receive responses from the other nodes.
  
  zmqpp::socket BroadcastChannel, InputChannel, HandshakeChannel;
  
  // Then there is a cache of pending commands. This is sorted on the 
  // actor IDs and contains the pending commands for this actor (currently 
  // there is only the discovery action so a map is used.) 
  
  std::map< ActorIDtag, zmqpp::message >	PendingActions;
  
  // The subscription handler is called whenever a peer connects to the 
  // broadcast channel. It makes sure that the input channel of the coordinator
  // subscribes (connects) to the discovery channel of the new peer. It will 
  // then send all pending actions on the broadcast channel so that the newly 
  // subscribed peer can respond with confirmations for the actors on that 
  // node.
  
  void HandleSubscription (void);
  
  // The command handler reads the command off the input channel, and stores it 
  // in the pending action if it is a discover command. If it is a confirmation 
  // command, it will remove the pending action as direct peer to peer 
  // communication between the two involved nodes should have been established.
  
  void HandleCommand (void);
  
  // The main activity of this class is to listen for incoming traffic on the 
  // two sockets, and either handle subscriptions or handle commands if 
  // if something has arrived. The following function implements this loop
  // and will run in a separate thread blocking for something to arrive on
  // either of the two sockets.
  
  void ProxyLoop (void);
  
  // Then the thread that executes this proxy loop
  
  std::thread ExecuteProxy;
  
  // The execution of the proxy loop continues until the shut down signal is 
  // received from one of the computing nodes. This will irrevocably terminate
  // the loop, and should be the last thing done before the discovery 
  // coordinator goes out of scope.
  
  bool ShutDownSignal;
  
public:
  
  // The constructor takes the ZeroMQ context as a parameter, and initialises
  // the two sockets before starting the proxy loop. The context is not a part
  // of this class since the node owning the discovery coordinator may also 
  // use the same context for other communication needs.
  
  DiscoveryCoordinator( zmqpp::context const & CommunicationContext, 
			std::string    const & ThisIPAddress  		);
  
  // The destructor waits for the proxy loop to terminate as a response of a 
  // shut down command received from one of the nodes. Thus, even if this 
  // class goes out of scope, the discovery coordinator will wait for the 
  // shut down signal before allowing the server to close.
  
  virtual ~DiscoveryCoordinator (void)
  { 
    ShutDownSignal = true;
    ExecuteProxy.join();
  }
};

/******************************************************************************
 NODE
 
 The node class uses two sockets, one for passing messages between actors 
 hosted on the nodes, and one for signing in or off from the coordinator node.
 The discovery is initiated only when a local actor wants to send a message to
 a remote actor for the first time. The message to send is then put on hold 
 until the node hosting the remote actor has been identified. 
 
 A publish-subscribe model is used to transmit the message since the filtering
 happens on the sender side in ZeroMQ. This implies that a message is sent only
 to the nodes subscribing to the message, and only the node hosting the 
 addressee will subscribe. In this way it will act like a point-to-point 
 communication with the benefit that the sender will not block waiting for 
 a response (which may in general come at a much later time, or not be need 
 at all).
 
 Both actor IDs and the actual message are assumed to be already serialised,
 or as basic types that are supported by the ZeroMQ message. On the receiving
 side, the ZeroMQ message is delivered directly to the message receiver 
 methods, which must be implemented correctly for the actor framework using
 this class (so that the messages will be correctly routed to the receiver 
 actor).
 
 The class is not supposed to be used on its own, but should be inherited to
 support the local actor framework used by the node. The core methods for 
 communication are therefore protected or pure virtual functions preventing 
 direct instantiation of this class.

******************************************************************************/

// First the message port is defined. This should follow directly after the 
// ports used for discovery

const std::string MessagePort = "4244";

class Node
{
private:
  
  // There are five sockets being used by the node. One to handshake with the 
  // coordinator node, one to publish messages to remote actors, and one to 
  // receive messages from remote nodes targeted for actors on this node. In
  // addition there are two channels for the discovery process
  
  zmqpp::socket SendChannel, ReceiveChannel,       // Peer to peer messages
		BroadcastChannel, ReplyChannel,    // Discovery channels
		HandshakeChannel;		   
  
  // There is set covering the known remote addresses. If an actor address is
  // found in this structure, then the message is forwarded on the message 
  // channel. If it is not found in this set, then the node will initiate the 
  // discovery process.
  
  std::set< ActorIDtag > RemoteActors;
  
  // Similarly there is a map holding the pending messages waiting for the 
  // remote node to indicate that it has connected to this node's message 
  // channel. A pending message will be forwarded automatically as soon as 
  // confirmation is received from the remote node.
  
  std::map< ActorIDtag, std::string > PendingMessages;
  
  // Messages are received by a method running in a separate thread always 
  // waiting for some input on one of the sockets.
  
  void MessageLoop (void);
  
  // This message loop will run in the dedicated thread object
  
  std::thread MessageThread;
  
  // There is a minor issue resulting from the fact that it will be this 
  // receiving thread that will detect that a remote actor's node has been 
  // discovered. Then it will send the pending messages on the sending socket.
  // However, sending on this socket can also be directly initiated by the 
  // actor system running on the node. Thus, two threads could in principle 
  // try to send on the same socket at the same time. The following mutex 
  // takes care of serializing this access, and it will be manage by the 
  // SendMessage method below.
  
  std::mutex SendAccess;
  
  // The execution thread will run as long as no shut down has been signalled
  
  bool ShutDownSignal;
  
  // The local IP address is also stored as it should be included in the 
  // discovery requests
  
  std::string LocalIP;
  
protected:
  
  // The function to send a message to a remote actor takes the unique ID of 
  // this actor and the message as arguments. It first checks to see if this
  // actor is already known and has subscribed, in which case the message is 
  // just sent. If not, the message is stored and a discovery request is 
  // sent, and the message left for later sending. Note that according to the 
  // actor model, the sender's address should be included in the message, but
  // how to do this depends on the actor implementation and how the message 
  // serialisation is done, so it must be taken care of by the extensions of 
  // this class.
  
  void SendMessage ( const ActorIDtag  & RemoteActor, 
		     const std::string & Content   );
  
  // When a message is received it is passed to the receive message method
  // which should be implemented according to the particular actor framework
  // operating on this node.
  
  virtual void ReciveMessage ( const ActorIDtag  & ReceiverActor,
			       const std::string & TheMessage      ) = 0;
       
  // When a discovery request arrives, the node must be able to verify if this
  // actor is hosted on this node. Again, this is only possible to do for the 
  // local actor framework, and the following function should return true if
  // the actor is on this node.

  virtual bool LocalActorQ ( const ActorIDtag ActorID ) = 0;
       
public:
  
  // The constructor needs the context of ZeroMQ, and the IP address of this
  // node and the IP address of the potentially remote discovery coordinator.
  
  Node ( const zmqpp::context & CommunicationContext, 
	 const std::string    & LocalIPAddress,
	 const std::string    & CoordinatorIPAddress  );
  
  // The destructor is slightly more complex so it is also implemented in 
  // the source file. It will first send a message on the handshake channel 
  // that we are going off-line, then it will set the shut down flag and 
  // wait for the communication thread to stop.
  
  virtual ~Node (void);
};


/******************************************************************************
 END
******************************************************************************/
}  	// Namespace Peer2peer
#endif 	// PEER_2_PEER