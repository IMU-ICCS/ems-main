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

namespace LASolver::Constraints
{
/*==============================================================================

 Registry

==============================================================================*/
//
// The registry maintains two lists of constraints - one for equality 
// constraints and one for inequality constraints. 

class Registry
{
private:
	
	std::list< std::function< double(void) > > TheConstraints;
	
public:
	
	// The constraint functions are added to the list by the new function that 
	// essentially moves the lambda expression into the list.
	
	inline void New( const std::function< double(void) > && ConstraintFunction )
	{
		TheConstraints.emplace_back( ConstraintFunction );
	}
	
	// There is a simple function to evaluate all constraints in one go
	
	inline std::vector< double > Evaluate( void )
	{
		std::vector< double > Result;
		
		for ( auto & aConstraint : TheConstraints )
			Result.push_back( aConstraint() );
		
		return Result;
	}
	
};

extern Registry Equality;
extern Registry Inequality;
	
}  // Name space LA Solver
//
// -----------------------------------------------------------------------------
// Pre-processor macros
// -----------------------------------------------------------------------------
//
// To hide most of the C++ annotations in order to define the constraint 
// lambda functions two pre-processor macros are given. These has to be given 
// on one line only and therefore goes beyond one line. They are fully 
// qualified to enable usage outside of any name space, and they are defined
// outside of any name space. As pre-processor directives they will be 
// applied first to be replaced by the correct C++ form for defining the 
// constraint functions.

#define Eq_Constraint( FunctionalForm )   ( LASolver::Constraints::Equality.New( [&](void)->double{ return FunctionalForm; } ) )
#define InEq_Constraint( FunctionalForm ) ( LASolver::Constraints::Inequality.New( [&](void)->double{ return FunctionalForm; } ) )

#endif // LA_SOLVER_CONSTRAINTS
