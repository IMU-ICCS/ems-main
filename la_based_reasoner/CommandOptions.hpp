/*=============================================================================
  Command Line Options

  The LA solver is started with a set of command line options. These can be
  given in standard Linux form as either long forms or short forms. They are
  described above, but --help or -? will produce a short description of the
  available options.

  The implementation uses the Boost Program Options package to parse the
  command line, and this parsing is internal (see the source file) and so
  all option values can be accessed by dedicated class interface functions
  returning the found value after the Command Line Options class has been
  constructed on the command line arguments.

  Required options:

  --AMQServer <ip> OR -B <ip>
		  The internet protocol (IP) address of the AMQ server (or message broker).
		  The IP can also be given symbolically and resolved by a DNS lookup.

  Optional options:

  --Name <string> or -@ <string>
		  Used to give the LA solver a name on the broker. The name must be one
		  single word, and will be used for external addresses of the form
		  <something>@name. The default name is MELODIC
  --Prefix <string> or -x <string>
		  The prefix is used on the name to indicate the protocol. The default
		  value is AMQ: and so the external addresses of the local protocol layer
		  actors are <something>@<prefix><name>, and the default will be
		  <something>@AMQ:MELODIC
  --AMQPort <n> OR -P <n>
		  The port on the AMQ server (or message broker) on the previously given
		  IP where the AMQ listens for messages. By default this is 61616
  --SessionServer <name> or -S <name>
		  The internal name to be used for the Session Layer server for the AMQ
		  layer. The name defaults to "SessionLayer". The layer server will have
		  have the full identifier <prefix><name>, and so by default it will be
		  called "AMQ:SessionLayer"
	--PresentationServer <name> or -P <name>
		  The internal name to be used for the Presentation Layer server for the
		  AMQ interface. The name defaults to "PresentationLayer". The layer server
		  will have the full identifier <prefix><name>, and so by default it will
		  be called "AMQ:PresentationLayer".
	--help or -?
			Produces a short form of this explanatory text.

  Copyright (c) 2019 Geir Horn, University of Oslo, Geir.Horn@mn.uio.no

  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License,
  v. 2.0. If a copy of the MPL was not distributed with this file, You can
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#ifndef LA_SOLVER_COMMAND_LINE_OPTIONS
#define LA_SOLVER_COMMAND_LINE_OPTIONS

#include <string>                    // Standard strings

#include <boost/program_options.hpp> // Option parser

namespace cmd = boost::program_options;

namespace LASolver
{
class CommandLineOptions
{
private:

	// The options class must have an object describing the options and the
	// help messages generated

	cmd::options_description Description;

	// The values are stored in a map from option names to values

	cmd::variables_map Values;

public:

	// There is a set of interface functions to read out the values of the
	// command line options

	inline std::string EndpointName( void )
	{ return Values["Name"].as< std::string >(); }

	inline std::string Prefix( void )
	{ return Values["Prefix"].as< std::string >(); }

	inline std::string AMQServerIP( void )
	{ return Values["AMQServer"].as< std::string >(); }

	inline std::string AMQServerPort( void )
	{ return Values["AMQPort"].as< std::string >(); }

	inline std::string SessionServerName( void )
	{ return Values["SessionServer"].as< std::string >(); }

	inline std::string PresentationServerName( void )
	{ return Values["PresentationServer"].as< std::string >(); }

	// The constructor must have the argument count and the argument vector
  // and it will do all the command line parsing. It will not be possible to
	// construct the class with no arguments, or to copy the class.

  CommandLineOptions( int argc, char **argv );
  CommandLineOptions( void ) = delete;
  CommandLineOptions( const CommandLineOptions & Other ) = delete;
};

}      // Name space LA Solver
#endif // LA_SOLVER_COMMAND_LINE_OPTIONS
