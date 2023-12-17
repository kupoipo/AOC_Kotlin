package _2023.d17

import util.*
import java.lang.Exception
import kotlin.system.measureNanoTime

/**
 * Can not work on some input because we don't check if we pass through the same place two times or not, but work on mine o/
 */
class LavaState(
    override var parent: State?,
    override var time: Int,
    val position: Point,
    val forward: Int,
    val comingFrom: Direction
) :
    State(parent, time) {
    override fun isDeadLock(): Boolean {
        return false
    }

    override fun nextStates(): MutableList<State> {
        return buildList {
            for (d in Direction.values().dropLast(1)) {
                var newPoint = position + d

                if (newPoint.outOfMap(map))
                    continue

                var scoreToAdd = map[newPoint]

                if (d == comingFrom) {
                    if (forward < 10) {
                        this.add(LavaState(this@LavaState, time + scoreToAdd, newPoint, forward + 1, d))
                    }
                } else {
                    if (d != comingFrom.opposite()) {
                        try {
                            repeat(3) {
                                newPoint += d
                                scoreToAdd += map[newPoint]
                            }
                            this.add(LavaState(this@LavaState, time + scoreToAdd, newPoint, 4, d))
                        } catch (_: Exception) {

                        }
                    }
                }
            }
        }.toMutableList()
    }

    override fun isGoal(): Boolean {
        return position == goal
    }

    override fun hashCode(): Int {
        var res = 17

        res = 31 * res + position.hashCode()
        res = 31 * res + forward.hashCode()
        res = 31 * res + comingFrom.hashCode()

        return res
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LavaState)
            return false
        return other.position == position && forward == other.forward && comingFrom == other.comingFrom
    }

    override fun timeWeighed(): Int {
        return time
    }

    override fun toString(): String {
        return position.toString()
    }
    companion object {
        lateinit var map: Matrix<Int>
        lateinit var goal: Point
    }
}

class Day17(override val input: String) : Day<Long>(input) {
    val start = LavaState(null, 0, Point(0,0), 0, Direction.NONE)
    init {
        LavaState.map = matrixFromString(input, 0) { it.digitToInt() }
        LavaState.goal = Point(LavaState.map[0].lastIndex, LavaState.map.lastIndex)
    }

    override fun solve1(): Long {
        return (State.shortestPastFrom(start)!!.rebuildPath().first() as LavaState).time.toLong()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day17(readFullText("_2023/d17/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day17(readFullText("_2023/d17/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}