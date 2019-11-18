package pl.atk.solver.finder

import org.specs2.mutable.Specification
import pl.atk.solver.model.ChessResolverError.{FieldAlreadyOccupied, OutOfChessboardBounds}
import pl.atk.solver.model.FieldsStatus.{Empty, Occupied}
import pl.atk.solver.model.Piece._

import scala.collection.immutable.TreeSet


class ChessboardSpec extends Specification {

  Seq(
    Chessboard((5, 5)).place((1, 1), Rook)
      .right
      .get
      .unthreatenedFields(Rook) -> TreeSet((2,0), (2,2), (2,3), (2,4), (3,0), (3,2), (3,3), (3,4), (4,0), (4,2), (4,3), (4,4)),
    Chessboard((5, 5)).place((1, 1), King)
      .right
      .get
      .unthreatenedFields(Rook) -> TreeSet((0,3), (0,4), (2,3), (2,4), (3,0), (3,2), (3,3), (3,4), (4,0), (4,2), (4,3), (4,4)),
    Chessboard((5, 5)).place((1, 1), Knight)
      .right
      .get
      .unthreatenedFields(Rook) -> TreeSet((0,0), (0,2), (0,4), (2,0), (2,2), (2,4), (3,3), (3,4), (4,0), (4,2), (4,3), (4,4)),
    Chessboard((5, 5)).place((1, 1), Rook)
      .right
      .get
      .unthreatenedFields(King) -> TreeSet((0,3), (0,4), (2,3), (2,4), (3,0), (3,2), (3,3), (3,4), (4,0), (4,2), (4,3), (4,4)),
    Chessboard((5, 5))
      .place((1, 1), Rook)
      .flatMap(_.place((2,2), Rook))
      .right
      .get
      .unthreatenedFields(Knight) -> TreeSet((0,0), (0,4),(3,3), (4,0), (4,4)),
    Chessboard((5, 5))
      .place((1, 1), Knight)
      .flatMap(_.place((4,3), Knight))
      .right
      .get
      .unthreatenedFields(Knight) -> TreeSet((4,4))

  ).foreach { case (c, f) =>
    "The 'unthreathedFields' method should" >> {
      "return correct field list" >> {
        c shouldEqual f
      }
    }

  }

  "The 'fieldStatus' method should" >> {
    "return correct status if field is occupied" >> {

      Chessboard((5,5)).place((1, 1), Rook)
          .map(_.fieldStatus((1,1))).right.get shouldEqual Some(Occupied(Rook))

      Chessboard((2,7)).place((1, 5), King)
        .map(_.fieldStatus((1,5))).right.get shouldEqual Some(Occupied(King))

      Chessboard((3, 6)).place((1, 5), Knight)
        .map(_.fieldStatus((1,5))).right.get shouldEqual Some(Occupied(Knight))

      Chessboard((3, 6)).place((2, 2), Queen)
        .map(_.fieldStatus((2,2))).right.get shouldEqual Some(Occupied(Queen))

      Chessboard((3, 6)).place((1, 1), Bishop)
        .map(_.fieldStatus((1,1))).right.get shouldEqual Some(Occupied(Bishop))

    }

    "return correct status if field is safe" >> {

      Chessboard((5,5)).place((1, 1), Rook)
        .map(_.fieldStatus((2,2))).right.get shouldEqual Some(Empty)

      Chessboard((2,7)).place((1, 2), King)
        .map(_.fieldStatus((1,5))).right.get shouldEqual Some(Empty)

      Chessboard((3,7)).place((1, 2), Knight)
        .map(_.fieldStatus((1,5))).right.get shouldEqual Some(Empty)

      Chessboard((3,7)).place((2, 6), Queen)
        .map(_.fieldStatus((1,3))).right.get shouldEqual Some(Empty)

      Chessboard((3,7)).place((2, 4), Bishop)
        .map(_.fieldStatus((2,5))).right.get shouldEqual Some(Empty)

    }
  }

  "The 'place' method should" >> {

    "return error if piece is placed outside bounds of Chessboard" >> {

      Chessboard((5,5)).place((-1, 0), Rook).left.get shouldEqual OutOfChessboardBounds
      Chessboard((5,5)).place((5, 4), Rook).left.get shouldEqual OutOfChessboardBounds

    }

    "return error if piece is placed on occupied field" >> {

      Chessboard((5,5))
        .place((0, 0), Rook)
        .flatMap(_.place((0,0), Rook)).left.get shouldEqual FieldAlreadyOccupied

    }

    "return new Chessboard if piece is places correctly" >> {

      val c = Chessboard((5,5))
        .place((0, 0), Rook)
        .flatMap(_.place((0,2), Queen))
        .flatMap(_.place((4,4), King))
        .right
        .get

        c.fieldStatus((0,0)) shouldEqual Some(Occupied(Rook))
        c.fieldStatus((0,2)) shouldEqual Some(Occupied(Queen))
        c.fieldStatus((4,4)) shouldEqual Some(Occupied(King))
    }


  }
}
