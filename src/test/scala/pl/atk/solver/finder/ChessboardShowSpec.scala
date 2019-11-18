package pl.atk.solver.finder

import cats.implicits._
import org.specs2.mutable.Specification
import pl.atk.solver.finder.Chessboard.Implicits._
import pl.atk.solver.model.Piece._

class ChessboardShowSpec extends Specification {

  val `0x0` = Chessboard((0, 0))

  val `3x3` = Chessboard((3, 3))
    .place((0, 0), Rook)
    .flatMap(_.place((1, 0), Knight))
    .flatMap(_.place((1, 2), King))
    .flatMap(_.place((2, 2), Knight))
    .right
    .get

  val `3x3 output` =
    """R|N|-
      |-+-+-
      |-|-|-
      |-+-+-
      |-|K|N""".stripMargin

  val `5x6` = Chessboard((5, 6))
    .place((0, 0), Rook)
    .flatMap(_.place((1, 0), Knight))
    .flatMap(_.place((1, 2), King))
    .flatMap(_.place((2, 2), Knight))
    .flatMap(_.place((4, 5), Rook))
    .flatMap(_.place((0, 5), Rook))
    .right
    .get

  val `5x6 output` =
    """R|N|-|-|-
      |-+-+-+-+-
      |-|-|-|-|-
      |-+-+-+-+-
      |-|K|N|-|-
      |-+-+-+-+-
      |-|-|-|-|-
      |-+-+-+-+-
      |-|-|-|-|-
      |-+-+-+-+-
      |R|-|-|-|R""".stripMargin

  val `7x3` = Chessboard((7, 3))
    .place((0, 0), Rook)
    .flatMap(_.place((1, 0), Knight))
    .flatMap(_.place((1, 2), King))
    .flatMap(_.place((5, 1), King))
    .flatMap(_.place((6, 2), Knight))
    .right
    .get

  val `7x3 output` =
    """R|N|-|-|-|-|-
      |-+-+-+-+-+-+-
      |-|-|-|-|-|K|-
      |-+-+-+-+-+-+-
      |-|K|-|-|-|-|N""".stripMargin


      Seq(
        `0x0` -> "",
        `3x3` -> `3x3 output`,
        `5x6` -> `5x6 output`,
        `7x3` -> `7x3 output`,
      ).foreach { case (c, o) =>
        "The 'draw' method should" >> {
          s"return correct representation of ${c.size._1}x${c.size._2} chessboard" >> {
            c.show shouldEqual o
          }
      }

  }
}
