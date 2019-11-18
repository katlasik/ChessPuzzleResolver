package pl.atk.solver.model

sealed trait ChessResolverError

sealed trait CombinationsFinderError extends ChessResolverError
sealed trait FileOutputterError extends ChessResolverError
sealed trait CommandLineParserError extends ChessResolverError

object ChessResolverError {

  case object OutOfChessboardBounds extends CombinationsFinderError
  case object FieldAlreadyOccupied extends CombinationsFinderError
  case object NotAllPiecesWerePlaced extends CombinationsFinderError

  case class CantWriteOutputToFile(t: Throwable) extends FileOutputterError

  case object EmptyPieces extends CommandLineParserError
  case object IllegalChessboardSize extends CommandLineParserError
  case object ParseError extends CommandLineParserError

}
