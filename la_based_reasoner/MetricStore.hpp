/*=============================================================================
  Metric store

  The metric store is an actor implementing the registry for the metric values.
  A metric is subscribed to by the AMQ interface, and its value can change
  asynchronously. When the value changes, a message is received by this actor.

  In line with the Theron++ principle of 'transparent communication' the metric
  values can be generated from some metric source that could be local to the
  actor system on this node or that could be remote. This 'metric value' is
  therefore a message that can be serialized and de-serialised.

  One or more solver actors may be running for the continuous variables of the
  problem. These may require stable conditions, and thereby there is a protocol
  indicating that the metric values should be frozen while the solver searches
  for a solution. Once the last solver indicates that it has finished working,
  then the metric values will be updated by the last cached values.

  If no continuous solver is active when a metric update message arrives, then
  the metric value will be immediately updated.

  Copyright (c) 2018-2019 Geir Horn, University of Oslo
  Geir.Horn@mn.uio.no

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef LA_SOLVER_METRIC_STORE
#define LA_SOLVER_METRIC_STORE

#include <string>                                 // Standard strings
#include <unordered_map>                          // For caching variable values
#include <memory>                                 // Smart pointers

// Theron++ Actor Framework

#include "Actor.hpp"                              // Theron++ actors
#include "Communication/SerialMessage.hpp"        // Messages to de-serialize
#include "Utility/StandardFallbackHandler.hpp"    // Catch unhanded messages
#include "Communication/DeserializingActor.hpp"   // To de-serialize messages
#include "Communication/AMQ/AMQEndpoint.hpp"      // The network endpoint
#include "Communication/AMQ/AMQSessionLayer.hpp"  // The session layer

#include "Variables.hpp"                          // The variables
#include "Metrics.hpp"                            // Metric definitions

namespace LASolver
{

class MetricStore
: virtual public Theron::Actor,
  virtual public Theron::StandardFallbackHandler,
  virtual public Theron::DeserializingActor,
  public MetricRegistry
{
	/*============================================================================

   Storing metric references

  ============================================================================*/
  //
  // The references to the metric objects are stored in an unordered map based
  // on the name of the metric.

private:

	std::unordered_map< std::string, Configuration::ValueElement * >
	RegisteredMetrics;

	// There is a simple function to check if the registry is empty with no
	// registered metric values.

protected:

	virtual bool empty( void ) override
	{ return RegisteredMetrics.empty(); }

	// In the same way the number of stored metric values can be obtained from
	// the virtual function

	virtual ValueVector::size_type NumberOfVariables( void ) override
	{ return RegisteredMetrics.size(); }

	// The two mandatory functions to register and remove metric references are
	// basically inserting or erasing them from this structure. It also entails
	// that this metric store actor subscribes to the AMQ topic for the metric.

	virtual
	void NewVariable( Configuration::ValueElement * TheMetric ) override;

	// The removal of the subscription is basically just cancelling the
	// subscription with the AMQ interface, and removes the registration of the
	// metric.

	virtual
	void RemoveVariable( Configuration::ValueElement * TheMetric ) override;

	/*============================================================================

   Freezing metric updates

  ============================================================================*/
  //
	// When a solver requests stable conditions it freezes the metric values
	// and since more solvers may request the freeze, then there is a counter
	// indicating the number of running solvers. Updates of metric values will
	// only happen when there is no running solvers.

private:

	unsigned int RunningSolvers;

	//----------------------------------------------------------------------------
  // Message: Freeze Updates
  //----------------------------------------------------------------------------
  //
  // The Freeze Update message is sent when a continuous solver starts solving
	// to keep the metric values stable until a solution has been found. The
	// freeze is acknowledged by the message handler, and the solver must not
	// start before it has received the acknowledgement.

public:

	class FreezeUpdates	{};
	class AcknowledgeFreeze {};

private:

	void SolverSignIn( const FreezeUpdates & Request,
										 const Theron::Address SendingSolver );

	//----------------------------------------------------------------------------
  // Message: Release Freeze
  //----------------------------------------------------------------------------
  //
  // When the solver has completed its search for a solution it will send a
  // message to release the hold of the metric updates. If this is the last
  // solver holding a freeze the metric values will immediately be updated.
  //
  // There is one minor problem that some metric might have been removed during
  // the freeze, and therefore its cached value may not be updated. In this
  // case, the cached value is simply ignored.

public:

  class ReleaseFreeze {};

private:

	void SolverRelease( const ReleaseFreeze & Request,
											const Theron::Address SendingSolver );

  /*============================================================================

  Metric values

  ============================================================================*/
  //
  // A metric value may in principle be anything value type that is useful in
  // the utility function or the constraints, although normally it is taken to
  // be a real number (double) or an integral number of some accuracy. Hence,
  // sending the metric value around is best done as a binary value, but it
  // must support conversion to the serial format required.
  //
  // It turns out that the Variable Value has the required functionality, but
  // it cannot be passed around since it is a template on the type of the
  // value carried. However, a smart pointer to the value element base class
  // can be passed around providing the polymorphic functionality needed.

public:

  using MetricValuePointer = std::shared_ptr< Configuration::ValueElement >;

	// The Metric message can now be defined as a serial message holding this
	// metric value

  class MetricMessage : public Theron::SerialMessage
  {
	private:

		 MetricValuePointer TheValue;

	public:

		inline MetricValuePointer ValuePointer( void ) const
		{ return TheValue; }

		inline std::string MetricName( void ) const
		{
			if ( TheValue )
				return TheValue->Name;
			else
				return std::string();
		}

		// Serializing the value simply means embedding the value in the JSON
		// metric format used by MELODIC.

		virtual Payload Serialize( void ) const override;

		// Inbound messages are coming as a JSON string and the de-serialising part
		// is just to decode the JSON format of the message and pick out the metric
		// value, and store this as a string that will be converted back to a value
		// by the receiving metric's Value function with the aid of the Convert
		// function of the value element.

	protected:

		virtual bool Deserialize( const Payload & TheMessage ) override;

		// The message must be default constructable since automatic
		// de-serialisation implies to first construct the message type and then
		// try to de-serialize the message.

	public:

		MetricMessage( void ) : TheValue()
		{}

		// The value message must be copyable

		MetricMessage( const MetricMessage & Other )
		: TheValue ( Other.TheValue )
		{}

		// The value based constructor takes the name of the metric and accepts
		// any type of value as it will create the right variable value element

		template< typename ValueType >
		MetricMessage( const std::string & MetricName, const ValueType GivenValue )
		: TheValue( std::make_shared< Configuration::VariableValue< ValueType > >(
				MetricName, GivenValue ) )
		{}

		// The destructor is virtual although it does not need to do any cleaning
		// for this message class

		virtual ~MetricMessage()
		{}
	};

	// if metric value updates arrives while there are running solvers blocking
	// the immediate update, their values must be cached until updates are again
	// allowed. This is a general map with the metric name as the key. The metric
	// values are stored as the serialized payload to be de-serialized by the
	// metric to its right value.

private:

	std::unordered_map< std::string, MetricValuePointer > ValueCache;

  // When a new value arrives on any of the subscribed topics, it will be sent
  // to the session layer that will forward the metric to all of the subscribed
  // actors. In the LA Solver situation there should only be one subscribing
  // actor, namely this metric store. The update handler will check if updates
  // are frozen by the solvers and then cache the received value, otherwise
  // the associated metric will be updated immediately.

  void UpdateMetricValue( const MetricMessage & MetricValueUpdate,
													const Theron::Address TopicOrQueue );

 	/*============================================================================

  Constructor and destructor

  ============================================================================*/
	//
	// The constructor takes the name of this metric store actor and starts
	// waiting for metrics to be defined and the solver to start solving.

public:

	MetricStore( const std::string & Name );

	virtual ~MetricStore( void )
	{}
};

}      // End name space LA Solver
#endif // LA_SOLVER_METRIC_STORE
