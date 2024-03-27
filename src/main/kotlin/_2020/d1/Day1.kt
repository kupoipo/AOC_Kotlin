package _2020.d1

import util.Day
import util.combination
import util.getPermutations
import util.readFullText
import kotlin.system.measureNanoTime

class Day1(override val input: String) : Day<Long>(input) {
    val data = input.split("\n").map(String::toLong)

    private fun findSum(sizeList: Int) = data.combination(sizeList).first { it.sum() == 2020L }.reduce { acc, l -> l * acc }

    override fun solve1(): Long = findSum(2)
    override fun solve2(): Long = findSum(3)
}

fun main() {
    val day = Day1(readFullText("_2020/d1/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day1(readFullText("_2020/d1/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}