package _2021.d13

import util.*
import kotlin.system.measureTimeMillis
class Day13(override val input : String) : Day<Int>(input) {
    val lines = input.split("\n")
    var points = mutableSetOf<Point>()
    val instruction = mutableListOf<Pair<Char, Int>>()
    var map : Matrix<Char>

    init {
        lines.forEach {line ->
            if (line.isEmpty()) return@forEach

            if (line.startsWith("fold")) {
                instruction.add(line[line.indexOf("=") - 1] to line.substringAfter("=").toInt())
            } else {
                line.allInts().let { points.add(Point(it[0], it[1])) }
            }
        }
        map = emptyMatrixOf((points.maxOf { it.y } + 1).toInt(), (points.maxOf { it.x } + 1).toInt(), ' ')
        points.forEach { map[it] = '#' }
    }

    fun fold(inst : Char, index : Int) {
        val newPoints = mutableSetOf<Point>()

        if (inst == 'y') {
            points.forEach {
                if (it.y <= index) {
                    newPoints.add(it)
                } else {
                    newPoints.add(Point(it.x, index - (it.y - index)))
                }
            }
        } else {
            points.forEach {
                if (it.x <= index) {
                    newPoints.add(it)
                } else {
                    newPoints.add(Point(index - (it.x - index), it.y))
                }
            }
        }

        points = newPoints
    }
    fun affiche() {
        map = emptyMatrixOf((points.maxOf { it.y } + 1).toInt(), (points.maxOf { it.x } + 1).toInt(), ' ')
        points.forEach { map[it] = '#' }

        showMap(map)
    }


    override fun solve1(): Int {
        fold(instruction[0].first, instruction[0].second)
        return points.size
    }
    override fun solve2(): Int {
        instruction.drop(1).forEach {
            fold(it.first, it.second)
        }

        affiche()

        return points.size
    }
}

fun main() {
    //var day = Day13(readFullText("_2021/d13/test"))
    var day = Day13(readFullText("_2021/d13/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}