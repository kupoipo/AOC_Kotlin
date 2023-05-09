package _2015.d1

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

class Day1(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long {
        var floor = 0L

        input.forEach { floor += if (it == ')') -1 else 1 }

        return floor
    }

    override fun solve2(): Long {
        var floor = 0L

        input.forEachIndexed { index, it ->
            floor += if (it == ')') -1 else 1
            if (floor == -1L)
                return index.toLong() + 1
        }

        return floor
    }
}

fun main() {
    var day = Day1(readFullText("_2015/d1/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    var dayTest = Day1(readFullText("_2015/d1/test"))

    println()
    println()

    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")


}