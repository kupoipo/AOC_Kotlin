package _2017.d11

import util.Day
import util.Point
import util.readFullText
import kotlin.math.abs
import kotlin.math.min
import kotlin.system.measureNanoTime
class Day11(override val input : String) : Day<Long>(input) {
    var x = 0
    var y = 0

    fun step(dir: String) {
        when(dir) {
            "nw" -> {
                x--
                y--
            }
            "n" -> y--
            "sw" -> x--
            "ne" -> x++
            "s" -> y++
            else -> {
                x++
                y++
            }
        }
    }
    override fun solve1(): Long {
        input.split(",").forEach {
            step(it)
        }

        return Point(x, y).manhattan(Point(0,0))
    }
    override fun solve2(): Long {
        x = 0; y = 0
        var max = 0L

        input.split(",").forEach { step ->
            step(step)

            Point(x, y).manhattan(Point(0,0)).let { dist ->
                if (dist > max)
                    max = dist
            }
        }

        return max
    }
}

fun main() {
    val day = Day11(readFullText("_2017/d11/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day11(readFullText("_2017/d11/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}