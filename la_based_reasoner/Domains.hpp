/*==============================================================================
Domains

The domain is a fundamental property of any variable or metric. It defines 
the possible values that can be assigned to the variable. There are two 
classes of domains that can be identified:

  Interval 
	  This is a continuous range from a lower bound to an upper bound. It only 
	  supports numerical types. If the type is a real number, then the interval 
	  is uncountable, however if the type is an integral type the interval is 
	  equivalent to a countable, sorted set of discrete values.
  Set
	  A discrete set of distinct values. It can be strings, numbers, or 
	  non-overlapping intervals.

Author and Copyright: Geir Horn, 2017
License: LGPL 3.0
==============================================================================*/

#ifndef LA_SOLVER_DOMAINS
#define LA_SOLVER_DOMAINS

#include <vector>                               // To implement the Set type
#include <set>                                  // To ensure uniqueness
#include <list>                                 // Standard lists
#include <boost/numeric/interval.hpp>           // To implement Interval
#include <type_traits>                          // To support meta programming
#include <algorithm>                            // Functions for containers
#include <iterator>                             // To iterate over containers
#include <boost/iterator/counting_iterator.hpp> // To iterate intervals
#include <limits>                               // Limits for standard types
#include <boost/numeric/conversion/cast.hpp>    // Safe numeric casts
#include <stdexcept>                            // Standard exceptions
#include <sstream>                              // To format nice error messages
#include <initializer_list>                     // Discrete set initialisation
#include <utility>                              // For safe forward

namespace LASolver::Domain
{	
/*==============================================================================

 Intervals

==============================================================================*/
//
// It is necessary to distinguish between floating point intervals and integer
// type intervals. The Enable type is used to make the definition based on 
// whether the type is integral or a real number.

template< class ValueType, class Enable = void >
class Interval;

// -----------------------------------------------------------------------------
// Real valued intervals
// -----------------------------------------------------------------------------
//
// When the value type is real, the implementation is simple because it is 
// fundamentally only a Boost interval supporting the needed interface functions
// to make it interchangeable with other domains.

template< class ValueType >
class Interval< ValueType, 
	typename std::enable_if_t< std::is_floating_point< ValueType >::value > >
: public boost::numeric::interval< ValueType >
{
public:
	
	// It is best practice to check if the interval is countable before trying 
	// to use any element wise methods. Note that this should be done with a 
	// compile time test using if-constexpr and therefore a type level definition
	// is given so that one can test the value of countable like any other type 
	// trait.

	static constexpr bool Countable = false;
	
	// All intervals are by definition continuous, and this is defined as a 
	// to allow compile time verification
	
	static constexpr bool Continuous = true;
	
	// The standard type definitions. Note that the index type is equal to the 
	// value type since it needs to support either infinity or not a number
	
	using value_type = ValueType;
	using Index      = ValueType;
	
	//
	// SIZE RELATED FUNCTIONS
	//
	// Some of the public functions from the interval is simply reused
	
	using boost::numeric::interval< ValueType >::upper;
	using boost::numeric::interval< ValueType >::lower;
	
	// All the boost interval functions are supported since the base class is 
	// publicly inherited, and in addition there is a size function. It is a 
	// matter of definition whether this should return the length of the interval
	// or infinity or Not-a-Number.
	
	inline Index size( void ) const
	{	return upper()-lower();	}
	
	// There is a corresponding function to check the number of values in the 
	// range, and this will not make sense unless the value type supports either 
	// infinity or not a number.
	
	inline Index NumberOfValues( void ) const
	{
		if constexpr ( std::numeric_limits< Index >::has_infinity )
			return std::numeric_limits< Index >::infinity();
		else if constexpr ( std::numeric_limits< Index >::has_quiet_NaN )
			return std::numeric_limits< Index >::quiet_NaN();
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << "Intervals of value type " << typeid( ValueType ).name()
									 << " are not countable";
									 
		  throw std::logic_error( ErrorMessage.str() );
		}
	}
	
	//
	// ELEMENT RELATED FUNCTIONS
	//
	// There is a simple function to test if a given value is a part of the 
	// interval. 
	
	inline bool ElementQ( const ValueType & TheValue ) const
	{
		return ( lower() <= TheValue ) && ( TheValue <= upper() );
	}
	
	//
	// CONSTRUCTORS
	//
	// The standard constructor simply takes the lower bound and the upper bound 
	// of the interval. 
	
	inline Interval( const ValueType & LowerBound, const ValueType & UpperBound )
	: boost::numeric::interval< ValueType >( LowerBound, UpperBound )
	{ }
	
	// The copy constructor simply extracts the bounds and delegates to the 
	// standard constructor the initialisation. Given that most other numerical 
	// types can be cast into a real type, it is not needed that the interval to
	// copy from is defined on the same value type as this interval, and an 
	// explicit cast is therefore done on the values for the bounds.
	
	template< class OtherValueType, 
						typename std::enable_if_t< 
										 std::is_arithmetic< OtherValueType >::value > = 0 >
	Interval( const Interval< OtherValueType > & Other )
	: Interval( static_cast< ValueType >( Other.lower() ), 
							static_cast< ValueType >( Other.upper() ) )
	{ }
	
	// To offer the same interface as the Set it is also necessary to be able 
	// to construct the interval from a container of elements. In this case it 
	// is just to pick out the largest and the smallest element. It is however 
	// necessary to verify that the provided value type of the iterators can be
	// converted to a real number. It should be noted that the search for the 
	// maximum and minimum elements return iterators, and these must be 
	// de-referenced to the given value. Since the full scan of the given range 
	// will be performed twice, it is a relatively costly operation, but 
	// feasible.
	
	template< class IteratorType, 
						typename std::enable_if_t< 
										 std::is_arithmetic< 
											 typename IteratorType::value_type >::value > = 0 >
	Interval( IteratorType Begin, IteratorType End )
	: Interval( static_cast< ValueType >( *std::min_element( Begin, End ) ), 
							static_cast< ValueType >( *std::max_element( Begin, End ) ) )
	{ }
	
	// The default constructor is deleted to prevent void intervals to be created
	// by accident.
	
	Interval( void ) = delete;
};

// -----------------------------------------------------------------------------
// Integral interval
// -----------------------------------------------------------------------------
//
// An integral interval is different for two reasons: it is countable, and it 
// is therefore possible to iterate over the elements of the interval. Secondly,
// in cannot be constructed from real numbered intervals or with real numbers
// because of rounding or truncation to the discrete domain of integral values.
//
// The domain class has an identical interface to the Set class and can be
// used interchangeably, although it keeps the internal representation of an 
// interval to support interval arithmetic.

template< class ValueType >
class Interval< ValueType, 
	typename std::enable_if_t< std::is_integral< ValueType >::value > >
: public boost::numeric::interval< ValueType >
{
public:
	
	// As for the real number implementation there is a definition to test 
	// at compile time if this is a countable domain or not.
	
	static constexpr bool Countable = true;
	
	// It is debatable if an integer interval is continuous or discrete. However,
	// it can be seen as continuous over the domain of the integral type. This
	// is to say that for integral numbers there is nothing between two 
	// adjacent numbers. Hence, the interval is continuous in adjacent numbers, 
	// although the numbers themselves are discrete points in a real number 
	// space.
	
	static constexpr bool Continuous = true;
	
	// The standard types are defined as before, however the index type is 
	// set to equal the index type of a vector having the same underlying type 
	// definition as this domain.
	
	using value_type = ValueType;
	using Index      = typename std::vector< ValueType >::size_type;

	//
	// SIZE RELATED FUNCTIONS
	//
	// Some of the public functions from the interval is simply reused
	
	using boost::numeric::interval< ValueType >::upper;
	using boost::numeric::interval< ValueType >::lower;

  // The size function is actually meaningful in this case. However, it could 
	// be that the size of the interval is longer than supported by the index 
	// type, and in this case a standard out of range exception is thrown.
	
	inline Index size( void ) const
	{
		ValueType IntervalWidth = upper()-lower()+1;
		
		if ( IntervalWidth <= std::numeric_limits< Index >::max() )
			return IntervalWidth;
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << "The length of the interval from " << lower() << " to "
									 << upper() << "(" << IntervalWidth << ") is larger than "
									 << "the largest value that can be supported by the Index "
									 << "type (" << std::numeric_limits< Index >::max() << ")";
									 
		  throw std::out_of_range( ErrorMessage.str() );
		}			
	}
	
	inline Index NumberOfValues( void ) const
	{ return size(); }
	
	//
	// ELEMENT RELATED FUNCTIONS
	//
	// Testing if a value is a part of the interval is identical to the way 
	// it is done for the real valued intervals.
	
	inline bool ElementQ( const ValueType & TheValue ) const
	{
		return ( lower() <= TheValue ) && ( TheValue <= upper() );
	}
	
	// A particular element in the range should be returned by the bracket 
	// operator provided that the argument is within the size of the range
	// defined. Note that the first element is at index = 0, and the last is 
	// at Size-1. It will throw an out of range exception if this condition is 
	// not fulfilled.

	inline ValueType operator[] ( const Index & idx ) const
	{
		ValueType Result = lower() + idx;
		
		// Then the validity of the result is verified to ensure that it did not 
		// wrap around and that it stays within the interval.
		
		if ( ( idx <= std::numeric_limits< ValueType >::max() - lower() ) &&
				 ( Result <= upper() ) )
			return Result;
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << "Index" << idx << " given to operator [] is too large "
									 << "to represent one of the " << upper()-lower() 
									 << " numbers between " << lower() << " and " << upper();
			
			throw std::out_of_range( ErrorMessage.str() );
		}
	}

	//
	// ITERATOR
	//	
	// An iterator is provided to allow stepping through the elements of the 
	// range. However, the Boost counting iterator provides the necessary 
	// functionality so only the iterator creating functions needs to be provided
	// a specialised boost iterator adapter that can readily be reused. 
	
	using const_iterator = boost::counting_iterator< ValueType, 
																						       std::forward_iterator_tag, 
																									 ValueType >;

  inline const_iterator cbegin( void ) const
	{	return const_iterator( lower() );	}
	
	inline const_iterator cend( void ) const
	{ return const_iterator( upper() ); }
	
 	//
	// CONSTRUCTORS
	//
	// The standard constructor just initialises the interval
	
	inline Interval( const ValueType & LowerBound, const ValueType & UpperBound )
	: boost::numeric::interval< ValueType >( LowerBound, UpperBound )
	{ }
	
  // The copy constructor is similar to the one for the real interval, but in 
  // this case only intervals based on an integral type can be accepted. The 
  // assignment can only be done if the interval can be contained within the 
  // numerical range of the value type, in all other cases an out of range 
  // exception is thrown.
  
 	template< class OtherValueType, 
						typename std::enable_if_t< 
										 std::is_integral< OtherValueType >::value > = 0 >
	Interval( const Interval< OtherValueType > & Other )
	: boost::numeric::interval< ValueType >()
	{ 		
		if( ( std::numeric_limits< ValueType >::min() <= Other.lower() ) && 
				( Other.max() <= std::numeric_limits< ValueType >::max() )		  )
			assign( static_cast< ValueType >( Other.lower() ), 
							static_cast< ValueType >( Other.upper() ) );
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
									 << "The given interval [" << Other.lower() << ", " 
									 << Other.upper() << " is not a subset of the interval that "
									 << "can be contained in " << typeid( ValueType ).name() 
									 << " which is [" << std::numeric_limits< ValueType >::min()
									 << ", " << std::numeric_limits< ValueType >::max() << "]";
									 
		  throw std::out_of_range( ErrorMessage.str() );
		}
	}
	
	// The iterator based constructor is even more challenging, but the 
	// construction can be delegated to the copy constructor by the trick of 
	// using a temporary interval defined for the value type of the iterator.

	template< class IteratorType, 
						typename std::enable_if_t< 
										 std::is_integral< 
											 typename IteratorType::value_type >::value > = 0 >
	Interval( IteratorType Begin, IteratorType End )
	: Interval( Interval< typename IteratorType::value_type >( 
							*std::min_element(Begin, End), *std::max_element(Begin, End) ) )
	{ }

	// The default constructor is deleted to ensure that one of the explicit 
	// constructors must be used.
	
	Interval( void ) = delete;
	
};

/*==============================================================================

 Sets

==============================================================================*/
//
// Discrete domains are fundamentally sets. However, they are stored in vectors
// because it may be necessary to directly access elements not by value but 
// by index in the set. This makes the initialisation more laborious since it 
// is necessary to remove duplicates of all elements upon construction. 

template< class ValueType >
class Set : protected std::vector< ValueType >
{
private:
	
	typename std::vector< ValueType >::iterator MinElement, MaxElement;
	
public:
	
	// The set is by definition countable
	
	static constexpr bool Countable = true;
	
	// Even though one can use the same argument for a set as for an integral 
	// interval by indexing the set with a continuous interval of integers, 
	// it is by default not indexed and it is therefore taken as discrete 
	// in this context.
	
	static constexpr bool Continuous = false;

	// The standard types are defined as before, however the index type is 
	// set to equal the index type of the vector.
	
	using value_type = ValueType;
	using Index      = typename std::vector< ValueType >::size_type;

	// The vector is declared as protected because it should not be directly 
	// accessed. However, one may well want to parse its elements by constant 
	// iterators.
	
	using std::vector< ValueType >::cbegin;
	using std::vector< ValueType >::cend;
	
	//
	// SIZE RELATED FUNCTIONS
	//
	// The upper and lower bounds have to be stored for the domain since it may 
	// not be sorted.
	
	inline ValueType lower( void ) const
	{ return *MinElement; }
	
	inline ValueType upper( void ) const
	{ return *MaxElement; }
	
	// The size functions are in this case trivial as the first is provided by 
	// the vector, and the second just calls the first.
	
	using std::vector< ValueType >::size;

	inline Index NumberOfValues( void ) const
	{ return size(); }
	
	//
	// ELEMENT RELATED FUNCTIONS
	//
  // Testing if an element is a part of the domain might in the worst case 
  // involve traversing the full vector
  
  inline bool ElementQ( const ValueType & TheValue ) const
	{
		return std::find( cbegin(), cend(), TheValue ) != cend();
	}
	
	// One could think that the element access operator can be reused from the 
	// vector, but the standard version does not check the range of the index. 
	// Hence, the operator is simply an encapsulation of the vector's "at" 
	// function
	
	inline ValueType operator[] ( const Index & idx ) const
	{ return at( idx ); }
	
 	//
	// CONSTRUCTORS
	//
	// There are fundamentally three ways to give a set of values to the domain:
	// They can be given as an initialisation list, they can be given in form 
	// of another container (i.e. as two iterators), or they can be given in 
	// terms of variadic arguments. In all cases duplicates must be removed and
	// the resulting vector stored. There is therefore a helper function 
	// taking a list of values whose value type must be the same as the value 
	// type of this Set, and stores the duplicate free list  to the local vector
	
protected:
	
	// Since both the begin, end, and assign functions are used from the vector 
	// they must be declared.
	
	using std::vector< ValueType >::begin;
	using std::vector< ValueType >::end;
	using std::vector< ValueType >::assign;
	
	void RemoveDuplicates( std::list< ValueType > & UniqueElements )
	{
		// A temporary set will be used to store the unique elements (in sorted 
		// order). It will be filled as the remove function proceeds with the 
		// parsing of the elements removing duplicates.
		
		std::set< ValueType > SortedElements;
		
		// The actual removal is done by the remove-if function on the list with 
		// a lambda function checking if the element has been seen previously, 
		// i.e. if it is in the set, in that case it will be removed, otherwise 
		// it will be added to the set.
		
		UniqueElements.remove_if(  
			[ &SortedElements ]( const ValueType & TheElement )->bool {
				if( SortedElements.find( TheElement ) != SortedElements.end() )
					return true; // Element is a duplicate to be removed
				else
				{
					SortedElements.insert( TheElement ); // add to the unique values
					return false;                        // do not delete this element
				}
			}
		);
		
		// The unique elements can then be stored in the underlying vector and 
		// the maximum and minimum element recorded. There is unfortunately no 
		// easy way to do this in one go because the push back or insert functions
		// on vectors does not return iterators. So, another three passes over 
		// the elements... The vector is compacted after being initialised and 
		// before the minimum and maximum element pointers are initialised. The 
		// order is important as the shrink to fit function invalidates iterators!
		
		assign( UniqueElements.begin(), UniqueElements.end() );
		
		std::vector< ValueType >::shrink_to_fit(); 		
	}
	
public:
	
	// The basic constructor takes two iterators defining a range of elements 
	// to add to the set.
	
	template< class IteratorType >
	Set( IteratorType Begin, IteratorType End )
	: std::vector< ValueType >()
	{
		// A temporary list will be used to store the values in the right order
		// by copying them to a list.
		
		std::list< ValueType > UniqueElements( Begin, End );
		
		RemoveDuplicates( UniqueElements );
		
		// Then the vector's largest and smallest elements can be recorded.
		
		MinElement = std::min_element( begin(), end() );
		MaxElement = std::max_element( begin(), end() );
	}

	// The constructor for initialisation lists can then simply delegate to the 
	// previous constructor.
	
  Set( const std::initializer_list< ValueType > & InitialElements )	
	: Set( InitialElements.begin(), InitialElements.end() )
	{ }
	
	// When the various initial values are given as direct arguments to the 
	// constructor, it is simply embedded in an initialiser list and delegated 
	// to the previous constructor. 
	//
	// Implementation note: Variadic functions in C will expand at run time, see
	// https://stackoverflow.com/questions/24316808/are-variadic-functions-deprecated
	// and the core is to have variadic templates that can expand at compile time.
	// This requires the type of the elements to be a part of the variadic 
	// template.  
	
	template< class TheValue = ValueType, class... TheType >
	Set( TheType & ... InitialElements )
	: Set( { TheValue(std::forward< TheType>(InitialElements))... })
	{}
	
	// The copy constructor normally requires the underlying value type of both 
	// domains to be identical. This may seem unnecessarily restrictive as it  
	// should be possible to convert between types. The problem is mainly integer 
	// conversions where types may convert, but not without data loss. The 
	// extreme case is when a long is converted to a char, which would be OK if 
	// the value of the long is less than 255, but otherwise only the least 
	// significant bits will be retained. Boost numeric cast will throw if the 
	// values cannot be converted properly. For other types, it is checked that 
	// there the given type is convertible to the value type. Finally, if it 
	// fails an exception is thrown.
	
	template< class OtherValueType >
	Set( const Set< OtherValueType > & Other )
	: std::vector< ValueType >()
	{
		if constexpr ( std::is_arithmetic< OtherValueType >::value && 
									 std::is_arithmetic< ValueType >::value )
			for ( const OtherValueType & AnElement : Other )
				push_back( boost::numeric_cast< ValueType >( AnElement ) );
		else 
			if constexpr ( std::is_convertible< OtherValueType, ValueType >::value )
				assign( Other.begin(), Other.end() );
			else
		  {
				std::ostringstream ErrorMessage;
				
				ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
										 << "Set copy constructor cannot convert from given type "
										 << typeid( OtherValueType ).name() << " to type "
										 << typeid( ValueType ).name();
										 
			  throw std::invalid_argument( ErrorMessage.str() );
			}
			
		// Arriving at this point means that the conversion was successful and 
		// the minimum and maximum elements can be recorded after compacting the 
		// vector storage.
		
		std::vector< ValueType >::shrink_to_fit();
		
		MinElement = std::min_element( cbegin(), cend() );
		MaxElement = std::max_element( cbegin(), cend() );
	}
	
	// Only the above constructors should be used, and the default constructor 
	// is therefore deleted.
	
	Set( void ) = delete;
};

// -----------------------------------------------------------------------------
// Sets of intervals
// -----------------------------------------------------------------------------
//
// When the set consist of interval sub-domains as elements, it is necessary
// to provide special handling of the initialisation since the intervals in the  
// discrete set should not overlap. If they do overlap, then they should be 
// merged into one single interval. A template specialisation is therefore 
// provided to cover the case where the set consists of Intervals.
//
// Note that the value type should be arithmetic, but no test is needed to 
// this effect since the subintervals will only exist for arithmetic types 
// and so trying to request intervals of any other kind will simply not compile.

template< class ValueType >
class Set< Interval< ValueType > > 
: protected std::vector< Interval< ValueType > >
{
public:
	
	// The set of intervals inherits the countable property from the interval 
	// it encapsulates.
	
	static constexpr bool Countable = Interval< ValueType >::Countable;
	
	// Despite inheriting the countable property from the intervals, the set 
	// of intervals is still a set, and consequently it is discrete even if 
	// the individual intervals may be continuous.Selecting a value from the 
	// domain will entail first a discrete decision on which sub-interval
	// the value belongs to, and then perhaps a continuous optimisation to 
	// find the optimal value in that interval.
	
	static constexpr bool Continuous = false;

	// The standard types are defined as before.
	
	using value_type = ValueType;
	using Index      = typename std::vector< Interval< ValueType > >::size_type;

private:
	
	// The infimum value is the least lower bound of all subintervals, and the 
	// supremum value is the largest upper bound of all subintervals. If the 
	// intervals are countable, the element count is the sum of all the elements 
	// in all intervals. Otherwise, it is zero.
	
	ValueType InfimumValue, SupremumValue;
	Index     ElementCount;
	
protected:
	
	// The at function from the vector will be used internally
	
	using std::vector< Interval< ValueType > >::at;
	
public:
	
	// The vector is declared as protected because it should not be directly 
	// accessed. However, one may well want to parse its elements by constant 
	// iterators.
	
	using std::vector< Interval< ValueType > >::cbegin;
	using std::vector< Interval< ValueType > >::cend;
	
	//
	// SIZE RELATED FUNCTIONS
	//
	// The upper and lower bounds are no longer so obvious, however they will be 
	// taken to be the minimum and maximum extensions of any of the subintervals.
	
	inline ValueType lower( void ) const
	{ return InfimumValue; }
	
	inline ValueType upper( void ) const
	{ return SupremumValue; }
	
	// The size function is taken to be the number of sub-intervals if the
	// interval type is not countable, otherwise it will be the element count. 
	
	inline Index size( void ) const
	{
		if constexpr ( Countable )
			return ElementCount;
		else
			return std::vector< Interval< ValueType > >::size();
	}
	
	inline Index NumberOfValues( void ) const
	{ return size(); }

	//
	// ELEMENT RELATED FUNCTIONS
	//
  // Testing to see if an element is a part of the set implies in this case 
  // to test if it is a member of any of the subintervals of the set. 
  
	inline bool ElementQ( const ValueType & TheValue ) const
	{
		for ( auto SubInterval = cbegin(); SubInterval != cend(); ++SubInterval )
			if ( SubInterval->ElementQ( TheValue ) )
				return true;
			
		return false;
	}
  
  // The operator for element access is only valid if the interval type is 
  // countable. If this is not the case, it will simply not be defined. To 
  // find the right element, it is necessary to discard the sub-intervals 
  // not holding the requested element. The need for the base type 
  // to forward the value type is explained in 
  // https://stackoverflow.com/questions/13786479/using-c11-stdenable-if-to-enable-member-function-if-vector-is-specific-lengt
  
	template< bool IsCountable = Countable >
	inline typename 
	std::enable_if< IsCountable, ValueType >::type
	operator[] ( const Index idx ) const
	{ 
		if ( idx < ElementCount )
		{
			auto  SubInterval    = cbegin();
			Index SubIntervalIdx = idx;
	
			while ( SubInterval!= cend() )
		  {
				Index SubIntervalSize = SubInterval->size();
				
				if ( SubIntervalIdx < SubIntervalSize )
					return SubInterval->operator[]( SubIntervalIdx );
				else
				{
					SubIntervalIdx -= SubIntervalSize;
					++SubInterval;
				}
			}
		}
		else
		{
			std::ostringstream ErrorMessage;
			
			ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
						       << "Index given to operator [] is " << idx 
						       << " but the set of intervals has only " << ElementCount 
						       << " elements";
									 
		  throw std::out_of_range( ErrorMessage.str() );
		}
	}
	
	// There is also a need to get a reference to a subinterval based on its index
	
	inline Interval< ValueType > & SubInterval( const Index idx )
	{ return at( idx );	}
	
	// It may also be desirable to find an interval containing a given value, 
	// and this is a direct scan of the intervals. If the value cannot be found
	// in any interval, then a standard out of range exception is thrown. The 
	// return value from this function can be used as the argument to the 
	// previous function to retrieve the interval containing the value.
	
	inline Index FindInterval( const ValueType TheValue ) 
	{
		for ( Index IntervalIndex = 0; IntervalIndex < size(); IntervalIndex++ )
			if( at( IntervalIndex ).ElementQ( TheValue ) )
				return IntervalIndex;
		
		// If the element could not be found in any of the subintervals the value 
		// is not valid for this domain and the exception is thrown.
			
		std::ostringstream ErrorMessage;
		
		ErrorMessage << __FILE__ << " at line " << __LINE__ << ": "
								 << "The given value " << TheValue 
								 << " is not a part of any subintervals of the domain";
								 
	  throw std::out_of_range( ErrorMessage.str() );
	}
	
	//
	// CONSTRUCTORS
	//
  // The same three constructors as for the standard Set domain must 
  // be supported, and the iterator version is again the main constructor that
  // also initialises the extreme values and the number of elements. Note that 
  // in this case the underlying vector storage can be directly initialised by 
  // the vector constructor, before a second pass of the stored values is 
  // done to record the extremal values.
  
	template< class IteratorType >
	Set( IteratorType Begin, IteratorType End )
	: std::vector< Interval< ValueType > >( Begin, End ), 
	  InfimumValue(0), SupremumValue(0),  ElementCount(0)
	{
		// it is necessary to check that the range is not completely empty before 
		// using it to initialise the count and the extremal values
		
		if ( Begin != End )
		{
			InfimumValue  = Begin->lower(); 
			SupremumValue = Begin->upper();
			
			for ( auto SubInterval = cbegin(); SubInterval != cend(); ++SubInterval )
			{
				if ( SubInterval->lower() < InfimumValue )
					InfimumValue = SubInterval->lower();
				
				if ( SupremumValue < SubInterval->upper() )
					SupremumValue = SubInterval->upper();
				
				ElementCount += SubInterval->NumberOfValues();
			}
		}
	}	
	
	// The constructor for initialisation lists can then simply delegate to the 
	// previous constructor.
	
  Set( const std::initializer_list< Interval< ValueType > > & InitialElements )	
	: Set( InitialElements.begin(), InitialElements.end() )
	{ }
	

	
};

}        // Name space LA Solver
#endif   // LA_SOLVER_DOMAINS
