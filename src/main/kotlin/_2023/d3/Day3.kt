package _2023.d3

import util.*
import kotlin.system.measureTimeMillis

class Number(var value: Int = 0, var positions: MutableList<Point> = mutableListOf(), var adjacent: Boolean = false) {
    fun majAdjacent(map: Matrix<Char>) {
        positions.forEach {
            it.adjacent().forEach { pAdjacent ->
                if (pAdjacent.x >= 0 && pAdjacent.y >= 0 && pAdjacent.x < map[0].size && pAdjacent.y < map.size) {
                    if (map[pAdjacent.y][pAdjacent.x] != '.' && !map[pAdjacent.y][pAdjacent.x].isDigit())
                        adjacent = true
                }
            }
        }
    }
}

class Day3(override val input: String) : Day<Long>(input) {
    val map = matrixFromString(input, '.') { it }
    val numbers = mutableListOf<Number>()

    init {
        var number = Number()
        var numberStr = ""

        for (line in map.indices) {
            for (col in map[line].indices) {
                val char = map[line][col]

                if (char.isDigit()) {
                    numberStr += char
                    number.positions.add(Point(col, line))
                } else if (numberStr != "") {
                    number.value = numberStr.toInt()
                    number.majAdjacent(map)
                    numbers.add(number)
                    number = Number()
                    numberStr = ""
                }
            }
        }
    }

    override fun solve1(): Long = numbers.filter { it.adjacent }.sumOf { it.value }.toLong()

    /**
     * More optimal version
     */
    override fun solve2(): Long = map.mapIndexed { line, l ->
        List(l.size) { col ->
            if (map[line][col] == '*') {
                val gears = numbers.filter { number ->
                    Point(col, line).adjacent().forEach {
                        if (number.positions.contains(it)) return@filter true
                    }

                    false
                }

                if (gears.size == 2) return@List gears.first().value * gears.last().value
            }
            0
        }.sum()
    }.sum().toLong()

    /**
     * Basic version
     *
    override fun solve2(): Long {
        var total = 0L
        for (line in map.indices) {
            for (col in map[line].indices) {
                if (map[line][col] == '*') {

                    val gears = numbers.filter { number ->
                        Point(col, line).adjacent().forEach {
                            if (number.positions.contains(it))
                                return@filter true
                        }

                        false
                    }

                    if (gears.size == 2)
                        total += gears.first().value * gears.last().value
                }
            }
        }

        return total
    }
    */
}

fun main() {
    val day = Day3(readFullText("_2023/d3/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day3(readFullText("_2023/d3/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}