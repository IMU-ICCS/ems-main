package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.milp_solver.{Helpers, CpIO, MILPSolver}

import scala.collection.JavaConversions._

/** Solve constraint problem stored in XMI file and save result to the other file */
object MainFile extends App with LazyLogging {
	if(args.length != 2) {
		println("You need to provide input CP file name and output file name")
		sys.exit()
	}
	
  val inFile = args(0)
  val outFile = args(1)

  logger.info(s"Reading file $inFile")
  val cp = CpIO.load(inFile)

  logger.info("Solving with CMPL...")
  var solution = MILPSolver.default_solve(cp, true, true);

  logger.info("CMPL done, results:")
  logger.debug("Results: " + Helpers.solutionToString(solution))

  logger.info(s"Saving results to $outFile")

  if(CpIO.store(outFile, cp) == false) {
    logger.error("Error saving the file")
    sys.exit(1);
  } else {
    logger.info("Done")
    sys.exit(0);
  }
}