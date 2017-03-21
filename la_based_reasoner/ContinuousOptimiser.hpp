/*=============================================================================
  Continuous optimiser
  
  This optimiser is an interface to the NLopt [1] package, which is a part of
  most standard Linux distributions, to solve for continuous variables taking 
  all discrete variables as fixed during the optimisation.
  
  This solver is used to find a solution for the continuous variables when 
  values have been assigned to the discrete variables. It is an actor so 
  so that a learning environment can explore several different settings of 
  the discrete variables concurrently by sending the discrete variable 
  assignments off to these actors. 
  
  This implies that most of the work is done by the message handler, with 
  support functions to interact with the evaluation actor to compute the 
  value of the utility function and the constraints.
  
  References:
  
  [1] http://ab-initio.mit.edu/wiki/index.php/NLopt 
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef CONTINUOUS_OPTIMISER
#define CONTINUOUS_OPTIMISER

#include <vector>
#include <algorithm>
#include <ostream>
#include <limits>
#include <nlopt.hpp>

#include "Interface.hpp"
#include "Theron/Theron.h"
#include "EvaluationActor.hpp"

// The continuous optimiser now belongs to the solver namespace since it 
// cannot be used outside of that framework because it is an actor that 
// interacts with the other actors of the solver.

namespace Solver
{

// The optimiser is an actor that starts the optimisation process when it 
// receives a set of discrete values for the discrete variables, and it 
// returns the value of the best objective function together with a vector 
// of all variable values, including the pointers to each variable and the 
// assigned value.
  
class ContinuousOptimiser : public Theron::Actor
{
private:
  
  // The optimisation algorithm is given as a parameter to the constructor and
  // remembered in a variable so that it can be re-used for each optimisation 
  // run this actor will do.
  
  nlopt::algorithm OptimisationAlgorithm;
  
  // There are also two parameters for the tolerance and one for the maximum 
  // number of evaluations of the objective function that can be done in the 
  // search.
  
  double ObjectiveFunctionTolerance, ConstraintTolerance;
  int    EvaluationLimit;
  
  // The optimisers need to assign values to the continuous variables and pass
  // on the values for the discrete variables. It is therefore useful to store
  // the pointers to the two classes of variables in separate vectors. 
  // Furthermore, since the variables are defined by the problem to be solved,
  // and compiled and linked with the solver, they will not change during 
  // a run. They can therefore be shared among the optimisers as the vector
  // values will only be read during a run and this will not create problems
  // even if the values are read from two different threads.
  
  static std::vector< GenericVariable * >  ContinuousVariables;
  
  // The discrete variables are received by the message handler as variable
  // value records as defined by the evaluation actor. It is therefore special
  // to this class and is not shared as a static variable.
  
  std::vector< EvaluationActor::VariableValue > DiscreteVariables;
  
  // The actual optimisation is done by the message handler function which
  // receives a vector of variable values. First initial values for all the 
  // continuous variables, and then the values of the discrete variables, 
  // which is stored in the above vector before the optimiser is initialised 
  // and optimisation can start. It returns the vector of all variables, with 
  // the optimal assignments for the continuous variables first and a copy of 
  // the discrete variables thereafter. Then follows the real value of 
  // the objective function, and the last value is the number of 
  // evaluations of the objective function (and the constraints) necessary to 
  // obtain this solution with the given accuracy (see the constructor below).
  // 
  // Note that there is a supporting function below that can be used to print
  // this format to an output stream given with the format expected for 
  // conversion back to other systems.
  
  void Optimiser( const std::vector< EvaluationActor::VariableValue > &
		  VariableValues, const Theron::Address Requestor   );

  // Setting the upper and lower bound constraints for all variables. Even if 
  // some of the constraints are already bound constraints set by the variables
  // and domains themselves, such constraints can be non-linear (see the 
  // MultiRange domain for instance). Hence, it helps to frame the problem by
  // giving the absolute boundaries for the variables.

  void SetVariableBounds ( nlopt::opt & Solver );
  
  // The objective function and the constraint evaluation functions are the 
  // interfaces to the back-end communication with the Evaluation Actor that 
  // does the real evaluation. This means that the interface functions must
  // halt the actor while waiting for the response. The actor has no yield or
  // wait function - it only processes messages from its message queue. 
  // A Receiver is an object that allows arbitrary code to wait for the 
  // receiver to get a message. The issue is then how to read the message 
  // received. It is possible to register a handler, but this will have to 
  // update a class data member that can be read when the execution continues 
  // after the receiver wait. The Catcher is a helper class for this situation
  // as it provides a handler for the message received, and allows the code
  // following the receiver wait to "directly" access this message.
  //
  // The receiver is shared among the the objective function and the constraint
  // evaluation functions, but as the received messages are different for the 
  // objective function that receives a single scalar and the constraints that
  // receives a vector of constraint values, two catchers are necessary.
  
  Theron::Receiver 			   RemoteEvaluation; 
  Theron::Catcher< double > 		   ObjectiveValue;
  Theron::Catcher< std::vector< double > > ConstraintValues;

  // The actual evaluation protocol is handled by function used by both the 
  // objective function and the constraint evaluators. It takes the values 
  // of the variables and a command indicating what to evaluate, and blocks
  // until the result is returned.
  
  void EvaluateVariables( const std::vector< double > & VariableValues,
			  EvaluationActor::Message::Command WhatToEvaluate );
  
  // The function to evaluate the objective function takes the variable 
  // values, a gradient vector, and some additional constant parameters for the 
  // function. This is registered as a callback function C-style, and it 
  // can therefore not be a regular member of the class since the "this" 
  // pointer is not passed. However, We can still achieve the desired 
  // behaviour by passing the 'this' pointer as the functional parameters,
  // and then cast it to the this pointer in the objective function.
  
  static
  double ObjectiveFunction( const std::vector< double > & VariableValues, 
			    std::vector< double > & Gradient, 
			    void * FunctionParameters );
  
  // The number of times the objective function is called determines the 
  // efficiency of the solver, and it is therefore counted. It is an integer
  // because the limit on the number of evaluations is an integer, and so 
  // the number of iterations will never exceed this limit.
  
  unsigned int IterationCounter;
    
  // The constraints are evaluated by a generic call back function specified
  // in the generic C-form (unfortunately no C++ overload for this). It is 
  // again possible to use the trick with the objective function ensuring 
  // that the Function Parameters contains the 'this' pointer. However, to
  // avoid implementing one function for each constraint type, a template 
  // is the best alternative. The function is defined relative to the type 
  // of constraints to evaluate.
					    
  template< Constraint::Type ConstraintType >
  static
  void EvaluateConstraints( unsigned int   NumberOfConstraints, 
			    double *       TheConstraintValues, 
			    unsigned int   NumberOfVariables, 
			    const double * VariableValues, 
			    double * 	   Gradient, 
			    void *         FunctionParameters     )
  {
    // We start by defining the missing 'this' pointer
    
    ContinuousOptimiser * This 
	= reinterpret_cast< ContinuousOptimiser * >(FunctionParameters);

    // We are currently not supporting gradients, so if they are requested
    // we simply stop with a strong warning. 
    
    if ( Gradient != nullptr )
    {
      throw std::string("Gradient methods not supported (yet)!");
    }

    // The evaluation must be done according to the type of constraints the 
    // function is evaluating. Even though this looks as an unnecessary test,
    // it should be optimised away by the compiler once it knows the template
    // parameter. 
    //
    // It is worth noting that since the function has a strict C-style 
    // signature, it becomes necessary to convert the variable values to a 
    // standard vector before it can be forwarded for evaluation.

    if ( ConstraintType == Solver::Constraint::Type::Equality )
      This->EvaluateVariables( std::vector< double >( 
		    VariableValues, VariableValues + NumberOfVariables ),
	    EvaluationActor::Message::Command::GetEqualityConstraints 
	    );
    else
      This->EvaluateVariables( std::vector< double >( 
		    VariableValues, VariableValues + NumberOfVariables ), 
	    EvaluationActor::Message::Command::GetInequalityConstraints 
	    );

    // At this point there is a vector of constraint values received from 
    // the remote evaluation, and this is captured and forwarded as a standard
    // C-array after verification that the right number of values have been 
    // received. It would be tempting to use the data method on the vector to
    // "convert" the vector to a C-style double * as in 
    //     TheConstraintValues = ReceivedValues.data();   
    // BEWARE!!! The data method returns a direct pointer to the memory array 
    // used internally by the vector to store its owned elements, i.e. it will 
    // deallocated when the vector is deleted at the end of this function! The 
    // good way to go is to copy the data from the vector to the memory area 
    // pointed to by the double pointer given to this function using again 
    // the pointer-iterator dualism.
      
    std::vector< double > ReceivedValues;
    Theron::Address       From; 
    
    This->ConstraintValues.Pop( ReceivedValues, From );
    
    if ( ReceivedValues.size() == NumberOfConstraints )
      std::copy( ReceivedValues.begin(), ReceivedValues.end(), 
		 TheConstraintValues );
    else
      throw std::string("Wrong number of constraint values received");
  }
   
public:
  
  // The constructor takes no parameters since all variables and constraints 
  // are globally defined. It sets out to solve the problem, and once the 
  // constructor terminates the interface functions can be called to retrieve 
  // the computed values. It supports a set of default parameters: The 
  // algorithm to use, the tolerance for the objective function, and the 
  // tolerance for the constraints. Basically, the algorithms are assumed to 
  // have converted once the change in the objective function is less than the
  // said tolerance. Finally, the search can also be stopped when a certain 
  // number of objective function evaluations has been executed. 
  //
  // The default tolerances implies that the objective function is solved to
  // about 8 significant digits and the constraints must be satisfied with an
  // accuracy of 6 digits. Relaxing these tolerances will allow a less accurate
  // solution to be found faster
  // 
  // There is by default no limit set for the number of evaluations of the 
  // objective function. 
  //
  // NOTE: Gradient based algorithms are not (yet) supported!
  
  ContinuousOptimiser( 
    Theron::Framework &              LocalFramework,
    nlopt::algorithm TheAlgorithm  = nlopt::algorithm::LN_COBYLA,
    double ObjectiveTolerance 	   = 1e-8,
    double ToleranceOfConstraints  = 1e-6,
    int    MaxEvaluations = std::numeric_limits< int >::max()	    );
  
  // The virtual destructor does nothing on its own, but allows the objects
  // to terminate properly.
  
  virtual ~ContinuousOptimiser (void)
  { };
};

}      // Namespace solver
#endif // CONTINUOUS_OPTIMISER