package pl.atk.solver.model

import enumeratum._
import pl.atk.solver.model.Piece.{Bishop, King, Knight, Queen, Rook}

sealed trait FieldStatus extends EnumEntry

case object FieldsStatus extends Enum[FieldStatus] with CatsEnum[FieldStatus] {

  val values = findValues

  case object Empty extends FieldStatus
  sealed trait Occupied extends FieldStatus {
    val piece: Piece
  }

  //used singletons to save memory
  object Occupied {
    case object OccupiedByRook extends Occupied {
      override val piece = Rook
    }
    case object OccupiedByKing extends Occupied {
      override val piece = King
    }
    case object OccupiedByBishop extends Occupied {
      override val piece = Bishop
    }
    case object OccupiedByKnight extends Occupied {
      override val piece = Knight
    }
    case object OccupiedByQueen extends Occupied {
      override val piece = Queen
    }

    def apply(piece: Piece): Occupied = {

      piece match {
        case Rook   => OccupiedByRook
        case Bishop => OccupiedByBishop
        case Knight => OccupiedByKnight
        case Queen  => OccupiedByQueen
        case King   => OccupiedByKing
      }

    }

    def unapply(occupied: Occupied): Option[Piece] = Some(occupied.piece)
  }

}
