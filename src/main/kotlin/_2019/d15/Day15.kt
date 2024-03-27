package _2019.d15

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
    val day = Day15(readFullText("_2019/d15/input"))
    println("Temps partie 1 : {${measureTimeMillis { println("Part 1 : " + day.solve1()) }}}")
    println("Temps partie 2 : {${measureTimeMillis { println("Part 2 : " + day.solve2()) }}}")

    println()

    val dayTest = Day15(readFullText("_2019/d15/test"))
    println("Temps partie 1 : {${measureTimeMillis { println("Part 1 : " + dayTest.solve1()) }}}")
    println("Temps partie 2 : {${measureTimeMillis { println("Part 2 : " + dayTest.solve2()) }}}")
}