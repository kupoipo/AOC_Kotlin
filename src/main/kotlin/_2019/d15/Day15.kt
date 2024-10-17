package _2019.d15

import _2019.IntCode
import util.*
import java.lang.Exception
import kotlin.system.measureNanoTime

const val WALL = 1

class Day15(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val items = mutableMapOf(-1 to "#", 0 to " ", 1 to "X", 2 to "?", 3 to "+", 4 to "@")
    private val intCode = IntCode(input, 1, 1) {
    }
    private val map : Matrix<Int> = emptyMatrixOf(41, 41, -1)
    private var position = Point(22, 20)
    private var oxygen : Point? = null

    private fun pathTo(goal: Point): List<Direction> {
        val queue = mutableListOf(position to mutableListOf<Direction>())
        val visited = mutableSetOf<Point>()

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (current.first == goal) return current.second

            visited.add(current.first)

            for (d in Direction.values().filter { it != Direction.NONE }) {
                val newPos = current.first + d

                if (!newPos.inMap(map) || map[newPos] == WALL) continue

                if (newPos !in visited && !queue.any { it.first == newPos }) {
                    queue.add(current.first + d to current.second.toMutableList().also { it.add(d) })
                }
            }
        }

        throw Exception("No path found to $goal (shouldn't happen)")
    }

    private fun inputFromDir(dir: Direction) = when (dir) {
        Direction.UP -> 1L
        Direction.DOWN -> 2L
        Direction.LEFT -> 3L
        Direction.RIGHT -> 4L
        else -> throw Exception("Shouldn't be here")
    }

    private fun cartography() {
        val unexplored = Direction.values().filter { it != Direction.NONE }.map { position + it }.toMutableSet()
        var rep: Long
        map[position] = 4

        while (unexplored.isNotEmpty()) {
            val goal = unexplored.minByOrNull { it.manhattan(position) }!!
            val path = pathTo(goal)

            for (d in path) {
                intCode.inputInt = inputFromDir(d)
                rep = intCode.executeUntilOutput()

                when (rep) {
                    0L -> { // Wall
                        map[position + d] = WALL
                        unexplored.remove(position + d)
                        break
                    }

                    1L -> { // Moved
                        map[position + d] = 4
                        map[position] = if (oxygen != null && oxygen == position) 2 else 0

                        position += d
                        unexplored.remove(position)
                        unexplored.addAll(Direction.values().filter { it != Direction.NONE && map[position + it] == -1 }
                            .map { position + it }.toMutableSet())
                    }

                    else -> {
                        oxygen = position + d
                        map[position + d] = 3
                        map[position] = 0

                        position += d
                        unexplored.remove(position)
                        unexplored.addAll(Direction.values().filter { it != Direction.NONE && map[position + it] == -1 }
                            .map { position + it }.toMutableSet())
                        continue
                    }
                }

            }

        }
        display()

    }

    fun display() {
        showMap(map, 1, transformation = { items[it]!! })
    }

    fun repair() : Long {
        map[position] = 0

        val queue = mutableListOf(oxygen!! to 0)
        val visited = mutableSetOf<Point>()
        var max = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current.second > max) max = current.second
            visited.add(current.first)

            for (d in Direction.values().filter { it != Direction.NONE }) {
                val newPos = current.first + d

                if (!newPos.inMap(map) || map[newPos] == WALL) continue

                if (newPos !in visited && !queue.any { it.first == newPos }) {
                    queue.add(current.first + d to current.second + 1)
                }
            }
        }

        return max.toLong()
    }

    override fun solve1(): Long = cartography().let { position = Point(21,21); pathTo(oxygen!!).size.toLong() }

    override fun solve2(): Long = repair()
}

fun main() {
    val day = Day15(false, readFullText("_2019/d15/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day15(true, readFullText("_2019/d15/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}