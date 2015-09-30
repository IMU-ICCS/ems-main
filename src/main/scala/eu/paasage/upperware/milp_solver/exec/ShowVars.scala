package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.metamodel.cp.ConstraintProblem
import eu.paasage.upperware.milp_solver.{CDOClient, Helpers}

import scala.collection.JavaConversions._

object ShowVars extends App with LazyLogging {

  if(args.length != 1) {
    println("You need to provide CDO resource name")
    sys.exit()
  }

  val resourceName = args(0)

  CDOClient.open_default(cdo => {
    logger.info(s"Retrieving CP $resourceName")
    cdo.retrieveModel[ConstraintProblem, Unit](resourceName, cp => {
      if(cp.getSolution.isEmpty) {
        logger.info(s"No solutions to display")
      } else {
        cp.getSolution.sortBy(_.getTimestamp).foreach(solution => logger.info(Helpers.solutionToString(solution)))
      }
    })
  })
}