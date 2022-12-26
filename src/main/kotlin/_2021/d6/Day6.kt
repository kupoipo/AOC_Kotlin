package _2021.d6

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day6(override val input : String) : Day<Long>(input) {
    val mapFishes = mutableMapOf<Int, Long>()

    private fun round() {
        (0..8).forEach { mapFishes[it - 1] = mapFishes[it] ?: 0}

        mapFishes[8] = mapFishes[-1]?: 0
        mapFishes[6] = mapFishes[6]!! + mapFishes[-1]!!
        mapFishes[-1] = 0
    }

    override fun solve1(): Long {
        (-1..8).forEach { mapFishes[it] = input.split(",").map { it.toInt() }.count{ fish -> it == fish}.toLong() }

        repeat (80) {  round() }

        return  mapFishes.values.sum()
    }
    override fun solve2(): Long {
        repeat(256) { round() }

        return mapFishes.values.sum()
    }
}

fun main() {
    //var day = Day6(readFullText("_2021/d6/test"))
    var day = Day6(readFullText("_2021/d6/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}