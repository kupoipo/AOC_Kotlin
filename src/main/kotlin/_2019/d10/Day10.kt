package _2019.d10

import util.*
import kotlin.system.measureNanoTime

class Day10(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val asteroid: List<Point> = matrixFromString(input, '.') { it }.let { map ->
        map.points().filter { p -> map[p] == '#' }
    }

    val map = matrixFromString(input, '.') { it }

    private val deployment = asteroid.map { it to vectors(it) }
        .maxBy { p -> p.second.size }

    private fun vectors(pos: Point): MutableList<Point> {
        val vectors = asteroid.map { pos - it }.filter { it != Point(0, 0) }.toMutableList()

        var index = 0
        while (index < vectors.size) {
            val originU = vectors[index]
            val u = vectors[index].reduceToSmallestVector()

            for (v in vectors.toMutableList()) {
                if (v == originU) continue
                if (u.sameDirection(v)) {
                    if (u.collinear(v)) {
                        vectors.remove(v)
                    }
                }
            }

            index++
        }

        return vectors
    }

    override fun solve1(): Long = deployment.second.size.toLong()

    override fun solve2(): Long {
        val upRightVectors = deployment.second.filter { it.y < 0 && it.x >= 0 }.map { it.reduceToSmallestVector() }.sortedBy { Point(0,0).angleWith(it) }
        val downRightVectors = deployment.second.filter { it.y >= 0 && it.x > 0 }.map { it.reduceToSmallestVector() }.sortedBy { Point(0,0).angleWith(it) }
        val downLeftVectors = deployment.second.filter { it.y > 0 && it.x <= 0 }.map { it.reduceToSmallestVector() }.sortedBy { Point(0,0).angleWith(it) }
        val upLeftVectors = deployment.second.filter { it.y <= 0 && it.x < 0 }.map { it.reduceToSmallestVector() }.sortedBy { Point(0,0).angleWith(it) }

        val vectorsClockWise = listOf(upRightVectors, downRightVectors, downLeftVectors, upLeftVectors)

        var cpt = 0

        while (true) {
            for (clockWise in vectorsClockWise) {
                for (vector in clockWise) {
                    var position = deployment.first + vector

                    while (position.inMap(map) && map[position] == '.')
                        position += vector

                    if (position.inMap(map)) {
                        cpt++
                        map[position] = '.'
                    }

                    if (cpt == 199)
                        return position.x * 100 + position.y
                }
            }
        }
    }
}

fun main() {
    val day = Day10(false, readFullText("_2019/d10/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    val dayTest = Day10(true, readFullText("_2019/d10/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}