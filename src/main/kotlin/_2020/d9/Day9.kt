package _2020.d9

import util.Day
import util.combination
import util.readFullText
import java.util.Collections.max
import java.util.Collections.min
import kotlin.system.measureNanoTime

class Day9(override val input: String, private val preamble: Int) : Day<Long>(input) {
    private var partOne: Long = 0L
    private val numbers = input.split("\n").map(String::toLong)
    override fun solve1(): Long = (preamble..numbers.lastIndex).first { i ->
        numbers.subList(i - preamble, i).combination(2).all { it.sum() != numbers[i] }
    }.let { partOne = numbers[it]; numbers[it] }


    override fun solve2(): Long {
        for (i in numbers.indices) {
            var sum = 0L

            for (j in i..numbers.lastIndex) {
                sum += numbers[j]

                if (sum == partOne) {
                    return (numbers.subList(i, j)).let {
                        min(it) + max(it)
                    }
                }
                if (sum > partOne) break
            }
        }

        return -1
    }
}

fun main() {
    val day = Day9(readFullText("_2020/d9/input"), 25)
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day9(readFullText("_2020/d9/test"), 5)
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}