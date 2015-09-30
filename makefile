###############################################################################
#
# The LA Reasoner
#
# This file makes compiles the files for the LA Reasoner
# 
# Author: Geir Horn, 2014
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

THERON ?= ../Theron
LA_FRAMEWORK ?= ../LA-Framework/Framework

# Optimisation -O3 is the highest level of optimisation and should be used 
# with production code. -Og is the code optimising and offering debugging 
# transparency and should be use while the code is under development
# Since the code is templated, it is recommended to not use any optimisation 
# while testing the code

OPTIMISATION_FLAG = 

# Other library specific flags. 

THERON_FLAGS = -DTHERON_POSIX=1 -DTHERON_BOOST=0 -DTHERON_CPP11=1
ARMADILLO_FLAGS = -DARMA_USE_CXX11
GLIB_FLAGS = -D_GLIBCXX_USE_NANOSLEEP -D_GLIBCXX_USE_SCHED_YIELD
GENERAL_FLAGS = $(OPTIMISATION_FLAG) -c -Wall -pthread -std=c++11 -ggdb -D_DEBUG
CFLAGS = $(GENERAL_FLAGS) $(GLIB_FLAGS) $(THERON_FLAGS) $(ARMADILLO_FLAGS)
LDFLAGS = -Wl,--allow-multiple-definition -pthread -ggdb -D_DEBUG
INCLUDE_FLAGS = -I ${LA_FRAMEWORK} -I ${THERON}/Include

THERON_LIB = ${THERON}/Lib/libtherond.a
NLOPT_LIB  ?= -lnlopt_cxx

LD_LIBS    = ${THERON_LIB} ${NLOPT_LIB} -lm

#
# TARGETS
#

clean:
	${RM} *.o
	${RM} *.a
	${RM} *.pdb

#
# Build the framework files
#

RandomGenerator.o: ${LA_FRAMEWORK}/RandomGenerator.hpp ${LA_FRAMEWORK}/RandomGenerator.cpp
	$(CC) $(CFLAGS) $(INCLUDE_FLAGS) ${LA_FRAMEWORK}/RandomGenerator.cpp -o RandomGenerator.o

#
# Build the object files
#

ContinuousOptimiser.o: Interface.hpp ContinuousOptimiser.hpp ContinuousOptimiser.cpp
	$(CC) $(CFLAGS) $(INCLUDE_FLAGS) ContinuousOptimiser.cpp -o ContinuousOptimiser.o
	
Interface.o: Interface.hpp Variables.model Constraints.model Interface.cpp
	$(CC) $(CFLAGS) $(INCLUDE_FLAGS) Interface.cpp -o Interface.o
	
Metric.o: Interface.hpp Metric.cpp
	$(CC) $(CFLAGS) $(INCLUDE_FLAGS) Metric.cpp -o Metric.o

CmdParser.o: CommandParser.hpp CommandParser.cpp
	$(CC) $(CFLAGS) $(INCLUDE_FLAGS) CommandParser.cpp -o CmdParser.o

main.o: Interface.hpp EvaluationActor.hpp PrintResults.hpp main.cpp
	$(CC) $(CFLAGS) $(INCLUDE_FLAGS) main.cpp -o main.o

LASolver: RandomGenerator.o ContinuousOptimiser.o Interface.o Metric.o  \
CmdParser.o main.o
	$(CC) RandomGenerator.o ContinuousOptimiser.o Interface.o Metric.o \
	CmdParser.o main.o $(LDFLAGS) $(LD_LIBS) -o LASolver
