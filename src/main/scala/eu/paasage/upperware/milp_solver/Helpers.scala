package eu.paasage.upperware.milp_solver

import scala.collection.JavaConversions._

import eu.paasage.upperware.metamodel.cp.Solution
import eu.paasage.upperware.metamodel.types._

object Helpers {
  def strValue(o: NumericValueUpperware): String = o match {
    case x: IntegerValueUpperware => s"${x.getValue.toString}"
    case x: DoubleValueUpperware  => s"${x.getValue.toString}"
    case x: FloatValueUpperware   => s"${x.getValue.toString}"
    case x: LongValueUpperware    => s"${x.getValue.toString}"
    case x: BooleanValueUpperware => if (x.isValue) "1" else "0"
    case null => "null"
  }
  def solutionToString(solution: Solution):String = {
    val sb = new StringBuilder
    sb ++= s"Solution at ${solution.getTimestamp}\n"
    solution.getVariableValue.foreach(v => {
      sb ++= f"Variable  ${v.getVariable.getId}%-60s = ${Helpers.strValue(v.getValue)}%s\n"
    })
    solution.getMetricVariableValue.foreach(v => {
      sb ++= f"MetricVar ${v.getVariable.getId}%-60s = ${Helpers.strValue(v.getValue)}%s\n"
    })
    return sb.toString
  }
}