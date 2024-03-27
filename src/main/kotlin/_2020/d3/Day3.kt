package _2020.d3

import d7.list
import util.*
import kotlin.system.measureNanoTime

class Day3(override val input: String) : Day<Long>(input) {
    val map: Matrix<Char> = matrixFromString(input, '.') { it }
    val direction = listOf(Point(3, 1), Point(1, 1), Point(5, 1), Point(7, 1), Point(1, 2))

    private fun traverse(vector: Point): Long {
        var position = Point(0, 0)
        var nbTrees = 0

        while (position.y.toInt() < map.lastIndex) {
            position += vector
            position.x = position.x % map[0].size
            if (map[position] == '#') nbTrees++
        }

        return nbTrees.toLong()
    }

    override fun solve1(): Long {
        return traverse(direction.first())
    }

    override fun solve2(): Long {
        return direction.fold(1) { acc, point -> acc * traverse(point) }
    }
}

fun main() {
    val day = Day3(readFullText("_2020/d3/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day3(readFullText("_2020/d3/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}