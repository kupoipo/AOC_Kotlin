package _2021.d16

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day16(override val input : String) : Day<Long>(input) {
    override fun solve1(): Long {
        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day16(readFullText("_2021/d16/test"))
    var day = Day16(readFullText("_2021/d16/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}