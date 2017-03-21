package eu.paasage.upperware.milp_solver

import eu.paasage.upperware.metamodel.types.impl.TypesFactoryImpl
import eu.paasage.upperware.metamodel.cp._
import eu.paasage.upperware.metamodel.types._
import scala.collection.JavaConversions._
import jCMPL.{CmplSolElement, Cmpl}
import com.typesafe.scalalogging.slf4j.LazyLogging

class OptimizationFailed() extends Exception


class MILPSolver(val cp: ConstraintProblem, val timestamp: Long, debug: Boolean = false, encodeVarNames: Boolean = true) extends CmplCPGenerator with LazyLogging {
  val reformulate: Boolean = true

  val encodeVarName:(String) => String = if(encodeVarNames) VarNameEncoders.genNumber else VarNameEncoders.identity
  val variablesMap: Map[String, Variable] = cp.getVariables.map(v => (encodeVarName(v.getId), v)).toMap

  object convertVal {
    val faktoria = new TypesFactoryImpl
    def apply(x: Any): NumericValueUpperware = {
      x match {
        case x: java.lang.Long => {
          val modelVal = faktoria.createLongValueUpperware
          modelVal.setValue(x)
          return modelVal
        }
        case x: java.lang.Double => {
          val modelVal = faktoria.createDoubleValueUpperware()
          modelVal.setValue(x)
          return modelVal
        }
      }
    }
  }

  def createSolution(variables:Iterable[CmplSolElement]):Solution = {
    val solution = CpFactory.eINSTANCE.createSolution()
    solution.setTimestamp(System.currentTimeMillis)
    updateSolution(solution, variables)
    return solution
  }

  def updateSolution(solution: Solution, variables:Iterable[CmplSolElement]) {
    val values = solution.getVariableValue

    variables.foreach(v => {
      if(variablesMap.isDefinedAt(v.name)) {
        val vv = CpFactory.eINSTANCE.createVariableValue()
        vv.setVariable(variablesMap(v.name))
        vv.setValue(convertVal(v.activity))
        values.add(vv)
      }
    })
  }

  val solution = {
    val solution = cp.getSolution().find(_.getTimestamp == timestamp)

    if(solution.isEmpty) {
      (    "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        :: ""
        :: "Solution with given timestamp was not found, failing!"
        :: ""
        :: "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" :: Nil).foreach(l => logger.error(l))
      throw new OptimizationFailed()
    }

    solution.get
  }

  val metricMap = solution.getMetricVariableValue.map(m => (m.getVariable, m.getValue)).toMap

  def solve:Solution = {
    val modelFile = scala.reflect.io.File.makeTemp("model", ".cmpl")
    try {



      val model = generate_model
      modelFile.writeAll(model)

      val cmpl = new Cmpl(modelFile.path)

      if (debug) {
        logger.debug("CMPL model file: " + modelFile.path)
        logger.debug("Variables mapping: ")
        variablesMap.foreach{ case (id, v) => logger.debug((id, v.getId).toString) }
        logger.debug("Model: ")
        logger.debug(model);
        logger.debug("Running CMPL...")
        cmpl.setOutput(true)
      }

      cmpl.solve

      if (cmpl.solverStatus == Cmpl.SOLVER_OK) {
        if (debug) cmpl.solutionReport

        updateSolution(solution, cmpl.solution.variables)

        return solution
      } else {
        (    "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
          :: ""
          :: "Dear User,"
          :: ""
          :: "MILP solver was unable to find any solution,"
          :: "because CP problem provided by upstream"
          :: "components is either infeasible or is outside"
          :: "MILP class."
          :: ""
          :: "If you ran the solver with debugging enabled,"
          :: "you should see CP problem pretty-printed above."
          :: ""
          :: "As PaaSage in concerned, no solution means"
          :: "no possible deployments."
          :: ""
          :: "Please review your CAMEL model and try again."
          :: "If CP problem is outside MILP class, please use"
          :: "more general solver (e.g. CP solver)."
          :: ""
          :: "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" :: Nil).foreach(l => logger.error(l))
        throw new OptimizationFailed()
      }
    } finally {
      if (!debug) modelFile.delete
    }
  }
}

object MILPSolver {
  def default_solve(cp: ConstraintProblem, timestamp: Long, debug: Boolean = false, encodeVarNames: Boolean = true) = new MILPSolver(cp, timestamp, debug, encodeVarNames).solve
}
