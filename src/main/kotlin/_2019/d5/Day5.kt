package _2019.d5

import _2019.IntCode
import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

class Day5(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long = IntCode(input, 1).execute()
    override fun solve2(): Long = IntCode(input, 5).execute()
}

fun main() {
    //var day = Day5(readFullText("_2019/d5/test"))
    var day = Day5(readFullText("_2019/d5/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day5(readFullText("_2019/d5/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}