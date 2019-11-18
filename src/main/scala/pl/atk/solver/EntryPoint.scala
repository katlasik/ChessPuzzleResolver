package pl.atk.solver

import monix.eval.{Task, TaskApp}
import pl.atk.solver.cli.CommandLineParser.{parse => parseConfig}
import pl.atk.solver.finder.CombinationsFinder.{find => findSolutions}
import pl.atk.solver.model.ChessResolverError.{EmptyPieces, IllegalChessboardSize}
import pl.atk.solver.utils.{FileOutputter, Measurer}
import pl.atk.solver.utils.Console._
import pl.atk.solver.utils.Measurer._

object EntryPoint extends TaskApp {

  override def runl(args: List[String]): Task[Unit] = {

    for {
      _ <- putStrLn(s"Looking for solutions...")
      (_, elapsedTime) <- measure {
        parseConfig(args) match {
          case Right(config) =>
            findSolutions(config.chessboardSize, config.pieces)
              .flatMap {
                case Right(r) =>
                  for {
                    _ <- putStrLn(s"Found ${r.size} solutions.")
                    _ <- putStrLn("Writing solutions to file...")
                    _ <- FileOutputter(config.outputFile).write(r)
                    _ <- putStrLn("Done.")
                  } yield ()
                case Left(e) => {
                  for {
                    _ <- logError(e)
                    _ <- putStrLn("Unfortunately, error has happened. Check log for details.")
                  } yield ()
                }
              }
          case Left(IllegalChessboardSize) => putStrLn("Chessboard needs to have both dimensions as positive numbers.")
          case Left(EmptyPieces)           => putStrLn("You need at least 1 piece to be placed.")
          case _                           => putStrLn("Couldn't parse commandline arguments.")
        }
      }
      _ <- putStrLn(s"It took about ${elapsedTime / 1000} second(s).")
    } yield ()
  }

}
