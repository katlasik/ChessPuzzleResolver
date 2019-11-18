package pl.atk.solver.finder

import org.specs2.mutable.Specification
import pl.atk.solver.model.Piece.{King, Knight, Rook}
import monix.execution.Scheduler.global
import pl.atk.solver.model.ChessResolverError.OutOfChessboardBounds

import scala.concurrent.duration._
import scala.concurrent.Await


class CombinationsFinderSpec extends Specification  {

  "The 'find' method should" >> {

    "return error if arguments are not correct" >> {
      Await.result(
        CombinationsFinder
          .find((-1,4), List(Rook, Rook, King))
          .runAsync(global),
        5 seconds
      )
        .left
        .get shouldEqual OutOfChessboardBounds

    }

    "return correct list" >> {
      Await.result(
        CombinationsFinder
          .find((3,3), List(Rook, Rook, King))
          .runAsync(global),
        5 seconds
      )
        .right
        .get shouldEqual List(
        """-|-|K
          |-+-+-
          |R|-|-
          |-+-+-
          |-|R|-""",

       """|K|-|-
          |-+-+-
          |-|-|R
          |-+-+-
          |-|R|-""",

       """|-|R|-
          |-+-+-
          |-|-|R
          |-+-+-
          |K|-|-""",

       """|-|R|-
          |-+-+-
          |R|-|-
          |-+-+-
          |-|-|K"""
      ).map(_.stripMargin)

    }

    "return list of solutions with correct length" >> {

      Await.result(
        CombinationsFinder
          .find((3,3), List(Rook, Rook))
          .runAsync(global),
        5 seconds
      )
        .right
        .get
        .length shouldEqual 18

      Await.result(
        CombinationsFinder
          .find((3,3), List(Rook, Rook, Knight))
          .runAsync(global),
        5 seconds
      )
        .right
        .get
        .length shouldEqual 6

      Await.result(
        CombinationsFinder
          .find((4,4), List(Rook, Rook, Knight, Knight, Knight, Knight))
          .runAsync(global),
        5 seconds
      )
        .right
        .get
        .length shouldEqual 8


    }
  }

}
