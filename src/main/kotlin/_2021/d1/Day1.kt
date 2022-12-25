package _2021.d1

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day1(override val input : String) : Day<Int>(input) {
    val numbers = input.split("\n").map { it.toInt() }

    override fun solve1(): Int {
        return numbers.dropLast(1).indices.count{ numbers[it] < numbers[it+1]}
    }
    override fun solve2(): Int {
        return numbers.dropLast(3).indices.count{ numbers.subList(it, it+3).sum() < numbers.subList(it+1, it+4).sum() }
    }
}

fun main() {
    //var day = Day1(readFullText("_2021.d1/test"))
    var day = Day1(readFullText("_2021/d1/test"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}