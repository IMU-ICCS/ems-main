/*=============================================================================
  Get Metric Value

  This is a small test programme setting up the metric store, register a
  metric and wait for its value to be updated.

  This file currently implements the raw communication pending the re-factoring
  of the AMQ support of Theron to a full blown networking layer.

  It seems that a requirement for AMQ is the APR and CPRtools packages and
  they need non-standard includes:
  -I/usr/include/apr-1

  Copyright (c) 2018 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <string>
#include <iostream>
#include <memory>   //unique pointers
#include <ctime>
#include <chrono>
#include <thread>

#include "Metrics.hpp"
#include "MetricStore.hpp"

#include <activemq/core/ActiveMQConnectionFactory.h>
#include <activemq/core/ActiveMQConnection.h>
#include <activemq/cmsutil/CachedConsumer.h>
#include <activemq/library/ActiveMQCPP.h>

#include <cms/Connection.h>
#include <cms/Session.h>
#include <cms/TextMessage.h>
#include <cms/MessageListener.h>

// The metric listener is a simple class that takes a metric and updates its
// value whenever there is a new AMQ message on the topic corresponding to the metric

class MetricUpdater : public cms::MessageListener
{
private:

	cms::Destination *                  Destination;
	activemq::cmsutil::CachedConsumer * MessageMonitor;

	LASolver::MetricValue *             TheMetric;

public:

	virtual void onMessage( const cms::Message* TheMessage ) override
	{
		const cms::TextMessage * TextMessage
													  = dynamic_cast< const cms::TextMessage * >( TheMessage );

	  if ( TextMessage != nullptr )
			TheMetric->Value( TextMessage->getText() );
	}

	// The constructor takes the pointer to the existing session and to the metric
	// and initialises the topic based on the name of the provided metric.

	MetricUpdater( LASolver::MetricValue * MetricPointer,
								 cms::Session * TheSession )
	: TheMetric( MetricPointer )
	{
		Destination    = TheSession->createTopic( TheMetric->Name );
		MessageMonitor = new activemq::cmsutil::CachedConsumer( TheSession->createConsumer( Destination ) );
		MessageMonitor->setMessageListener( this );
		MessageMonitor->start();
	}

	~MetricUpdater( void )
	{
		MessageMonitor->stop();
		delete Destination;
		delete MessageMonitor;
	}
};

int main(int argc, char **argv)
{
	// TODO: in order to run this with the finalized metric registry a small dummy implementation of the
	// metric registry must be available. The metric registry itself does not support storage.

  LASolver::MetricRegistry();

	// Setting up the AMQ connection and session

	activemq::library::ActiveMQCPP::initializeLibrary();

	activemq::core::ActiveMQConnectionFactory * AMQFactory = new activemq::core::ActiveMQConnectionFactory();

	AMQFactory->setBrokerURI("tcp://158.39.75.236:61616");

	activemq::core::ActiveMQConnection * AMQConnection = dynamic_cast< activemq::core::ActiveMQConnection *>( AMQFactory->createConnection() );
	delete AMQFactory;

	cms::Session * AMQSession = AMQConnection->createSession( cms::Session::AUTO_ACKNOWLEDGE );

	// Starting the connection

	AMQConnection->start();

	// Create the test metric

	LASolver::MetricValue TotalCores( "TotalCores", 0.0 );

	std::cout << "Initial metric value: " << TotalCores() << std::endl;
	// Create the updater and bind it to the metric and the session

	MetricUpdater UpdateMetric( &TotalCores, AMQSession );

	// Reporting the metric value every 60 seconds

	unsigned int Minutes = 100;

	while ( --Minutes > 0 )
  {
		time_t Now = std::chrono::system_clock::to_time_t(
																					 std::chrono::system_clock::now() );
		std::string NowText = ctime( &Now );

		NowText.erase( NowText.find('\n') ); // Remove the line feed

		std::cout << "[ " << NowText << " ] Total Cores = " << TotalCores() << std::endl;
		std::this_thread::sleep_for( std::chrono::seconds( 60 ) );
	}

  std::cout << "Out of ticks to check the total cores - terminating";

	AMQConnection->close();
	activemq::library::ActiveMQCPP::shutdownLibrary();

	delete AMQSession;
	delete AMQConnection;

	return EXIT_SUCCESS;
}

/* JSON related stuff
 *
 // This is now a simple JSON parser test

 std::string TheValueMessage( "{\"metricValue\": \"11.0\",\"vmName\": \"TotalCores\",\"cloudName\": \"Amazon1\",\"componentName\": \"ScalarmModel.ScalarmDeployment.StorageManager\",\"level\": \"2\",\"timestamp\": \"1540388253073\"}" );

 // The metric is then constructed with initial value 0.0

 LASolver::MetricValue Storage( "TheStorage", 0.0 );

 */
