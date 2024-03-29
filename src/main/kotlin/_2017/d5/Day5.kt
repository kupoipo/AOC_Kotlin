package _2017.d5

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day5(override val input: String) : Day<Long>(input) {
    var instructions = input.split("\n").map { it.toInt() }.toMutableList()
    override fun solve1(): Long {
        var index = 0
        var step = 0L

        while (index < instructions.size) {
            instructions[index]++

            index += instructions[index] - 1

            step++
        }

        return step
    }

    override fun solve2(): Long {
        instructions = input.split("\n").map { it.toInt() }.toMutableList()
        var index = 0
        var step = 0L

        while (index < instructions.size) {
            val lastIndex = instructions[index]

            if (instructions[index] > 2) instructions[index]--
            else instructions[index]++

            index += lastIndex

            step++
        }

        return step
    }
}

fun main() {
    val day = Day5(readFullText("_2017/d5/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day5(readFullText("_2017/d5/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}