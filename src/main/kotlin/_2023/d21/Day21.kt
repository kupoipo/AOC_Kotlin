package _2023.d21

import _2023.d21.StepCounterState.Companion.map
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
        return time == nbStep
    }

    companion object {
        lateinit var map: Matrix<Char>
        var reachable = mutableSetOf<Point>()
        var nbStep = 1
    }

    override fun toString(): String {
        return position.toString()
    }
}

class Day21(override val input: String) : Day<Long>(input) {
    init {
        map = matrixFromString(input, '.') { it }
    }

    fun getNbPositionReachable(i: Int): Long {
        StepCounterState.nbStep = i
        StepCounterState.reachable = mutableSetOf()

        val queue = mutableListOf(StepCounterState(null, 0, map.pointOfFirst { it == 'S' }))

        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)

            if (current.isDeadLock())
                continue

            if (!current.isGoal())
                queue.addAll(current.nextStates() as List<StepCounterState>)

            if (i % 2 == current.time % 2)
                StepCounterState.reachable.add(current.position)

        }


        return StepCounterState.reachable.size.toLong()
    }

    override fun solve1(): Long = getNbPositionReachable(64)

    override fun solve2(): Long {
        map.setCenter('.')
        map = map.tile(31, 31, '.')
        map.setCenter('S')

        var list = buildList {
            for (i in 65 .. (65 + 131 * 2) step 131) {
                add(getNbPositionReachable(i))
            }
        }

        var count = list.last()

        list = buildList {
            for (i in 1..2) {
                add(list[i] - list[i - 1])
            }
        }

        val sizeStep = list.last() - list.first()
        var step = list.last() + sizeStep
        var start = 65L + 262L

        while (start < 26501365L) {
            start += 131L
            count += step
            step += sizeStep

        }
        return count
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

}