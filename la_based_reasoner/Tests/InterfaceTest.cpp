/*=============================================================================
LA Solver interface test

This file gives an example on how the LA Solver should be interfaced by the 
CP-Generator setting up the variables with their domains, and the constraints 
over the variables.

Author and Copyright: Geir Horn, 2018
License: LGPL 3.0
=============================================================================*/

#include <string>          // Standard strings

#include "Domains.hpp"     // The supported domain types
#include "Variables.hpp"   // The solver variables
#include "Constraints.hpp" // Constraint management

// It is BAD practice of pre-defining the name spaces, and it is only used here
// because everything belongs to the LA Solver name space.

using namespace LASolver;

int main(int argc, char **argv) 
{
	// ---------------------------------------------------------------------------
	// Domains
	// ---------------------------------------------------------------------------
	//
	// To illustrate the domain constructors a few domains are explicitly 
	// created as variable. Note that it will be better to put these as temporary
	// arguments to the variable constructors, see below. Each variable 
	// constructor will copy the domain, so by first defining the domain and 
	// then passing it to the variable creates two copies of the domain of which 
	// only the one inside the variable will be used. 
	//
	// First some intervals that contains all values of the type from the lower 
	// to the upper limit. Any numerical type can be used for the type definition.
	
	Domain::Interval< double >              RealRange ( 1.5, 6.0 );
	Domain::Interval< unsigned short int >  ShortRange( 1, 10 );
	
	// A more complex interval is the discrete set. It allows variable values 
	// only from the set. The values of the set can either be given within 
	// curly braces {} or as a comma separated list of elements. If the elements
	// are already contained in an Standard Template Library (STL) collection, 
	// like a vector, one can also give the begin and end iterator to have the 
	// set copy a range of values from the other container.
	
	Domain::Set< unsigned int >  IntegerCollection({1,1024,2056});
	Domain::Set< std::string >   ProviderNames( "Private", "AWS", "Google", "Rackspace" );
	
	// The final type of domain is the discrete set of intervals. If two or more 
	// of the given intervals are overlapping they will be merged into one 
	// interval: The first two intervals in the example will therefore be 
	// one subinterval [1,..,10]. It should also be noted that all of the 
	// subintervals must be defined for the same type (here "int"). 
	
	Domain::Set< Domain::Interval< int > > SetOfIntervals(
		{ Domain::Interval< int >(1,5), Domain::Interval< int >(3,10), 
			Domain::Interval< int >(15,30), Domain::Interval< int >(50,100) }	);
	
	// In practice one may make a type definition for the subinterval and give 
	// a shorter initialisation. This will only affect the compiler memory usage
	// and is not wasteful, it just makes the code less obvious.
	
	using SubInt = Domain::Interval< double >;
	
	Domain::Set< SubInt > DoubleIntervals( 
												{ SubInt(0.5,3.14), SubInt(6.13, 10.0) } );
	
	// The CP meta model in PaaSage defined many more domain types, but they 
	// all seem to map nicely onto these three main categories. However, more 
	// domain types can be added later if needed.
	
	// ---------------------------------------------------------------------------
	// Variables
	// ---------------------------------------------------------------------------
	//
	// Each variable is defined on a domain, and it takes three parameters:
	// The name of the variable as a string, the domain, and the optional initial 
	// value. If no initial value is given, it will be taken as a random value 
	// from the domain. It should be noted that the given name is the label used
	// for the variable when it provided as a part of the configuration.
	//
	// First a variable over an interval, where the domain has already been 
	// defined.
	
	Variable< Domain::Interval< unsigned short int > >
		FirstVariable( "First", ShortRange, 8 );
	
	// Again one may make this shorter and better with a type definition and 
	// in-line definition of the domain. The initial value is not given, and so 
	// it will be taken as a random value in the interval [3.14, 7.0). Note that
	// the interval is assumed to be half-open in this case.
	
	using RealInterval = Domain::Interval< double >;
	
	Variable< RealInterval > SecondVariable( "Second", RealInterval(3.14, 7.0) );
	
	// It would of course be possible to do a query-replace with the definition 
	// of the real interval in the above variable definition. However, if many 
	// variables belongs to the same domain type (like Real Interval here), it 
	// may be reasonable to define this as a type.
	//
	// An extensive definition of a set variable
	
	Variable< Domain::Set< std::string > > 
	Provider( "CloudProvider", 
						Domain::Set< std::string >("Private", "AWS", "Google", "Rackspace"),
						"AWS" );
	
	// And an even more extensive definition of a set of disjoint intervals
	
	Variable< Domain::Set< Domain::Interval< int > > >
	Disjoint( "SomeIntervals", Domain::Set< Domain::Interval< int > >(
		{ Domain::Interval< int >(1,5), Domain::Interval< int >(3,10), 
			Domain::Interval< int >(15,30), Domain::Interval< int >(50,100) } ), 
		75 );
	
	// The initial value 75 will be used in this case since it is a member of the 
	// last interval. If an initial value had been given that is not in any 
	// subinterval, it would have been replaced with a random value from one of 
	// subintervals. As an example, consider if 45 was given as initial value in 
	// this example. It does not belong to any of the subintervals and a random 
	// initialisation would have been made instead.

	// ---------------------------------------------------------------------------
	// Constraints
	// ---------------------------------------------------------------------------
	//
  // The constraints should be defined last since they involve functional 
	// expressions of the variables that to a value that can be checked against 
	// the value of zero. The expressions should be given with no semi-colon at 
	// the end. The operator () is used to get the value of the variable. It
	// must be noted that all constraints should be the left hand expressions of
	// comparisons with zero (known as standard form). Thus an equality constraint
	// is given as h(x) (== 0) and an inequality constraint as f(x) (<= 0).
	// 
	// It is necessary to distinguish between equality and inequality constraints. 
	// The former has the potential to reduce the problem size, e.g. if there 
	// is an equality constraint saying "Provider() - "Google" (== 0)  " then the 
	// provider variable can be eliminated from the problem because we know its 
	// value. Note that such simple eliminations should be done by the 
	// CP-Generator and in this case there should never be a provider variable. 
	// Other cases are not so obvious, and the effective elimination of a variable
	// is done by the solver. An example of this is if there is a constraint 
	// 		FirstVariable() + SecondVariable() == 9
	// and its functional expression must be given in standard form (== 0):
	//		FirstVariable() + SecondVariable() - 9
	// Then one of the variables is given as a function of the value of the other
	// even though the connection is more indirect (as it requires additional 
	// inequality constraints to restrict the domain of the variable not 
	// eliminated). inequality constraints confine the search space.
	// 
	// Constraints are in general easy to evaluate, and the more confined the 
	// search space is, the faster a solution will be found. One should therefore
	// be encouraged to define effective constraints, but avoid inefficient 
	// constraints. From the previous example: Defining a constraint like
	//    FirstVariable() <= 20
	// To be given in standard form as ( <= 0 ):
	//    FirstVariable() - 20
	// is ineffective because the above equality constraint puts a maximum value 
	// on the first variable to be 9 - 3.14 = 5.86.
	//
	// Constraint elimination and search space reduction is a broad research 
	// topic and beyond the scope of this project. Here the solver will just 
	// AND together all the constraint values and only evaluate the utility 
	// function if all constraints evaluate to true for the problem.
	//
	// It is also useful to activate or de-activate a constraint based on a 
	// particular value. For instance, if the FirstVariable must be less 
	// than 4.0 if provider is "AWS", this corresponds to the constraint 
	//	KroneckerDelta( Provider(), "AWS" ) * FirstVariable() <= 
	//  KroneckerDelta( Provider(), "AWS" ) * 20
	// or better in standard form ( <= 0 ):
	//  KroneckerDelta( Provider(), "AWS") * (FirstVariable() - 20 )
	// where KroneckerDelta evaluates to 1 if its two argument compares equal
	// or to zero otherwise. Hence, in this example, if the provider is AWS 
	// the constraint will have a value, otherwise its value will be zero and 
	// the comparison <= 0 will return true irrespective of the FirstVariable().

	Eq_Constraint( FirstVariable() - SecondVariable() - 9 );
	InEq_Constraint( KroneckerDelta( Provider(), "AWS") * 
									 (FirstVariable() - 20) );
	
	return EXIT_SUCCESS;
}

