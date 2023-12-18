package _2021.d5

import util.*
import java.lang.Math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis
class Day5(override val input : String) : Day<Int>(input) {
    private val lines = input.split("\n").map{
        val points = it.allInts()
        if ((points[0] == points[2] && points[1] != points[3]) || (points[0] != points[2] && points[1] == points[3]))
            Point(x = points[0], y = points[1]) to Point(x = points[2], y = points[3])
        else
            null
    }.filterNotNull().toMutableSet()

    private val diag = input.split("\n").map{
        val points = it.allInts()
        Point(x = points[0], y = points[1]) to Point(x = points[2], y = points[3])
    }.toMutableSet() - lines

    private val xMin = lines.minOf { min(it.first.x, it.second.x ) }.toInt()
    private val xMax = lines.maxOf { max(it.first.x, it.second.x ) }.toInt()
    private val yMin = lines.minOf { min(it.first.y, it.second.y ) }.toInt()
    private val yMax = lines.maxOf { max(it.first.y, it.second.y ) }.toInt()

    var map : Matrix<Int> = matrixOf()

    override fun solve1(): Int {
        map = emptyMatrixOf(yMax+2, xMax+2, 0)

        lines.forEach {
            val x1 = it.first.x.toInt()
            val x2 = it.second.x.toInt()
            val y1 = it.first.y.toInt()
            val y2 = it.second.y.toInt()

            if (x1 != x2) {
                for (col in min(x1, x2) .. max(x1,x2)) {
                    map[y1][col] = map[y1][col] + 1
                }
            } else {
                for (lig in min(y1, y2) .. max(y1,y2)) {
                    map[lig][x1] = map[lig][x1] + 1
                }
            }
        }

        return map.sumOf { it.sumOf { if (it > 1) 1L else 0 } }.toInt()
    }
    override fun solve2(): Int {
        diag.forEach {
            var x1 = it.first.x.toInt()
            val x2 = it.second.x.toInt()
            var y1 = it.first.y.toInt()
            val y2 = it.second.y.toInt()

            val dx = if (x2-x1 > 0) 1 else -1
            val dy = if (y2-y1 > 0) 1 else -1

            for (i in 0..abs(y2-y1)) {
                map[y1][x1] = map[y1][x1] + 1
                x1 += dx
                y1 += dy
            }
        }

        return map.sumOf { it.sumOf { if (it > 1) 1L else 0 } }.toInt()
    }
}

fun main() {
    //var day = Day5(readFullText("_2021/d5/test"))
    var day = Day5(readFullText("_2021/d5/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}