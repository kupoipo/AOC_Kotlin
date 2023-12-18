package _2022.d24
import util.Day
import util.*
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

var map : Matrix<Char> = matrixOf()
var goal : Point = Point(0,0)
var blizzardAtTiming = mutableListOf<Set<Blizzard>>()

data class Blizzard(var pos : Point, val c : Char = '>') {

    private val dir : Direction = when (c) {
        '<' -> Direction.LEFT
        '>' -> Direction.RIGHT
        '^' -> Direction.UP
        else -> { Direction.DOWN }
    }

    fun nextPos() : Blizzard {
        val newPos = pos + dir.times(1)
        if (newPos.x.toInt() == map[0].size - 1) newPos.x = 1
        if (newPos.x.toInt() == 0) newPos.x = (map[0].size - 2).toLong()
        if (newPos.y.toInt() == map.size - 1) newPos.y = 1
        if (newPos.y.toInt() == 0) newPos.y = (map.size - 2).toLong()

        return Blizzard(newPos, c)
    }
}

data class State(val pos : Point, val time : Int) {
    private var parent : State? = null
    private fun isValidState(nextBlizzard : Set<Blizzard>, order : Direction) : Boolean{
        val nextPos = pos + order

        if (nextPos.x < 0 || nextPos.y < 0 || nextPos.y >= map.size) return false
        if (map[nextPos] == '#') return false

        for (it in nextBlizzard) {
            if (it.pos == nextPos) {
                return false
            }
        }

        return true
    }

    fun nextState() : List<State> {

        if ( time < 600 && blizzardAtTiming.size <= time + 1) {
            blizzardAtTiming.add(buildSet{
                blizzardAtTiming[time].forEach {
                    add(it.nextPos());
                }
            })
        }

        val father = this

        val nextBlizzard = blizzardAtTiming[(time+1)%600]

        val res = buildList {
            for (d in Direction.values()) {
                if (isValidState(nextBlizzard, d)) {
                    add(State(pos + d, time + 1).apply { parent = father })
                }
            }
        }

        return res
    }
    fun isOver() = pos == goal

    fun timeToGoal() : Long { return time + pos.manhattan(goal) }
}

class Day24(override val input: String) : Day<Int>(input) {
    init {
        blizzardAtTiming.add( buildSet {
            input.split("\n").forEachIndexed{ y, line ->
                line.forEachIndexed { x, case -> if (case in "><^v") add(Blizzard(Point(y = y, x = x), c = case)) }
            }
        } )

        map = emptyMatrixOf(input.count { it == '\n' } + 1, input.length / (input.count { it == '\n' } + 1) , '.')
        (0 until map.size).forEach{line -> map[line][0] = '#'; map[line][map[0].size - 1] = '#'}
        (0 until map[0].size).forEach{col -> map[0][col] = '#'; map[map.size - 1][col] = '#'}
        map[Point(x = map[0].size - 2, y = map.size - 1)] = '.'; map[Point(1,0)] = '.'
    }

    fun solve(initialState : State, finish : Point) : State? {
        val comparator: Comparator<State> = compareBy { it.timeToGoal() }
        val visited = mutableSetOf<State>()
        val queue = PriorityQueue(comparator)
        queue.add(initialState)
        goal = finish

        while (queue.isNotEmpty()) {
            val currentState = queue.poll()

            if (currentState.isOver()) {
                return currentState
            }

            for (state in currentState.nextState())
                if (state !in visited && state !in queue) queue.add(state)

            visited.add(currentState)
        }

        return null
    }

    override fun solve1(): Int {
        return solve(State(Point(x = 1, y = 0), 0), Point(x = map[0].size - 2, y = map.size - 1))!!.time

    }

    override fun solve2(): Int {
        var s = solve(State(Point(x = 1, y = 0), 0), Point(x = map[0].size - 2, y = map.size - 1))!!
        s = solve(s, Point(1,0))!!
        s = solve(s, Point(x = map[0].size - 2, y = map.size - 1))!!
        return s.time
    }

}

fun main() {
    var d = Day24(readFullText("d24/input"))
    var d1 = measureTimeMillis { println(d.solve1()) }

    println("Part 1 in $d1")

    d1 = measureTimeMillis {  println(d.solve2()) }

    println("Part 2 in $d1")
}