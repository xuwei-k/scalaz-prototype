package scalaz

trait ExtendsTraverse[F[_]] extends ExtendsFoldable[F] with ExtendsFunctor[F]{
  def traverse: Traverse[F]
}

////
/**
  *
  */
////
trait Traverse[F[_]] extends ExtendsTraverse[F] { self: Foldable[F] with Functor[F] =>
  override def traverse: Traverse[F] = this
  override def foldable: Foldable[F] = this
  override def functor: Functor[F] = this
  ////
  ////
}

object Traverse {
  def apply[F[_]](implicit F: Traverse[F]): Traverse[F] = F

  ////
  ////
}
