package eu.paasage.upperware.milp_solver

import eu.paasage.upperware.metamodel.cp._
import eu.paasage.upperware.metamodel.types._
import scala.collection.JavaConversions._
import eu.paasage.upperware.milp_solver.expr.Expr._

/** Variable name encoder functions
  *
  * Variable names in CMPL follow usual naming scheme (begin with letter, letters, numbers and some special chars e.g. _ are accepted). On the other hand, in CP model we may encounter any name (no restrictions on that). To be sure that CMPL model will be syntactically correct, we may encode variable names in some way.
  * */
object VarNameEncoders {
  /** Hex encode variable names to be valid CMPL variable names */
  def hex(in: String): String = "v" + in.getBytes.map("%02X" format _).mkString
  /** Identity encoding – useful for debuging as generated models have human readable names */
  def identity(in: String) = in
  def no_special_chars(in: String): String = "[^a-zA-Z0-9\\s]".r.replaceAllIn(in, "_")

  val map = scala.collection.mutable.Map[String, String]()
  def genNumber(in: String):String = {
    map.getOrElseUpdate(in, "v" + map.size.toString + "l")
  }
}

/** CP to CMPL converter trait */
trait CmplCPGenerator {

  /** `ConstraintProblem` to be converted */
  def cp: ConstraintProblem
  /** Variable name encoder to use. See `VarNameEncoders` for reference. */
  def encodeVarName: (String) => String
  /** Mapping between encoded variable names and `Variable` objects */
  def variablesMap: Map[String, Variable]
  /** Whether apply distributive property of multiplication over addition */
  val reformulate: Boolean

  def metricMap: Map[MetricVariable, NumericValueUpperware]

  private val comparisionOperators = Map(
    ComparatorEnum.GREATER_THAN        -> ">",
    ComparatorEnum.LESS_THAN           -> "<",
    ComparatorEnum.GREATER_OR_EQUAL_TO -> ">=",
    ComparatorEnum.LESS_OR_EQUAL_TO    -> "<=",
    ComparatorEnum.EQUAL_TO            -> "=",
    ComparatorEnum.DIFFERENT           -> "!="
  )

  private val types = Map(
    BasicTypeEnum.DOUBLE  -> "real",
    BasicTypeEnum.FLOAT   -> "real",
    BasicTypeEnum.INTEGER -> "integer",
    BasicTypeEnum.LONG    -> "long"
  )

  /** Convert `NumericValueUpperware` to `String` */
  private def value(o: NumericValueUpperware): String = o match {
    case x: IntegerValueUpperware => s"(${x.getValue.toString})"
    case x: DoubleValueUpperware  => s"(${x.getValue.toString})"
    case x: FloatValueUpperware   => s"(${x.getValue.toString})"
    case x: LongValueUpperware    => s"(${x.getValue.toString})"
    case x: BooleanValueUpperware => if (x.isValue) "1" else "0"
  }

  /** Recursively convert an Expression` into CMPL format */
  private def expression(o: Expression): String = {
    val expression = expr.Expr.fromWP3(o, metricMap)
    val problem = if(reformulate) expression.flatten else expression
    expr.ExprSerializator(encodeVarName)(problem)
  }

  /** Recursively convert an Expression` into CMPL format */
  private def expressionObjective(o: Expression, g: GoalOperatorEnum): String = {
    val expression = expr.Expr.fromWP3(o, metricMap)
    val problem = if(reformulate) expression.flatten else expression

    problem match {
      case (expr.Divide(a, b)) if a.isConstant => {
        val ex = expr.ExprSerializator(encodeVarName)(b)
        val invGoal = if (g == GoalOperatorEnum.MIN) "max" else "min"
        s"${ex} -> ${invGoal};"
      }
      case _ => {
        val ex = expr.ExprSerializator(encodeVarName)(problem);
        s"${ex} -> ${g};"
      }
    }


  }

  /** Get CMPL represenation of variable domain */
  private def domain(o: Domain): String = o match {
    case x: RangeDomain if ((x.getType == BasicTypeEnum.INTEGER || x.getType == BasicTypeEnum.LONG) && x.getFrom.asInstanceOf[IntegerValueUpperware].getValue == 0 && x.getTo.asInstanceOf[IntegerValueUpperware].getValue == 1) => "binary"
    case x: RangeDomain => s"${types(x.getType)} [${value(x.getFrom)}..${value(x.getTo)}]"
    case x: NumericListDomain => if(x.getValues.length == 2) "binary" else "not_supported"
    case x: NumericDomain => types(x.getType)
    case x: BooleanDomain => "binary"
    case _ => s"[${o.getClass.getName} not supported"
  }

  /** Generate variables part of the model */
  private def variables = {
    def textVariables = variablesMap.map { case (id, variable) => s"${id}: ${domain(variable.getDomain)};" }
    textVariables.mkString("\n")
  }

  /** Generate objectives part of the model */
  private def objectives = {
    if(!cp.getGoals.isEmpty) {
      val textGoals = cp.getGoals.map(g => expressionObjective(g.getExpression, g.getGoalType))
      textGoals.mkString("\n")
    } else {
      "1 -> min;"
    }
  }

  /** Generate constraints part of the model */
  private def constraints = {
    val textConstraints = cp.getConstraints.map(c => {
      c.getComparator match {
        case ComparatorEnum.LESS_THAN    => s"${expression(c.getExp1)} <= (${expression(c.getExp2)} - 1);"
        case ComparatorEnum.GREATER_THAN => s"${expression(c.getExp1)} >= (${expression(c.getExp2)} + 1);"
        case _                           => s"${expression(c.getExp1)} ${comparisionOperators(c.getComparator)} ${expression(c.getExp2)};"
      }
    })
    textConstraints.mkString("\n")
  }

  /** Generate CMPL model containing all parts */
  def generate_model = s"""
variables:
${variables}

objectives:
${objectives}

constraints:
${constraints}
	"""
}
