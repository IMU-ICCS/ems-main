package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.metamodel.cp.ConstraintProblem
import eu.paasage.upperware.milp_solver.{CDOClient, Helpers, MILPSolver, OptimizationFailed}
import jCMPL.CmplException

import scala.collection.JavaConversions._


/** Solve constraint problem stored in CDO server and update it in place */
object MainCDO extends App with LazyLogging {
	
	if(args.length != 1) {
		println(s"You need to provide resource name.")
		sys.exit()
	}

  val resourceName = args(0)
  try {
    CDOClient.open_default(cdo => {
      logger.info(s"Retrieving CP $resourceName")
      cdo.processModel[ConstraintProblem](resourceName, cp => {
          logger.info("Solving with CMPL...")
          val solution = MILPSolver.default_solve(cp, true, true)

          logger.debug("CMPL done.")
          logger.debug("Results: ")
          logger.debug(Helpers.solutionToString(solution))
          logger.debug("Commiting transaction.")
      })
      logger.info("Done")
    })
  } catch {
    case _:OptimizationFailed => {
      logger.error("Optimization failed.")
      System.exit(1)
    }
    case _:CmplException => {
      logger.error("CMPL failed.")
      System.exit(2)
    }
  }
}