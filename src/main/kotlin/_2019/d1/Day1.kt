package _2019.d1

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day1(override val input : String) : Day<Long>(input) {

    private fun totalFuel(s: Int) : Int {
        val fuel = (s/3 - 2)
        if (fuel <= 0) return 0

        return fuel + totalFuel(fuel)
    }

    override fun solve1(): Long {
        return input.split("\n").map { s -> s.toInt()/3 - 2 }.sum().toLong()
    }

    override fun solve2(): Long {
        return input.split("\n").map { s -> totalFuel(s.toInt()) }.sum().toLong()
    }
}

fun main() {
    //var day = Day1(readFullText("_2019/d1/test"))
    val day = Day1(readFullText("_2019/d1/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day1(readFullText("_2019/d1/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}