package _2016.d25

import util.Day
import util.isInt
import util.readFullText
import java.lang.Exception
import kotlin.system.measureNanoTime

class Day25(override val input: String) : Day<Long>(input) {
    private fun run(a: Int): String {
        var output = ""
        var a = a
        val d = a + 643 * 4

        while (true) {
            a = d

            do {
                var c = 2 - (a % 2)
                a /= 2

                var b = 2 - c

                output += b

                if (output.length > 10) {
                    return output
                }
            } while (a != 0)
        }

    }

    override fun solve1(): Long {
        for (i in 0..2000) {

            val repr = run(i)

            if (repr.matches(Regex("""(\d)(\d)(\1\2){4}\1"""))) {
                return i.toLong()
            }
        }

        return -1
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day25(readFullText("_2016/d25/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day25(readFullText("_2016/d25/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}