package _2023.d23

import kotlin.system.measureNanoTime
import util.*

class D23Node(val parent: D23Node?, val position: Point, val step: Int, val d: Direction) {
    val children = mutableMapOf<D23Node, Int>()
    val id = name++

    override fun toString(): String {
        return "$id : [${position.y}, ${position.x}]"
    }

    companion object {
        var name = 1
    }
}

/**
 * 2182 - 6670
 */
class Day23(override val input: String) : Day<Long>(input) {
    val map = matrixFromString(input, '.') { it }
    val goal = Point(map[0].lastIndex - 1, map.lastIndex)

    override fun solve1(): Long {
        return 0
    }

    fun longestPathFrom(root: D23Node, size: Long, path: Set<D23Node>): Long {
        if (root.id == D23Node.name - 1) {
            return size
        }

        var maxSize = 0L

        for ((c, s) in root.children) {
            if (c in path)
                continue

            val currentSize = longestPathFrom(c, size + s, path.toMutableSet().apply { add(c) })

            if (currentSize > maxSize)
                maxSize = currentSize
        }

        return maxSize
    }

    override fun solve2(): Long {
        val graph = mutableMapOf<Point, D23Node>()
        val root = D23Node(null, Point(1, 0), 0, Direction.DOWN)
        val queue = mutableListOf(Point(1, 0) to Direction.DOWN)
        graph[Point(1, 0)] = root

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val currentNode = graph[current.first]
            var position = current.first
            var nextDir = current.second
            var nbStep = 1

            position += nextDir

            /**
             * Moving toward the next intersection of the maze
             */
            while (position != goal
                && position.adjacent(false).count { map[it] in "^<>v" } < 2
            ) {
                /**
                 *
                 */
                while (map[position] != '#' && position != goal
                    && position.adjacent(false).count { map[it] in "^<>v" } < 2
                ) {
                    position += nextDir
                    nbStep++
                }

                if (map[position] == '#') {
                    nbStep--
                    position += nextDir.opposite()
                    for (dir in Direction.values()) {
                        if (nextDir == dir.opposite() || dir == Direction.NONE)
                            continue

                        if (map[position + dir] != '#') {
                            nextDir = dir
                            break
                        }
                    }
                }
            }

            if (position == goal) {
                val node = graph.getOrPut(position) { D23Node(currentNode, position, 0, nextDir) }
                currentNode!!.children[node] = nbStep
            } else {
                if (graph[position] == null) {
                    val node = graph.getOrPut(position) { D23Node(currentNode, position, 0, nextDir) }
                    currentNode!!.children[node] = nbStep

                    for (dir in Direction.values()) {
                        if ((nextDir == dir.opposite() && position.adjacent(false)
                                .count { map[it] in "^<>v" } != 4) || dir == Direction.NONE || map[position + dir] == '#'
                        )
                            continue

                        queue.add(position to dir)
                    }
                } else {
                    val node = graph[position]!!
                    currentNode!!.children[node] = nbStep
                }
            }
        }

        return longestPathFrom(root, 0, setOf(root))
    }
}

fun main() {
    val day = Day23(readFullText("_2023/d23/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day23(readFullText("_2023/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}