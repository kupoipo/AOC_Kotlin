package _2025.d1

import _2021.d18.split
import util.*
import java.util.LinkedList
import kotlin.system.measureNanoTime

class Day1(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class Wall(val north: Boolean, val east: Boolean, val south: Boolean, val west: Boolean) {
        val dirs =
            mapOf(Direction.LEFT to west, Direction.RIGHT to east, Direction.UP to north, Direction.DOWN to south)
    }

    private val robots = mutableListOf<Point>()
    private val distances = mutableListOf<Int>()

    private lateinit var map: Matrix<Wall>

    init {
        val lines = input.split("\n").toMutableList()
        lines[1].allInts().chunked(2).forEach { robots.add(Point(it[1], it[0])) }
        distances.addAll(lines[2].allInts())

        map = matrixOf(lines.drop(3).map {
            it.split(" ").map {
                val bin = it.toInt().toString(2).padStart(4, '0').map { it != '1' }
                Wall(bin[3], bin[2], bin[1], bin[0])
            }.toMutableList()
        }.toMutableList())

    }

    fun getPointsOf(robot: Point, distance: Int): Set<Point> {
        val res =  buildSet {
            val map = emptyMatrixOf(map.size, map.size, 0)
            val queue = LinkedList<Point>()
            queue.add(robot)

            val visited = mutableSetOf<Point>()

            println("Launching for robot $robot")

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()

                visited.add(current)
                if (map[current] == distance) {
                    add(current)
                } else {
                    val wall = this@Day1.map[current]

                    for (d in wall.dirs) {
                        if (d.value) {
                            val newPos = current + d.key
                            if (newPos.inMap(map) && newPos !in visited && newPos !in queue) {
                                map[newPos] = map[current] + 1
                                queue.add(newPos)
                            }
                        }
                    }
                }
            }
        }

        println(res)
        return res
    }

    override fun solve1(): Long {
        val points =
            robots.indices.map { getPointsOf(robots[it], distances[it]) }.reduce { acc, set -> acc.intersect(set) }

        println(points)
        return -1
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day1(false, readFullText("_2025/d1/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day1(true, readFullText("_2025/d1/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}