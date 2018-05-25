/*==============================================================================
Interface

This file is simply a place holder for the static variables of the LA solver 
interface.

Author and Copyright: Geir Horn, 2018
License: LGPL 3.0
==============================================================================*/

#include "Domains.hpp"
#include "Variables.hpp"
#include "Metrics.hpp"
#include "Constraints.hpp"

// The static variables used to register the variable definitions

std::shared_ptr< LASolver::Configuration::VariableRegistry > 
	LASolver::Configuration::VariableRegistry::Discrete,
	LASolver::Configuration::VariableRegistry::Continuous;
	
// A similar variable is used to store the metrics
	
std::shared_ptr< LASolver::MetricRegistry > 
	LASolver::MetricRegistry::Metrics;
	
// The static variables to store the functional expressions for the constraints
	
std::list< std::function< double(void) > >
	LASolver::Constraints::Equality,
	LASolver::Constraints::Inequality;
