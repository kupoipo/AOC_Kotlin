package _2019.d19

import _2019.IntCode
import util.*
import kotlin.system.measureNanoTime

class Day19(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map = emptyMatrixOf(50, 50, 0L)
    private val data = mutableMapOf<Point, Long>()

    fun getPoint(p: Point): Long = data.getOrPut(p) { IntCode(input, p.x, p.y).execute(true) }


    init {
        for (y in 0 until 50) {
            for (x in 0 until 50) {
                IntCode(input, x.toLong(), y.toLong()).execute(true).let {
                    data[Point(x, y)] = it
                    map[y][x] = it
                }
            }
        }

        showMap(map, 1) { if (it == 0L) "." else "X" }
    }

    override fun solve1(): Long = map.numberOf(1L).toLong()

    fun insideBeam(x: Int, y: Int, size: Int): Boolean {
        for (dy in y..y + size) {
            for (dx in x..x + size) {
                if (getPoint(Point(dx, dy)) == 0L)
                return false
            }
        }
        return true
    }

    override fun solve2(): Long {
        var minX = 0
        for (y in 100..10000) {
            while (IntCode(input, minX.toLong(), y.toLong()).execute(true) == 0L) {
                minX++
            }

            var x = minX
            while (getPoint(Point(x, y)) != 0L) {
                if(insideBeam(x, y, 99)) return (x * 10000 + y).toLong()
                x++
            }
        }

        return -1
    }
}

fun main() {
    val day = Day19(false, readFullText("_2019/d19/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day19(true, readFullText("_2019/d19/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}