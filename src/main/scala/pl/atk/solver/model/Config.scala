package pl.atk.solver.model

import pl.atk.solver.utils.Types.Coordinates

case class Config(
    pieces: List[Piece],
    chessboardSize: Coordinates,
    outputFile: String
)
