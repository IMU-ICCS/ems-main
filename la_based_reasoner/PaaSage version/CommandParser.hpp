/*=============================================================================
  Command line parser
 
  The different parameters that can be given on the command line will be parsed
  by this class, and the values will be available through interface functions.
  The parameters are supported in both short or long form where both forms 
  take the same argument. Th folowing commands are available for the LA Solver:
  
  -m or --model <CDO Model ID>
    A string giving the reference to the application model in the CDO server.
    It is used to write back assigned values for the parameters.
  -mc or --metricscollector <IP address>
    The metric values are published by the metric collector and the solver 
    subscribes to these values on the port on the metric collector server,
    which is located on this address.
  -t or --threads <number>
    The number of threads the solver is allowed to use. This depends on the 
    CPU architecture of the host computer, and typically it makes no sense to
    use more threads than there are cores on the machine. However, as the 
    solver does a parallel search for solutions, it will be faster the more 
    threads it can use. The default value is 16 threads. Note that when 
    remote metrics are used, more threads could be added by the communication
    layer.
  
  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef COMMAND_LINE_PARSER
#define COMMAND_LINE_PARSER

#include <string>

// The parser is specific to the LA Solver and consequently belongs to that 
// name space

namespace Solver {

// The class defined here is mainly the interface, with most of the 
// implementation left for the source file
  
class CommandParser
{
private:
  
  // The data field values collected are stored for future use by external 
  // class users.
  
  std::string CDOModelID, 
	      MetricCollectorIP;
  
  unsigned int NoThreads;
  
public:
  
  // The commands supported do have unique keys
  
  enum class Cmd
  {
    Model, 
    Collector,
    Threads
  };
  
  // The constructor takes the same arguments as main
  
  CommandParser( int argc, char **argv );
  
  // Then there are inline interface functions to read the values of the 
  // parametrs obtained from the command line or their default values.
  
  inline std::string ModelID( void  )
  { return CDOModelID; }
  
  inline std::string CollectorIP( void )
  { return MetricCollectorIP; }
  
  inline unsigned int NumberOfThreads( void )
  { return NoThreads; }
};
  
} // End of namespace solver
#endif //COMMAND_LINE_PARSER