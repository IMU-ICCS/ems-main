/*=============================================================================
  Discrete Variable Handler

  A problem with the current implementation is that it cannot yet handle 
  stochastic variables or discrete variables because the learning part is 
  not yet implemented. In order to test the work flow, discrete variables are
  replaced with continuous variables of type double defined over the range of 
  indices indicating the true discrete variable values. As an example: if the 
  double is given as 3.2 then this value is rounded to the index 3 and the 
  managed variable is assigned the third enumerated value from its domain, 
  whatever that value is.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef DISCRETE_VARAIABLE_HANDLER
#define DISCRETE_VARAIABLE_HANDLER

#include <cmath>
#include "Interface.hpp"

class DiscreteVariableHandler 
: public Solver::Variable< Solver::Range<double> >
{
private:
  
  // The pointer to the discrete variable we handle is stored.
  
  Solver::GenericVariable * DiscretVariable;
  
public:

  // The main action is the overloaded function that will round the given 
  // double value to an index value, find the corresponding value from the 
  // domain and assign this to the variable. The raw value is also stored as
  // the value of this "variable" for debugging purposes.
  
  bool SetValue( const double & NewValue )
  {
    if ( (LowerBound() <= NewValue) && (NewValue <= UpperBound()) )
    {
      size_t Index = static_cast<size_t>( round( NewValue ) );
      
      DiscretVariable->SetValue( 
	DiscretVariable->GetDomain()->EnumeratedValue( Index )
      );
      
      // Full qualification is needed since this function covers the function 
      // of the basic variable class.
      
      Solver::Variable< Solver::Range<double> >::SetValue( NewValue );
    }
   else
     throw std::string("Assigned variable out of bounds!");
   
   return true;
  }
  
  // The generic interface that operates on a generic value simply checks 
  // that the given value is really a double, and then passes this value 
  // to the above function to convert it to a useful discrete variable.
  // In contrast with the standard function that simply ignores an illegal 
  // value, this function will throw an exception since the solver should 
  // really only pass on double values.
  
  virtual bool SetValue( const Solver::GenericValue & NewValue )
  {
    if ( NewValue.type() == typeid(double) )
      SetValue( boost::get< double >( NewValue ) );
    else
      throw std::string("Non-real value given from the solver");
    
    return true;
  };
  
  // It is also necessary to overload the value reporting function to ensure 
  // that it is the value of the managed variable that will be returned
  
  virtual void PrintValue (std::ostream & Output) const
  {
    DiscretVariable->PrintValue( Output );
  }
  
  // The constructor ensures that the range covers the number of elements in 
  // the discrete set of the managed variable. 
  
  DiscreteVariableHandler( Solver::GenericVariable * ManagedVariable )
  : Solver::Variable< Solver::Range<double> >(
    ManagedVariable->GetLabel(), false, 0.0, 
    {0.0, static_cast<double>(ManagedVariable->GetDomain()->NumberOfValues()-1)}
  )
  {
    DiscretVariable = ManagedVariable;
    
    // Setting the initial value corresponds to finding the right index of the 
    // variable currently assigned to the managed variable. Since it is an 
    // invariant that the managed variable is discrete, we know that the value
    // assigned to this variable must be one of its possible values, thus 
    // the following scan to find the corresponding value should stop before 
    // the position is incremented outside of the bounds. 
    
    size_t Position = 0;
    
    while (  ( Position <= UpperBound() ) && 
	    !( ManagedVariable->GetDomain()->EnumeratedValue( Position ) == 
	       ManagedVariable->GetValue() ) )
      Position++;
    
    SetValue( static_cast<double>( Position ) );
  }
};

#endif // DISCRETE_VARAIABLE_HANDLER