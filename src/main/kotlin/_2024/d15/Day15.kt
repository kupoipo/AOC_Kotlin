package _2024.d15

import util.*
import kotlin.system.measureNanoTime

class Day15(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    companion object {
        const val PLAYER = "@"
        const val WALL = "#"
        const val CRATE = "O"
        const val EMPTY = "."
    }

    data class State(val player: Point, val crates: Set<Point>) {
        fun weigth() = crates.sumOf { it.x + it.y * 100 }
    }

    lateinit var currentState: State
    lateinit var initState: State
    lateinit var map: Matrix<String>
    private val instructions = input.split("\n\n").last().replace("\n", "")

    fun initMap(part2: Boolean = false) {
        map = matrixFromString(input.split("\n\n").first(), ".") { it.toString() }
        var pos = Point(0, 0)
        val crates = mutableSetOf<Point>()
        for (p in map.points()) {
            if (map[p] == PLAYER) {
                pos = p
                map[p] = EMPTY
            }

            if (map[p] == CRATE) {
                crates.add(p)
                map[p] = EMPTY
            }
        }

        currentState = State(pos, crates)
        initState = currentState.copy()
    }

    fun play(part2: Boolean): Long {
        initMap(part2)
        instructions.forEach { this.move(it) }
        return currentState.weigth()
    }

    fun move(dir: Char) {
        currentState = nextState(
            when (dir) {
                '^' -> Direction.UP
                '>' -> Direction.RIGHT
                '<' -> Direction.LEFT
                else -> Direction.DOWN
            }
        )
    }

    fun nextState(dir: Direction): State {
        val nextPos = currentState.player + dir

        if (map[nextPos] == WALL) return currentState
        if (nextPos !in currentState.crates) {
            return State(nextPos, currentState.crates.toMutableSet())
        }

        val toRemove = mutableSetOf<Point>()
        var secondPos = nextPos
        while (secondPos in currentState.crates) {
            toRemove.add(nextPos)
            secondPos += dir
        }

        if (map[secondPos] == WALL) return currentState
        return State(
            nextPos,
            currentState.crates.filter { it != nextPos }.toMutableSet().apply { this.add(secondPos) })
    }

    fun display() {
        val displayMap = map.clone()
        displayMap[currentState.player] = PLAYER
        currentState.crates.forEach {
            displayMap[it] = CRATE
        }
        showMap(displayMap, 1)
    }

    override fun solve1(): Long = play(false)
    override fun solve2(): Long = play(true)
}

fun main() {
    val day = Day15(false, readFullText("_2024/d15/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day15(true, readFullText("_2024/d15/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}