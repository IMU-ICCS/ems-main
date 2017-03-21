/*=============================================================================
  Evaluation Actor

  The main purpose of the evaluation actor is to provide sequential access to
  the problem variables and constraints when parallel solvers are used. The 
  evaluation actor receives a set of variable values and a command whether
  the utility function or the constraint values should be evaluated. It sends
  back to the caller a single real value in the first case, and a vector of
  real values in the second case. 
  
  The pointer to the variables must be provided as part of the provided vector
  and the associated value will be set without further checks on the validity 
  of the value type and the variable type.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef EVALUATION_ACTOR
#define EVALUATION_ACTOR

#include <vector>
#include "Interface.hpp"
#include "Theron/Theron.h"

// This actor belongs to the Solver name space

namespace Solver
{

// The actor is a Theron Actor so that it can send and receive messages.
  
class EvaluationActor : public Theron::Actor
{
public:
  
  // For each variable the caller must send a pointer to the variable and the 
  // value to use in the evaluation of this deliverable. This is defined as 
  // a class to allow it to be used as a temporary object.
  
  class VariableValue
  {
  public:
    
    GenericVariable * TheVariable;
    GenericValue      TheValue;
    
    VariableValue( GenericVariable * aVariable, const GenericValue & aValue )
    {
      TheVariable = aVariable;
      TheValue    = aValue;
    }
  };
  
  // The actual message to be passed contains two parts, an evaluation command
  // and a vector of variable values to set before evaluation. Note that it is 
  // tacitly assume that all variables will be set. This is because solvers 
  // running in parallel can have changed the variables since last evaluation,
  // and therefore the result will be unpredictable if only some of the 
  // variable values are defined. However, there is strictly not necessary if 
  // only one solver interacts with this evaluator.
  
  class Message
  {
  public: 
    
    enum class Command
    {
      GetUtility,
      GetEqualityConstraints,
      GetInequalityConstraints
    } TheCommand;
    
    std::vector< VariableValue > Values;
    
    Message( void ) : Values()
    {}
  };
  
private:
  
  // Equality constraints and inequality constraints are separately evaluated
  // and pointers to the constraint functions are therefore stored based on 
  // their type. These are initialised in the constructor of this actor.
  
  std::vector < const Constraint * > EqualityConstraints, 
				     InequalityConstraints;
  
protected:
  
  // The handler to receive variable values. First the variables are updated
  // to the given values, before computing the correct value or values.
  
  void GetValue( const Message & Request, const Theron::Address Requestor )
  {
    // A subtle point is that Theron messages are constant to allow sending 
    // temporary values, e.g. the utility function value below. Hence, we cannot 
    // set the value of the variables pointed to by the message directly. It
    // must first be copied. 
    
    std::vector< VariableValue > Values( Request.Values.begin(),
					 Request.Values.end()    );
    
    // Then all variables can be updated based on the received values.

    for ( VariableValue & VariableAndValue : Values )
      VariableAndValue.TheVariable->SetValue( VariableAndValue.TheValue );
  
    // Based on the request command, either the utility or the constraint 
    // values are computed. The utility value is trivially returning the 
    // value of the utility function. The constraint values will be stored
    // in a vector. This vector is not needed for a utility evaluation, 
    // so it should only be initialised when it is needed. However a switch 
    // is one scoping construct and just initialising the vector for the two
    // constraint cases would lead to a name clash. The solution is to scope
    // each of the two constraint evaluations (adding { }) to ensure that 
    // each case is handled separately by the compiler.
    
    switch ( Request.TheCommand )
    {
      case Message::Command::GetUtility :
	Send( UtilityFunction(), Requestor );
	break;
      case Message::Command::GetEqualityConstraints :
	{
	  std::vector< double > ConstraintValues;
	  
	  for ( const Constraint * TheConstraint : EqualityConstraints )
	    ConstraintValues.push_back( TheConstraint->Value() );
	  
	  Send( ConstraintValues, Requestor );
	}
	break;
      case Message::Command::GetInequalityConstraints :
	{
	  std::vector< double > ConstraintValues;
	  
	  for ( const Constraint * TheConstraint : InequalityConstraints )
	    ConstraintValues.push_back( TheConstraint->Value() );
	  
	  Send( ConstraintValues, Requestor );
	}
	break;
    }
  }
  
public:
  
  // The constructor takes an address of a Theron framework in which it executes
  // and register the above handler. Note that there should be one and only
  // one evaluation actor in the system (since its role is to protect the 
  // global variables). it is therefore not possible to change the name of 
  // this actor.
  
  EvaluationActor( Theron::Framework & LocalFramework )
  : Theron::Actor( LocalFramework, "EvaluationActor" )
  {
    // Initialise the two types of constraints by checking all constraints
    // defined globally.

    for ( auto ConstraintIterator = Constraints.cbegin();
	  ConstraintIterator     != Constraints.cend();
	  ++ConstraintIterator  )
    {
      const Constraint * TheConstraint = *ConstraintIterator;
      
      if ( TheConstraint->ConstraintType() == Constraint::Type::Equality )
	EqualityConstraints.push_back( TheConstraint );
      else
	InequalityConstraints.push_back( TheConstraint );
    } 
  
    // Then the handler function is registered and the actor is ready to 
    // evaluate the requested values based on given variable values.
    
    RegisterHandler(this, &EvaluationActor::GetValue );
  }
};

}      // End of namespace Solver
#endif // EVALUATION_ACTOR