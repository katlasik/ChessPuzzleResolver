package pl.atk.solver.finder

import cats.{Eval, Order}
import pl.atk.solver.utils.Types.{Coordinates, ErrorOr}
import pl.atk.solver.model.Piece
import cats.implicits._
import monix.eval.Task
import pl.atk.solver.model.ChessResolverError.NotAllPiecesWerePlaced
import pl.atk.solver.utils.Extensions._
import pl.atk.solver.finder.ChessboardFields.Implicits._

object CombinationsFinder {

  /**
    * Finds all possible combinations how pieces can be placed on NxM chessboard.
    * @param chessboardSize size of chessboard
    * @param pieces pieces to be placed
    * @return Monix's task returning list of possible solutions as strings
    */
  def find(chessboardSize: Coordinates, pieces: List[Piece]): Task[ErrorOr[List[String]]] = {
    val (x, y) = chessboardSize
    val chessboard = Chessboard(chessboardSize)

    val sorted = pieces.sorted(implicitly[Order[Piece]].toOrdering)

    //since chessboard is symmetric, we need only to search for solutions in 1st quadrant
    val tasks = (0, 0)
      .to(x.halfRoundedUp(), y.halfRoundedUp())
      .map(startingPoint => Task(resolve(startingPoint, sorted, chessboard).value))

    Task
      .gather(tasks)
      .map {
        _.flatten
          .flatMap {
            //creating mirror solutions for rest of quadrants
            case Right(c)                     => c.fields.mirrors().map(_.show.asRight)
            case Left(NotAllPiecesWerePlaced) => Nil
            case Left(e)                      => List(e.asLeft)
          }
          .distinct
          .sequence
      }
  }

  private def resolve(startingPoint: Coordinates,
                      pieces: List[Piece],
                      chessboard: Chessboard): Eval[List[ErrorOr[Chessboard]]] = {

    Eval.always(pieces).flatMap {
      case x1 :: x2 :: xs =>
        chessboard.place(startingPoint, x1) match {
          case Right(c) =>
            c.unthreatenedFields(x2).toList match {
              case Nil                => Eval.now(List(NotAllPiecesWerePlaced.asLeft))
              case unthreatenedFields => resolveEvery(unthreatenedFields, x2 :: xs, c)
            }
          case l => Eval.now(List(l))
        }
      case List(x) => Eval.now(List(chessboard.place(startingPoint, x)))
    }

  }

  private def resolveEvery(startingPoints: List[Coordinates],
                           pieces: List[Piece],
                           chessboard: Chessboard): Eval[List[ErrorOr[Chessboard]]] = {

    Eval.always(startingPoints).flatMap {
      case x :: xs =>
        for {
          r <- resolve(x, pieces, chessboard)
          s <- resolveEvery(xs, pieces, chessboard)
        } yield s ++ r
      case Nil => Eval.now(Nil)
    }

  }

}
