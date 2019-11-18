package pl.atk.solver.model

import enumeratum.values._
import cats.implicits._

sealed abstract class Piece(val value: Int, val shortcut: String) extends IntEnumEntry

object Piece extends CatsOrderValueEnum[Int, Piece] with IntEnum[Piece] {

  val values = findValues

  case object Queen extends Piece(1, "Q")
  case object Rook extends Piece(2, "R")
  case object Knight extends Piece(4, "N")
  case object Bishop extends Piece(3, "B")
  case object King extends Piece(5, "K")

}
