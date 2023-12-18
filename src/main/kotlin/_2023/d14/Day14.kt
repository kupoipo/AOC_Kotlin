package _2023.d14

import util.*
import kotlin.system.measureNanoTime

const val NB_CYCLE = 1_000_000_000
class Day14(override val input : String) : Day<Long>(input) {
    val map = matrixFromString(input, '.' ) { it }
    val rocks = map.points().filter { map[it] == 'O' }.toMutableList()
    val mapCycle = mutableMapOf<Set<Point>, Int>()
    val cycle = listOf(::moveNorth, ::moveWest, ::moveSouth, ::moveEast)
    fun moveNorth() {
        rocks.sortedBy{ it.y }.forEach { r ->
            map[r] = '.'
            (r.y downTo 0).firstOrNull { map[it][r.x] != '.' }.let {
                if (it == null) r.y = 0
                else r.y = it + 1
            }
            map[r] = 'O'
        }
    }
    fun moveWest() {
        rocks.sortedBy { it.x  }.forEach { r ->
            map[r] = '.'
            (r.x downTo 0).firstOrNull { map[r.y][it] != '.' }.let {
                if (it == null) r.x = 0
                else r.x = it + 1
            }
            map[r] = 'O'
        }
    }
    fun moveSouth() {
        rocks.sortedByDescending { it.y  }.forEach { r ->
            map[r] = '.'
            (r.y until map.size).firstOrNull { map[it][r.x] != '.' }.let {
                if (it == null) r.y = map.lastIndex.toLong()
                else r.y = it - 1
            }
            map[r] = 'O'
        }
    }
    fun moveEast() {
        rocks.sortedByDescending { it.x  }.forEach { r ->
            map[r] = '.'
            (r.x until map[0].size).firstOrNull { map[r.y][it] != '.' }.let {
                if (it == null) r.x = map[0].lastIndex.toLong()
                else r.x = it - 1
            }
            map[r] = 'O'
        }
    }

    override fun solve1(): Long {
        moveNorth()

        return rocks.sumOf { map.size - it.y }.toLong()
    }
    override fun solve2(): Long {
        var i = NB_CYCLE
        rocks.clear()
        rocks.addAll(map.points().filter { map[it] == 'O' })

        while (i > 0) {
            cycle.forEach { it() }

            val set = mutableSetOf<Point>().apply { rocks.forEach { this.add(Point(it)) } }

            if (set !in mapCycle.keys) {
                mapCycle[set] = NB_CYCLE - i
            } else {
                val sizeCycle = (NB_CYCLE - i) - mapCycle.entries.first { it.key == set }.value
                i %= sizeCycle
            }
            i--
        }

        return rocks.sumOf { map.size - it.y }.toLong()
    }
}

fun main() {
    val day = Day14(readFullText("_2023/d14/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day14(readFullText("_2023/d14/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}