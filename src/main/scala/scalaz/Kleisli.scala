package scalaz

final case class Kleisli[F[_], A, B](run: A => F[B]) {
  def map[C](f: B => C)(implicit F: Functor[F]): Kleisli[F, A, C] =
    Kleisli(run.andThen(F.map(_)(f)))

  def flatMap[C](f: B => Kleisli[F, A, C])(implicit F: Bind[F]): Kleisli[F, A, C] =
    Kleisli((r: A) => F.bind(run(r))(((b: B) => f(b).run(r))))
}

// no longer need prioritized classes. just use companion object
object Kleisli {
  implicit def kleisliFunctor[F[_]: Functor, A]: Functor[({type l[a] = Kleisli[F, A, a]})#l] =
    new KleisliFunctor[F, A] {
      def F0 = implicitly
    }

  implicit def kleisliApply[F[_]: Apply, A]: Apply[({type l[a] = Kleisli[F, A, a]})#l] =
    new KleisliApply[F, A] {
      def F1 = implicitly
    }

  implicit def kleisliApplicative[F[_]: Applicative, A]: Applicative[({type l[a] = Kleisli[F, A, a]})#l] =
    new KleisliApplicative[F, A] {
      def F2 = implicitly
    }

  implicit def kleisliBind[F[_]: Bind, A]: Bind[({type l[a] = Kleisli[F, A, a]})#l] =
    new KleisliBind[F, A] {
      def F3 = implicitly
    }

  implicit def kleisliMonad[F[_]: Monad, A]: Monad[({type l[a] = Kleisli[F, A, a]})#l] =
    new KleisliMonad[F, A] {
      def F4 = implicitly
    }
}

private trait KleisliFunctor[F[_], R] extends Functor[({type l[a] = Kleisli[F, R, a]})#l] {
  implicit def F0: Functor[F]
  override def map[A, B](fa: Kleisli[F, R, A])(f: A => B) =
    fa map f
}

private trait KleisliApply[F[_], R] extends Apply[({type λ[α] = Kleisli[F, R, α]})#λ] with KleisliFunctor[F, R] {
  implicit def F1: Apply[F]
  override def F0 = F1.functor
  override def ap[A, B](fa: => Kleisli[F, R, A])(f: => Kleisli[F, R, A => B]) =
    Kleisli[F, R, B](r => F1.ap(fa.run(r))(f.run(r)))
}

private trait KleisliApplicative[F[_], R] extends Applicative[({type λ[α] = Kleisli[F, R, α]})#λ] with KleisliApply[F, R] {
  implicit def F2: Applicative[F]
  override def F1 = F2.apply
  def point[A](a: => A) =
    Kleisli((r: R) => F2.point(a))
}

private trait KleisliBind[F[_], R] extends Bind[({type λ[α] = Kleisli[F, R, α]})#λ] with KleisliApply[F, R] {
  implicit def F3: Bind[F]
  override def F1 = F3.apply
  def bind[A, B](fa: Kleisli[F, R, A])(f: A => Kleisli[F, R, B]) =
    fa flatMap f
}

private trait KleisliMonad[F[_], R] extends Monad[({type λ[α] = Kleisli[F, R, α]})#λ] with KleisliApplicative[F, R] with KleisliBind[F, R] {
  implicit def F4: Monad[F]
  override def F2 = F4.applicative
  override def F3 = F4.bind
}
