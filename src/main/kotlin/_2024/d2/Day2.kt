package _2024.d2

import util.Day
import util.allInts
import util.readFullText
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day2(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val lines = input.split("\n").map { it.allInts() }

    private fun isSafe(report: List<Int>): Boolean {
        val isAscendant = report[1] - report[0] > 0
        return (1..report.lastIndex).map { report[it] - report[it - 1] }.all {
            abs(it) in 1..3 && if (isAscendant) it > 0 else it < 0
        }
    }

    private fun isSafeV2(report: List<Int>): Boolean =
        (isSafe(report)) || (0..report.lastIndex).any { i -> isSafe(report.toMutableList().apply { this.removeAt(i) }) }


    override fun solve1(): Long = lines.count { report -> isSafe(report) }.toLong()
    override fun solve2(): Long = lines.count { report -> isSafeV2(report) }.toLong()
}

fun main() {
    val day = Day2(false, readFullText("_2024/d2/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day2(true, readFullText("_2024/d2/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}