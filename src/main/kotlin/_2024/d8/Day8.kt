package _2024.d8

import util.*
import kotlin.system.measureNanoTime

class Day8(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map = matrixFromString(input, '.') { it }
    private val antennas = buildMap<Char, MutableList<Point>> {
        map.points().forEach { p ->
            if (map[p] != '.') {
                this.getOrPut(map[p]) { mutableListOf() }.add(p)
            }
        }
    }

    override fun solve1(): Long = buildSet {
        antennas.values.forEach { points ->
            points.pairs().forEach { (p, p2) ->
                addAll(listOf(p.symmetries(p2), p2.symmetries(p)).filter { it.inMap(map) })
            }
        }
    }.size.toLong()

    override fun solve2(): Long = buildSet {
        antennas.values.forEach { points ->
            points.pairs(true).forEach { (p, p2) ->
                var current = p.symmetries(p2)
                val delta = p2 - p

                while (current.inMap(map)) {
                    add(current)
                    current += delta
                }
                addAll(points)
            }
        }
    }.size.toLong()

}

fun main() {
    val day = Day8(false, readFullText("_2024/d8/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day8(true, readFullText("_2024/d8/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }
}