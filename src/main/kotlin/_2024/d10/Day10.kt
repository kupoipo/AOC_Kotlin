package _2024.d10

import util.*
import kotlin.system.measureNanoTime

class Day10(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map: Matrix<Int> = matrixFromString(input, -1) { it.digitToInt() }
    private val zeros = map.points().filter { map[it] == 0 }

    private fun pathFrom(p: Point): Long {
        val queue = mutableListOf(p)
        val visited = mutableSetOf<Point>()
        var nbPaths = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (map[current] == 9) {
                nbPaths++
                continue
            }

            visited.add(current)

            for (d in Direction.values()) {
                val newPoint = current + d

                if (newPoint.inMap(map) && map[newPoint] - 1 == map[current] && newPoint !in visited && newPoint !in queue) {
                    queue.add(newPoint)
                }
            }
        }

        return nbPaths.toLong()
    }

    private fun pathFromV2(p: Point): Long {
        val queue = mutableListOf(p to mutableListOf<Direction>())
        val visited = mutableSetOf<MutableList<Direction>>()
        var nbPaths = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (map[current.first] == 9) {
                nbPaths++
                continue
            }

            visited.add(current.second)

            for (d in Direction.values()) {
                val newPoint = current.first + d
                val newPath  = current.second.toMutableList().apply { this.add(d) }
                val newPair = newPoint to newPath

                if (newPoint.inMap(map) && map[newPoint] - 1 == map[current.first] && newPath !in visited && newPair !in queue) {
                    queue.add(newPair)
                }
            }
        }

        return nbPaths.toLong()
    }

    override fun solve1(): Long = zeros.sumOf(::pathFrom)

    override fun solve2(): Long = zeros.sumOf(::pathFromV2)
}

fun main() {
    val day = Day10(false, readFullText("_2024/d10/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day10(true, readFullText("_2024/d10/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}