/*=============================================================================
  Registries

  The variables and metrics and constraint functions are stored in global
  registries, and this source file holds these static variables.

  Copyright (c) 2018 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <memory>
#include <list>
#include <functional>

#include "Variables.hpp"
#include "Metrics.hpp"
#include "Constraints.hpp"

namespace LASolver
{
	// First the registries for the continuous and the discrete variables
	// are defined as part of the configuration

	namespace Configuration
	{
		Variables< VariableType::Discrete > *
			Variables< VariableType::Discrete >::Registry   = nullptr;
		Variables< VariableType::Continuous > *
			Variables< VariableType::Continuous >::Registry	= nullptr;
	}

	// The constraint functions are stored as either equality constraints
	// or as inequality constraints.

  std::list< std::function< double(void) > >
	  Constraints::EqFunctions,
		Constraints::InEqFunctions;

	// The metric registry keeps the values of constants that are measured
	// and automatically updated.

	MetricRegistry *	MetricRegistry::Metrics = nullptr;
}
