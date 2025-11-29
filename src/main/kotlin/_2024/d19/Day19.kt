package _2024.d19

import _2021.d18.split
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day19(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val towels = input.replace(" ", "").split("\n").first().split(",").sorted()
    private val towelsToMake = input.split("\n").drop(2)
    private val impossible = mutableSetOf<String>()
    private fun isPossible(current: String) : Boolean {
        if (current.isEmpty()) {
            return true
        }
        if (impossible.contains(current)) return false

        for (towel in towels) {
            if (current.startsWith(towel)) {
                if (isPossible(current.drop(towel.length))) {
                    return true
                }
            }
        }

        impossible.add(current)

        return false
    }

    override fun solve1(): Long = towelsToMake.filter{  isPossible(it) } .size.toLong()
    override fun solve2(): Long {
        return -1L
    }
}

fun main() {
    val day = Day19(false, readFullText("_2024/d19/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day19(true, readFullText("_2024/d19/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}