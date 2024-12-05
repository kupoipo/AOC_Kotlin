package _2024.d5

import util.*
import kotlin.system.measureNanoTime

class Day5(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val rules = input.split("\n\n").first().split("\n").map { it.allLong() }.let { pairs ->
        mutableMapOf<Long, MutableSet<Long>>().apply {
            for (pair in pairs) {
                this.getOrPut(pair.first()) { mutableSetOf() }.add(pair.last())
            }
        }
    }

    private val updates = input.split("\n\n").last().split("\n").map { it.allLong() }

    private fun isCorrect(update: List<Long>) = update.indices.all { i ->
        val number = update[update.lastIndex - i]
        val before = update.take(update.lastIndex - i)

        before.all { n -> rules[number] == null || n !in rules[number]!! }
    }

    private fun middle(update: List<Long>) = update[update.size / 2]

    private fun getPermute(update: List<Long>): Pair<Int, Int> {
        for (i in update.indices) {
            for (j in (i + 1 until update.size)) {
                val n = update[i]
                val n2 = update[j]
                if (rules[n2] != null && n in rules[n2]!!) {
                    return i to j
                }
            }
        }
        throw Exception("Shouldn't appear")
    }

    private fun correct(update: MutableList<Long>): List<Long> {
        while (!isCorrect(update)) {
            getPermute(update).let {
                update.swap(it.first, it.second)
            }
        }

        return update
    }

    override fun solve1(): Long = updates.filter(::isCorrect).sumOf(::middle)
    override fun solve2(): Long = updates.filterNot(::isCorrect).map(::correct).sumOf(::middle)
}

fun main() {
    val day = Day5(false, readFullText("_2024/d5/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day5(true, readFullText("_2024/d5/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}