package fs2

import cats.effect.{ExitCode, IO, IOApp}

/*
Нужно, чтобы каждые batchSize элементов стрима в лог выводился сам элемент,
    если следующий батч < batchSize, то выводить последний элемент стрима.
Пример:
    Stream(1, 2, 3, 4, 5, 6, 7, 8, 9)
    batchSize = 2
Ожидаемый результат:
    println = 2, 4, 6, 8, 9
 */

object Counter extends IOApp {
    //TODO: Implement this
    def counter(batchSize: Int, log: Int => IO[Unit]): Pipe[IO, Int, Unit] =
        stream => ???

    val stream: Stream[IO, Int] = Stream(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val batchSize: Int = 2

    override def run(args: List[String]): IO[ExitCode] = {
        stream
            .through(counter(batchSize, x => IO(println(x))))
            .compile
            .toList
            .as(ExitCode.Success)
    }
}
