package cats

import cats.implicits._
import cats.effect.{Concurrent, ExitCode, IO, IOApp, Timer}
import cats.effect.concurrent.{MVar, Ref}
import cats.effect.implicits._

import scala.concurrent.duration._
import scala.util.Random

object WorkerPool extends IOApp {
    // To start, our requests can be modelled as simple functions.
    // You might want to replace this type with a class if you go for bonuses. Or not.
    final case class Worker[F[_], A, B](run: A => F[B])

    // Sample stateful worker that keeps count of requests it has accepted
    def mkWorker[F[_]](id: Int)(implicit timer: Timer[F], C: Concurrent[F]): F[Worker[F, Int, Int]] =
        Ref[F].of(0).map { counter =>
            def simulateWork: F[Unit] =
                C.delay(50 + Random.nextInt(450)).map(_.millis).flatMap(timer.sleep)

            def report: F[Unit] =
                counter.get.flatMap(i => C.delay(println(s"Total processed by $id: $i")))

            Worker(x =>
                simulateWork >>
                    counter.update(_ + 1) >>
                    report >>
                    C.delay(x + 1)
            )
        }

    trait WorkerPool[F[_], A, B] {
        def exec(a: A): F[B]
    }

    object WorkerPool {
        // Implement this constructor, and, correspondingly, the interface above.
        // You are free to use named or anonymous classes
        def of[F[_] : Concurrent : Timer, A, B](fs: List[Worker[F, A, B]]): F[WorkerPool[F, A, B]] =
            for {
                queue <- MVar[F].empty[Worker[F, A, B]]
                _     <- fs.map(queue.put).map(_.start.void).sequence

                pool = new WorkerPool[F, A, B] {
                    override def exec(a: A): F[B] =
                        Concurrent[F].uncancelable(
                            queue.take.flatMap(worker =>
                                worker.run(a).guarantee(queue.put(worker).start.void)
                            )
                        )
                }
            } yield pool
    }

    // Sample test pool to play with in IOApp
    def testPool(n: Int): IO[WorkerPool[IO, Int, Int]] =
        List.range(0, n)
            .traverse(mkWorker[IO])
            .flatMap(WorkerPool.of[IO, Int, Int])

    def run(args: List[String]): IO[ExitCode] = {
        for {
            n        <- IO.pure(10)
            replicas <- IO.pure(1)
            pool     <- testPool(n)
            _        <- List.range(0, n).parTraverseN(10)(_ => pool.exec(2).replicateA(replicas))
        } yield ExitCode.Success
    }
}
