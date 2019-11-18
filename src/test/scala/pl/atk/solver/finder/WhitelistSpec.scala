package pl.atk.solver.finder

import org.specs2.mutable.Specification
import pl.atk.solver.model.Piece.{Queen, Rook}

import scala.collection.immutable.TreeSet

class WhitelistSpec extends Specification {

  "The 'apply' method should" >> {

    "return correct list" >> {

      Whitelist((6,8)).forPiece(Rook) shouldEqual
        TreeSet(
          (0,0), (0,1), (0,2), (0,3), (0,4), (0,5), (0,6), (0,7),
          (1,0), (1,1), (1,2), (1,3), (1,4), (1,5), (1,6), (1,7),
          (2,0), (2,1), (2,2), (2,3), (2,4), (2,5), (2,6), (2,7),
          (3,0), (3,1), (3,2), (3,3), (3,4), (3,5), (3,6), (3,7),
          (4,0), (4,1), (4,2), (4,3), (4,4), (4,5), (4,6), (4,7),
          (5,0), (5,1), (5,2), (5,3), (5,4), (5,5), (5,6), (5,7)
        )

    }
  }

  "The 'blacklist' method should" >> {

    "return correct list if checked for different piece type" >> {

      Whitelist((6,8))
        .blacklist((1,1), Queen)
        .forPiece(Rook) shouldEqual
        TreeSet(
          (0,3), (0,4), (0,5), (0,6), (0,7), (2,3), (2,4), (2,5), (2,6), (2,7),
          (3,0), (3,2), (3,4), (3,5), (3,6), (3,7), (4,0), (4,2), (4,3),
          (4,5), (4,6), (4,7), (5,0), (5,2), (5,3), (5,4), (5,6), (5,7)
        )

    }

    "return correct list if checked for same piece type" >> {

      Whitelist((6,8))
        .blacklist((1,1), Queen)
        .forPiece(Queen) shouldEqual
        TreeSet(
          (2,3), (2,4), (2,5), (2,6), (2,7), (3,0), (3,2), (3,4), (3,5), (3,6),
          (3,7), (4,0), (4,2), (4,3), (4,5), (4,6), (4,7), (5,0), (5,2), (5,3),
          (5,4), (5,6), (5,7)
        )

    }
  }
}
