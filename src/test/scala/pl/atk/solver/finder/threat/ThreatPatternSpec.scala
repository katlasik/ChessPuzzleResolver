package pl.atk.solver.finder.threat

import org.specs2.mutable.Specification
import pl.atk.solver.model.Piece._

class ThreatPatternSpec extends Specification {

  "The 'find' method should" >> {

    "return correct threatened fields for Rook" >> {

      ThreatPattern.find(Rook, (1,1), (3,4)) shouldEqual
        Set((0,1), (1,1), (2,1), (1,0), (1,2), (1,3))

    }

    "return correct threatened fields for King" >> {

      ThreatPattern.find(King, (1,1), (3,4)) shouldEqual
        Set((0,1), (0,0), (2,1), (2,0), (1,1), (1,2), (0,2), (1,0), (2,2))

    }

    "return correct threatened fields for Knight" >> {

      ThreatPattern.find(Knight, (2,2), (5,5)) shouldEqual
        Set((0,1), (1,0), (3,0), (4,1), (2,2), (0,3), (1,4), (3,4), (4,3))
    }

    "return correct threatened fields for Queen" >> {

      ThreatPattern.find(Queen, (2,2), (5,5)) shouldEqual
        Set(
          (0,4), (0,0), (1,3), (1,1), (2,2), (3,1),
          (3,3), (4,0), (4,4), (0,2), (1,2), (3,2),
          (4,2), (2,0), (2,1), (2,3), (2,4)
        )
    }

    "return correct threatened fields for Bishop" >> {

      ThreatPattern.find(Bishop, (2,2), (5,5)) shouldEqual
        Set((0,4), (0,0), (1,3), (1,1), (2,2), (3,1), (3,3), (4,0), (4,4))
    }


  }
}
