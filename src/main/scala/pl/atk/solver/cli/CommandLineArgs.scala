package pl.atk.solver.cli

case class CommandLineArgs(
    width: Int = 3,
    height: Int = 3,
    filename: String = "output",
    kings: Int = 0,
    queens: Int = 0,
    bishops: Int = 0,
    rooks: Int = 0,
    knights: Int = 0
)
