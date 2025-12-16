package _2025.d9

import util.*
import java.lang.RuntimeException
import java.util.*
import kotlin.system.measureNanoTime

class Day9(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val points = input.split("\n").map { line -> line.allLong().let { Point(it.first(), it.last()) } }
    private val rectangles = PriorityQueue<Rectangle>()

    init {
        for (pair in points.combination()) {
            rectangles.add(
                Rectangle(
                    pair.first().x, pair.first().y,
                    pair.last().x,
                    pair.last().y
                )
            )
        }
    }

    override fun solve1(): Long = rectangles.poll().area

    override fun solve2(): Long {
        val polygon = mutableListOf<Segment>()

        var current = points.first()

        for (p in points) {
            polygon.add(Segment(current, p))
            current = p
        }

        polygon.add(Segment(points.last(), points.first()))

        while (rectangles.isNotEmpty()) {
            val currentRect = rectangles.poll()

            if (currentRect.points.all { it.isPointInsidePolygon(points) } && !currentRect.segments.any {
                    polygon.any { pSegment ->
                        pSegment.intersect(
                            it
                        )
                    }
                }) {
                return currentRect.area
            }
        }

        throw RuntimeException("No solution found")
    }
}

fun main() {
    var day: Day9
    println("Temps construction : ${measureNanoTime { day = Day9(false, readFullText("_2025/d9/input")) } / 1e9}s\n")
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            var dayTest: Day9
            println("Test $i")
            println("Temps construction : ${measureNanoTime { dayTest = Day9(true, readFullText("_2025/d9/test$i")) } / 1e9}s\n")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}