/*=============================================================================
  Command Line Options

  The command line options are described in the header file for this class.
  The constructor parsing the given command line is implemented in this file.

  Copyright (c) 2019 Geir Horn, University of Oslo, Geir.Horn@mn.uio.no

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include <iostream>

#include "CommandOptions.hpp"

LASolver::CommandLineOptions::CommandLineOptions( int argc, char ** argv )
: Description("Allowed options"), Values()
{
	// The options are defined first with long and short option name, value type
	// and optional value or a flag to indicate that the option is required.

	Description.add_options()
	( "help,h", "Produce this help message" )
	( "Name,@", cmd::value< std::string >()->default_value( "MELODIC" ),
			"The name of the endpoint"	)
	( "Prefix,x", cmd::value< std::string >()->default_value( "AMQ:" ),
			"Prefix to indicate the protocol" )
	( "AMQServer,B", cmd::value< std::string >()->required(),
			"IP address of the AMQ server" )
	( "AMQPort,P", cmd::value< std::string >()->default_value( "61616" ),
			"The port on the AMQ server" )
	( "SessionServer,S", cmd::value< std::string >()->default_value( "SessionLayer" ),
			"Internal name for the Session Layer server for the AMQ interface" )
	( "PresentationServer,P", cmd::value< std::string >()->default_value( "PresentationLayer" ),
			"Internal name for the Presentation Layer server for the AMQ interface" );

	// The command line is then parsed for these options and the parser will
	// throw and exception if any of the required options are not given

	cmd::store( cmd::parse_command_line( argc, argv, Description), Values );

  // Printing the description of the options if the help option is present

  if ( Values.count("help") > 0 )
  {
		std::cout << Description << std::endl;
		exit( EXIT_SUCCESS );
	}

	// Is this right at the end of the constructor?
	cmd::notify( Values );
}
