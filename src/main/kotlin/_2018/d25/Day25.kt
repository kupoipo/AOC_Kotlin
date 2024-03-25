package _2018.d25

import util.Day
import util.allLong
import util.readFullText
import kotlin.math.abs
import kotlin.system.measureNanoTime
class Day25(override val input : String) : Day<Long>(input) {
    class Point4D(val x : Long, val y : Long, val z: Long, val t: Long) {
        fun manhattan(other: Point4D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(t - other.t)
    }

    private val points = input.split("\n").map { line ->
        line.allLong().let { Point4D(it.first(), it[1], it[2], it.last()) }
    }

    private val constellations = mutableListOf<MutableSet<Point4D>>()

    override fun solve1(): Long {
        for (p in points) {
            val goals = constellations.filter { constellation -> constellation.any { it.manhattan(p) <= 3 } }

            when (goals.size) {
                0 -> constellations.add(mutableSetOf(p))
                1 -> goals.first().add(p)
                else -> {
                    goals.forEach { constellations.remove(it) }
                    constellations.add(buildSet {
                        goals.forEach { this.addAll(it) }
                        add(p)
                    }.toMutableSet())
                }
            }
        }

        return constellations.size.toLong()
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day25(readFullText("_2018/d25/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day25(readFullText("_2018/d25/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}