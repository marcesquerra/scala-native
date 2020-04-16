package scala.scalanative

import dotty.tools.dotc.core.Decorators._
import dotty.tools.dotc.core.Names._
import dotty.tools.dotc.core.Symbols._
import dotty.tools.dotc.core.Contexts._
import dotty.tools.dotc.core.Types._

package object nscplugin {

  def newTermName(prefix: String): Name = prefix.toTermName
  def getRequiredClass(fullname: String)(implicit ctx: Context): ClassSymbol =
    ctx.requiredClass(fullname)
  def getRequiredModule(className: String)(implicit ctx: Context): TermSymbol =
    ctx.requiredModule(className)

  // def getMember(owner: ClassSymbol, name: Name): Symbol =
  //   owner.requiredMethodRef(name)
}
