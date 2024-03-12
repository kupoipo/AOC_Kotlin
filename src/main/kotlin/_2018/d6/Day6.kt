package _2018.d6

import util.*
import kotlin.system.measureNanoTime

class Day6(override val input: String) : Day<Long>(input) {
    private val points = input.split("\n").map { it.allInts().let { ints -> Point(ints.first(), ints.last()) } }
    private val bottomRight = Point(points.maxOf { it.x } + 1, points.maxOf { it.y } + 1)
    private val shared = mutableSetOf<Point>()
    private val infinite = mutableSetOf<Int>()
    private val map: Matrix<Pair<Int, Int>>
    private val mapManhattan: Matrix<Long>

    init {
        val topLeft = Point(points.minOf { it.x }, points.minOf { it.y })

        points.forEach { point ->
            point.x = point.x - topLeft.x + 1
            point.y = point.y - topLeft.y + 1
        }

        bottomRight.x = bottomRight.x - topLeft.x + 1
        bottomRight.y = bottomRight.y - topLeft.y + 1

        map = emptyMatrixOf((bottomRight.y + 1).toInt(), (bottomRight.x + 1).toInt(), Int.MAX_VALUE to -1)
        mapManhattan = emptyMatrixOf((bottomRight.y).toInt(), (bottomRight.x).toInt(), 0)

        map.forEachPoint { p ->
            if (p.inMap(mapManhattan))
                mapManhattan[p] = points.sumOf { it.manhattan(p) }
        }

        points.forEach { p -> map[p] = 0 to p.id }
        points.forEach { expandPoint(it) }

        shared.forEach { map[it] = 0 to 0 }

        for (i in 0 until map.size) {
            infinite.add(map[i][0].second)
            infinite.add(map[i][map[i].lastIndex].second)
        }

        for (i in 0 until map[0].size) {
            infinite.add(map[0][i].second)
            infinite.add(map[map[i].lastIndex][i].second)
        }
    }

    private fun expandPoint(p: Point) {
        val explored = mutableSetOf<Point>()
        val queue = mutableListOf(0 to p)

        while (queue.isNotEmpty()) {
            val position = queue.removeFirst()
            val nextCost = position.first + 1

            explored.add(position.second)

            if (map[position.second].first > nextCost) {
                map[position.second] = nextCost to p.id
                shared.remove(position.second)
            } else if (map[position.second].first == nextCost) {
                shared.add(position.second)
            }

            position.second.forEachNeighbors { nextPoint ->
                if (nextPoint.inMap(map) && map[nextPoint].first > nextCost && !explored.contains(nextPoint) && queue.none { it.second == nextPoint }) {
                    queue.add(nextCost to nextPoint)
                }
            }
        }
    }

    override fun solve1(): Long {
        return points.filter { !infinite.contains(it.id) }.maxOf { p ->
            map.sumOf { line -> line.count { it.second == p.id } }
        }.toLong()
    }

    override fun solve2(): Long {
        var sum = 0L


        mapManhattan.forEachPoint {
            sum += if (mapManhattan[it] < 10_000) 1 else 0
        }

        return sum
    }
}

fun main() {
    val day = Day6(readFullText("_2018/d6/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day6(readFullText("_2018/d6/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}