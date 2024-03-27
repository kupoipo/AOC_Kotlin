package _2017.d4

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day4(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long = input.split("\n").count { it.split(" ").size == it.split(" ").toSet().size }.toLong()
    override fun solve2(): Long = input.split("\n").map { it.split(" ") }.count {
        for (i in it.indices) {
            for (j in i + 1..it.lastIndex) {
                if (it[i].toSet() == it[j].toSet()) {
                    return@count false
                }
            }
        }
        true
    }.toLong()
}


fun main() {
    val day = Day4(readFullText("_2017/d4/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day4(readFullText("_2017/d4/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}