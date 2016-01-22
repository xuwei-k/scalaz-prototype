package scalaz

object KleisliTest {

  def functor[F[_]: Functor, A] = Functor[({type l[a] = Kleisli[F, A, a]})#l]

  // `could not find implicit value for parameter F: scalaz.Functor[[a]scalaz.Kleisli[F,A,a]]`
  // NOT ambiguous implicit error

  // def functor[F[_]: Apply, A] = Functor[({type l[a] = Kleisli[F, A, a]})#l]

}
