package _2021.d10

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day10(override val input : String) : Day<Int>(input) {
    override fun solve1(): Int {
        return -1
    }
    override fun solve2(): Int {
        return -1
    }
}

fun main() {
    //var day = Day10(readFullText("_2021/d10/test"))
    var day = Day10(readFullText("_2021/d10/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}