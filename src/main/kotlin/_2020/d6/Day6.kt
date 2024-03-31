package _2020.d6

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day6(override val input: String) : Day<Long>(input) {
    private val questions = input.split("\n\n").map { line ->
        line.groupingBy { it }.eachCount()
    }

    override fun solve1(): Long = questions.sumOf { it.size - (if (it.keys.contains('\n')) 1 else 0) }.toLong()

    override fun solve2(): Long = questions.sumOf { group ->
        group.count {
            it.value == (group['\n'] ?: 0) + 1
        }.toLong()
    }
}

fun main() {
    val day = Day6(readFullText("_2020/d6/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day6(readFullText("_2020/d6/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}