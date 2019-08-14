/*=============================================================================
  Get Metric Value

  This is a small test programme setting up the metric store, register a
  metric and wait 180 seconds for its value to be updated. The time stamp of
  the update and the new value of the metric is printed for each update.

  This test programme tests:

  a. The full Theron++ transparent communication stack for AMQ
  b. The metric system storing MELODIC JSON serialized metric values
  b. The LA solver's options class to read the command line options

  Copyright (c) 2019 Geir Horn, University of Oslo, Geir.Horn@mn.uio.no

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <chrono>                            // Concept of time
#include <ctime>                             // Human readable time
#include <iostream>                          // To produce output

// Theron++ actor framework headers

#include "Communication/AMQ/AMQEndpoint.hpp" // AMQ communication stack

// The LA Solver headers

#include "Metrics.hpp"                       // The metric definition
#include "MetricStore.hpp"                   // The metric store actor
#include "CommandOptions.hpp"                // Command line options

/*==============================================================================

  Metric printing

==============================================================================*/
//
// This test programme will only report the value of the metric to the console
// when the metric value is updated. Hence the value update function is extended
// to print the value.
//
// It is defined as a template to be able to test different metric types, both
// integral and real values

template< typename ValueType >
class PrintMetric : public LASolver::Metric< ValueType >
{
public:

	virtual void Value( const std::any & GivenValue ) override
	{
		LASolver::Metric< ValueType >::Value( GivenValue );

		// Then print the time stamp and the new value obtained by the standard
		// value returning operator.

		time_t Now = std::chrono::system_clock::to_time_t(
																					 std::chrono::system_clock::now() );
		std::string NowText = ctime( &Now );

		NowText.erase( NowText.find('\n') ); // Remove the line feed

		std::cout << "[ " << NowText << " ] "
							<< LASolver::Metric< ValueType >::Name << " = "
		          << this->operator()() << std::endl;
	}

	// The constructor simply takes the metric name and forwards this to the
	// base class.

	PrintMetric( const std::string MetricName, ValueType InitialValue )
	: LASolver::Metric< ValueType >( MetricName, InitialValue )
	{}

	// The destructor is virtual just to ensure that all the base classes are
	// correctly terminated.

	virtual ~PrintMetric()
	{}
};

/*==============================================================================

  Main

==============================================================================*/

int main(int argc, char **argv)
{
	// Parsing the command line options

	LASolver::CommandLineOptions Options( argc, argv );

	// Setting up the AMQ network endpoint to receive metric values from the
	// AMQ server (or message broker)


	Theron::ActiveMQ::NetworkEndpoint AMQNode(
		Options.EndpointName(), Options.AMQServerIP(), Options.AMQServerPort(),
		Options.SessionServerName(), Options.PresentationServerName(),
		Options.Prefix() );

	// Starting the LA Solver metric store that receives the incoming values
	// and assigns them to the various registered metrics.

	LASolver::MetricStore MetricManager( "MetricManager");

	// Defining the metric

	PrintMetric< unsigned int > TotalCores( "TotalCores", 0 );

	// Receive the metric for a certain amount of time, and print the time
	// stamp and the metric value when new metric values are received. Since
	// the Theron++ actor system is multi threaded this simply implies that this
	// thread must be suspended for a certain amount of time.

  std::this_thread::sleep_for( std::chrono::seconds( 600 ) );

	// Then send a shut down message to the AMQ network layer

	Theron::Actor::Send( Theron::Network::ShutDown(),
											 Theron::Address::Null(), AMQNode.GetAddress() );

	// Finally, wait for the actors to finish the shut down protocol.

	Theron::Actor::WaitForGlobalTermination();

	return EXIT_SUCCESS;
}
