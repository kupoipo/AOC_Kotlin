package _2021.d17

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day17(override val input : String) : Day<Int>(input) {
    val xRange = input.substringAfter("x=").substringBefore(",").split("..").let { it[0].toInt()..it[1].toInt() }
    val yRange = input.substringAfter("y=").split("..").let { it[0].toInt()..it[1].toInt() }

    fun simulate(dx : Int, dy : Int) : Int {
        if (dx <= 0) return -1

        var dx = dx; var dy = dy
        var yMax = 0
        var x = 0; var y = 0

        while (true) {
            x += dx
            y += dy

            if (y > yMax) yMax = y

            if (x > xRange.last || y < yRange.first) return -1
            if (x in xRange && y in yRange) return yMax

            if (dx > 0) dx -= 1
            dy -= 1
        }
    }

    override fun solve1(): Int {
        return (0..xRange.first/2).maxOf { x -> (0..1500).maxOf { y -> simulate(x, y) } }
    }
    override fun solve2(): Int {
        return (0..xRange.last).sumOf { x -> (yRange.first ..1500).count { y -> simulate(x, y) != -1} }
    }
}

fun main() {
    //var day = Day17(readFullText("_2021/d17/test"))
    var day = Day17(readFullText("_2021/d17/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}