package _2024.d20

import util.*
import kotlin.system.measureNanoTime

class Day20(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private var start: Point = Point()
    private var end: Point = Point()
    private var maximum: Int = 0
    private val map: Matrix<Char> = matrixFromStringIndexed(input, '.') { c, y, x ->
        if (c == 'E') end = Point(x, y)
        if (c == 'S') start = Point(x, y)
        if (c in "ES.") '.' else '#'
    }
    private val computedMap: Matrix<Int> = emptyMatrixOf(map.size, map.nbColumns, -1)
    private val path = mutableListOf<Direction>()

    init {
        val queue = mutableListOf<Pair<Point, Int>>()
        val seen = mutableSetOf<Point>()
        queue.add(end to 0)

        while (queue.isNotEmpty()) {
            val (currentPoint, currentDist) = queue.removeFirst()

            if (computedMap[currentPoint] == -1)
                computedMap[currentPoint] = currentDist

            seen.add(currentPoint)

            for (d in Direction.values()) {
                val nextPoint = currentPoint + d

                if (nextPoint.inMap(computedMap) && nextPoint !in seen && map[nextPoint] != '#') {
                    queue.add(nextPoint to currentDist + 1)
                    path.add(d.opposite())
                }
            }
        }
        path.reverse()
        maximum = computedMap[start]
    }

    private fun timeCheatFrom(pos: Point, cheatTime: Int): Map<Int, Long> {
        val res = mutableMapOf<Int, Long>()
        val currentTime = computedMap[pos]

        for (y in pos.y - cheatTime..pos.y + cheatTime) {
            for (x in pos.x - cheatTime..pos.x + cheatTime) {
                val potentialPos = Point(x, y)
                val distManhattan = pos.manhattan(potentialPos)

                if (distManhattan > cheatTime || potentialPos.outOfMap(map) || map[potentialPos] == '#') continue

                val newTime = (maximum - currentTime) + distManhattan + computedMap[potentialPos]

                if (newTime < maximum) {
                    val savedTime = (maximum - newTime).toInt()
                    res[savedTime] = res.getOrElse(savedTime) { 0 } + 1
                }
            }
        }

        return res
    }

    private fun getTimesPerCheat(cheatTime: Int): Map<Int, Long> {
        val res = mutableMapOf<Int, Long>()
        var currentPosition = start

        for (dir in path) {
            val cheats = timeCheatFrom(currentPosition, cheatTime)

            for ((time, nb) in cheats) {
                res[time] = res.getOrElse(time) { 0 } + nb
            }

            currentPosition += dir
        }

        return res
    }

    override fun solve1(): Long = getTimesPerCheat(2).filter { it.key >= 100 }.values.sum()

    override fun solve2(): Long = getTimesPerCheat(20).filter { it.key >= 100 }.values.sum()
}

fun main() {
    val day = Day20(false, readFullText("_2024/d20/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day20(true, readFullText("_2024/d20/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            e.printStackTrace()
            break
        }
    }

}