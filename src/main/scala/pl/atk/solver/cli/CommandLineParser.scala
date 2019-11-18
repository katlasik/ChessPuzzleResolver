package pl.atk.solver.cli

import pl.atk.solver.model.ChessResolverError.{EmptyPieces, IllegalChessboardSize, ParseError}
import pl.atk.solver.model.{ChessResolverError, Config, Piece}
import pl.atk.solver.model.Piece._
import cats.implicits._
import pl.atk.solver.utils.Types.{Coordinates, ErrorOr}

object CommandLineParser {

  private lazy val parser = new scopt.OptionParser[CommandLineArgs]("ChessSolver") {
    head("ChessSolver", "0.1")

    opt[String]('o', "output")
      .action((x, c) => c.copy(filename = x))
      .valueName("<file>")
      .text("Name of output file (if not specified 'output' is used)")

    opt[Int]('w', "width")
      .valueName("<number>")
      .action((x, c) => c.copy(width = x))
      .text("Width of chessboard (defaults to 3)")

    opt[Int]('h', "height")
      .valueName("<number>")
      .action((x, c) => c.copy(height = x))
      .text("Height of chessboard (defaults to 3)")

    opt[Int]('N', "knights")
      .valueName("<number>")
      .action((x, c) => c.copy(knights = x))
      .text("Number of knights (defaults to 0)")

    opt[Int]('K', "kings")
      .valueName("<number>")
      .action((x, c) => c.copy(kings = x))
      .text("Number of kings (defaults to 0)")

    opt[Int]('Q', "queens")
      .valueName("<number>")
      .action((x, c) => c.copy(queens = x))
      .text("Number of queens (defaults to 0)")

    opt[Int]('R', "rooks")
      .valueName("<number>")
      .action((x, c) => c.copy(rooks = x))
      .text("Number of rooks (defaults to 0)")

    opt[Int]('B', "bishops")
      .valueName("<number>")
      .action((x, c) => c.copy(bishops = x))
      .text("Number of bishops (defaults to 0)")
  }

  def parse(args: List[String]): ErrorOr[Config] = {

    def validatePieces(pieces: List[Piece]): ErrorOr[List[Piece]] =
      if (pieces.isEmpty) EmptyPieces.asLeft else pieces.asRight

    def validateChessboardSize(size: Coordinates): ErrorOr[Coordinates] =
      size match {
        case (x, y) => if (x > 0 && y > 0) size.asRight else IllegalChessboardSize.asLeft
      }

    def createPiecesList(c: CommandLineArgs) =
      List(
        List.fill(c.queens)(Queen),
        List.fill(c.rooks)(Rook),
        List.fill(c.bishops)(Bishop),
        List.fill(c.knights)(Knight),
        List.fill(c.kings)(King)
      ).flatten

    Either
      .fromOption(
        parser.parse(args, CommandLineArgs()),
        ParseError: ChessResolverError
      )
      .flatMap { c =>
        (
          validatePieces(createPiecesList(c)),
          validateChessboardSize((c.width, c.height)),
          c.filename.asRight: ErrorOr[String]
        ).mapN(Config)
      }
  }

}
