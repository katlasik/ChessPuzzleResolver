package pl.atk.solver.utils

import monix.execution.Scheduler.global
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import pl.atk.solver.model.ChessResolverError.CantWriteOutputToFile

class FileOutputterSpec extends Specification with Mockito {

  "The 'write' method should" >> {
    "be invoked right amount of times" >> {

      val wrapper = mock[WriterWrapper]

      val outputter = new FileOutputterImpl(wrapper)

      outputter.write(List.fill(10)("blabla")).coeval(global).value.right.get shouldEqual Right(())

      there were exactly(10)(wrapper).println("blabla")
      there were exactly(10)(wrapper).println
      there was one(wrapper).close

    }

    "return error if exception os thrown" >> {

      val wrapper = mock[WriterWrapper]
      val exception = new RuntimeException()

      wrapper.println("blabla").throws(exception)

      val outputter = new FileOutputterImpl(wrapper)

      outputter.write(List("blabla")).coeval(global).value.right.get shouldEqual Left(CantWriteOutputToFile(exception))




    }
  }

}
