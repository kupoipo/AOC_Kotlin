package _2017.d1

import util.Day
import util.allDigits
import util.readFullText
import kotlin.system.measureNanoTime

class Day1(override val input: String) : Day<Long>(input) {
    val digits = input.allDigits()
    override fun solve1(): Long = digits.mapIndexed { index, i -> if (digits[index] == digits[(index + 1)%digits.size]) i else 0 }.sum().toLong()
    override fun solve2(): Long = digits.mapIndexed { index, i -> if (digits[index] == digits[(index + digits.size/2)%digits.size]) i else 0 }.sum().toLong()
}

fun main() {
    val day = Day1(readFullText("_2017/d1/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day1(readFullText("_2017/d1/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}