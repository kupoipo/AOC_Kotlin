package _2025.d8

import util.Day
import util.Point3DLong
import util.allLong
import util.readFullText
import java.util.PriorityQueue
import kotlin.system.measureNanoTime


class Day8(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class ClosestPoint(val from: Point3DLong, val to: Point3DLong, val distance: Double) :
        Comparable<ClosestPoint> {
        override fun compareTo(other: ClosestPoint): Int = this.distance.compareTo(other.distance)
    }

    private val points = input.split("\n").map { line -> line.allLong().let { Point3DLong(it[0], it[1], it[2]) } }
    private val closestPoints = PriorityQueue<ClosestPoint>()
    private val circuits = mutableMapOf<Point3DLong, MutableSet<Point3DLong>>()

    init {
        for (i in points.indices) {
            for (j in i + 1..points.lastIndex) {
                val pointFrom = points[i]
                val pointTo = points[j]

                closestPoints.add(ClosestPoint(pointFrom, pointTo, pointFrom.euclidean(pointTo)))
            }
        }
    }

    private fun connect(nbPairs: Int): Long {
        for (i in 0 until nbPairs) {
            val pair = closestPoints.poll()

            if (circuits[pair.to] == null || circuits[pair.from] == null) {
                val newCircuit = if (circuits[pair.to] == null && circuits[pair.from] == null) {
                    mutableSetOf()
                } else {
                    circuits.getOrElse(pair.to) { circuits[pair.from]!! }
                }

                newCircuit.add(pair.to)
                newCircuit.add(pair.from)

                circuits[pair.to] = newCircuit
                circuits[pair.from] = newCircuit
            } else {
                val circuitFrom = circuits[pair.from]!!
                val circuitTo = circuits[pair.to]!!
                
                circuitFrom.addAll(circuitTo)
                circuitFrom.forEach { circuits[it] = circuitFrom }
            }

            if (circuits[pair.from]!!.size == points.size) {
                return pair.from.x * pair.to.x
            }
        }

        val biggestCircuit = circuits.values.toSet().toList().sortedBy { -it.size }

        return biggestCircuit.map { it.size.toLong() }.take(3).reduce(Long::times)
    }

    override fun solve1(): Long = connect(if (isTest) 10 else 1000)

    override fun solve2(): Long = connect(closestPoints.size)
}

fun main() {
    val day = Day8(false, readFullText("_2025/d8/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day8(true, readFullText("_2025/d8/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}