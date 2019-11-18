package pl.atk.solver.finder.threat

import pl.atk.solver.utils.Types.Coordinates

private[threat] object CommonThreatPatterns {

  /**
    * Returns all threatened fields by pieces attacking using cross shape (like rook or queen).
    * @param coordinates coordinates of the middle of the cross
    * @param size size of chessboard
    * @return set of threatened fields coordinates including passed coordinates
    */
  def cross(coordinates: Coordinates, size: Coordinates): Set[(Int, Int)] =
    (coordinates, size) match {
      case ((x, y), (sx, sy)) => ((0 until sx).map((_, y)) ++ (0 until sy).map((x, _))).toSet
    }

  /**
    * Returns all threatened fields by pieces attacking using x-shape (like bishop or queen).
    * @param coordinates coordinates of the middle of the x
    * @param size size of chessboard
    * @return set of threatened fields coordinates including passed coordinates
    */
  def diagonals(coordinates: Coordinates, size: Coordinates): Set[(Int, Int)] =
    (coordinates, size) match {
      case ((x, y), (sx, _)) => (0 until sx).flatMap(a => List((a, -a + y + x), (a, a + y - x))).toSet
    }

}
