package _2015.d24

import util.Day
import util.allArrangement
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day24(override val input: String) : Day<Long>(input) {
    private val packages = input.allInts().map { it.toLong() }
    private var sizeGroup: Long = 0

    fun lowestQuantum(): Long =
        packages.takeLast(15).toMutableList().apply { this.addAll(packages.take(4)); println(this) }
            .allArrangement { it.sum() == sizeGroup }.minOf { it.reduce { i, j -> i * j } }.toLong()

    override fun solve1(): Long {
        sizeGroup = packages.sum() / 3
        return lowestQuantum()
    }

    override fun solve2(): Long {
        sizeGroup = packages.sum() / 4
        return lowestQuantum()
    }
}

fun main() {
    val day = Day24(readFullText("_2015/d24/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day24(readFullText("_2015/d24/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}