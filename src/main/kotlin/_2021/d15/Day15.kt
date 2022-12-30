package _2021.d15

import util.*
import kotlin.system.measureTimeMillis

var map : Matrix<Int> = matrixOf()
data class StateDay15(val pos: Point) : State() {
    override fun isDeadLock(): Boolean {
        return false
    }

    override fun nextStates(): MutableList<State> {
        val res = mutableListOf<State>()
        pos.forEachNeighbors {
            if (it.inMap(map)) {
                val state = StateDay15(it)
                state.parent = this
                state.time   = this.time + map[pos]
                res.add(state)
            }
        }

        return res
    }

    override fun toString(): String {
        return "Pos : $pos, time : $time, timeToReach : " + timeToGoal()
    }

    override fun isGoal(): Boolean {
        return pos == goal
    }

    override fun timeToGoal(): Int {
        return time + pos.manhattan(goal)
    }

    companion object {
        var goal : Point = Point(-1,-1)

        fun initGoal(map : Matrix<Int>)  {
            goal = Point(map[0].size - 1, map.size - 1) }
    }
}
class Day15(override val input : String) : Day<Int>(input) {
    init {
        map = matrixFromString(input, 0, ::charToInt)
        StateDay15.initGoal(map)
    }

    override fun solve1(): Int {
        val goal = (State.shortestPastFrom(StateDay15(Point(0,0)).apply { time = -6 }) as StateDay15?)!!

        return goal.time + map[goal.pos]
    }
    override fun solve2(): Int {
        val startingMap = matrixFromString(input, 0, ::charToInt)

        map = emptyMatrixOf(startingMap.size * 5, startingMap[0].size * 5, 0)

        repeat(5) {
            for (y in 0 until startingMap.size) {
                for (x in 0  until  startingMap[0].size) {
                    map[y][x + it*startingMap[0].size] = if (startingMap[y][x] + it >= 10) startingMap[y][x] + it - 9 else (startingMap[y][x] + it )
                }
            }
        }

        for (it in 1..4) {
            for (y in 0 until startingMap.size) {
                for (x in 0  until  map[0].size) {
                    map[y + it*startingMap.size][x] = if (map[y][x] + it >= 10) map[y][x] + it - 9 else (map[y][x] + it )
                }
            }
        }

        StateDay15.initGoal(map)

        val goal = (State.shortestPastFrom(StateDay15(Point(0,0)).apply { time = -6 }) as StateDay15?)!!

        return goal.time + map[goal.pos]
    }
}

fun main() {
  //  var dayTest = Day15(readFullText("_2021/d15/test"))
    var day = Day15(readFullText("_2021/d15/input"))

    val t1 = measureTimeMillis {
       // println("Part 1 test : " + dayTest.solve1())
        println("Part 1 real : " + day.solve1())
    }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}