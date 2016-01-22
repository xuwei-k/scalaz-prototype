package scalaz

trait ExtendsBind[F[_]] extends ExtendsApply[F]{
  def bind: Bind[F]
}

////
/**
  *
  */
////
trait Bind[F[_]] extends ExtendsBind[F] { self: Apply[F] =>
  override def bind: Bind[F] = this
  override def apply: Apply[F] = this
  ////
  def bind[A, B](fa: F[A])(f: A => F[B]): F[B]

  override def ap[A, B](fa: => F[A])(f: => F[A => B]): F[B] = {
    lazy val fa0 = fa
    bind(f)(functor.map(fa0))
  }
  ////
}

object Bind {
  def apply[F[_]](implicit F: Bind[F]): Bind[F] = F

  ////
  ////
}
