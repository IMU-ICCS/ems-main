/*=============================================================================
  Metric

  A metric is a monitored value that recedes in the metadata database. When 
  a new value is received from a metric it goes to the Metric Collector 
  component that will update the atomic metric in the metadata database, and
  compute all composite metrics depending on this particular measurement. It 
  will then publish the new values of the changed metrics to remote subscribers.
  The metadata database can be seen as one such subscriber for all metrics, and 
  the metric interface of the solver subscribes to the metrics used in the 
  constraints and utility function.

  The actual mechanism for the distribution of the metric values could range 
  from a direct socket based implementation using the Boost Asynchronous Input 
  Output library (ASIO) [1], to the ZeroMQ [2] offering a publish-subscribe 
  abstraction over sockets, to a generic message queue model based on the 
  Advanced Message Queuing Protocol (AMQP) standard [3]. 
  
  <At the time of writing it is still unclear which option is chosen the 
   current implementation assumes ASIO >
  
  It has become apparent that the XS queue currently supported by the Theron
  library [4] was a fork from ZeroMQ, and has received little attention since 
  the split. Thus, the Theron distribution model also needs an overhaul. The 
  decision has therefore been made to base the metric mechanism of PaaSage on
  the mechanism of Theron, and then rather change or extend Theron's 
  distribution mechanism with the mechanism of PaaSage if possible.
  
  The implication of this architecture is that each metric should be a Theron
  receiver. It need not be an actor since it is not supposed to send any 
  messages to other actors. There is only one problem: Theron cannot be 
  used as global variables and the endpoint and framework must be created 
  after main has started. However, the metrics are used in the constraints and
  the utility function, i.e. in the files that are compiled and linked with
  the solver code. Thus, the metrics are global variables. They can therefore
  not be (inherit) a Theron receiver (even though this would be the cleanest 
  approach). 
  
  The solution is the CDOInterface object: It maintains a list of the metrics 
  as they are created (as global variables), however the interface object 
  itself will not be constructed before main is up and running since it 
  requires the IP address and the port of the remote message queue server, and
  these will typically be parameters on the command line.
  
  <The rest of the architecture depends on the communication technology chosen
   for PaaSage>
  
  References:
  
  [1] http://www.boost.org/doc/libs/1_55_0/doc/html/boost_asio.html
  [2] http://zeromq.org/
  [3] http://en.wikipedia.org/wiki/Advanced_Message_Queuing_Protocol
  [4] http://docs.theron-library.com/6.00/index.html
  
  Author: Geir Horn, 2014
  License: LGPL3.0
=============================================================================*/

#include <boost/asio.hpp>
#include <thread>
#include "Interface.hpp"

/*****************************************************************************
 TCP Socket Handler

 This is a global object that takes care of initialising the ASIO layer and
 setting up the socket for communicating with the remote CDO server. This 
 means that it hardcodes the IP port used by the server, and a maximum size 
 for the message buffer.
 
******************************************************************************/

using namespace boost::asio;

class TCPSocketHandler
{
private:
  
  // All IO in the ASIO library requires a service object to handle the 
  // communication.
  
  io_service IOServices;
  
  // In order to figure out the destination address of the server there is
  // a resolver iterator set to point to the remote server
  
  tcp::resolver::iterator Server;
  
  // IO operations are performed on a TCP socket.
  
  ip::tcp::socket Socket;
  
  // Incoming packets are accepted using an acceptor object
  
  ip::tcp::acceptor Receiver;
  
  // Finally there is a standard thread to keep the communication alive 
  // until the program shuts down.
  
  std::thread ExecutionThread;
  
protected:
  
  // The GetData function is called on an incoming connection and ensures 
  // that the actual message is read from the socket, before the handler 
  // starts waiting for the next connection. 
  
  void GetData ( void )
  {
    std::string TheMessage;
    
    read( Socket, boost::asio::buffer( TheMessage ) );
      
    // Forward the message to the right metric
    Solver::CDOInterface::ReceiveNewValue( TheMessage );
      
    // Start the wait for the next message with this function as input 
    // handler called through a delegation lambda function catching the this 
    // pointer
    
    Receiver.async_accept( Socket, 
      [this](boost::system::error_code Error)
      {
	if ( !Error ) GetData();
      });
  };
  
public:
  
  // The constructor initialises the the service object and the socket and 
  // connects these together. The rest of the initialisation is left for the 
  // start function below.
  
  TCPSocketHandler (void)
  : IOServices(), Socket( IOServices ), Receiver( IOServices )
  { };
  
  // The destructor closes the socket and implicitly cancel all pending 
  // operations by destroying the IOServices object
  
  ~TCPSocketHandler (void)
  {
    Socket.close();
  };
  
  // The actual initialisation is made in the Start function that makes sure
  // the right connection is made for the CDO server and that the thread 
  // waiting for data to arrive is started on the IO services.
  
  void Start (const std::string & ServerIP, const unsigned int & ServerPort)
  {
    // First we find the address of the server
    
    tcp::resolver Resolver( IOServices );
    
    Server = Resolver.resolve( tcp::resolver::query( 
			       ServerIP, std::to_string( ServerPort ) ) );
    
    // Then we connect to the server and register the callback for the GetData
    // function. Note the use of a lambda function in order to capture the 
    // this pointer and to cal the GetData on this object. This is necessary
    // in order to be able to write to the server before receiving anything.
    
    async_connect( Socket, Server, 
	[this](boost::system::error_code Error, tcp::resolver::iterator)
	{
	  if ( !Error ) GetData();
	});
    
    // Then we initialise the receiver with the local endpoint by converting
    // the string to an unsigned int.
    
    ip::tcp::endpoint LocalEndpoint( ip::tcp::v4(), ServerPort );
    
    // Then this endpoint is used to initiate and bind the receiver
    
    Receiver.open( LocalEndpoint.protocol() );
    Receiver.bind( LocalEndpoint );
    // Receiver.listen(); // May not be necessary with the accept call?
    
    // Then we register the handler to listen to incoming connections on this
    // socket and forward them to the get data handler.
    
    Receiver.async_accept( Socket, 
      [this](boost::system::error_code Error)
      {
	if ( !Error ) GetData();
      });
    
    // Finally, the thread that runs the IOServices is started
    
    ExecutionThread = std::thread( [&IOServices]()
    {
      IOServices.run();
    });
  };
  
  // There is a function to write a string to the socket. This is a 
  // simplification in that it will simply return a boolean true to indicate
  // if all bytes was written and no error occured. Otherwise, it will return
  // false
  
  bool Write ( const std::string & Message )
  {
    boost::system::error_code ErrorCode;
    std::size_t		      BytesWritten;
    
    BytesWritten = boost::asio::write( Socket, boost::asio::buffer( Message ),
				       ErrorCode  );
  }
  
} CommuncationHandler;

/*****************************************************************************

 CDOInterface
 
 This is the base class for all metrics and uses the global class for
 a) Registering the metric object as a subscriber with the remote CDO server
 b) Registering the handler function for incoming value updates
 c) Dispatch the new value to the corresponding metric object
 
******************************************************************************/

// There is a mapping between the metrics ID and its handling object which 
// is ensured at the CDO interface level so that when an incoming value 
// message arrives it can be forwarded to the right metrics object.

std::map< MetricsID, MetricBase * > Solver::CDOInterface::SubscribedMetrics();

// The CDO Interface needs to know the address of the CDO server and these 
// are global variables that applies to all metrics and here defined as 
// place holders - however the initialisation of the port is important since it
// is used as a flag to decide when to initialise the communication with the 
// server.
// 
// Own IP address resolution (if this ever will be necessary):
// http://stackoverflow.com/questions/2146191/obtaining-local-ip-address-using-getaddrinfo-c-function

std::string  Solver::CDOInterface::ServerIPAddress();
unsigned int Solver::CDOInterface::ServerTCPPortNumber = 0;

// These variables will be set by the constructor.  

void Solver::CDOInterface::CDOInterface ( Theron::Framework & TheFramework,
					  const std::string & IPAddress, 
					  const unsigned int PortNumber   )
{
  ServerIPAddress     = IPAddress;
  ServerTCPPortNumber = PortNumber;
  
  // Then we can proceed to initialisation of the communication

  CommuncationHandler.Start( ServerIPAddress, ServerTCPPortNumber );
  
  // Finally we may register all the defined metrics as subscribers to the 
  // CDO server.

  for ( std::pair< MetricsID, CDOInterface * > TheMetric : SubscribedMetrics )
  {
    std::stringstream Message;
  
    Message << TheMetric.first;
  
    CommuncationHandler.Write( Message.str() ); 
  }  
};


// Geir 17 April: This should be moved to the object receiving the messages
// from the Metric Collector.

// The callback handler will receive a string with two fields from the 
// socket layer: the first field is the metric ID and the second field is 
// the new value of this metric. It uses the first field to look up the right 
// object to handle the second field when it is forwarded to the set value 
// function.

void Solver::CDOInterface::ReceiveNewValue( std::string ValueMessage )
{
  std::stringstream Message( ValueMessage );
  MetricsID    	    TheID;
  std::string  	    TheValue;
  
  TheID    << Message;
  TheValue << Message;
  
  // Then we look for the object in charge of the metric with this ID and 
  // and sets the value for that object. If no such object exists, we have 
  // recevied either a corrupted message or a message not for this solver,
  // but there is no way to indicate this exception back to the server and so
  // the wrong message will simply be ignored.
  
  auto MetricObject = SubscribedMetrics.find( TheID );
  
  if ( MetricObject != SubscribedMetrics.end() )
    MetricObject->second->SetValue( TheValue );
}