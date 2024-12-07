package _2024.d7

import util.Day
import util.allLong
import util.readFullText
import kotlin.system.measureNanoTime

class Day7(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val data = input.split("\n").map { it.allLong() }.map { it.first() to it.drop(1) }

    private fun isCorrectRec(
        target: Long,
        current: Long,
        available: List<Long>,
        operators: List<(Long, Long) -> Long>
    ): Boolean {
        if (current == target && available.isEmpty()) return true
        if (current > target || available.isEmpty()) return false

        return operators.any { operator ->
            isCorrectRec(target, operator(current, available.first()), available.drop(1), operators)
        }
    }

    private fun isCorrect(
        data: Pair<Long, List<Long>>,
        operators: List<(Long, Long) -> Long>
    ) = isCorrectRec(
        data.first,
        data.second.first(),
        data.second.drop(1),
        operators
    )

    override fun solve1(): Long = data.filter { isCorrect(it, listOf(Long::plus, Long::times)) }.sumOf { it.first }

    override fun solve2(): Long = data.filter { isCorrect(it, listOf(Long::plus, Long::times, { a, b -> "$a$b".toLong() })) }.sumOf { it.first }
}

fun main() {
    val day = Day7(false, readFullText("_2024/d7/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day7(true, readFullText("_2024/d7/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}