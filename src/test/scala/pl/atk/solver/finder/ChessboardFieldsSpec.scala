package pl.atk.solver.finder

import org.specs2.mutable.Specification
import pl.atk.solver.model.ChessResolverError.{FieldAlreadyOccupied, OutOfChessboardBounds}
import pl.atk.solver.model.FieldsStatus._
import pl.atk.solver.model.Piece._

class ChessboardFieldsSpec extends Specification {

  "The 'isEmpty' method should" >> {
    "return right value" >> {

      val cf = new ChessboardFields(
        List(
          List(Empty, Occupied(Rook), Empty),
          List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
          List(Occupied(King), Empty, Occupied(King)),
        )
      )

      cf.isEmpty((0,0)) shouldEqual Some(true)
      cf.isEmpty((1,1)) shouldEqual Some(false)
      cf.isEmpty((4,4)) shouldEqual None

    }
  }

  "The 'place' method should" >> {
    "return right value" >> {

      val cf = new ChessboardFields(
        List(
          List(Empty, Occupied(Rook), Empty),
          List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
          List(Occupied(King), Empty, Occupied(King)),
        )
      )

      cf.place((0,0), Rook) shouldEqual Right(
        new ChessboardFields(
          List(
            List(Occupied(Rook), Occupied(Rook), Empty),
            List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
            List(Occupied(King), Empty, Occupied(King)),
          )
        )
      )

      cf.place((1,1), Rook) shouldEqual Left(FieldAlreadyOccupied)
      cf.place((4,4), Rook) shouldEqual Left(OutOfChessboardBounds)

    }
  }

  "The 'status' method should" >> {
    "return right value" >> {

      val cf = new ChessboardFields(
        List(
          List(Empty, Occupied(Rook), Empty),
          List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
          List(Occupied(King), Empty, Occupied(King)),
        )
      )

      cf.status((0,0)) shouldEqual Some(Empty)
      cf.status((1,1)) shouldEqual Some(Occupied(Rook))
      cf.isEmpty((4,4)) shouldEqual None

    }
  }

  "The 'mirrors' method should" >> {
    "return right mirrored chessboard fields" >> {

      new ChessboardFields(
        List(
          List(Empty, Occupied(Rook), Empty),
          List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
          List(Occupied(King), Empty, Occupied(King)),
        )
      ).mirrors() shouldEqual Set(
        new ChessboardFields(
          List(
            List(Empty, Occupied(Rook), Empty),
            List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
            List(Occupied(King), Empty, Occupied(King))
          )
        ),
        new ChessboardFields(
          List(
            List(Empty, Occupied(Rook), Empty),
            List(Occupied(Knight), Occupied(Rook), Occupied(Queen)),
            List(Occupied(King), Empty, Occupied(King))
          )
        ),
        new ChessboardFields(
          List(
            List(Occupied(King), Empty, Occupied(King)),
            List(Occupied(Knight), Occupied(Rook), Occupied(Queen)),
            List(Empty, Occupied(Rook), Empty)
          )
        ),
        new ChessboardFields(
          List(
            List(Occupied(King), Empty, Occupied(King)),
            List(Occupied(Queen), Occupied(Rook), Occupied(Knight)),
            List(Empty, Occupied(Rook), Empty)
          )
        ),
      )


    }
  }

}
