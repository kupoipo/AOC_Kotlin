package _2015.d2

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureTimeMillis

// l x w x h
class Day2(override val input : String) : Day<Long>(input) {
    override fun solve1(): Long {
        var totalWrappingPaper = 0

        input.split("\n").forEach { line ->
            var l : Int; var h : Int; var w : Int
            line.allInts().let {
                l = it[0]
                w = it[1]
                h = it[2]
            }

            totalWrappingPaper += 2 * l * w
            totalWrappingPaper += 2 * h * w
            totalWrappingPaper += 2 * h * l

            line.allInts().sorted().let {
                totalWrappingPaper += it[0] * it[1]
            }

        }

        return totalWrappingPaper.toLong()
    }
    override fun solve2(): Long {
        var totalWrappingPaper = 0

        input.split("\n").forEach { line ->
            line.allInts().sorted().let {
                totalWrappingPaper += it[0] * it[1] * it[2]
                totalWrappingPaper += it[0] * 2 + 2 * it[1]
            }

        }

        return totalWrappingPaper.toLong()
    }
}

fun main() {
    //var day = Day2(readFullText("_2015/d2/test"))
    var day = Day2(readFullText("_2015/d2/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day2(readFullText("_2015/d2/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}