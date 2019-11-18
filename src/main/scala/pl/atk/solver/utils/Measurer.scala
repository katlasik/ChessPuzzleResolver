package pl.atk.solver.utils

import monix.eval.Task

object Measurer {

  def measure[A](task: Task[A]): Task[(A, Long)] =
    Task.deferAction { sc =>
      val start = sc.currentTimeMillis
      val stopTimer = Task.delay(sc.currentTimeMillis() - start)
      task.flatMap( a => stopTimer.map(r => (a, r)))
    }

}
