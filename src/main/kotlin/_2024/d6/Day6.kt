package _2024.d6

import util.*
import kotlin.system.measureNanoTime


data class PositionDirection(val p: Point, val dir: Direction)

class Day6(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val map = matrixFromString(input, '.') { it }
    val start = map.pointOfFirst { it == '^' }

    private fun places(origin: Point): Set<Point> {
        var current = origin
        var direction = Direction.UP
        val visited = mutableSetOf<Point>()

        while (current.inMap(map)) {
            visited.add(current)
            val nextPosition = current + direction
            if (nextPosition.inMap(map) && map[nextPosition] == '#') {
                direction = direction.right()
            } else {
                current = nextPosition
            }
        }

        return visited
    }

    fun isLoop(p: Point): Boolean {
        if (map[p] == '#') return false

        var isLoop = false
        map[p] = '#'


        var current = start
        var direction = Direction.UP
        val visited = mutableSetOf<PositionDirection>()

        while (current.inMap(map)) {
            val pd = PositionDirection(current, direction)

            if (pd in visited) {
                isLoop = true
                break
            }

            visited.add(PositionDirection(current, direction))
            val nextPosition = current + direction
            if (nextPosition.inMap(map) && map[nextPosition] == '#') {
                direction = direction.right()
            } else {
                current = nextPosition
            }
        }



        map[p] = '.'
        return isLoop
    }

    override fun solve1(): Long = places(map.pointOfFirst { it == '^' }).size.toLong()
    override fun solve2(): Long = map.points().count { isLoop(it) }.toLong()
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