package _2019.d20

import util.*
import java.util.PriorityQueue
import kotlin.system.measureNanoTime

class Wrap(val name: String, val outer: Point, val inner: Point, val isInner: Boolean, var exit: Wrap?) {
    val reachableWraps = mutableSetOf<Pair<Wrap, Int>>()

    override fun toString(): String {
        return "$name : $outer - $inner exit : ${exit?.outer} : isInner : $isInner"
    }
}

class Day20(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map: Matrix<Char> = matrixFromString(input, ' ') { it }
    private val wraps = mutableMapOf<Point, Wrap>()
    private val start: Point
    private val goal: Point

    private fun processMap(wraps: MutableMap<String, MutableList<Wrap>>, horizontal: Boolean) {
        for (i in (if (horizontal) map.indices else map[0].indices)) {
            var name = ""
            for (j in (if (horizontal) map[0].indices else map.indices)) {
                val (y, x) = if (horizontal) i to j else j to i
                if (map[y][x] in 'A'..'Z') {
                    name += map[y][x]
                    if (name.length == 2) {
                        val rightBottom = if (horizontal) Point(x + 1, y) else Point(x, y + 1)
                        val leftTop = if (horizontal) Point(x - 2, y) else Point(x, y - 2)

                        val (outer, inner) = if (rightBottom.inMap(map) && map[rightBottom] == '.') {
                            rightBottom to Point(x, y)
                        } else {
                            leftTop to if (horizontal) Point(x - 1, y) else Point(x, y - 1)
                        }

                        val wrap =
                            Wrap(
                                name,
                                outer,
                                inner,
                                inner.y != 1L && inner.x.toInt() != map.nbColumns - 2 && inner.x != 1L && inner.y.toInt() != map.size - 2,
                                null
                            )

                        if (wraps.getOrPut(name) { mutableListOf() }.isNotEmpty()) {
                            wrap.exit = wraps[name]!!.first()
                            wraps[name]!!.first().exit = wrap
                        }
                        wraps[name]!!.add(wrap)
                    }
                } else {
                    name = ""
                }
            }
        }
    }

    init {
        val wraps = mutableMapOf<String, MutableList<Wrap>>()
        processMap(wraps, horizontal = true)
        processMap(wraps, horizontal = false)

        wraps.forEach { (_, v) -> v.forEach { this.wraps[it.outer] = it } }

        start = wraps["AA"]!!.first().outer
        goal = wraps["ZZ"]!!.first().outer

        for (w in this.wraps.values) {
            val queue = mutableListOf(w.outer to 0)
            val visited = mutableSetOf<Point>()

            while (queue.isNotEmpty()) {
                val current = queue.removeAt(0)

                visited.add(current.first)

                for (dir in Direction.values().dropLast(1)) {
                    var newPoint = current.first + dir
                    val wrap = this.wraps.values.firstOrNull { it.inner == newPoint }
                    var add: RecursivePoint

                    if (wrap != null) {
                        if (wrap != w) {
                            w.reachableWraps.add(wrap to current.second+1)
                        }
                        continue
                    }

                    if (map[newPoint.y][newPoint.x] != '#' && newPoint !in visited && !queue.any { newPoint == it.first }) {
                        queue.add(newPoint to current.second + 1)
                    }
                }
            }
        }
    }

    data class RecursivePoint(val point: Point, val pathSize: Int, val depth: Int, val parent: RecursivePoint? = null) : Comparable<RecursivePoint> {
        override fun compareTo(other: RecursivePoint): Int {
            return pathSize.compareTo(other.pathSize)
        }

    }

    fun path(part2: Boolean): Long {
        val queue = PriorityQueue<RecursivePoint>()
        val visited = mutableSetOf<Pair<Point, Int>>()

        queue.add(RecursivePoint(start, 0, 0))
        while (queue.isNotEmpty()) {
            val current = queue.poll()

            visited.add(current.point to current.depth)

            for (p in wraps[current.point]!!.reachableWraps) {
                if (p.first.exit == null) {
                    if (p.first.outer == goal &&  if (part2) current.depth == 0 else true) {
                        return current.pathSize.toLong() + p.second - 1
                    }
                    continue
                }
                val newPoint = p.first.exit!!.outer
                val newDepth = current.depth + (if (p.first.isInner) 1 else -1)

                if (part2 && newDepth < 0) continue

                if (map[newPoint.y][newPoint.x] != '#' && (newPoint to newDepth) !in visited && !queue.any { newPoint == it.point && newDepth == it.depth }) {
                    queue.add(RecursivePoint(newPoint, current.pathSize + p.second, newDepth, current))
                    visited.add(newPoint to newDepth)
                }
            }

        }

        throw Exception("No path found")
    }

    override fun solve1(): Long = path(false)

    override fun solve2(): Long = path(true)

}

fun main() {
    val day = Day20(false, readFullText("_2019/d20/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day20(true, readFullText("_2019/d20/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}