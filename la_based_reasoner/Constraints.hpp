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

#include "Variables.hpp"     // To set values before evaluating constraints

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
//
// However, in order to correctly evaluate the constraints the variables must 
// be set first as the global variable values are used in the constraints. To 
// ensure that this is correctly done, the constraints and the evaluation of 
// constraints are encapsulated in a class controlling the access to the actual
// constraint functions.

class Constraints
{
private:

  // The functional expressions of the constraints are kept in two simple lists
	// of standard functions.
	
	static std::list< std::function< double(void) > > EqFunctions, InEqFunctions;	
	
public:
	
	// The two types of constraints are defined as accessible types used when 
	// defining or accessing the interface functions
	
	enum class Type
	{
		Equality,
		Inequality
	};
	
	// There is a function to add a constraint to the right function list. This 
	// will only be used in the pre-processor macros defined below.
	
	template< Type TheConstraintClass, class LambdaFunction >
	static void Register( LambdaFunction && TheExpression )
	{
		if constexpr ( TheConstraintClass == Type::Equality )
	    EqFunctions.emplace_back( TheExpression );
		else if constexpr ( TheConstraintClass == Type::Inequality )
			InEqFunctions.emplace_back( TheExpression );
	}
	
	// A similar function is used to evaluate the constraints given a set of 
	// variable value assignments. The function will use the variable registry 
	// to set the values before the right set of constraint functions are 
	// evaluated.
	
	template< Type TheConstraintClass >
	static std::vector< double > Evaluate( 
  Configuration::VariableRegistry::DiscreteVariableValues   & DiscreteValues,
	Configuration::VariableRegistry::ContinuousVariableValues & ContinuousValues	)
	{
		std::vector< double > Result;
		
		Configuration::VariableRegistry::Discrete->SetValues(   DiscreteValues   );
		Configuration::VariableRegistry::Continuous->SetValues( ContinuousValues );

		if constexpr ( TheConstraintClass == Type::Equality )
			for ( auto & aConstraint : EqFunctions )
				Result.push_back( aConstraint() );
		else if constexpr ( TheConstraintClass == Type::Inequality )
			for ( auto & aConstraint : InEqFunctions )
				Result.push_back( aConstraint() );
			
		return Result;
	}
	
	// A typical scenario is to keep the discrete variables fixed when solving
	// the problem for the the continuous variables. An overloaded version of 
	// this function supports this scenario. However, care must be taken since 
	// if there are parallel optimisers running within for the same problem,
	// they may have different discrete variables to look after and then the 
	// full form of the function is the only way to ensure that the right 
	// discrete variable values are used in the constraint calculation.
	
	template< Type TheConstraintClass >
	static std::vector< double > Evaluate( 
	Configuration::VariableRegistry::ContinuousVariableValues & ContinuousValues	)
	{
		std::vector< double > Result;
		
		Configuration::VariableRegistry::Continuous->SetValues( ContinuousValues );

		if constexpr ( TheConstraintClass == Type::Equality )
			for ( auto & aConstraint : EqFunctions )
				Result.push_back( aConstraint() );
		else if constexpr ( TheConstraintClass == Type::Inequality )
			for ( auto & aConstraint : InEqFunctions )
				Result.push_back( aConstraint() );
			
		return Result;
	}
	
};

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

#define Eq_Constraint( FunctionalForm )   ( LASolver::Constraints::Register< LASolver::Constraints::Type::Equality   >( [&](void)->double{ return FunctionalForm; } ) )
#define InEq_Constraint( FunctionalForm ) ( LASolver::Constraints::Register< LASolver::Constraints::Type::Inequality >( [&](void)->double{ return FunctionalForm; } ) )

#endif // LA_SOLVER_CONSTRAINTS
