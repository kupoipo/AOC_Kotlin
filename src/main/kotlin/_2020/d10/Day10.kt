package _2020.d10

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day10(override val input: String) : Day<Long>(input) {
    private val jolts = input.split("\n").map(String::toInt).sorted().toMutableList()
        .apply { this.add(0, 0); this.add(this.last() + 3) }
    private val countJolts = mutableMapOf<Int, Long>(0 to 1)

    override fun solve1(): Long {
        return (1..jolts.lastIndex).let { joltsIndex ->
            joltsIndex.count { jolts[it] - jolts[it - 1] == 1 } * (joltsIndex.count { jolts[it] - jolts[it - 1] == 3 })
        }.toLong()
    }

    override fun solve2(): Long {
        for (i in jolts.drop(1)) {
            countJolts[i] =
                countJolts.getOrPut(i - 3) { 0L } + countJolts.getOrPut(i - 2) { 0L } + countJolts.getOrPut(i - 1) { 0L }
        }

        return countJolts[jolts.last()]!!.toLong()
    }
}

fun main() {
    val day = Day10(readFullText("_2020/d10/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day10(readFullText("_2020/d10/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}