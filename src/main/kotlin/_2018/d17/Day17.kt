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

    }

    private tailrec fun fall(from: Point, dir: Direction): Point? {
        if (map[from] == '.') {
            map[from] = '|'
        }

        if (from.y.toInt() == map.size - 1) return null
        if (map[from] == '#') return from

        if (map[from + Direction.DOWN] == '.') fall(from + Direction.DOWN, Direction.DOWN)
        if (map[from + Direction.DOWN] in "~#") {
            if (dir != Direction.DOWN)
                return fall(from + dir, dir)

            val left = fall(from + Direction.LEFT, Direction.LEFT)
            val right = fall(from + Direction.RIGHT, Direction.RIGHT)

            if (map[left!!] == '#' && map[right!!] == '#')
                for (x in left.x + 1 until right.x)
                    map[left.y][x] = '~'

        } else {
            return from
        }

        return null
    }

    fun count(toCount: Char): Long {
        var nbWater = 0L
        for (y in minY..map.lastIndex) {
            for (x in 0..map[0].lastIndex) {
                if (map[y][x] == toCount) nbWater++
            }
        }

        return nbWater
    }

    override fun solve1(): Long {
        fall(waterFall, Direction.DOWN)

        return count('~')
    }

    override fun solve2(): Long {
        return count('|')
    }

}

fun main(args: Array<String>) {
    val day = Day17(readFullText("_2018/d17/input"))

    val t1 = measureNanoTime { println("Part 2 : " + day.solve1()) }
    println("Temps partie 2 : ${t1 / 1e9}s")

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
