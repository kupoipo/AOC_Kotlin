package _2016.d3

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day3(override val input: String) : Day<Long>(input) {
    fun isTriangle(triangle: List<Int>): Boolean {
        if (triangle[0] + triangle[1] <= triangle[2]) return false
        if (triangle[2] + triangle[0] <= triangle[1]) return false
        if (triangle[2] + triangle[1] <= triangle[0]) return false
        return true
    }

    override fun solve1(): Long = input.split("\n").map { it.allInts() }.count { isTriangle(it) }.toLong()
    override fun solve2(): Long = input.split("\n").map { it.allInts() }.windowed(3, 3).map { (l1, l2, l3) ->
        mutableListOf(
            mutableListOf(
                l1.first(), l2.first(), l3.first()
            ),
            mutableListOf(
                l1.last(), l2.last(), l3.last()
            ),
            mutableListOf(
                l1[1], l2[1], l3[1]
            )
        )
    }.sumOf { it.count { isTriangle(it) } }.toLong()
}

fun main() {
    val day = Day3(readFullText("_2016/d3/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day3(readFullText("_2016/d3/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}