package pl.atk.solver.utils

import java.io.{File, PrintWriter}

import cats.implicits._
import monix.eval.Task
import pl.atk.solver.model.ChessResolverError.CantWriteOutputToFile
import pl.atk.solver.utils.Types.ErrorOr

import scala.util.control.NonFatal

trait FileOutputter {

  /**
    * Writers list of strings to file.
    * @param output list of strings to be written
    * @return task containing error or unit value
    */
  def write(output: List[String]): Task[ErrorOr[Unit]]
}

//Wrapper trait for PrintWriter to allow mocking in tests
private trait WriterWrapper {
  def println(str: String): Unit
  def println(): Unit
  def close(): Unit
}

object FileOutputter {

  def apply(filename: String): FileOutputter = {
    // $COVERAGE-OFF$
    val pw = new PrintWriter(new File(filename))
    new FileOutputterImpl(new WriterWrapper {
      override def println(str: String): Unit = pw.println(str)
      override def println(): Unit = pw.println()
      override def close(): Unit = pw.close()
    })
    // $COVERAGE-ON$
  }
}

private class FileOutputterImpl(writer: WriterWrapper) extends FileOutputter {
  override def write(output: List[String]): Task[ErrorOr[Unit]] = {
    Task.now {
      try {
        output.foreach { o =>
          writer.println(o)
          writer.println()
        }.asRight
      } catch {
        case NonFatal(e) => CantWriteOutputToFile(e).asLeft
      } finally {
        writer.close()
      }
    }
  }
}
