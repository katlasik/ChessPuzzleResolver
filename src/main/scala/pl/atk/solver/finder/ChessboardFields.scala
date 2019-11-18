package pl.atk.solver.finder

import pl.atk.solver.utils.Types.{Coordinates, ErrorOr}
import pl.atk.solver.model.ChessResolverError.{FieldAlreadyOccupied, OutOfChessboardBounds}
import pl.atk.solver.model.{FieldStatus, Piece}
import pl.atk.solver.model.FieldsStatus._
import cats.implicits._
import cats._

private[finder] object ChessboardFields {

  def apply(chessboardSize: Coordinates): ChessboardFields = chessboardSize match {
    case (x, y) => new ChessboardFields(List.fill(y)(List.fill(x)(Empty)))
  }

  object Implicits {
    implicit val eqChessboardFields: Eq[ChessboardFields] = Eq.fromUniversalEquals[ChessboardFields]

    implicit val showChessboardFields: Show[ChessboardFields] = (cf: ChessboardFields) => {
      cf.fields
        .map(
          _.map {
            case Empty       => "-"
            case Occupied(p) => p.shortcut
          }.mkString("|")
        )
        .mkString(s"\n${"-+" * cf.fields.headOption.map(_.size - 1).getOrElse(0)}-\n")
    }
  }

}

private[finder] class ChessboardFields(private val fields: List[List[FieldStatus]]) {

  /**
    * Returns new ChessboardFields with new piece placed
    * @param coordinates coordinates of newly placed piece
    * @param piece piece type
    * @return new ChessboardFields with newly placed piece
    */
  def place(coordinates: Coordinates, piece: Piece): ErrorOr[ChessboardFields] = {
    isEmpty(coordinates)
      .map {
        case true =>
          coordinates match {
            case (x, y) => new ChessboardFields(fields.updated(y, fields(y).updated(x, Occupied(piece)))).asRight
          }
        case false => FieldAlreadyOccupied.asLeft
      }
      .getOrElse(OutOfChessboardBounds.asLeft)
  }

  /**
    * Returns option of true if field is empty, or false if occupied. If coordinates are out of bonds none is returned.
    * @param coordinates coordinates of field
    * @return option of true if field is not occupied, option of false when occupied, none is coordinates out of bonds
    */
  def isEmpty(coordinates: Coordinates): Option[Boolean] = coordinates match {
    case (x, y) => Either.catchOnly[IndexOutOfBoundsException](fields(y)(x) === Empty).toOption
  }

  /**
    * Returns option of status of field. If coordinates are out of bonds none is returned.
    * @param coordinates coordinates of field
    * @return option of status of field
    */
  def status(coordinates: Coordinates): Option[FieldStatus] = coordinates match {
    case (x, y) => Either.catchOnly[IndexOutOfBoundsException](fields(y)(x)).toOption
  }

  /**
    * Returns set of 4 chessboard fields: original, mirrored on x-axis, mirrored on y-axis, mirrored on both axis.
    * @return 4 chessboard fields
    */
  def mirrors(): Set[ChessboardFields] = {

    val reversedRows = fields.map(_.reverse)

    Set(
      this,
      new ChessboardFields(reversedRows),
      new ChessboardFields(reversedRows.reverse),
      new ChessboardFields(fields.reverse)
    )
  }

  override def equals(o: Any): Boolean = {
    o match {
      case cf: ChessboardFields => fields == cf.fields
      case _                    => false
    }
  }

  override def hashCode(): Int = fields.hashCode()

}
