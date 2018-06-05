/*==============================================================================
Metrics

A metric is fundamentally the same as a variable whose value is set 
automatically by some measurement value. The metrics will be kept in a registry 
separate from the variables since the solver is only reading their values.

Author and Copyright: Geir Horn, 2018
License: LGPL 3.0
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
protected:

  // Metric variables are registered using the same functions as defined for 
	// the variable registry, and they will eventually be defined by the class
	// implementing the metric handling. 
	
	using Configuration::VariableRegistry::NewVariable;
	using Configuration::VariableRegistry::RemoveVariable;
	
	template< class ValueType >
	friend class Metric;

private:
	
	// The idea is that the metric registry is to be inherited by some other 
	// class that is able to receive metric updates on the data plane 
	// communication protocol used for a given application of the LA Solver. 
	// The metric registry is therefore stored as a pointer to this base class,
	// and then it is assumed that the derived class is assigned using the 
	// public creator function. This behaviour is identical to the one used for 
	// the variable registry.
	
	static std::shared_ptr< MetricRegistry > Metrics;
		
public:
	
	// The function to create the metric registry takes the registry type as 
	// a template parameter, and the arguments for the registry constructor as 
	// parameters. It checks that the given class is really derived from this 
	// registry at compile time. 
	
	template< class RegistryType, class... RegistryArguments >
	static bool Create( RegistryArguments &&... TheArguments  )
	{
		static_assert( std::is_base_of< MetricRegistry, RegistryType >::value,
			"Discrete variable registry must be derived from class Variable Registry"
		);
		
		Metrics = std::make_shared< RegistryType >( 
					    std::forward< RegistryArguments >( TheArguments )... );
		
		if ( Metrics )
			return true;
		else 
			return false;
	}
	
	// Only the virtual destructor must be defined.
	
	virtual ~MetricRegistry()
	{ }
};

/*==============================================================================

 The Metric

==============================================================================*/
//
// A metric is a configuration variable because it has no domain, and it simply
// registers with the global metric registry. If the registry has not been 
// instantiated, a logic error will be thrown.

template< class ValueType >
class Metric : public Configuration::Variable< ValueType >
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
	
	Metric( const std::string TheTopicName, ValueType InitialValue )
	: Configuration::Variable< ValueType >( TheTopicName, InitialValue )
	{
		if ( MetricRegistry::Metrics )
			MetricRegistry::Metrics->NewVariable( this );
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "The metric " << TheTopicName << " is created before "
									 << "the metric registry has been instantiated";
									 
		  throw std::logic_error( ErrorMessage.str() );
		}
	}
	
	// The default constructor is deleted
	
	Metric( void ) = delete;
	
	// The destructor is virtual because the metric variable is polymorphic 
	
	virtual ~Metric()
	{	MetricRegistry::Metrics->RemoveVariable( this ); }
};


}

#endif // LA_SOLVER_METRICS
