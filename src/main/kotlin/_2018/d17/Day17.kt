package _2018.d17

import util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

class Day17(override val input: String) : Day<Long>(input) {
    private var map = emptyMatrixOf(0, 0, '.')
    private var minX: Int = Int.MAX_VALUE
    private var maxX: Int = Int.MIN_VALUE
    private var minY: Int = Int.MAX_VALUE
    private var maxY: Int = Int.MIN_VALUE
    private var columns: Int = 0
    private var lines: Int = 0
    private var waterFall = Point(500, 0)

    init {
        var x1: Int
        var x2: Int
        var y1: Int
        var y2: Int

        val tempMap = emptyMatrixOf(2050, 2050, '.')

        input.split("\n").forEach { line ->
            line.allInts().let {
                if (line.first() == 'x') {
                    x1 = it.first()
                    x2 = it.first()

                    y1 = it[1]
                    y2 = it.last()
                } else {
                    y1 = it.first()
                    y2 = it.first()

                    x1 = it[1]
                    x2 = it.last()
                }
            }

            minX = min(minX, x1)
            maxX = max(maxX, x2)
            minY = min(minY, y1)
            maxY = max(maxY, y2)

            for (y in y1..y2) {
                for (x in x1..x2) {
                    tempMap[y][x] = '#'
                }
            }
        }

        columns = maxX - minX

        waterFall.x -= minX - 1
        map = emptyMatrixOf(maxY + 1, columns + 3, '.')

        for (y in 0..maxY) {
            for (x in minX..maxX) {
                map[y][x - minX + 1] = tempMap[y][x]
            }
        }

        map[0][waterFall.x] = '+'

        map = map.take(100).toMutableList()
        maxY = 100
    }

    private fun expand(from: Point, direction: Direction): Pair<Point, Boolean> {
        var expand = from + direction

        if (expand.outOfMap(map)) return expand to true

        while (map[expand] in ".~" && (expand + Direction.DOWN).inMap(map) && map[expand + Direction.DOWN] != '.') {
            map[expand] = '~'
            expand += direction
            if (expand.outOfMap(map)) return expand to true
        }

        return if (map[expand] != '.') {
            expand to true
        } else {
            expand to false
        }
    }

    private fun fall(from: Point) {
        if (from.outOfMap(map)) return

        if (map[from] == '~') return
        map[from] = '~'
            println("fall from $from")
            showMap(map, 1)
            println(countWater())
        var nextWaterfallPos = from + Direction.DOWN
        var stop = false


        while (nextWaterfallPos.inMap(map) && map[nextWaterfallPos] == '.') {
            map[nextWaterfallPos] = '~'
            nextWaterfallPos += Direction.DOWN
        }


        nextWaterfallPos += Direction.UP

        while (!stop && nextWaterfallPos.inMap(map)) {
            map[nextWaterfallPos] = '~'
            val left: Pair<Point, Boolean> = expand(nextWaterfallPos, Direction.LEFT)
            val right: Pair<Point, Boolean> = expand(nextWaterfallPos, Direction.RIGHT)

            if (left.second && right.second) {
                nextWaterfallPos += Direction.UP

            } else {
                if (!left.second && (left.first + Direction.DOWN).inMap(map)) {
                    fall(left.first)
                }

                if (!right.second && (right.first + Direction.DOWN).inMap(map)) {
                    fall(right.first)
                }
                stop = true

            }
        }
    }

    fun countWater() : Long {
        var nbWater = 0L
        for (y in minY..map.lastIndex) {
            for (x in 0..map[0].lastIndex) {
                if (map[y][x] == '~') nbWater++
            }
        }

        return nbWater
    }

    override fun solve1(): Long {
        fall(waterFall)

        showMap(map, 1)
        return countWater()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day17(readFullText("_2018/d17/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day17(readFullText("_2018/d17/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}