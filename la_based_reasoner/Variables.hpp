/*==============================================================================
Variables

A variable represents a value of the configuration vector, and the problem 
is optimised for the configuration vector. There are fundamentally two types 
of variables:

Discrete variables
  These are solved by an assignment learning automata that tries to find 
  the value that maximises the expectation of a positive increase in the 
  objective function value.
  
Continuous variables
  These are variables bounded over some range.
  
Author and Copyright: Geir Horn, 2018
License: LGPL 3.0
==============================================================================*/

#ifndef LA_SOLVER_VARIABLES
#define LA_SOLVER_VARIABLES

#include <string>                             // Standard strings
#include <type_traits>                        // For meta-programming
#include <typeinfo>                           // To check types
#include <typeindex>                          // For storing type info in a map
#include <any>                                // For any value
#include <unordered_map>                      // For looking up types of Any
#include <functional>                         // To convert the any 
#include <sstream>                            // For error messages
#include <limits>                             // For numeric limits on types
#include <list>                               // To register the variables
#include <memory>                             // For smart pointers
#include <mutex>                              // Locks for variable registry
#include <boost/numeric/conversion/cast.hpp>  // Safe numeric casts

#include "Domains.hpp"            // The domain types of the variables
#include "RandomGenerator.hpp"    // To select random numbers over domains

// -----------------------------------------------------------------------------
// Configuration message: Google Protocol Buffer
// -----------------------------------------------------------------------------
//
// The configuration is exposed externally as a Google protocol buffer message
// and the "C++" generated header defines the message class, and the protection
// nested names pace is opened.

#include "laSolver.pb.h"

using namespace eu::melodic::models::interfaces::lasolver;

// The maps have to be defined here although they should have been defined as 
// a type in the Compute Utility Request class. Now it makes the implementation 
// vulnerable if a new message format is decided, but not manually reflected 
// in the below definitions

using IntMap = ::google::protobuf::Map< std::string, ::google::protobuf::int64 >; 
using RealMap = ::google::protobuf::Map< std::string, double >; 
using StringMap = ::google::protobuf::Map< std::string, std::string >; 

// -----------------------------------------------------------------------------
// LA Solver name space
// -----------------------------------------------------------------------------

namespace LASolver 
{
// It is necessary to give a forward declaration of the Variable template class
	
template< class DomainType, class Enable = void >
class Variable;
	
/*==============================================================================

 Configuration

==============================================================================*/
//
// The configuration is a map of all variable values, and provides a unified 
// way of exporting the values to the external users by the way of the Google 
// protocol buffers. This is best done by exploiting polymorphism and 
// specialisations and the related classes are protected by a dedicated 
// names space.
//
namespace Configuration
{
// -----------------------------------------------------------------------------
// Value Element
// -----------------------------------------------------------------------------
//
// Every variable IS a generic value element,  The Value Element keeps the 
// external name of the variable as an read-only element, and provides the 
// export function to the Google Protocol Buffer to be implemented by derived 
// value classes according to their type.
// 
// More importantly, the value element also implements a generic way to set 
// and get the value. This mechanism is based on the Any value type 
// available (since C++17), which needs to be converted to the actual value 
// held by the derived value class depending on its type. The semantic of the 
// Any variable is that the receiver should know the type of variable and 
// therefore be able to read it back correctly. However, in the "value based"
// system created here, type may depend on what the compiler assigns. For 
// instance a value of 10 may fit in all kind of integral types, and even if 
// the value class is defined for an unsigned it, it may be stored in the Any 
// variable as an unsigned short, or even char, type. The value element class 
// must ensure safe conversions between the different types depending on their 
// genetic class (integral, real, or text strings).

class ValueElement
{
private:
	
	// The safe conversion functions are depending on the argument type and the 
	// desired destination type, and implemented as type class dependent 
	// conversion functions. The interface to these functions is the Convert 
	// function below.
	//
	// When the conversion is to an integral value, it is necessary to check that 
	// the value is small enough to fit correctly in the return type. Otherwise,
	// a domain error will be thrown. Note that since the value will be rounded 
	// this will work both for integral variable values and for real integral 
	// values.

	template< class ReturnType, class Argument >
	typename std::enable_if< std::is_integral< ReturnType >::value && 
													 std::is_arithmetic< Argument >::value, 
													 ReturnType >::type
	SafeCast( const Argument & GivenValue )
	{
		if ( ( std::numeric_limits< ReturnType >::min() <= GivenValue ) &&
		   ( GivenValue <= std::numeric_limits< ReturnType >::max() ) )
			return boost::numeric_cast< ReturnType >( std::round( GivenValue ) );
		else
	  {
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << "The value " << GivenValue << " of the variable is outside "
									 << " the range of vales [" 
									 << std::numeric_limits< ReturnType >::min() << ", "
									 << std::numeric_limits< ReturnType >::max() << "] "
									 << "that can be stored in " << typeid( ReturnType ).name();
									 
		  throw std::domain_error( ErrorMessage.str() );
		}
	}

	// If the return type is a real type and the type of the variable is numeric 
	// then the worst that can happen is a lack of precision, and so the type can 
	// be directly cast into the destination type.

	template< class ReturnType, class Argument >
	typename std::enable_if< std::is_floating_point< ReturnType >::value && 
											     std::is_arithmetic< Argument >::value, 
													 ReturnType >::type
	SafeCast( const Argument & GivenValue )
	{
		return boost::numeric_cast< ReturnType >( GivenValue );
	}

	// When the return value is convertible to a string, all variable data types 
	// can be used.

	template< class ReturnType, class Argument >
	typename std::enable_if< 
							  std::is_convertible< ReturnType, 
																		 std::string >::value, std::string >::type
	SafeCast( const Argument & GivenValue )
	{ 
		std::ostringstream TheValue;
		
		TheValue << std::boolalpha;
		TheValue << GivenValue;
		
		return std::string( TheValue.str() );
	}

	// Similarly, if the return type is not a string but the value is a string,
	// then it is a simple matter of using the input operator on an stream.

	template< class ReturnType >
	ReturnType SafeCast( const std::string & GivenValue )
	{
		std::istringstream TheValueString( GivenValue );
		ReturnType Result;
		
		TheValueString >> Result;
		
		return Result;
	}

	// Boolean values must also be treated separately

	template< class ReturnType >
	typename std::enable_if< std::is_arithmetic< ReturnType >::value, 
													 ReturnType >::type
	SafeCast( const bool GivenValue )
	{
		return static_cast< ReturnType >( GivenValue );
	}
	
protected:
	
	const std::string Name;

  // The constructor simply stores the external variable name into this string.

  inline ValueElement( const std::string & GivenName )
  : Name( GivenName )
	{ }	
	
	// The type of a variable complicates the issue of reading out a generic 
	// variable value since it can be anything. The class holding the value 
	// must therefore be able to return the value as an Any type.
	
	virtual std::any GetValue( void ) = 0;
	
	// Finally, the actual conversion from a standard Any type to a given 
	// destination type is managed by a conversion template function that 
	// basically has to test every standard type and do the any cast on the 
	// match before performing the safe conversion to the desired result type.
  // 
	// When converting from the Any type, only the type info of the stored 
	// value is available. Type infos can only be compared using the equality 
	// operator, which implies a massive if-else chain to test all standard 
	// types, and one may need to test types that are not frequently used for 
	// a given problem before coming to the type stored in the Any variable. 
	//
	// The alternative approach taken here involves using an unordered map 
	// providing the conversion of the given and setting this in the value 
	// whose reference is given as a void pointer to have a unified, type-less 
	// interface.
	//
	// This has a memory overhead, but since the map is static it should be 
	// shared among all value elements, and hopefully the run-time performance 
	// offsets the memory penalty taken. In order to help setting up this map 
	// some macros are defined
	
	template< class ReturnType >
	ReturnType Convert( const std::any & TheValue )
	{
		static const std::unordered_map< std::type_index, 
				         std::function< ReturnType(const std::any & Value ) > > 
    AnyCast( {
			{ std::type_index(typeid( bool )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< bool >(Value) ); } },
			{ std::type_index(typeid( char )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< char >(Value) ); } },
			{ std::type_index(typeid( char16_t )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< char16_t >(Value) ); } },
		  { std::type_index(typeid( char32_t )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< char32_t >(Value) ); } },
		  { std::type_index(typeid( wchar_t )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< wchar_t >(Value) ); } },
		  { std::type_index(typeid( signed char )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< signed char >(Value) ); } },
		  { std::type_index(typeid( short int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< short int >(Value) ); } },
		  { std::type_index(typeid( int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< int >(Value) ); } },
	    { std::type_index(typeid( long int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< long int >(Value) ); } },
		  { std::type_index(typeid( long long int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< long long int >(Value) ); } },
		  { std::type_index(typeid( unsigned char )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< unsigned char >(Value) ); } },
		  { std::type_index(typeid( unsigned short int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< unsigned short int >(Value) ); } },
		  { std::type_index(typeid( unsigned int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< unsigned int >(Value) ); } },
		  { std::type_index(typeid( unsigned long int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< unsigned long int >(Value) ); } },
		  { std::type_index(typeid( unsigned long long int )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< unsigned long long int >(Value) ); } },
		  { std::type_index(typeid( float )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< float >(Value) ); } },
		  { std::type_index(typeid( double )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< double >(Value) ); } },
		  { std::type_index(typeid( long double )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< long double >(Value) ); } },
		  { std::type_index(typeid( std::string )), [this]( const std::any & Value )->ReturnType{ return SafeCast< ReturnType >( std::any_cast< std::string >(Value) ); } }
		} );

		// The Type ID of the received value will then be looked up in this map,
		// and if it exist, then the corresponding conversion function will be 
		// returned to convert the value (that must be given as argument to the 
		// conversion function). If there is no conversion for a given type, the 
		// lookup fails with an out of range exception which will be thrown again 
		// as a more understandable domain error.
		
		try
		{
			return AnyCast.at( TheValue.type() )(TheValue);
		}
		catch ( std::out_of_range & Error )
		{
			// The value type is not one of the standard types, and in this case 
			// it is only possible to throw an exception indicating this problem
			
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << "The type of the variable value " 
									 << TheValue.type().name() 
									 << " cannot be converted to the destination type "
									 << typeid( ReturnType ).name()
									 << " by the Convert() function";
									 
			 throw std::domain_error( ErrorMessage.str() );
		}
	}

public:
	
	// The export function takes a reference to the Google Protocol buffer 
	// message and sets the correct value field the current variable value.
	
	virtual void Export( ComputeUtilityRequest & Configuration ) const = 0;
	
	// Setting the value is relatively easy as the given value can be passed 
	// as an Any type and converted according to the right type of the variable 
	// value.
	
	virtual void Value( const std::any & GivenValue ) = 0;
	
	// Reading the value back from a generic value element is more difficult since
	// the destination type to return must be known, and the appropriate 
	// conversions made. It should be noted that this mechanism using Any is 
	// more expensive than calling the operator () directly on the variable 
	// object if that object is available. This is to be used with generic 
	// pointers. Fundamentally, it is just figuring out the type of the Any 
	// value, and then call the convert function to cast the value to the 
	// requested return type
	
	template< class ReturnType >
	inline ReturnType Value ( void )
	{
		return Convert< ReturnType >( GetValue() );
	}
	
	// The default constructor is disallowed
	
	ValueElement( void ) = delete;
	
	// The destructor does nothing to do, but it should be virtual since the 
	// class is abstract.
	
	virtual ~ValueElement()
	{ }
};


// -----------------------------------------------------------------------------
// Registry
// -----------------------------------------------------------------------------
//
// All variables are recorded depending on whether they are continuous or 
// discrete, and these two lists are maintained by the registry class. Only 
// the variables are allowed to use the registry from their constructors.
//
// The variables are stored according to the type of variables:
//
//   Discrete variables are typically defined over a set of distinct values
//   Continuous variables are typically continuous intervals over the type 
//      of the deliverable.

class Registry
{
private:
	
	std::mutex RegistryLock;
	
protected:
	
	std::list< ValueElement * > DiscreteVariables, 
															ContinuousVairables;
	
	// The type of the registration can be stated by using on of the provided 
	// variable classes cont
	
	enum class VariableClass
	{
		Discrete,
		Continuous
	};
	
	// The registration and removal of variables is done from the
	// constructor and destructor calling the following functions.
	
	inline void RegisterVariable( VariableClass VariableType, 
																ValueElement * TheVariable )
	{
		std::lock_guard< std::mutex > TheLock( RegistryLock );
		
		switch ( VariableType )
		{
			case VariableClass::Discrete: 
				DiscreteVariables.push_back( TheVariable );
				break;
			case VariableClass::Continuous:
				ContinuousVairables.push_back( TheVariable );
				break;
		}
	}
	
	inline void UnregisterVariable( VariableClass VariableType, 
																	ValueElement * TheVariable )
	{
		std::lock_guard< std::mutex > TheLock( RegistryLock );
		
		switch ( VariableType )
		{
			case VariableClass::Discrete:
				DiscreteVariables.remove( TheVariable );
				break;
			case VariableClass::Continuous:
				ContinuousVairables.remove( TheVariable );
				break;
		}
	}
	
	// Only the variable class is allowed to access these functions
	
	template< class DomainType, class Enable >
	friend class LASolver::Variable;

public:
	
	// The default constructor initialises the two lists
	
	Registry( void )
	: RegistryLock(), DiscreteVariables(), ContinuousVairables()
	{ }
};

// There is a global instance of this registry for the variables to use when 
// a variable is defined to allow each variable to register in the appropriate 
// class. This registry is the manager of the configuration, and ensures the 
// proper setting of variable values, evaluation of utility and solution to 
// continuous variables.
//
// However, all the problem variables will be defined as global variable 
// class, and there is no way to ensure that a global manager object will 
// be initialised before the variable instances. In order to ensure that 
// variables can register, the Manager is held in a smart pointer that is 
// initialised by the first Variable Value class that is instantiated 
// using the Create Manager function.

extern std::shared_ptr< Registry > Manager;

// The create manager function must be defined to create the manager instance
// The idea is that if some other class needs to expand the registry class by 
// inheriting it, a pointer to the derived object can be created instead of 
// just a registry pointer.

extern void CreateManager( void );

// -----------------------------------------------------------------------------
// Variable Value
// -----------------------------------------------------------------------------
//
// The actual variable value is a template for the type used to store the 
// variable value, and provide the interface to access this value

template< class ValueType >
class VariableValue : public ValueElement
{
private:
	
	ValueType TheValue;
	
protected:
	
	// The value of the variable can be readily returned as an Any type
	
	virtual std::any GetValue( void ) override
	{ return TheValue; }
	
public:
	
	// The value type is defined to be compatible with the STL containers
	
	using value_type = ValueType;

	// There are functions for getting and setting the value when the type of 
	// the variable is known. The version setting the value is virtual because 
	// variables may need to check the value against the domain of the variable.
	
	virtual void operator() ( ValueType GivenValue )
	{ TheValue = GivenValue;	}
	
	inline ValueType operator() ( void ) const
	{ return TheValue; }
		
	// The generic value function will first try to convert the given value to
	// the type of this variable, and if successful it will call the above 
	// operator to check the validity of the value against the domain ranges. 
	// If the conversion fails, it will reproduce the message of the bad any 
	// cast with the variable name added.
	
	virtual void Value( const std::any & GivenValue ) override
	{
		try
		{
			ValueType NewValue = Convert< ValueType >( GivenValue );
			this->operator()( NewValue );
		}
		catch ( std::domain_error & Error )
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << Error.what() << " for setting a value for the variable "
									 << Name;
									 
		  throw std::domain_error( ErrorMessage.str() );
		}
	}
	
	// There must be an initial value given for the value at construction time...
	
	inline VariableValue( const std::string & TheName, ValueType InitialValue )
	: ValueElement( TheName ), TheValue( InitialValue )
	{
		if ( ! Manager ) CreateManager();
	}
	
	// ...and therefore there should not be a default constructor and a 
	// virtual destructor doing nothing.
	
	VariableValue( void ) = delete;
	
	virtual ~VariableValue()
	{ }	
};

// -----------------------------------------------------------------------------
// Configuration variable
// -----------------------------------------------------------------------------
//
// The main purpose of the value class is to specify a generic interface to 
// the export function allowing it to store the variable value depending on 
// the type of value. 

template< class ValueType, class Enable = void >
class Variable;

// The first specialisation is for the integral types

template< class ValueType >
class Variable< ValueType,
  std::enable_if_t< std::is_integral< ValueType >::value > >
: public VariableValue< ValueType >
{
public:
	
	// The function to export this value to the configuration simply 
	// inserts the value into the set of integral values of the 
	// configuration. Note that the value can only be accessed via the 
	// () operator since it is protected by the variable value class.
	
	virtual void Export( ComputeUtilityRequest & Configuration ) const override
	{
		Configuration.mutable_intvars()->insert(
			IntMap::value_type( ValueElement::Name, this->operator()() )	);
	}
	
	// The constructor takes the name and the initial value of the variable and
	// passes this on to the variable value class.
	
	inline Variable( const std::string & TheName, ValueType InitialValue )
	: VariableValue< ValueType >( TheName, InitialValue )
	{ }
	
	// The default constructor is deleted, and there is a virtual destructor to 
	// ensure correct destruction of the base classes.
	
	Variable( void ) = delete;
	
	virtual ~Variable()
	{}
};

// The second type is for real valued variables

template< class ValueType >
class Variable< ValueType,
  std::enable_if_t< std::is_floating_point< ValueType >::value > >
: public VariableValue< ValueType >
{
public:

	// The function to export this value to the configuration simply 
	// inserts the value into the set of real values of the 
	// configuration. Note that the value can only be accessed via the 
	// () operator since it is protected by the variable value class.
	
	virtual void Export( ComputeUtilityRequest & Configuration ) const override
	{
		Configuration.mutable_realvars()->insert(
			RealMap::value_type( ValueElement::Name, this->operator()() )	 );
	}
	// The constructor takes the name and the initial value of the variable and
	// passes this on to the variable value class.
	
	inline Variable( const std::string & TheName, ValueType InitialValue )
	: VariableValue< ValueType >( TheName, InitialValue )
	{ }
	
	// The default constructor is deleted, and there is a virtual destructor to 
	// ensure correct destruction of the base classes.
	
	Variable( void ) = delete;
	
	virtual ~Variable()
	{}	
};

// The last specialisation is for the case where the variable type is a string 
// or can be converted to a string type.

template< class ValueType >
class Variable< ValueType,
  typename std::enable_if_t< 
				   std::is_convertible< ValueType, std::string >::value > >
: public VariableValue< ValueType >
{
public:
	
	// The function to export this value to the configuration simply 
	// inserts the value into the set of string values of the 
	// configuration. Note that the value can only be accessed via the 
	// () operator since it is protected by the variable value class.
	
	virtual void Export( ComputeUtilityRequest & Configuration ) const override
	{
		Configuration.mutable_stringvars()->insert(
			StringMap::value_type( ValueElement::Name, this->operator()() )	);
	}

	// The constructor takes the name and the initial value of the variable and
	// passes this on to the variable value class.
	
	inline Variable( const std::string & TheName, ValueType InitialValue )
	: VariableValue< ValueType >( TheName, InitialValue )
	{ }
	
	// The default constructor is deleted, and there is a virtual destructor to 
	// ensure correct destruction of the base classes.
	
	Variable( void ) = delete;
	
	virtual ~Variable()
	{}	
};

} // Name space Configuration	

/*==============================================================================

 Variables

==============================================================================*/
//
// The variable is a configuration variable defined by the domain type, and 
// it belongs to one of the registration classes. It is therefore 
// specialisations for the three main types of domains:
//
// 1. Intervals - always continuous
// 2. Sets - always discrete and an allocation automata
// 3. Set of intervals - hybrid variable and an allocation automata
//
// -----------------------------------------------------------------------------
// Intervals
// -----------------------------------------------------------------------------
//
// The interval is per definition a continuous range in the value type, and 
// it is countable if the value type is an integral value. It should be noted 
// that intervals are by definition defined only for numerical values, i.e.
// arithmetic types

template< class ValueType >
class Variable< Domain::Interval< ValueType >, 
								std::enable_if_t< std::is_arithmetic< ValueType >::value > >
: public Configuration::Variable< ValueType >
{
private:
	
	Domain::Interval< ValueType > TheDomain;
	
public:
	
	// The variable attributes are similar to the ones for the interval domains
	
	static constexpr bool Countable  = Domain::Interval< ValueType >::Countable;
	static constexpr bool Continuous = true;
	
	using value_type = ValueType;
	
	// The value return function is directly re-used
	
	using Configuration::Variable< ValueType >::operator();
	
	// Values can be set with the operator or with the value function for any 
	// type. The operator function will check that the given value fits in the 
	// domain and throw a standard out of range exception if it does not.
	
	virtual void operator() ( ValueType GivenValue ) override
	{
		if ( TheDomain.ElementQ( GivenValue ) )
			Configuration::Variable< ValueType >::operator()( GivenValue );
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << " The given variable value " << GivenValue 
									 << " is outside the domain of the interval ["
									 << TheDomain.lower() << ", " << TheDomain.upper()
									 << "] for variable " << Configuration::ValueElement::Name;
									 
		  throw std::out_of_range( ErrorMessage.str() );			
		}
	}
	
	// A continuous variable must be able to report the upper and lower bound
	// of the interval.
	
	inline ValueType LowerBound( void )
	{ return TheDomain.lower(); }
	
	inline ValueType UpperBound( void )
	{ return TheDomain.upper(); }
	
	// The main constructor takes the name of the variable, an instance of the 
	// domain (typically temporary), and an initial value of the variable. The 
	// configuration variable will just store the initial value, and so it has 
	// to be stored again using the above operator () to check that the initial 
	// value is within the given domain. Finally, the variable is inserted in 
	// the list of continuous variables.
	
	Variable( const std::string & TheName, 
						const Domain::Interval< ValueType > & GivenDomain, 
						ValueType InitialValue )
	: Configuration::Variable< ValueType >( TheName, InitialValue ),
	  TheDomain( GivenDomain )
	{
		this->operator()( InitialValue );
		Configuration::Manager->RegisterVariable( 
									 Configuration::Registry::VariableClass::Continuous, this );
	}
	
	// It is also possible to define the variable without the initial value, and
	// in this case it will be drawn randomly over the given interval. It should 
	// be noted that the random generator of the LA Framework supports intervals 
	// directly. The actual initialisation is delegated to the main constructor.
	
	Variable( const std::string & TheName, 
						const Domain::Interval< ValueType > & GivenDomain )
	: Variable( TheName, GivenDomain, Random::Number( GivenDomain ) )
	{	}
	
	// The default constructor and copy constructor are explicitly deleted. 
	// Not having a copy constructor means that the variable cannot be directly 
	// stored in an STL container.
	
	Variable( void ) = delete;
	Variable( const Variable & Other ) = delete;
	
	// The destructor is virtual to allow correct destruction of base classes
	
	virtual ~Variable()
	{ Configuration::Manager->UnregisterVariable( 
									 Configuration::Registry::VariableClass::Continuous, this ); }
};

// -----------------------------------------------------------------------------
// Sets
// -----------------------------------------------------------------------------
//
// The specialisation for sets is similar to the implementation for intervals,
// but differs in the error message and the registration with the configuration 
// manager. Furthermore, for sets the value type can be anything

template< class ValueType >
class Variable< Domain::Set< ValueType > >
: public Configuration::Variable< ValueType >
{
private:
	
	Domain::Set< ValueType > TheDomain;
	
public:
	
	// The variable attributes are defined as for sets
	
	static constexpr bool Countable  = true;
	static constexpr bool Continuous = true;
	
	using value_type = ValueType;
	
	// The value return function is directly re-used
	
	using Configuration::Variable< ValueType >::operator();
	
	// The operator to set the variable value will throw an out of range if 
	// the given value is not element in the set.

	virtual void operator() ( ValueType GivenValue ) override
	{
		if ( TheDomain.ElementQ( GivenValue ) )
			Configuration::Variable< ValueType >::operator()( GivenValue );
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << " The given variable value " << GivenValue 
									 << " is  not a member of the domain set for variable " 
									 << Configuration::ValueElement::Name;
									 
		  throw std::out_of_range( ErrorMessage.str() );			
		}
	}
	
	// The main constructor is similar to the one for intervals, and the initial
	// value will be stored a second time to allow the above operator to check 
	// that it is in the given set.
	
	Variable( const std::string & TheName, 
						const Domain::Set< ValueType > & GivenDomain, 
						ValueType InitialValue )
	: Configuration::Variable< ValueType >( TheName, InitialValue ),
	  TheDomain( GivenDomain )
	{
		this->operator()( InitialValue );
		Configuration::Manager->RegisterVariable( 
									 Configuration::Registry::VariableClass::Discrete, this );
	}
	
	// If the initial value is not given it is drawn as a random element of the 
	// set using the fact that the set is indexed: A random index in the range 
	// [0, Size) is drawn, and the element used to initialise the variable 
	// through delegation to the main constructor
	
	Variable( const std::string & TheName, 
						const Domain::Set< ValueType > & GivenDomain )
	: Variable( TheName, GivenDomain, 
							GivenDomain[ Random::Number( 0, GivenDomain.size() ) ] )
	{	}

	// The default constructor and copy constructor are explicitly deleted. 
	// Not having a copy constructor means that the variable cannot be directly 
	// stored in an STL container.
	
	Variable( void ) = delete;
	Variable( const Variable & Other ) = delete;
	
	// The destructor is virtual to allow correct destruction of base classes
	
	virtual ~Variable()
	{ Configuration::Manager->UnregisterVariable( 
									 Configuration::Registry::VariableClass::Discrete, this ); }
};

// -----------------------------------------------------------------------------
// Set of Intervals
// -----------------------------------------------------------------------------
//
// This is the most complex variable since the set is discrete, but the 
// individual intervals are continuous. The variable is therefore implemented 
// as a two-level variable. It is a discrete variable, and once a value is 
// assigned to the discrete variable, it creates a continuous variable with 
// the right interval as domain and with the same name as the variable. 
//
// When the configuration is reported, the discrete variable is mute and does 
// not report a value whereas the continuous variable will report the value 
// in the right sub-interval. 

template< class ValueType >
class Variable< Domain::Set< Domain::Interval< ValueType > > >
: public Configuration::Variable< 
				  typename Domain::Set< Domain::Interval< ValueType > >::Index > 
{
private:
						
	Domain::Set< Domain::Interval< ValueType > > TheDomain;
	
	// The secondary continuous variable for the selected subinterval is 
	// dynamically allocated whenever a new subinterval is selected. Note that 
	// this implies that the solution process must first fix the integer variables
	// and then subsequently solve the continuous optimisation problem.
	
	using IntervalVariableType = Variable< Domain::Interval< ValueType > >;
	
	std::shared_ptr< IntervalVariableType >	IntervalVariable; 
		
public:

	// The variable attributes are defined as for sets, but where the value type
	// is the index of the sub-interval containing the real value.
	
	static constexpr bool Countable  = true;
	static constexpr bool Continuous = true;
	
	using Index = typename Domain::Set< Domain::Interval< ValueType > >::Index;
	using value_type = Index;
	
	// The value return function is directly re-used
	
	using Configuration::Variable< Index >::operator();
	
	// Since the value in the configuration is only reported by the continuous 
	// sub-variable, the export function will do nothing.
	
  virtual void Export( ComputeUtilityRequest & Configuration ) const override
  { }
  
  // Setting the value means selecting an index in the range [0,..,Size) and
  // the operator for setting values simply checks this condition on the given 
  // value. If it is an allowed subinterval, the value is recorded as a value 
  // of this index variable, and the corresponding continuous interval variable
  // is created. Note that the initial value of the continuous variable will 
  // be drawn at random, and it is given the same name as this variable. Since
  // it is a standard variable implementation, it will have a valid export
  // function and report the value of this variable when the configuration 
  // variables are exported.
  
	virtual void operator() ( Index GivenValue ) override
	{
		if ( GivenValue < TheDomain.NumberOfValues() )
		{
			Configuration::Variable< Index >::operator()( GivenValue );
			IntervalVariable = std::make_shared< IntervalVariableType >(
				Configuration::ValueElement::Name, TheDomain.SubInterval( GivenValue ) 
			);
		}
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << " The given interval index " << GivenValue 
									 << " is  larger than the number of subintervals " 
									 << TheDomain.NumberOfValues() << " for variable "
									 << Configuration::ValueElement::Name;
									 
		  throw std::out_of_range( ErrorMessage.str() );			
		}
	}

	// The main constructor takes the name, the domain, and an initial value. 
	// The initial value is a value in one of the subintervals of the domain, 
	// and the interval variable is then created for this subinterval. If the 
	// given value does not correspond to a legal value of the domain, a random
	// will be chosen instead. 
	
	Variable( const std::string & TheName, 
						const Domain::Set< Domain::Interval< ValueType > > & GivenDomain, 
					  const ValueType InitialValue )
	: Configuration::Variable< Index >( TheName, Index(0) ),
	  TheDomain( GivenDomain )
	{
		try
		{
			Index IntervalIndex = TheDomain.FindInterval( InitialValue );
			this->operator()( IntervalIndex );
			IntervalVariable->operator()( InitialValue );
		}
		catch ( std::out_of_range & Error )
		{
			// Since the standard operator selecting the subinterval creates a 
			// continuous interval pointer on a random number in the selected interval
			// it is sufficient just to call this operator with a random interval to
			// complete the random initialisation.
			
			this->operator()( Random::Number( 
				typename Domain::Set< Domain::Interval< ValueType > >::Index(0), 
				TheDomain.NumberOfValues() ) 
			);			
		}
		
		// This discrete set index variable can the be registered with the 
		// configuration master
		
		Configuration::Manager->RegisterVariable( 
									 Configuration::Registry::VariableClass::Discrete, this );
	}
	
	// In the case a random initialisation is desired in the first place, a 
	// random initial value over the range of values in the domain is proposed 
	// to the main constructor. If the subintervals of the domain have not too 
	// large gaps between them, it is likely that the proposed value will be in 
	// one of the subintervals and kept by the main constructor, otherwise it 
	// will make its own, legal random initialisation.
	
	Variable( const std::string & TheName, 
						const Domain::Set< Domain::Interval< ValueType > > & GivenDomain )
	: Variable( TheName, GivenDomain, 
							Random::Number( TheDomain.lower(), TheDomain.upper() ) )
	{}
	
		// The default constructor and copy constructor are explicitly deleted. 
	// Not having a copy constructor means that the variable cannot be directly 
	// stored in an STL container.
	
	Variable( void ) = delete;
	Variable( const Variable & Other ) = delete;
	
	// The destructor is virtual to allow correct destruction of base classes,
	// and it checks out with the manager. 
	
	virtual ~Variable()
	{ Configuration::Manager->UnregisterVariable( 
									 Configuration::Registry::VariableClass::Discrete, this ); }
};


}      // Name space LA Solver
#endif // LA_SOLVER_VARIABLES
