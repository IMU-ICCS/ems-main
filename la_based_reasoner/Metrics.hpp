/*==============================================================================
Metrics

A metric is fundamentally the same as a variable whose value is set
automatically by some measurement value. The metrics will be kept in a registry
separate from the variables since the solver is only reading their values.

Author and Copyright: Geir Horn, University of Oslo 2018-2019

License:
This Source Code Form is subject to the terms of the Mozilla Public License,
v. 2.0. If a copy of the MPL was not distributed with this file, You can
obtain one at http://mozilla.org/MPL/2.0/.
==============================================================================*/

#ifndef LA_SOLVER_METRICS
#define LA_SOLVER_METRICS

#include <list>             // A list to hold the metric references
#include <memory>           // Smart pointer to the metric registry
#include <string>           // Storing the name of the metric
#include <sstream>          // For error messages
#include <stdexcept>        // For standard exceptions
#include <limits>           // For numeric limits of types

#include "Variables.hpp"    // The definition of the variables

namespace LASolver
{
/*==============================================================================

 Metric registry

==============================================================================*/
//
// The metric registry is nothing but an instance of the variable registry,
// and it simply allows access to the protected virtual interface to ensure
// that only classes that is a Metric, i.e. inheriting the Metric class, are
// allowed to register.

class MetricRegistry : public virtual Configuration::VariableRegistry
{
private:

	// The idea is that the metric registry is to be inherited by some other
	// class that is able to receive metric updates on the data plane
	// communication protocol used for a given application of the LA Solver.
	// The metric registry is therefore stored as a pointer to this base class,
	// and this will be used to access the methods to store the metrics.

	static MetricRegistry * Metrics;

public:

	// Metrics are registered and removed from this registry by the two static
	// functions with the same name as for the variables. The registration
	// function will throw a logic error if the metric is registered before
	// the metric registry has been created.

	static bool Register( Configuration::ValueElement * TheMetric )
	{
		if ( Metrics != nullptr )
		{
			Metrics->NewVariable( TheMetric );
			return true;
		}
		else
		{
			std::ostringstream ErrorMessage;

			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "Metric " << TheMetric->Name << " was created before "
									 << "the metric registry was created.";

		  throw std::logic_error( ErrorMessage.str() );
		}
	}

	// Removal will just forget about the metric if the registry pointer is null.

	static void Remove( Configuration::ValueElement * TheMetric )
	{
		if ( Metrics != nullptr )
			Metrics->RemoveVariable( TheMetric );
	}

protected:

	// Derived classes may update the metric pointer if they want to make
	// additional functionality available through this pointer. Similar to
	// the update functions for the variable registries, this will verify that
	// the derived class is really a registry and if it is a derived class of
	// an instance of the currently registered registry it is allowed to
	// overwrite the metric value.

	template< class DerivedClass >
	void UpdateRegistryPointer( DerivedClass * DerivedRegistry )
	{
		static_assert( std::is_base_of< MetricRegistry, DerivedClass >::value,
		  "Cannot update the metric registry pointer with the given class" );

		MetricRegistry * BaseOfDerived =
			dynamic_cast< MetricRegistry * >( DerivedRegistry );

		if ( (Metrics == nullptr) || (Metrics == BaseOfDerived) )
			Metrics = DerivedRegistry;
		else
		{
			std::ostringstream ErrorMessage;

			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "The metrics registry has already been "
									 << "defined and only one metrics registry "
									 << "can exist for the solver";

		  throw std::logic_error( ErrorMessage.str() );
		}
	}

	// Even though the variables can be all set in one go, it does not make sense
	// to set all the metric values as once as they are supposed to be event
	// indicators or sensor readings representing the current execution context
	// and never values that will be actively set by any LA Solver component.
	// However, the variable registry requires a function to set the values of
	// the variables, and this will here be defined to throw a logic error if
	// used.

	virtual	void SetValues( ValueVector & Values ) override
	{
		std::ostringstream ErrorMessage;

		ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
		             << "The function to set a vector of variable values has been "
								 << "called on a metric registry. Metrics receive their values "
								 << "one by one.";

	  throw std::logic_error( ErrorMessage.str() );
	}

	// The constructor simply initializes the static metric pointer to this
	// class class providing the default behaviour to set and remove metrics

	inline MetricRegistry( void )
	{ UpdateRegistryPointer( this ); }

	// The copy constructor is deleted

	MetricRegistry( const MetricRegistry & Other )  = delete;

public:

	// The virtual destructor resets the metric registry pointer to ensure
	// that further access will create segmentation faults.

	virtual ~MetricRegistry()
	{ Metrics = nullptr; }
};

/*==============================================================================

 The Metric

==============================================================================*/
//
// A metric value is a configuration variable because it has no domain, and
// the bounds are set to the maximal bounds of the value type given.

template< class ValueType >
class MetricValue : public Configuration::Variable< ValueType >
{
protected:

	// Defining the upper and lower bound of a metric makes no sense since the
	// metric has no bounds. However, the bounds can be defined to correspond
	// with the ranges for the metric value type, i.e. the range from the smallest
	// number that can be stored in the metric to the largest number the type
	// can hold.

	virtual std::any GetUpperBound( void ) override
	{ return std::numeric_limits< ValueType >::max(); }

	virtual std::any GetLowerBound( void ) override
	{ return std::numeric_limits< ValueType >::min(); }


public:

	// The value type is defined to be accessible at compile time

	using value_type = ValueType;

	// The constructor takes the name of the topic to subscribe to

	inline MetricValue( const std::string TheMetricName, ValueType InitialValue )
	: Configuration::Variable< ValueType >( TheMetricName, InitialValue )
	{	}

	// The default constructor and copy constructor is deleted

	MetricValue( void ) = delete;
	MetricValue( const MetricValue & Other ) = delete;

	// The destructor is virtual because the metric variable is polymorphic
	// It deletes the parser and removes the metric instance from the metric
	// registry.

	virtual ~MetricValue()
	{}
};

// The Metric is a metric value that register with the metric registry.
// The reason for this split in two parts is that the metric value can be
// used by sensors to set new values to a metric, but these temporary value
// objects should not be registered as proper metrics.

template< class ValueType >
class Metric : public MetricValue< ValueType >
{
public:

	// The value type is defined to be accessible at compile time

	using value_type = ValueType;

	// The constructor takes the name of the topic to subscribe to

	inline Metric( const std::string TheMetricName, ValueType InitialValue )
	: MetricValue< ValueType >( TheMetricName, InitialValue )
	{	MetricRegistry::Register( this );	}

	// The default constructor and copy constructor is deleted

	Metric( void ) = delete;
	Metric( const Metric & Other ) = delete;

	// The destructor is virtual because the metric variable is polymorphic
	// It deletes the parser and removes the metric instance from the metric
	// registry.

	virtual ~Metric()
	{	MetricRegistry::Remove( this ); }
};


}      // Name space LA Solver
#endif // LA_SOLVER_METRICS
