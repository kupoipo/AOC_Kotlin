package _2025.d3

import _2021.d18.split
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day3(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val banks = input.split('\n')
    private val joltages = mutableMapOf<String, MutableList<MutableList<Int>>>()

    init {
        input.split("\n").forEach { bank ->
            val positions = MutableList<MutableList<Int>>(10) { mutableListOf() }

            bank.forEachIndexed { index, c -> positions[c.digitToInt()].add(index) }

            joltages[bank] = positions
        }
    }

    private fun biggestJoltageRecursive(
        positions: MutableList<MutableList<Int>>,
        currentIndex: Int,
        currentNumber: String,
        joltage: Int
    ): Long {
        if (currentNumber.length == joltage) return currentNumber.toLong()

        for (number in 9 downTo 0) {
            val currentPositions = positions[number]

            for (position in currentPositions) {
                if (position > currentIndex) {
                    val res = biggestJoltageRecursive(positions, position, currentNumber + number.toString(), joltage)
                    if (res != -1L) return res
                }
            }
        }

        return -1
    }

    private fun biggestJoltage(bank: String, joltage: Int): Long =
        biggestJoltageRecursive(joltages[bank]!!, -1, "", joltage)

    override fun solve1(): Long = banks.sumOf { biggestJoltage(it, 2) }

    override fun solve2(): Long = banks.sumOf { biggestJoltage(it, 12) }
}

fun main() {
    val day = Day3(false, readFullText("_2025/d3/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day3(true, readFullText("_2025/d3/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}