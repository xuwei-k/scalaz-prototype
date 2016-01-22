package scalaz

trait ExtendsApplicative[F[_]] extends ExtendsApply[F]{
  def applicative: Applicative[F]
}

////
/**
  *
  */
////
trait Applicative[F[_]] extends ExtendsApplicative[F] { self: Apply[F] =>
  override def applicative: Applicative[F] = this
  override def apply: Apply[F] = this
  ////

  def point[A](a: => A): F[A]

  ////
}

object Applicative {
  def apply[F[_]](implicit F: Applicative[F]): Applicative[F] = F

  ////
  ////
}
