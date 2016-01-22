package scalaz

trait ExtendsMonad[F[_]] extends ExtendsBind[F] with ExtendsApplicative[F]{
  def monad: Monad[F]
}

////
/**
  *
  */
////
trait Monad[F[_]] extends ExtendsMonad[F] { self: Bind[F] with Applicative[F] =>
  override def monad: Monad[F] = this
  override def bind: Bind[F] = this
  override def applicative: Applicative[F] = this
  ////
  ////
}

object Monad {
  def apply[F[_]](implicit F: Monad[F]): Monad[F] = F

  ////
  ////
}
