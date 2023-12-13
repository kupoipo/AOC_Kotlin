package _2016.d13

import util.*
import kotlin.system.measureNanoTime

data class MazeCubicleState(override var parent: State?, override var time: Int, val p: Point) : State(parent, time) {
    override fun isDeadLock(): Boolean {
        return p.outOfMap(map) || map[p] == '#' || time > maxStep
    }

    override fun nextStates(): MutableList<State> {
        return buildList {
            for (p1 in p.adjacent(false)) {
                this.add(MazeCubicleState(this@MazeCubicleState, time + 1, p1))
            }
        }.toMutableList()
    }

    override fun isGoal(): Boolean {
        return !isDeadLock() && p == goal
    }

    override fun equals(other: Any?): Boolean {
        val other = other as MazeCubicleState

        return other.p == p
    }

    override fun hashCode(): Int {
        return p.hashCode()
    }

    override fun timeWeighed(): Int {
        return time + goal.manhattan(p)
    }

    override fun toString(): String {
        return p.toString()
    }

    companion object {

        val map: Matrix<Char> = emptyMatrixOf(50, 50, '.')
        lateinit var goal: Point
        var maxStep = 1000

        fun initMap(input: String) {
            input.split("\n").let { (point, seed) ->
                goal = Point(point.firstInt(), point.lastInt())
                map.forEachPoint {
                    val n = it.x * it.x + 3 * it.x + 2 * it.x * it.y + it.y + it.y * it.y + seed.toInt()
                    val nbBinary = Integer.toBinaryString(n).count { it == '1' }

                    map[it] = if (nbBinary % 2 == 1) '#' else '.'
                }
            }

        }
    }
}

class Day13(override val input: String) : Day<Long>(input) {
    init {
        MazeCubicleState.initMap(input)
    }

    override fun solve1(): Long {
        return State.shortestPastFrom(MazeCubicleState(null, 0, Point(1,1)))!!.rebuildPath(false).size.toLong() - 2
    }

    override fun solve2(): Long {
        var total = 0L
        MazeCubicleState.maxStep = 50
        for (y in 0..50) {
            for (x in 0..50) {
                MazeCubicleState.goal = Point(x, y)
                if (Point(x,y).manhattan(Point(1,1)) > 50) continue
                val path = State.shortestPastFrom(MazeCubicleState(null, 0, Point(1,1)))
                if (path != null) {
                    total++
                }
            }
        }


        return total
    }
}

fun main() {
    val day = Day13(readFullText("_2016/d13/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day13(readFullText("_2016/d13/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}