package _2018.d20

import util.Day
import util.Direction
import util.Point
import util.readFullText
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

class Day20(override val input: String) : Day<Long>(input) {
    private val map = mutableMapOf<Point, Pair<Int, Int>>()
    private val regex = input.drop(1)
    private val positions = mutableListOf<Pair<Point, Int>>()

    init {
        var pos = Point(0, 0)
        var depth = 0

        positions.add(pos to depth)

        for (c in regex) {
            when (c) {
                'E', 'N', 'S', 'W' -> {
                    pos += Direction.fromChar(c)
                    depth++

                    if (map.containsKey(pos)) {
                        val lastData = map[pos]!!
                        min(lastData.first, depth) to max(lastData.second, depth)
                    } else {
                        map[pos] = depth to depth
                    }
                }

                '|' -> {
                    positions.last().let {
                        pos = it.first
                        depth = it.second
                    }
                }

                '(' -> positions.add(pos to depth)

                ')', '$' -> {
                    positions.removeLast().let {
                        pos = it.first
                        depth = it.second
                    }
                }

                else -> {
                    error("$c unknown.")
                }
            }
        }
    }

    override fun solve1(): Long = map.maxBy { it.value.second }.value.first.toLong()

    override fun solve2(): Long = map.count { it.value.first >= 1000 }.toLong()

}

fun main() {
    val day = Day20(readFullText("_2018/d20/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day20(readFullText("_2018/d20/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}