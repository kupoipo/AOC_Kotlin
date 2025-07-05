package _2024.d16

import util.*
import java.util.*
import kotlin.system.measureNanoTime

class Day16(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class Day16State(
        override var parent: State?,
        val direction: Direction,
        val position: Point,
        val visited: Set<Point>,
        override var time: Int
    ) : State(parent, time) {

        override fun isDeadLock(): Boolean = false

        override fun nextStates(): MutableList<State> {
            val res = mutableListOf<Day16State>()
            val newVisited = visited.toMutableSet()
            var currentPosition = position
            var nbSteps = 0

            while (map[currentPosition] != '#') {
                newVisited.add(currentPosition)

                val right = currentPosition + direction.right()
                val left = currentPosition + direction.left()

                if (map[right] != '#') {
                    res.add(
                        Day16State(
                            this,
                            direction.right(),
                            right,
                            newVisited.toSet(),
                            time + 1001 + nbSteps
                        )
                    )
                }

                if (map[left] != '#') {
                    res.add(
                        Day16State(
                            this,
                            direction.left(),
                            left,
                            newVisited.toSet(),
                            time + 1001 + nbSteps
                        )
                    )
                }

                currentPosition += direction
                nbSteps++
            }

            if (Day16State.map[currentPosition] == '#') {
                currentPosition += direction.opposite()
                nbSteps--
            }

            if (nbSteps > 0) {
                res.add(
                    Day16State(
                        this,
                        direction,
                        currentPosition,
                        newVisited.toSet(),
                        time + nbSteps
                    )
                )
            }

            return res as MutableList<State>
        }

        override fun isGoal(): Boolean = position == goal

        override fun hashCode(): Int = Objects.hash(position, direction)
        override fun equals(other: Any?): Boolean =
            other is Day16State && other.position == position && other.direction == direction

        companion object {
            var bestTime: Int? = null
            var goal: Point = Point(0, 0)
            var map: Matrix<Char> = emptyMatrixOf(0, 0, '.')
        }
    }


    val start: Point

    init {
        Day16State.map = matrixFromString(input, '.') { it }
        Day16State.goal = Day16State.map.points().first { Day16State.map[it] == 'E' }
        start = Day16State.map.points().first { Day16State.map[it] == 'S' }

        Day16State.map[start] = '.'
    }

    override fun solve1(): Long {
        Day16State.bestTime = null
        val startNode = Day16State(null, Direction.RIGHT, start, mutableSetOf(), 0)
        State.shortestPastFrom(startNode)!!.time.toLong().let {
            Day16State.bestTime = it.toInt()
            return it
        }

    }
    override fun solve2(): Long {
        val bestTime = Day16State.bestTime ?: error("solve1() must be called first")

        val queue = PriorityQueue<State>()
        val seen = mutableMapOf<Pair<Point, Direction>, Int>()
        val bestPathTiles = mutableSetOf<Point>()

        queue.add(Day16State(null, Direction.RIGHT, start, emptySet(), 0))

        while (queue.isNotEmpty()) {
            val current = queue.poll() as Day16State

            if (current.time > bestTime) continue

            val key = current.position to current.direction
            if (seen[key]?.let { it <= current.time } == true) continue
            seen[key] = current.time

            if (current.isGoal() && current.time == bestTime) {
                bestPathTiles.addAll(current.visited)
                bestPathTiles.add(current.position)
                continue
            }

            queue.addAll(current.nextStates())
        }

        bestPathTiles.add(start)
        bestPathTiles.forEach { Day16State.map[it] = 'O' }

        showMap(Day16State.map, 1)

        return bestPathTiles.size.toLong()
    }

}

fun main() {
    val day = Day16(false, readFullText("_2024/d16/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day16(true, readFullText("_2024/d16/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }
}