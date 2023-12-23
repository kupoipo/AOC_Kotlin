package _2023.d23

import kotlin.system.measureNanoTime
import util.*

class D23State(override var parent: State?, override var time: Int,  val p: Point, val visited: Set<Point>) : State(parent, time) {
    override fun hashCode(): Int {
        return time.hashCode()
    }

    override fun isDeadLock(): Boolean {
        return p.outOfMap(map) || map[p] == '#'
    }

    override fun nextStates(): MutableList<State> {
        return buildList {
            when (map[p]) {
                '.' -> {
                    for (position in p.adjacent(false)) {
                        if (position !in visited) {
                            add(D23State(this@D23State, time + 1, position, visited.toMutableSet().apply { add(position) }))
                        }
                    }
                }
                '>' -> {
                    if (p+ Direction.RIGHT !in visited) {
                        add(D23State(this@D23State, time + 1, p + Direction.RIGHT, visited.toMutableSet().apply { add(p+Direction.RIGHT) }))
                    }
                }
                '<' ->  {
                    if (p+ Direction.LEFT !in visited) {
                        add(D23State(this@D23State, time + 1, p + Direction.LEFT, visited.toMutableSet().apply { add(p+Direction.LEFT) }))
                    }
                }
                '^' -> {
                    if (p+ Direction.UP !in visited) {
                        add(D23State(this@D23State, time + 1, p + Direction.UP, visited.toMutableSet().apply { add(p+Direction.UP) }))
                    }
                }
                else ->  {
                    if (p+ Direction.DOWN !in visited) {
                        add(D23State(this@D23State, time + 1, p + Direction.DOWN, visited.toMutableSet().apply { add(p+Direction.DOWN) }))
                    }
                }
            }
        }.toMutableList()
    }

    override fun isGoal(): Boolean {
        return p == Point(map[0].lastIndex - 1, map.lastIndex)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is D23State)
            return false
        return other.p == p
    }

    companion object {
        lateinit var map : Matrix<Char>
    }
}

class Day23(override val input : String) : Day<Long>(input) {
    init {
        D23State.map = matrixFromString(input, '.') { it }
    }
    override fun solve1(): Long {
        val start = D23State(null, 0, Point(1,0), setOf(Point(1,0)))
        val paths = State.longestPathFrom(start)!!.rebuildPath().size
        return paths.toLong()
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day23(readFullText("_2023/d23/input"))

  /*  val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")*/

    println()
    println()

    val dayTest = Day23(readFullText("_2023/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}