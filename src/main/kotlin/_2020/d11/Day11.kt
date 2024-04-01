package _2020.d11

import util.*
import kotlin.system.measureNanoTime

class Day11(override val input: String) : Day<Long>(input) {
    var map: Matrix<Char> = matrixFromString(input, '.') { it }
    val visibleSeat = mutableMapOf<Point, MutableList<Point>>()

    init {
        map.forEachPoint {
            val seats = mutableListOf<Point>()

            for (i in -1..1) {
                for (j in -1..1) {
                    if (i != 0 || j != 0) {
                        var pos = Point(it) + Point(j, i)
                        while (pos.inMap(map) && map[pos] == '.') {
                            pos += Point(j, i)
                        }

                        if (pos.inMap(map)) seats.add(pos)
                    }
                }
            }

            visibleSeat[it] = seats
        }
    }

    fun next(part2: Boolean): Boolean {
        val previous = map.hashCode()
        val nextMap = emptyMatrixOf(map.size, map[0].size, '.')
        map.forEachPoint {
            val neighbors = (if (part2) visibleSeat[it]!! else it.adjacent(
                true,
                false
            )).count { p -> p.inMap(map) && map[p] == '#' }
            when (map[it]) {
                'L' -> {
                    if (neighbors == 0) nextMap[it] = '#'
                    else nextMap[it] = 'L'
                }

                '#' -> {
                    if (neighbors >= if (part2) 5 else 4) nextMap[it] = 'L'
                    else nextMap[it] = '#'

                }
            }
        }
        map = nextMap

        return nextMap.hashCode() != previous
    }

    override fun solve1(): Long {
        while (next(false)) {

        }
        return map.sumOf { it.count { it == '#' } }.toLong()
    }

    override fun solve2(): Long {
        map = matrixFromString(input, '.') { it }
        while (next(true)) {
        }
        return map.sumOf { it.count { it == '#' } }.toLong()
    }
}

fun main() {
    val day = Day11(readFullText("_2020/d11/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day11(readFullText("_2020/d11/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}