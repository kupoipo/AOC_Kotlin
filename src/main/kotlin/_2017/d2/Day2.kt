package _2017.d2

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime
class Day2(override val input : String) : Day<Long>(input) {
    override fun solve1(): Long = input.split("\n").sumOf { it.allInts().max() - it.allInts().min() }.toLong()
    override fun solve2(): Long = input.split("\n").map { it.allInts() }.sumOf {
        for (i in it) {
            for (j in it) {
                if (i != j && i%j == 0)
                    return@sumOf i/j
            }
        }
        0
    }.toLong()
}

fun main() {
    val day = Day2(readFullText("_2017/d2/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day2(readFullText("_2017/d2/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}