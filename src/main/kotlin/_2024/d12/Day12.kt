package _2024.d12

import util.*
import kotlin.system.measureNanoTime

class Day12(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class Region(val char: Char, val perimeter: Long, val points: MutableSet<Point>, val map: Matrix<Char>) {
        fun getPerimeterV2(): Long {


            return points.map { p ->
                val neighbors = p.adjacent(false).filter {
                    it.outOfMap(map) || map[it] != map[p]
                }

                var corners = when (neighbors.size) {
                    4 -> 4L
                    3 -> 2L
                    2 -> if (neighbors.first().notInLineWith(neighbors.last())) 1L else 0L
                    else -> 0L
                }

                for (corner in listOf(Point(1, 1), Point(1, -1), Point(-1, -1), Point(-1, 1))) {
                    val sideOne = p + Point(corner.x, 0)
                    val sideTwo = p + Point(0, corner.y)
                    val diagonal = p + corner

                    if (sideOne.inMap(map) && map[sideOne] == map[p]
                        && sideTwo.inMap(map) && map[sideTwo] == map[p]
                        && map[diagonal] != map[p]) {
                        corners++
                    }
                }

                corners
            }.sum()
        }

        override fun toString(): String =
            "A region of $char"// plants with price ${getPerimeterV2()} * ${points.size} = ${getPerimeterV2() * points.size}"
    }

    private val map = matrixFromString(input, '.') { it }
    private val points = map.points()

    fun getRegion() = buildList {
        val copy = points.toMutableList()

        while (copy.isNotEmpty()) {
            val queue = mutableListOf(copy.removeFirst())
            val visited = mutableSetOf<Point>()
            val char = map[queue.first()]

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                copy.remove(current)

                visited.add(current)

                for (dir in Direction.values()) {
                    val newPoint = current + dir
                    if (newPoint !in visited && newPoint.inMap(map) && map[newPoint] == char && newPoint !in queue) {
                        queue.add(newPoint)
                    }
                }
            }

            val perimeter = visited.fold(0L) { acc, p ->
                acc + p.adjacent(false).count { it.outOfMap(map) || map[it] != map[p] }
            }

            add(Region(char, perimeter, visited, map))
        }
    }

    override fun solve1(): Long = getRegion().sumOf { it.perimeter * it.points.size }
    override fun solve2(): Long = getRegion().sumOf { it.getPerimeterV2() * it.points.size }
}

fun main() {
    val day = Day12(false, readFullText("_2024/d12/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day12(true, readFullText("_2024/d12/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}