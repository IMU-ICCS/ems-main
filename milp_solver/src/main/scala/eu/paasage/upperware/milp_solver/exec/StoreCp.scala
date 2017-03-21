package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.milp_solver.{CDOClient, CpIO}

object StoreCp extends App with LazyLogging {

  if(args.length != 2) {
    println("You need to provide CDO resource name and file name")
    sys.exit()
  }

  val resourceName = args(0)
  val path = args(1)

  CDOClient.open_default(cdo => {
    logger.info(s"Loading CP from $path")

    val cp = CpIO.load(path)

    logger.info(s"Storing to CDO in $resourceName")
    cdo.storeModel(cp, resourceName);

    logger.info("Done")
  })
}