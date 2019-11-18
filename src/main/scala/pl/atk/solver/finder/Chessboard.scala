package pl.atk.solver.finder

import cats._
import cats.implicits._
import pl.atk.solver.utils.Types.{Coordinates, ErrorOr}
import pl.atk.solver.model.{FieldStatus, Piece}
import pl.atk.solver.finder.ChessboardFields.Implicits._

private[finder] object Chessboard {

  object Implicits {
    implicit val showChessboard: Show[Chessboard] = (c: Chessboard) => c.fields.show
    implicit val eqChessboard: Eq[Chessboard] = Eq.fromUniversalEquals[Chessboard]
  }

  def apply(size: Coordinates): Chessboard = new Chessboard(size, Whitelist(size), ChessboardFields(size))

}

private[finder] class Chessboard(val size: Coordinates, val whitelist: Whitelist, val fields: ChessboardFields) {

  /**
    * Returns new chessboard with newly placed piece
    * @param coordinates coordinates of newly placed piece
    * @param piece piece type
    * @return
    */
  def place(coordinates: Coordinates, piece: Piece): ErrorOr[Chessboard] =
    fields
      .place(coordinates, piece)
      .map { pieces =>
        new Chessboard(
          size,
          whitelist.blacklist(coordinates, piece),
          pieces
        )
      }

  /**
    * Returns list of fields which are not threatened by already placed pieces and for given piece won't threat any of already placed pieces
    * @param piece piece to be placed
    * @return list of fields coordinated that are not threatened and won't threat any already placed piece
    */
  def unthreatenedFields(piece: Piece): Set[Coordinates] = whitelist.forPiece(piece)

  /**
    * Status of field, Could be Empty or Occupied
    * @param coordinates coordinates of fields
    * @return field's status
    */
  def fieldStatus(coordinates: Coordinates): Option[FieldStatus] = fields.status(coordinates)

  override def equals(o: Any): Boolean = o match {
    case c: Chessboard => fields == c.fields
    case _             => false
  }

  override def hashCode(): Int = fields.hashCode()

}
