package pl.atk.solver.utils

import pl.atk.solver.model.ChessResolverError

object Types {
  type Coordinates = (Int, Int)
  type ErrorOr[C] = Either[ChessResolverError, C]
}
