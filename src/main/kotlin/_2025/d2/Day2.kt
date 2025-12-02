package _2025.d2

import util.Day
import util.allLong
import util.readFullText
import kotlin.system.measureNanoTime

class Day2(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val ranges = input.split(",").map(String::allLong).map { it.first()..it.last() * -1 }

    private fun countInvalid(range: LongRange, part2: Boolean = false) = range.filter { number ->
        val str = number.toString()
        val start = if (part2) 1 else (str.length / 2).coerceAtLeast(1)

        val chunks = (start..str.length / 2).map { str.chunked(it) }

        chunks.any { parts ->
            parts.all { it == parts.first() }
        }
    }

    /**
     * Slower than the first version but more elegant.
     *
     * @param regex Pattern to find
     */
    private fun countInvalidRegex(regex: String) =
        ranges.flatMap { range -> range.filter { number -> number.toString().matches(regex.toRegex()) } }.sum()

    override fun solve1(): Long = countInvalidRegex("""(\d+)\1""")

    override fun solve2(): Long = countInvalidRegex("""(\d+)(\1)+""")
}

fun main() {
    val day = Day2(false, readFullText("_2025/d2/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day2(true, readFullText("_2025/d2/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}