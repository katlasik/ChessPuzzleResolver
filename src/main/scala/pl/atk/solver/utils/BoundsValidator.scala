package pl.atk.solver.utils

import Types.Coordinates

object BoundsValidator {

  /**
    * Check if coordinates are inside bounds from (0,0) to size.
    * @param size size of bounds
    * @param coordinates coordinates to be checked
    * @return true if coordinates are inside bounds
    */
  def check(size: Coordinates)(coordinates: Coordinates): Boolean = {
    (size, coordinates) match {
      case ((s1, s2), (x1, x2)) => 0 <= x1 && x1 < s1 && 0 <= x2 && x2 < s2
    }
  }
}
