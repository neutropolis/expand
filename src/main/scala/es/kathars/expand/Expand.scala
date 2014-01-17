package es.kathars.expand

import scala.annotation.StaticAnnotation
import scala.reflect.macros.Context
import scala.language.experimental.macros

class expand(val bound: Int) extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro Macros.expandImpl
}

object Macros {

  def expandImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.mirror._
    import c.universe._
    import Flag._

    val q"new expand($b)" = c.prefix.tree
    val Literal(Constant(bound: Int)) = b
    val boundl = (1 to bound).toList

    val q"class $name[$tparam](val $paramname: $paramtpe)" =
      annottees.head.tree

    def newtparams(bound: Int): List[TypeDef] = for {
      i <- (1 to bound).toList
    } yield TypeDef(
      tparam.mods,
      newTypeName(tparam.name.decoded.replaceAll("α", i.toString)),
      tparam.tparams,
      tparam.rhs)

    val q"class ${_}[${_}]($p)" = q"class A[T](val a1: T)"
    val parammods = p.mods

    def newparams(bound: Int): List[ValDef] = for {
      i <- (1 to bound).toList
    } yield q"$parammods val ${newTermName(paramname.decoded.replaceAll("α", i.toString))}: ${newtparams(bound)(i-1).name}"

    val classes = for {
      i <- boundl
    } yield q"""class ${newTypeName(name.decoded.replaceAll("α", i.toString))}[..${newtparams(i)}](..${newparams(i)})"""

    c.Expr[Any](Block(classes, Literal(Constant(()))))
  }
}
