package _2019.d3

import _2022.d24.goal
import util.*
import kotlin.math.abs
import kotlin.system.measureTimeMillis

class Path(val index: Int, val range: IntRange, val dir: Char, val type: Char) {
    fun collideWith(p: Path): Point? {
        if (type == p.type) return null

        if (p.index in range && index in p.range) {
            return if (type == 'L') Point(p.index, index) else Point(index, p.index)
        }

        return null
    }
}

fun getPaths(input: String): List<Path> {
    var position = Point(0, 0)
    val paths = mutableListOf<Path>()

    input.split(",").forEach {
        val n: Int = it.substring(1).toInt()

        when (it[0]) {
            'R' -> {
                paths.add(Path(position.y, position.x..position.x + n, 'U', 'L'))
                position += Point(n, 0)
            }

            'L' -> {
                paths.add(Path(position.y, position.x - n..position.x, 'D', 'L'))
                position += Point(-n, 0)
            }

            'U' -> {
                paths.add(Path(position.x, position.y - n..position.y, 'U', 'C'))
                position += Point(0, -n)
            }

            else -> {
                paths.add(Path(position.x, position.y..position.y + n, 'D', 'C'))
                position += Point(0, n)
            }
        }
    }

    return paths
}

class Day3(override val input: String) : Day<Long>(input) {
    private val pathsFirstJourney: List<Path> = getPaths(input.split("\n")[0])
    private val pathsSecondJourney = getPaths(input.split("\n")[1])
    private val collisions = mutableSetOf<Point>()

    init {
        for (path in pathsSecondJourney) {
            for (firstPath in pathsFirstJourney) {
                val collision: Point? = path.collideWith(firstPath)

                if (collision != null) collisions.add(collision)

            }
        }

        collisions.remove(Point(0, 0))

    }

    override fun solve1(): Long = collisions.minOf { p -> p.manhattan(Point(0, 0)) }.toLong()

    private fun stepTo(goalPoint: Point, paths: List<Path>): Int {
        var currentPoint = Point(0, 0)
        var nbStep = 0

        for (p in paths) {
            if (p.type == 'L') {
                if (currentPoint.y == goalPoint.y) {
                    return nbStep + abs(currentPoint.x - goalPoint.x)
                }

                currentPoint += if (p.dir == 'U') {
                    Point(abs(p.range.first - p.range.last), 0)
                } else {
                    Point(-abs(p.range.first - p.range.last), 0)
                }

            } else {
                if (currentPoint.x == goalPoint.x) {
                    return nbStep + abs(currentPoint.y - goalPoint.y)
                }

                currentPoint += if (p.dir == 'U') {
                    Point(0, -abs(p.range.first - p.range.last))
                } else {
                    Point(0, abs(p.range.first - p.range.last))
                }
            }

            nbStep += p.range.last - p.range.first
        }

        return -1
    }

    override fun solve2(): Long {
        val steps = mutableSetOf<Int>()

        for (collision in collisions) {
            steps.add(stepTo(collision, pathsFirstJourney) + stepTo(collision, pathsSecondJourney))
        }

        return steps.min().toLong()
    }
}

fun main() {
    //var day = Day3(readFullText("_2019/d3/test"))
    var day = Day3(readFullText("_2019/d3/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day3(readFullText("_2019/d3/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}