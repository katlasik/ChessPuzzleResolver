# ChessPuzzleResolver

[![CircleCI](https://circleci.com/gh/katlasik/ChessPuzzleResolver/tree/master.svg?style=svg)](https://circleci.com/gh/katlasik/ChessPuzzleResolver/tree/master)

## Motivation 

This project is attempt to create program finding all solutions of **generalized n queen problem**,
where instead of only queens you can place any pieces (except pawns).

Program calculates solutions and then writes them in graphical form to output file.

## Running

You can run project with command:

    sbt run -mem 4096

Since running it takes a lot of memory, it using `-mem 4096` to increase
initial heap space is recommended.

Parameters of computation are passed as command line arguments:

    -o, --output <file>     Name of output file (if not specified 'output' is used)
    -w, --width <number>    Width of chessboard (defaults to 3)
    -h, --height <number>   Height of chessboard (defaults to 3)
    -N, --knights <number>  Number of knights (defaults to 0)
    -K, --kings <number>    Number of kings (defaults to 0)
    -Q, --queens <number>   Number of queens (defaults to 0)
    -R, --rooks <number>    Number of rooks (defaults to 0)
    -B, --bishops <number>  Number of bishops (defaults to 0)
    
For example running command as below would calculate combinations for chessboard of size **3x3** and for **1 king** and **2 rooks**:

    sbt "run -R 2 -K 1 -w 3 -h 3"
    
By default solutions are outputted to file output in working directory. You can change name of file using parameter `--output`. 

## Testing
You can run test suites with:

    sbt test
    
## Building
You can build project with:

    sbt assembly

Then you'd be able to run it as standalone jar:

    java -Xms4096m -jar target/scala-2.12/ChessResolver-assembly-0.0.1-SNAPSHOT.jar -N 1 -B 2 -Q 2 -K 2 -w 7 -h 7

As said previously, program will work faster if you increase your intial heap with `-Xms4096m`

