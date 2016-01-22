package scalaz
package std

object list {

  implicit val listInstance: Monad[List] with Bind[List] with Applicative[List] with Apply[List] with Functor[List] =
    new Monad[List] with Bind[List] with Applicative[List] with Apply[List] with Functor[List] {
      def point[A](a: => A) =
        a :: Nil

      def bind[A, B](fa: List[A])(f: A => List[B]) =
        fa flatMap f

      def map[A, B](fa: List[A])(f: A => B) =
        fa map f
    }

}
