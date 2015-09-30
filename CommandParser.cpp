/*=============================================================================
  Command line parser
 
  The different parameters that can be given on the command line will be parsed
  by this class, and the values will be available through interface functions.
  The supported commands are listed in the header file.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <map>
#include <vector>
#include <string>
#include <algorithm>
#include <iostream>
#include <sstream>


#include "CommandParser.hpp"

using namespace std;
using namespace Solver;

// The commands are defined as a standard mapping in order to quickly convert
// a string to the unambiguous command.

const map< string, CommandParser::Cmd > CommandMap = 
{
  {"-m", 		 CommandParser::Cmd::Model     },
  {"--model", 		 CommandParser::Cmd::Model     },
  {"-mc",		 CommandParser::Cmd::Collector },
  {"--metricscollector", CommandParser::Cmd::Collector },
  {"-t", 		 CommandParser::Cmd::Threads   },
  {"--threads",		 CommandParser::Cmd::Threads   }
};

// The constructor parses the command line arguments, and sets the values 
// provided or the default values for the internal fields.

CommandParser::CommandParser(int argc, char** argv)
: CDOModelID(), MetricCollectorIP()
{
  // Setting default values for the parameters that have default values
  
  NoThreads = 16;

  // The arguments are first converted to proper strings. The first argument 
  // is omitted because it is simply the name of the application. 
  
  vector< string > Arguments( argv + 1, argv + argc );
  
  // The parsing is done by stepping through the arguments and picking out 
  // values.
  
  auto ArgumentValue = Arguments.begin();
  
  while ( ArgumentValue != Arguments.end() )
  {
    // First the command is read and converted to lower case

    string Command( *ArgumentValue );    
    transform( Command.begin(), Command.end(), Command.begin(), ::tolower );
    
    // Then this can be used to look up in the command map, and if known we
    // treat subsequent values to the command as verbatim values.
    
    auto GivenCommand = CommandMap.find( Command );
    
    if ( GivenCommand == CommandMap.end() )
    {
      cerr << "Invalid command line argument " << *ArgumentValue 
	    << " given!" << endl;
      cerr << "The following arguments (long form) are supported: " << endl;
      cerr << "--model <CDO Model ID>" << endl;
      cerr << "--metricscollector <IP address>" << endl;
      cerr << "--threads <number>" << endl;
      cerr << "Terminating" << endl;
      exit(1);
    }
    else
      switch ( GivenCommand->second )
      {
	case CommandParser::Cmd::Model :
	  if ( ++ArgumentValue != Arguments.end() )
	    CDOModelID = *ArgumentValue;
	  else
	    cerr << "--model must be followed by the model CDO reference name"
		<< endl;
	  break;
	case CommandParser::Cmd::Collector :
	  if ( ++ArgumentValue != Arguments.end() )
	    MetricCollectorIP = *ArgumentValue;
	  else
	    cerr << "--metricscollector must be followed by an IP address string"
		<< endl;
	  break;
	case CommandParser::Cmd::Threads :
	  if ( ++ArgumentValue != Arguments.end() )
	  {
	    istringstream Value( *ArgumentValue );
	    Value >> NoThreads;
	  }
	  else
	    cerr << "--threads must be followed by a number of threads" << endl;
	  break;
	default:
	  break;
      }
    
    // Move to the next argument in the list
    
    ++ArgumentValue;
  }
}
