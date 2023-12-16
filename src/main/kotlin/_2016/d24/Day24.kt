package _2016.d24

import _2016.d22.Day22
import util.*
import kotlin.system.measureNanoTime

class D24State(override var parent: State?, override var time: Int, val p: Point) : State(parent, time) {
    override fun isDeadLock(): Boolean {
        return p.outOfMap(Day24.map) || Day24.map[p] == '#'
    }

    override fun nextStates(): MutableList<State> {
        return buildList {
            p.adjacent(false).forEach {
                this.add(D24State(this@D24State, time + 1, it))
            }
        }.toMutableList();
    }

    override fun isGoal(): Boolean {
        return p == goal
    }

    override fun hashCode(): Int {
        return p.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is D24State)
            return false
        return other.p == p
    }

    companion object {
        var goal = Point(0, 0)
    }
}

class Day24(override val input: String) : Day<Long>(input) {
    var part2 = false

    init {
        init(input)
    }

    fun shortestPath(visited: List<Point>, from: Point, length: Long): Long {
        if (visited.size == interestedPoint.size) {
            return length + if (part2) shortestPath[from]!![firstInterestedPoint]!! else 0
        }

        var min = 1_000_000L

        for (point in interestedPoint) {
            if (point !in visited) {
                val local = shortestPath(
                    buildList { addAll(visited); add(point) },
                    point,
                    length + shortestPath[from]!![point]!!
                )

                if (local < min)
                    min = local
            }
        }

        return min
    }

    override fun solve1(): Long = shortestPath(listOf(firstInterestedPoint), firstInterestedPoint, 0)

    override fun solve2(): Long {
        part2 = true
        return shortestPath(listOf(firstInterestedPoint), firstInterestedPoint, 0)
    }

    companion object {
        lateinit var map: Matrix<Char>
        lateinit var interestedPoint: List<Point>
        lateinit var firstInterestedPoint: Point
        var shortestPath: MutableMap<Point, MutableMap<Point, Int>> = mutableMapOf()

        fun init(input: String) {
            shortestPath = mutableMapOf()
            map = matrixFromString(input, '.') { it }
            interestedPoint = map.points().filter { p -> map[p] != '.' && map[p] != '#' }
            firstInterestedPoint = interestedPoint.first { map[it] == '0' }

            for (start in interestedPoint) {
                shortestPath[start] = mutableMapOf()
                for (finish in interestedPoint) {
                    if (start != finish) {
                        val startState = D24State(null, 0, start)
                        D24State.goal = finish

                        shortestPath[start]!![finish] = State.shortestPastFrom(startState)!!.rebuildPath().size - 2
                    }
                }
            }
        }
    }
}

fun main() {
    val day = Day24(readFullText("_2016/d24/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day24(readFullText("_2016/d24/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}