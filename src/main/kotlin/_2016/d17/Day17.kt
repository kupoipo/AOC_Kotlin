package _2016.d17

import util.*
import kotlin.system.measureNanoTime

class StateD17_2016(parent: State?, time: Int, val position: Point, val path: String) : State(parent, time) {
    val map = emptyMatrixOf(4,4,' ')
    override fun isDeadLock(): Boolean {
        return position.outOfMap(map)
    }

    override fun nextStates(): MutableList<State> {
        val md = md5(path).take(4)

        return buildList {
            if (md[0] in OPEN) this.add(StateD17_2016(this@StateD17_2016, time + 1, position + Direction.UP, path + "U"))
            if (md[1] in OPEN) this.add(StateD17_2016(this@StateD17_2016, time + 1, position + Direction.DOWN, path + "D"))
            if (md[2] in OPEN) this.add(StateD17_2016(this@StateD17_2016, time + 1, position + Direction.LEFT, path + "L"))
            if (md[3] in OPEN) this.add(StateD17_2016(this@StateD17_2016, time + 1, position + Direction.RIGHT, path + "R"))
        }.toMutableList()
    }

    override fun isGoal(): Boolean {
        return position == Point(3,3)
    }

    companion object {
        const val OPEN = "bcdef"
    }
}

class Day17(override val input : String) : Day<String>(input) {
    val start = StateD17_2016(null, 0, Point(0,0), input)
    override fun solve1(): String {
        val goal = State.shortestPastFrom(start)!!.rebuildPath().first()
        return (goal as StateD17_2016).path.drop(input.length)
    }
    override fun solve2(): String {
        val goal = State.allPathTo(start)
        return goal.maxBy { it.time }.time.toString()
    }
}

fun main() {
    val day = Day17(readFullText("_2016/d17/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day17(readFullText("_2016/d17/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}