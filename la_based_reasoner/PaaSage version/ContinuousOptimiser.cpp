/*=============================================================================
  Continuous optimiser
  
  This optimiser is an interface to the NLopt [1] package, which is a part of
  most standard Linux distributions, to solve for continuous variables taking 
  all discrete variables as fixed during the optimisation.
    
  References:
  
  [1] http://ab-initio.mit.edu/wiki/index.php/NLopt 
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <string>
#include <boost/variant.hpp>

#include "ContinuousOptimiser.hpp"
#include "Interface.hpp"

#ifdef _DEBUG
  #include <iostream>
  #include <iterator>
  #include <algorithm>
#endif

using namespace Solver;

//-----------------------------------------------------------------------------
// Static variables
//-----------------------------------------------------------------------------

std::vector< GenericVariable * > ContinuousOptimiser::ContinuousVariables;

//-----------------------------------------------------------------------------
// Utility functions
//-----------------------------------------------------------------------------

// Printing the variables is scanning over all continuous variables known to 
// the optimiser class and printing their string ID and their current values.
// Note that this function is no longer in use because as an actor the 
// optimiser will return the obtained values as a message to the caller, and
// printing the solution must be done by the caller.

// std::ostream & operator << ( std::ostream & OutputStream, 
// 			     const ContinuousOptimiser & Result)
// {
//     for ( auto TheVariable : Result.Variables )
//       TheVariable->PrintValue( OutputStream );
// 		   
//     return OutputStream;
// }

// Setting the variable bounds will frame the search area to legal values 
// supported by the variable domains, even if some of the bounds for simple 
// ranges will duplicate other constraints already defined.

void ContinuousOptimiser::SetVariableBounds( nlopt::opt & Solver )
{
 std::vector< double > UpperBounds, LowerBounds;
 
 for ( Solver::GenericVariable * Variable : ContinuousVariables )
 {
   LowerBounds.push_back( Variable->LowerBound() );
   UpperBounds.push_back( Variable->UpperBound() );
 }
 
 Solver.set_upper_bounds( UpperBounds );
 Solver.set_lower_bounds( LowerBounds ); 
}

// The evaluation function implements the protocol with the remote evaluation 
// actor, and blocks waiting for the response to be returned. It is called from
// the objective function evaluator or from the constraint function evaluator.

void ContinuousOptimiser::EvaluateVariables(
  const std::vector< double > &	    VariableValues, 
  EvaluationActor::Message::Command WhatToEvaluate   )
{
  // The following assessment should not fail because then there is something
  // very wrong going on.
  
  if ( VariableValues.size() != ContinuousVariables.size() )
  {
    throw std::string("Wrong number of variable values given");
  }
  
  // Then building the message to the remote evaluation actor requesting it 
  // to return the given evaluation command, and evaluating it for the set of 
  // variable values given above for the set of known continuous variables.
  
  EvaluationActor::Message EvaluationCommand;
  
  EvaluationCommand.TheCommand = WhatToEvaluate;
  
  auto TheValue = VariableValues.begin();
  
  for ( GenericVariable * TheVariable : ContinuousVariables )
  {
    EvaluationCommand.Values.push_back( 
      EvaluationActor::VariableValue( TheVariable, *TheValue ) );
    ++TheValue;
  }
  
  // The response to the message should go to the blocking receiver and not 
  // to this actor's message queue since it is not possible to return from 
  // this function call before the value(s) has been obtained and the value(s) 
  // can be returned. The message must therefore be sent with the receiver as
  // the sending address, which means that the send function on the framework 
  // must be used.
  
  GetFramework().Send( EvaluationCommand, RemoteEvaluation.GetAddress(),
		       Theron::Address("EvaluationActor")   );
  
  // Then there is nothing to do but wait for the remote evaluation to complete
  // and the value(s) computed to be returned.
  
  RemoteEvaluation.Wait();
}


//-----------------------------------------------------------------------------
// Initial variable values
//-----------------------------------------------------------------------------
// Since the variables are generic they can be any arithmetic type, however 
// the type must be convertible to a double as double variable values are
// expected by the solver. The initial values resulting from this process will 
// be stored  in a vector to be passed to the solver.
  
// To set these values we would need to use the "visitor" mechanism, and 
// implementing this with a template function should handle all different 
// arithmetic types and a function to throw an error if a string is accidentally
// received

class InitialVariableValues 
: public boost::static_visitor< void >, public std::vector< double >
{
  public:  

    void operator()( std::string Value )
    {
      throw std::string("Only arithmetic types supported!");
    }
    
    template< typename BasicType >
    void operator()( BasicType Value )
    {         
      push_back( static_cast< double >( Value ) );
    };
};

//-----------------------------------------------------------------------------
// Optimiser
//-----------------------------------------------------------------------------
// This is the main function receiving the discrete variables from the discrete
// part of the solver, before it initialises and launches the NLopt solver 
// for the continuous part of the problem. It returns the vector of all 
// variables, with the optimal assignments for the continuous variables first
// and a copy of the discrete variables towards the end. Then follows 
// the real value of the objective function, and the last value is 
// the number of evaluations of the objective function (and the constraints)
// necessary to obtain this solution with the given accuracy (see the 
// constructor below).

void ContinuousOptimiser::Optimiser(
  const std::vector< EvaluationActor::VariableValue > & VariableValues, 
  const Theron::Address Requestor )
{
  // The actual solver is initialised with the algorithm and the number of 
  // continuous variables it needs to assign.
  
  nlopt::opt Solver( OptimisationAlgorithm, ContinuousVariables.size() );
  
  // The initial values are recorded from the provided generic values via the 
  // visitor mechanism allowing us to do this generically without knowing 
  // exactly the type of the variable (it can be any real value like float or
  // double) as long as it is convertible to a double. An exception is thrown
  // if the number of received variable values less than the number of 
  // continuous variables. It is no problem if there are no discrete variables.

  InitialVariableValues InitialValues;

  if ( VariableValues.size() < ContinuousVariables.size() )
    throw std::string("Optimiser: Not enough initial values given!" );
  else
  {
    auto Variable = VariableValues.cbegin();
    
    for ( size_t VariableIndex = 0; 
	 ( VariableIndex < ContinuousVariables.size() ) 
	    && ( Variable != VariableValues.cend() );
	 ++VariableIndex, ++Variable )
      boost::apply_visitor( InitialValues, Variable->TheValue );
  
    // Then the discrete parameters are stored so that they can be easily passed
    // on to the evaluator actor when the objective function or the constraints 
    // are to be evaluated.
    
    DiscreteVariables.assign( Variable, VariableValues.cend() );
  }
  
  // Framing the search area setting the bounds of the variables
  
  SetVariableBounds( Solver );

  // The objective function is registered with the solver. Note that since the 
  // objective function is meant to be a C-function, it takes no 'this' pointer
  // and we have to pass the this pointer explicitly to the function.
  
  Solver.set_min_objective( &ContinuousOptimiser::ObjectiveFunction, this );

  // Then the constraint evaluator functions are registered with the solver. 
  // The functions are defined based on the type of constraints to evaluate, 
  // and the same tolerance is used for all constraints. 
  
  Solver.add_equality_mconstraint( 
    &ContinuousOptimiser::EvaluateConstraints< 
		  Solver::Constraint::Type::Equality >, this, 
    std::vector< double >( Constraints.EQCount(), ConstraintTolerance )
  );
  
  Solver.add_inequality_mconstraint(
    &ContinuousOptimiser::EvaluateConstraints<
		  Solver::Constraint::Type::Inequality >, this, 
    std::vector< double >( Constraints.NEQCount(), ConstraintTolerance)
  );  
  
  // The tolerance on the objective function is set as the stop criteria. The 
  // algorithm should stop whenever two successive evaluations of the objective 
  // functions gives values that are less than the tolerance.
  
  Solver.set_ftol_abs( ObjectiveFunctionTolerance );
  Solver.set_maxeval ( EvaluationLimit );
  
  // The solver also needs a place holder for the result of the objective 
  // function.

  double MinimumObjectiveValue;
  
  // Finally the optimisation can be started.

  try
  {
    Solver.optimize( InitialValues, MinimumObjectiveValue );
  }
  catch ( nlopt::roundoff_limited NLOptError )
  {
    std::cerr << "Solver: Round-off error - continuing" << std::endl;
  }
  catch ( std::bad_alloc NLOptError )
  {
    throw std::string("Solver: Out of memory");
  }
  catch ( std::runtime_error NLOptError )
  {
    throw std::string("Solver: Generic error");
  }

  // The values found are sent back to the requester in the pre-defined format
  // where the first values are for the solution for the continuous variables, 
  // then the given values of the discrete parameters used, then the value of
  // the objective function and finally the number of iterations. Note the 
  // initial values vector is now the actual optimal assignments for the 
  // continuous variables. Note also that there is no need to check ranges, 
  // since that condition was checked before assigning the initial values.
  
  std::vector< EvaluationActor::VariableValue > Results = VariableValues;
  
  auto OptimalVariable = Results.begin();
  
  for ( double value : InitialValues )
  {
    OptimalVariable->TheValue = value;
    ++OptimalVariable;
  }
  
  // Then add the last two special elements
  
  Results.push_back( 
	    EvaluationActor::VariableValue( nullptr, MinimumObjectiveValue ) );
  Results.push_back( 
	    EvaluationActor::VariableValue( nullptr, IterationCounter )  );
  
  // Sending the results, and we are done
  
  Send( Results, Requestor );
}


//-----------------------------------------------------------------------------
// Objective function
//-----------------------------------------------------------------------------
// Fundamentally this function encapsulates the UtilityFunction, however the 
// real function call has to be made through the remote Evaluation Actor 
// in order to ensure consistency if multiple optimisers are running in 
// parallel on the same problem. This function will therefore first format 
// the message for the remote evaluation actor, and then wait for the return 
// value to arrive.

double ContinuousOptimiser::ObjectiveFunction( 
  const std::vector< double > & VariableValues, 
  std::vector< double > & Gradient, 
  void * FunctionParameters )
{
  // Since NLopt uses a C-style interface the function call has no 'this' 
  // parameter - however NLopt allows a pointer to any additional parameters 
  // to be passed, and in this case it is defined to pass the 'this' pointer
  // so it is safe to redefine it in order to access members of the particular 
  // optimiser calling this function.
  
  ContinuousOptimiser * This 
	= reinterpret_cast< ContinuousOptimiser * >(FunctionParameters);

  // Currently we do not support the use of gradient methods, and it is an 
  // unrecoverable error if an algorithm that requires gradients is used.
  
  if ( !Gradient.empty() )
  {
    throw std::string("Gradient methods not supported (yet)!");
  }

  // Then the remote evaluation can be initiated. Note that the evaluation 
  // function will block until the objective value has been returned.
  
  This->EvaluateVariables( VariableValues, 
			   EvaluationActor::Message::Command::GetUtility );
  
  // The solution is now stored in the catcher's queue. We update the counter 
  // for the number of objective function evaluations, read the received value,
  // and returns it as the value of this 'function'.
  
  This->IterationCounter++;

  double 	  UtilityValue;  // The computed value
  Theron::Address From;          // The dummy address of the Evaluation actor
  
  This->ObjectiveValue.Pop( UtilityValue, From );
  
  return UtilityValue;
}

//-----------------------------------------------------------------------------
// Constructor
//-----------------------------------------------------------------------------
// The constructor orchestrates the entire solver operation. The variable 
// pointers are implicitly set through the constructor of the variables 
// object. The NLopt object is initialised to use a non-gradient method 
// and then the full set of constraints are split in equality and inequality
// constraints. Lastly, the objective function and the constraint functions
// are registered, before the optimisation is started.

ContinuousOptimiser::ContinuousOptimiser(
    Theron::Framework & LocalFramework,
    nlopt::algorithm TheAlgorithm,
    double ObjectiveTolerance, 
    double ToleranceOfConstraints,
    int    MaxEvaluations		)
: Theron::Actor(LocalFramework), DiscreteVariables(), 
  RemoteEvaluation(), ObjectiveValue(), ConstraintValues()
{
  // If the static variable pointer store is empty, it should be initialised
  
  if ( ContinuousVariables.empty() )
    for ( auto VariableRecord : Solver::Variables )
      if ( VariableRecord.second->GetCategory() == 
			      Solver::Domain::Categories::Continuous )
	ContinuousVariables.push_back( VariableRecord.second );
 
  // Then the other variables are initialised
 
  OptimisationAlgorithm      = TheAlgorithm;
  ObjectiveFunctionTolerance = ObjectiveTolerance;
  ConstraintTolerance	     = ToleranceOfConstraints;
  EvaluationLimit	     = MaxEvaluations;
  IterationCounter	     = 0;    
  
  // The handlers for the returned objective value and the constraint vectors 
  // are registered.
  
  RemoteEvaluation.RegisterHandler( 
      &ObjectiveValue, &Theron::Catcher< double >::Push  );
  
  RemoteEvaluation.RegisterHandler( 
      &ConstraintValues, &Theron::Catcher< std::vector< double > >::Push  );
  
  // Finally we register the message handler and the class is ready to start
  // optimising the continuous part of the problem.
  
  RegisterHandler( this, &ContinuousOptimiser::Optimiser );
}
