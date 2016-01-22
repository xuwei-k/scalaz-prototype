final case class TypeClass(val name: String, val parents: List[TypeClass])

object TypeClass {

  private[this] val set = collection.mutable.Set.empty[TypeClass]

  lazy val all: Set[TypeClass] = set.toSet

  def typeClass(name: String, parents: TypeClass*): TypeClass = {
    val t = new TypeClass(name, parents.toList)
    set += t
    t
  }

  val functor = typeClass("Functor")
  val apply_ = typeClass("Apply", functor)
  val applicative = typeClass("Applicative", apply_)
  val bind = typeClass("Bind", apply_)
  val monad = typeClass("Monad", bind, applicative)
  val foldable = typeClass("Foldable")
  val traverse = typeClass("Traverse", foldable, functor)

}
