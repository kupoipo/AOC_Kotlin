package _2016.d22

import util.*
import kotlin.system.measureNanoTime

class DFState(override var parent: State?, override var time: Int, val p: Point) : State(parent, time) {
    override fun isDeadLock(): Boolean {
        return p.outOfMap(Day22.map) || Day22.map[p].used > 100
    }

    override fun nextStates(): MutableList<State> {
        return buildList {
            p.adjacent(false).forEach {
                this.add(DFState(this@DFState, time + 1, it))
            }
        }.toMutableList()
    }

    override fun isGoal(): Boolean {
        return p == Point(Day22.map[0].lastIndex, 0)
    }

    override fun hashCode(): Int {
        return p.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is DFState)
            return false
        return other.p == p
    }
}

class Node(val size: Int, val used: Int, val available: Int, val use: Int) {
    override fun toString(): String {
        return "$used/$size"
    }
}

class Day22(override val input: String) : Day<Long>(input) {
    init {
        input.split("\n").drop(2).forEach {
            it.allInts().let { data ->
                map[data[1]][data[0]] = Node(data[2], data[3], data[4], data[5])
            }
        }
    }

    override fun solve1(): Long {
        var total = 0L

        map.forEachPoint { p1 ->
            map.forEachPoint { p2 ->
                if (p1 != p2 && map[p1].used != 0 && map[p1].used <= map[p2].available)
                    total += 1
            }
        }

        return total
    }

    override fun solve2(): Long {
        val start = DFState(null, 0, map.points().first { map[it].use == 0 })
        return State.shortestPastFrom(start)!!.rebuildPath().size - 2 + (5L * (map[0].size - 2))
    }

    companion object {
        val map = emptyMatrixOf(35, 30, Node(0, 0, 0, 0))
    }
}

fun main() {
    val day = Day22(readFullText("_2016/d22/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day22(readFullText("_2016/d22/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}