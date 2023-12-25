package _2023.d23

import kotlin.system.measureNanoTime
import util.*

const val DIRECTION = "^<>v"

class Node2(val position: Point) {
    val children = mutableMapOf<Node2, Int>()
    val id = name++

    companion object {
        var name = 1
    }

    override fun toString(): String {
        return "[${position.y}; ${position.x}]"
    }
}

/**
 * 2182 - 6670
 */
class Day23Clean(override val input: String) : Day<Long>(input) {
    val map = matrixFromString(input, '.') { it }
    val goal = Point(map[0].lastIndex - 1, map.lastIndex)
    val start = Point(1, 0)
    val end = Point(map[0].lastIndex - 1, map.lastIndex)
    val graph = map.points().filter { it.adjacent().count { adj -> adj.inMap(map) && map[adj] in DIRECTION } > 2 }.toMutableSet()
        .apply { add(start); add(end) }.associateWith { Node2(it) }
    override fun solve1(): Long {
        return 0
    }

    fun longestPathFrom(root: Node2, size: Long, path: Set<Node2>): Long {
        if (root.id == Node2.name - 1) {
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

    fun stepFrom(node: Node2, dir: Direction) : Pair<Node2, Int> {
        var position = node.position
        var nextDir = dir
        var nbStep = 1

        position += nextDir

        while (position !in graph.keys) {
            while (position.inMap(map) && map[position] != '#' && position !in graph.keys) {
                position += nextDir
                nbStep++
            }

            if (position.outOfMap(map) || map[position] == '#') {
                nbStep--
                position += nextDir.opposite()
                for (dir in Direction.values()) {
                    if (nextDir == dir.opposite() || dir == Direction.NONE)
                        continue

                    if ((position+dir).inMap(map) && map[position + dir] != '#') {
                        nextDir = dir
                        break
                    }
                }
            }
        }

        return graph[position]!! to nbStep
    }

    override fun solve2(): Long {
        for (g in graph) {
            val position = g.key

            for (d in Direction.values()) {
                (position + d).let {
                    if (it.inMap(map) && (map[it] in DIRECTION || g.value.position == start)) {
                        val (node, step) = stepFrom(g.value, d)
                        g.value.children[node] = step
                    }
                }
            }
        }

        for (g in graph.values) {
            for (c in g.children.keys) {
                println(""" "$g" -> "$c" [label=${g.children[c]}]  """)
            }
        }


        return longestPathFrom(graph[start]!!, 0, setOf(graph[start]!!))
    }
}

fun main() {
    val day = Day23Clean(readFullText("_2023/d23/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")


    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day23Clean(readFullText("_2023/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}