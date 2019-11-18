package pl.atk.solver.cli

import org.specs2.mutable.Specification
import pl.atk.solver.model.ChessResolverError.{EmptyPieces, IllegalChessboardSize}
import pl.atk.solver.model.Config
import pl.atk.solver.model.Piece._

class CommandLineParserSpec extends Specification {

  "The 'parse' method should" >> {

    "return correct config for short arg names" >> {

      CommandLineParser.parse(
        "-o file.txt -Q 1 -K 1 -B 2 -N 1 -R 1 -w 4 -h 4".split(" ").toList
      ).right.get shouldEqual
        Config(
          List(Queen, Rook, Bishop, Bishop, Knight, King),
          (4,4),
          "file.txt"
        )

    }

    "return correct config for long arg names" >> {

      CommandLineParser.parse(
        "--output file.txt --queens 1 --kings 1 --bishops 2 --knights 1 --rooks 1 --width 4 --height 4".split(" ").toList
      ).right.get shouldEqual
        Config(
          List(Queen, Rook, Bishop, Bishop, Knight, King),
          (4,4),
          "file.txt"
        )
    }

    "return correct if no pieces are provider" >> {

      CommandLineParser.parse(
        "--output file.txt --width 4 --height 4".split(" ").toList
      ).left.get shouldEqual EmptyPieces
    }

    "return correct if chessboard size is incorrect" >> {

      CommandLineParser.parse(
        "--queens 1 -w 0 -h 1".split(" ").toList
      ).left.get shouldEqual IllegalChessboardSize
    }
  }
}
