package _2020.d5

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day5(override val input: String) : Day<Long>(input) {
    private val seats = input.split("\n").map(::seatFromLine).map { it.first * 8 + it.second }

    private fun seatFromLine(line: String): Pair<Long, Long> {
        val line = line.replace(Regex("F|L"), "0").replace(Regex("B|R"), "1")
        val row = line.dropLast(3)
        val col = line.takeLast(3)
        return row.toLong(2) to col.toLong(2)

    }

    override fun solve1(): Long = seats.max()

    override fun solve2(): Long = (seats.min()..(128*8L)).first { it !in seats }

}

fun main() {
    val day = Day5(readFullText("_2020/d5/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day5(readFullText("_2020/d5/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}