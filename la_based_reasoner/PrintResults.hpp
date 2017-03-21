/*=============================================================================
  Print Results

  Printing the variables is scanning over all continuous variables known to 
  the optimiser class and printing their string ID and their current values.
  However, in a concurrent environment other solvers may continue to search 
  for solutions and set the variable values even though there is one solution 
  that should be printed. Printing is therefore done only using static 
  information about the variables.
  
  An aditional complication is that the printing could be to a results file, 
  and this file could be global - and it could be input to further processing
  elsewhere. In order to protect these global resources, the printing will be
  done by an actor. 

  This again causes other problems, in that it could be situations where the 
  actor asking for the printing must wait for the priting to be done before 
  activities are continued. Thus the priting actor sends back a confirmation
  when the results have been printed.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/
  
#ifndef PRINT_RESULTS
#define PRINT_RESULTS

#include <cstdlib>
#include <string>
#include <iostream>
#include "Interface.hpp"
#include "Theron/Theron.h"
#include "EvaluationActor.hpp"

// The actor belongs to the solver name space as it exports the results 
// obtained by the Continuous Optimiser in the specific format specified 
// by the Optimise function.

namespace Solver {

// The Print Result class fundamentally provides a unique return message and
// the actual message handler to serve print requests. 

class PrintResults : public Theron::Actor
{
public:
  
  // The status of the print request is returned as an enum to be easily 
  // verified by the caller. If the post processing returned status zero, 
  // (or there is no post processing requested), the status will be Done,
  // otherwise it will be PostFailure.
  
  enum class Status
  {
    Done,
    PostFailure
  };
  
private:
  
  // The class is initialised with a flag indicating whether a status message
  // should be sent or not, and a string being the external command used 
  // as argument to a system call.
  
  bool 	      SendStatus;
  std::string PostProcessingCommand;
  
  // Since the same class can be used to print the results to the console and
  // to any other output stream, it stores the output stream to be used to 
  // present results. NOTE: The output stream has not copy constructor, and 
  // so it must be stored as a reference to a globally allocated stream. If 
  // it is identified with a file, it is important that the file is not closed
  // before this actor goes out of scope!
  
  std::ostream & Output;
  
  // The actual printing function handles a message with the format described
  // by the Continuous Optimiser: It is a vector where the first elements 
  // corresponds to the continuous variables, then follows the discrete 
  // variables, and the last two elements are the appended value of the 
  // utility function and the number of iterations needed to find a solution.
  
  void Print( const std::vector< EvaluationActor::VariableValue > & Results,
	      Theron::Address Requestor )
  {
    Status Outcome = Status::Done;
    
    if ( Results.size() == Variables.size() + 2 )
    {
      Output << "# UtilityValue = " << (++Results.rbegin())->TheValue 
	      << std::endl;
      Output << "# Evaluations  = " <<    Results.rbegin()->TheValue 
	      << std::endl;
      
      for ( const EvaluationActor::VariableValue & Assignment : Results )
	if ( Assignment.TheVariable != nullptr )
	  Output << Assignment.TheVariable->GetLabel() << " " 
		  << Assignment.TheValue << std::endl;
    }

    if ( PostProcessingCommand.size() > 0 )
      if ( std::system( PostProcessingCommand.c_str() ) != 0 )
	  Outcome = Status::PostFailure;

    if ( SendStatus )
      Send( Outcome, Requestor );
  }
  
public:
  
  // The constructor takes the framework in which this actor will execute, the
  // output stream to use for the printing, a flag to indicate whether a status 
  // should be sent or not to the requester, and potentially the post processing 
  // command. It initialises all variables, and registers the print function 
  // as the message handler. Note that the actor is named so it will be possible
  // to send it results by name without knowing its Theron address.
  
  PrintResults( Theron::Framework & TheFramework, 
		std::ostream & TheOutputStream,
		bool TheSendStatus = false, 
		const std::string ExecuteCommand = "")
  : Theron::Actor( TheFramework, "PrintResults" ), 
    PostProcessingCommand( ExecuteCommand ),
    Output( TheOutputStream )
  {
    SendStatus = TheSendStatus;
    RegisterHandler( this, &PrintResults::Print );
  }
};
  
  
} 	// End of namespace solver
#endif 	// PRINT_RESULTS