package _2024.d21

import util.*
import java.lang.RuntimeException
import kotlin.system.measureNanoTime

class Day21(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val numericKeyboard = "789456123 0A".mapIndexed { index, c -> c to index }.associate { (c, index) ->
        c to Point(index % 3, index / 3)
    }

    private val arrowKeyboard = " ^A<v>".mapIndexed { index, c -> c to index }.associate { (c, index) ->
        c to Point(index % 3, index / 3)
    }

    private val codes = input.split("\n")

    private fun bfs(from: Point, to: Point, keyBoard: Map<Char, Point>): List<String> {
        val res = mutableListOf<String>()
        val queue = mutableListOf<Pair<MutableList<Direction>, Point>>()
        val seen = mutableSetOf<Point>()

        queue.add(mutableListOf<Direction>() to from)

        while (queue.isNotEmpty()) {
            val (currentMoves, currentPos) = queue.removeFirst()

            // Maybe remove this cond, there is a world where more input in a robot leads to less input in the next one
            // --------------------------------------------------------------------------------------------------------
            if (currentMoves.size > from.manhattan(to)) continue

            if (currentPos == to) {
                res.add(currentMoves.map { it.sign }.joinToString("") + "A")
                continue
            }

            seen.add(currentPos)

            for (d in Direction.values().dropLast(1)) {
                val nextPos = currentPos + d

                val yRange = if (keyBoard == arrowKeyboard) 0..1 else 0..3

                if (nextPos.x in 0..3 && nextPos.y in yRange && nextPos !in seen && keyBoard[' ']!! != nextPos) {
                    val moves = currentMoves.toMutableList().apply { this.add(d) }
                    queue.add(moves to nextPos)
                }
            }
        }

        return res
    }

    private fun shortestPathFrom(code: String, keyboard: Map<Char, Point>): List<String> {
        var res = mutableListOf<String>()
        var current = keyboard['A']!!

        for (c in code) {
            if (c !in keyboard.keys) throw RuntimeException("Only ${keyboard.keys} are allowed : $c found")

            val nextKey = keyboard[c]
            val paths = bfs(current, nextKey!!, keyboard)

            if (res.isEmpty()) res.addAll(paths)
            else {
                val nextRes = mutableListOf<String>()
                for (currentPath in res) {
                    for (newPath in paths) {
                        nextRes.add(currentPath + newPath)
                    }
                }
                res = nextRes
            }

            current = nextKey
        }


        return res
    }

    private fun toNextRobot(inputs: List<String>): List<String> {
        var paths = inputs.map { shortestPathFrom(it, arrowKeyboard) }.flatten()
        val min = paths.minOf { it.length }
        return paths.filter { it.length == min }
    }

    private fun shortestPath(code: String): String {
        println(code)
        val paths = shortestPathFrom(code, numericKeyboard)
        println(paths.first().length)
        val paths2 = toNextRobot(paths)
        println(paths2.first().length)
        val paths3 = toNextRobot(paths2)
        println(paths3.first().length)

        val paths4 = toNextRobot(paths3)
        println(paths4.first().length)

        return paths3.minBy { it.length }
    }

    override fun solve1(): Long = codes.sumOf { it.allInts().first() * shortestPath(it).length }.toLong()

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day21(false, readFullText("_2024/d21/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day21(true, readFullText("_2024/d21/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}