/*=============================================================================
  Solver interface - Definition of variables and constraints
  
  This file is compiled separately as a shared library for the solver. It 
  defines two global structures, one mapping the numerical variable IDs to 
  generic variable pointers, and one being a list of constraints. 
  
  It is intended that the variables and the constraints are exported from the
  metamodel in two files "Variables.model" and "Constraints.model", which are
  included from this file. Thus there should be no need for the metamodel 
  exporter to change anything in this file. The actual syntax of the two files
  are given in the example sections below.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

// The constraints can use mathematical functions, so we include them to 
// ensure that we can support any functional expression

#include <cmath>

// Then the solver interface definitions

#include "Interface.hpp"

using namespace Solver;

// In order to learn how to write variables and constraints there are examples
// below. The following pre-processor flag decides whether these examples 
// will be compiled or not. It is useful to first check the syntax of an 
// export by replacing the content of the example sections, and when one is 
// confident that the export works as intended, the example sections can be 
// removed together with the following flag.

//#define SOLVER_INTERFACE_EXAMPLES

/*****************************************************************************
 Metric static variables
 
 NOTE: These must be declared in this file in order to ensure that they are
 properly initialised before the Metrics objects are defined as part of the 
 Variables section below. They MUST NOT be moved to the "Metric.cpp" file!
******************************************************************************/
 
std::map< MetricsID, MetricBase * > Solver::MetricBase::Metrics;

// The freeze counter is initialised to zero

unsigned int MetricBase::Frozen = 0;

// To ensure that no freeze is made during an update of the metrics to their
// last received value, there is a lock to prevent the freeze. There is also 
// a lock to ensure that only one thread accesses the map storing all 
// metrics.

std::mutex MetricBase::FrozenLatch,
	   MetricBase::LookupLatch;

/*****************************************************************************
 Variable and metric definitions - Utility function
 
 This section defines some examples of various types of variables. Such 
 definitions are normally defined by the metamodel in a separate file which is
 included in this file.
 
 The general syntax of a variable is 
 
 Variable< DomainType > VariableName( Label, InitFlag, InitValue, 
				      DomainInitialisation );
 
 The label can be any text string however it is strongly recommended that 
 the label is the same as the variable name. This will make it easier if the
 solver should need to pass feedback to the users about variables that could
 be assigned values within the limits of their domains to satisfy all 
 constraints. 
 
 The InitFlag must be true if an initial value is given, and false if a random
 value from the domain of the variable should be used as initial value. If
 the flag is true, then the InitValue is used as this initial value, otherwise
 it will be ignored (but not that it must be of the same type as the domain 
 value. The rest of the parameters are passed on to the domain.
 
 The domain type is one of the domain types defined in the header file,
 potentially with a type qualifier, e.g. double, as in 
 
 Variable < Range< double > > 
 
 if the domain is a range in the real domain. The domain initialiser is a 
 list whose elements should be as expected by the domain, and where the 
 number of elements depends on the type of domain. For instance if the domain
 is a list, i.e. a list of strings, the domain initialisation may look like
 
 DomainInitialisation = {"first", "second, "third", "last"}
 
 whereas if the domain is a range, the list will have only two elements
 
 DomainInitialisation = { LowerLimit, UpperLimit }
 
 and it is even possible to give these two element without the curly braces.
 Just beware that the range is the exception allowing the curly braces to be
 omitted.
 
 Please refer to the examples below to understand how different types of 
 variables can be defined.
 
 The Utility function should be defined last in the variables file and it
 has the following format:

 Solver::ObjectiveFunction Solver::UtilityFunction( [&](void){ return
   <some expression of variables.Value() and metrics.Value() >;
 });

 
******************************************************************************/

// The variables themselves are defined as global objects within the scope of 
// this file. This is necessary in order to be able to refer to the variables
// by name in the constraints thereby making the constraints more readable. In
// order to access the variables from the solver code outside of this file,
// there is a global structure mapping from the variable ID to a pointer to
// the actual variable object. NOTE: The IDs must be unique.

Solver::VariableMap    Solver::Variables;

// The dynamic library only supports the passing of pointers back to the 
// calling code, and so we define a helper function to return the address of 
// the variable structure. IMPORTANT: By passing a pointer to a C++ structure,
// i.e. the variable map, we implicitly assume that both the shared library 
// AND this code is compiled with the same version of the compiler and with 
// the same parameters so that the memory lay-out of the structure would be 
// the same on both sides of the library boundary (the same assumption holds 
// for the variable pointers in the map by the way).

extern "C" VariableMap * GetVariables (void)
{
  return & Solver::Variables;
};

// The method deleting a generalised value pointer calls the delete that must
// be specific to this dynamically linked library. Although the definition of 
// the function is trivial, it must be defined here in order to avoid that the
// compiler optimiser inlines this simple function and thereby run the risk 
// of using the wrong delete operator.

void Domain::GeneralisedValuePointer::DestroyPointer(void)
{
  delete this;
}

// One might rightly ask why not a similar no-inline declaration is necssary
// for the "new" call allocating the generalised value pointer. The reason is
// that the pointer is allocated by the derived domain types that are all 
// templated classes, thus their functions will be instantiated where the
// objects are instatiated, i.e. in this file (the dynamically linked library).

//-----------------------------------------------------------------------------
// START: Variable definitions
//-----------------------------------------------------------------------------

#ifdef SOLVER_INTERFACE_EXAMPLES

/*
 * The following is mainly syntax examples intended to show the possibilities
 * but not do anything useful. 
 * 

// A simple List based variable

Variable< List >  Greeting( "Greeting", true, "Hello",
		           { "Hello", "Hola", "Bonjour", "God dag"}),
		  World  ( "World", false, "",
			   {"World", "Mundo", "Monde", "Verden"});
		  
// Then two numerical lists Note that we have to tell the compiler that a 
// constant like 13 is an unsigned int and not an int as this is impossible 
// to tell from the constant itself that it should be stored in a variable 
// that does not support negative numbers.
		  
Variable< NumericList< unsigned int > > 
      Fibionacci( "Fibionacci", fales, 0u, 
		  {1u, 1u, 2u, 3u, 5u, 8u, 13u, 21u, 34u});
      
Variable< NumericList< double > >
      pi( "Pi", true, 3.14, {3.14, 6.28, 9.42} );
      
// Then two range variables, one integral and one double. 
      
Variable< Range< unsigned int > > Zero2ten( "Zero2ten", false, 0, 0, 10);
Variable< Range< double> > Radians( "Radians", false, 0.0, {0.0, 6.28});

// The multi-range is basically a range of ranges, thus one can either give 
// the ranges explicitly in the initialiser part, or one could give a set of 
// numbers being pairwise interpreted as the upper and lower bound of the 
// ranges. The following two declarations are therefore the same.

Variable< MultiRange< unsigned int> > 
          SpeedLimit( "SpeedLimit", false, 0,
	    { Range< unsigned int >(0,30), Range< unsigned int >(50,90),
	      Range< unsigned int >(100,130) }),
	  DriveLimit( "DriveLimit", true, 50, {0u,30u,50u,90u,100u,130u});

// A final thing about multi ranges is that it consolidates the given ranges
// into non-overlapping ranges. The following range should therefore reduce to
// a single interval
	  
Variable< MultiRange< unsigned int> > 
      Ferrari( "Ferrari", false, 0, {0u,30u,20u,50u,50u,90u,30u,130u});

// The metric is a variable that is updated based on measurements either on 
// the execution platform or from within the application itself. It will 
// be updated by the metrics collector as soon as a new value is available.
      
Metric<double> Load("CPU");

// The Utility function should be defined last in the variables file and it
// has the following format:
//
// Solver::ObjectiveFunction Solver::UtilityFunction( [&](void){ return
//   <some expression of variables and metrics>;
// });
*/

#else
  #include "Variables.model"
#endif

/*****************************************************************************
 Constraint definitions
 
 The constraints are referring to the values of variables, and a constraint is 
 defined by a satisfaction function evaluating to true if the constraint is 
 satisfied, and to false if the constraint is not satisfied. However, the 
 constraints are normally defined on "normal form" as either equality 
 constraints or inequality constraints as
 
 g(x) <= 0
 h(x) == 0
 
 A simple boolean satisfaction function "f(variables) >= q(variables)" 
 can only evaluate to true or false. However, if we write the same constraint 
 using 
 
 g(variables) = q(variables) - f(variables) 

 then the same constraint is "g(variables) <= 0", which is on normal form. 
 So in other words, you can turn any constraint into "normal form" by 
 subtracting the left hand side of the comparison operator from the right hand 
 side. With the normal form we only need to know the value function "g(x)" and
 wheather the constraint is an equality constraint or an inequality constraint.
 
 The reason for doing so is that we can now get a VALUE for g(variables). 
 Essentially, many algorithms will use the gradient of the constraints, so 
 that if we have the constraint "g(variables)<=0", and for some variable 
 assignment we have "g(variables) > 0". Then it would be interesting to know 
 in which direction we should change the variables in order to decrease the 
 value of g.
 
 In order to define the constraint value functions g(x) or h(x), lambda 
 functions are used whose general structure is
 
 [&](void){ return <functional form of the constraint value function>; };
 
 The examples below show how constraints based on the example variables are 
 defined, and hopefully this is sufficient to define real constraints based
 on the information in the metamodel. All constraints are made with the 
 helper function make_XX_constraint.
 
******************************************************************************/
// At this point we must remember two fundamentals: 
// 1) You cannot execute statements outside of the stack starting with "main".
// 2) When working with a shared library you can only return pointers to 
//    memory.
//
// The first fundamental implies that we cannot say make_constraint directly,
// because that is calling a function. For the same reason we cannot say
// Constraints.push_back() because we would again be calling a function. BUT
// we can define a function which we can call from main after opening the 
// shared library, and inside this function we can of cause execute statements
// and manipulating symbols that are global to the scope of this file. 
//
// The solution is to define the constraints in the constructor of the global
// constraint list, since the constructors of global objects are invoked before
// main() thus ensuring that the constraints are propoerly intiailised.

ConstraintContainer::ConstraintContainer(void)
: std::vector< Solver::Constraint* >()
{
  NumberOfEQConstraints  = 0;
  NumberOfNEQConstraints = 0;
  
  //----------------------------------------------------------------------------
  // START: Constraint definitions
  //----------------------------------------------------------------------------

  #ifdef SOLVER_INTERFACE_EXAMPLES
    
  // This is how the content of the file exported from the metamodel should look
  // like. It is explicitly formatted for easy output as each constraint has a 
  // header line: 
  // 	make_XX_constraint( [&](void){ return
  // Then follows the lines needed to evaluate the constraint - in most cases 
  // one line would suffice. Remember to end the constraint definition with a 
  // semicolon! Then follows the footer line of the constraint:
  //	});
  // To close the definition.

  /*
   * Syntax examples
   * 
   
  make_inequality_constraint( [&](void){ return
    SpeedLimit.Value() - Ferrari.Value();
  });

  make_inequality_constraint( [&](void){ return
    ( Zero2ten.Value() + pow( Fibionacci.Value(), 3.0 ) )- DriveLimit.Value();
  });

  make_equality_constraint( [&](void){ return
    10.0 - Radians.Value() * pi.Value();
  });
  */
  
  #else
    #include "Constraints.model"
  #endif
  
  //----------------------------------------------------------------------------
  // END: Constraint definitions
  //----------------------------------------------------------------------------
}

//..............................................................................
// Other constraint functions
//..............................................................................
// Then we can define the global list of constraints essentially invoking the 
// above constructor

Solver::ConstraintContainer Solver::Constraints;

// To get a reference to this if dynamic linking is used, the following 
// function should be called.

extern "C" Solver::ConstraintContainer * GetConstraints (void)
{
  return & Solver::Constraints;
};

// The register function on a constraint inserts a copy of the constraint into 
// the list of constraints. It ensures that the constraint list only holds 
// pointers to globally dynamically allocated objects that can be deleted, 
// and no stack object addresses.

void Constraint::Register(void)
{
  if ( TypeOfConstraint == Type::Equality )
    Constraints.NumberOfEQConstraints++;
  else
    Constraints.NumberOfNEQConstraints++;
  
  Constraints.push_back( MakeCopy() );
}

// The destructor of the constraint container can now safely delete all the 
// constraint objects using the global delete since we know that they have 
// all be allocated using the global new.

ConstraintContainer::~ConstraintContainer(void)
{
   for ( Constraint * TheConstraint : Constraints )
     ::delete TheConstraint;
}

