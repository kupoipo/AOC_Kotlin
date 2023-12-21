package _2023.d21

import util.*
import kotlin.system.measureNanoTime

class StepCounterState(override var parent: State?, override var time: Int, val position: Point) : State(parent, time) {
    override fun isDeadLock(): Boolean = position.outOfMap(map) || map[position] == '#' || position in reachable
    override fun nextStates(): MutableList<State> {
        return buildList {
            position.adjacent(false).forEach {
                if (it !in reachable)
                        add(StepCounterState(this@StepCounterState, time + 1, it))
            }
        }.toMutableList()
    }

    override fun isGoal(): Boolean {
        return time == 64
    }

    companion object {
        lateinit var map: Matrix<Char>
        var reachable = mutableSetOf<Point>()
    }

    override fun toString(): String {
        return position.toString()
    }
}

class Day21(override val input: String) : Day<Long>(input) {
    init {
        StepCounterState.map = matrixFromString(input, '.') { it }
        StepCounterState.reachable = mutableSetOf()
    }

    override fun solve1(): Long {
        val queue = mutableListOf(StepCounterState(null, 0, StepCounterState.map.pointOfFirst { it == 'S' }))

        while(queue.isNotEmpty()) {
            val current = queue.removeAt(0)

            if (current.isDeadLock())
                continue

            if (!current.isGoal())
                queue.addAll(current.nextStates() as List<StepCounterState>)

            if (current.time % 2 == 0)
                StepCounterState.reachable.add(current.position)

        }


        return StepCounterState.reachable.size.toLong()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day21(readFullText("_2023/d21/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day21(readFullText("_2023/d21/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}