package _2015.d15

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day15(override val input : String) : Day<Long>(input) {
    override fun solve1(): Long {
        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day15(readFullText("_2015/d15/test"))
    var day = Day15(readFullText("_2015/d15/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day15(readFullText("_2015/d15/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}