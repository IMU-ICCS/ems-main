package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.metamodel.cp.ConstraintProblem
import eu.paasage.upperware.milp_solver.{CDOClient, Helpers, MILPSolver, OptimizationFailed}
import jCMPL.CmplException
import org.zeromq.ZMQ

import scala.collection.JavaConversions._


/** Solve constraint problem stored in CDO server and update it in place */
object MainCDO extends App with LazyLogging {

  def sendZeroMQ(id: String) {
    val context = ZMQ.context(1)
    val publisher = context.socket(ZMQ.PUB)
    publisher.bind("tcp://*:5540")
    publisher.send("MILPsolutionAvailable".getBytes(), ZMQ.SNDMORE)
    publisher.send(id.getBytes(), 0)
  }

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
      sendZeroMQ(resourceName)
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
