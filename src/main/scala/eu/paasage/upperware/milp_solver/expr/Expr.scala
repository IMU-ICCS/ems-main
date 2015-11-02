package eu.paasage.upperware.milp_solver.expr

import eu.paasage.upperware.metamodel._
import scala.collection.JavaConversions._
import eu.paasage.upperware.metamodel.cp.{MetricVariable, MetricVariableValue, OperatorEnum}
import eu.paasage.upperware.metamodel.types._


abstract class Binary extends Expr
case class Add(a: Expr, b: Expr) extends Binary
case class Subtract(a: Expr, b: Expr) extends Binary
case class Multiply(a: Expr, b: Expr) extends Binary
case class Divide(a: Expr, b: Expr) extends Binary

case class Const(value: Double) extends Expr
case class Variable(name: String) extends Expr

object Expr {
  def fromWP3(expr: cp.Expression, variableMap: Map[MetricVariable, NumericValueUpperware]):Expr = expr match {
    case x: cp.ComposedExpression => {
      val exprs = x.getExpressions
      exprs.tail.foldLeft(fromWP3(exprs.head, variableMap))((acc: Expr, elem: cp.Expression) => x.getOperator match {
        case OperatorEnum.PLUS  => Add(acc, fromWP3(elem, variableMap))
        case OperatorEnum.MINUS => Subtract(acc, fromWP3(elem, variableMap))
        case OperatorEnum.TIMES => Multiply(acc, fromWP3(elem, variableMap))
        case OperatorEnum.DIV   => Divide(acc, fromWP3(elem, variableMap))
      })
    }
    case x: cp.Variable => Variable(x.getId)
    case x: cp.Constant => x.getValue match {
      case x: IntegerValueUpperware => Const(x.getValue)
      case x: DoubleValueUpperware  => Const(x.getValue)
      case x: FloatValueUpperware   => Const(x.getValue)
      case x: LongValueUpperware    => Const(x.getValue)
      case x: BooleanValueUpperware => Const(if (x.isValue) 1 else 0)
    }
    case x: cp.MetricVariable => variableMap(x) match {
      case x: IntegerValueUpperware => Const(x.getValue)
      case x: DoubleValueUpperware  => Const(x.getValue)
      case x: FloatValueUpperware   => Const(x.getValue)
      case x: LongValueUpperware    => Const(x.getValue)
      case x: BooleanValueUpperware => Const(if (x.isValue) 1 else 0)
    }
    case _ => throw new Exception("[" + expr.getClass.getName + " not supported")
  }
}

case class ExprSerializator(encodeVarName: (String) => String) {
  def wrap(expr: Expr):String = expr match {
    case expr:Add => s"(${apply(expr)})"
    case expr:Subtract => s"(${apply(expr)})"
    case _ => apply(expr)
  }

  def apply(expr: Expr):String = expr match {
    case Add(a, b) => s"${apply(a)} + ${apply(b)}"
    case Subtract(a, b) => s"${apply(a)} - ${apply(b)}"
    case Multiply(a, b) => s"${wrap(a)} * ${wrap(b)}"
    case Divide(a:Expr, b:Const) => s"${wrap(a)} * (${1.0/b.value})"
    case Divide(a, b) => s"${wrap(a)} / ${wrap(b)}"
    case Const(value) => s"(${value.toString()})"
    case Variable(name) => encodeVarName(name)
  }
}

abstract class Expr {
  def flatten:Expr = this match {
    case Add(a, b) => Add(a.flatten, b.flatten)
    case Subtract(a, b) => Subtract(a.flatten, b.flatten)

    case Multiply(a, b) => {
      val a_flat = a.flatten
      val b_flat = b.flatten
      (a_flat, b_flat) match {
        case (Add(a,b), Add(c,d)) => Add(Add(Multiply(a,c).flatten, Multiply(b,c).flatten), Add(Multiply(a,d).flatten, Multiply(b,d).flatten))
        case (Add(a,b), c) => Add(Multiply(a,c).flatten, Multiply(b,c).flatten)
        case (c, Add(a,b)) => Add(Multiply(c,a).flatten, Multiply(c,b).flatten)
        case (a,b) => Multiply(a,b)
      }
    }
    case Divide(a, b) => {
      val a_flat = a.flatten
      val b_flat = b.flatten
      (a_flat, b_flat) match {
        case (Add(a,b), Add(c,d)) => Add(Add(Divide(a,c).flatten, Divide(b,c).flatten), Add(Divide(a,d).flatten, Divide(b,d).flatten))
        case (Add(a,b), c) => Add(Divide(a,c).flatten, Divide(b,c).flatten)
        case (c, Add(a,b)) => Add(Divide(c,a).flatten, Divide(c,b).flatten)
        case (a,b) => Divide(a,b)
      }
    }
    case _ => this
  }
}
