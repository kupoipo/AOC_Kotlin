package _2024.d6

import util.*
import kotlin.system.measureNanoTime


class Day6(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map = matrixFromString(input, '.') { it }
    private val start = map.pointOfFirst { it == '^' }
    private val travel = places(start)

    private fun places(origin: Point): Set<Point> {
        var current = PositionDirection(origin, Direction.UP)
        val visited = mutableSetOf<Point>()

        while (current.p.inMap(map)) {
            visited.add(current.p)
            current = current.next(map)
        }

        return visited
    }

    private fun isLoop(p: Point): Boolean {
        if (map[p] == '#' || p !in travel) return false

        map[p] = '#'

        var current = PositionDirection(start, Direction.UP)
        val visited = mutableSetOf<PositionDirection>()

        while (current.p.inMap(map)) {
            current = current.next(map)
            if (current in visited) {
                map[p] = '.'
                return true
            }
            visited.add(current)
        }

        map[p] = '.'
        return false
    }

    override fun solve1(): Long = travel.size.toLong()
    override fun solve2(): Long = map.points().count(::isLoop).toLong()

    data class PositionDirection(val p: Point, val dir: Direction) {
        fun next(map: Matrix<Char>): PositionDirection {
            val nextPosition = p + dir
            return if (nextPosition.inMap(map) && map[nextPosition] == '#') {
                PositionDirection(p, dir.right())
            } else {
                PositionDirection(nextPosition, dir)
            }
        }
    }
}

fun main() {
    val day = Day6(false, readFullText("_2024/d6/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day6(true, readFullText("_2024/d6/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}