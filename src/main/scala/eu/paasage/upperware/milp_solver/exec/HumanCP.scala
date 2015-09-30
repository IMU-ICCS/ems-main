package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.metamodel.cp.{ConstraintProblem, Variable}
import eu.paasage.upperware.milp_solver.{CDOClient, CmplCPGenerator, VarNameEncoders}

import scala.collection.JavaConversions._

class StubSolver(val cp: ConstraintProblem) extends CmplCPGenerator with LazyLogging {
  val encodeVarName:(String) => String = VarNameEncoders.no_special_chars
  val reformulate = false
  val debug = true
  val variablesMap: Map[String, Variable] = cp.getVariables.map(v => (encodeVarName(v.getId), v)).toMap
}

object HumanCP extends App with LazyLogging {

  if(args.length != 1) {
    println("You need to provide CDO resource name")
    sys.exit()
  }

  val resourceName = args(0)

  CDOClient.open_default(cdo => {
    logger.info(s"Retrieving CP $resourceName")
    cdo.retrieveModel[ConstraintProblem, Unit](resourceName, cp => {
      val s = new StubSolver(cp)
      logger.info(s.generate_model)
    })
  })
}