package pl.atk.solver.finder

import cats.Order
import cats.implicits._
import cats.kernel.Comparison.GreaterThan
import pl.atk.solver.utils.Types.Coordinates
import pl.atk.solver.finder.threat.ThreatPattern.{find => threatenedFields}
import pl.atk.solver.model.Piece
import pl.atk.solver.utils.Extensions._

import scala.collection.immutable.TreeSet

class Whitelist private (whitelists: Map[Piece, Set[Coordinates]], size: Coordinates) {

  /**
    * Returns new whitelist with field threatened by newly placed piece blacklisted
    * @param coordinates Coordinates of placed piece
    * @param piece Piece type
    * @return new Whitelist
    */
  def blacklist(coordinates: Coordinates, piece: Piece): Whitelist = {

    //if we're placing same piece type as in previous iteration, we should only go forward(right and down),
    //therefore we drop preceding fields, in other hand if piece is of another type, we should check
    // both back (left and up) and forward.
    def dropPrecedingWhen(whitelist: Set[Coordinates])(when: Boolean): Set[(Int, Int)] =
      if (when) whitelist.dropWhile(implicitly[Order[Coordinates]].comparison(_, coordinates) =!= GreaterThan)
      else whitelist

    new Whitelist(
      whitelists
        .map {
          case (variant, whitelist) =>
            variant -> (
              dropPrecedingWhen(whitelist)(variant === piece)
                -- threatenedFields(variant, coordinates, size)
                -- threatenedFields(piece, coordinates, size)
            )
        },
      size
    )
  }

  /**
    * Returns list of fields where piece could be placed without threatening any other piece or be threatened by any other.
    * @param piece type of piece
    * @return list of fields which are not threatened or placing piece on it won't threat any other piece
    */
  def forPiece(piece: Piece): Set[Coordinates] = whitelists(piece)

}

object Whitelist {

  def apply(size: Coordinates): Whitelist = {
    new Whitelist(
      Piece
        .values
        .view //coordinates are sorted by rows from top to bottom
        .map(_ -> TreeSet((0, 0).to(size): _*)(implicitly[Order[Coordinates]].toOrdering))
        .toMap,
      size
    )
  }

}
