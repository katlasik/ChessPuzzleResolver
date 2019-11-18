package pl.atk.solver.finder.threat

import pl.atk.solver.model.Piece
import pl.atk.solver.utils.Types.Coordinates
import pl.atk.solver.model.Piece._
import pl.atk.solver.utils.BoundsValidator.check

private[threat] trait ThreatPattern[P] {
  def threatenedFields(coordinates: Coordinates, chessboardSize: Coordinates): Set[Coordinates]
}

private[finder] object ThreatPattern {

  import CommonThreatPatterns._

  private val rookThreatPattern: ThreatPattern[Rook.type] =
    (coordinates: Coordinates, size: Coordinates) => cross(coordinates, size)

  private val queenPattern: ThreatPattern[Queen.type] =
    (coordinates: Coordinates, size: Coordinates) => diagonals(coordinates, size) ++ cross(coordinates, size)

  private val bishopPattern: ThreatPattern[Bishop.type] =
    (coordinates: Coordinates, size: Coordinates) => diagonals(coordinates, size)

  private val knightThreatPattern: ThreatPattern[Knight.type] =
    (coordinates: Coordinates, size: Coordinates) => {

      coordinates match {
        case (x, y) =>
          Set(
            (x, y),
            (x - 2, y - 1),
            (x - 1, y - 2),
            (x + 1, y - 2),
            (x + 2, y - 1),
            (x - 2, y + 1),
            (x - 1, y + 2),
            (x + 1, y + 2),
            (x + 2, y + 1)
          ).filter(check(size))
      }

    }

  private val kingThreatPattern: ThreatPattern[King.type] =
    (coordinates: Coordinates, size: Coordinates) => {

      coordinates match {
        case (x, y) =>
          Set(
            (x, y),
            (x - 1, y),
            (x - 1, y - 1),
            (x + 1, y),
            (x + 1, y - 1),
            (x, y + 1),
            (x - 1, y + 1),
            (x, y - 1),
            (x + 1, y + 1),
          ).filter(check(size))
      }

    }

  /**
   * Finds fields threatened by piece on specific coordinates
   * @param piece Type of piece
   * @param coordinates Coordinates where piece is placed
   * @param chessboardSize Size of chessboard
   * @return set of coordinates threatened by placed piece
   */
  def find(piece: Piece, coordinates: Coordinates, chessboardSize: Coordinates): Set[Coordinates] = {

    piece match {
      case Rook   => rookThreatPattern.threatenedFields(coordinates, chessboardSize)
      case King   => kingThreatPattern.threatenedFields(coordinates, chessboardSize)
      case Queen  => queenPattern.threatenedFields(coordinates, chessboardSize)
      case Bishop => bishopPattern.threatenedFields(coordinates, chessboardSize)
      case Knight => knightThreatPattern.threatenedFields(coordinates, chessboardSize)
    }

  }

}
