###############################################################################
#
# The LA Reasoner
#
# This file makes compiles the files for the LA Reasoner
# 
# Author: Geir Horn, 2014 - 2018
#
###############################################################################

#
# Setting the standard definitions
#

CC = g++
AR = ar
RM = rm -f

# These locations have been made relative to work independent of the different 
# machines used for this development. Note that the LA framework will be 
# located differently during a real deployment.

THERON ?= ../../Theron++
LA_FRAMEWORK ?= ../../LA-Framework

# These frameworks contain certain objective functions that must be built 
# as part of the built process for the solvers.

THERON_OBJECTS = $(THERON)/Actor.o
LA_OBJECTS = ${LA_FRAMEWORK}/RandomGenerator.o

# Optimisation -O3 is the highest level of optimisation and should be used 
# with production code. -Og is the code optimising and offering debugging 
# transparency and should be use while the code is under development
# Since the code is templated, it is recommended to not use any optimisation 
# while testing the code

OPTIMISATION_FLAG = 

# It is useful to let the compiler generate the dependencies for the various 
# files, and the following will produce .d files that can be included at the 
# end. The -MMD flag is equivalent with -MD, but the latter will include system 
# headers in the output (which we do not need here). The -MP includes an 
# empty rule to create the dependencies so that make would not create any errors 
# if the file name changes.

DEPENDENCY_FLAGS = -MMD -MP

# Options 

GENERAL_OPTIONS = -c -Wall -std=c++1z -ggdb -D_DEBUG -Wformat-truncation=0 -Wno-sign-compare
INCLUDE_DIRECTORIES = -I. -I/usr/include -I$(THERON) -I$(LA_FRAMEWORK)

# Other library specific flags. 

#THERON_FLAGS = -DTHERON_POSIX=1 -DTHERON_BOOST=0 -DTHERON_CPP11=1
ARMADILLO_FLAGS = -DARMA_USE_CXX11
GLIB_FLAGS = -D_GLIBCXX_USE_NANOSLEEP -D_GLIBCXX_USE_SCHED_YIELD -DHAVE_INLINE

# Libraries used - note that the Theron lib should be added to the link command
# later when the code uses it. The code uses the GNU Scientific Library (GSL)
# and the math library (m).

MACHINE := $(shell uname --nodename)
ifeq ($(MACHINE),Glamdring)
  NLOPT_LIB ?= -lgslcblas -lnlopt_cxx
else
  NLOPT_LIB ?= -lgslcblas -lnlopt
endif

# Then the flags for the compiler can be defined

CFLAGS = $(DEPENDENCY_FLAGS) $(OPTIMISATION_FLAG) $(GENERAL_OPTIONS) \
         $(GLIB_FLAGS) $(ARMADILLO_FLAGS) $(THERON_FLAGS)
INCLUDE_FLAGS = -I ${LA_FRAMEWORK} -I ${THERON}/Include

# The linker needs a separate set of flags and libraries. Note that the 
# Google protocol buffers library is given directly here.

#THERON_LIB = ${THERON}/Lib/libtherond.a
LDFLAGS = -Wl,--allow-multiple-definition -pthread -ggdb -D_DEBUG
LD_LIBS    = ${THERON_LIB} ${NLOPT_LIB} -lprotobuf -lm

#
# SOLVER MODULES
#
# The compiled files that are needed by the linker in order to build the 
# solver executable.

SOLVER_OBJECTS = 

# And these are needed to build the various targets

ALL_MODULES = $(THERON_OBJECTS) $(LA_OBJECTS) $(SOLVER_OBJECTS)

#
# TARGETS
#

clean:
	${RM} *.o
	${RM} *.d
	${RM} *.a
	${RM} *.pdb
	${RM} ${LA_FRAMEWORK}/*.o
	$(RM) ${THERON}/*.o
	${RM} ${LA_FRAMEWORK}/*.d
	$(RM) ${THERON}/*.d

# Generic compile targets

%.o : %.cpp
	$(CC) $(CFLAGS) $< -o $@ $(INCLUDE_DIRECTORIES)

#
# Test targets 
#
# The model file defines the CP-model to be solved by the solver, and it is 
# mainly a syntax check to ensure that it has been correctly set up.

Model: Model.o Interface.o ${LA_OBJECTS}
	$(CC) Model.o Interface.o ${LA_OBJECTS} $(LDFLAGS) $(LD_LIBS) -o Model

#
# Main target
#

LASolver: ${ALL_MODULES}
	$(CC) ${ALL_MODULES} $(LDFLAGS) $(LD_LIBS) -o LASolver

#
# DEPENDENCIES
#

-include $(ALL_MODULES:.o=.d)
