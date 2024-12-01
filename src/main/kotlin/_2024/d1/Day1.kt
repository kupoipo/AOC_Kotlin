package _2024.d1

import util.Day
import util.allLong
import util.readFullText
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day1(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val pairs = input.split("\n").map { line -> line.allLong() }

    private val first = pairs.map { it.first() }
    private val second = pairs.map { it.last() }

    override fun solve1(): Long = first.indices.sumOf { i -> abs(first[i] - second[i]) }

    override fun solve2(): Long = first.sumOf { s -> second.count { it == s } * s }
}

fun main() {
    val day = Day1(false, readFullText("_2024/d1/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day1(true, readFullText("_2024/d1/test$i"))
            println("Test $i :")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}