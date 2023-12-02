package _2023.d2

import util.Day
import util.listOfMatch
import util.readFullText
import kotlin.system.measureTimeMillis

val MAX_VALUE = mapOf("red" to 12, "green" to 13, "blue" to 14)
class Day2(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long =
        input.split("\n").map { Regex("""\d+ (red|blue|green)""").listOfMatch(it) }
            .mapIndexed { index, matches ->
                if (matches.all { el ->
                        val el = el.strip().split(" ")

                        MAX_VALUE[el.last()]!! >= el.first().toInt()
                    }) index + 1 else 0
            }.sum().toLong()

    override fun solve2(): Long =
        input.split("\n").map { Regex("""\d+ (red|blue|green)""").listOfMatch(it) }.sumOf { matches ->
            val localMax = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            matches.forEach { el ->
                val tab =  el.split(" ");
                val (nb, color) = tab.first().toInt() to tab.last()

                if (localMax[color]!! < nb)
                    localMax[color] = nb
            }

            localMax.values.reduce { i, j -> i * j }.toLong()
        }
}

fun main() {
    //var day = Day2(readFullText("_2023/d2/test"))
    var day = Day2(readFullText("_2023/d2/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day2(readFullText("_2023/d2/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}