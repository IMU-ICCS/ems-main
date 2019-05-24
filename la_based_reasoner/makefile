###############################################################################
#
# The LA Reasoner
#
# This file makes compiles the files for the LA Reasoner
#
# Author: Geir Horn, 2014 - 2019
#
###############################################################################

#
# Setting the standard definitions
#

CXX = g++
#CXX = clang

# The compiler is instructed to produce all warnings and provide output for
# the code to be debugged with GDB. This produces larger object files and if
# this is a concern the GDB switch can be dropped. The following flags were
# also previously needed:
# -Wformat-truncation=0

GENERAL_OPTIONS = -c -Wall -std=c++17 -ggdb -Wno-deprecated-declarations \
                  -Wno-sign-compare

# Optimisation -O3 is the highest level of optimisation and should be used
# with production code. -Og is the code optimising and offering debugging
# transparency and should be use while the code is under development

OPTIMISATION_FLAG =

# There LA solver directory and the standard user include is explicitly set
# as directories to ensure that the compiler uses these standard locations.

STANDARD_INCLUDE = -I. -I/usr/include

# It is useful to let the compiler generate the dependencies for the various
# files, and the following will produce .d files that can be included at the
# end. The -MMD flag is equivalent with -MD, but the latter will include system
# headers in the output (which we do not need here). The -MP includes an
# empty rule to create the dependencies so that make would not create any errors
# if the file name changes.

DEPENDENCY_FLAGS = -MMD -MP

# The linker flags include the use of POSIX threads as they are the underlying
# implementation of standard C++ threads and is explicitly needed on some
# systems.

LDFLAGS = -Wl,--allow-multiple-definition -ggdb -D_DEBUG -pthread

# The object files and the dependencies are kept in a separate directory
# relative to the top level.

OBJECTS_DIR = Bin

#------------------------------------------------------------------------------
#
# External used frameworks
#
#------------------------------------------------------------------------------
#
# These locations have been made relative to work independent of the different
# machines used for this development. Essentially they are all located at the
# same level as the MELODIC upperware code base.
#
# The first framework is the Theron++ actor framework that is maintained as
# a separate library file to speed up compilation.

THERON ?= ../../Theron++
THERON_INCLUDE = -I $(THERON)
THERON_LIB = $(THERON)/Theron++.a

# The Learning Automata framework is an essential part of the functionality
# as it provides the stochastic learning for discrete configuration variables.

LA_FRAMEWORK ?= ../../LA-Framework
LA_INCLUDE = -I $(LA_FRAMEWORK)

# In order to read the metric values from the AMQ message broker serving the
# MELODIC metric system, some non-standard include locations are needed, and
# the appropriate libraries must be added for linking. The SSL library -lssl is
# not explicitly used, but may be needed as the AMQ interface supports
# encrypted transfer between the AMQ server and the clients.

AMQ_INCLUDE = -I /usr/include/activemq-cpp-3.9.4/ -I /usr/include/apr-1
AMQ_LIB     = -l activemq-cpp

# The Google protocol buffers are used to exchange configurations with the
# Utility Generator and this requires to link against the protocol buffer
# library

PROTOBUF_LIB = -l protobuf

# The JsonCpp library is used to parse metric value messages arriving on
# AMQ topics and its library must be included in the linking of executables

JSON_LIB = -l jsoncpp

# The Armadillo linear algebra library is used to represent matrices and
# some derived vectors, and to do some linear algebra operations. It needs to
# be told to use C++11, although there should not be an option by now.

ARMADILLO_FLAGS = -DARMA_USE_CXX11

# Boost is used around in the code and both boost system and boost options
# are linked as libraries.

BOOST_LIB = -l boost_system -l boost_program_options

# The Armadillo library uses functions from linear libraries and also the
# GNU Scientific Library can be used. Continuous variables are solved using
# algorithms from the NLopt library. The C++ version of NLopt my require
# a special library (in general just -lnlopt should be sufficient)

SCIENCE_LIB ?= -l gslcblas -l nlopt_cxx -l m

#------------------------------------------------------------------------------
#
# Combined flags
#
#------------------------------------------------------------------------------
#
# Setting up the combined macros based on the above definitions

CXXFLAGS = $(DEPENDENCY_FLAGS) $(OPTIMISATION_FLAG) $(GENERAL_OPTIONS) \
           $(ARMADILLO_FLAGS)

INCLUDE_DIRECTORIES = $(STANDARD_INCLUDE) $(THERON_INCLUDE) $(LA_INCLUDE) \
                      $(AMQ_INCLUDE)

LINKER_LIBS = $(THERON_LIB) $(AMQ_LIB) $(PROTOBUF_LIB) $(JSON_LIB) \
              $(BOOST_LIB) $(SCIENCE_LIB)

#------------------------------------------------------------------------------
#
# Compiling source files
#
#------------------------------------------------------------------------------
#
# The LA solver header and source files are defined first, with the generic
# target to build the object files from these.

LASOLVER_HEADERS = Application.hpp CommandOptions.hpp Constraints.hpp \
                   Domains.hpp laSolver.pb.h Metrics.hpp MetricStore.hpp \
                   Solver.hpp Variables.hpp

LASOLVER_SOURCE = CommandOptions.cpp MetricStore.cpp Model.cpp Registries.cpp

LASOLVER_OBJECTS = ${LASOLVER_SOURCE:.cpp=.o}

$(OBJECTS_DIR)/%.o : %.cpp
	$(CXX) $(CXXFLAGS) $< -o $@ $(INCLUDE_DIRECTORIES)

# The Google protocol buffer file has to be separately compiled because it is
# differently named.

$(OBJECTS_DIR)/laSolver.o : laSolver.pb.h laSolver.pb.cc
	$(CXX) $(CXXFLAGS) laSolver.pb.cc -o $@ $(INCLUDE_DIRECTORIES)

# The Theron++ library has its own make command that can be invoked to
# check the availability of the library

# The LA framework has one file that is currently needed (will be replaced by
# a library like for Theron++)

LA_FRAMEWORK_OBJECTS = RandomGenerator.o

$(OBJECTS_DIR)/RandomGenerator.o : ${LA_FRAMEWORK}/RandomGenerator.hpp ${LA_FRAMEWORK}/RandomGenerator.cpp
	$(CXX) $(CXXFLAGS) ${LA_FRAMEWORK}/RandomGenerator.cpp -o $@ $(INCLUDE_DIRECTORIES)

###############################################################################
#
# Targets
#
###############################################################################
#

ALL_MODULES = $(addprefix $(OBJECTS_DIR)/, $(LASOLVER_OBJECTS) $(LA_FRAMEWORK_OBJECTS))

#------------------------------------------------------------------------------
#
# Test targets
#
#------------------------------------------------------------------------------
#

$(OBJECTS_DIR)/%.o : Tests/%.cpp
	$(CXX) $(CXXFLAGS) $< -o $@ $(INCLUDE_DIRECTORIES)

GetMetricValue: $(OBJECTS_DIR)/GetMetricValue.o ${ALL_MODULES}
	$(CXX) $(OBJECTS_DIR)/GetMetricValue.o ${ALL_MODULES} ${LDFLAGS} \
	${THERON_LIB} $(LINKER_LIBS) -o $(OBJECTS_DIR)/GetMetricValue

UseMetricStore: $(OBJECTS_DIR)/UseMetricStore.o ${ALL_MODULES}
	(cd $(THERON); make Library)
	$(CXX) $(OBJECTS_DIR)/UseMetricStore.o ${ALL_MODULES} ${LDFLAGS} \
	$(LINKER_LIBS) -o $(OBJECTS_DIR)/UseMetricValue

#------------------------------------------------------------------------------
#
# Production targets
#
#------------------------------------------------------------------------------
#

clean:
	$(RM) $(OBJECTS_DIR)/*

#
# DEPENDENCIES
#

-include $(ALL_MODULES:.o=.d)
