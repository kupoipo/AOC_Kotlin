package _2023.d11

import util.*
import kotlin.system.measureNanoTime

class Day11(override val input: String) : Day<Long>(input) {
    val galaxy = matrixFromString(input, '.') { it }

    val indexesColumn = galaxy[0].indices.filter { column -> galaxy.indices.all { galaxy[it][column] == '.' } }
    val indexesRows = galaxy.indices.filter { line -> galaxy[line].all { it == '.' } }.toMutableList()

    fun totalLength(distance: Int): Long {
        val planets = galaxy.points().filter { galaxy[it] == '#' }

        planets.forEach { p ->
            indexesColumn.reversed().forEach { c -> if (p.x > c) p.x += distance }
            indexesRows.reversed().forEach { r -> if (p.y > r) p.y += distance }
        }

        return planets.indices.sumOf { i1 ->
            (i1 + 1 until planets.size).sumOf { i2 ->
                val p1 = planets[i1]
                val p2 = planets[i2]
                p1.manhattan(p2).toLong()
            }
        }
    }

    override fun solve1(): Long = totalLength(1)

    override fun solve2(): Long = totalLength(999_999)
}

fun main() {
    val day = Day11(readFullText("_2023/d11/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day11(readFullText("_2023/d11/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}