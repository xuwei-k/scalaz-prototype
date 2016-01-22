package scalaz

trait ExtendsFunctor[F[_]]{
  def functor: Functor[F]
}

////
/**
  *
  */
////
trait Functor[F[_]] extends ExtendsFunctor[F] {
  override def functor: Functor[F] = this
  ////
  def map[A, B](fa: F[A])(f: A => B): F[B]
  ////
}

object Functor {
  def apply[F[_]](implicit F: Functor[F]): Functor[F] = F

  ////
  ////
}
