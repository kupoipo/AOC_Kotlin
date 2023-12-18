package _2023.d18

import util.*
import kotlin.system.measureNanoTime


class Day18(override val input: String) : Day<Long>(input) {
    fun getCubicMeters(parser: (String) -> Pair<Char, Int>): Long {
        val corners = mutableListOf<Point>()
        var lengthPolygon = 0L
        var start = Point(0, 0)

        corners.add(start)

        input.split("\n").forEach { line ->
            val (d, nb) = parser(line)

            start.moveInDirection(d, nb).let { end ->
                corners.add(end)
                lengthPolygon += start.manhattan(end)
                start = end
            }
        }

        return calculateGaussianSurface(corners) + 1 - lengthPolygon / 2 + lengthPolygon
    }

    override fun solve1() = getCubicMeters { line ->
        line.split(" ").let {
            return@getCubicMeters it.first().first() to it[1].toInt()
        }
    }

    override fun solve2(): Long {
        return getCubicMeters { line ->
            line.split(" ").let { (d, nb, color) ->
                val d = when (color.dropLast(1).last().digitToInt()) {
                    0 -> 'R'
                    1 -> 'D'
                    2 -> 'L'
                    else -> 'U'
                }

                return@getCubicMeters d to ("0" + color.drop(2).take(5)).toInt(16)
            }
        }
    }
}

fun main() {
    val day = Day18(readFullText("_2023/d18/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")
    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day18(readFullText("_2023/d18/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}