/*=============================================================================
  MAIN
 
  The main file will synchronise the Learning Automata (LA) based solver. The 
  LA is intrinsic discrete, so if there are continuous variables, these 
  will be solved by a non-linear solver holding the discrete variables and 
  the metrics fixed while this sub-problem is solved.
  
  The variables and the utility function of the problem are defined in the 
  file "variables.model" and the constraints are defined in the separate file
  "constraints.model". Both files are included into the "Interface.cpp" file
  and compiled to executable code.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

// Standard library includes

#include <iostream>

// The project related includes - in the order of appearance

#include "Interface.hpp"
#include "Theron/Theron.h"
#include "CommandParser.hpp"
#include "EvaluationActor.hpp"
#include "ContinuousOptimiser.hpp"
#include "PrintResults.hpp"

// The main file has three rather separate parts:
// 1) Initialisation of all main objects involved in the concurrent 
//    optimisation.
// 2) Starting the optimisation 
// 3) Reporting the results of the optimisation

int main(int argc, char **argv) 
{
  //----------------------------------------------------------------------------
  // Initialisation
  //----------------------------------------------------------------------------

  // First the command line arguments are verified, and or default values are
  // loaded if they have any
  
  Solver::CommandParser Parameters( argc, argv );
  
  // Then the Theron execution infrastructure is established. The framework
  // is to be replaced by an LA environment derivative.
  
  Theron::EndPoint  TheComputer("Localhost","127.0.0.1");
  Theron::Framework TheFramework( TheComputer, "Solver",
    Theron::Framework::Parameters( Parameters.NumberOfThreads() )
  );
  
  // The evaluation of the objective function and the constraints are protected
  // by an evaluation actor. 
  
  Solver::EvaluationActor TheProblem( TheFramework );
  
  // Results will be printed by a dedicated print actor. It takes the output 
  // destination as argument, a flag whether to return the outcome of the 
  // print operation, and potentially a sting for an external command to be 
  // executed after the printing if the results should be picked up by other 
  // applications. The outcome flag is here set so that we can wait for the 
  // message that the printing is done before we terminate main.
  
  Solver::PrintResults Output( TheFramework, std::cout, true );
  
  // This test will use only one solver
  
  Solver::ContinuousOptimiser Optimiser( TheFramework );
  
  // The results will be sent back from the optimiser to a caller address and 
  // here it will be returned to a receiver since main is not an actor. The 
  // actual message will be caught by a catcher object whose enqueue function 
  // is registered as the callback function.
  
  Theron::Receiver Solution;
  Theron::Catcher< std::vector<Solver::EvaluationActor::VariableValue> > Result;
  
  Solution.RegisterHandler( &Result, 
    &Theron::Catcher< 
	  std::vector< Solver::EvaluationActor::VariableValue > >::Push );

  // There is another catcher and handler for the results of the printing of
  // the results.
  
  Theron::Catcher< Solver::PrintResults::Status > PrintOutcome;
  
  Solution.RegisterHandler( &PrintOutcome, 
    &Theron::Catcher< Solver::PrintResults::Status >::Push
  );

  // Setting up the vector of initial values with the continuous variables 
  // in the first positions and the later variables are the discrete variables.
  
  std::vector< Solver::EvaluationActor::VariableValue > ContinuousVariables, 
							DiscreteVariables;
  
  for ( const auto & aVariable : Solver::Variables )
    if ( aVariable.second->GetCategory() 
				     == Solver::Domain::Categories::Continuous )
      ContinuousVariables.push_back( 
	  Solver::EvaluationActor::VariableValue(
	      aVariable.second, aVariable.second->GetValue())
      );
    else
      DiscreteVariables.push_back(
	  Solver::EvaluationActor::VariableValue(
	    aVariable.second, aVariable.second->GetValue())
      );
  
  //----------------------------------------------------------------------------
  // Starting optimisation
  //----------------------------------------------------------------------------
  // As of here it is possible to assign value to the discrete variables and 
  // then solve for the continuous variables. 
  
  // In the present version only the initially given discrete values will be 
  // used, so optimisation can be started right away. The starting point is 
  // the unified vector of initial values for the continuous variables and 
  // assigned discrete values.
    
  std::vector< Solver::EvaluationActor::VariableValue > 
	FeasibleSolution( ContinuousVariables );
  
  FeasibleSolution.insert( FeasibleSolution.end(), 
			   DiscreteVariables.begin(), DiscreteVariables.end() );
  
  // This is sent to the optimiser with the receiver as the sender address, 
  // and then it is just to wait for the solution to appear.
  
  TheFramework.Send( FeasibleSolution, 
		     Solution.GetAddress(), Optimiser.GetAddress()  );
  
  Solution.Wait();

  //----------------------------------------------------------------------------
  // Receiving and printing the results
  //----------------------------------------------------------------------------
  // In this version the solution will be put back into the feasible solution 
  // vector and then printed to the console before the application happily 
  // terminates.
 
  Theron::Address From; // dummy
  
  Result.Pop( FeasibleSolution, From );

  TheFramework.Send( FeasibleSolution, Solution.GetAddress(), 
		     Theron::Address("PrintResults") 		);

  Solution.Wait(); // Printing is done, but we do not care about the outcome.
  
  return 0;
}
