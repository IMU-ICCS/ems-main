/*=============================================================================
  Solver interface

  This header defines the core concepts used by the solver:
  
  Variables   - The quantities that needs a value
  Domains     - Each variable has a domain
  Constraints - Functional relationships among the variables that evaluate to
	        either true or false
	        
  The intention is that the accompanying code file (.cpp) will be executed 
  from the CS metamodel and will initialise all these variables for a 
  particular version of the problem. The solver will then try to find a 
  configuration that satisfies all the constraints. This configuration will
  then be evaluated to obtain the utility of the particular configuration. 
  
  The output of the solver will be a file containing the best configuration 
  seen until now, i.e. values assigned to all variables such that all 
  constraints are satisfied and such that this configuration leads to the 
  highest Utility value observed.
  
  The concept is based on the dynamic link loader [1]: The accompanying C++ 
  code file is compiled after the problem has been defined. Then it will be 
  linked dynamically with the solver which then calls the structures defined 
  in this header [2]. 
  
  References:
  
  [1] http://linux.die.net/man/3/dlopen
  [2] http://www.tldp.org/HOWTO/html_single/C++-dlopen/
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef SOLVER_INTERFACE
#define SOLVER_INTERFACE

// The code depends heavily on multiple structures of the Standard Template 
// Library, and these are included in the order of use. Note the dependency on
// the boost template library (one file)

#include <string>
#include <boost/variant.hpp>
#include <iterator>
#include <set>
#include <initializer_list>
#include <vector>
#include <typeinfo>
#include <typeindex>
#include <algorithm>
#include <map>
#include <functional> 
#include <sstream>
#include <utility>
#include <ostream>

// In several places we will need to test if the template argument is 
// integral or a real value, and define functions accordingly. The following 
// utilities enables these tests at compile time.

#include <type_traits>
//#include <boost/utility/enable_if.hpp>

// The metrics could be subject to reads from one thread and writes from 
// another and they will therefore be defined as atomic operations that are 
// protected by low-level mechanisms at the CPU level (at worst these 
// mechanisms degenerates to a stadard mutex protection).

#include <mutex>
#include <atomic>

// Selecting a random value from the domain values of variables requires the 
// random generator. This is formally a part of the LA framework and should 
// be included from there.

#include "RandomGenerator.hpp"

// The solver is defined as a namespace in order to keep the class names 
// unique from other contexts.

namespace Solver {

/*****************************************************************************
 Generic values
 
 There are two difficult parts related to the strict typing. One is the 
 function to read out the value of a variable. What type does it return? The 
 answer depends on the type of the variable. This implies that one cannot use
 a generic variable pointer and cast it to the correct base class since there
 are many combinations possible if you take the domain type times the numeric
 type for instance. The methods for obtaining the value of a variable is given
 after the definition of the variable templates.
 
 A worse problem is setting the variable value as one 
 should be able to do that through a generic pointer since one cannot know 
 what the pointer should be cast into. 
 
 The magic wand is the boost::variant, which generalises the union type that
 can only work for basic types, e.g. strings are not supported. The generic 
 value is defined as a variant that can take any type supported by the 
 metamodel. Actually it is here defined for any arithmetic type as well as 
 strings.

******************************************************************************/

typedef boost::variant< 
  bool, 
  char, 
  char16_t, 
  char32_t, 
  std::string, 
  short int, 
  int, 
  long int, 
  long long int, 
  unsigned char, 
  unsigned short int, 
  unsigned int, 
  unsigned long int, 
  unsigned long long int, 
  float, 
  double, 
  long double 
> GenericValue;

/*****************************************************************************
 Constraints 

 In optimisation problems, constraints are normally either of the inequality 
 type or equality type, i.e. they are defined as g(x) <= 0 or h(x) = 0. To 
 homogeneously represent both types they will be instances of the constraint 
 base class that defines a Value() function to get the value of g(x) or h(x) 
 respectively depending on the constraint type, and the Evaluate() function 
 that evaluates the constraint value by carrying out the logical test 
 depending on the constraint type. 
   
******************************************************************************/

// The Constraint class mainly deals with the types of constraints in case 
// one would like to know if this is an equality constraint or an inequality 
// constraint and defines the general interface for all constraints.

class Constraint
{ 
public:

  enum class Type
  {
    Equality,
    Inequality
  };

private:
  
  // Then the variable holding this the type of this constraint.
  
  Type TypeOfConstraint;
  
protected:
  
  // In order to ensure that the global list of constraints only contains 
  // pointers to objects allocated with the global 'new' operator and not
  // addresses of objects on the stack etc. a constraint must be able to 
  // create a clean copy of itself. However, as each constraint is a special 
  // object, the copy operation has to be deferred to the derived classes.
  
  virtual Constraint * MakeCopy (void) = 0;

public:
  
  // There is a public function to read out this type if it needs to be 
  // checked by other code.
  
  inline Type ConstraintType (void) const
  {
    return TypeOfConstraint;
  };
  
  // The constructor takes this type and stores it in the local variable
  
  Constraint( Type TheType ) 
  {
    TypeOfConstraint = TheType;
  }

  // There is a generic and external interface to evaluate the constraint 
  // function in terms of the variables involved. This will be defined by 
  // derived ValueConstraint class below. Note that because each constraint 
  // object will be different depending on the function it encapsulates, only
  // this function is generally callable.
  
  virtual double Value (void) const = 0;
  
  // The constraint can be evaluated depending on its type by a generic 
  // function. Unfortunately we have to test the type of the constraint, 
  // thus using a virtual function and derived classes for the various types
  // is better, and this is defined by classes derived from the 
  // ValueConstraint.
  
  virtual bool Evaluate (void) const = 0;
  
  // The next function is ingenious way to insert this constraint into 
  // global constraint structure to prevent pointers created with overloaded 
  // 'new' operators, or addresses of objects on the stack to be inserted 
  // as constraints. It basically insert a copy of the constraint for which 
  // it is called using the above MakeCopy function.
  
  void Register (void);
  
  // A dummy virtual constructor
  
  virtual ~Constraint (void)
  { };
};

//-----------------------------------------------------------------------------
// Constraint structure
//-----------------------------------------------------------------------------
// Similar to the variables, there is a global list of constraints. However,
// in contrast with the variables, the constraints are unnamed. They are 
// therefore not defined as objects by itself, but only as elements of the  
// constraints list. This poses a problem since only objects can be defined 
// in the scope of the Interface.cpp file. For convenience, the constraints 
// are also defined in terms of generator functions (see below), and 
// function calls can only be done after main has defined the starting point
// of the calling stack. The solution is to define the constraint list as 
// a special object whose constructor defines the constraints.
// 
// NOTE: Defining this as a std::list does not work. It will compile, but give
// a segmentation fault when elements are added. A vector works fine though...

class ConstraintContainer : private std::vector< Constraint * >
{
private:
  
  // The number of constraints for each type (equality and inequality) are
  // needed to initialise the solver correctly. Since the number of constraints
  // is related to the problem to be solved, this number will not change between
  // invocation and it will be the same for all the solvers running 
  // concurrently. They can therefore be static and initialised by the first 
  // optimiser being constructed.
  
  unsigned int NumberOfEQConstraints, NumberOfNEQConstraints;
   
public:
  
  // The constructor to define the constraints and initialise the constraint
  // list. It is defined in Interface.cpp and embodies the constraints of 
  // the problem.
  
  ConstraintContainer( void );
  
  // The reason for the constraint list to be private is to ensure that all 
  // constraints are allocated with the same 'new' operator. This is important
  // if dynamic linking is used since a solver using this interface can have
  // overload the 'new' operator and constraints dynamically allocated in 
  // different scopes should call different delete operators. It would also be
  // possible to allocate a constraint as an object on the stack (e.g. in 
  // main) and pass the reference to these objects as the constraint pointer. 
  // Calling delete on such a pointer will obviously not work. 
  // 
  // Furthermore, each constraint will be a unique class because it 
  // encapsulates the constraint function. Hence it has to insert itself, 
  // and it should therefore be allowed access to the constraint list.
  
  friend void Constraint::Register( void );

  // In order to be able to check the value of a constraint or to evaluate 
  // it we will need to iterate over the container. However, we will never 
  // modify any of the objects, so it is only necessary to open access to
  // the constant begin andlist end functions.
  
  using std::vector< Constraint * >::cbegin;
  using std::vector< Constraint * >::cend;
  
  // There are also two utility functions to return the number of constraints
  // of each type.
  
  inline unsigned int EQCount( void )
  { return NumberOfEQConstraints; }
  
  inline unsigned int NEQCount( void )
  { return NumberOfNEQConstraints; }
  
  // The destructor will delete all constraints calling the generic delete 
  // operator.
  
  ~ConstraintContainer( void );
};

// The actual list of constraints is now a global object of this class. It
// can be directly accessed if static linking of the model is used, otherwise
// it the address of the list can be obtained using the below C function.

extern ConstraintContainer Constraints;

// A pointer to this structure can be obtained by calling the following 
// function

extern "C" ConstraintContainer * GetConstraints (void);

//-----------------------------------------------------------------------------
// Constraint Value
//-----------------------------------------------------------------------------
//  The functional form is typically given as a lambda function, which is 
//  represented as an object of a unique type. Essentially this makes all the 
//  constraints different, and the constraint is therefore implemented as a 
//  template.

template < class ConstraintFunction >
class ValueConstraint : public Constraint
{
protected:
  
  // The function to evaluate is stored as a protected object so that it can 
  // be directly evaluated by derived classes. There is no danger that it can 
  // be changed accidentally because of the unique signature of the lambda 
  // function stored which will only be known at the time of construction.
  
  ConstraintFunction ValueFunction;
    
public:

  // The constructor only sets the constraint type and captures the function 
  // representing the constraints. It uses move semantics strictly assuming 
  // that the provided functional form is a lambda function.
  
  ValueConstraint( Constraint::Type TheType, 
		   const ConstraintFunction & TheFunction )
  : Constraint( TheType ), ValueFunction( TheFunction )
  {  }
  
  // The value function is the main interface to read the value of the 
  // constraint given the current assignment of the variables. Its return 
  // value is double to allow the use of standard numerical optimisation 
  // algorithms. Consequently, the value function must have a conversion to
  // double. The could shall not compile if the function cannot be converted.
  
  virtual double Value (void) const
  {
    return static_cast< double >( ValueFunction() );
  }
  
  // The destructor is again virtual and trivial
  
  virtual ~ValueConstraint (void)
  { };
};

//-----------------------------------------------------------------------------
// Equality constraint
//-----------------------------------------------------------------------------
// The equality constraint is a specialisation forwarding the function 
// representing the constraint and explicitly defining the constraint as an 
// equality constraint by the Evaluate function

template < class ConstraintFunction >
class EqualityConstraint : public ValueConstraint< ConstraintFunction >
{
public:
  
  // The constructor simply forwards the function of this constraint 
  
  EqualityConstraint( const ConstraintFunction & TheFunction )
  : ValueConstraint< ConstraintFunction> ( 
	  Constraint::Type::Equality, TheFunction )
  { };
  
  // Evaluating the test of this constraint is now done via the stored 
  // value object in the base class. It is mandatory that the compiler can
  // make sense out of the test, i.e. that the value object has a return 
  // value that can be tested in this way.
  
  virtual bool Evaluate ( void ) const
  {
    return ( ValueConstraint< ConstraintFunction >::ValueFunction() == 0 );
  }
  
  // In order to make a clean pointer to a constraint we have to make a 
  // new object using the global 'new' operator and initiate this object with
  // the current constraint function.
  
  virtual Constraint * MakeCopy (void)
  {
    return ::new EqualityConstraint( 
		 ValueConstraint< ConstraintFunction >::ValueFunction );
  }
  
  // The virtual destructor does nothing for this class
  
  virtual ~EqualityConstraint (void)
  { };
};

//-----------------------------------------------------------------------------
// Inequality constraint
//-----------------------------------------------------------------------------
// This is identical to the equality constraint except using a different test
// to evaluate the constraint.

template < class ConstraintFunction >
class InequalityConstraint : public ValueConstraint< ConstraintFunction >
{
public:
  
  // The constructor simply forwards the function of this constraint 
  
  InequalityConstraint( const ConstraintFunction & TheFunction )
  : ValueConstraint< ConstraintFunction >( 
	Constraint::Type::Inequality, TheFunction )
  { };
  
  // Evaluating the test of this constraint is now done via the stored 
  // value object in the base class. It is mandatory that the compiler can
  // make sense out of the test, i.e. that the value object has a return 
  // value that can be tested in this way.
  
  virtual bool Evaluate ( void ) const
  {
    return ( ValueConstraint< ConstraintFunction >::ValueFunction() <= 0 );
  }
  
  // Similar to the Equality Constraint also the inequality constraint has
  // a copy function using the global 'new' in order to ensure that the 
  // objects pointed to from the constraint list can be safely deleted.
  
  virtual Constraint * MakeCopy (void)
  {
    return ::new InequalityConstraint( 
		 ValueConstraint< ConstraintFunction >::ValueFunction );
  }
  
  // Finally there is a virtual destructor to ensure that we clean up 
  // everything.
  
  virtual ~InequalityConstraint (void)
  { };
};

//-----------------------------------------------------------------------------
// Generator functions
//-----------------------------------------------------------------------------
// The constraint objects must be allocated and inserted into the global list
// of constraints. In order to make the interface as trivial as possible for 
// the user two generator functions are defined for the two types of 
// constraints.
//
// Note that we do an explicit registration although this could have been 
// done by the constructor of the constraint. This allows a minor flexibility
// for derived classes to do something interesting prior to registration if 
// that is desired, or to have constraints that are not registered (for 
// some strange reason...)

template < class ConstraintFunction >
void make_equality_constraint ( const ConstraintFunction & TheFunction )
{
  EqualityConstraint< ConstraintFunction > TheConstraint( TheFunction );
  TheConstraint.Register();
}

template < class ConstraintFunction >
void make_inequality_constraint ( const ConstraintFunction & TheFunction )
{
  InequalityConstraint< ConstraintFunction > TheConstraint( TheFunction );
  TheConstraint.Register();
}

/*****************************************************************************
 Domains
 
 Each variable has a domain. In order to provide a uniform interface for these
 they are all derived from the domain base class, and the different types of
 domains supported must correspond to the different domain types supported 
 by the CS metamodel. The base class basically defines the possible domain
 types, and offer a function to check the type of the domain.
  
******************************************************************************/

class Domain
{
public:
  
  // The metamodel supports variable domains of specific types. It should be 
  // noted that the numeric types can support any numeric type like float,
  // int, or duble.
  
  enum class Types
  {
    List, 		// Discrete domain (supports strings)
    NumericList,	// Discrete domain (supports numeric types)
    Range,		// Consists of a numeric interval
    MultiRange,		// Consists of several numeric intervals
    Boolean,		// A domain that is either true or false
    Unknown		// Default error value for the base class
  };
  
  // It is necessary to know if the domain is discrete or continuous, in 
  // particular for ranges. They will be discrete if they support an integral
  // numeric type and continuous if they support real variables.
  
  enum class Categories
  {
    Discrete,
    Continuous,
    Unknown
  };
  
protected:
  
  // The actual type of this particular domain is kept in a variable that
  // can be set by the constructors of the derived domain types, but should
  // be read only. It is therefore protected
  
  Types DomainType;
  
  // The category is also stored in the base class in order to be available
  // through a base class pointer. The value of this is also set by the 
  // constructors of the derived classes.
  
  Categories VariableCategory;
  
public:
  
  // The domain type is read by a call to the below function
  
  inline Types Type (void) const
  {
    return DomainType;
  };
  
  // A similar function is defined to access the category of the domain
  
  inline Categories Category (void) const
  {
    return VariableCategory;
  }

  // If the domain is continuous, one should set constraints to keep the 
  // variable using the domain within its bounds. However, for discrete 
  // domains no constraints can be defined. Since the test for the domain 
  // category is done in the constructor of the variable, i.e. at run-time
  // each domain must define a constraint defining function in order for the 
  // code to compile. This function will be overloaded by derived classes 
  // supporting continuous domains. For all other domains it will just 
  // create an exception if it is wrongly called.
    // Setting the upper and lower bound constraints for all variables. Even if 
  // some of the constraints are already bound constraints set by the variables
  // and domains themselves, such constraints can be non-linear (see the 
  // MultiRange domain for instance). Hence, it helps to frame the problem by
  // giving the absolute boundaries for the variables.

  template < class VariableType >
  void DefineConstraints ( VariableType * TheVariable )
  {
    throw std::string("Constraints allowed only for continuous domains");
  }
  
  // The constructor takes no parameters, and sets the domain type to 
  // unknown (in case someone accidentally creates a bare base class)
  
  Domain( void )
  {
    DomainType 	     = Types::Unknown;
    VariableCategory = Categories::Unknown;
  };
 
  // There is also a virtual destructor. It does nothing for this class, but
  // it is needed for the dynamic linking, which also needs it to be 
  // virtual.
  
  virtual ~Domain( void )
  { };

  // If the domain is discrete then we will also need to know the number of
  // elements in the domain. Again this is only known for derived classes,
  // and the continuous classes have no meaningful way to define this. The 
  // function returns a size_t variable, as this is the type used for the 
  // vectors in the standard template library. Continuous domains will 
  // throw an exception if this function is called.
  
  virtual size_t NumberOfValues( void ) const = 0;
  
  // There is also a function to check if a value is a part of the domain.
  // One should note that a similarly named function taking the right variable
  // type should be provided by the derived classes for quick access from 
  // variable classes actually knowing what their type is.
  
  virtual bool ElementQ( const GenericValue & Element ) const = 0;

  // The values in the domain are generally unknown to other classes, but 
  // they would still need to select one of the values by their natural 
  // index (enumeration). The index is supposed to run from 0 to n-1. If the 
  // domain is continuous, then this function will throw an exception.
  
  virtual GenericValue EnumeratedValue(size_t Index) const = 0;
  
  // An alternative way to read out the variable would be to use a classical
  // index operator, and here it is for convenience
  
  inline GenericValue operator[] (size_t Index) const
  {
    return EnumeratedValue( Index );
  };
  
  // --------------------------- ITERATOR -------------------------------------
  // Christian Perez (INRIA, Lyon) correctly commented that this indexed 
  // element access is inefficient and inconvenient if one would like to 
  // iterate over all the elements in the domain. This is mainly due to the 
  // fact that the EnumeratedValue function is stateless and cannot find 
  // the next value based on the previous value, like an iterator would. 
  // For this reason an iterator has been added. Given that the domain is a 
  // property of the variables, the domain values shall not be changed after
  // construction. It is thus only necessary to implement a constant input 
  // iterator to read the values of the domain. Note that for this reason 
  // we are returning the actual value and not just a reference to the value 
  // in the storage container.
  // 
  // An iterator is not trivial since the internal structure of each derived
  // class is different. Normally one would implement the iterator in the 
  // derived class, however this is not possible since one generally only 
  // has access to a pointer to the base class, and can therefore only call
  // iterator generating function like begin() or end() on the base class.
  // These two functions must have a well defined return type, i.e. the 
  // iterator object known to the base class.
  //
  // This dilemma is resolved by "internal polymorphism". The iterator is 
  // constructed with a generalised value pointer which implements virtually
  // the operators needed to increment, decrement, or read the value pointed
  // to by this generalised object. This object is thus the interface for the 
  // actual implementations in the base classes (which in turn does not need
  // to implement the actual iterator).
  //
  // In addition to the capability of moving the pointer it must also be able
  // to detect equivalence of two pointers. A more subtle point is that it 
  // needs to self-destruct. The reason is that this generalised pointer is
  // created with the iterator as a dynamically allocated object by a function
  // on a domain object. However, under the current architecture, the domain
  // object may be located in the dynamically linked library. This implies, 
  // at least theoretically, that the main code using this library could have
  // defined its own "new" and "delete" operators. Thus, an object created 
  // in the library could simply cause serious problems if on attempts to 
  // delete it by a different delete operator when the object was created with 
  // a non-matching "new".
  
protected:
  
  class GeneralisedValuePointer
  {
  public:
    
    virtual GenericValue Value (void) = 0;  	// Get the value
    virtual void Decrement (void) = 0;   	// Move to the left
    virtual void Increment (void) = 0;  	// Move to the right    
    virtual bool Equals( const GeneralisedValuePointer * OtherValue ) const = 0;
    virtual GeneralisedValuePointer * MakeCopy (void) const = 0;

    void DestroyPointer ( void ); // Defined in .cpp to avoid inlining.    
    virtual ~GeneralisedValuePointer (void)
    { };
  };

  // Some of the domains are implemented as a standard container already 
  // supporting an iterator. We can then define the generalised pointer to
  // be this standard iterator and directly reuse the functionality of the 
  // STL container iterator.
  
  template < class Container >
  class STLElementPointer
  : public GeneralisedValuePointer, public Container::iterator
  {
  public:
    // The constructor takes an iterator of the set and copies this to the 
    // base class
    
    STLElementPointer( typename Container::iterator SetIterator )
    : GeneralisedValuePointer(), 
      Container::iterator( SetIterator )
      { };

    // Making a copy means allocating a new element pointer and copying 
    // this pointer.
      
    virtual GeneralisedValuePointer * MakeCopy (void) const
    {
      return new STLElementPointer< Container >( *this );
    }

    // Returning the value of an element means calling the dereference 
    // operator of the STL iterator
    
    virtual GenericValue Value (void)
    {
      return Container::iterator::operator*();
    }
    
    // Decrementing the pointer means decrementing the iterator. Since
    // the prefix operators should be more efficient than the postfix 
    // operators, we call the prefix operators on this iterator inherited
    // through the STL iterator
    
    virtual void Decrement (void) {   --(*this);   };
    virtual void Increment (void) {   ++(*this);   }  		
    
    // The most complex function in syntax is the test for equality where 
    // we use again the iterator's comparison operator, ensuring that 
    // the provided pointer really is an element pointer before dereferencing
    // it to become "another iterator"
    
    virtual bool Equals( const GeneralisedValuePointer * OtherValue ) const
    {
      return Container::iterator::operator==(
	*( dynamic_cast< const STLElementPointer<Container> * >( OtherValue ) )
      );
    }

    // A virtual destructor to ensure that this element is properly closed
    
    virtual ~STLElementPointer (void)
    { };
  };
  
public:
  
  // The actual iterator can now be defined only in this domain base class
  // taking care of a generalise value pointer given to it at the time of 
  // construction. It implements the operators you would expect an iterator
  // object to support. It is a standard bi-directional iterator, but since
  // the domain should not be changed after construction, the value type is
  // constant.
  
  class iterator 
  : std::iterator< std::bidirectional_iterator_tag, GenericValue >
  {
  private:
    
    GeneralisedValuePointer * Element;
    
  public:
    
    // The normal constructor only stores the given pointer
    
    iterator( GeneralisedValuePointer * TheValue )
    {
      Element = TheValue;
    }
    
    // There is also a copy constructor. This is not as easy as one may think
    // since we are storing a pointer to a generalised value pointer. Should
    // we set the copied iterator to point to the SAME value element as 
    // the old iterator? What will then happen if the old iterator is 
    // destroyed deleting the value pointer it takes care of? How about 
    // increments or decrements? Both objects would then move the same value
    // pointer. The solution is that the generalised value pointer must be 
    // able to clone itself, and that was the reason for introducing the 
    // function to make a copy above so that the copy is a new generalised 
    // value pointer that initially points to the same location as the 
    // copied iterator, but can then be moved independently of the original.
    
    iterator( const iterator & OtherIterator )
    {
      Element = OtherIterator.Element->MakeCopy();
    }
    
    // Comparing the iterator with another iterator must be done by the 
    // structure specific class
    
    bool operator== (const iterator & OtherIterator) const
    {
      return Element->Equals( OtherIterator.Element );
    }
    
    // Checking if two iterators are different is just to negate the previous
    // statement
    
    bool operator!= (const iterator & OtherIterator) const
    {
      return !( Element->Equals( OtherIterator.Element ) );
    }
    
    // The dereference operator will return the value currently pointed at by
    // this iterator, which is using the same dereference operator on the 
    // pointer itself
    
    GenericValue operator* (void)
    {
      return Element->Value();
    }
    
    // The prefix decrement operator moves the pointer to an earlier element
    // in the structure, and return this operator.
    
    iterator & operator-- (void)
    {
      Element->Decrement();
      return *this;
    }
    
    // In the postfix version the old value of the iterator should be returned,
    // i.e. a copy of the iterator as it was before the value was decremented.
    
    iterator operator-- (int)
    {
      iterator OldIterator( *this ); // make a copy of the current state
      Element->Decrement();
      return OldIterator;
    }
    
    // the prefix increase operator increments the position of this operator 
    // and then returns this iterator
    
    iterator & operator++ (void)
    {
      Element->Increment();
      return *this;
    }
    
    // The postifx operator will again return a copy of the iterator as it was
    // before the increment.
    
    iterator operator++ (int)
    {
      iterator OldIterator( *this ); // make a copy of the current state
      Element->Increment();
      return OldIterator;
    }
    
    // The destructor makes sure to remove the generic value pointer when 
    // this iterator is closed
    
    virtual ~iterator (void)
    {
      Element->DestroyPointer();
    }
  };
  
  // Finally there are the functions to return an iterator to the first 
  // element in the domain, and to the last element. These must also be 
  // virtual since there is no way define them without knowing the actual 
  // structure used to store the domain elements. Note that they are declared
  // as constant functions since they will not change the container object 
  // for which they are called.
  
  virtual iterator begin( void ) const = 0;
  virtual iterator end  ( void ) const = 0;
  
};

// ----------------------------------------------------------------------------
// List
//-----------------------------------------------------------------------------
// A discrete domain is actually a set since it does not make sense to have
// two identical elements in the domain. The simplest one is the list 
// domain holding strings. The alternatives must be given as an initialiser 
// to the list constructor.
//   MyListDomain( { "First", "Second", "Third" } );
//
// Since most of the functionality is provided by the Standard Template 
// Library set, this class only needs to initialise its domain type to tell
// what sort of domain it is.

class List : public Domain 
{
private:
  
  // The actual set of lists is kept private to avoid that new lists are 
  // added to the domain after its creation.
  
  std::set< std::string > ListElements;
  
public:
  
  // First we define the value type of this list, which is obviously a string
  // This is important as it is used by the variables having this domain.
  
  typedef std::string	value_type;
    
  // Then there is a function to test if a particular string is a member of 
  // the domain. It returns "true" if the provided string is in the domain,
  // false otherwise
  
  bool ElementQ ( const std::string & Element ) const
  {
    if ( ListElements.find( Element ) != ListElements.end() )
      return true;
    else
      return false;
  };
  
  // The generic version of this function simply converts the generic 
  // value to a string and calls the specific function to test if this belongs
  // to the domain.
  
  virtual bool ElementQ ( const GenericValue & Element ) const
  {
    return ElementQ( boost::get< std::string >( Element ) );
  }
  
  // The number of possible values is in this case just the number of strings
  // in the value set.
  
  virtual size_t NumberOfValues (void) const
  {
    return ListElements.size();
  };
  
  // A value can be retrieved by its index. However since a set does not 
  // support direct access, we have to count the number of of elements from 
  // the start of the set.
  
  virtual GenericValue EnumeratedValue (size_t Index) const
  {
    auto aValue = ListElements.begin();
      
    while (  (Index > 0) && (aValue != ListElements.end()) )
    {
      --Index;
      ++aValue;
    }
    
    if ( aValue != ListElements.end() )
      return *aValue;
    else
      throw std::string("List: Element index out of bounds");
  };
   
  // Selecting a random value is to pick one of the values in the set. In 
  // order not to duplicate code, this will be be implemented in terms of the 
  // above generic enumerated value converted back to the actual string.
  
  std::string RandomValue( void ) const
  {
    return boost::get< std::string >(
      EnumeratedValue( 
	Random::Number( 0u, static_cast<unsigned int>( ListElements.size()-1 ) )
      )
    );
  }
  
  // The constructor takes an initialiser and set the values for the domain
  // accordingly. Awkwardly, the compiler will not understand that a constant
  // char string like "text" can be used to initialise a standard string, so 
  // it is necessary to make the constructor generic. A further implication
  // is that the initializer list cannot be directly passed to the set 
  // constructor if the string type is "const char *", so we have to use
  // iterators to initialise the values.

  template< class StringType >
  List( std::initializer_list< StringType > InitialValues ) 
  : Domain(), ListElements( InitialValues.begin(), InitialValues.end() )
  {
    DomainType 		= Domain::Types::List;
    VariableCategory 	= Domain::Categories::Discrete;
  };
  
  // There is also a copy constructor to build a domain from another 
  // domain
  
  List( const List & OtherList )
  : Domain(), ListElements( OtherList.ListElements )
  {
    DomainType 	 	= OtherList.DomainType;
    VariableCategory 	= OtherList.VariableCategory;
  }
  
  // The destructor is only a place-holder to make sure it is virtual
  
  virtual ~List (void)
  { };
  
  // --------------------------- ITERATOR -------------------------------------
  // Since the list is implemented by using the STL Set, we can also implement
  // the generalised element pointer using the template for an STL domain 
  // iterator directly in the begin and end functions.
  
public:
  
  virtual Domain::iterator begin ( void ) const
  {
    return Domain::iterator( 
    new STLElementPointer< std::set< std::string > >( ListElements.begin() ) 
    );
  }
  
  virtual Domain::iterator end ( void ) const
  {
    return Domain::iterator( 
    new STLElementPointer< std::set< std::string > >( ListElements.end() ) );
  }
  
};

// ----------------------------------------------------------------------------
// Range
//-----------------------------------------------------------------------------
// A Range is also a numeric concept for variables that can assume any value
// over a given interval. The lower and upper bounds of the interval 
// must be given to the constructor as they will be stored for read only 
// reference by the corresponding interface functions. The range is countable
// only if it is finite, meaning that the numeric type of the range is an
// integer. 

template< typename NumericType >
class Range : public Domain
{
private:
  
  // The limits of the interval is stored in two numeric variables
  
  NumericType LowerLimit, UpperLimit;
  
  // This class will support two different constructors, one with directly
  // receiving the limits and one taking an initialiser list. They will both 
  // use the following internal initialisation function to ensure that the 
  // limits will be in the right order independent of the order they are given.
  
  void Initialise ( NumericType LimitA, NumericType LimitB )
  {
    if ( LimitA <= LimitB )
    {
      LowerLimit = LimitA;
      UpperLimit = LimitB;
    }
    else
    {
      LowerLimit = LimitB;
      UpperLimit = LimitA;
    }
    
    // Then initialise the types of the domain base class to provide 
    // meta-information about this domain.
    
    DomainType = Types::Range;
    
    if ( std::is_integral< NumericType >::value )
      VariableCategory = Domain::Categories::Discrete;
    else
      VariableCategory = Domain::Categories::Continuous;
  };
  
public:
  
  // First the test that this class is used only with a numeric type
  
  static_assert( std::is_arithmetic< NumericType >::value,
		 "Domain Range must have a numeric type!" );
  
  // Then the value_type is defined to provide a way to know what sort of 
  // range this is
  
  typedef NumericType	value_type;
  
  // Then there is a function to test whether a given element is an element
  // in this range
  
  bool ElementQ ( const NumericType & Element ) const
  {
    if ( (LowerLimit <= Element) && (Element <= UpperLimit) )
      return true;
    else
      return false;
  };
  
  // The generic function converts the generic value to the numeric type 
  // of this range and calls the specific function.
  
  virtual bool ElementQ ( const GenericValue & Element ) const
  {
    return ElementQ( boost::get< NumericType >( Element ) );
  };
  
  // Then there are functions to read out the stored bounds 
  
  inline NumericType LowerBound (void) const
  {
    return LowerLimit;
  }
  
  inline NumericType UpperBound (void) const
  {
    return UpperLimit;
  }

  // A special trick is necessary since the number of values method should
  // only be defined if the numeric type is an integer (as there are an 
  // infinite number of real values in any real interval).
  
  virtual size_t NumberOfValues (void) const
  {
    if ( std::is_integral< NumericType >::value )
      return UpperLimit - LowerLimit + 1;
    else
      throw std::string("Range is not discrete!");
  };
  
  // The same trick must be applied for retrieving a value by its (implicit)
  // index since this function can only exist for a countable range. However,
  // finding the value to return is then rather easy as we can simply add 
  // the index to the lower bound and return it if it is less or equal to 
  // the upper bound of the range.
  
  virtual GenericValue EnumeratedValue (size_t Index) const
  {
    if ( std::is_integral< NumericType >::value )
    {
      NumericType ReturnValue = LowerLimit + Index;
      
      if ( ReturnValue <= UpperLimit )
	return ReturnValue;
      else
	throw std::string("Range: Index to value out of bounds!");
    }
    else
      throw std::string("Range is not discrete!");
  };

  // Computing the random number depends on the nature of the range. If the 
  // range is discrete, an integer will be returned, otherwise a proper double
  // will be returned.
  
  NumericType RandomValue( void ) const
  {
    if ( std::is_integral< NumericType >::value )
      return Random::Number( LowerLimit, LowerLimit 
			      + static_cast< NumericType>( NumberOfValues() ));
    else
      return Random::Number( LowerLimit, UpperLimit );
  }
  
  // Continuous domains implicitly define constraints, for instance if the 
  // variable x is in the interval [a,b], it is the same as saying that 
  // (a-x) <= 0 and (x-b) <= 0. The following function will take a pointer to
  // a variable like x and define the corresponding constraints. Note that 
  // the function will exist if and only if the underlying data type is 
  // continuous.
  
  template < class VariableType >
  void DefineConstraints ( VariableType * TheVariable )
  {
    if ( std::is_floating_point< NumericType >::value )
    {
      make_inequality_constraint( [this, TheVariable](void){ 
	    return ( this->LowerLimit - TheVariable->Value() );
      });
      
      make_inequality_constraint( [this, TheVariable](void){ 
	    return ( TheVariable->Value() - this->UpperLimit );
      });
    }
    else
      throw std::string("Constraints allowed only for continuous domains");
  }
  
  // The constructor takes the bounds of the interval and stores them in 
  // the private variables, as well as defines the type of the domain
  
  Range( NumericType From, NumericType To )
  {
    Initialise( From, To );
  };
  
  // The normal constructor will however take an initialiser with two 
  // elements and construct the range from these two limits.
  
  Range( std::initializer_list< NumericType > InitialValues )
  {
    if ( InitialValues.size() == 2 )
    { 
      std::vector< NumericType > Limits( InitialValues );
      Initialise( Limits[0], Limits[1] );
    }
    else
      throw std::string("Range initialisation takes two elements only!");
  }
  
  // Finally, a copy constructor is needed for many structures. 
  
  Range ( const Range< NumericType > & AnotherRange)
  {
    UpperLimit 		= AnotherRange.UpperLimit;
    LowerLimit 		= AnotherRange.LowerLimit;
    DomainType 		= AnotherRange.DomainType;
    VariableCategory 	= AnotherRange.VariableCategory;
  };
  
  // The destructor is a place-holder to ensure proper termination
  
  virtual ~Range (void)
  {};

  // --------------------------- ITERATOR -------------------------------------
  // If the range is countable it will also support an iterator to go over 
  // the elements in the range. This is an example on how an "iterator" can be
  // implemented for something that is not a container.

protected:
  
  class ElementPointer
  : public GeneralisedValuePointer
  {
  private:
    
    // The whole idea is this iterator remembers the value it points at,
    // and has a pointer to the range it points to (in order to throw 
    // exceptions if one tries to move the iterator outside the bounds of the 
    // range. The state of the iterator is its value and its range.
    
    NumericType 	   	 TheValue;
    const Range< NumericType > * TheRange;
    
  public:
    
    // The constructor takes the value and a pointer to the range as input
    // and stores these in the variables.
    
    ElementPointer( const NumericType StartValue, 
		    const Range< NumericType > * Owner )
    {
      TheRange = Owner;
      
      if ( TheRange->ElementQ( StartValue ) )
	TheValue = StartValue;
      else
	throw std::string("Range iterator: Value not part of range");
    }
    
    // Returning the value is simply returning the local variable. Note that
    // we have to check that this function is not called when the value is 
    // out of bounds.
    
    virtual GenericValue Value (void)
    {
      if ( ( TheRange->LowerBound() <= TheValue ) &&
	   ( TheValue <= TheRange->UpperBound() )     )      
	return TheValue;
      else
	throw std::string("Range iterator: Value requested out of bounds");
    }
    
    // Decrementing the value is trivial provided that it does not pass
    // the lower bound of the range
    
    virtual void Decrement (void)
    {
      if ( TheRange->LowerBound() < TheValue )
	TheValue--;
      else
	throw 
	std::string("Range iterator: Value decremented beyond lower bound");
    }
    
    // It is actually allowed to increment the value one step beyond the 
    // upper bound since this represents the "end" iterator.
    
    virtual void Increment (void)
    {
      if ( TheValue <= TheRange->UpperBound() )
	TheValue++;
      else
	throw 
	std::string("Range iterator: Value incremented beyond upper bound");
    }
    
    // Equality of two iterators means that they have to operate on the same
    // range object, and they have to have the same value.
    
    virtual bool Equals( const GeneralisedValuePointer * OtherValue ) const
    {
      const ElementPointer * Other
		      = dynamic_cast < const ElementPointer * > ( OtherValue );
      
      if ( ( TheRange == Other->TheRange ) && ( TheValue == Other->TheValue ) )
	return true;
      else
	return false;
    }
    
    // Copying this iterator means to create a new generalised object and 
    // transfer the state to that object.
    
    virtual GeneralisedValuePointer * MakeCopy (void) const
    {
      return new ElementPointer( TheValue, TheRange );
    }
        
    // Then a virtual destructor to make sure that the memory is correctly 
    // released
    
    virtual ~ElementPointer (void)
    { };
  };
 
  // The begin and end functions will only return iterators if this range is 
  // countable. An iterator is at the end() position if its value is one more
  // than the upper limit of the range.
  
public:
  
  virtual Domain::iterator begin ( void ) const
  {
    if ( std::is_integral< NumericType >::value )
      return Domain::iterator( new ElementPointer( LowerLimit, this ) );
    else
      throw std::string("Range: iterator only exists for countable ranges");
  }
  
  virtual Domain::iterator end ( void ) const
  {
    if ( std::is_integral< NumericType >::value )
      return Domain::iterator( new ElementPointer( UpperLimit+1, this ) );
    else
      throw std::string("Range: iterator only exists for countable ranges");
  }
};

// ----------------------------------------------------------------------------
// MultiRange
//-----------------------------------------------------------------------------
// The next domain type is one that is constructed by multiple ranges. This
// is slightly more challenging since we would expect:
//	1. The ranges are in sorted order
//	2. Ranges contained in other ranges should be eliminated
// 	3. Partly overlapping ranges should be merged
// Essentially this calls for the ranges to be stored in a set. However,
// this implies that we must tell the set what is meant by "ordering" of 
// ranges. Assuming that all ranges are non-overlapping, the ranges can be 
// ordered by their lower bounds.

template< typename NumericType >
class RangeComparator
{
public:
  
  static_assert( std::is_arithmetic< NumericType >::value,
		 "RangeComparator must have a numeric type!" );
  
  // This object is called as a function, hence its operator () is invoked
  // with the two ranges to compare. It only checks the lower bounds, and 
  // returns true if the lower bound of the first range is less than the 
  // lower bound of the second range.
  
  bool operator() ( const Range< NumericType > & FirstRange,
		    const Range< NumericType > & SecondRange ) const
  {
    return FirstRange.LowerBound() < SecondRange.LowerBound();
  }
};

// The multiple range class will overload the insert function offered by the
// standard set in order to check that the range requested for insertion does
// not overlap any of the currently stored ranges, or should be merged with 
// one or more of the existing intervals.
// 
// NOTE: A key assumption is that the numeric type is the same for all ranges,
// which may not be the case - but then the most general type should be used.
// This means that if both integer intervals and double intervals are defined,
// the actual variable having these intervals as its domain must be a real 
// number and double should be used for all intervals. The code will fail to
// compile if the numeric type is not the same for all ranges, since the 
// insertion function will be generated in only one version so inserting a 
// range with a different numerical type will fail.

template< typename NumericType >
class MultiRange 
: public Domain
{
private:
  
  // The set type will be needed to create iterators for elements, and 
  // given the length of the declaration it will be more convenient to have 
  // it declared as a type
  
  typedef 
  std::set< Range< NumericType >, RangeComparator< NumericType > > SetType;
  
  // The multi-range has a set of ranges that are ordered according to the 
  // range comparator. 
  
  SetType SubRanges;
  
  // The insert function will try to merge the given interval with one of 
  // the existing intervals if there is an overlap, only if this is not 
  // possible will the new range be inserted. In order to correspond to the 
  // standard set insertion function, also this returns a pair with an 
  // iterator to the inserted element and a boolean value to indicate if the
  // insertion was successful. The iterator is set to end() if the insertion
  // was unsuccessful.
  
  std::pair< typename SetType::iterator, bool >
  insert( Range< NumericType > NewRange )
  {
    // Define the pair to return after this operation
    
    std::pair< typename SetType::iterator, bool > ReturnStatus;
  
    // The search for overlapping intervals starts from the first of the 
    // intervals that are already stored.
    
    auto anInterval = SubRanges.begin();
    
    // Intervals to the left of the new range can immediately be skipped.
    // We know that an interval is to the left of the current interval if
    // its upper bound is less than the lower bound of the new interval
    
    while ( ( anInterval != SubRanges.end() ) && 
	    ( anInterval->UpperBound() < NewRange.LowerBound() ) )
      ++anInterval;
    
    // Now there are three cases to consider: 
    // 1) all intervals are less than the one we want to insert, i.e. we 
    //    reached the end of the set of ranges. In this case we can safely 
    //    insert the new interval.
    // OR
    // 2) The next existing interval's lower bound is larger than the new 
    //    interval's upper bound, i.e. the new interval lies between two 
    //    existing intervals or at least to the left of interval now pointed 
    //    to by the anInteval iterator.
    
    if ( ( anInterval == SubRanges.end() ) || 
	 ( anInterval->LowerBound() > NewRange.UpperBound() ) )
      ReturnStatus = SubRanges.insert( NewRange );
    else
    {
      // 3) The existing interval overlap with the new interval. Merging 
      //    the two intervals means forming a new interval whose lower bound
      //    is the minimum of the lower bounds of both intervals, and 
      //    the upper bound is the maximum of the upper bounds. This new 
      //    extended interval will replace the one stored in the list of 
      //    ranges. 
     
      Range< NumericType > ExtendedInterval(
	std::min( anInterval->LowerBound(), NewRange.LowerBound() ),
	std::max( anInterval->UpperBound(), NewRange.UpperBound() )  );
      
      SubRanges.erase( anInterval );
      ReturnStatus = insert( ExtendedInterval );
    }
    
    // Then we can return the outcome of this operation
    
    return ReturnStatus;
  };
  
public:
  
  // Out of convenience we shadow the value type declared by the set since
  // that will be a range and we will be more interested in the numeric type
  
  typedef NumericType value_type;
  
  // The test for the numeric type is really not necessary here since it 
  // will be done when in the range object, but for completeness it is 
  // included
  
  static_assert( std::is_arithmetic< NumericType >::value,
		 "Domain MultiRange must have a numeric type!" );
 
  // An element belongs to the multi-range if it belongs to one of the 
  // subintervals, so we scan the intervals until we find an interval 
  // containing the requested value. If no such interval exists, we return 
  // false.
  
  bool ElementQ ( const NumericType & Element ) const
  {
    for ( auto aRange = SubRanges.begin(); aRange != SubRanges.end(); ++aRange )
      if ( aRange->ElementQ( Element ) )
	return true;
      
    return false;
  };
  
  // The generic test is again just calling the specific element function
  // to do the actual test.
  
  virtual bool ElementQ ( const GenericValue & Element ) const
  {
    return ElementQ( boost::get< NumericType >( Element ) );
  };

  // The same trick as the one applied for the single range is necessary 
  // to ensure that the number of values function is only defined if the 
  // numeric type is countable. However, in this case we have to run over 
  // all sub-ranges and adding together the number of elements in them.
  
  virtual size_t NumberOfValues (void) const
  {
    if ( std::is_integral< NumericType >::value )
    {
      size_t Count = 0;
      
      for ( auto aRange = SubRanges.begin(); 
	    aRange != SubRanges.end();  ++aRange )
	Count += aRange->NumberOfValues();
      
      return Count;
    }
    else
      throw std::string("Multirange has uncountable ranges");
  };

  // The same trick is applied to the function to find a value by index as 
  // this only makes sense if the intervals are countable. In this case we
  // first have to scan for the right interval containing a value corresponding
  // to the provided index before we can return this. 
  
  virtual GenericValue EnumeratedValue (size_t Index) const
  {
    if ( std::is_integral< NumericType >::value )
    {
      auto aRange = SubRanges.begin();
      
      while ( (aRange != SubRanges.end()) && (Index >= aRange->NumberOfValues()) )
      {
	Index -= aRange->NumberOfValues();
	++aRange;
      }
      
      // At this point we have either a pointer to a sub-range containing the 
      // value to return, or we have an illegal index if we have exhausted the 
      // available sub-ranges.
      
      if ( aRange != SubRanges.end() )
	return aRange->EnumeratedValue( Index );
      else
	throw std::string("MultiRange: illegal element Index given!");
    }
    else
      throw std::string("Multirange has uncountable ranges");
  };

  // There are two functions to return the absolute bounds of the interval
  
  NumericType LowerBound (void) const
  {
    if ( SubRanges.size() > 0 )
      return SubRanges.begin()->LowerBound();
    else
      throw std::string("MultiRange: No sub-intervals defined!");
  }
  
  NumericType UpperBound (void) const
  {
    if ( SubRanges.size() > 0 )
      return SubRanges.rbegin()->UpperBound();
    else
      throw std::string("MultiRange: No sub-intervals defined!"); 
  }
  
  // There might also be a need to go through the sub-intervals, and so
  // it will be useful to be able to access directly the sub-ranges
  
  inline SetType * GetSubRanges ( void )
  {
    return & SubRanges;
  }
  
  // The random number for the multi-range is more involved. If the supported
  // type is countable, then a random integer will be used to select the 
  // element at the right position of the element in the global range of 
  // elements. When the ranges are continuous, a similar approach is taken
  // where a total range is created, and then the length of each interval 
  // will be subtracted from this overall length. Special attention should be
  // taken with integer variables as they could be unsigned.
  
  NumericType RandomValue( void ) const
  {
    if ( std::is_integral< NumericType >::value )
    {
      // In order not to duplicate code we will use the standard enumerated 
      // value, which returns a generic value that must be converted to the 
      // numeric type of the range.
      
      return boost::get< NumericType >( EnumeratedValue( 
	     Random::Number(0u, static_cast<unsigned int>(NumberOfValues()-1) )
	     )  );
    }
    else
    {
      // This must be implemented bottom-up since there is no standard method 
      // that can be used in this case.
      
      NumericType TotalLength = static_cast< NumericType >(0),
		  RandomLength;
      
      for ( auto aRange : SubRanges )
	TotalLength += aRange.UpperBound() - aRange.LowerBound();
      
      RandomLength = Random::Number( 0, TotalLength );
      auto aRange  = SubRanges.begin();
      
      while ( ( aRange != SubRanges.end() ) &&
	      ( RandomLength > (aRange->UpperBound() - aRange->LowerBound()) ) )
      {
	RandomLength -= aRange->UpperBound() - aRange->LowerBound();
	++aRange;
      }
      
      if ( aRange != SubRanges.end() )
	return aRange->LowerBound() + RandomLength;
      else
	throw std::string("Random number out of range!");
    }
  }
  
  // Defining the constraints for this domain is more difficult than 
  // for simple ranges. Consider as an example the case where the variable 
  // value x should be in either of the two discontinuous intervals [a,b] 
  // and [c,d]. Directly defining the set of constraints for the two intervals
  // yields a-x<=0 and x-b<=0 for the first and c-x<=0 and x-d<=0 for the 
  // second interval. However these constraints are logically conflicting and 
  // no value can satisfy all of them. Assuming that c > b so that the second
  // interval is above the first, and letting x=c satisfies the first, third
  // and the fourth constraint, but x-b<=0 is not satisfied since c-b > 0. 
  // This reasoning holds for any value in one of the two intervals: one 
  // constraint will not be satisfied. 
  // 
  // The Fundamental Theorem of Algebra comes to our aid. It states that
  // every non-zero, single-variable, degree n polynomial with complex 
  // coefficients has, counted with multiplicity, exactly n roots. In other 
  // words, any polynomial can be written as p(x)=w(x-r1)(x-r2)...(x-rn) 
  // where r1,..,rn are the roots (or zeros) of the polynomial and w is some
  // constant. If we identify the limits of the intervals with the roots,
  // we see that each interval will contribute with exactly two distinct roots,
  // since two interval boarders cannot equal because the intervals would have
  // been merged if they did. Hence, our polynomial will be of even degree.
  // An even degree polynomial p(x) will go towards infinity as x decreases 
  // below the first root, or if x increases above the last root. Assuming 
  // that the roots of the polynomial is sorted in increasing order, this again
  // implies that p(x)<=0 when x is in the interval [r1,r2] or [r3,r4]...
  // The polynomial can therefore be used as our constraint as it 
  // will be negative or zero whenever the variable value x is inside one 
  // of the legal sub-intervals.
  //
  // For the example in the first paragraph the constraint polynomial for the
  // two sub intervals would be:
  // p(x) = (x - a) (x - b) (x - c) (x - d)
  // = a b c d - a b c x - a b d x - a c d x - b c d x + a b x^2 + a c x^2 + 
  //   b c x^2 + a d x^2 + b d x^2 + c d x^2 - a x^3 - b x^3 - c x^3 - 
  //   d x^3 + x^4
  
  template < class VariableType >
  void DefineConstraints ( VariableType * TheVariable )
  {
    if ( std::is_floating_point< NumericType >::value )
    {
      make_inequality_constraint( [this, TheVariable](void){ 
	NumericType polynomial = 1.0; 
	
	for ( auto Interval = this->SubRanges.begin();
	      Interval != this->SubRanges.end(); ++Interval )
	{
	  polynomial *= ( TheVariable->Value() - Interval->LowerBound() ) 
			  * ( TheVariable->Value() - Interval->UpperBound() );
	}
	
	return polynomial;
      });      
    }
    else
      throw std::string("Constraints allowed only for continuous domains");
  }
  
  // The constructor takes a list of ranges, and inserts these in the 
  // set of sub ranges. 
  
  MultiRange( std::initializer_list< Range< NumericType > > InitialRanges )
  : Domain(), SubRanges()
  { 
    for (auto aRange = InitialRanges.begin(); aRange != InitialRanges.end();
         ++aRange )
      insert( *aRange);
    
    DomainType = Domain::Types::MultiRange;
    
    if ( std::is_integral< NumericType >::value )
      VariableCategory = Domain::Categories::Discrete;
    else
      VariableCategory = Domain::Categories::Continuous;
  };
  
  // There is also a variant of this constructor where the ranges are not 
  // explicitly given, but only a set of boundaries. These are understood 
  // as pairs of lower and upper limits for the successive intervals. 
    
  MultiRange( std::initializer_list< NumericType > InitialLimits )
  {
    // The list of limits is only valid if it contains an even number of 
    // elements
    
    if ( (InitialLimits.size() % 2) == 0 )
    {
      for ( auto aLimit = InitialLimits.begin(); 
	    aLimit != InitialLimits.end(); ++aLimit )
      {
	// The variables are necessary since some compilers may issue a 
	// warning if the operations are combined into one, like in the 
	// expression incrementing and reading the next value: *(++aLimit)
	
	NumericType From, To;
	From = *aLimit; ++aLimit; To = *aLimit;
	
	insert( Range< NumericType >( From, To ) );
      }
    }
    else
      throw std::string("MultiRange initialiser invalid");
    
    DomainType = Domain::Types::MultiRange;
    
    if ( std::is_integral< NumericType >::value )
      VariableCategory = Domain::Categories::Discrete;
    else
      VariableCategory = Domain::Categories::Continuous;    
  }

  // For completeness there is a copy constructor
  
  MultiRange( const MultiRange< NumericType > & OtherRange )
  : Domain(), SubRanges( OtherRange.SubRanges )
  {
    DomainType 		= OtherRange.DomainType;
    VariableCategory 	= OtherRange.VariableCategory;
  }
  
  // The virtual destructor is a place holder to ensure that the class is 
  // properly destroyed.
  
  virtual ~MultiRange(void)
  {}
  
  // --------------------------- ITERATOR -------------------------------------
  // The iterator for the multi range is similar to the iterator of the range,
  // and in addition it needs to know which sub-range currently providing 
  // the sub-interval limits. The implementation is therefore slightly more 
  // challenging
 
protected:
  
  class ElementPointer
  : public GeneralisedValuePointer
  {
  private:
    
    // The state is represented by the value of the iterator, the subinterval
    // it belongs to and the multi-range structure.
    
    NumericType       			TheValue;
    typename SetType::iterator 		SubRange;
    const MultiRange< NumericType > *   TheRange;
    
  public:
    
    // The constructor takes the start value and the multi range for which 
    // this element belongs to, and it will find the correct subinterval 
    // for this value.
    
    ElementPointer( const NumericType StartValue, 
		    const MultiRange< NumericType > * Owner )
    {
      TheRange = Owner;
      SubRange = TheRange->SubRanges.begin();
      
      while ( ( SubRange != TheRange->SubRanges.end() ) && 
	      !SubRange->ElementQ( StartValue )			)
	++SubRange;
      
      if ( SubRange != TheRange->SubRanges.end() )
	TheValue = StartValue;
      else
	throw std::string("Multi range iterator: Value not member of range");
    }
    
    // Returning the value is just to return the current value state
    
    virtual GenericValue Value (void)
    {
      return TheValue;
    }
    
    // Decrementing the value is more complex as this could result in three
    // situations: We stay within the current interval, we move to the interval
    // to the left of the current interval (its upper bound), or there are
    // no more intervals to move to and this request cannot be fulfilled. 
    
    virtual void Decrement (void)
    {
      NumericType NewValue = TheValue - 1;
      
      if ( NewValue < SubRange->LowerBound() )
      {
	if ( SubRange != TheRange->SubRanges.begin() )
	{
	  --SubRange;
	  TheValue = NewValue;
	}
	else
	  throw 
	  std::string("Multi range iterator decrement beyond lower bound");
      }
      else
	TheValue = NewValue;
    }
    
    // The same concerns must be taken when incrementing the value, although
    // is is possible to increment the value one item beyond the end of the 
    // last sub-interval to indicate "end()"
    
    virtual void Increment (void)
    {
      if ( SubRange != TheRange->SubRanges.end() )
      {
	// We have a legal situation, and can increment the value
	TheValue++;
	if ( TheValue > SubRange->UpperBound() )
	  ++SubRange;
      }
      else
	throw 
	std::string("Multi range iterator incremented beyond upper bound");
    };
    
    // We can assess equality by testing only that the multirange is identical
    // for the two iterators, and that they have the same value. Then it follows
    // that the sub-range must be identical.
    
    virtual bool Equals( const GeneralisedValuePointer * OtherValue ) const
    {
      const ElementPointer * Other
		      = dynamic_cast< const ElementPointer * >( OtherValue );
		      
      if ( (TheRange == Other->TheRange) && (TheValue == Other->TheValue) )
	return true;
      else
	return false;
    }
    
    // Copying means transferring the main state, i.e. value and range as the 
    // constructor of the copy object will identify the correct sub-range
    
    virtual GeneralisedValuePointer * MakeCopy (void) const
    {
      return new ElementPointer( TheValue, TheRange );
    }

    virtual ~ElementPointer (void)
    { };
  };

public:
  
  // Functions creating the iterator, i.e. the begin() and the end() of the 
  // multi-range.
  
  virtual Domain::iterator begin( void ) const 
  {
    if ( std::is_integral< NumericType >::value )
      return Domain::iterator( 
	     new ElementPointer( SubRanges.begin()->LowerBound(), this ) );
    else
      throw std::string("Multi range iterator requires countable type");
  };
  
  virtual Domain::iterator end  ( void ) const 
  {
    if ( std::is_integral< NumericType >::value )
      return Domain::iterator( 
	     new ElementPointer( (--SubRanges.end())->UpperBound(), this ) );
    else
      throw std::string("Multi range iterator requires countable type");
  };
};

//-----------------------------------------------------------------------------
// NumericList
//-----------------------------------------------------------------------------
// The numeric domain is a simple list of numbers and we use a template for  
// the actual numeric type. The static assert used below will create a compile
// error if the type used is not. Note that it also accepts the type to be
// boolean since the boolean class is implemented from this type.

template< typename NumericType >
class NumericList : public Domain
{
private:
  
  // The elements are kept in a private set of values.
  
  std::set< NumericType > ListElements;
  
public:
  
  // First the test that this list is used only with a numeric type
  
  static_assert( std::is_arithmetic< NumericType >::value,
		 "Domain NumericList must have a numeric type!" );

  // The numeric type is defined as the value type for this domain and used
  // by variables having this as the domain
  
  typedef NumericType	value_type;
  
  // we must also be able to asses that a particular number is part of the 
  // domain, and this is done by the the ElementQ function returning true
  // if the number belongs to the domain, false otherwise.

  bool ElementQ ( const NumericType & Element ) const
  {
    if ( ListElements.find( Element ) != ListElements.end() )
      return true;
    else
      return false;
  };
  
  // The generic value function calls the specific function to keep 
  // the code simple and avoid duplication.
  
  virtual bool ElementQ ( const GenericValue & Element ) const
  {
    return ElementQ( boost::get< NumericType >( Element ) );
  };

  // The number of elements in the list determines the number of possible 
  // values to use for this domain.
  
  virtual size_t NumberOfValues (void) const
  {
    return ListElements.size();
  };

  // The bounds of the range is the first and the last element stored provided
  // in the type of the set.
  
  inline NumericType LowerBound( void ) const
  {
    return *ListElements.begin();
  }
  
  inline NumericType UpperBound( void ) const
  {
    return *ListElements.rbegin();
  }
  
  // A value can be retrieved by its index. However since a set does not 
  // support direct access, we have to count the number of of elements from 
  // the start of the set.
  
  virtual GenericValue EnumeratedValue (size_t Index) const
  {
    if ( Index < ListElements.size() )
    {
      auto aValue = ListElements.begin();
	
      while (  (Index > 0) && (aValue != ListElements.end()) )
      {
	--Index;
	++aValue;
      }
      
      return *aValue;
    }
    else
      throw std::string("NumericList: Element index out of bounds");
  };

  // Selecting a random value is to pick one of the values in the set 
  // again this is implemented in terms of the enumerated value converted 
  // back to the numeric type
  
  NumericType RandomValue( void ) const
  {
    return boost::get< NumericType >( EnumeratedValue( 
      Random::Number( 0u, static_cast< unsigned int>( ListElements.size()-1 ) ) 
      ) );
  }
  
  // Then the constructor, which indicates what sort of domain this is
  
  NumericList( std::initializer_list< NumericType > InitialValues )
  : Domain(), ListElements( InitialValues )
  {
    DomainType 		= Domain::Types::NumericList;
    VariableCategory 	= Domain::Categories::Discrete;
  };
  
  // There is also a copy constructor
  
  NumericList( const NumericList< NumericType > & OtherList )
  : Domain(), ListElements( OtherList.ListElements )
  {
    DomainType 		= OtherList.DomainType;
    VariableCategory	= OtherList.VariableCategory;
  };
  
  // An interesting observation is that a range or multi range can be seen as a 
  // numeric list if the ranges involved are countable. These constructors
  // take care of that transformation by reading out the discrete data. The 
  // implementation of the two constructors is identical.

  NumericList( const Range< NumericType > & TheRange )
  : Domain(), ListElements()
  {
    static_assert( std::is_integral< NumericType >::value,
		"Numeric Lists can only be constructed from countable ranges");
    
    for ( size_t Index = 0; Index < TheRange.NumberOfValues(); Index++ )
      ListElements.insert( boost::get< NumericType >( 
			   TheRange.EnumeratedValue( Index ) ) );
  };
  
  NumericList( const MultiRange< NumericType > & TheRange )
  : Domain(), ListElements()
  {
    static_assert( std::is_integral< NumericType >::value,
		"Numeric Lists can only be constructed from countable ranges");
    
    for ( size_t Index = 0; Index < TheRange.NumberOfValues(); Index++ )
      ListElements.insert( boost::get< NumericType >(
				  TheRange.EnumeratedValue( Index ) ) );    
  };
  
  // Then the destructor is just a place-holder 
  
  virtual ~NumericList (void)
  { };
  
  // --------------------------- ITERATOR -------------------------------------
  // Given that the numeric list is implemented as a normal STL Set, we can 
  // again implement the iterator using the standard iterator for this set.  
  
public:
  
  virtual Domain::iterator begin ( void ) const
  {
    return Domain::iterator( 
    new STLElementPointer< std::set< NumericType > >( ListElements.begin() ) 
    );
  }
  
  virtual Domain::iterator end ( void ) const
  {
    return Domain::iterator( 
    new STLElementPointer< std::set< NumericType > >( ListElements.end() ) );
  }
    
};

// The constructors taking ranges can be used directly, but they had to force
// the type of the range to be equal to the type of the numeric list, i.e.
// it is not possible to generate the list based on the type of the range. 
// The following two generator functions does that and are useful as wrappers
// for temporary objects used in initialisation.
//
// NOTE: Care should be taken when making this conversion because large 
// intervals will result in large lists, and often it would then be more 
// convenient to actually work with the intervals.

template< typename RangeType >
NumericList< RangeType > MakeList( const Range< RangeType > & TheRange )
{
  return NumericList< RangeType >( TheRange );
}

template< typename RangeType >
NumericList< RangeType > MakeList( const MultiRange< RangeType > & TheRange )
{
  return NumericList< RangeType >( TheRange );
}

// ----------------------------------------------------------------------------
// Boolean
//-----------------------------------------------------------------------------
// The simplest domain a variable can have is either true or false. It is 
// implemented in terms of the NumericList setting its two elements to 
// false and true respectively. All of the functionality is handled by the 
// NumericList so we only have to provide the constructor.

class Boolean : public NumericList< bool >
{
public:

  Boolean( void )
  : NumericList< bool >({false, true})
  {
    DomainType = Domain::Types::Boolean;
  };

};

/*****************************************************************************
 Variables
 
 The general syntax for declaring a variable is 
 
 Variable< DomainType > VariableName( ID, Label, DomainInitialisation );
 
 The ID is an unsigned integer value that must be unique to the variable.
 The label can be any text string however it is strongly recommended that 
 the label is the same as the variable name. This will make it easier if the
 solver should need to pass feedback to the users about variables that could
 be assigned values within the limits of their domains to satisfy all 
 constraints. 
 
 The domain type is one of the domain types defined above, potentially with a 
 type qualifier, e.g. double, as in 
 
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
 
******************************************************************************/

// Variables have a unique reference known to the CP model. This reference is 
// a string describing the variable

typedef std::string VariableID;

// With the generic values we can universally implement the generic variable 
// class. The idea is that it has a generic set value function taking a generic
// value, and a function to return its generic value. The function to set the 
// value is virtual and should be overloaded by derived classes able to check
// this value against its domain. 

class GenericVariable
{
private:
  
  // All variables will have an unique identifier, its label in the model
  
  VariableID Label;
  
  // The type of the variable is stored as a typeid class that can be 
  // checked to find the type of the variable at run-time. Note that the 
  // type_info structure returned by typeid() can not be assigned or copied
  // so we have to use the type_index class instead as it can be assigned 
  // a typeid()
  
  std::type_index ValueType;
  
public:
  
  // There is a function to return this the type of this variable.
  
  std::type_index Type (void)
  {
    return ValueType;
  }
  
  // The function to set a value must be overloaded by the derived classes
  // in order to check the value against the domain of the variable.
  
  virtual bool SetValue ( const GenericValue & NewValue ) = 0;
  
  // An external class will need to know about the domain of possible values
  // in order to set sensible values. The following function returns a pointer
  // to the domain for this variable, and it must be provided by derived 
  // classes as this base class has no domain.
  
  virtual const Domain * GetDomain ( void ) const = 0;
  
  // The most important topic dealing with deliverables is to know if they 
  // are continuous or discrete. This is specified by the domain, and the 
  // domain is known when the derived class is instantiated.
  
  virtual const Domain::Categories GetCategory (void) const = 0;
  
  // The value stored can also be returned as a generic value. This may not 
  // serve much purpose unless we actually know how to convert this generic
  // value to the basic type it represents. The type can either be checked
  // using the type function on this generic variable class, or it can be
  // checked using the type function on the returned generic object, i.e. 
  // if ( ReturnedValue.type() == typeid( double ) ) ...
  
  virtual const GenericValue GetValue ( void ) const = 0;

  // Similarly there are two functions to return the upper and the lower
  // bounds of a variable. This will give a value for all numeric ranges,
  // and for strings the lower bound will be one, corresponding to the first
  // element in the list, and the upper bound will be the number of strings.
  
  virtual double UpperBound ( void ) const = 0;
  virtual double LowerBound ( void ) const = 0;
  
  // Then there are two simple functions to obtain the ID and the label of 
  // this variable.
  
  inline std::string GetLabel (void) const
  {
    return Label;
  };
  
  // When a solution is found the value of the variable is printed in the 
  // format <label> <value>. It can be printed either to a file or to the 
  // console. This is deferred to the actual variable type since it is 
  // unnecessary to first convert it into a generic value with GetValue. 
  
  virtual void PrintValue (std::ostream & Output) const = 0;
  
  // The constructor
  
  GenericVariable( const VariableID & TheLabel, 
    const std::type_index & TheType  ) 
  : Label( TheLabel ), ValueType( TheType )
  { };
  
  // The destructor is trivial just making sure that everything is properly
  // destroyed and deallocated (even though we are not using dynamic allocation
  // for this class.
  
  virtual ~GenericVariable( void )
  { };
};

//-----------------------------------------------------------------------------
// Variables struct
//-----------------------------------------------------------------------------
// References to all variables are kept in a global map taking care of 
// looking up a generic variable based on the variable's label. This 
// structure is declared in the Interface.cpp file.

typedef std::map< VariableID, GenericVariable * > VariableMap;

extern VariableMap Variables;

// A pointer to this variable map can be obtained from calling the below 
// function if the structure is not readily available (i.e. if dynamic 
// binding is used).

extern "C" VariableMap * GetVariables (void);

//-----------------------------------------------------------------------------
// Variable values
//-----------------------------------------------------------------------------
// Obtaining the actual value of a variable is achieved with a value function
// acting on the variable. This allows us to ensure that the right basic type
// is returned. This is possible because C++ allows the return type of the 
// function to be defined after the argument list and consequently it can be 
// based on compile time knowledge of the arguments passed.

template < class VariableType >
auto Value ( const VariableType & TheVariable )
-> typename VariableType::value_type
{
  return TheVariable.Value();
};

// It shold be possible to define a similar function taking a generic 
// vairable pointer, which is set to a base class, and obtain the return 
// value of that pointer. There are several ideas that might work for this:
// Using typeid(), see http://en.wikipedia.org/wiki/Typeid 
// Using Run-Time Type Info (RTTI), see 
// http://en.wikipedia.org/wiki/Run-time_type_information
// i.e. using dynamic cast, perhaps with exceptions see
// http://en.wikipedia.org/wiki/Dynamic_cast
// Using Curiously Recurring Template Patterns, see
// http://en.wikipedia.org/wiki/Curiously_recurring_template_pattern
//
// A "visitor" could be a solution, however the problem is how to know which
// type to return.

//-----------------------------------------------------------------------------
// Variable template
//-----------------------------------------------------------------------------
// The Variable will then ensure that assignments to this variable is 
// conforming to the domain specification, and provide a way to get the value
// as a simple type.

template < class DomainType >
class Variable : public GenericVariable
{
public: 
  
  // The type of the return value as given by the domain is also the 
  // value type for this variable, so we export it for easy reference
  
  typedef typename DomainType::value_type value_type;  
  
private:
  
  // The variable has a domain
  
  DomainType TheDomain;
  
  // The real purpose of the variable is to be assigned a value compatible
  // with the value type of the domain.
  
  value_type TheValue;
  
  // The constructors need to have different signatures, but they will 
  // execute the same initialisation operations: Registering the variable 
  // with the global list of variables, setting the domain based constraints
  // and setting an initial value for the variable.
  
  inline void Initialise( bool 		InitialValueGiven,
			  value_type 	InitialValue			)
  {
    Variables.insert( 
      std::pair< VariableID, GenericVariable *>( GetLabel(), this ) );

    // If the domain is continuous the corresponding constraints will be 
    // defined.

    if ( TheDomain.Category() == Domain::Categories::Continuous )
      TheDomain.DefineConstraints( this );
    
    // The initial value is either given or set as a random value from the 
    // domain.
    
    if ( InitialValueGiven )
      TheValue = InitialValue;
    else
      TheValue = TheDomain.RandomValue();
  }
  
public:
  
  // A new value can be assigned to the variable by the set value function
  // which will first use check that the proposed value is a part of the 
  // domain before storing it.
  
  virtual bool SetValue( const GenericValue &  NewValue )
  {
    if ( ( NewValue.type() == typeid(value_type) ) &&
         ( TheDomain.ElementQ( boost::get< value_type >( NewValue ) ) )
       )
    {
      TheValue =  boost::get< value_type >( NewValue );
      return true;
    }
    else
      return false;
  };
  
  // There is also an overloaded set value to be used if the correct 
  // type is used for the argument (and not a generic value)
  
  bool SetValue( const value_type & NewValue )
  {
    TheValue = NewValue;
    return true;
  }
  
  // If one has a pointer to this variable type or the object itself one can 
  // read out the value by directly accessing the value function
  
  inline const value_type Value ( void ) const
  {
    return TheValue;
  };
  
  // If one only has a pointer to the generic variable, one can still get 
  // the value of the variable as a generic value, although it will be 
  // more difficult to use than the direct value.
  
  const GenericValue GetValue ( void ) const
  {
    return GenericValue( TheValue );
  }
  
  // The functions returning the upper or lower bounds depends requires the 
  // value type to be numeric. 
  
  virtual double UpperBound( void ) const
  {
    if ( std::is_arithmetic< value_type >::value )
      return static_cast< double >( TheDomain.UpperBound() );
    else
      throw std::string("Upper bound requires arithmetic type"); 
  }
  
  virtual double LowerBound( void ) const
  {
    if ( std::is_arithmetic< value_type >::value )
      return static_cast< double >( TheDomain.LowerBound() );
    else
      throw std::string("Lower bound requires arithmethic type");
  }
  
  // In order to set good values other classes will need to know about the 
  // domain of this variable and hence there is a function to return a 
  // pointer to the domain.
  
  virtual const Domain * GetDomain( void ) const
  {
    return & TheDomain;
  }
  
  // The category (discrete or continuous that a variable may have is known
  // only by the domain of this variable. 
  
  virtual const Domain::Categories GetCategory (void) const
  {
    return TheDomain.Category();
  }

  // Output of the variable's value to a given stream
  
  void PrintValue (std::ostream & Output) const
  {
    Output << GetLabel() << " " << TheValue << std::endl;
  }
  
  // The constructor takes the numerical identification, the textual label,
  // and the various parameters for the domain type and forward these to the 
  // domain for storing these elements. Neither the number of variables nor 
  // the types expected by the domain are known at this point, so we simply 
  // forward whatever follows the identifier and the label.
  
  template < class... DomainInitialisers >
  Variable ( const VariableID & TheLabel,
	     bool InitialValueGiven, value_type InitialValue,
	     DomainInitialisers... DomainArguments )
  : GenericVariable( TheLabel, typeid(value_type) ),
    TheDomain( DomainArguments... )
  { 
    Initialise( InitialValueGiven, InitialValue );
  };
  
  // Apparently initialiser lists, e.g. {2,4,5} need to be treated differently
  // so there is a separate constructor for these
  
  template < class InitialiserType = value_type >
  Variable ( const VariableID & TheLabel,
	     bool InitialValueGiven, value_type InitialValue,
	     std::initializer_list< InitialiserType > DomainArguments )
  : GenericVariable( TheLabel, typeid(value_type) ),
    TheDomain( DomainArguments )
  { 
    Initialise( InitialValueGiven, InitialValue );
  };
      
  // Only simple list of values can be given as initialisers. If the type of
  // domain requires more elaborate initialisation, then this domain must be 
  // initialised separately and passed as an argument to the constructor.
  
  Variable ( const VariableID TheLabel,
	     bool InitialValueGiven, value_type InitialValue,
	     DomainType & GivenDomain )
    : GenericVariable( TheLabel, typeid(value_type) ),
      TheDomain( GivenDomain )
  {
    Initialise( InitialValueGiven, InitialValue );
  }
  
  // The destructor is again declared as virtual to ensure proper destruction
  // of all classes
  
  virtual ~Variable (void)
  { };
};

/*****************************************************************************
 Metrics

 A metric is something that is based on monitoring of the running application
 and can be both a raw set of samples, or an operation on another metric. 
 Thus there can be one metric measuring the CPU consumption of an artefact at
 regular intervals, and then there can be another metric defined that is the 
 windowed average of this CPU consumption. 
 
 The crux is that these are defined at the level of the execution environment
 and stored in the metadata database. To use these metrics in the constraints,
 we only need to know the ID of the metric. This class therefore needs to 
 interface with the Connected Data Objects (CDO) repository [3], which is a 
 distributed shared model framework for Eclipse Modelling Framework (EMF) 
 models and meta models. The CDO interface is in Java, so direct access would
 need to use the compiled Java Native Interface [5].

 However a better approach is a server-client interface. The individual 
 metrics objects should "subscribe" to changes from the metadata collector. 
 Immediately when a monitored quantity is updated, the metadata collector 
 server will distribute the new updated value to all subscribing clients. This
 basic mechanism is implemented using Zero Message Queue (ZMQ)[5]: 
 
 1) The client subscribes to the metrics collector with a message containing
    the ID of the metric it subscribes to. The server must infer the IP
    address of the client from this message.
 2) When the value of the metric changes, the metrics collector sends
    a message as a simple string to all subscribers of this value with two 
    fields: The ID of the metric and the new value.
    
 The implementation is split in two levels. One to deal with the subscribtion
 and the ZMQ, and one defining the metrics to be used in constraints and the 
 utility function. Only the latter is part of the solver interface and 
 defined and implented here. The former interface is implemeted in the file
 Metrics.cpp
 
 References:

 [3] http://projects.eclipse.org/projects/modeling.emf.cdo
 [4] http://gcc.gnu.org/onlinedocs/gcj/About-CNI.html
 [5] http://zeromq.org/
  
******************************************************************************/

// At the time of writing it is not known if the identification tag for a 
// metric will be a string or a number or something else, so it is defined as
// a generic type to be properly defined once we know the format supported 
// by the metadata database.

typedef std::string 	MetricsID;

//-----------------------------------------------------------------------------
// Metrics registry
//-----------------------------------------------------------------------------


//-----------------------------------------------------------------------------
// Metrics base class
//-----------------------------------------------------------------------------
// The Metric Base class register each metric with the metrics collector
// and is the interface for the SetValue function on the metric. 

class MetricBase
{
private:
  
  // The identification of the metric is set in the constructor as it should
  // not change over the lifetime of the metric.
  
  MetricsID ID;
  
  // There is a map of all the metrics in order to update the the right metric
  // by its ID. It is static so that it can be accessed by all metrics. 
  // ===> NOTE: All static variables of the metrics class are defined  <===
  // ===> in the Interface.cpp file 				       <===
  // There is an issue with initialisation of static objects in a class and 
  // the initialisation of class objects when the two are in different files! 
  // E.g. if the static registry (a map) is in the file "Metric.cpp" and the 
  // actual metric object is created in the file "Interface.cpp", then there 
  // is no guarantee that the objects in "Metric.cpp" will be initalised before 
  // the objects in "Interface.cpp", so one could end up registering the metric 
  // to an uninitialised map. segmentation fault!
  // This is known as the "static initialization order fiasco" and discussed on
  // the page http://libcw.sourceforge.net/global/global.html

  static std::map< MetricsID, MetricBase * > Metrics;

  // Algorithms of mathematical solvers will not be able to handle constants
  // of equations that will change during the optimisation process. The metrics
  // are typically such constants, and so there is a flag to indicate whether
  // the value is allowed to change or not. This is shared by all metrics. 
  // As the freeze event can be called from several places, a single boolean 
  // flag is not sufficient - we need to keep track of how many users have
  // requested the freeze so that we can release the hold only when the last
  // Freeze is released. Furthermore, since the Freeze and UnFreeze events 
  // can come from different threads, we must make sure that there is no
  // race condition on this variable, i.e. it must be protected by a mutex
  
  static unsigned int Frozen;
  
  // The lock is necessary to ensure that no other thread is freezing the values
  // while they are updated to the last known good value. A lock is also 
  // necessary for the structure storing the metrics because it will be used
  // by the thread serving the messages from the metrics collector and the 
  // thread updating the values after an unfreeze event. 
  
  static std::mutex FrozenLatch, LookupLatch;
  
protected:
  
  // The metric must be registered with the Metrics Collector in order to 
  // start receiving new value updates. This registration is done by a separate
  // function that can be called from the constructor of derived classes only
  // after the value object has been initialised. In order to stor the pointer
  // to the right metric class (and not to the base class), the pointer is
  // passed as an argument to the function.
  
  void RegisterMetric( MetricBase * TheMetric );

  // When the metrics are released, they must be updated. The reason is that 
  // if the metric's value is only updated when it is used or updated, then 
  // infrequently updated metrics may be frozen again before any of these 
  // events happen.
  
  virtual void Update( void ) = 0;
  
  // When asked for the metric value, the metric need to know if it should 
  // update its value because nothing is frozen, or if it should reuse the 
  // its old value. The status of the global freeze is only known at the 
  // base class level, and so the derived metric will need to ask the base
  // class to conditionally update the value (by returning a call on the above
  // Update function).
  
  inline void ConditionalUpdate( void )
  {
    FrozenLatch.lock();
    
    if ( Frozen == 0 )
      Update();
    
    FrozenLatch.unlock();
  }
  
public:
  
  // The actual value is sent to the subscribing metric object by the 
  // set value function which has no meaning for the this base class.
  // Note that this function will be called from the constructor, and so 
  // it must be possible to set the value before the constructor of the derived
  // class has executed.
  
  virtual void SetValue( const std::string & ValueString ) = 0;  
  
  // There is a special interface function in order to set the value of a 
  // metric by its ID. Basically it looks up the right metric in the map and
  // calls the SetValue function.
  
  inline static void UpdateValue( const MetricsID & TheID, 
				  const std::string & ValueString )
  {
    LookupLatch.lock();
    Metrics[ TheID ]->SetValue( ValueString );
    LookupLatch.unlock();
  }
  
  // When a mathematical solver starts, the metric values must be kept fixed
  // during the run of the algorithm and the evaluations of variables and 
  // constraints. There are two static functions to toggle the flag used to 
  // indicate if the value reported by the metrics is allowed to change or 
  // not.
  
  inline static void Freeze( void )
  {
    FrozenLatch.lock();
    Frozen++;
    FrozenLatch.unlock();
  }
  
  // The function unfreezing the metrics will make sure that all metrics are
  // updated to their most recently received value.
  
  inline static void UnFreeze( void )
  {  
    FrozenLatch.lock();
    LookupLatch.lock();
    
    if ( --Frozen == 0 )
      for ( auto aMetric : Metrics )
	aMetric.second->Update();
    
    LookupLatch.unlock();
    FrozenLatch.unlock();
  }
    
  // The constructor takes the ID of the metric and stores this. 
  
  MetricBase( const MetricsID & TheID ) 
  {
    ID = TheID;
  }
  
  // It also has a virtual destructor to properly deallocate the metric
  
  virtual ~MetricBase ()
  {};
};

//-----------------------------------------------------------------------------
// Metrics
//-----------------------------------------------------------------------------
// The actual metric takes as a parameter its value type, i.e. the type 
// of value returned by the metric. This is typically a double, but could also 
// an integer if the metric counts something. The only requirement is that 
// this is arithmetic.

template < typename ValueType >
class Metric : public MetricBase
{
private:

  // Abort the compilation if the type is not aritmetic.
  
  static_assert( std::is_arithmetic< ValueType >::value,
		 "The type of the metric must be numeric!" );
  
  // The value of the metric can theoretically be read by one application 
  // thread at the same time another thread attept to update the value based
  // on the receipt of a new value. The standard way to handle this would be
  // to protect the variable with a mutex lock in order to ensure that only 
  // one thread could read or write the variable at the same time. However,
  // a mutex is quite costly given that we are here dealing with only a 
  // few bytes of data. Most processors have built in locks for known data 
  // types preventing multiple thread access at the CPU level. Such mechanisms
  // are supported by the atomic library in C++, which in the worst case 
  // degenerates to plain mutex control, but in the best case can be much 
  // faster.
  
  std::atomic< ValueType > CachedValue;
  
  // When an optimisation algorithm runs, all metrics should report a 
  // consistent value even though the cached value will continue to be 
  // updated in the background. The value to report is kept in a copy 
  // of the cached value.
  
  ValueType FrozenValue;
 
public:

  // The value type is also defined in case some external entity needs this
  
  typedef ValueType value_type;
  
  // The update function simply sets the frozen value to the cached value,
  // whatever that may be in this particular moment. Note that the cached 
  // value is updated by a different thread receiving the subscriptions from 
  // the metrics collector, so it may have changed "in the background"
  
  virtual void Update( void )
  {
    FrozenValue = CachedValue.load();
  }
  
  // The constructor takes the ID of the metric and the last value known for 
  // the metric as obtained from the metadata database at the time of exporting
  // the model. For frequently updated metrics this value can be outdated by
  // the time the metric is constructed. In this case, a new update will soon
  // overwrite the old value. Should the metric be infrequently updated, then 
  // it is very unlikely that a new value will arrive for the time interval 
  // between the export of the model and the start of the solver. Still, the 
  // metric value object must be initialised before we can start reviving any
  // values to it, so the register metric function can only be called 
  // after the cached value has been initialised.
    
  Metric ( const MetricsID & TheID, ValueType LastKnownValue ) 
  : MetricBase( TheID ), CachedValue( LastKnownValue )
  { 
    RegisterMetric( this );
    Update();
  }
    
  // The value function is trivial as it just returns the stored value, 
  // however it must check if the live value should be reported or just the 
  // frozen value.
  
  inline ValueType Value( void )
  {
    ConditionalUpdate();
    
    return FrozenValue;
  }

  // The function receiving a new value string will convert this string 
  // into the type of the value stored for this metric. A minor complication
  // is that the stream input operator may not be overloaded if the value
  // type is real, and so the solution is convert the string to a value, and
  // then assign this value to the metric value.
  
  virtual void SetValue ( const std::string & ValueString )
  {
    std::istringstream GivenValue( ValueString );
    ValueType NewValue;
    
    GivenValue >> NewValue;
    CachedValue = NewValue;
  }  
  
  // The destructor does nothing but allows the atomic object to be removed
  
  virtual ~Metric()
  {}
};

/*****************************************************************************
 Objective function

 The last part of the optimisation problem is the objective function. It is 
 defined like a variable, and evaluates to a real number. It is therefore well
 supported by the standard function object. Note that it takes no arguments 
 as it is supposed to capture and operate on the defined variables and metrics.
 
 The utility function is defined in terms of this objective function, but
 specialised with a particular functional combination of the variables and 
 the metrics. 
******************************************************************************/

typedef std::function< double(void)> ObjectiveFunction;
 
extern ObjectiveFunction UtilityFunction;

/*****************************************************************************
 END OF NAMESPACE Solver
******************************************************************************/
} 	// End of namespace Solver
#endif 	// SOLVER_INTERFACE