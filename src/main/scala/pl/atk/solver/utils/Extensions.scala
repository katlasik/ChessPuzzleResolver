package pl.atk.solver.utils

import cats.implicits._

object Extensions {

  implicit class RichInt(n: Int) {

    /**
      * Returs half of value. If initial values is odd, returns half rounded up.
      * @return half or half rounded up
      */
    def halfRoundedUp(): Int = {
      if (n % 2 === 0) {
        n / 2
      } else {
        n / 2 + 1
      }
    }
  }

  implicit class RichPair(p: (Int, Int)) {

    /**
      * Returns all combinations of pairs from range.
      * @return all combinations of pairs from range
      */
    def to(x: Int, y: Int): List[(Int, Int)] = to((x, y))

    def to(e: (Int, Int)): List[(Int, Int)] = {
      (p, e) match {
        case ((sx, sy), (ex, ey)) => {
          ((sx until ex).toList, (sy until ey).toList).tupled.sorted
        }
      }
    }

  }

}
