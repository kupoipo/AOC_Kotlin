package _2015.d18

import util.*
import kotlin.system.measureTimeMillis

class Day18(override val input: String) : Day<Long>(input) {
    var map: Matrix<Char> = matrixFromString(input, '#') { it }

    fun reset() {
        map = matrixFromString(input, '#') { it }
    }

    private fun nbNeighbors(p: Point): Int {
        return p.adjacent().filter { it.x >= 0 && it.y >= 0 && it.x < map[0].size && it.y < map.size }
            .count { map[it.y][it.x] == '#' }
    }

    private fun nextState() {
        emptyMatrixOf(map.size, map[0].size, '#').apply {
            map.forEachPoint { p ->
                nbNeighbors(p).let { nb ->
                    if (map[p] == '#') {
                        this[p] = if (nb == 3 || nb == 2) '#' else '.'
                    } else {
                        this[p] = if (nb == 3) '#' else '.'
                    }

                }
            }

            map = this
        }
    }

    override fun solve1(): Long {
        repeat(100) {
            nextState()
        }

        return map.sumOf { it.count { it == '#' } }.toLong()
    }

    override fun solve2(): Long {
        reset()

        repeat(100) {
            map.corners().forEach { p -> map[p] = '#' }
            nextState()
        }

        map.corners().forEach { p -> map[p] = '#' }

        return map.sumOf { it.count { it == '#' } }.toLong()
    }
}

fun main() {
    val day = Day18(readFullText("_2015/d18/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day18(readFullText("_2015/d18/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}