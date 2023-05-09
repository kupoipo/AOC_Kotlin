package _2015.d3

import util.Day
import util.Point
import util.readFullText
import kotlin.system.measureTimeMillis

class Day3(override val input: String) : Day<Long>(input) {
    var position = Point(0, 0)
    var positionRobot = Point(0, 0)
    var positions = mutableSetOf<Point>()

    override fun solve1(): Long {
        position = Point(0, 0)
        positions = mutableSetOf<Point>()

        positions.add(position)

        input.forEach {
            position += move(it)

            positions.add(position)
        }

        return positions.size.toLong()
    }


    override fun solve2(): Long {
        position = Point(0, 0)
        positions = mutableSetOf()

        positions.add(position)

        for (i in input.indices step 2) {
            val santaMove = input[i]
            val robotMove = input[i + 1]

            position += move(santaMove)
            positionRobot += move(robotMove)

            positions.add(position)
            positions.add(positionRobot)
        }

        return positions.size.toLong()
    }

    fun move(dir: Char): Point = when (dir) {
        '<' -> Point(-1, 0)
        '>' -> Point(1, 0)
        'v' -> Point(0, 1)
        '^' -> Point(0, -1)
        else -> {
            Point(0, 0)
        }
    }
}

fun main() {
    //var day = Day3(readFullText("_2015/d3/test"))
    var day = Day3(readFullText("_2015/d3/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day3(readFullText("_2015/d3/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}