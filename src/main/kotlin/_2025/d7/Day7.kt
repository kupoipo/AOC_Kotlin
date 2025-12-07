package _2025.d7

import util.*
import kotlin.system.measureNanoTime

class Day7(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map = matrixFromString(input, '.') { it }
    private val startPoint = map.pointOfFirst { it == 'S' }
    private val splitsSaw = mutableSetOf<Point>()
    private val paths = mutableMapOf<Point, Long>()

    private fun nbSplit(from: Point): Long {
        var current = from

        do {
            if (map[current] == '^') {
                if (splitsSaw.contains(current)) return 0
                splitsSaw.add(current)

                return 1 + nbSplit(current + Direction.RIGHT) + nbSplit(current + Direction.LEFT)
            }
            current += Direction.DOWN
        } while (current.inMap(map))

        return 0
    }

    private fun nbPaths(from: Point): Long {
        var current = from

        do {
            if (map[current] == '^') {
                return paths.getOrPut(current) {
                    return@getOrPut nbPaths(current + Direction.RIGHT) + nbPaths(current + Direction.LEFT)
                }
            }
            current += Direction.DOWN
        } while (current.inMap(map))

        return 1
    }

    override fun solve1(): Long = nbSplit(startPoint)

    override fun solve2(): Long = nbPaths(startPoint)
}

fun main() {
    for (i in 1..5) {
        try {
            val dayTest = Day7(true, readFullText("_2025/d7/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

    val day = Day7(false, readFullText("_2025/d7/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()
}