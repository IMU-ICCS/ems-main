/*=============================================================================
  Application
  
  The main purpose of the application actor is to wrap the application model,
	i.e. the variables, the metric values and the constraints. It therefore 
	implements the functionality of the variable registry. 
	
	During initialisation it informs the LA assigner about the discrete variables,
	and then it receives a set of variable assignments from the Solver actor 
	that has got a set of discrete variable assignments from the LA Assigner and 
	supplements these with the set of continuous variable assignments to find 
	a complete solution. 
	
	The solver will request separately evaluations of the equality constraints, 
	and the inequality constraints, or it can request an evaluation of the 
	application utility. In the latter case the application will set up a 
	'configuration' according to the protocol used to evaluate the utility and 
	send this configuration to the Utility Generator actor that may in turn 
	communicate with an external utility generator object for the utility 
	evaluation. The result of the evaluation is then returned to the requesting 
	solver.
	
	This structure is set up in order to allow branch-and-bound like algorithms 
	where multiple solver actors will run in parallel on various assignments of 
	the discrete variables. 
	
	Please see the main file for an overview of the various actors and the 
	algorithm they jointly implement.
	
  Copyright (c) 2018 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef LASOLVER_APPLICATION_ACTOR
#define LASOLVER_APPLICATION_ACTOR

#include <vector>           // To store variables
#include <memory>           // For smart pointers

#include "Actor.hpp"				// The Actor framework
#include "Domains.hpp"    	// The domain of a variable
#include "Variables.hpp"		// The variables defining the application
#include "Metrics.hpp"    	// The metrics defining the problem
#include "Constraints.hpp"	// To store the constraint functional expressions

#include "Solver.hpp"       // The continuous variable solver actor

namespace LASolver
{

class Application : virtual public Theron::Actor
{
	/*============================================================================

    Variable management

	============================================================================*/
	//
	// There must be a registry for the variables where the value elements are 
	// stored in standard vectors to allow them to be referenced by the index of 
	// the vector. This is defined as a class that is instantiated for each of 
	// the two classes of variables, the discrete variables and the continuous
	// variables.
	
private:
	
	class VariableStore : virtual public Configuration::VariableRegistry
	{
	protected:
		
		std::vector< Configuration::ValueElement > Variables;
		
	protected:
		
		// The standard functions used to define and remove variables
		
		virtual 
		void NewVariable( Configuration::ValueElement * TheVariable) override;
	
		virtual 
		void RemoveVariable( Configuration::ValueElement * TheVariable) override;
		
		// Then there is a function to check if there are any variables. This 
		// will fundamentally just call the counterpart of the variable vector.
		
		virtual bool empty( void ) override;
		
	public:
		
		// The constructor simply initialises the variable vector and the 
		// registry. Note that it must explicitly call the variable registry 
		// constructor since it is virtually inherited.
		
		VariableStore( void )
		: VariableRegistry(), Variables()
		{}
		
		// There is a virtual destructor to ensure correct destruction
		
		virtual ~VariableStore( void )
		{ }
	};
	
	// The continuous variables are nothing special in the LA solver and one 
	// just has to implement the function to set the values of the variables 
	// based on a vector of values
	
	class ContinuousStore
	: virtual public Configuration::VariableRegistry,
	  virtual public Configuration::Variables< 
									 Configuration::VariableType::Continuous >,
		virtual public VariableStore
	{
	public:
		
		virtual void SetValues( ValueVector & Values ) override;
		virtual ValueVector::size_type NumberOfVariables( void ) override;
		
		// The constructor must explicitly call the variable registry since it 
		// is virtually inherited.
						
		ContinuousStore( void )
		: VariableRegistry(),
		  Variables< Configuration::VariableType::Continuous >(),
		  VariableStore()
		{}
		
		// There is a virtual destructor to ensure that all base classes 
		// destruct properly.
		
		virtual ~ContinuousStore( void )
		{}
	};
	
	// The discrete variable registry will need to know the address of the 
	// LA Assigner actor in order to inform the LA Assigner of new variables 
	// or removed variables. This means that it needs to re-implement the the 
	// assignment functions from the variable registry.
	
	class DiscreteStore
	: virtual public Configuration::VariableRegistry,
	  virtual public Configuration::Variables< 
									 Configuration::VariableType::Discrete >,
		virtual public VariableStore
	{
	private:
		
		const Address AssignerActor;
		
		// However since the discrete registry is not an actor itself, it needs 
		// to use the send function of the application actor, and it therefore 
		// needs a pointer to the owning application actor.
		
		const Application * OwningActor;
		
	protected:

		virtual 
		void NewVariable( Configuration::ValueElement * TheVariable) override;
	
		virtual 
		void RemoveVariable( Configuration::ValueElement * TheVariable) override;
		
	public:

		virtual void SetValues( ValueVector & Values ) override;
		virtual ValueVector::size_type NumberOfVariables( void ) override;
		
		// The constructor takes the address of the assigner actor as argument and
		// stores this for the lifetime of the registry.
		
		DiscreteStore( const Address & TheLAAssignerActor, 
									 const Application * TheApplicationActor )
		: VariableRegistry(),
		  Variables< Configuration::VariableType::Discrete >(),
		  VariableStore(),
		  AssignerActor( TheLAAssignerActor ),
		  OwningActor( TheApplicationActor )
		{}
		
		// This means that there should not be a default constructor for this 
		// registry as it will not work without the address of the LA assigner 
		// actor and the owning actor
		
		DiscreteStore( void ) = delete;
		
		// The destructor is virtual in order to clean up all the virtual base 
		// classes.
		
		virtual ~DiscreteStore( void )
		{}
	};
	
  // Pointers to the variable registries are kept in order to operate on them 
	// by setting values and evaluating constraints.
	
	std::shared_ptr< ContinuousStore > ContinuousVariables;
	std::shared_ptr< DiscreteStore   > DiscreteVariables;
	
	// ---------------------------------------------------------------------------
  // Messages
  // ---------------------------------------------------------------------------
  //
	// The Solver actor optimises the continuous variables for a given assignment
	// of the discrete variables. Given that the set of intervals will create a 
	// continuous variable once the discrete choice of the sub-interval has been 
	// done, the solver needs to obtain the number of continuous variables and 
	// the upper and lower bounds of these from the application given the assigned 
	// values to the discrete variables.
	
	class ContinuousVariableBounds
	{
	public:
		
		Configuration::Variables< 
			Configuration::VariableType::Continuous >::ValueVector
			LowerBounds,
			UpperBounds;
	};

	// This message is returned from the message handler receiving the discrete 
	// variable value assignments
	
	void VariableBounds( const Solver::DiscreteVariableValues & Values, 
											 const Address ContinuousSolver );
	

	/*============================================================================

	  Constraint evaluations

	============================================================================*/
	//

	/*============================================================================

    Utility function evaluation

	============================================================================*/
	//

  /*============================================================================

    Constructor and destructor

	============================================================================*/
	//
  // The constructor takes the address of the LA assigner actor as parameter 
  // since this must be informed about the creation and deletion of variables.
  
  Application( const Address & TheLAAssignerActor );
	
	// The destructor is virtual to make sure that the actor terminates correctly
	
	virtual ~Application( void )
	{}
		
};     // End class Application 
}      // End name space LA Solver
#endif // LASOLVER_APPLICATION_ACTOR
