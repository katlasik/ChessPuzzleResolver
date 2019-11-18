package pl.atk.solver.model

import cats.Order
import org.specs2.mutable.Specification
import pl.atk.solver.model.Piece._


class PieceSpec extends Specification {

  "The List of PieceType should" >> {

    "sort in right order" >> {
      List(Rook, Queen, King, Bishop, Knight).sorted(implicitly[Order[Piece]].toOrdering) shouldEqual
        List(Queen, Rook, Bishop, Knight,  King)
    }

  }
}

