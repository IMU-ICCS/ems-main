package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.metamodel.cp.ConstraintProblem
import eu.paasage.upperware.milp_solver.{CDOClient, CpIO}
import org.eclipse.emf.ecore.util.EcoreUtil

object RetrieveCp extends App with LazyLogging {

  if(args.length != 2) {
    println("You need to provide CDO resource name and file name")
    sys.exit()
  }

  val resourceName = args(0)
  val path = args(1)

  CDOClient.open_default(cdo => {
    logger.info(s"Retrieving CP $resourceName")
    cdo.retrieveModel[ConstraintProblem, Unit](resourceName, cp => {
        logger.info(s"Storing CP in $path")
        CpIO.store(path, EcoreUtil.copy(cp))
        logger.info("Done")
    })
  })
}