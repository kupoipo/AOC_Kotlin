package _2017.d12

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day12(override val input: String) : Day<Long>(input) {
    val pipes = input.split("\n").map { it.allInts().drop(1) }.toMutableList()

    fun connexionTo(i: Int) : Set<Int> {
        val visited = mutableSetOf(0)
        val queue = pipes[i].toMutableList()

        while (queue.isNotEmpty()) {
            val i = queue.removeFirst()

            if (i !in visited) {
                visited.add(i)
                for (el in pipes[i])
                    if (el !in queue) queue.add(el)
            }

        }

        return visited
    }

    override fun solve1(): Long = connexionTo(0).size.toLong()

    override fun solve2(): Long {
        val groups = mutableListOf<Set<Int>>()

        repeat(2000) { i ->
            if (!groups.any { set -> i in set }) {
                groups.add(connexionTo(i))
            }
        }

        return groups.size.toLong()
    }
}

fun main() {
    val day = Day12(readFullText("_2017/d12/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day12(readFullText("_2017/d12/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}