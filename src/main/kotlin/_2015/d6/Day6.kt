package _2015.d6

import util.*
import kotlin.system.measureTimeMillis

class Day6(override val input: String) : Day<Long>(input) {
    val lights = matrixOf(MutableList(1000) { MutableList(1000) { 0 } })

    fun playLine(line: String) {
        var origin: Point
        var end: Point

        line.allInts().let {
            origin = Point(it[0], it[1])
            end = Point(it[2], it[3])
        }

        for (lig in origin.x..end.x) {
            for (col in origin.y..end.y) {
                var lig = lig.toInt()
                var col = col.toInt()
                if (line.startsWith("toggle")) {
                    lights[lig][col] += 2
                } else if (line.startsWith("turn off")) {
                    lights[lig][col] = if (lights[lig][col] - 1 < 0) 0 else lights[lig][col] - 1
                } else {
                    lights[lig][col] += 1
                }
            }
        }
    }

    override fun solve1(): Long {
        input.split("\n").forEach {
            playLine(it)
        }

        return lights.sumOf { it.sum() }.toLong()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day6(readFullText("_2015/d6/test"))
    var day = Day6(readFullText("_2015/d6/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day6(readFullText("_2015/d6/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}