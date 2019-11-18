package pl.atk.solver.utils

import org.specs2.mutable.Specification

class BoundsValidatorSpec extends Specification {

  "The 'check' method should" >> {

    "return 'true' if coordinates are correct" >> {

      BoundsValidator.check((3,3))((1,1)) shouldEqual true
      BoundsValidator.check((3,3))((2,2)) shouldEqual true
      BoundsValidator.check((3,3))((0,0)) shouldEqual true
      BoundsValidator.check((3,3))((0,1)) shouldEqual true
      BoundsValidator.check((3,3))((2,1)) shouldEqual true
      BoundsValidator.check((5,1))((4,0)) shouldEqual true

    }

    "return 'false' if coordinates are correct" >> {

      BoundsValidator.check((3,3))((4,3)) shouldEqual false
      BoundsValidator.check((3,3))((3,3)) shouldEqual false
      BoundsValidator.check((3,3))((-1,0)) shouldEqual false
      BoundsValidator.check((3,3))((0,-1)) shouldEqual false
      BoundsValidator.check((3,3))((5,1)) shouldEqual false

    }
  }
}
