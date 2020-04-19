package scala.scalanative
package nscplugin

import dotty.tools.dotc.ast.Trees._
import dotty.tools.dotc.ast.tpd
import dotty.tools.dotc.core.Constants.Constant
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Decorators._
import dotty.tools.dotc.core.StdNames._
import dotty.tools.dotc.core.Symbols._
import dotty.tools.dotc.typer.FrontEnd
import dotty.tools.dotc.plugins.{PluginPhase, StandardPlugin}
import dotty.tools.dotc.transform.{Pickler, Staging}

class NirPlugin extends StandardPlugin {
  val name: String = "nir"
  override val description: String = "divide zero check"

  def init(options: List[String]): List[PluginPhase] = {
    (new NirPluginPhase) :: Nil
  }
}

class NirPluginPhase extends PluginPhase {
  import tpd._

  val phaseName = "nirPhase"

  override val runsAfter = Set(FrontEnd.name)
  override val runsBefore = Set(Staging.name)

  override def transformApply(tree: Apply)(implicit ctx: Context): Tree = {
    tree match {
      case Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0))))
          if rcvr.tpe <:< defn.IntType =>
        ctx.error("dividing by zero", tree.sourcePos)
      case _ =>
        ()
    }
    tree
  }
}
