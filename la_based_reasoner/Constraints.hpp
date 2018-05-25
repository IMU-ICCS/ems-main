/*==============================================================================
Constraints

This defines a unified way of setting the constraints for the problem to be 
solved by the LA Solver.

Author and Copyright: Geir Horn, 2018
License: LGPL 3.0
==============================================================================*/

#ifndef LA_SOLVER_CONSTRAINTS
#define LA_SOLVER_CONSTRAINTS

#include <functional>        // To store the constraint functions 
#include <list>              // The functions are kept in lists
#include <vector>            // To evaluate all constraints at once
#include <memory>            // For shared pointers

// -----------------------------------------------------------------------------
// Kronecker's Delta function
// -----------------------------------------------------------------------------
//
// The Kronecker's Delta function is unity if its two arguments can compare 
// equal, otherwise it is zero. It is readily defined as a template to support
// any kind of arguments that has and can be compared with an equality operator.
//
// It is useful to activate or de-activate a constraint based on a particular 
// value. For instance, if the FirstVariable must be less than 4.0 if provider 
// is "AWS", this corresponds to the constraint 
//	KroneckerDelta( Provider(), "AWS" ) * FirstValue() <= 
//  KroneckerDelta( Provider(), "AWS" ) * 20
// or better in standard form ( <= 0 ):
//  KroneckerDelta( Provider(), "AWS") * (FirstValue() - 20 )
// where KroneckerDelta evaluates to 1 if its two argument compares equal
// or to zero otherwise. Hence, in this example, if the provider is AWS 
// the constraint will have a value, otherwise its value will be zero and 
// the comparison <= 0 will return true irrespective of the FirstValue().

template< class FirstType, class SecondType >
unsigned char KroneckerDelta( const FirstType & a, const SecondType & b )
{
	if ( a == b )	return 1;
	else return 0;
}

namespace LASolver
{
	
/*==============================================================================

 Constraint

==============================================================================*/
// 
// A constraint is a function over the variables that provides an evaluation 
// function that must be defined. The idea is that the functional expression is 
// stored as a lambda expression in a standard function. 

namespace Constraints
{
	extern std::list< std::function< double(void) > > Equality, Inequality;	
}

/*==============================================================================

 Vector view

==============================================================================*/
//
// Some solvers will take the values of the constraints in a single vector and
// there is a function to return this vector

std::vector< double > ConstraintValues( 
	std::list< std::function< double(void) > > & TheConstraints )
{
	std::vector< double > Result;
	
	for ( auto & aConstraint : TheConstraints )
		Result.push_back( aConstraint() );
	
	return Result;	
}

}  // Name space LA Solver

//
// -----------------------------------------------------------------------------
// Pre-processor macros
// -----------------------------------------------------------------------------
//
// To hide most of the C++ annotations in order to define the constraint 
// lambda functions two pre-processor macros are given. It would be perfect if 
// these could have been defined as constant expressions, but then the argument 
// is 'typed' and not transferred as an expression to the lambda expression.
// A drawback with the macro approach is that it has to be defined on a single 
// line

#define Eq_Constraint( FunctionalForm )   ( LASolver::Constraints::Equality.emplace_back( [&](void)->double{ return FunctionalForm; } ) )
#define InEq_Constraint( FunctionalForm ) ( LASolver::Constraints::Inequality.emplace_back( [&](void)->double{ return FunctionalForm; } ) )

#endif // LA_SOLVER_CONSTRAINTS
