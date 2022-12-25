package _2021.d7

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day7(override val input : String) : Day<Int>(input) {
    override fun solve1(): Int {
        return -1
    }
    override fun solve2(): Int {
        return -1
    }
}

fun main() {
    //var day = Day1(readFullText("_2021.d7/test"))
    var day = Day1(readFullText("_2021.d7/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}