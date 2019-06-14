/*=============================================================================
  Solver
  
  The Solver actor receives an assignment of values to the discrete variables
  and solves for optimal values for the continuous variables using the BOBYQA 
  algorithm [1]. The discrete variable values are assigned by the LA Assigner
  actor, and during the solver iterations it interacts with the Application 
  agent to evaluate the constraints and the application utilities for the 
  combined set of assignments of discrete and continuous variables.
   
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef LASOLVER_SOLVER_ACTOR
#define LASOLVER_SOLVER_ACTOR

#include "Actor.hpp"      // The Actor framework
#include "Variables.hpp"  // The variable objects

namespace LASolver 
{
	
class Solver : virtual public Theron::Actor
{
	
public:
	
	// ---------------------------------------------------------------------------
  // Messages
  // ---------------------------------------------------------------------------
  //
	// The Solver gets a discrete variable value assignment message from the LA 
	// assigner to start the solution process. It also uses the same message to 
	// request the upper and lower limits for the continuous variables. This 
	// message is simply a discrete variable vector.
	
	using DiscreteVariableValues = 
				Configuration::Variables< 
					Configuration::VariableType::Discrete >::ValueVector;
	
	

	
};      // End class Solver
}       // End name space LA Solver
#endif  // LASOLVER_SOLVER_ACTOR
