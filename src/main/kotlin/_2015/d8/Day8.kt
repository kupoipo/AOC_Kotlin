package _2015.d8

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

class Day8(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long = input.split("\n").sumOf {
        val nbLiteral = it.length
        val double = Regex("""\\\\""").findAll(it).toList().size
        val it = it.replace("\\\\", "")
        val x = Regex("""\\x""").findAll(it).toList().size
        val slash = it.count { it == '\\' }
        val nbChar = it.length - (x * 3 + (slash - x) + 2) + double

        nbLiteral - nbChar

    }.toLong()

    override fun solve2(): Long =
        input.split("\n").sumOf { it.count { c -> c == '\\' } + it.count { c -> c == '\"' } + 2 }.toLong()
}

fun main() {
    //var day = Day8(readFullText("_2015/d8/test"))
    var day = Day8(readFullText("_2015/d8/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day8(readFullText("_2015/d8/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}