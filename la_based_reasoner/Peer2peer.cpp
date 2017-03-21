/*==============================================================================
  Peer-to-peer communication architecture

  This file contains the implementation for the classes of the peer2peer 
  header, and the purpose of each method is explained in the header file. 
  The comments here are limited to details of the actual implementation only.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
==============================================================================*/

#include <utility>
#include "Peer2peer.hpp"

/******************************************************************************
 Discovery Coordinator
******************************************************************************/

// First we set up a static map that can convert string versions of the 
// commands to the correct enumerations. It is defined this way to allow
// the commands to be written in mixed cases or as only upper case. It is 
// recommended that the upper case versions are used

const std::map < std::string, Peer2peer::DiscoveryCoordinator::Command >
    CommandMap = {
      { "Discover", Peer2peer::DiscoveryCoordinator::Command::Discover },
      { "DISCOVER", Peer2peer::DiscoveryCoordinator::Command::Discover },
      { "Confirm",  Peer2peer::DiscoveryCoordinator::Command::Confirm  },
      { "CONFIRM",  Peer2peer::DiscoveryCoordinator::Command::Confirm  },
      { "ShutDown", Peer2peer::DiscoveryCoordinator::Command::ShutDown },
      { "SHUTDOWN", Peer2peer::DiscoveryCoordinator::Command::ShutDown },
      { "OnLine",   Peer2peer::DiscoveryCoordinator::Command::OnLine   },
      { "ONLINE",   Peer2peer::DiscoveryCoordinator::Command::OnLine   },
      { "OffLine",  Peer2peer::DiscoveryCoordinator::Command::OffLine  },
      { "OFFLINE",  Peer2peer::DiscoveryCoordinator::Command::OffLine  },
      { "Invalid",  Peer2peer::DiscoveryCoordinator::Command::Invalid  },
      { "INVALID",  Peer2peer::DiscoveryCoordinator::Command::Invalid  }
    };

// This class is best understood by starting with its main loop implemented 
// by the proxy loop function

void Peer2peer::DiscoveryCoordinator::ProxyLoop(void)
{
  zmqpp::poller SocketMonitor;  // The sockets are monitored by a poller
  
  // The two sockets are added to the monitor, with instructions to wait for
  // input to happen on the sockets.
  
  SocketMonitor.add( InputChannel,     zmqpp::poller::poll_in );
  SocketMonitor.add( HandshakeChannel, zmqpp::poller::poll_in );
  
  // The main loop starts the polling of the sockets in a blocking mode,
  // and then if there is input on any socket, the corresponding handler
  // function is called to service the event. 
  
  while ( !ShutDownSignal )
  {
    SocketMonitor.poll( zmqpp::poller::wait_forever );
    
    if ( SocketMonitor.has_input( InputChannel ) )
      HandleCommand();
    
    if ( SocketMonitor.has_input( HandshakeChannel ) )
      HandleSubscription();
  }
  
  // In order to terminate cleanly, the registered sockets are removed from 
  // the monitor.
  
  SocketMonitor.remove( HandshakeChannel );
  SocketMonitor.remove( InputChannel     );
}

// Subscriptions arrive on the handshake channel as special messages consisting
// of two frames, the first is the command, and the second is the IP address of
// the subscribing node.

void Peer2peer::DiscoveryCoordinator::HandleSubscription(void)
{
  std::string	     CommandString, SenderIP;
  zmqpp::message     Message, Acknowledge;
  
  // First we get the message from the handshake channel and extract the 
  // command and the sender IP
  
  HandshakeChannel.receive( Message );
  Message >> CommandString >> SenderIP;
  
  // There is then a check to see if the command is legal, and there is only 
  // necessary to do something if the command tells us that a node is going 
  // on-line - then we should connect our input channel to the node's 
  // discovery channel before we acknowledge the message. We probably do not 
  // need to subscribe the channel as it only sets the message filter and 
  // in this case we subscribe to everything sent on the discovery channel.
  
  auto Command = CommandMap.find( CommandString );
  
  if ( Command != CommandMap.end() )
    switch ( Command->second )
    {
      case Peer2peer::DiscoveryCoordinator::Command::OnLine:
	InputChannel.connect( "tcp://" + SenderIP + ":" + DiscoveryPort );
	
	// If there are unresolved requests, these will be forwarded again
	// hoping that the new node can catch some of these. Note that the 
	// command (second field) should be sent before the 
	
	for ( auto Action = PendingActions.begin();
	      Action != PendingActions.end(); ++Action )
	  BroadcastChannel.send( Action->second );
	
	// The acknowledgement is the same as for the request to go off-line
      case Peer2peer::DiscoveryCoordinator::Command::OffLine:
	Acknowledge << "CONFIRM";
	break;
      default:
	Acknowledge << "INVALID";
    };
    
  // Finally we acknowledge the command with the outcome of the various 
  // operations
    
  HandshakeChannel.send( Acknowledge );
}

// In the normal situation there are requests flowing on the input channel.
// These will be forwarded on the broadcast channel, and if the message is 
// a discovery request, then the message will be stored until we see the 
// corresponding confirmation for that request. When this is received we will
// clear the pending request for this actor.

void Peer2peer::DiscoveryCoordinator::HandleCommand(void)
{
  zmqpp::message Request;
  
  InputChannel.receive(  Request );
  BroadcastChannel.send( Request );
  
  std::string CommandString;
  ActorIDtag  ActorID;
  
  Request >> CommandString;
  
  // If the command is valid and correspond to a discovery request it is 
  // stored in the pending actions map, and if it is a confirmation then the 
  // corresponding pending action is deleted. All other actions are ignored
  // for now.
  
  auto Command = CommandMap.find( CommandString );
  
  if ( Command != CommandMap.end() )
    switch ( Command->second )
    {
      case Peer2peer::DiscoveryCoordinator::Command::Discover :
	Request >> ActorID;
	PendingActions.insert( make_pair( ActorID, Request) );
	break;
      case Peer2peer::DiscoveryCoordinator::Command::Confirm :
	Request >> ActorID;
	PendingActions.erase( ActorID );
	break;
      case Peer2peer::DiscoveryCoordinator::Command::ShutDown :
	ShutDownSignal = true;
	break;
      default:
	// We do nothing for all other commands
	break;
    };
}

// The constructor of the discovery coordinator binds the sockets to the 
// given context, and binds the ports to the sockets used for the discovery
// protocol. Then it will start the thread to wait for incoming messages.

Peer2peer::DiscoveryCoordinator::DiscoveryCoordinator (
  const zmqpp::context & CommunicationContext, 
  const std::string & ThisIPAddress )
: BroadcastChannel( CommunicationContext, zmqpp::socket_type::publish ),
  InputChannel(     CommunicationContext, zmqpp::socket_type::subscribe ),
  HandshakeChannel( CommunicationContext, zmqpp::socket_type::reply ),
  PendingActions(), ExecuteProxy()
{
  // Binding the sockets to the right ports
  
  BroadcastChannel.bind( "tcp://" + ThisIPAddress + ":" + DiscoveryPort );
  HandshakeChannel.bind( "tcp://" + ThisIPAddress + ":" + HandshakePort );
  
  // Starting the thread waiting for the sockets to receive input from new
  // nodes coming on-line
  
  ShutDownSignal = false;
  
  ExecuteProxy = std::thread( &DiscoveryCoordinator::ProxyLoop, this );
}

/******************************************************************************
 Node
******************************************************************************/

// The constructor starts the sockets and connects them or binds them to the 
// remote publishers or the correct sockets. Then it starts the main thread 
// listening for incoming messages.

Peer2peer::Node::Node( 
  const zmqpp::context & CommunicationContext, 
  const std::string & LocalIPAddress, 
  const std::string & CoordinatorIPAddress )
: SendChannel(      CommunicationContext, zmqpp::socket_type::publish ),
  ReceiveChannel(   CommunicationContext, zmqpp::socket_type::subscribe ),
  BroadcastChannel( CommunicationContext, zmqpp::socket_type::publish ),
  ReplyChannel(     CommunicationContext, zmqpp::socket_type::subscribe ),
  HandshakeChannel( CommunicationContext, zmqpp::socket_type::request ),
  RemoteActors(), PendingMessages(), 
  MessageThread(), SendAccess()
{
  // Binding the local send channels
  
  SendChannel.bind(      "tcp://" + LocalIPAddress + ":" + MessagePort );
  BroadcastChannel.bind( "tcp://" + LocalIPAddress + ":" + DiscoveryPort );
  
  // Connecting to the discovery coordinator node
  
  ReplyChannel.connect( "tcp://" + CoordinatorIPAddress + ":" + DiscoveryPort );
  HandshakeChannel.connect( "tcp://" + CoordinatorIPAddress + ":" 
			    + HandshakePort);
  
  // Initiate other local variables and start the thread to listen for incoming
  // communication
  
  LocalIP 	 = LocalIPAddress;
  ShutDownSignal = false;
  MessageThread  = std::thread( &Peer2peer::Node::MessageLoop, this );
  
  // Finally we can tell the discovery coordinator node that we are on-line
  
  zmqpp::message HeartbeatMessage;
  
  HeartbeatMessage << "ONLINE" << LocalIP;
  HandshakeChannel.send( HeartbeatMessage );
}

// When a local actor wants to send a message we will need to check if the 
// addressee's hosting node has subscribed to the publish channel, in which 
// case we can easily send the message. If the remote actor is not known, 
// a discovery process will be initiated.

void Peer2peer::Node::SendMessage( const Peer2peer::ActorIDtag & RemoteActor, 
				   const std::string & Content )
{
  zmqpp::message Message;

  if ( RemoteActors.find( RemoteActor ) != RemoteActors.end() )
  {
    // The normal situation where the node hosting the remote actor has a 
    // subscription so that we can just format and send the message.
    
    Message << RemoteActor << Content;
    
    SendAccess.lock();
    SendChannel.send( Message );
    SendAccess.unlock();
  }
  else
  {
    // As the remote actor is not known at this point, we first store the 
    // message as pending and initiate the discovery of the hosting node.
    
    PendingMessages.insert( make_pair( RemoteActor, Content ) );
    
    Message << "DISCOVER" << RemoteActor << LocalIP;
    SendAccess.lock();
    BroadcastChannel.send( Message );
    SendAccess.unlock();
  }
}

// The most complex method is the message loop that will wait for incoming 
// messages on all sockets and then respond to the information accordingly.

void Peer2peer::Node::MessageLoop(void)
{
  zmqpp::poller SocketMonitor;  // The sockets are monitored by a poller
  
  // The sockets are added to the monitor, with instructions to wait for
  // input to happen on the sockets.
  
  SocketMonitor.add( ReceiveChannel,   zmqpp::poller::poll_in );
  SocketMonitor.add( ReplyChannel,     zmqpp::poller::poll_in );
  SocketMonitor.add( HandshakeChannel, zmqpp::poller::poll_in );
  
  // The main loop starts the polling of the sockets in a blocking mode,
  // and then if there is input on any socket, the corresponding action
  // is taken. Note that the message object is constructed within the scope 
  // where it will be used. This is done to ensure that the memory it allocates
  // will be properly destructed when it goes out of scope.
  
  while ( !ShutDownSignal )
  {
    SocketMonitor.poll( zmqpp::poller::wait_forever );
    
    // Messages for actors on this node is is simply read off the socket and
    // handed over to the message handler provided by the local agent framework
    
    if ( SocketMonitor.has_input( ReceiveChannel ) )
    {
      zmqpp::message Message;
      std::string    ActorID, Content;
      
      ReceiveChannel.receive( Message );
      Message >> ActorID >> Content;
      
      ReciveMessage( ActorID, Content );
    }
    
    // Messages on the reply channel mean that there are incoming discovery 
    // actions, either requests for identifying actors hosted on this node or
    // responses to the node's own discovery requests.
    
    if ( SocketMonitor.has_input( ReplyChannel ) )
    {
      // Obtain the message from the socket
      
      zmqpp::message Message;
      std::string CommandString, ActorID, RemoteIP;
      
      ReplyChannel.receive( Message );
      Message >> CommandString >> ActorID;
      
      // Check and handle the commands
      
      auto Command = CommandMap.find( CommandString );
      
      if ( Command != CommandMap.end() )
	switch ( Command->second )
	{
	  case Peer2peer::DiscoveryCoordinator::Command::Confirm :
	    // If the actor ID confirmed has a pending message from an actor on
	    // this node, it should be registered as a known remote actor and
	    // the message should be forwarded.
	    
	    auto Pending = PendingMessages.find( ActorID );
	    
	    if ( Pending != PendingMessages.end() )
	    {
	      RemoteActors.insert( ActorID );
	      SendMessage( ActorID, Pending->second );
	    }
	    break;
	  case Peer2peer::DiscoveryCoordinator::Command::Discover :
	    // If we have received a discovery request, we only need to react 
	    // if the actor whose ID is received belong to this node. 
	    
	    if ( LocalActorQ( ActorID ) )
	    {
	      // We should first set up a filter to receive messages for this 
	      // actor and subscribe to the remote node's message channel. 
	      // The documentation say that the subscription has to be done 
	      // before connection (on the C version). How this work with 
	      // the C++ API, and on sockets that are already connected is
	      // not clear. We assume here that connecting a second time 
	      // will not harm, and ensure that we push all the subscriptions 
	      // to the publisher (sender side) where the filtering of 
	      // outbound messages takes place (if TCP is used as protocol).
	      
	      Message >> RemoteIP;
	      ReceiveChannel.subscribe( ActorID );
	      ReceiveChannel.connect( "tcp://" + RemoteIP + ":" + MessagePort );
	      
	      // Then we have to confirm the connection
	    }
	    
	    break;
	}
    }
  }
}
