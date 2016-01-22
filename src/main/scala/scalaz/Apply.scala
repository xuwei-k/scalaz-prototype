package scalaz

trait ExtendsApply[F[_]] extends ExtendsFunctor[F]{
  def apply: Apply[F]
}

////
/**
  *
  */
////
trait Apply[F[_]] extends ExtendsApply[F] { self: Functor[F] =>
  override def apply: Apply[F] = this
  override def functor: Functor[F] = this
  ////
  def ap[A,B](fa: => F[A])(f: => F[A => B]): F[B]

  def apply2[A, B, C](fa: => F[A], fb: => F[B])(f: (A, B) => C): F[C] =
    ap(fb)(map(fa)(f.curried))

  ////
}

object Apply {
  def apply[F[_]](implicit F: Apply[F]): Apply[F] = F

  ////
  ////
}
