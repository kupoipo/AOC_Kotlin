package _2017.d13

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day13(override val input: String) : Day<Long>(input) {
    val scanner = MutableList(93) { 0 }

    init {
        input.split("\n").map { it.allInts() }.forEach { (index, depth) -> scanner[index] = depth }
    }

    fun isCaught(time: Int): Boolean {
        return scanner.filterIndexed { index, depth ->
            if (depth == 0) return@filterIndexed false

            return@filterIndexed (index + time) % ((depth - 1) * 2) == 0
        }.isNotEmpty()
    }

    override fun solve1(): Long = scanner.mapIndexed { index, depth ->
        if (depth != 0 && index % ((depth - 1) * 2) == 0) (index * depth).toLong()
        else 0L
    }.sum()

    override fun solve2(): Long = (1..1_000_000_000).first { !isCaught(it) }.toLong()

}

fun main() {
    val day = Day13(readFullText("_2017/d13/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day13(readFullText("_2017/d13/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}