package _2025.d5

import util.*
import kotlin.system.measureNanoTime

class Day5(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val ranges =
        input.split("\n\n").first().split("\n").map { line -> line.allLong().let { it[0]..it[1] * -1 } }
    private val ingredients = input.split("\n\n").last().split("\n").map(String::toLong)

    override fun solve1(): Long = ingredients.count { ingredient -> ranges.any { it.contains(ingredient) } }.toLong()

    override fun solve2(): Long {
        val normalizedRanges = ranges.toMutableList()
        var i = 0
        while (i < normalizedRanges.lastIndex) {
            do {
                var change = false
                val currentRange = normalizedRanges[i]
                for (j in normalizedRanges.indices) {
                    if (i == j) continue

                    val otherRange = normalizedRanges[j]

                    if (currentRange.contains(otherRange)) {
                        change = true
                        normalizedRanges.removeAt(j)
                        break
                    }

                    if (currentRange.isOverlapping(otherRange)) {
                        change = true
                        normalizedRanges[i] = currentRange.merge(otherRange)
                        normalizedRanges.removeAt(j)
                        break
                    }
                }
            } while (change)
            i += 1
        }

        return normalizedRanges.sumOf(LongRange::size)
    }
}

fun main() {
    val day = Day5(false, readFullText("_2025/d5/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day5(true, readFullText("_2025/d5/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}