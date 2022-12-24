package d10

import util.readInput

var sommeCycle : Int = 0

var map = Array<Array<Char>>(6) { Array<Char>(40) {'.'} }

fun checkCycle(cyc: Int, x: Int) {
    if (cyc%40 == 20) {
        sommeCycle += x * cyc
    }

    var cycle = (cyc%40)

    if (x + 1 == cycle || x == (cycle)  || x + 2== (cycle)) {
        map[(cyc )/40][(cyc)%40]='x'
    } else {
        map[(cyc )/40][(cyc)%40]=' '
    }
}

fun part1(content : List<String>) {
    var X = 0
    var cycle = 0

    for (line in content) {
        checkCycle(cycle, X)
        when (line) {
            "noop" -> {
                cycle++
            }

            else -> {
                var (instruction, value) = line.split(" ")

                when (instruction) {
                    "addx" -> {
                        var nb = value.toInt()
                        cycle++
                        checkCycle(cycle, X)
                        cycle++
                        X += nb
                    }
                }
            }
        }


    }

    for (line in map) {
        for (cell in line) {
            if (cell == 'x') {
                print("██")
            } else {
                print ("  ")
            }
        }
        println()
    }
}


fun main() {
    part1(readInput("d10/input"))
}

