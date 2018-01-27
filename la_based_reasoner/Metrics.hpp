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

#include "Variables.hpp"    // The definition of the variables

namespace LASolver 
{
/*==============================================================================

 Metric registry

==============================================================================*/
//
// The metric registry is nothing but an instance of the variable registry, 
// and it is also held by a global smart pointer because it is not known 
// when an object would be instantiated by the compiler. It could be before the
// first metric definition, but the consequences can be grave if the metric 
// registry does not exists when the first metric is created.

extern std::shared_ptr< Configuration::Registry > Metrics;

// There is a global function to initialise this pointer and create the 
// registry object. It is done this way to allow derived classes to define 
// a different creation function for a derived metrics registry class.

extern void CreateMetricsRegistry( void );

/*==============================================================================

 The Metric

==============================================================================*/
//
// A metric is a configuration variable because it has no domain, and it simply
// registers with the global metric registry. If the registry has not been 
// instantiated, it will be created.

template< class ValueType >
class Metric : public Configuration::Variable< ValueType >
{
public:
	
	Metric( const std::string TheTopicName, ValueType InitialValue )
	: Configuration::Variable< ValueType >( TheTopicName, InitialValue )
	{
		if ( ! Metrics ) CreateMetricsRegistry();
		
		Metrics->NewVariable( this );
	}
	
	// The default constructor is deleted
	
	Metric( void ) = delete;
	
	// The destructor is virtual because the variable is polymorphic 
	
	virtual ~Metric()
	{	Metrics->RemoveVariable( this ); }
};


}

#endif // LA_SOLVER_METRICS
