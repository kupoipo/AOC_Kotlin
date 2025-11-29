package _2024.d18

import util.*
import kotlin.system.measureNanoTime

class Day18State(parent: Day18State?, time: Int, val from: Direction?, val position: Point) : State(parent, time) {
    override fun isDeadLock(): Boolean =
        map.contains(position) || position.x < 0 || position.y < 0 || position.x > goal.x || position.y > goal.y

    override fun nextStates(): MutableList<State> {
        return Direction.values().map { Day18State(this, time + 1, it, position + it) }.toMutableList()
    }

    override fun isGoal(): Boolean = position == goal

    override fun hashCode(): Int {
        return position.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Day18State

        if (position != other.position) return false

        return true
    }

    override fun toString(): String {
        return this.position.toString()
    }

    companion object {
        val map = mutableSetOf<Point>()
        var goal: Point = Point(0, 0)
    }
}

class Day18(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private var pixels = input.allInts().chunked(2)
    val start = Day18State(null, 0, null, Point(0, 0))

    init {
        Day18State.map.clear()
        if (isTest) Day18State.goal = Point(6, 6) else Day18State.goal = Point(70, 70)
        pixels.let {
            (if (isTest) it.take(12) else it.take(1024)).forEach { (x, y) -> Day18State.map.add(Point(x, y)) }
        }
    }

    override fun solve1(): Long = State.shortestPastFrom(start)?.rebuildPath()?.size?.toLong()?.minus(2L)
        ?: 0

    override fun solve2(): Long {
        var path = State.shortestPastFrom(start)?.rebuildPath()
        var i = if (isTest) 12 else 1024
        while (path != null) {
            pixels[i].let { Day18State.map.add(Point(it.first(), it.last())) }
            path = State.shortestPastFrom(start)?.rebuildPath()
            i++
        }

        println(pixels[i - 1])

        return (i - 1).toLong()
    }
}

fun main() {
    val day = Day18(false, readFullText("_2024/d18/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day18(true, readFullText("_2024/d18/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}