package pl.atk.solver.utils

import com.typesafe.scalalogging.LazyLogging
import monix.eval.Task
import pl.atk.solver.model.ChessResolverError

object  Console extends LazyLogging {

  def putStrLn(str: String): Task[Unit] = Task.now(println(str))
  def logError(e: ChessResolverError): Task[Unit] = Task.now(logger.error("Error: {}", e))


}
