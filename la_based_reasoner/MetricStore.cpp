/*=============================================================================
  Metric store

  This file implements the functions of the Metric Store. Please see the header
  file for details.

  In the MELODIC platform a metric is a JSON structure containg the actual
	value as the 'metricValue' field that represents a number, typically a
	real number. Since the metric value is encoded serialised on the link as a
	JSON object, the metric supports parsing this JSON string. However, this means
	that the JsonCpp library must be included when linking:

	-ljsoncpp

  Copyright (c) 2018-2019 Geir Horn, University of Oslo
  Geir.Horn@mn.uio.no

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <unordered_map>                          // Topic to metric mapping
#include <sstream>                                // To format error messages
#include <stdexcept>                              // Standard exceptions
#include <memory>                                 // Smart and unique pointers
#include <mutex>                                  // Thread safe operation

// JSON library for parsing the MELODIC metric serial format

#include <json/json.h>

// Theron++ actor framework headers

#include "Communication/AMQ/AMQSessionLayer.hpp"

// LA Solver specific headers

#include "Metrics.hpp"
#include "MetricStore.hpp"

/*==============================================================================

 Metric registration

==============================================================================*/
//
// When a new metric is created, it will register itself by calling the new
// variable function on the metric registry. If the metric topic already exists
// it cannot be bound to a different metric instance. If this is the case a
// logical error is thrown. If the metric is a new registration, then the
// topic defined by the metric name is subscribed to with a message to the
// AMQ session layer.

void LASolver::MetricStore::NewVariable(
	Configuration::ValueElement * TheMetric )
{
	auto Result = RegisteredMetrics.emplace( TheMetric->Name, TheMetric );

	if ( Result.second == true ) // new registration successful
		Send( Theron::ActiveMQ::SessionLayer::CreateSubscription(
							TheMetric->Name, Theron::ActiveMQ::Message::Destination::Topic ),
					Theron::ActiveMQ::Network::GetAddress(
							Theron::ActiveMQ::Network::Layer::Session ) );
  else if ( Result.first->second != TheMetric )
	{
		std::ostringstream ErrorMessage;

		ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
		             << "Two different metric objects try to register for the same"
								 << " metric name " << TheMetric->Name << " but the metric"
								 << " names must be unique";

	  throw std::logic_error( ErrorMessage.str() );
	}
}

// If a metric with an active subscription is removed, a message to cancel the
// subscription will be sent to the session layer and the metric record will be
// deleted from the registry map.

void LASolver::MetricStore::RemoveVariable(
	Configuration::ValueElement * TheMetric)
{
	auto Subscription = RegisteredMetrics.find( TheMetric->Name );

	if ( Subscription != RegisteredMetrics.end() )
  {
		Send(
			Theron::ActiveMQ::SessionLayer::CancelSubscription( TheMetric->Name ),
		  Theron::ActiveMQ::Network::GetAddress(
			  Theron::ActiveMQ::Network::Layer::Session ) );

		RegisteredMetrics.erase( Subscription );
	}
}

/*==============================================================================

 Solver management

==============================================================================*/
//
// There can be many sub-solvers running and they may not be able to deal with
// an objective function or constraint functions changing as a function of the
// metric values. Therefore they have the opportunity to freeze further metric
// value updates while searching for solutions, and if there are multiple
// solvers updates are blocked until the last of the solvers requesting the
// updates to be frozen signs out (cancel the freeze request). Thus, the
// handler for the sign in message simply increases the count of solvers
// working and confirms back to the solver that it can start working.

void LASolver::MetricStore::SolverSignIn(
	const LASolver::MetricStore::FreezeUpdates & Request,
	const Theron::Address SendingSolver)
{
	RunningSolvers++;

	Send( AcknowledgeFreeze(), SendingSolver );
}

// The solver release acts in the reverse direction by decrementing the number
// of active solvers. When this number reaches zero, it will update the metric
// values with the last metric value found in the metric cache.

void LASolver::MetricStore::SolverRelease(
	const LASolver::MetricStore::ReleaseFreeze & Request,
	const Theron::Address SendingSolver )
{
	if ( ( RunningSolvers > 0 ) && ( --RunningSolvers == 0 ) )
	{
		for ( const auto & ValuePair : ValueCache )
	  {
			auto TheMetric = RegisteredMetrics.find( ValuePair.first );

			if ( TheMetric != RegisteredMetrics.end() )
			 TheMetric->second->Value( ValuePair.second->GetValue() );
	  }

		ValueCache.clear();
	}
}

/*==============================================================================

 Metric values

==============================================================================*/
//
// Arriving values for the subscribed topics will be cached if there are running
// solvers, otherwise just forwarded to the corresponding metric object.

void LASolver::MetricStore::UpdateMetricValue(
	const LASolver::MetricStore::MetricMessage & MetricValueUpdate,
	const Theron::Address TopicOrQueue )
{
	// First the name of the topic is recorded. If this message arrives from a
	// MELODIC sensor, the sender address will encode the topic and not the
	// message. Hence the topic should be taken from the address. However, if
	// the message originates from a metric producing actor on this endpoint,
	// the metric message will contain a metric name.

	std::string TopicID;

	if ( MetricValueUpdate.MetricName().empty() )
		TopicID = TopicOrQueue.AsString();
	else
		TopicID = MetricValueUpdate.MetricName();

	// The name is then looked up in the database of registered metrics, and
	// only if it is known will it be stored. If there are no active solvers
	// having blocked the metric updates, the value will immediately be stored,
	// and if there are blocking solvers it will be cached. Since a map cannot
	// change the value field, any previous records for this topic must be
	// deleted before the new record for this new value can be stored.

	auto MetricRecord = RegisteredMetrics.find( TopicID );

	if ( MetricRecord != RegisteredMetrics.end() )
  {
		if ( RunningSolvers == 0 )
				MetricRecord->second->Value(
															MetricValueUpdate.ValuePointer()->GetValue() );
		else
		{
			ValueCache.erase( TopicID );
			ValueCache.emplace( TopicID, MetricValueUpdate.ValuePointer() );
		}
	}
}

// The message function to de-serialize the incoming string must decode this as
// a JSON record as metric values are passed as JSON records in MELODIC. For
// some incomprehensible reason the reader parser interface taking a string has
// been depreciated and now there is a "builder" that creates the parser object
// which later has to be deleted. Who let these Java programmers into the
// room?!?
//
// The JSON builder seems to be an object that can be stored for the life time
// of the application, and the dynamically allocated Reader object can probably
// also remain through the duration of the application, but it should be
// deleted when the programme terminates. For this reason it is encapsulated
// in a unique pointer class. The reader is not thread safe (explicitly stated
// in the JsonCpp documentation), and so access to this is protected by a
// mutex. Hopefully this is more efficient than recreating the reader on every
// message decoding.

bool LASolver::MetricStore::MetricMessage::Deserialize(
	const Theron::SerialMessage::Payload & TheMessage )
{
	static Json::CharReaderBuilder Builder;
	static std::unique_ptr< Json::CharReader > const
				 Reader( Builder.newCharReader() );

  // Mutex and lock to protect the reader access

  static std::mutex ReaderLock;
	std::lock_guard< std::mutex > ReaderAccess( ReaderLock );

	// The parsed message is stored in a JSON key-value map, and potential parsing
	// errors are reported in a standard string

	Json::Value  Message;
	std::string  ParsingErrors;

	// Here comes the real nasty interface as the reader requires the pointer
	// to the start and the end of a C-string....

	if ( Reader->parse( TheMessage.c_str(),
											TheMessage.c_str() + TheMessage.size(),
											&Message, &ParsingErrors )  )
	{
		// The parsing was OK, and now the metric value can be set up on the
		// basis of the string that is the argument of the 'metricValue' field
		// of the JSON record. This string is only turned into a numeric value
		// by the Value function of the metric object receiving the numeric
		// string. The metric name string will not exist if the metric arrives
		// from a remote MELODIC sensor where the metric name is encoded in the
		// sender. This is why the metric handler takes the name of the topic
		// from the sender ID if it is empty. When a metric is sent as a metric
		// message the value will be encoded.

		TheValue = std::make_shared< MetricValue< std::string > >(
								 Message["metricName"].asString(),
								 Message["metricValue"].asString() );

		return true;
	}
	else
	{
		std::ostringstream ErrorMessage;

		ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
		             << "Failed to parse the JSON string \" "
								 << TheMessage << " \" with the following error: "
								 << ParsingErrors;

	  throw std::invalid_argument( ErrorMessage.str() );
	}
}

// Serializing the metric message is much easier. It just creates the JSON
// message and sets the fields from the value pointer. It will throw an error
// if the value pointer does not exist indicating incorrect initialization of
// the message.

Theron::SerialMessage::Payload
LASolver::MetricStore::MetricMessage::Serialize() const
{
	if ( TheValue )
  {
		Json::Value SerialMessage;
		SerialMessage["metricName"]  = TheValue->Name;
		SerialMessage["metricValue"] = TheValue->GetValueString();
		return SerialMessage.toStyledString();
	}
	else
  {
		std::ostringstream ErrorMessage;

		ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
	               << "Cannot serialize a default constructed Metric Message";

	  throw std::logic_error( ErrorMessage.str() );
	}
}

/*==============================================================================

 Constructor

==============================================================================*/

LASolver::MetricStore::MetricStore( const std::string & Name )
: Actor( Name ), StandardFallbackHandler( GetAddress().AsString() ),
  DeserializingActor( GetAddress().AsString() ), MetricRegistry(),
  RunningSolvers(0), ValueCache()
{
	RegisterHandler( this, &MetricStore::SolverSignIn      );
	RegisterHandler( this, &MetricStore::SolverRelease     );
	RegisterHandler( this, &MetricStore::UpdateMetricValue );
}
