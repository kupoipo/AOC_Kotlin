package _2020.d13

import util.*
import kotlin.system.measureNanoTime

class Day13(override val input: String) : Day<Long>(input) {
    private val departure = input.firstInt().toLong()
    private val bus = input.allLong().drop(1)

    override fun solve1(): Long = bus.minBy { it - departure % it }.let { it * (it - departure % it) }

    override fun solve2(): Long {
        val bus = input.split("\n").last().split(",").mapIndexed { index, s -> s to index }.filter { it.first != "x" }
            .map { it.first.toLong() to it.second }

        var res = 0L
        var step = 1L

        for (b in bus) {
            while ((res + b.second) % b.first != 0L) {
                res += step
            }

            step *= b.first
        }

        return res
    }
}

fun main() {
    val day = Day13(readFullText("_2020/d13/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day13(readFullText("_2020/d13/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}