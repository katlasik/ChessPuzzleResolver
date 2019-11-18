package pl.atk.solver.utils

import org.specs2.mutable.Specification
import pl.atk.solver.utils.Extensions._

class ExtensionsSpec extends Specification {

  "The 'RichInt.halfRoundedUp' method should" >> {
    "return right int value" >> {
      5.halfRoundedUp shouldEqual 3
      10.halfRoundedUp shouldEqual 5
    }
  }

  "The 'RichPair.to' method should" >> {
    "return right values" >> {
      (0,0) to (2,2) shouldEqual List((0,0), (0,1), (1,0), (1,1))
      (1,1) to (2,2) shouldEqual List((1,1))
      (1,1) to (3,3) shouldEqual List((1,1), (1,2), (2,1), (2,2))
    }
  }

}
