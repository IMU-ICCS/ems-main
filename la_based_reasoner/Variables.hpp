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
#include <iomanip>                            // Stream formatting
#include <stdexcept>                          // For standard exceptions
#include <limits>                             // For numeric limits on types
#include <memory>                             // For smart pointers
#include <boost/numeric/conversion/cast.hpp>  // Safe numeric casts
#include <algorithm>                          // For min and max

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

// The Constraint registry must also be defined in order to be allowed the
// access to set the variable values prior to evaluating the constraints

class Constraints;

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
// the value class is defined as an unsigned int, it may be stored in the Any
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

public:

	// The name of the variable is assigned by the constructor and cannot be
	// changed after construction. Hence it can be a read-only field.

	const std::string Name;

  // The constructor simply stores the external variable name into this string.

  inline ValueElement( const std::string & GivenName )
  : Name( GivenName )
	{ }

	// The type of a variable complicates the issue of reading out a generic
	// variable value since it can be anything. The class holding the value
	// must therefore be able to return the value as an Any type. However, in
	// order to use this value, the right conversion must be applied, and this
	// can be rather complicated, see the Convert function below. It is however
	// always possible to convert the contained value to a string and there is
	// a separate required virtual function for that.

	virtual std::any    GetValue( void ) const = 0;
	virtual std::string GetValueString( void ) const = 0;

	// In the same way there are functions returning the lower and upper bound
	// of the variable. For continuous variables this is straight forward,
	// and for discrete variables it corresponds to the maximal or minimal value
	// of the set.

	virtual std::any GetUpperBound( void ) = 0;
	virtual std::any GetLowerBound( void ) = 0;

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

protected:

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

	template< typename ReturnType >
	inline ReturnType Value( void )
	{
		return Convert< ReturnType >( GetValue() );
	}

	// In the same way there are templates to return the bounds of the variable

	template< typename ReturnType >
	inline ReturnType LowerBound( void )
	{
		return Convert< ReturnType>( GetLowerBound() );
	}

	template< typename ReturnType >
	inline ReturnType UpperBound( void )
	{
		return Convert< ReturnType >( GetUpperBound() );
	}

	// The default constructor is disallowed

	ValueElement( void ) = delete;

	// The destructor does nothing to do, but it should be virtual since the
	// class is abstract.

	virtual ~ValueElement()
	{ }
};


// -----------------------------------------------------------------------------
// Variable registry
// -----------------------------------------------------------------------------
//
// All variables are recorded depending on whether they are continuous or
// discrete, and these two types are defined as enumerations

enum class VariableType
{
	Discrete,
	Continuous
};

// The variables are stored depending on their type in classes defining the
// type specific interface and the underlying storage and other features of
// the variable registry. This is just a forward declaration that will be
// further defined after the common variable registry.

template< VariableType RegistryType >
class Variables;

// The variable registry implements the common functionality of the two types
// of variable storage, and provides a common interface to be used by the
// variables and the constraints.

class VariableRegistry
{
protected:

	// The registration and removal of variables is done from the variable's
	// constructor and destructor calling the following functions.

	virtual void NewVariable( ValueElement * TheVariable ) = 0;
	virtual void RemoveVariable( ValueElement * TheVariable ) = 0;

	// The variables stored can be assigned values one by one, but normally they
	// will all be assigned values in one go by providing a vector of values.
	// However, a variable values can be of many different storage types, and
	// this is the reason why the function Value used to set the value of a
	// Value element takes generic value. The same approach is taken here with
	// a generic value vector provided

public:

	using ValueVector = std::vector< std::any >;

	// There is also a boolean function to check if the registry has any
	// variables, and another to report the number of variables in the
	// registry.

	virtual bool empty( void ) = 0;
	virtual ValueVector::size_type	NumberOfVariables( void ) = 0;

	// Then there is a function to set the variables given a vector of values,
	// and this must be provided by the derived classes knowing how the variables
	// are stored.

	virtual	void SetValues( ValueVector & Values ) = 0;

  // The constructor is left as the default constructor doing nothing.
	// The virtual destructor will call the delete operation to ensure
	// that the static registries are cleared.

	virtual ~VariableRegistry()
	{ }
};

// The two specialisations for the variable stores can now be defined derived
// from the variable registry and implementing the methods that are different
// depending on the class of the stored variables.

template< >
class Variables< VariableType::Discrete >
: virtual public VariableRegistry
{
private:

	// There is a global pointer to this registry as there can be only one
	// discrete variable registry for a problem.

	static Variables< VariableType::Discrete > *  Registry;

	// The Constraints registry is allowed to access this pointer in order to
	// set the variable values prior to evaluating the constraints.

	friend class LASolver::Constraints;

	// There is a small function to register a variable without having access
	// to the registry.

public:

	static bool Register( ValueElement * TheVariable )
	{
		if ( Registry != nullptr )
		{
			Registry->NewVariable( TheVariable );
			return true;
		}
		else
		{
			std::ostringstream ErrorMessage;

			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "Variable " << TheVariable->Name << " was created before "
									 << "the discrete variable registry was created.";

		  throw std::logic_error( ErrorMessage.str() );
		}
	}

	// Similarly, there is a static function to de-register a variable. This
	// will just ignore the request if the registry has already been cleared.

	static void Remove( ValueElement * TheVariable )
	{
		if ( Registry != nullptr )
			Registry->RemoveVariable( TheVariable );
	}

protected:

	// By default it only gives access to the functions defined as standard.
	// However, should a derived class wish to save a pointer to a derived
	// class, then this is possible via the update function. Note that there
	// can only be one registry for discrete variables, and if the pointer
	// is already set a logic error exception will be thrown if the class is
	// not derived from this class and the Discrete pointer is set to this
	// base class pointer.

	template< class DerivedClass >
	void UpdateRegistryPointer( DerivedClass * DerivedRegistry )
	{
		static_assert( std::is_base_of< Variables< VariableType::Discrete >,
									                  DerivedClass >::value,
		  "Cannot update the discrete registry pointer with the given class" );

		Variables< VariableType::Discrete > * BaseOfDerived =
			dynamic_cast< Variables< VariableType::Discrete > * >( DerivedRegistry );

		if ( ( Registry == nullptr) || ( Registry == BaseOfDerived) )
			Registry = DerivedRegistry;
		else
		{
			std::ostringstream ErrorMessage;

			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "The discrete variable registry has already been "
									 << "defined and only one discrete variable registry "
									 << "can exist for the solver";

		  throw std::logic_error( ErrorMessage.str() );
		}
	}

	// The default constructor stores a pointer to his class as the variable
	// registry by using the above update function that will throw if the static
	// registry pointer is not null.

	inline Variables( void )
	{	UpdateRegistryPointer( this ); }

public:

	// Finally, the variable store provides a virtual destructor to ensure
	// correct removal of all classes. Theoretically, it could be that the
	// problem will be recreated with some other variable registry, and so
	// the global pointer is cleared for potential re-use.

	virtual ~Variables( void )
	{  Registry = nullptr; }
};

// A very similar set of definitions are given for the continuous variables
// where the variable values are given as a vector of doubles.

template<>
class Variables< VariableType::Continuous > : virtual public VariableRegistry
{
private:

	// The continuous variable registry defines and controls the global pointer

	static Variables< VariableType::Continuous > * Registry;

	// The Constraints registry is allowed to access this pointer in order to
	// set the variable values prior to evaluating the constraints.

	friend class LASolver::Constraints;

	// In the same way as for the discrete variable registry, continuous domain
	// variables should register with a dedicated static function that will
	// throw a logic error if the variables are created before the registry.

public:

	static bool Register( ValueElement * TheVariable )
	{
		if ( Registry != nullptr )
		{
			Registry->NewVariable( TheVariable );
			return true;
		}
		else
		{
			std::ostringstream ErrorMessage;

			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "Variable " << TheVariable->Name << " was created before "
									 << "the continuous variable registry was created.";

		  throw std::logic_error( ErrorMessage.str() );
		}
	}

	// This function is again matched by a function to remove a variable.

	static void Remove( ValueElement * TheVariable )
	{
		if ( Registry != nullptr )
			Registry->RemoveVariable( TheVariable );
	}

protected:

	// By default it only gives access to the functions defined as standard.
	// However, should a derived class wish to save a pointer to a derived
	// class, then this is possible via the update function. Note that there
	// can only be one registry for discrete variables, and if the pointer
	// is already set a logic error exception will be thrown if the class is
	// not derived from this class and the Discrete pointer is set to this
	// base class pointer.

	template< class DerivedClass >
	void UpdateRegistryPointer( DerivedClass * DerivedRegistry )
	{
		static_assert( std::is_base_of< Variables< VariableType::Continuous >,
									                  DerivedClass >::value,
		  "Cannot update the continuous registry pointer with the given class" );

		Variables< VariableType::Continuous > * BaseOfDerived =
		dynamic_cast< Variables< VariableType::Continuous > * >( DerivedRegistry );

		if ( ( Registry == nullptr) || ( Registry == BaseOfDerived) )
			Registry = DerivedRegistry;
		else
		{
			std::ostringstream ErrorMessage;

			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
			             << "The continuous variable registry has already been "
									 << "defined and only one continuous variable registry "
									 << "can exist for the solver";

		  throw std::logic_error( ErrorMessage.str() );
		}
	}

	// The constructor is protected so that this class must be inherited and by
	// default it only sets the global registry pointer to the instantiation
	// of this class.

	inline Variables( void )
	{ UpdateRegistryPointer( this ); }

public:

	// The destructor is virtual to allow the correct invocation of the derived
	// class destructor. The global registry pointer is removed as a safety
	// measure in case it is accessed after the registry has been closed.

	virtual ~Variables( void )
	{ Registry = nullptr; }
};

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

public:

	// The value of the variable can be readily returned as an Any type

	virtual std::any GetValue( void ) const override
	{ return TheValue; }

	// Converting the value to a string is best done by the overloaded stream
	// operators. The only thing that needs attention is the precision of
	// real numbers to avoid loosing precision.

	virtual std::string GetValueString( void ) const override
	{
		std::ostringstream FormattedValue;

		if constexpr ( std::is_floating_point_v< ValueType > )
		  FormattedValue << std::setprecision(
											  std::numeric_limits< ValueType >::digits10 + 1 );

		FormattedValue << TheValue;

		return FormattedValue.str();
	}

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

			ErrorMessage << Error.what() << " in setting a value for " << Name;

		  throw std::domain_error( ErrorMessage.str() );
		}
	}

	// There must be an initial value given for the variable at construction time,
	// and if the configuration manager has not been instantiated, this is the
	// first variable being defined and it should initialise the variable manager
	// and the constraint registries for both equality and inequality constraints.

	inline VariableValue( const std::string & TheName,
												const ValueType InitialValue )
	: ValueElement( TheName ), TheValue( InitialValue )
	{	}

	inline VariableValue( const VariableValue & Other )
	: ValueElement( Other.Name ), TheValue( Other.TheValue )
	{}

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

} // END Name space Configuration

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
public:

	// The variable attributes are similar to the ones for the interval domains

	static constexpr bool Countable  = Domain::Interval< ValueType >::Countable;
	static constexpr bool Continuous = true;

	using value_type = ValueType;


private:

	Domain::Interval< ValueType > TheDomain;

protected:

	// The generic functions for obtaining the range of the domain should be
	// protected as they are not supposed to be used directly, but through the
	// conversion functions of the Value Element.

	virtual std::any GetUpperBound( void ) override
	{ return TheDomain.upper(); }

	virtual std::any GetLowerBound( void ) override
	{ return TheDomain.lower(); }

public:

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
	// of the interval. The lower case forms can be used directly if one knows
	// the return type. Otherwise, one has to resort to the standard any type
	// conversion supported by the Value Element class.

	inline ValueType upper( void )
	{ return TheDomain.upper(); }

	inline ValueType lower( void )
	{ return TheDomain.lower(); }

	// The main constructor takes the name of the variable, an instance of the
	// domain (typically temporary), and an initial value of the variable. The
	// configuration variable will just store the initial value, and so it has
	// to be stored again using the above operator () to check that the initial
	// value is within the given domain. Finally, the variable is registered as
	// a continuous variable.

	Variable( const std::string & TheName,
						const Domain::Interval< ValueType > & GivenDomain,
						ValueType InitialValue )
	: Configuration::Variable< ValueType >( TheName, InitialValue ),
	  TheDomain( GivenDomain )
	{
		this->operator()( InitialValue );

		Configuration::Variables<
			Configuration::VariableType::Continuous >::Register( this );
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
	{
		Configuration::Variables<
			Configuration::VariableType::Continuous >::Remove( this );
	}
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
public:

	// The variable attributes are defined as for sets

	static constexpr bool Countable  = true;
	static constexpr bool Continuous = true;

	using value_type = ValueType;

private:

	Domain::Set< ValueType > TheDomain;

protected:

	// Set domains are not stored as sets but as vectors, and it is therefore
	// more complicated to find the upper and lower bound of the set since it
	// must be looked up in each case. However, the domain stores its maximum
	// and minimum value, and so it is just to report these.

	virtual std::any GetUpperBound( void ) override
	{ return TheDomain.upper(); }

	virtual std::any GetLowerBound( void ) override
	{ return TheDomain.lower(); }


public:

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

		Configuration::Variables<
			Configuration::VariableType::Discrete >::Register( this );
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
	{
		Configuration::Variables<
			Configuration::VariableType::Discrete >::Remove( this );
	}
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
public:

	// The variable attributes are defined as for sets, but where the value type
	// is the index of the sub-interval containing the real value.

	static constexpr bool Countable  = true;
	static constexpr bool Continuous = true;

	using Index = typename Domain::Set< Domain::Interval< ValueType > >::Index;
	using value_type = ValueType;

private:

	Domain::Set< Domain::Interval< ValueType > > TheDomain;

	// The secondary continuous variable for the selected subinterval is
	// dynamically allocated whenever a new subinterval is selected. Note that
	// this implies that the solution process must first fix the integer variables
	// and then subsequently solve the continuous optimisation problem.

	using IntervalVariableType = Variable< Domain::Interval< ValueType > >;

	std::shared_ptr< IntervalVariableType >	IntervalVariable;

protected:

	// The upper and lower bounds are directly returned from the domain and
	// correspond to the supremum of the upper bounds of all intervals and the
	// infimum of all intervals respectively.

	virtual std::any GetUpperBound( void ) override
	{ return TheDomain.upper(); }

	virtual std::any GetLowerBound( void ) override
	{ return TheDomain.lower(); }

public:

	// Since the value in the configuration is only reported by the continuous
	// sub-variable, the export function will do nothing.

  virtual void Export( ComputeUtilityRequest & Assignments ) const override
  { }

  // The value return function is returning the value held for the selected
	// sub interval since that is the true value of the variable. This because
	// the variable will be used in constraints, and at that point the internal
	// interval variable is not defined, i.e. it has no external presence, but
	// it is the value that should be used when evaluating the constraints.

	inline ValueType operator()( void )
	{
		return IntervalVariable->operator()();
	}

  // It should be noted that this variable be seen internally to the solver
  // as a discrete variable. Hence the solver will never assign a continuous
  // value to this variable. The solver will see the interval variable as any
  // other interval variable and assign values directly to this. It is therefore
  // no need to provide a function to set the continuous value.
	//
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
		if ( Configuration::Variable< Index >::operator()() == GivenValue )
			return;
		else if ( GivenValue < TheDomain.NumberOfValues() )
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
	//
	// Note that the default value of the discrete variable is set to the number
	// of subintervals since this is not a legal index (one after the end of the
	// legal index range 0..n-1). This ensures that the continuous variable will
	// be correctly created by as the initial interval index should never match
	// this number.

	Variable( const std::string & TheName,
						const Domain::Set< Domain::Interval< ValueType > > & GivenDomain,
					  const ValueType InitialValue )
	: Configuration::Variable< Index >( TheName, GivenDomain.NumberOfValues() ),
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
		// variable registry

		Configuration::Variables<
			Configuration::VariableType::Discrete >::Register( this );
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
	{
		Configuration::Variables<
			Configuration::VariableType::Discrete >::Remove(this);
	}
};


}      // Name space LA Solver
#endif // LA_SOLVER_VARIABLES
