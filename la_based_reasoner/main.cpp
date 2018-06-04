/*=============================================================================
  LA Solver
 
  The LA Solver implements a stochastic combinatorial optimiser following the 
  strategy outlined in [1]. It is a complete re-implementation of the PaaSage
  LA Solver released in 2014. The fundamental idea is that the execution context
  of a deployed application is constantly changing, and the measurements from 
  the running application will indicate if an adaptation is necessary. The 
  LA Solver aims to find an optimised configuration for the current context 
  considering that the measurements can be frequently changing, e.g. the number
  of users of an application. The goal is therefore to find the deployment that
  behaves best on average.
  
  The problem is solved in a two-stage process and the basic algorithm
  
  do
	{
		Assign discrete variables
		do
		{
			Assign continuous variables
			Evaluate the utility of the configuration (all variables)
			Update the values of the continuous variables
		} 
		until ( converged )
		Update the discrete variables
	} 
	until ( converged )
	
	A network of Learning Automata (LA) [2] is used for the assignment of the 
	discrete variables, and the BOBYQA algorithm [3] implemented in NLOpt [4] is 
	used for solving the continuous problem remaining after assigning the 
	discrete variables.
	
	The LA solver is implemented as an actor system using Theron++ [5] by the 
	following core actor classes:
	
	A. Application: This actor takes care of the variables, constraints and 
	   metric values of the application model. It is responsible for evaluating
	   the constraints for a given variable assignment (the configuration), 
	   and to report the configuration to the utility generator.
  B. Utility Generator: Receives the configuration from the application actor,
	   and calculates the objective function value for this configuration, i.e. 
	   the utility. In MELODIC the utility is computed by an external component 
	   and this actor is the interface to the external component.
  C. Metric manager: receives the events that the value of a metric has changed
     and updates the corresponding internal metric value upon request from the 
     LA Assigner.
  D. LA Assigner: Updates the probabilities for the various discrete values 
     and selects the value of the discrete variables. Once the variables have 
     been selected, it sends the assignment to the solver actor. It also 
     receives the metric values and caches these until a solution is found 
     for the current metric values to avoid confusing the continuous variable 
	   solver with new metric values.
  D. Solver: The actor responsible for finding the optimal continuous variable
     values given a received assignment from the LA Assigner. It calls the 
     Application actor to evaluate constraints and the utility function, and 
     receives its value from the Utility Generator. It sends each utility value
     back to the LA Assigner. Once a solution is found it is sent back to the 
     LA Assigner as an optimal configuration for the current context.
   
  Copyright (c) 2018 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  References:
  
  [1] Geir Horn: "A vision for a stochastic reasoner for autonomic cloud 
      deployment", Proceedings of the Second Nordic Symposium on Cloud 
      Computing & Internet Technologies (NordiCloud 2013), pp. 46–53, 
      Conference Location: Oslo, Norway, 2-3 September 2013
  [2] Mandayam A. L. Thathachar and P. S. Sastry: "Networks of Learning 
      Automata: Techniques for Online Stochastic Optimization", Kluwer Academic
      2004, ISBN 1-4020-7691-6
  [3] M. J. D. Powell: "The BOBYQA algorithm for bound constrained 
      optimization without derivatives", Report DAMTP 2009/NA06, Department of 
      Applied Mathematics and Theoretical Physics, Centre for Mathematical 
      Sciences, Cambridge University, England, UK, August 2009
  [4] https://nlopt.readthedocs.io/en/latest/
  [5] https://github.com/GeirHo/TheronPlusPlus
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

// Standard library includes


// The project related includes - in the order of appearance

// The main file has three rather separate parts:
// 1) Initialisation of all main objects involved in the concurrent 
//    optimisation.
// 2) Starting the optimisation 
// 3) Reporting the results of the optimisation

int main(int argc, char **argv) 
{
  //----------------------------------------------------------------------------
  // Initialisation
  //----------------------------------------------------------------------------

  
  //----------------------------------------------------------------------------
  // Starting optimisation
  //----------------------------------------------------------------------------

  //----------------------------------------------------------------------------
  // Receiving and printing the results
  //----------------------------------------------------------------------------
  // In this version the solution will be put back into the feasible solution 
  // vector and then printed to the console before the application happily 
  // terminates.
 
  
  return 0;
}
