package _2018.d1

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day1(override val input : String) : Day<Long>(input) {
    override fun solve1(): Long {
        return input.split("\n").fold(0) { it, value -> it + value.toLong() }
    }
    override fun solve2(): Long {
        val instructions = input.split("\n").map { it.toLong() }
        val set = mutableSetOf<Long>()
        var current = 0L
        var currentIndex = 0
        while (true) {
            current += instructions[currentIndex]
            currentIndex = (currentIndex + 1) % instructions.size
            if (set.contains(current)) {
                return current
            }
            set.add(current)
        }
    }
}

fun main() {
    val day = Day1(readFullText("_2018/d1/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day1(readFullText("_2018/d1/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}