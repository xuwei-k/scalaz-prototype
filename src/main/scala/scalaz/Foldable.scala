package scalaz

trait ExtendsFoldable[F[_]]{
  def foldable: Foldable[F]
}

////
/**
  *
  */
////
trait Foldable[F[_]] extends ExtendsFoldable[F] {
  override def foldable: Foldable[F] = this
  ////
  ////
}

object Foldable {
  def apply[F[_]](implicit F: Foldable[F]): Foldable[F] = F

  ////
  ////
}
