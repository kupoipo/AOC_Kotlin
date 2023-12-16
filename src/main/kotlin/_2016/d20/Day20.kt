package _2016.d20

import util.*
import kotlin.system.measureNanoTime

class Day20(override val input: String) : Day<Long>(input) {
    val ranges  = input.split("\n").map { it.split("-").let { (f, l) -> f.toLong() ..l.toLong() } }
    var authorized = mutableListOf(0..4294967295L)

    fun filterRange(authorized: List<LongRange>, forbiddens: LongRange): MutableList<LongRange> {
        val res = mutableListOf<LongRange>()

        for (range in authorized) {
            if (range.contains(forbiddens)) {
                res.add(range.first until forbiddens.first)
                res.add(forbiddens.last + 1 .. range.last )
            } else if (range.isOverlapping(forbiddens)) {
                if (forbiddens.first > range.first) {
                    res.add(range.first until forbiddens.first())
                } else {
                    res.add(forbiddens.last + 1 .. range.last())
                }
            } else {
                res.add(range)
            }
        }

        return res.filter { it.last >= it.first }.toMutableList()
    }

    override fun solve1(): Long  = ranges.fold(authorized) { acc, range -> filterRange(acc, range) }.minBy { it.first }.first

    override fun solve2(): Long = ranges.fold(authorized) { acc, range -> filterRange(acc, range) }.sumOf { it.last - it.first + 1 }
}

fun main() {
    val day = Day20(readFullText("_2016/d20/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day20(readFullText("_2016/d20/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}